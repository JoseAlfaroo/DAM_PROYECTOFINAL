package com.argus.proyectofinaldami

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.argus.proyectofinaldami.entidad.UserSessionData

class SplashActivity:AppCompatActivity() {
    private lateinit var tvBienvenido:TextView
    private lateinit var btnContinuar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        tvBienvenido = findViewById(R.id.tvBienvenido)
        btnContinuar = findViewById(R.id.btnContinuar)
        //bienvenida()

        btnContinuar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    fun bienvenida(){
        tvBienvenido.setText("Bienvenido "+UserSessionData.nombres+" "+UserSessionData.apellidos+"!")
    }



}