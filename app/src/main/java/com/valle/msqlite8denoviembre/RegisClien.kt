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

class RegisClien : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var adapter: UserAdapter
    private lateinit var users: ArrayList<Usuario>

    private lateinit var carnetInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var phoneInput: EditText

    private var selectedUser: Usuario? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis_clien)

        // Inicialización de la base de datos y los datos
        db = DatabaseHelper(this)
        users = db.getAllUsers()

        // Inicialización de los campos de texto
        carnetInput = findViewById(R.id.txtCarnet)
        nameInput = findViewById(R.id.txtNombres)
        lastNameInput = findViewById(R.id.txtApellidos)
        phoneInput = findViewById(R.id.txtPhone)

        // Configuración del RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.rvDatosUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(this, users)
        recyclerView.adapter = adapter

        // Configuración del listener de selección de usuario
        adapter.onUserSelected = { user ->
            selectedUser = user
            carnetInput.setText(user.getCarnet())
            nameInput.setText(user.getFirstName())
            lastNameInput.setText(user.getLastName())
            phoneInput.setText(user.getPhone())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.icon_add -> {
                if (carnetInput.text.toString().isBlank() ||
                    nameInput.text.toString().isBlank() ||
                    lastNameInput.text.toString().isBlank() ||
                    phoneInput.text.toString().isBlank()
                ) {
                    Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    val user = Usuario().apply {
                        val codigoUser = "USE-" + (users.size + 1).toString().padStart(4, '0')
                        setCarnet(codigoUser)
                        setFirstName(nameInput.text.toString())
                        setLastName(lastNameInput.text.toString())
                        setPhone(phoneInput.text.toString())
                    }
                    db.addUser(user)
                    users.add(user)
                    adapter.notifyItemInserted(users.size -1)
                    Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                }
            }
            R.id.icon_edit -> {
                selectedUser?.let { user ->
                    user.setCarnet(carnetInput.text.toString())
                    user.setFirstName(nameInput.text.toString())
                    user.setLastName(lastNameInput.text.toString())
                    user.setPhone(phoneInput.text.toString())

                    db.updateUser(user)
                    val index = users.indexOf(user)
                    if (index != -1) {
                        users[index] = user
                        adapter.notifyItemChanged(index)
                    }
                    Toast.makeText(this, "Usuario actualizado exitosamente", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                }
            }
            R.id.icon_del -> {
                selectedUser?.let { user ->
                    db.deleteUser(user)
                    val index = users.indexOf(user)
                    if (index != -1) {
                        users.removeAt(index)
                        adapter.notifyItemRemoved(index)
                    }
                    Toast.makeText(this, "Usuario eliminado exitosamente", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun limpiarCampos() {
        carnetInput.setText("")
        nameInput.setText("")
        lastNameInput.setText("")
        phoneInput.setText("")
        selectedUser = null
    }
}
