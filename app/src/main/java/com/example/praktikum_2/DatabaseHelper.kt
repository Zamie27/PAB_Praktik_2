package com.example.praktikum_2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Note(
    val id: Int,
    val title: String,
    val content: String
)

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "notes.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE Notes (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Notes")
        onCreate(db)
    }

    fun insertNote(title: String, content: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("content", content)
        }
        return db.insert("Notes", null, values) != -1L
    }

    fun getAllNotes(): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM Notes", null)
    }
}