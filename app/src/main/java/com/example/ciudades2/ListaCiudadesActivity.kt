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

class ListaCiudadesActivity : AppCompatActivity(), OnClickListenerInterface {
    private lateinit var binding: ActivityListaCiudadesBinding
    private lateinit var listaCiudades: List<Ciudad>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaCiudadesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.titulo_lista)

        /**
         * Prepara los datos de las ciudades y configura el RecyclerView.
         */
        prepararDatos()

        val adaptador = CiudadAdapter(listaCiudades, this)
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
        listaCiudades = listOf(
            Ciudad(
                "París", "Francia", "La ciudad del amor y la Torre Eiffel.", R.drawable.paris,
                "https://es.wikipedia.org/wiki/Par%C3%ADs", 48.8566, 2.3522
            ),

            Ciudad(
                "Nueva York",
                "Estados Unidos",
                "La ciudad que nunca duerme, Times Square y Central Park.",
                R.drawable.nuevayork,
                "https://es.wikipedia.org/wiki/Nueva_York",
                40.7128,
                -74.0060
            ),

            Ciudad(
                "Tokio", "Japón", "Mezcla de tradición y tecnología.", R.drawable.tokio,
                "https://es.wikipedia.org/wiki/Tokio", 35.6895, 139.6917
            ),

            Ciudad(
                "Río de Janeiro",
                "Brasil",
                "Famoso por el Cristo Redentor y sus playas.",
                R.drawable.rio,
                "https://es.wikipedia.org/wiki/R%C3%ADo_de_Janeiro",
                -22.9068,
                -43.1729
            ),

            Ciudad(
                "Londres",
                "Reino Unido",
                "Capital británica con el Big Ben y el London Eye.",
                R.drawable.londres,
                "https://es.wikipedia.org/wiki/Londres",
                51.5074,
                -0.1278
            ),

            Ciudad(
                "Roma",
                "Italia",
                "La ciudad eterna, hogar del Coliseo y el Vaticano.",
                R.drawable.roma,
                "https://es.wikipedia.org/wiki/Roma",
                41.9028,
                12.4964
            ),

            Ciudad(
                "Berlín",
                "Alemania",
                "Ciudad moderna y llena de historia, con la Puerta de Brandeburgo.",
                R.drawable.berlin,
                "https://es.wikipedia.org/wiki/Berl%C3%ADn",
                52.5200,
                13.4050
            ),

            Ciudad(
                "Sídney",
                "Australia",
                "Famosa por su Ópera y el puerto natural.",
                R.drawable.sydney,
                "https://es.wikipedia.org/wiki/S%C3%ADdney",
                -33.8688,
                151.2093
            ),

            Ciudad(
                "El Cairo",
                "Egipto",
                "Capital junto al Nilo, cerca de las pirámides de Giza.",
                R.drawable.elcairo,
                "https://es.wikipedia.org/wiki/El_Cairo",
                30.0444,
                31.2357
            ),

            Ciudad(
                "Moscú",
                "Rusia",
                "Ciudad imperial con la Plaza Roja y el Kremlin.",
                R.drawable.moscu,
                "https://es.wikipedia.org/wiki/Mosc%C3%BA",
                55.7558,
                37.6173
            ),

            Ciudad(
                "Toronto",
                "Canadá",
                "Ciudad multicultural con la torre CN y el lago Ontario.",
                R.drawable.toronto,
                "https://es.wikipedia.org/wiki/Toronto",
                43.6510,
                -79.3470
            ),

            Ciudad(
                "Ciudad de México",
                "México",
                "Una de las urbes más grandes del mundo, rica en historia y cultura.",
                R.drawable.ciudadmexico,
                "https://es.wikipedia.org/wiki/Ciudad_de_M%C3%A9xico",
                19.4326,
                -99.1332
            ),

            Ciudad(
                "Buenos Aires",
                "Argentina",
                "Capital del tango y la arquitectura europea.",
                R.drawable.buenosaires,
                "https://es.wikipedia.org/wiki/Buenos_Aires",
                -34.6037,
                -58.3816
            ),

            Ciudad(
                "Pekín",
                "China",
                "Capital milenaria con la Ciudad Prohibida y la Gran Muralla cercana.",
                R.drawable.pekin,
                "https://es.wikipedia.org/wiki/Pek%C3%ADn",
                39.9042,
                116.4074
            ),

            Ciudad(
                "Dubái",
                "Emiratos Árabes Unidos",
                "Famosa por el Burj Khalifa y sus lujosos rascacielos.",
                R.drawable.dubai,
                "https://es.wikipedia.org/wiki/Dub%C3%A1i",
                25.276987,
                55.296249
            ),

            Ciudad(
                "Estambul",
                "Turquía",
                "Puente entre Europa y Asia, con la Mezquita Azul y Santa Sofía.",
                R.drawable.estambul,
                "https://es.wikipedia.org/wiki/Estambul",
                41.0082,
                28.9784
            ),

            Ciudad(
                "Los Ángeles",
                "Estados Unidos",
                "Capital del cine y hogar de Hollywood.",
                R.drawable.losangeles,
                "https://es.wikipedia.org/wiki/Los_%C3%81ngeles",
                34.0522,
                -118.2437
            ),

            Ciudad(
                "Bangkok",
                "Tailandia",
                "Ciudad vibrante con templos dorados y una animada vida callejera.",
                R.drawable.bangkok,
                "https://es.wikipedia.org/wiki/Bangkok",
                13.7563,
                100.5018
            ),

            Ciudad(
                "Seúl",
                "Corea del Sur",
                "Ciudad moderna con palacios antiguos y rascacielos futuristas.",
                R.drawable.seul,
                "https://es.wikipedia.org/wiki/Se%C3%BAl",
                37.5665,
                126.9780
            )
        )
    }
}
