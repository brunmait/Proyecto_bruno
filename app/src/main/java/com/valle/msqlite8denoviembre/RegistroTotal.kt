package com.valle.msqlite8denoviembre

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegistroTotal : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var idConsultaCliente: EditText
    private lateinit var idConsultaVenta: EditText
    private lateinit var textViewClienteNombre: TextView
    private lateinit var textViewClienteTelefono: TextView
    private lateinit var textViewVentaDescripcion: TextView
    private lateinit var textViewVentaPrecio: TextView
    private lateinit var textViewVentaFecha: TextView // Nuevo campo para la fecha
    private lateinit var buttonBuscar: Button
    private lateinit var btnComprar: Button // Agregar esta línea

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_total)

        // Inicializa componentes
        databaseHelper = DatabaseHelper(this)
        idConsultaCliente = findViewById(R.id.idConsultaCliente)
        idConsultaVenta = findViewById(R.id.idConsultaVenta)
        textViewClienteNombre = findViewById(R.id.textViewClienteNombre)
        textViewClienteTelefono = findViewById(R.id.textViewClienteTelefono)
        textViewVentaDescripcion = findViewById(R.id.textViewVentaDescripcion)
        textViewVentaPrecio = findViewById(R.id.textViewVentaPrecio)
        textViewVentaFecha = findViewById(R.id.textViewVentaFecha) // Inicializa el TextView de la fecha
        buttonBuscar = findViewById(R.id.button2)
        btnComprar = findViewById(R.id.btnComprar) // Inicializa el botón de comprar

        // Configura el listener del botón de buscar
        buttonBuscar.setOnClickListener {
            val userId = idConsultaCliente.text.toString().trim()
            val ventaId = idConsultaVenta.text.toString().trim()

            // Buscar y mostrar información del usuario
            val user = databaseHelper.getUserByCarnet(userId)
            textViewClienteNombre.text = user?.let { "Nombre: ${it.getFirstName()}" } ?: "Usuario no encontrado"
            textViewClienteTelefono.text = user?.let { "Teléfono: ${it.getPhone()}" } ?: "Teléfono no disponible"

            // Buscar y mostrar información de la venta
            val venta = databaseHelper.getVentaByCodigo(ventaId)
            textViewVentaDescripcion.text = venta?.let { "Descripción: ${it.getDescripcion()}" } ?: "Venta no encontrada"
            textViewVentaPrecio.text = venta?.let { "Precio: ${it.getPrecio()}" } ?: "Precio no disponible"
            textViewVentaFecha.text = venta?.let { "Fecha: ${it.getFecha()}" } ?: "Fecha no disponible" // Muestra la fecha
        }

        // Configura el listener del botón de comprar
        btnComprar.setOnClickListener {
            val intent = Intent(this, Comprar::class.java)
            startActivity(intent)
        }
    }
}
