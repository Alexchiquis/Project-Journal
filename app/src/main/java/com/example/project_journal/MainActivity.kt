package com.example.project_journal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAddEntry : ImageButton = findViewById(R.id.buttonAddEntry)
        buttonAddEntry.setOnClickListener{
            val intent = Intent(this, CreateEntry::class.java)
            startActivity(intent)
        }

    }
}



