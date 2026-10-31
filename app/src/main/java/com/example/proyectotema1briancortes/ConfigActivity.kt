package com.example.proyectotema1briancortes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/*
 * Primera pantalla de la app.
 * Aqui el usuario pone su nombre y el telefono al que quiere llamar
 * los datos se guardan con SharedPreferences para que no se pierdan
 * al cerrar la app, y se pasan a MainActivity con putExtra.
 */
class ConfigActivity : AppCompatActivity() {

    // Elementos de la vista que los enlazo en initEvent
    private lateinit var etNombre: EditText
    private lateinit var etTelefono: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        initEvent()
        showData()
    }

    // Si ya habia datos guardados de otra vez, los pongo en los campos
    private fun showData() {
        val prefs = getSharedPreferences("config", MODE_PRIVATE) // fichero "config", solo para mi app
        etNombre.setText(prefs.getString("nombre", ""))     // "" si todavia no hay nada guardado
        etTelefono.setText(prefs.getString("telefono", ""))
    }

    private fun initEvent() {
        etNombre = findViewById(R.id.etNombre)
        etTelefono = findViewById(R.id.etTelefono)
        btnGuardar = findViewById(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()

            // Sin telefono no dejo pasar, porque luego la llamada no funcionaria
            if (telefono.isEmpty()) {
                Toast.makeText(this, "Tienes que poner un teléfono", Toast.LENGTH_LONG).show()
            } else {
                // Guardo la configuracion en las preferencias compartidas
                // apply() lo guarda en segundo plano
                val prefs = getSharedPreferences("config", MODE_PRIVATE)
                prefs.edit()
                    .putString("nombre", nombre)
                    .putString("telefono", telefono)
                    .apply()

                // Este es el Intent explicito done le digo exactamente a que Activity ir
                // y le paso el nombre y el telefono con putExtra
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("nombre", nombre)
                    putExtra("telefono", telefono)
                }
                startActivity(intent)
            }
        }
    }
}
