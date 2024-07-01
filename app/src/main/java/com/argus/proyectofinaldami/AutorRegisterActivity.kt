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
import com.google.android.material.textfield.TextInputEditText

class AutorRegisterActivity:AppCompatActivity() {
    private lateinit var txtNombreAutorRegister:TextInputEditText
    private lateinit var btnRegisterAutor:Button
    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout

    private lateinit var btnGenero:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_autor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.autor_register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtNombreAutorRegister=findViewById(R.id.txtNombreRegister)
        btnRegisterAutor=findViewById(R.id.btnRegisterAutor)
        btnRegisterAutor.setOnClickListener { register_autor() }

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
        btnGenero=findViewById(R.id.btnGeneroMenu)
        btnGenero.setOnClickListener { irgenero() }
    }
    fun register_autor(){
        var nom=txtNombreAutorRegister.text.toString()

        if (nom.isEmpty()){
            showAlert("El campo Nombre del Autor es obligatorio")
            return
        }

        var bean=Autor(0,nom)
        var salida=AutorController().save(bean)
        if(salida>0) {
            showAlert("Autor Registrado", DialogInterface.OnClickListener { dialog, which ->
                irautor()
            })
        }
        else
            showAlert("Error en el registro de un Autor")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent= Intent(this,AutorActivity::class.java)
        startActivity(intent)
    }
    private fun irgenero(){
        val intent = Intent(this, GeneroActivity::class.java)
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

    private fun irprestamo() {
        val intent = Intent(this, PrestamoActivity::class.java)
        startActivity(intent)
    }

    private fun irperfil() {
        val intent = Intent(this, PerfilActivity::class.java)
        startActivity(intent)
    }
}