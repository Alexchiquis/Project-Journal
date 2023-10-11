package com.example.project_journal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper( context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {
    companion object {
        private const val dbName = "journalEntries.db"
        private const val dbVersion = 1
    }
    private val TABLE_NAME = "journal_entries"
    private val COLUMN_ID = "_id"
    private val COLUMN_CONTENT = "content"
    private val COLUMN_DATE = "date"
    private val COLUMN_MOOD = "mood"
    private val COLUMN_IMAGE_PATH = "imagePath"

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

    fun getAllEntries(): List<JournalData> {
        val entries = mutableListOf<JournalData>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM journal_entries", null)

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getLong(it.getColumnIndexOrThrow(COLUMN_ID))
                    val content = it.getString(it.getColumnIndexOrThrow(COLUMN_CONTENT))
                    val date = it.getString(it.getColumnIndexOrThrow(COLUMN_DATE))
                    val mood = it.getString(it.getColumnIndexOrThrow(COLUMN_MOOD))
                    val imagePath = it.getString(it.getColumnIndexOrThrow(COLUMN_IMAGE_PATH))

                    Log.d("ImageDebug", "Ruta de la imagen en la base de datos: $imagePath")

                    val entry = JournalData(id, content, date, mood, imagePath)
                    entries.add(entry)
                } while (it.moveToNext())
            }
        }
        return entries
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}