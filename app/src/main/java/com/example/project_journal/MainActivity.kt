package com.example.project_journal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: JournalEntryAdapter
    override fun onResume() {
        super.onResume()
        loadEntriesFromDatabase()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAddEntry : ImageButton = findViewById(R.id.buttonAddEntry)
        //Visualizar entradas
        recyclerView = findViewById(R.id.recyclerView)
        adapter = JournalEntryAdapter() // Crea el adaptador personalizado
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonAddEntry.setOnClickListener{
            val intent = Intent(this, CreateEntry::class.java)
            startActivity(intent)
        }

    }
    private fun loadEntriesFromDatabase() {
        val dbHelper = DbHelper(this)
        val entries = dbHelper.getAllEntries()

        // Agrega las entradas al adaptador
        adapter.submitList(entries)
    }
}



