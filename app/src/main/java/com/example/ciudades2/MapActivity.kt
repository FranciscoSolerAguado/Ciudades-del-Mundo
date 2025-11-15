package com.example.ciudades2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ciudades2.databinding.ActivityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapBinding
    private var mapa: GoogleMap? = null

    // Solicitud de permiso
    private val solicitudPermiso = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { otorgado ->
        if (otorgado) {
            habilitarMiUbicacion()
        }
    }

    /**
     * Inicializa la actividad del mapa.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.titulo_mapa)

        // Obtiene el fragmento del mapa y configura el callback
        val fragment = supportFragmentManager
            .findFragmentById(R.id.fragment_mapa) as SupportMapFragment
        fragment.getMapAsync(this)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_map)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Configura el mapa cuando está listo.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mapa = googleMap

        val paris = LatLng(48.8566, 2.3522)
        val ny = LatLng(40.7128, -74.0060)
        val tokyo = LatLng(35.6895, 139.6917)
        val rio = LatLng(-22.9068, -43.1729)

        mapa?.apply {
            /**
             * Añade marcadores para las ciudades predeterminadas.
             */
            addMarker(MarkerOptions().position(paris).title("París"))
            addMarker(MarkerOptions().position(ny).title("Nueva York"))
            addMarker(MarkerOptions().position(tokyo).title("Tokio"))
            addMarker(MarkerOptions().position(rio).title("Río de Janeiro"))

            val lat = intent.getDoubleExtra("latitud", Double.NaN)
            val lon = intent.getDoubleExtra("longitud", Double.NaN)
            val nombre = intent.getStringExtra("nombre")

            /**
             * Si se proporcionan coordenadas válidas, centra el mapa en esa ubicación y añade un marcador.
             * De lo contrario, centra el mapa en París con un zoom más amplio.
             */
            if (!lat.isNaN() && !lon.isNaN()) {
                val punto = LatLng(lat, lon)
                addMarker(MarkerOptions().position(punto).title(nombre ?: "Ciudad"))
                moveCamera(CameraUpdateFactory.newLatLngZoom(punto, 10f))
            } else {
                moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 3.5f))
            }
        }

        comprobarPermisoUbicacion()
    }

    /**
     * Comprueba y solicita el permiso de ubicación si es necesario.
     */
    private fun comprobarPermisoUbicacion() {
        // Verifica si el permiso de ubicación final ha sido concedido
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                habilitarMiUbicacion()
            }
            else -> solicitudPermiso.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    /**
     * Habilita la capa de "Mi Ubicación" en el mapa.
     */
    private fun habilitarMiUbicacion() {
        try {
            // Habilita la capa de "Mi Ubicación" si el permiso ha sido concedido
            mapa?.isMyLocationEnabled = true
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}
