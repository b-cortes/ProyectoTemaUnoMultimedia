package com.example.proyectotema1briancortes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/*
 * Esta es para la pantalla de llamada, se abre desde MainActivity con un intent explicito.
 * Al pulsar el boton verde se hace la llamada con un intent implicito llamado ACTION_CALL.
 *  - API menor a 23: solo hace falta el permiso en el manifest y llama directamente.
 *  - API mayor o igual a 23: hay que pedirle el permiso al usuario solo la primera vez.
 */
class CallActivity : AppCompatActivity() {

    private lateinit var tvLlamada: TextView
    private lateinit var btnCall: ImageButton
    private lateinit var btnVolver: Button

    private var telefono: String = ""

    // Es una funcion de orden superior que lanza la peticion del permiso y
    // en la lambda recibo si el usuario ha dado a permitir o no
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            call()
        } else {
            Toast.makeText(this, "Necesitas habilitar los permisos", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)
        initEvent()
        showData()
    }

    // Recojo el telefono y el nombre que me pasa MainActivity
    private fun showData() {
        telefono = intent.getStringExtra("telefono") ?: "" // si viene null pongo vacio
        val nombre = intent.getStringExtra("nombre")
        tvLlamada.text = "$nombre, vas a llamar al\n$telefono"
    }

    private fun initEvent() {
        tvLlamada = findViewById(R.id.tvLlamada)
        btnCall = findViewById(R.id.btnCall)
        btnVolver = findViewById(R.id.btnVolver)

        btnCall.setOnClickListener {
            requestPermissions()
        }

        // finish() cierra esta pantalla y vuelvo a la principal
        btnVolver.setOnClickListener {
            finish()
        }
    }

    // Aqui esta la diferencia entre APIs
    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // API 23 (Marshmallow) o mayor
            if (PermissionPhone()) { // el usuario ya dio el permiso antes
                call()
            } else { // todavia no, se lo pido (sale el dialogo de Permitir)
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        } else {
            call() // API < 23: con el permiso del manifest es suficiente
        }
    }

    // true si el permiso de llamada ya esta concedido
    private fun PermissionPhone(): Boolean =
        ContextCompat.checkSelfPermission(this,
            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED

    // Este es el Intente implicito Android abre la app de llamadas con el numero
    private fun call() {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$telefono")
        }
        startActivity(intent)
    }
}
