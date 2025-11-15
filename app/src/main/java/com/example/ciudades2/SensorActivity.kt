package com.example.ciudades2

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ciudades2.databinding.ActivitySensorBinding
import kotlin.math.sqrt

class SensorActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivitySensorBinding
    private lateinit var sensorManager: SensorManager
    private var lastShakeTime: Long = 0
    private lateinit var listaCiudades: List<Ciudad>
    private var ciudadMostrada: Ciudad? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySensorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepararDatos()

        InitComponents()

        // Obtiene una instancia del servicio de sensores del sistema.
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        // Obtiene el sensor de acelerómetro por defecto del dispositivo.
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        // Comprueba si el sensor de acelerómetro está disponible.
        if (accelerometer != null) {
            // Si está disponible, registra un listener para empezar a recibir eventos del acelerómetro.
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            // Si no está disponible, muestra un mensaje de Toast al usuario.
            Toast.makeText(this, "No se encuentra el sensor del acelerómetro", Toast.LENGTH_SHORT)
                .show()
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_sensor)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Método heredado de la clase SensorEventListener
     * Método que se llama cuando se detecta un cambio en el sensor de acelerómetro.
     */
    override fun onSensorChanged(event: SensorEvent?) {
        // Comprueba si el evento proviene del sensor de acelerómetro.
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            // Obtiene los valores del sensor de acelerómetro.
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            // Calcula la magnitud del vector de aceleración.
            val acceleration = sqrt((x * x + y * y + z * z).toDouble())

            // Comprueba si la aceleración supera un umbral y si ha pasado un tiempo suficiente desde el último movimiento.
            val now = System.currentTimeMillis()
            if (acceleration > 20 && now - lastShakeTime > 1000) {
                lastShakeTime = now
                // Si se cumple la condición, muestra una ciudad en la tarjeta.
                mostrarCiudadEnCard()
            }
        }
    }

    /**
     * Muestra una ciudad en la tarjeta
     */
    private fun mostrarCiudadEnCard() {
        if (listaCiudades.isNotEmpty()) {
            ciudadMostrada = listaCiudades.random()

            binding.textInitial.visibility = View.GONE
            binding.itemCiudadSensor.root.visibility = View.VISIBLE

            ciudadMostrada?.let { ciudad ->
                binding.itemCiudadSensor.imagenElemento.setImageResource(ciudad.imagenRecurso)
                binding.itemCiudadSensor.tvNombreElemento.text = ciudad.nombre
                binding.itemCiudadSensor.tvPaisElemento.text = ciudad.pais
                binding.itemCiudadSensor.tvDescripcionElemento.text = ciudad.descripcionCorta
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}


    /**
     * Configura el botón para volver a la actividad anterior.
     */
    fun onButtonVolverClick() {
        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun InitComponents(){
        onButtonVolverClick()
        onBtnShareClick()

        binding.itemCiudadSensor.root.setOnClickListener {
            ciudadMostrada?.let {
                onCityClick(it)
            }
        }

        binding.itemCiudadSensor.btnVerMapaElemento.setOnClickListener {
            ciudadMostrada?.let {
                onCityMapClick(it)
            }
        }
    }

    /**
     * Maneja el clic en el botón de compartir para compartir la información de la ciudad mostrada.
     */
    fun onBtnShareClick(){
        binding.itemCiudadSensor.btnShare.setOnClickListener {
            ciudadMostrada?.let {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Echa un vistazo a ${it.nombre}: ${it.urlInformacion}")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }

    /**
     * Maneja el clic en una ciudad para abrir la actividad del navegador web con más información.
     */
    fun onCityClick(ciudad: Ciudad) {
        val intent = Intent(this, BrowserActivity::class.java)
        intent.putExtra("url", ciudad.urlInformacion)
        startActivity(intent)
    }

    /**
     * Maneja el clic en el botón de mapa para abrir la actividad del mapa con la ubicación de la ciudad.
     */
    fun onCityMapClick(ciudad: Ciudad) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("latitud", ciudad.latitud)
        intent.putExtra("longitud", ciudad.longitud)
        intent.putExtra("nombre", ciudad.nombre)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    /**
     * Prepara los datos de las ciudades
     */
    private fun prepararDatos() {
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

            ),
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
