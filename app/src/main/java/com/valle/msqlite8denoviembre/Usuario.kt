package com.valle.msqlite8denoviembre

class Usuario {
    private var carnet: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var phone: String? = null

    private var fotoUrl: String? = null

    // Métodos getter y setter para Carnet
    fun getCarnet(): String? = carnet
    fun setCarnet(carnet: String) { this.carnet = carnet }
    // Métodos getter y setter para Nombre
    fun getFirstName(): String? = firstName
    fun setFirstName(firstName: String) { this.firstName = firstName }

    // Métodos getter y setter para Apellido
    fun getLastName(): String? = lastName
    fun setLastName(lastName: String) { this.lastName = lastName }

    // Métodos getter y setter para Teléfono
    fun getPhone(): String? = phone
    fun setPhone(phone: String) { this.phone = phone }
    // Métodos getter y setter para Foto
    fun getFotoUrl(): String? = fotoUrl
    fun setFotoUrl(fotoUrl: String) { this.fotoUrl = fotoUrl }
}
