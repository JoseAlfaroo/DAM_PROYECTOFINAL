package com.argus.proyectofinaldami

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.adaptador.LibroAdapter
import com.argus.proyectofinaldami.entidad.Libro
import com.argus.proyectofinaldami.services.ApiServiceLibro
import com.argus.proyectofinaldami.utils.ApiUtils
import com.argus.proyectofinaldami.utils.appConfig
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibroActivity:AppCompatActivity() {
    private lateinit var rvLibro: RecyclerView
    private lateinit var txtBuscarLibro: TextInputEditText
    private lateinit var btnBuscarLibro: ImageView
    private lateinit var btnNuevoLibro: Button
    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor:LinearLayout
    private lateinit var btnPrestamo:LinearLayout
    private lateinit var btnPerfil:LinearLayout
    private lateinit var apiLibro: ApiServiceLibro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_libro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.libro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvLibro=findViewById(R.id.rvLibro)
        txtBuscarLibro=findViewById(R.id.txtBuscarLibro)
        btnBuscarLibro=findViewById(R.id.btnBuscarLibro)
        btnNuevoLibro=findViewById(R.id.btnNuevoLibro)
        apiLibro=ApiUtils.getAPIServiceTLC()
        listarlibros()



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

        btnBuscarLibro.setOnClickListener { buscarlibro() }
        btnNuevoLibro.setOnClickListener { nuevoautor() }
    }

    fun listarlibros(){
        apiLibro.findLibros(null).enqueue(object: Callback<List<Libro>>{
            override fun onResponse(call: Call<List<Libro>>, response: Response<List<Libro>>) {
                if(response.isSuccessful){
                    var data=response.body()
                    var adaptador=LibroAdapter(data!!)
                    rvLibro.adapter=adaptador
                    rvLibro.layoutManager=LinearLayoutManager(appConfig.CONTEXTO)

                }
            }

            override fun onFailure(call: Call<List<Libro>>, t: Throwable) {
                showAlert(t.localizedMessage)

            }
        })
    }

    fun buscarlibro(){
        val titulo_autor = txtBuscarLibro.text.toString()
        if (titulo_autor.isEmpty()) {
            listarlibros()
        } else {
            apiLibro.findLibros(titulo_autor).enqueue(object : Callback<List<Libro>> {
                override fun onResponse(call: Call<List<Libro>>, response: Response<List<Libro>>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data.isNullOrEmpty()) {
                            showAlert("No se encontraron libros con el título \"$titulo_autor\".")
                        } else {
                            val adaptador = LibroAdapter(data)
                            rvLibro.adapter = adaptador
                            rvLibro.layoutManager = LinearLayoutManager(appConfig.CONTEXTO)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Libro>>, t: Throwable) {
                    showAlert(t.localizedMessage)
                }
            })
        }
    }

    fun nuevoautor(){
        var intent= Intent(this,LibroRegisterActivity::class.java)
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