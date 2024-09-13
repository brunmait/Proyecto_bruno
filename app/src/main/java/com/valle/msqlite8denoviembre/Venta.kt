package com.valle.msqlite8denoviembre

class Venta {
    private var codigoVenta: String? = null
    private var descripcion: String? = null
    private var precio: Double? = null
    private var cantidad: Int? = null
    private var fecha: String? = null // Nueva propiedad
    fun getCodigoVenta(): String? = codigoVenta
    fun setCodigoVenta(codigoVenta: String) { this.codigoVenta = codigoVenta }

    fun getDescripcion(): String? = descripcion
    fun setDescripcion(descripcion: String) { this.descripcion = descripcion }

    fun getPrecio(): Double? = precio
    fun setPrecio(precio: Double) { this.precio = precio }

    fun getCantidad(): Int? = cantidad
    fun setCantidad(cantidad: Int) { this.cantidad = cantidad }
    fun getFecha(): String? = fecha
    fun setFecha(fecha: String){this.fecha = fecha}
}
