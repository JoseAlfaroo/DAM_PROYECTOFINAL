package com.argus.proyectofinaldami

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LibroActivity:AppCompatActivity() {
    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor:LinearLayout
    private lateinit var btnPrestamo:LinearLayout
    private lateinit var btnPerfil:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_libro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.libro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnHome=findViewById(R.id.btnHomeMenu)
        btnLibro=findViewById(R.id.btnLibroMenu)
        btnAutor=findViewById(R.id.btnAutorMenu)
        btnPrestamo=findViewById(R.id.btnPrestamoMenu)
        btnPerfil=findViewById(R.id.btnPerfilMenu)
        btnHome.setOnClickListener { irhome() }
        btnLibro.setOnClickListener { irlibro() }
        btnAutor.setOnClickListener { irautor() }
        btnPrestamo.setOnClickListener { irprestamo() }
        btnPerfil.setOnClickListener { irperfil() }
    }
    fun irhome(){
        var intent= Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }

    fun irlibro(){
        var intent= Intent(this,LibroActivity::class.java)
        startActivity(intent)
    }
    fun irautor(){
        var intent=Intent(this,AutorActivity::class.java)
        startActivity(intent)
    }

    fun irprestamo(){
        //var intent=Intent(this,PrestamoActivity::class.java)
        startActivity(intent)
    }
    fun irperfil(){
        //var intent=Intent(this,PerfilActivity::class.java)
        startActivity(intent)
    }
}