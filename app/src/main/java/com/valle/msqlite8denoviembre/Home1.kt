package com.valle.msqlite8denoviembre

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class Home1 : AppCompatActivity() {

    lateinit var btnIvaPro: CardView
    lateinit var btnIvaPro2: CardView
    lateinit var btnBuscoCliente: CardView
    lateinit var btnExit: CardView
    lateinit var btnTotal: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home1)

        cargarR()
        estadoBoton()
    }

    // Cargar referencias a botones
    fun cargarR() {
        btnIvaPro = findViewById(R.id.btnIvaProducto)
        btnIvaPro2 = findViewById(R.id.btnProducto)
        btnBuscoCliente = findViewById(R.id.btnBuscarCliente)
        btnExit = findViewById(R.id.btnSalir)
        btnTotal = findViewById(R.id.btnRegistroTotal)
    }

    // Configurar estado de los botones
    fun estadoBoton() {
        btnIvaPro.setOnClickListener {
            val i = Intent(this, Iva::class.java)
            startActivity(i)
        }
        btnIvaPro2.setOnClickListener {
            val i = Intent(this, Productos1::class.java)
            startActivity(i)
        }
        btnBuscoCliente.setOnClickListener {
            Log.d("MiApp", "Botón Exit presionado, lanzando actividad Ventas") // Agregamos un log para verificar
            try {
                val i = Intent(this, RegisClien::class.java)
                startActivity(i)
            } catch (e: Exception) {
                Log.e("MiApp", "Error al iniciar la actividad Ventas: ${e.message}")
            }
        }

        // Manejar el evento del botón `Exit` con logs para verificar si entra correctamente
        btnExit.setOnClickListener {
            Log.d("MiApp", "Botón Exit presionado, lanzando actividad Ventas") // Agregamos un log para verificar
            try {
                val i = Intent(this, Ventas::class.java)
                startActivity(i)
            } catch (e: Exception) {
                Log.e("MiApp", "Error al iniciar la actividad Ventas: ${e.message}")
            }
        }

        // Configurar el OnClickListener para el botón `btnTotal`
        btnTotal.setOnClickListener {
            val i = Intent(this, RegistroTotal::class.java)
            startActivity(i)
        }
    }
}
