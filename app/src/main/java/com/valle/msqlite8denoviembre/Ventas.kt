package com.valle.msqlite8denoviembre

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Ventas : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var adapter: VentaAdapter
    private lateinit var ventas: ArrayList<Venta>

    private lateinit var codigoVentaInput: EditText
    private lateinit var descripcionInput: EditText
    private lateinit var precioInput: EditText
    private lateinit var cantidadInput: EditText
    private lateinit var fechaInput: EditText // Nuevo campo

    private var selectedVenta: Venta? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventas) // Asegúrate de que este archivo existe y está correctamente configurado

        // Inicializa base de datos y otros componentes
        db = DatabaseHelper(this)
        ventas = db.getAllVentas()

        // Inicialización de los inputs
        codigoVentaInput = findViewById(R.id.txtCodigoVenta)
        descripcionInput = findViewById(R.id.txtDescripcion)
        precioInput = findViewById(R.id.txtPrecio)
        cantidadInput = findViewById(R.id.txtCantidad)
        fechaInput = findViewById(R.id.txtFecha) // Inicialización del nuevo campo

        // Configuración del RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.rvDatosVentas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VentaAdapter(this, ventas)
        recyclerView.adapter = adapter

        adapter.onVentaSelected = { venta ->
            selectedVenta = venta
            codigoVentaInput.setText(venta.getCodigoVenta())
            descripcionInput.setText(venta.getDescripcion())
            precioInput.setText(venta.getPrecio().toString())
            cantidadInput.setText(venta.getCantidad().toString())
            fechaInput.setText(venta.getFecha()) // Cargar la fecha
        }
    }

    // Crea el menú de opciones
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Maneja la selección de opciones en el menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.icon_add -> {
                if (codigoVentaInput.text.toString().isBlank() ||
                    descripcionInput.text.toString().isBlank() ||
                    precioInput.text.toString().isBlank() ||
                    cantidadInput.text.toString().isBlank() ||
                    fechaInput.text.toString().isBlank() // Verificar el campo fecha
                ) {
                    Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    val venta = Venta().apply {
                        val codigoSimple = "VEN-" + (ventas.size + 1).toString().padStart(4, '0')
                        setCodigoVenta(codigoSimple)
                        setDescripcion(descripcionInput.text.toString())
                        setPrecio(precioInput.text.toString().toDouble())
                        setCantidad(cantidadInput.text.toString().toInt())
                        setFecha(fechaInput.text.toString()) // Establecer la fecha
                    }
                    db.addVenta(venta)
                    ventas.add(venta)
                    adapter.notifyItemInserted(ventas.size - 1)
                    Toast.makeText(this, "Venta creada exitosamente", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                }
            }
            R.id.icon_edit -> {
                selectedVenta?.let { venta ->
                    venta.setCodigoVenta(codigoVentaInput.text.toString())
                    venta.setDescripcion(descripcionInput.text.toString())
                    venta.setPrecio(precioInput.text.toString().toDouble())
                    venta.setCantidad(cantidadInput.text.toString().toInt())
                    venta.setFecha(fechaInput.text.toString()) // Actualizar la fecha

                    db.updateVenta(venta)
                    val index = ventas.indexOf(venta)
                    if (index != -1) {
                        ventas[index] = venta
                        adapter.notifyItemChanged(index)
                    }
                    Toast.makeText(this, "Venta actualizada exitosamente", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                }
            }
            R.id.icon_del -> {
                selectedVenta?.let { venta ->
                    db.deleteVenta(venta)
                    val index = ventas.indexOf(venta)
                    if (index != -1) {
                        ventas.removeAt(index)
                        adapter.notifyItemRemoved(index)
                    }
                    Toast.makeText(this, "Venta eliminada exitosamente", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Método para limpiar los campos de entrada
    private fun limpiarCampos() {
        codigoVentaInput.setText("")
        descripcionInput.setText("")
        precioInput.setText("")
        cantidadInput.setText("")
        fechaInput.setText("") // Limpiar el campo de fecha
        selectedVenta = null
    }
}
