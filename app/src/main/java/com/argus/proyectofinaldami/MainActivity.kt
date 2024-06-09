package com.argus.proyectofinaldami

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    //Elementos
    private lateinit var txtEmail:TextInputEditText
    private lateinit var txtPassword:TextInputEditText
    private lateinit var LoginButton:Button
    private lateinit var RegisterButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Referencias (Uff referencia)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        LoginButton = findViewById(R.id.LoginButton)
        RegisterButton = findViewById(R.id.RegisterButton)

        LoginButton.setOnClickListener{ login() }
        RegisterButton.setOnClickListener{ register() }

    }

    private fun login() {
        Toast.makeText(this, "Probando botón iniciar sesión", Toast.LENGTH_SHORT).show()
    }

    private fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}