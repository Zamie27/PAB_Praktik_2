package com.example.praktikum_2


class NoteRepository(private val dbHelper: DatabaseHelper) {
    fun getNotes(): List<Note> {
        val cursor = dbHelper.getAllNotes()
        val notes = mutableListOf<Note>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
                notes.add(Note(id, title, content))
            } while (cursor.moveToNext())
        }
        cursor.close() // Selalu tutup cursor untuk mencegah memory leak
        return notes
    }

    fun addNote(title: String, content: String) {
        dbHelper.insertNote(title, content)
    }
}