package com.example.proyectotema1briancortes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ConfigActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)


        val prefs = getSharedPreferences("config", MODE_PRIVATE)


        etNombre.setText(prefs.getString("nombre", ""))
        etTelefono.setText(prefs.getString("telefono", ""))

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()

            if (telefono.isEmpty()) {
                etTelefono.error = "Introduce un teléfono"
                return@setOnClickListener
            }


            prefs.edit()
                .putString("nombre", nombre)
                .putString("telefono", telefono)
                .apply()


            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("telefono", telefono)
            startActivity(intent)
        }
    }
}