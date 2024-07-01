package com.argus.proyectofinaldami

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
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
import com.argus.proyectofinaldami.entidad.Genero
import com.argus.proyectofinaldami.entidad.Libro
import com.argus.proyectofinaldami.services.ApiServiceLibro
import com.argus.proyectofinaldami.utils.ApiUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class LibroRegisterActivity:AppCompatActivity() {
    private lateinit var txtTituloLibroRegister:TextInputEditText
    private lateinit var spnAutorLibroRegister: AutoCompleteTextView
    private lateinit var spnGeneroLibroRegister: AutoCompleteTextView
    private lateinit var txtDescripcionLibroRegister:TextInputEditText
    private lateinit var btnSeleccionarPortadaRegister:Button
    private lateinit var imgPortadaRegister:ImageView
    private lateinit var btnRegisterLibro:Button

    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout
    private lateinit var btnGenero:LinearLayout
    private lateinit var apiLibro: ApiServiceLibro

    private lateinit var bd: DatabaseReference

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
        btnSeleccionarPortadaRegister=findViewById(R.id.btnSeleccionarPortadaRegister)
        imgPortadaRegister=findViewById(R.id.imgPortadaRegister)

        btnRegisterLibro=findViewById(R.id.btnRegisterLibro)
        btnRegisterLibro.setOnClickListener { register_libro() }
        cargarautores()

        apiLibro=ApiUtils.getAPIServiceTLC()

        val galleryImage=registerForActivityResult( ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imgPortadaRegister.setImageURI(it)
            })

        btnSeleccionarPortadaRegister.setOnClickListener { galleryImage.launch("image/*") }

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

        bd = FirebaseDatabase.getInstance().reference

        conectar()
        cargarGeneros()

    }
    fun conectar(){
        FirebaseApp.initializeApp(this)
        bd=FirebaseDatabase.getInstance().reference
    }

    private fun cargarGeneros() {
        val generoList = mutableListOf<String>()
        bd.child("genero").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                generoList.clear() // Clear existing genres
                for (row in snapshot.children) {
                    val genero = row.getValue(Genero::class.java)
                    if (genero != null) {
                        generoList.add(genero.nombre_genero)
                    }
                }
                val adapter = ArrayAdapter(this@LibroRegisterActivity, android.R.layout.simple_dropdown_item_1line, generoList)
                spnGeneroLibroRegister.setAdapter(adapter)
            }

            override fun onCancelled(error: DatabaseError) {
                showAlert(error.message)
            }
        })
    }

    fun cargarautores(){
        val autores = AutorController().findAll()
        val autorNames = autores.map { it.nombre_autor }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, autorNames)
        spnAutorLibroRegister.setAdapter(adapter)
    }
    fun register_libro(){
        val bitmap = (imgPortadaRegister.drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        var tit=txtTituloLibroRegister.text.toString()
        var aut=spnAutorLibroRegister.text.toString()
        var gen=spnGeneroLibroRegister.text.toString()
        var des=txtDescripcionLibroRegister.text.toString()
        var img= Base64.encodeToString(byteArray, Base64.DEFAULT)
        var bean=Libro(0,tit,aut,gen,des,img)

        val camposFaltantes = mutableListOf<String>()

        if (tit.isEmpty()) {
            camposFaltantes.add("Título")
        }
        if (aut.isEmpty()) {
            camposFaltantes.add("Autor")
        }
        if (gen.isEmpty()) {
            camposFaltantes.add("Género")
        }
        if (des.isEmpty()) {
            camposFaltantes.add("Descripción")
        }

        if (camposFaltantes.isNotEmpty()) {
            val mensaje = "Faltan los siguientes campos por llenar:\n${camposFaltantes.joinToString(", ")}"
            showAlert(mensaje)
            return
        }

        val autores = AutorController().findAll()
        val autorNames = autores.map { it.nombre_autor }
        if (!autorNames.contains(aut)) {
            showAlert("El autor seleccionado no es válido.")
            return
        }

        if (des.length > 500) {
            showAlert("La descripción del libro no puede tener más de 500 caracteres.")
            return
        }



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

    override fun onBackPressed() {
        super.onBackPressed()
        var intent= Intent(this,LibroActivity::class.java)
        startActivity(intent)
    }

    fun showAlert(men:String, listener: DialogInterface.OnClickListener? = null){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("The Librarian Cat")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",listener)
        val dialog: AlertDialog=builder.create()
        dialog.show()
    }

    private fun irhome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
    private fun irgenero(){
        val intent = Intent(this, GeneroActivity::class.java)
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