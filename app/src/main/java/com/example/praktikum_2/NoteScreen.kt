package com.example.praktikum_2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteScreen(viewModel: NoteViewModel) {
    // Menggunakan .value secara langsung untuk mendapatkan List<Note>
    val noteList = viewModel.notes.value

    Column(modifier = Modifier.padding(16.dp)) {
        var titleInput by remember { mutableStateOf("") }
        var contentInput by remember { mutableStateOf("") }

        OutlinedTextField(
            value = titleInput,
            onValueChange = { titleInput = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = contentInput,
            onValueChange = { contentInput = it },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if(titleInput.isNotBlank() && contentInput.isNotBlank()){
                    viewModel.addNote(titleInput, contentInput)
                    titleInput = ""
                    contentInput = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Note")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            // Pastikan menggunakan noteList yang merupakan List<Note>
            items(noteList) { note ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(note.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(note.content, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}