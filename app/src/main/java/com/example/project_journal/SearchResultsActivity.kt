package com.example.project_journal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        val textViewWord: TextView = findViewById(R.id.textViewWord)
        val keyword = intent.getStringExtra("searchKeyword")

        val dbHelper = DbHelper(this)
        val entries = dbHelper.searchEntriesByKeyword(keyword)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEntriesForWord)
        val adapter = JournalEntryAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        textViewWord.text = "Palabara Buscada: $keyword"
        adapter.submitList(entries)
    }
}