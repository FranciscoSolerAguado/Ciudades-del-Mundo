package com.example.ciudades2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ciudades2.databinding.ActivityBrowserBinding

class BrowserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBrowserBinding

    /**
     * Inicializa la actividad del navegador web.
     */
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.titulo_navegador)


        val url = intent.getStringExtra("url")
            ?: "https://es.wikipedia.org/"

        /**
         * Configuración del WebView para cargar la URL proporcionada.
         */
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient = WebViewClient()
        binding.webview.loadUrl(url)

        onButtonVolverClick()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_browser)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Configura el botón para volver a la actividad anterior.
     */
    fun onButtonVolverClick() {
        binding.btnVolver.setOnClickListener {
            finish()
        }
    }
}