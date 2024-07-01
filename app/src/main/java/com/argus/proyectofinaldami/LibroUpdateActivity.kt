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

class LibroUpdateActivity:AppCompatActivity() {

    private lateinit var txtCodigoLibroUpdate:TextInputEditText
    private lateinit var txtTituloLibroUpdate:TextInputEditText
    private lateinit var spnAutorLibroUpdate: AutoCompleteTextView
    private lateinit var spnGeneroLibroUpdate: AutoCompleteTextView
    private lateinit var txtDescripcionLibroUpdate:TextInputEditText
    private lateinit var btnSeleccionarPortadaUpdate:Button
    private lateinit var imgPortadaUpdate: ImageView
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
        btnSeleccionarPortadaUpdate=findViewById(R.id.btnSeleccionarPortadaUpdate)
        imgPortadaUpdate=findViewById(R.id.imgPortadaUpdate)

        btnUpdateLibro=findViewById(R.id.btnUpdateLibro)
        btnDeleteLibro=findViewById(R.id.btnDeleteLibro)
        btnUpdateLibro.setOnClickListener { update_libro() }
        btnDeleteLibro.setOnClickListener { delete_libro() }
        apiLibro=ApiUtils.getAPIServiceTLC()
        datos()
        cargarautores()

        val galleryImage=registerForActivityResult( ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imgPortadaUpdate.setImageURI(it)
            })

        btnSeleccionarPortadaUpdate.setOnClickListener { galleryImage.launch("image/*") }

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

                    val decodedString = Base64.decode(obj.imagen, Base64.DEFAULT)
                    val byteArray = decodedString
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    imgPortadaUpdate.setImageBitmap(bitmap)
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
        val bitmap = (imgPortadaUpdate.drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        var id=txtCodigoLibroUpdate.text.toString().toInt()
        var tit=txtTituloLibroUpdate.text.toString()
        var aut=spnAutorLibroUpdate.text.toString()
        var gen=spnGeneroLibroUpdate.text.toString()
        var des=txtDescripcionLibroUpdate.text.toString()
        var img=Base64.encodeToString(byteArray, Base64.DEFAULT)
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

    override fun onBackPressed() {
        super.onBackPressed()
        var intent= Intent(this,LibroActivity::class.java)
        startActivity(intent)
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
        var intent=Intent(this,PerfilActivity::class.java)
        startActivity(intent)
    }
}