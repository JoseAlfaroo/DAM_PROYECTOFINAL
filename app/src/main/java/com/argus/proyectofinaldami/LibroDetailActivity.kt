package com.argus.proyectofinaldami

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
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
import java.io.ByteArrayOutputStream

class LibroDetailActivity:AppCompatActivity() {

    private lateinit var tvTituloLibroDetail:TextView
    private lateinit var tvAutorLibroDetail: TextView
    private lateinit var tvGeneroLibroDetail: TextView
    private lateinit var tvDescripcionLibroDetail:TextView
    private lateinit var imgPortadaDetail: ImageView

    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout
    private lateinit var apiLibro: ApiServiceLibro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_libro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.libro_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvTituloLibroDetail=findViewById(R.id.tvTituloLibroDetail)
        tvAutorLibroDetail=findViewById(R.id.tvAutorLibroDetail)
        tvGeneroLibroDetail=findViewById(R.id.tvGeneroLibroDetail)
        tvDescripcionLibroDetail=findViewById(R.id.tvDescripcionLibroDetail)
        imgPortadaDetail=findViewById(R.id.imgPortadaDetail)

        apiLibro=ApiUtils.getAPIServiceTLC()
        datos()

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
                    tvTituloLibroDetail.setText(obj.titulo)
                    tvAutorLibroDetail.setText(obj.autor)
                    tvGeneroLibroDetail.setText(obj.genero)
                    tvDescripcionLibroDetail.setText(obj.descripcion)

                    val decodedString = Base64.decode(obj.imagen, Base64.DEFAULT)
                    val byteArray = decodedString
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    imgPortadaDetail.setImageBitmap(bitmap)
                }
            }

            override fun onFailure(call: Call<Libro>, t: Throwable) {
                showAlert(t.localizedMessage)
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent= Intent(this,LibroActivity::class.java)
        startActivity(intent)
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
        var intent=Intent(this,PerfilActivity::class.java)
        startActivity(intent)
    }
}