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

class AutorUpdateActivity:AppCompatActivity() {
    private lateinit var txtCodigoAutorUpdate: TextInputEditText
    private lateinit var txtNombreAutorUpdate: TextInputEditText
    private lateinit var btnUpdateAutor: Button
    private lateinit var btnDeleteAutor: Button
    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_autor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.autor_update)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtCodigoAutorUpdate=findViewById(R.id.txtCodigoAutorUpdate)
        txtNombreAutorUpdate=findViewById(R.id.txtNombreAutorUpdate)
        btnUpdateAutor=findViewById(R.id.btnUpdateAutor)
        btnDeleteAutor=findViewById(R.id.btnDeleteAutor)
        btnUpdateAutor.setOnClickListener { update_autor() }
        btnDeleteAutor.setOnClickListener { delete_autor() }

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
        datos()
    }
    fun datos(){
        var info=intent.extras!!
        var bean=AutorController().findById(info.getInt("codigo_autor"))
        txtCodigoAutorUpdate.setText(bean.codigo_autor.toString())
        txtNombreAutorUpdate.setText(bean.nombre_autor)
    }
    fun update_autor(){
        var cod=txtCodigoAutorUpdate.text.toString().toInt()
        var nom=txtNombreAutorUpdate.text.toString()
        var bean=Autor(cod,nom)


        var salida=AutorController().update(bean)
        if(salida>0){
            showAlert("Autor Actualizado", DialogInterface.OnClickListener { dialog, which ->
                irautor()
            })
        }
        else
            showAlert("Error en la actualización de un Autor")
    }

    fun delete_autor(){
        var cod=txtCodigoAutorUpdate.text.toString().toInt()
        showAlertConfirm("Esta seguro de eliminar Autor con ID : "+cod,cod)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent= Intent(this,AutorActivity::class.java)
        startActivity(intent)
    }

    fun showAlert(men:String, listener: DialogInterface.OnClickListener? = null){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("App The Librarian Cat")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",listener)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    fun showAlertConfirm(men:String,cod:Int){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("The Librarian Cat")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",DialogInterface.OnClickListener{
                dialogInterface: DialogInterface, i: Int ->
            var salida=AutorController().delete(cod)
            if(salida>0) {
                showAlert("Autor Eliminado", DialogInterface.OnClickListener { dialog, which ->
                    irautor()
                })
            }
            else {
                showAlert("Error en la eliminación del Autor")
            }
        })
        builder.setNegativeButton("Cancelar",null)
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