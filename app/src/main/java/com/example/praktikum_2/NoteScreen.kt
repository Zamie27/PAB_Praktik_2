package com.example.praktikum_2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteScreen(viewModel: NoteViewModel) {
//  Menambahkan tombol Cancel Edit yang muncul hanya saat proses edit berlangsung.
    val noteList = viewModel.notes.value
    var titleInput by remember { mutableStateOf("") }
    var contentInput by remember { mutableStateOf("") }
    var editingNote by remember { mutableStateOf<Note?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
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
//              Mengubah logika tombol Add Note: Jika sedang dalam mode edit, teks tombol berubah menjadi Update Note dan menjalankan fungsi update.
                if (titleInput.isNotBlank() && contentInput.isNotBlank()) {
                    if (editingNote == null) {
                        viewModel.addNote(titleInput, contentInput)
                    } else {
                        viewModel.updateNote(editingNote!!.id, titleInput, contentInput)
                        editingNote = null
                    }
                    titleInput = ""
                    contentInput = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
//          Menambahkan state editingNote untuk melacak data mana yang sedang diedit.
            Text(if (editingNote == null) "Add Note" else "Update Note")
        }

        if (editingNote != null) {
            TextButton(
                onClick = {
                    editingNote = null
                    titleInput = ""
                    contentInput = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel Edit")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(noteList) { note ->
//              Menambahkan Modifier.clickable pada Card: Sehingga ketika Card diklik, data tersebut akan muncul kembali di kolom input untuk diedit.
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            editingNote = note
                            titleInput = note.title
                            contentInput = note.content
                        }
                ) {
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