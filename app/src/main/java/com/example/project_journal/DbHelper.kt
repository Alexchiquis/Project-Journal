package com.example.project_journal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper( context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {
    companion object {
        private const val dbName = "journalEntries.db"
        private const val dbVersion = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS journal_entries (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "content TEXT," +
                    "date TEXT," +
                    "mood TEXT," +
                    "imagePath TEXT)"
        )

    }

    fun saveEntry(entry: JournalData): Boolean { //GUARDAR DATOS EN BD

        val db = writableDatabase
        val values = ContentValues()
        values.put("content", entry.textContent)
        values.put("date", entry.entryDate)
        values.put("mood", entry.moodEmoji)
        values.put("imagePath", entry.photoPath)

        val newRowId = db.insert("journal_entries", null, values)
        db.close()

        return newRowId != -1L // Retorna true si la inserción fue exitosa
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}