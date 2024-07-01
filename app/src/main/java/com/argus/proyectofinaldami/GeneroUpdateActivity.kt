package com.argus.proyectofinaldami

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.adaptador.AutorAdapter
import com.argus.proyectofinaldami.adaptador.GeneroAdapter
import com.argus.proyectofinaldami.controller.AutorController
import com.argus.proyectofinaldami.entidad.Genero
import com.argus.proyectofinaldami.utils.appConfig
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

class GeneroUpdateActivity:AppCompatActivity() {
    private lateinit var txtCodigoGeneroUpdate: TextInputEditText
    private lateinit var txtNombreGeneroUpdate: TextInputEditText
    private lateinit var btnUpdateGenero: Button
    private lateinit var btnDeleteGenero: Button
    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout

    private lateinit var bd:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_genero)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.genero_update)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtCodigoGeneroUpdate=findViewById(R.id.txtCodigoGeneroUpdate)
        txtNombreGeneroUpdate=findViewById(R.id.txtNombreGeneroUpdate)
        btnUpdateGenero=findViewById(R.id.btnUpdateGenero)
        btnDeleteGenero=findViewById(R.id.btnDeleteGenero)
        btnUpdateGenero.setOnClickListener { update_genero() }
        btnDeleteGenero.setOnClickListener { delete_genero() }


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
        datosgenero()
    }
    fun conectar(){
        FirebaseApp.initializeApp(this)
        bd=FirebaseDatabase.getInstance().reference
    }

    fun datosgenero(){
        var info=intent.extras!!
        var cod_gen=info.getString("codigo_genero")
        var consulta=FirebaseDatabase.getInstance().getReference("genero")
        //realizar la consulta por DNI
        consulta.orderByChild("codigo_genero").equalTo(cod_gen).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //obtener el registro del alumno segun DNI
                    var row = snapshot.children.first()
                    //obtener datos del alumno que contiene "row"
                    var bean = row.getValue(Genero::class.java)
                    //mostrar valores de bean en las cajas
                    txtCodigoGeneroUpdate.setText(bean!!.codigo_genero)
                    txtNombreGeneroUpdate.setText(bean!!.nombre_genero)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showAlert(error.message)
            }

        })

    }
    fun update_genero(){
        var cod_gen=txtCodigoGeneroUpdate.text.toString()
        var nom_gen=txtNombreGeneroUpdate.text.toString()
        var ID=bd.push().key
        var bean=Genero(ID!!,nom_gen)
        if (nom_gen.isEmpty()){
            showAlert("El campo Género es obligatorio")
            return
        }
        bd.child("genero").child(cod_gen).setValue(bean).addOnCompleteListener {
            showAlert("Género actualizado", DialogInterface.OnClickListener { dialog, which ->
                irgenero()
            })
        }
    }

    fun delete_genero(){
        var cod_gen=txtCodigoGeneroUpdate.text.toString()
        showAlertConfirm("¿Seguro de eliminar el libro #"+cod_gen+"?",cod_gen)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent= Intent(this,GeneroActivity::class.java)
        startActivity(intent)
    }

    fun showAlertConfirm(men:String,cod_gen:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("The Librarian Cat")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",DialogInterface.OnClickListener{
                dialogInterface: DialogInterface, i: Int ->
            bd.child("genero").child(cod_gen).removeValue().addOnCompleteListener {
                showAlert("Género eliminado", DialogInterface.OnClickListener { _, _ ->
                    irgenero()
                })
            }
        })
        builder.setNegativeButton("Cancelar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
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

    private fun irgenero(){
        val intent = Intent(this, GeneroActivity::class.java)
        startActivity(intent)
    }

    private fun irautor() {
        val intent = Intent(this, GeneroUpdateActivity::class.java)
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