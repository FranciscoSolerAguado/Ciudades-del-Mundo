package com.example.ciudades2

/**
 * Interfaz para manejar los clics en las ciudades y el botón de volver.
 */
interface OnClickListenerInterface {
    fun onCityClick(ciudad: Ciudad)
    fun onCityMapClick(ciudad: Ciudad)
    fun onCityShareClick(ciudad: Ciudad)
    fun onButtonVolverClick()
    fun prepararDatos()
}