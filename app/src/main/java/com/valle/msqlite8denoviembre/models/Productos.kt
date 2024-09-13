package com.example.miprimerdiakotlin.models

class Productos constructor(nombre: String, precio: Double) {
    private var nombre: String = nombre
    private var precio: Double = precio

    // Métodos get y set para nombre
    fun getNombre(): String {
        return nombre
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    // Métodos get y set para precio
    fun getPrecio(): Double {
        return precio
    }

    fun setPrecio(precio: Double) {
        this.precio = precio
    }

    // FUNCION PARA CALCULAR IVA
    fun calIVA(iva: Double): Double {
        val total: Double = precio * iva
        return total
    }
}