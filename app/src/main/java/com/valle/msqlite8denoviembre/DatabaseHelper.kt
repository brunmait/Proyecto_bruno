package com.valle.msqlite8denoviembre

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2 // Actualiza la versión si es necesario
        private const val DATABASE_NAME = "Database"
        private const val TABLE_USERS = "Users"
        private const val TABLE_VENTAS = "Ventas"

        // Columnas para la tabla de usuarios
        private const val KEY_CARNET = "carnet"
        private const val KEY_FIRST_NAME = "first_name"
        private const val KEY_LAST_NAME = "last_name"
        private const val KEY_PHONE = "phone"

        // Columnas para la tabla de ventas
        private const val KEY_CODIGO_VENTA = "codigo_venta"
        private const val KEY_DESCRIPCION = "descripcion"
        private const val KEY_PRECIO = "precio"
        private const val KEY_CANTIDAD = "cantidad"
        private const val KEY_FECHA = "fecha" // Nueva columna
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear la tabla de usuarios con el campo carnet como clave primaria
        val CREATE_USERS_TABLE = ("CREATE TABLE $TABLE_USERS (" +
                "$KEY_CARNET TEXT PRIMARY KEY," + // Definir carnet como la clave primaria
                "$KEY_FIRST_NAME TEXT," +
                "$KEY_LAST_NAME TEXT," +
                "$KEY_PHONE TEXT)")
        db.execSQL(CREATE_USERS_TABLE)

        // Crear la tabla de ventas
        val CREATE_VENTAS_TABLE = ("CREATE TABLE $TABLE_VENTAS (" +
                "$KEY_CODIGO_VENTA TEXT PRIMARY KEY," +
                "$KEY_DESCRIPCION TEXT," +
                "$KEY_PRECIO REAL," +
                "$KEY_CANTIDAD INTEGER," +
                "$KEY_FECHA TEXT)") // Nueva columna
        db.execSQL(CREATE_VENTAS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            // Agregar la columna fecha a la tabla Ventas en la versión 2
            db.execSQL("ALTER TABLE $TABLE_VENTAS ADD COLUMN $KEY_FECHA TEXT")
        }
        // Si se realizan cambios en versiones futuras, puedes manejarlos aquí
    }

    // Métodos para usuarios
    fun addUser(user: Usuario) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_CARNET, user.getCarnet())  // carnet es la clave primaria
            put(KEY_FIRST_NAME, user.getFirstName())
            put(KEY_LAST_NAME, user.getLastName())
            put(KEY_PHONE, user.getPhone())
        }
        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    fun updateUser(user: Usuario): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_FIRST_NAME, user.getFirstName())
            put(KEY_LAST_NAME, user.getLastName())
            put(KEY_PHONE, user.getPhone())
        }
        return db.update(
            TABLE_USERS,
            values,
            "$KEY_CARNET = ?",
            arrayOf(user.getCarnet()))  // Utilizar carnet
    }

    fun deleteUser(user: Usuario) {
        val db = this.writableDatabase
        db.delete(TABLE_USERS, "$KEY_CARNET = ?", arrayOf(user.getCarnet()))  // Utilizar carnet
        db.close()
    }

    fun getAllUsers(): ArrayList<Usuario> {
        val users = ArrayList<Usuario>()
        val selectQuery = "SELECT * FROM $TABLE_USERS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            val carnetIndex = cursor.getColumnIndex(KEY_CARNET)
            val firstNameIndex = cursor.getColumnIndex(KEY_FIRST_NAME)
            val lastNameIndex = cursor.getColumnIndex(KEY_LAST_NAME)
            val phoneIndex = cursor.getColumnIndex(KEY_PHONE)

            do {
                val user = Usuario().apply {
                    setCarnet(cursor.getString(carnetIndex))
                    setFirstName(cursor.getString(firstNameIndex))
                    setLastName(cursor.getString(lastNameIndex))
                    setPhone(cursor.getString(phoneIndex))
                }
                users.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return users
    }

    // Métodos para ventas
    fun addVenta(venta: Venta) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_CODIGO_VENTA, venta.getCodigoVenta())
            put(KEY_DESCRIPCION, venta.getDescripcion())
            put(KEY_PRECIO, venta.getPrecio())
            put(KEY_CANTIDAD, venta.getCantidad())
            put(KEY_FECHA, venta.getFecha()) // Nueva columna
        }
        db.insert(TABLE_VENTAS, null, values)
        db.close()
    }

    fun updateVenta(venta: Venta): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_DESCRIPCION, venta.getDescripcion())
            put(KEY_PRECIO, venta.getPrecio())
            put(KEY_CANTIDAD, venta.getCantidad())
            put(KEY_FECHA, venta.getFecha()) // Nueva columna
        }
        return db.update(
            TABLE_VENTAS,
            values,
            "$KEY_CODIGO_VENTA = ?",
            arrayOf(venta.getCodigoVenta())
        )
    }

    fun deleteVenta(venta: Venta) {
        val db = this.writableDatabase
        db.delete(TABLE_VENTAS, "$KEY_CODIGO_VENTA = ?", arrayOf(venta.getCodigoVenta()))
        db.close()
    }

    @SuppressLint("Range")
    fun getAllVentas(): ArrayList<Venta> {
        val ventas = ArrayList<Venta>()
        val selectQuery = "SELECT * FROM $TABLE_VENTAS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val venta = Venta().apply {
                    setCodigoVenta(cursor.getString(cursor.getColumnIndex(KEY_CODIGO_VENTA)))
                    setDescripcion(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPCION)))
                    setPrecio(cursor.getDouble(cursor.getColumnIndex(KEY_PRECIO)))
                    setCantidad(cursor.getInt(cursor.getColumnIndex(KEY_CANTIDAD)))
                    setFecha(cursor.getString(cursor.getColumnIndex(KEY_FECHA))) // Nueva columna
                }
                ventas.add(venta)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return ventas
    }

    // Obtener usuario por carnet
    fun getUserByCarnet(carnet: String): Usuario? {
        val selectQuery = "SELECT * FROM $TABLE_USERS WHERE $KEY_CARNET = ?"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(carnet))

        var user: Usuario? = null
        if (cursor.moveToFirst()) {
            val carnetIndex = cursor.getColumnIndex(KEY_CARNET)
            val firstNameIndex = cursor.getColumnIndex(KEY_FIRST_NAME)
            val lastNameIndex = cursor.getColumnIndex(KEY_LAST_NAME)
            val phoneIndex = cursor.getColumnIndex(KEY_PHONE)

            user = Usuario().apply {
                setCarnet(cursor.getString(carnetIndex))
                setFirstName(cursor.getString(firstNameIndex))
                setLastName(cursor.getString(lastNameIndex))
                setPhone(cursor.getString(phoneIndex))
            }
        }
        cursor.close()
        return user
    }

    // Obtener venta por código
    @SuppressLint("Range")
    fun getVentaByCodigo(codigoVenta: String): Venta? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_VENTAS WHERE $KEY_CODIGO_VENTA = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(codigoVenta))

        var venta: Venta? = null
        if (cursor.moveToFirst()) {
            venta = Venta().apply {
                setCodigoVenta(cursor.getString(cursor.getColumnIndex(KEY_CODIGO_VENTA)))
                setDescripcion(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPCION)))
                setPrecio(cursor.getDouble(cursor.getColumnIndex(KEY_PRECIO)))
                setCantidad(cursor.getInt(cursor.getColumnIndex(KEY_CANTIDAD)))
                setFecha(cursor.getString(cursor.getColumnIndex(KEY_FECHA))) // Nueva columna
            }
        }
        cursor.close()
        return venta
    }
}
