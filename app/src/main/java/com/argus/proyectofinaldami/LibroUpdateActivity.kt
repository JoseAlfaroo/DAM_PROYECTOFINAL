package com.argus.proyectofinaldami

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.argus.proyectofinaldami.controller.AutorController
import com.argus.proyectofinaldami.entidad.Autor
import com.argus.proyectofinaldami.entidad.Libro
import com.argus.proyectofinaldami.services.ApiServiceLibro
import com.argus.proyectofinaldami.utils.ApiUtils
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibroUpdateActivity:AppCompatActivity() {

    private lateinit var txtCodigoLibroUpdate:TextInputEditText
    private lateinit var txtTituloLibroUpdate:TextInputEditText
    private lateinit var spnAutorLibroUpdate: AutoCompleteTextView
    private lateinit var spnGeneroLibroUpdate: AutoCompleteTextView
    private lateinit var txtDescripcionLibroUpdate:TextInputEditText
    private lateinit var txtImagenLibroUpdate:TextInputEditText
    private lateinit var btnUpdateLibro:Button
    private lateinit var btnDeleteLibro:Button

    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout
    private lateinit var apiLibro: ApiServiceLibro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_libro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.libro_update)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtCodigoLibroUpdate=findViewById(R.id.txtCodigoLibroUpdate)
        txtTituloLibroUpdate=findViewById(R.id.txtTituloLibroUpdate)
        spnAutorLibroUpdate=findViewById(R.id.spnAutorLibroUpdate)
        spnGeneroLibroUpdate=findViewById(R.id.spnGeneroLibroUpdate)
        txtDescripcionLibroUpdate=findViewById(R.id.txtDescripcionLibroUpdate)
        txtImagenLibroUpdate=findViewById(R.id.txtImagenLibroUpdate)

        btnUpdateLibro=findViewById(R.id.btnUpdateLibro)
        btnDeleteLibro=findViewById(R.id.btnDeleteLibro)
        btnUpdateLibro.setOnClickListener { update_libro() }
        btnDeleteLibro.setOnClickListener { delete_libro() }
        apiLibro=ApiUtils.getAPIServiceTLC()
        datos()
        cargarautores()



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

    fun datos(){
        var info=intent.extras!!
        apiLibro.findLibroById(info.getInt("libroID")).enqueue(object : Callback<Libro>{
            override fun onResponse(call: Call<Libro>, response: Response<Libro>) {
                if(response.isSuccessful){
                    var obj=response.body()!!
                    txtCodigoLibroUpdate.setText(obj.libroID.toString())
                    txtTituloLibroUpdate.setText(obj.titulo)
                    spnAutorLibroUpdate.setText(obj.autor,false)
                    spnGeneroLibroUpdate.setText(obj.genero,false)
                    txtDescripcionLibroUpdate.setText(obj.descripcion)
                    txtImagenLibroUpdate.setText(obj.imagen)
                }
            }

            override fun onFailure(call: Call<Libro>, t: Throwable) {
                showAlert(t.localizedMessage)
            }

        })
    }

    fun cargarautores(){
        val autores = AutorController().findAll()
        val autorNames = autores.map { it.nombre_autor }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, autorNames)
        spnAutorLibroUpdate.setAdapter(adapter)
    }
    fun update_libro(){
        var id=txtCodigoLibroUpdate.text.toString().toInt()
        var tit=txtTituloLibroUpdate.text.toString()
        var aut=spnAutorLibroUpdate.text.toString()
        var gen=spnGeneroLibroUpdate.text.toString()
        var des=txtDescripcionLibroUpdate.text.toString()
        var img=txtImagenLibroUpdate.text.toString()
        var bean=Libro(id,tit,aut,gen,des,img)
        apiLibro.updateLibro(id,bean).enqueue(object: Callback<Libro>{
            override fun onResponse(call: Call<Libro>, response: Response<Libro>) {
                if (response.isSuccessful){
                    var obj=response.body()!!
                    showAlert("Libro actualizado exitosamente", DialogInterface.OnClickListener { _, _ ->
                        irlibro()
                    })
                }
                else {
                    showAlert("Error en la actualización del libro: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Libro>, t: Throwable) {
                showAlert(t.localizedMessage)
            }

        })
    }

    fun delete_libro(){
        var id=txtCodigoLibroUpdate.text.toString().toInt()
        showAlertConfirm("Esta seguro de eliminar el Libro con ID : "+id,id)
    }

    fun showAlertConfirm(men:String,id:Int){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("App The Librarian Cat")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",DialogInterface.OnClickListener{
                dialogInterface: DialogInterface, i: Int ->
            apiLibro.deleteLibro(id).enqueue(object:Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful){
                        showAlert("Libro Eliminado", DialogInterface.OnClickListener { _, _ ->
                            irlibro()
                        })
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    showAlert(t.localizedMessage)
                }
            })
        })
        builder.setNegativeButton("Cancelar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    fun showAlert(men:String, listener: DialogInterface.OnClickListener? = null){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("App The Librarian Cat")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",listener)
        val dialog: AlertDialog=builder.create()
        dialog.show()
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
        var intent= Intent(this,AutorActivity::class.java)
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