package com.example.miprimerdiakotlin.bd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Crear la tabla productos
        db?.execSQL(
            "CREATE TABLE productos (" +
                    "id_productos INTEGER PRIMARY KEY," +
                    "nombre TEXT," +
                    "precio REAL," +
                    "codigo TEXT)"
        )

        // Crear la tabla personas
        db?.execSQL(
            "CREATE TABLE personas (" +
                    "carnet TEXT PRIMARY KEY," +
                    "nombre TEXT," +
                    "apellido TEXT," +
                    "telefono TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Actualizar las tablas si hay una nueva versión
        db?.execSQL("DROP TABLE IF EXISTS productos")
        db?.execSQL("DROP TABLE IF EXISTS personas")
        onCreate(db)
    }
}

    /*val createTableQuery = "CREATE TABLE productos (" +
               "id_producto INTEGER PRIMARY KEY AUTOINCREMENT," +
               "nombre TEXT NOT NULL," +
               "precio REAL NOT NULL)"
       db?.execSQL(createTableQuery)*/
