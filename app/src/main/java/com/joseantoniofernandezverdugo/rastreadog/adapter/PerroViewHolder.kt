package com.joseantoniofernandezverdugo.rastreadog.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.joseantoniofernandezverdugo.rastreadog.R
import com.joseantoniofernandezverdugo.rastreadog.modelo.Perro

class PerroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val razaTextView: TextView = itemView.findViewById(R.id.nombre)
    val colorTextView: TextView = itemView.findViewById(R.id.tvColor)
    val purezaTextView: TextView = itemView.findViewById(R.id.tvPureza)
    val ciudadTextView: TextView = itemView.findViewById(R.id.tvCiudad)
    val precioTextView: TextView = itemView.findViewById(R.id.tvPrecio)
    val fotoImageView: ImageView = itemView.findViewById(R.id.ivPerro)

    fun render(perro: Perro) {
        razaTextView.text = perro.raza
        colorTextView.text = "Color: ${perro.color}"
        purezaTextView.text = "Raza: ${perro.razaPadre}"
        ciudadTextView.text = "Ciudad: ${perro.ciudad}"
        precioTextView.text = "${perro.precio}€"

        // Obtener la referencia de la imagen en Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${perro.uid}")

        // Obtener la URL de descarga y cargar la imagen con Glide
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(itemView.context).load(uri).into(fotoImageView)
        }.addOnFailureListener {

        }
    }
}
