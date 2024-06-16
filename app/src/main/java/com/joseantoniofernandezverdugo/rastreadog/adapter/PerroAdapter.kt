package com.joseantoniofernandezverdugo.rastreadog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joseantoniofernandezverdugo.rastreadog.R
import com.joseantoniofernandezverdugo.rastreadog.modelo.Perro

class PerroAdapter(
    private val perroList: List<Perro>,
    private val onItemClick: (Perro) -> Unit
) : RecyclerView.Adapter<PerroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_perro, parent, false)
        return PerroViewHolder(view)
    }

    override fun getItemCount(): Int = perroList.size

    override fun onBindViewHolder(holder: PerroViewHolder, position: Int) {
            val perro = perroList[position]
            holder.render(perro)
            holder.itemView.setOnClickListener {
                onItemClick(perro)
            }
        }

}
