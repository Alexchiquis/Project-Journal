package com.example.project_journal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import java.util.Locale

class SearchEntry : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_entry)
        calendarView = findViewById(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Cargar las entradas relacionadas con la fecha seleccionada
            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                GregorianCalendar(year, month, dayOfMonth).time
            )
            // Inicia una nueva actividad  para mostrar las entradas de la fecha seleccionada
            startEntriesForDateActivity(selectedDate)
        }
    }
    private fun startEntriesForDateActivity(selectedDate: String){
        // Puedes pasar la fecha como un extra en el Intent o como un argumento en el Fragment
        val intent = Intent(this, EntriesForDateActivity::class.java)
        intent.putExtra("selectedDate",selectedDate)
        startActivity(intent)
    }

}