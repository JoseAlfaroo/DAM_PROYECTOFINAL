package com.argus.proyectofinaldami

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.adaptador.AutorAdapter
import com.argus.proyectofinaldami.adaptador.LibroAdapter
import com.argus.proyectofinaldami.adaptador.LibroHomeAdapter
import com.argus.proyectofinaldami.controller.AutorController
import com.argus.proyectofinaldami.entidad.Libro
import com.argus.proyectofinaldami.entidad.UserSessionData
import com.argus.proyectofinaldami.services.ApiServiceLibro
import com.argus.proyectofinaldami.utils.ApiUtils
import com.argus.proyectofinaldami.utils.appConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity:AppCompatActivity() {

    //datos usu
    private lateinit var tvUserID:TextView
    private lateinit var tvUserNom:TextView
    private lateinit var tvUserApe:TextView
    private lateinit var tvUserEmail:TextView

    private lateinit var rvLibroMenu:RecyclerView
    private lateinit var rvAutorMenu:RecyclerView
    private lateinit var btnHome:LinearLayout
    private lateinit var btnLibro:LinearLayout
    private lateinit var btnAutor:LinearLayout
    private lateinit var btnGenero:LinearLayout
    private lateinit var btnPrestamo:LinearLayout
    private lateinit var btnPerfil:LinearLayout

    private lateinit var apiLibro: ApiServiceLibro


    private lateinit var imgCarruselHome:ViewFlipper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var images = intArrayOf(R.drawable.libros,R.drawable.librogato1,R.drawable.libros2,R.drawable.librogato2,R.drawable.libros3)
        imgCarruselHome=findViewById(R.id.imgCarruselHome)



        tvUserID = findViewById(R.id.tvIDSesion)
        tvUserNom = findViewById(R.id.tvNomSesion)
        tvUserApe = findViewById(R.id.tvApeSesion)
        tvUserEmail = findViewById(R.id.tvEmailSesion)

        cargardatosusu()

        rvLibroMenu=findViewById(R.id.rvLibroMenu)
        rvAutorMenu=findViewById(R.id.rvAutorMenu)

        var data= AutorController().findAll()
        var adaptador= AutorAdapter(data)
        rvAutorMenu.adapter=adaptador
        rvAutorMenu.layoutManager= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        apiLibro=ApiUtils.getAPIServiceTLC()
        listarlibros()



        btnHome=findViewById(R.id.btnHomeMenu)
        btnLibro=findViewById(R.id.btnLibroMenu)
        btnAutor=findViewById(R.id.btnAutorMenu)
        btnGenero=findViewById(R.id.btnGeneroMenu)
        btnPrestamo=findViewById(R.id.btnPrestamoMenu)
        btnPerfil=findViewById(R.id.btnPerfilMenu)
        btnHome.setOnClickListener { irhome() }
        btnLibro.setOnClickListener { irlibro() }
        btnAutor.setOnClickListener { irautor() }
        btnGenero.setOnClickListener { irgenero() }
        btnPrestamo.setOnClickListener { irprestamo() }
        btnPerfil.setOnClickListener { irperfil() }

        for (image in images) {
            flipperImages(image)
        }
    }
    fun flipperImages(image:Int){

        val imageView = ImageView(this)
        imageView.setImageResource(image)

        imgCarruselHome.addView(imageView)
        imgCarruselHome.flipInterval = 3000
        imgCarruselHome.isAutoStart = true

        val inAnimation = AnimationUtils.loadAnimation(applicationContext, android.R.anim.slide_in_left)
        val outAnimation = AnimationUtils.loadAnimation(applicationContext, android.R.anim.slide_out_right)
        imgCarruselHome.inAnimation = inAnimation
        imgCarruselHome.outAnimation = outAnimation
    }

    fun listarlibros(){

        apiLibro.findLibros(null).enqueue(object: Callback<List<Libro>> {
            override fun onResponse(call: Call<List<Libro>>, response: Response<List<Libro>>) {
                if(response.isSuccessful){
                    var data=response.body()
                    var adaptador= LibroHomeAdapter(data!!)
                    rvLibroMenu.adapter=adaptador
                    rvLibroMenu.layoutManager=LinearLayoutManager(appConfig.CONTEXTO,LinearLayoutManager.HORIZONTAL,false)

                }
            }
            override fun onFailure(call: Call<List<Libro>>, t: Throwable) {
                showAlert(t.localizedMessage)

            }
        })
    }

    fun cargardatosusu(){
        tvUserID.setText(UserSessionData.userID.toString())
        tvUserNom.setText(UserSessionData.nombres)
        tvUserApe.setText(UserSessionData.apellidos)
        tvUserEmail.setText(UserSessionData.email)
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