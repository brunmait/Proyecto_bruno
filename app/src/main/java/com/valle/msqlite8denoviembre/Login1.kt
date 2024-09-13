package com.valle.msqlite8denoviembre;

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.valle.msqlite8denoviembre.Home1
import com.valle.msqlite8denoviembre.R

class Login1: AppCompatActivity() {
    lateinit var btnIngresar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cargarR();
        estadoBoton();
    }
    fun cargarR(){
        btnIngresar = findViewById(R.id.btnRegistroProducto)
    }

    fun estadoBoton(){
        btnIngresar.setOnClickListener{
            val i = Intent(this, Home1::class.java)
            startActivity(i)
        }
    }
}