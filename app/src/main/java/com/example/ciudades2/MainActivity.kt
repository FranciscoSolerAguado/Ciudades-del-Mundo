package com.example.ciudades2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ciudades2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.titulo_principal)

        /**
         * Método que inicia los métodos que manejan la
         * navegación a otras actividades al hacer clic en los botones correspondientes.
         */
        InitComponents()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun OnInfoClick() {

        /**
         * Muestra un diálogo informativo al hacer clic en el ícono de información.
         */
        binding.infoIcono.setOnClickListener {
            mostrarDialogoInformativo()
        }
    }

    private fun mostrarDialogoInformativo() {
        // Creamos un constructor de diálogos (Builder)
        val builder = AlertDialog.Builder(this)

        // Título y el mensaje del diálogo
        builder.setTitle(R.string.como_funciona_la_app)
        builder.setMessage(R.string.texto_como_funciona_la_app)

        // Añadir un botón para cerrar el diálogo
        //    'setPositiveButton' es para el botón de acción principal (ej. "Aceptar")
        builder.setPositiveButton(R.string.entendido) { dialog, _ ->
            dialog.dismiss() // Esto cierra el diálogo cuando el usuario presiona el botón
        }

        // Crear y mostrar el AlertDialog
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Método que inicia los métodos que manejan la
     * navegación a otras actividades al hacer clic en los botones correspondientes.
     */
    private fun InitComponents(){
        OnListaCiudadesClick()
        OnListaSpainClick()
        OnAgitarMovilClick()
        OnInfoClick()
    }

    /**
     * Navega a la actividad ListaCiudadesActivity al hacer clic en el botón correspondiente.
     */
    private fun OnListaCiudadesClick() {
        binding.btnIrLista.setOnClickListener {
            startActivity(Intent(this, ListaCiudadesActivity::class.java))
        }
    }

    /**
     * Navega a la actividad ListaCiudadesEspActivity al hacer clic en el botón correspondiente.
     */
    private fun OnListaSpainClick() {
        binding.btnIrListaEsp.setOnClickListener {
            startActivity(Intent(this, ListaCiudadesEspActivity::class.java))
        }
    }

    /**
     * Navega a la actividad SensorActivity al hacer clic en el botón correspondiente.
     */
    private fun OnAgitarMovilClick() {
        binding.btnAgitar.setOnClickListener {
            startActivity(Intent(this, SensorActivity::class.java))
        }
    }
}