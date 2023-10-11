package com.example.project_journal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class JournalEntryAdapter : RecyclerView.Adapter<JournalEntryAdapter.EntryViewHolder>() {

    private var entries: List<JournalData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_journal_entry_adapter, parent, false)
        return EntryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]

        // Aquí vinculas los datos de la entrada a las vistas en el ViewHolder
        holder.bind(entry)
        val moodEmoji = entry.moodEmoji
        val emojiArray = holder.itemView.resources.getStringArray(R.array.emoji_array)


        // Obtener ImageView
        val moodImageView = holder.moodImageView

        if (emojiArray.contains(moodEmoji)) {
            // Si el emoji está en el array, configura la imagen del ImageView
            when (moodEmoji) {

                "😀" -> moodImageView.setImageResource(R.drawable.ic_emoji_happy)
                "☹️" -> moodImageView.setImageResource(R.drawable.ic_emoji_sad)
                "😠" -> moodImageView.setImageResource(R.drawable.ic_emoji_angry)
                // Agrega más despues
                else -> moodImageView.setImageResource(R.drawable.fail_image) // Emoji por defecto o error
            }
        } else {
            // Si el emoji no está en el array, muestra una imagen predeterminada
            moodImageView.setImageResource(R.drawable.fail_image)
        }
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    fun submitList(entryList: List<JournalData>) {
        entries = entryList
        notifyDataSetChanged()
    }

    inner class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define las vistas en el ViewHolder y vincula los datos
        private val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val moodImageView: ImageView = itemView.findViewById(R.id.moodImageView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(entry: JournalData) {

            Log.d("ImageDebug", "Ruta de la imagen Adapter: ${entry.photoPath}")

            contentTextView.text = entry.textContent  //Contenido
            dateTextView.text = entry.entryDate       // Fecha

            Glide.with(itemView)
                .load(entry.photoPath) // La ruta de la imagen almacenada en la base de datos
                .apply(RequestOptions().placeholder(R.drawable.fail_image)) //  imagen de carga
                .into(imageView)
        }
    }
}