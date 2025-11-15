package com.example.ciudades2

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CiudadEspAdapter(
    private val listaCiudades: List<Ciudad>,
    private val listener: OnClickListenerInterface
) :
    RecyclerView.Adapter<CiudadViewHolder>() {

    /**
     * crea un ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CiudadViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ciudad_esp, parent, false)
        return CiudadViewHolder(vista)
    }

    /**
     * vincula los datos de la ciudad al ViewHolder.
     */
    override fun onBindViewHolder(holder: CiudadViewHolder, position: Int) {
        val ciudad = listaCiudades[position]
        holder.tvNombre.text = ciudad.nombre
        holder.tvPais.text = ciudad.pais
        holder.tvDescripcion.text = ciudad.descripcionCorta
        holder.imagenCiudad.setImageResource(ciudad.imagenRecurso)

        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Ciudad seleccionada: ${ciudad.nombre}",
                Toast.LENGTH_SHORT
            )
            listener.onCityClick(ciudad)
        }

        /**
         * Configura el botón para ver más información sobre la ciudad.
         */
        holder.btnMapa.setOnClickListener {
            listener.onCityMapClick(ciudad)
        }

        /**
         * Configura el botón para compartir la información de la ciudad.
         */
        holder.btnShare.setOnClickListener {
            listener.onCityShareClick(ciudad)
        }
    }

    /**
     * devuelve el tamaño de la lista de ciudades.
     */
    override fun getItemCount(): Int = listaCiudades.size
}