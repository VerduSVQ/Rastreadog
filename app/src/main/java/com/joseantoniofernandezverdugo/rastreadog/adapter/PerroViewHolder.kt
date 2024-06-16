package com.joseantoniofernandezverdugo.rastreadog.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joseantoniofernandezverdugo.rastreadog.R
import com.joseantoniofernandezverdugo.rastreadog.modelo.Perro

class PerroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val razaTextView: TextView = itemView.findViewById(R.id.tvRaza)
    val colorTextView: TextView = itemView.findViewById(R.id.tvColor)
    val purezaTextView: TextView = itemView.findViewById(R.id.tvPureza)
    val ciudadTextView: TextView = itemView.findViewById(R.id.tvCiudad)
    val precioTextView: TextView = itemView.findViewById(R.id.tvPrecio)

    fun render(perro: Perro) {
        razaTextView.text = perro.raza
        colorTextView.text = "Color: ${perro.color}"
        purezaTextView.text = "Raza: ${perro.razaPadre}"
        ciudadTextView.text = "Ciudad"
        precioTextView.text = "${perro.precio}€"

    }
}
