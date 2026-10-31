package com.example.proyectotema1briancortes

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

/*
 * Pantalla principal tiene la imagen, el texto de bienvenida y los botones:
 *  - Llamada  : abre CallActivity
 *  - Web      : abre UrlActivity
 *  - Alarma   : pone una alarma para dentro de 2 min
 *  - Compartir: comparte un texto
 *  - Engranaje: vuelve a la configuracion
 */
class MainActivity : AppCompatActivity() {

    private lateinit var btnLlamada: ImageButton
    private lateinit var btnUrl: ImageButton
    private lateinit var btnAlarma: ImageButton
    private lateinit var btnCompartir: ImageButton
    private lateinit var btnConfig: ImageButton

    // Datos que me llegan desde ConfigActivity
    private var nombre: String? = null
    private var telefono: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()
        showData()
    }

    // Recojo los extras que me manda ConfigActivity
    private fun showData() {
        nombre = intent.getStringExtra("nombre")
        telefono = intent.getStringExtra("telefono")
    }

    private fun initEvent() {
        btnLlamada = findViewById(R.id.btnLlamada)
        btnUrl = findViewById(R.id.btnUrl)
        btnAlarma = findViewById(R.id.btnAlarma)
        btnCompartir = findViewById(R.id.btnCompartir)
        btnConfig = findViewById(R.id.btnConfig)

        // intent explicito a la pantalla de llamada
        // le paso el telefono para que sepa a quien llamar
        btnLlamada.setOnClickListener {
            val intent = Intent(this, CallActivity::class.java).apply {
                putExtra("telefono", telefono)
                putExtra("nombre", nombre)
            }
            startActivity(intent)
        }

        // intent explicito a la ventana donde se escribe la URL
        btnUrl.setOnClickListener {
            val intent = Intent(this, UrlActivity::class.java).apply {
                putExtra("nombre", nombre)
            }
            startActivity(intent)
        }

        // intent implicito, Android abre la app del reloj
        btnAlarma.setOnClickListener {
            // Cojo la hora de ahora y le sumo 2 minutos
            // (uso java.util.Calendar porque el de android.icu solo va desde API 24)
            val calendario = Calendar.getInstance()
            calendario.add(Calendar.MINUTE, 2)

            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_HOUR, calendario.get(Calendar.HOUR_OF_DAY))
                putExtra(AlarmClock.EXTRA_MINUTES, calendario.get(Calendar.MINUTE))
                putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma de Brian - Práctica PMDM")
            }
            startActivity(intent)
            Toast.makeText(this, "Alarma puesta para dentro de 2 minutos", Toast.LENGTH_LONG).show()
        }

        // intent implicito con ACTION_SEND
        // createChooser saca la lista de apps para elegir
        btnCompartir.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Mira la app que he hecho en PMDM - Brian Cortés")
            }
            startActivity(Intent.createChooser(intent, "Compartir con..."))
        }

        // Volver a la configuracion
        // El FLAG_ACTIVITY_CLEAR_TOP como ConfigActivity ya esta en la pila,
        // cierra las de encima y vuelve a ella en vez de crear otra nueva
        btnConfig.setOnClickListener {
            val intent = Intent(this, ConfigActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(intent)
        }
    }
}
