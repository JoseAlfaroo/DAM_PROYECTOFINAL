package com.argus.proyectofinaldami

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.argus.proyectofinaldami.entidad.User
import com.argus.proyectofinaldami.entidad.UserSessionData
import com.argus.proyectofinaldami.services.ApiServiceUser
import com.argus.proyectofinaldami.utils.ApiUtils
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Arrays

class PerfilActivity:AppCompatActivity() {
    private lateinit var tvNombreUsuario:TextView
    private lateinit var tvApellidoUsuario:TextView
    private lateinit var tvEmailUsuario:TextView
    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout
    private lateinit var btnCerrarSesion:Button
    private lateinit var apiUser: ApiServiceUser
    var callbackManager: CallbackManager? = null
    private lateinit var FBLoginBtn: LoginButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.perfil)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //FBLoginBtnPerfil.setOnClickListener{ fblogin() }
        apiUser= ApiUtils.getUserAPIServiceTLC()
        tvNombreUsuario=findViewById(R.id.tvNombreUsuario)
        tvApellidoUsuario=findViewById(R.id.tvApellidoUsuario)
        tvEmailUsuario=findViewById(R.id.tvEmailUsuario)
        cargarusuario()

        btnHome=findViewById(R.id.btnHomeMenu)
        btnLibro=findViewById(R.id.btnLibroMenu)
        btnAutor=findViewById(R.id.btnAutorMenu)
        btnPrestamo=findViewById(R.id.btnPrestamoMenu)
        btnPerfil=findViewById(R.id.btnPerfilMenu)


        btnCerrarSesion=findViewById(R.id.btnCerrarSesion)
        btnCerrarSesion.setOnClickListener { cerrarsesion() }
        btnHome.setOnClickListener { irhome() }
        btnLibro.setOnClickListener { irlibro() }
        btnAutor.setOnClickListener { irautor() }
        btnPrestamo.setOnClickListener { irprestamo() }
        btnPerfil.setOnClickListener { irperfil() }
    }

    fun cargarusuario(){
        tvNombreUsuario.setText(UserSessionData.nombres)
        tvApellidoUsuario.setText(UserSessionData.apellidos)
        tvEmailUsuario.setText(UserSessionData.email)
    }

    fun cerrarsesion(){
        UserSessionData.userID=null
        UserSessionData.email=null
        UserSessionData.apellidos=null
        UserSessionData.nombres=null
        var intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
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