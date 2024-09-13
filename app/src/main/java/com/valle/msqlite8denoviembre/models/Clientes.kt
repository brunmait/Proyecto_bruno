package com.example.miprimerdiakotlin.models

class Clientes(
    private var carnet: String,
    private var nombre: String,
    private var apellido: String,
    private var celular: String
) {

    // Métodos getter y setter para carnet
    fun getCarnet(): String {
        return carnet
    }

    fun setCarnet(carnet: String) {
        this.carnet = carnet
    }

    // Métodos getter y setter para nombre
    fun getNombre(): String {
        return nombre
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    // Métodos getter y setter para apellido
    fun getApellido(): String {
        return apellido
    }

    fun setApellido(apellido: String) {
        this.apellido = apellido
    }

    // Métodos getter y setter para celular
    fun getCelular(): String {
        return celular
    }

    fun setCelular(celular: String) {
        this.celular = celular
    }

    override fun toString(): String {
        return "Carnet: $carnet\nNombre: $nombre\nApellido: $apellido\nCelular: $celular"
    }
}