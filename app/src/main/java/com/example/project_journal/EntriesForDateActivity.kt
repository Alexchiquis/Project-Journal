package com.example.project_journal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.nio.channels.spi.AbstractSelectableChannel

class EntriesForDateActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:JournalEntryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entries_for_date)
        val textViewDate:TextView = findViewById(R.id.textViewDate)
        recyclerView = findViewById(R.id.recyclerViewEntriesForDate)
        adapter = JournalEntryAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Obtiene la fecha seleccionada de los extras del Intent
        val selectedDate = intent.getStringExtra("selectedDate")
        textViewDate.text = "Diario fecha: $selectedDate"
        // Carga las entradas relacionadas con la fecha seleccionada
        loadEntriesForDate(selectedDate)
    }
    private fun loadEntriesForDate(selectedDate: String?){
        // Implementa la lógica para cargar las entradas relacionadas con la fecha seleccionada
        if(selectedDate != null){
            val dbHelper = DbHelper(this)
            val entries = dbHelper.getEntriesForDate(selectedDate)
            adapter.submitList(entries)
        }
    }

}