package com.valle.msqlite8denoviembre;

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.miprimerdiakotlin.bd.AdminSQLiteOpenHelper
import com.example.miprimerdiakotlin.models.Productos
import com.valle.msqlite8denoviembre.R

class Iva : AppCompatActivity() {
    lateinit var btncalc:Button
    lateinit var txtPecio:EditText
    lateinit var txtNombre:EditText
    lateinit var tvresul:TextView
    lateinit var spLista:Spinner
    lateinit var lispro:ListView
    lateinit var btnBuscar:Button
    lateinit var productosList:MutableList<String>
    lateinit var  adapterListView:ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iva)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //CODIGO
        cargarR()
        estadoOnclicked()
        cargarListaProductos()


        //cargar lista de datois en spinner
        val lispPaises= arrayOf("USA","ESP","BOL")
        val adaptador1=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lispPaises)
        spLista.adapter=adaptador1

    }
    fun cargarR(){
        btncalc=findViewById(R.id.calprodu)
        txtPecio=findViewById(R.id.txtProducto)
        txtNombre=findViewById(R.id.txtnompro)
        tvresul=findViewById(R.id.tvResultado)
        spLista=findViewById(R.id.spaises)
        lispro=findViewById(R.id.listaProduct)
        btnBuscar=findViewById(R.id.btnBuscar)
    }
    fun estadoOnclicked(){
        btncalc.setOnClickListener(){
            val laptop=Productos(txtNombre.text.toString(),txtPecio.text.toString().toDouble())
            //al datosRe:Double=laptop.calIVA()
            when (spLista.selectedItem.toString()) {
                "USA" -> productosList.add(laptop.getNombre() + "," +laptop.getPrecio()+ " IVA" + laptop.calIVA(0.03).toString())
                "ESP" -> productosList.add(laptop.getNombre() + "," + laptop.getPrecio()+"IVA" + laptop.calIVA(0.05).toString())
                "BOL" -> productosList.add(laptop.getNombre() + "," + laptop.getPrecio()+"IVA" + laptop.calIVA(0.13).toString())

            }
            lispro.adapter=adapterListView


        }
        btnBuscar.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select nombre,precio from productos where id_productos=${txtNombre.text.toString()}", null)
            if (fila.moveToFirst()) {
                txtNombre.setText(fila.getString(0))
                txtPecio.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un artículo con dicho código",  Toast.LENGTH_SHORT).show()
            bd.close()
        }
    }
    fun cargarListaProductos(){
        // val productos= arrayOf("laptop", "mouse")
        productosList=mutableListOf("3500")
        adapterListView =ArrayAdapter(this,android.R.layout.simple_list_item_1,productosList)
        lispro.adapter=adapterListView
    }

}