package com.example.ciudades2

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
 * El ViewHolder crea una referencia a los elementos visuales de cada item del RecyclerView
 */
class CiudadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Referencias a los elementos visuales del item del RecyclerView.
     */
    val imagenCiudad: ImageView = itemView.findViewById(R.id.imagen_elemento)
    val tvNombre: TextView = itemView.findViewById(R.id.tv_nombre_elemento)
    val tvPais: TextView = itemView.findViewById(R.id.tv_pais_elemento)
    val tvDescripcion: TextView = itemView.findViewById(R.id.tv_descripcion_elemento)
    val btnMapa: Button = itemView.findViewById(R.id.btn_ver_mapa_elemento)
    val btnShare: ImageButton = itemView.findViewById(R.id.btn_share)
}