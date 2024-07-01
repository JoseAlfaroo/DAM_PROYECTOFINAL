package com.argus.proyectofinaldami

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.adaptador.AutorAdapter
import com.argus.proyectofinaldami.adaptador.GeneroAdapter
import com.argus.proyectofinaldami.controller.AutorController
import com.argus.proyectofinaldami.entidad.Genero
import com.argus.proyectofinaldami.utils.appConfig
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GeneroActivity:AppCompatActivity() {
    private lateinit var rvGenero:RecyclerView
    private lateinit var txtBuscarGenero:TextInputEditText
    private lateinit var btnBuscarGenero:Button
    private lateinit var btnNuevoGenero:Button
    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout

    private lateinit var bd:DatabaseReference
    private lateinit var lista:ArrayList<Genero>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_genero)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.genero)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvGenero=findViewById(R.id.rvGenero)
        rvGenero.layoutManager = LinearLayoutManager(this)


        txtBuscarGenero=findViewById(R.id.txtBuscarGenero)
        btnBuscarGenero=findViewById(R.id.btnBuscarGenero)
        btnNuevoGenero=findViewById(R.id.btnNuevoGenero)


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
        btnBuscarGenero.setOnClickListener { buscargenero() }
        btnNuevoGenero.setOnClickListener { nuevogenero() }
        lista=ArrayList<Genero>()
        conectar()
        listargenero()
    }
    fun conectar(){
        FirebaseApp.initializeApp(this)
        bd=FirebaseDatabase.getInstance().reference
    }

    fun listargenero(){
        bd.child("genero").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(row in snapshot.children){
                    var bean=row.getValue(Genero::class.java)
                    lista.add(bean!!)
                }
                var adaptador=GeneroAdapter(lista)
                rvGenero.adapter=adaptador
                rvGenero.layoutManager=LinearLayoutManager(appConfig.CONTEXTO)
            }

            override fun onCancelled(error: DatabaseError) {
                showAlert(error.message)
            }

        })
    }

    fun buscargenero(){
        val text = txtBuscarGenero.text.toString().trim()
        lista.clear()
        bd.child("genero").removeEventListener(generoListener)

        val query = if (text.isEmpty()) {
            bd.child("genero")
        } else {
            bd.child("genero").orderByChild("nombre_genero").startAt(text).endAt(text + "\uf8ff")
        }

        query.addValueEventListener(generoListener)

    }

    private val generoListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            lista.clear()
            for (row in snapshot.children) {
                val bean = row.getValue(Genero::class.java)
                if (bean != null) {
                    lista.add(bean)
                }
            }
            val adapter = GeneroAdapter(lista)
            rvGenero.adapter = adapter
            rvGenero.layoutManager = LinearLayoutManager(appConfig.CONTEXTO)
        }

        override fun onCancelled(error: DatabaseError) {
            showAlert(error.message)
        }
    }

    fun nuevogenero(){
        var intent= Intent(this,GeneroRegisterActivity::class.java)
        startActivity(intent)
    }

    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("The Librarian Cat")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    private fun irhome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun irlibro() {
        val intent = Intent(this, LibroActivity::class.java)
        startActivity(intent)
    }

    private fun irautor() {
        val intent = Intent(this, GeneroActivity::class.java)
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