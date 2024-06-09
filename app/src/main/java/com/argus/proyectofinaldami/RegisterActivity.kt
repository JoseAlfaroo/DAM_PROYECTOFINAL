package com.argus.proyectofinaldami

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    //Elementos
    private lateinit var txtName: TextInputEditText
    private lateinit var txtLastName: TextInputEditText
    private lateinit var txtAddress: TextInputEditText
    private lateinit var txtPhone: TextInputEditText
    private lateinit var txtEmail: TextInputEditText
    private lateinit var txtPassword: TextInputEditText
    private lateinit var txtConfirmPassword: TextInputEditText
    private lateinit var RegisterButton: Button
    private lateinit var LoginInsteadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Referencias (No voy a repetir eso)
        txtName = findViewById(R.id.txtName)
        txtLastName = findViewById(R.id.txtLastName)
        txtAddress = findViewById(R.id.txtAddress)
        txtPhone = findViewById(R.id.txtPhone)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword)
        RegisterButton = findViewById(R.id.RegisterButton)
        LoginInsteadButton = findViewById(R.id.LoginInsteadButton)

        RegisterButton.setOnClickListener{ register() }
        LoginInsteadButton.setOnClickListener{ login() }
    }

    private fun register() {
        Toast.makeText(this, "Probando botón registro", Toast.LENGTH_SHORT).show()
    }

    private fun login() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}