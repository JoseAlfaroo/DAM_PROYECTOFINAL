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

class LibroRegisterActivity:AppCompatActivity() {
    private lateinit var txtTituloLibroRegister:TextInputEditText
    private lateinit var spnAutorLibroRegister: AutoCompleteTextView
    private lateinit var spnGeneroLibroRegister: AutoCompleteTextView
    private lateinit var txtDescripcionLibroRegister:TextInputEditText
    private lateinit var txtImagenLibroRegister:TextInputEditText
    private lateinit var btnRegisterLibro:Button

    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout
    private lateinit var apiLibro: ApiServiceLibro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_libro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.libro_register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtTituloLibroRegister=findViewById(R.id.txtTituloLibroRegister)
        spnAutorLibroRegister=findViewById(R.id.spnAutorLibroRegister)
        spnGeneroLibroRegister=findViewById(R.id.spnGeneroLibroRegister)
        txtDescripcionLibroRegister=findViewById(R.id.txtDescripcionLibroRegister)
        txtImagenLibroRegister=findViewById(R.id.txtImagenLibroRegister)

        btnRegisterLibro=findViewById(R.id.btnRegisterLibro)
        btnRegisterLibro.setOnClickListener { register_libro() }
        cargarautores()

        apiLibro=ApiUtils.getAPIServiceTLC()

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

    fun cargarautores(){
        val autores = AutorController().findAll()
        val autorNames = autores.map { it.nombre_autor }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, autorNames)
        spnAutorLibroRegister.setAdapter(adapter)
    }
    fun register_libro(){
        var tit=txtTituloLibroRegister.text.toString()
        var aut=spnAutorLibroRegister.text.toString()
        var gen=spnGeneroLibroRegister.text.toString()
        var des=txtDescripcionLibroRegister.text.toString()
        var img=txtImagenLibroRegister.text.toString()
        var bean=Libro(0,tit,aut,gen,des,img)
        apiLibro.saveLibro(bean).enqueue(object: Callback<Libro>{
            override fun onResponse(call: Call<Libro>, response: Response<Libro>) {
                if (response.isSuccessful){
                    var obj=response.body()!!
                    showAlert("Libro registrado exitosamente", DialogInterface.OnClickListener { _, _ ->
                        irlibro()
                    })
                }
                else {
                    showAlert("Error en el registro del libro: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Libro>, t: Throwable) {
                showAlert(t.localizedMessage)
            }

        })
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