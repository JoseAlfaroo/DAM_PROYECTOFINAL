package com.argus.proyectofinaldami

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.adaptador.AutorAdapter
import com.argus.proyectofinaldami.controller.AutorController
import com.google.android.material.textfield.TextInputEditText

class AutorActivity:AppCompatActivity() {
    private lateinit var rvAutor:RecyclerView
    private lateinit var txtBuscarAutor:TextInputEditText
    private lateinit var btnBuscarAutor:Button
    private lateinit var btnNuevoAutor:Button
    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout
    private lateinit var btnGenero:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_autor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.autor)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvAutor=findViewById(R.id.rvAutor)
        var data=AutorController().findAll()
        var adaptador=AutorAdapter(data)
        rvAutor.adapter=adaptador
        rvAutor.layoutManager= LinearLayoutManager(this)


        txtBuscarAutor=findViewById(R.id.txtBuscarAutor)
        btnBuscarAutor=findViewById(R.id.btnBuscarAutor)
        btnNuevoAutor=findViewById(R.id.btnNuevoAutor)


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
        btnBuscarAutor.setOnClickListener { buscarautor() }
        btnNuevoAutor.setOnClickListener { nuevoautor() }

        btnGenero=findViewById(R.id.btnGeneroMenu)
        btnGenero.setOnClickListener { irgenero() }
    }

    fun buscarautor(){
        val query = txtBuscarAutor.text.toString()
        val data = if (query.isEmpty()) {
            AutorController().findAll()
        } else {
            AutorController().findByName(query)
        }
        val adaptador = AutorAdapter(data)
        rvAutor.adapter = adaptador
        rvAutor.layoutManager = LinearLayoutManager(this)

    }

    fun nuevoautor(){
        var intent= Intent(this,AutorRegisterActivity::class.java)
        startActivity(intent)
    }

    private fun irhome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
    private fun irgenero(){
        val intent = Intent(this, GeneroActivity::class.java)
        startActivity(intent)
    }

    private fun irlibro() {
        val intent = Intent(this, LibroActivity::class.java)
        startActivity(intent)
    }

    private fun irautor() {
        val intent = Intent(this, AutorActivity::class.java)
        startActivity(intent)
    }

    private fun irprestamo() {
        val intent = Intent(this, PrestamoActivity::class.java)
        startActivity(intent)
    }

    private fun irperfil() {
        val intent = Intent(this, PerfilActivity::class.java)
        startActivity(intent)
    }

}