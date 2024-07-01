package com.argus.proyectofinaldami

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.argus.proyectofinaldami.controller.AutorController
import com.argus.proyectofinaldami.entidad.Autor
import com.argus.proyectofinaldami.entidad.Genero
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GeneroRegisterActivity:AppCompatActivity() {
    private lateinit var txtGeneroRegister:TextInputEditText
    private lateinit var btnRegisterGenero:Button
    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout

    private lateinit var bd: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_genero)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.genero_register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtGeneroRegister=findViewById(R.id.txtGeneroRegister)
        btnRegisterGenero=findViewById(R.id.btnRegisterGenero)
        btnRegisterGenero.setOnClickListener { register_genero() }

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

        conectar()
    }
    fun conectar(){
        FirebaseApp.initializeApp(this)
        bd=FirebaseDatabase.getInstance().reference
    }
    fun register_genero(){
        var nom_gen=txtGeneroRegister.text.toString()
        var ID=bd.push().key
        var bean=Genero(ID!!,nom_gen)
        if (nom_gen.isEmpty()){
            showAlert("El campo Género es obligatorio")
            return
        }
        bd.child("genero").child(ID!!).setValue(bean).addOnCompleteListener {
            showAlert("Genero registrado",DialogInterface.OnClickListener { dialog, which ->
                irgenero()
                })
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent= Intent(this,GeneroActivity::class.java)
        startActivity(intent)
    }

    fun showAlert(men:String, listener: DialogInterface.OnClickListener? = null){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("The Librarian Cat")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",listener)
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
        val intent = Intent(this, AutorActivity::class.java)
        startActivity(intent)
    }

    private fun irgenero(){
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