package com.example.praktikum_2

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NoteRepository(DatabaseHelper(application))

    private val _notes = mutableStateOf(listOf<Note>())
    val notes: State<List<Note>> = _notes

    fun loadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getNotes()
            _notes.value = data
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(title, content)
            loadNotes()
        }
    }
}