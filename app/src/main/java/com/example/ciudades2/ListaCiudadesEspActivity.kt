package com.example.ciudades2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ciudades2.databinding.ActivityListaCiudadesBinding

class ListaCiudadesEspActivity : AppCompatActivity(), OnClickListenerInterface {
    private lateinit var binding: ActivityListaCiudadesBinding
    private lateinit var listaCiudadesEsp: List<Ciudad>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaCiudadesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.titulo_lista)

        /**
         * Prepara los datos de las ciudades y configura el RecyclerView.
         */
        prepararDatos()
        val adaptador = CiudadEspAdapter(listaCiudadesEsp, this)
        binding.recyclerCiudades.layoutManager = LinearLayoutManager(this)
        binding.recyclerCiudades.adapter = adaptador

        onButtonVolverClick()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_lista_ciudades)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    /**
     * Maneja el clic en una ciudad para abrir la actividad del navegador web con más información.
     */
    override fun onCityClick(ciudad: Ciudad) {
        val intent = Intent(this, BrowserActivity::class.java)
        intent.putExtra("url", ciudad.urlInformacion)
        startActivity(intent)
    }

    /**
     * Maneja el clic en el botón de mapa para abrir la actividad del mapa con la ubicación de la ciudad.
     */
    override fun onCityMapClick(ciudad: Ciudad) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("latitud", ciudad.latitud)
        intent.putExtra("longitud", ciudad.longitud)
        intent.putExtra("nombre", ciudad.nombre)
        startActivity(intent)
        Toast.makeText(this, "No disponible de momento", Toast.LENGTH_SHORT).show()
    }

    /**
     * Maneja el clic en el botón de compartir para compartir información sobre la ciudad.
     */
    override fun onCityShareClick(ciudad: Ciudad) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Echa un vistazo a ${ciudad.nombre}: ${ciudad.urlInformacion}")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    /**
     * Configura el botón para volver a la actividad anterior.
     */
    override fun onButtonVolverClick() {
        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    /**
     * Prepara la lista de ciudades con sus datos correspondientes.
     */
    override fun prepararDatos() {
        listaCiudadesEsp = listOf(
            Ciudad(
                "Madrid",
                "Comunidad de Madrid",
                "La capital de España, con el Palacio Real y el Museo del Prado.",
                R.drawable.madrid,
                "https://es.wikipedia.org/wiki/Madrid",
                40.4168,
                -3.7038
            ),

            Ciudad(
                "Barcelona",
                "Cataluña",
                "Ciudad cosmopolita con la Sagrada Familia y obras de Gaudí.",
                R.drawable.barcelona,
                "https://es.wikipedia.org/wiki/Barcelona",
                41.3874,
                2.1686
            ),

            Ciudad(
                "Valencia",
                "Comunidad Valenciana",
                "Famosa por la Ciudad de las Artes y las Ciencias y la paella.",
                R.drawable.valencia,
                "https://es.wikipedia.org/wiki/Valencia",
                39.4699,
                -0.3763
            ),

            Ciudad(
                "Sevilla",
                "Andalucía",
                "Cuna del flamenco, con la Giralda y la Plaza de España.",
                R.drawable.sevilla,
                "https://es.wikipedia.org/wiki/Sevilla",
                37.3891,
                -5.9845
            ),

            Ciudad(
                "Zaragoza",
                "Aragón",
                "Ciudad del Pilar y el Ebro, con historia romana y mudéjar.",
                R.drawable.zaragoza,
                "https://es.wikipedia.org/wiki/Zaragoza",
                41.6488,
                -0.8891
            ),

            Ciudad(
                "Málaga",
                "Andalucía",
                "Ciudad costera con el Museo Picasso y playas del Mediterráneo.",
                R.drawable.malaga,
                "https://es.wikipedia.org/wiki/M%C3%A1laga",
                36.7213,
                -4.4214
            ),

            Ciudad(
                "Murcia",
                "Región de Murcia",
                "Capital huertana con una impresionante catedral barroca.",
                R.drawable.murcia,
                "https://es.wikipedia.org/wiki/Murcia",
                37.9834,
                -1.1299
            ),

            Ciudad(
                "Palma de Mallorca",
                "Islas Baleares",
                "Isla mediterránea con playas y la Catedral de Palma.",
                R.drawable.palma,
                "https://es.wikipedia.org/wiki/Palma_de_Mallorca",
                39.5696,
                2.6502
            ),

            Ciudad(
                "Las Palmas de Gran Canaria",
                "Islas Canarias",
                "Capital insular con playas, cultura y clima cálido todo el año.",
                R.drawable.laspalmas,
                "https://es.wikipedia.org/wiki/Las_Palmas_de_Gran_Canaria",
                28.1235,
                -15.4363
            ),

            Ciudad(
                "Bilbao",
                "País Vasco",
                "Ciudad industrial renovada con el Museo Guggenheim.",
                R.drawable.bilbao,
                "https://es.wikipedia.org/wiki/Bilbao",
                43.2630,
                -2.9350
            ),

            Ciudad(
                "Alicante",
                "Comunidad Valenciana",
                "Ciudad portuaria con el Castillo de Santa Bárbara.",
                R.drawable.alicante,
                "https://es.wikipedia.org/wiki/Alicante",
                38.3452,
                -0.4810
            ),

            Ciudad(
                "Córdoba",
                "Andalucía",
                "Famosa por su Mezquita-Catedral y su historia musulmana.",
                R.drawable.cordoba,
                "https://es.wikipedia.org/wiki/C%C3%B3rdoba_(Espa%C3%B1a)",
                37.8882,
                -4.7794
            ),

            Ciudad(
                "Valladolid",
                "Castilla y León",
                "Ciudad histórica y antigua capital de España.",
                R.drawable.valladolid,
                "https://es.wikipedia.org/wiki/Valladolid",
                41.6523,
                -4.7245
            ),

            Ciudad(
                "Granada",
                "Andalucía",
                "Con la majestuosa Alhambra y vistas a Sierra Nevada.",
                R.drawable.granada,
                "https://es.wikipedia.org/wiki/Granada",
                37.1773,
                -3.5986
            ),

            Ciudad(
                "Toledo",
                "Castilla-La Mancha",
                "Ciudad de las tres culturas y patrimonio de la humanidad.",
                R.drawable.toledo,
                "https://es.wikipedia.org/wiki/Toledo",
                39.8628,
                -4.0273
            ),

            Ciudad(
                "Santander",
                "Cantabria",
                "Capital cántabra junto al mar, con la playa del Sardinero.",
                R.drawable.santander,
                "https://es.wikipedia.org/wiki/Santander_(Espa%C3%B1a)",
                43.4623,
                -3.8099
            ),

            Ciudad(
                "San Sebastián",
                "País Vasco",
                "Ciudad elegante con la Playa de la Concha y gastronomía reconocida.",
                R.drawable.sansebastian,
                "https://es.wikipedia.org/wiki/San_Sebasti%C3%A1n",
                43.3183,
                -1.9812
            ),

            Ciudad(
                "A Coruña",
                "Galicia",
                "Ciudad atlántica con la Torre de Hércules y un bonito paseo marítimo.",
                R.drawable.acoruna,
                "https://es.wikipedia.org/wiki/La_Coru%C3%B1a",
                43.3623,
                -8.4115
            ),

            Ciudad(
                "Salamanca",
                "Castilla y León",
                "Ciudad universitaria con una de las universidades más antiguas de Europa.",
                R.drawable.salamanca,
                "https://es.wikipedia.org/wiki/Salamanca",
                40.9701,
                -5.6635
            )
        )
    }

}