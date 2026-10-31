package com.example.proyectotema1briancortes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/*
 * Ventana para escribir la web a la que quieres ir,
 * se abre desde MainActivity con un intent explicito y,
 * al pulsar "Ir a la web", abre el navegador con INTENT_VIEW.
 */
class UrlActivity : AppCompatActivity() {

    private lateinit var tvUrl: TextView
    private lateinit var etUrl: EditText
    private lateinit var btnIr: Button
    private lateinit var btnVolverUrl: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url)
        initEvent()
        showData()
    }

    // Texto de debajo del titulo
    private fun showData() {
        tvUrl.text = "¿A dónde quieres ir?"
    }

    private fun initEvent() {
        tvUrl = findViewById(R.id.tvUrl)
        etUrl = findViewById(R.id.etUrl)
        btnIr = findViewById(R.id.btnIr)
        btnVolverUrl = findViewById(R.id.btnVolverUrl)

        btnIr.setOnClickListener {
            var url = etUrl.text.toString().trim()

            if (url.isEmpty()) {
                Toast.makeText(this, "Escribe una dirección web", Toast.LENGTH_LONG).show()
            } else {
                // Si no pone http:// o https:// se lo añado yo,
                // asi se puede escribir solo "www.marca.com"
                if (!url.startsWith("http")) {
                    url = "https://$url"
                }
                // Intent implicito donde no digo que app abrir, Android busca el navegador
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                startActivity(intent)
            }
        }

        // Vuelvo a la pantalla principal
        btnVolverUrl.setOnClickListener {
            finish()
        }
    }
}
