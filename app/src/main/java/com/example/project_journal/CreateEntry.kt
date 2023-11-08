package com.example.project_journal

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateEntry : AppCompatActivity() {

    private lateinit var editTextContent: EditText
    private lateinit var imageView: ImageView
    private lateinit var addImageButton: Button
    private lateinit var emojiSpinner: Spinner
    private lateinit var saveButton: Button

    private var imagePath: String? = null
    private val REQUEST_IMAGE_PICK = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_entry)

        // Inicializamos lo del XML
        editTextContent = findViewById(R.id.editTextContent)
        imageView = findViewById(R.id.imageView)
        addImageButton = findViewById(R.id.addImageButton)
        emojiSpinner = findViewById(R.id.emojiSpinner)
        saveButton = findViewById(R.id.saveButton)

        //Spinner para ver los emojis y seleccionarlos
        val emojiAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.emoji_array,
            android.R.layout.simple_spinner_item
        )
        emojiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        emojiSpinner.adapter = emojiAdapter

        // Boton de Guardar al hacer click
        saveButton.setOnClickListener {
            saveEntry()
        }
        // Boton de agregar al hacer click
        addImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(intent, REQUEST_IMAGE_PICK )
        }

    }
    private fun saveEntry() {
        val content = editTextContent.text.toString()
        val selectedEmoji = emojiSpinner.selectedItem.toString()
        val currentDate = getCurrentDate() // Para la fecha de hoy


        if (content.isNotEmpty()) {
            val dbHelper = DbHelper(this)
            val entry = JournalData(-1, content, currentDate, selectedEmoji, imagePath ?: "")
            val entrySavedSuccessfully = dbHelper.saveEntry(entry)

            if (entrySavedSuccessfully) {
                Toast.makeText(this, "Entrada guardada", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar la entrada", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "El contenido de la entrada no puede estar vacío", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            if (selectedImageUri != null) {
                imagePath = copyImageToAppDirectory(selectedImageUri)
                Glide.with(this)
                    .load(selectedImageUri)
                    .apply(RequestOptions().placeholder(R.drawable.fail_image))
                    .into(imageView)

            }
        }
    }

    private fun copyImageToAppDirectory(selectedImageUri: Uri): String {
        val inputStream = contentResolver.openInputStream(selectedImageUri)
        val appDirectory = File(getExternalFilesDir(null), "images")
        if (!appDirectory.exists()) {
            appDirectory.mkdirs()
        }
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val copiedImageFile = File(appDirectory, fileName)
        val outputStream = FileOutputStream(copiedImageFile)

        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len: Int
        while (inputStream?.read(buffer).also { len = it ?: 0 } != -1) {
            outputStream.write(buffer, 0, len)
        }

        outputStream.close()
        inputStream?.close()

        return copiedImageFile.absolutePath
    }
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
}