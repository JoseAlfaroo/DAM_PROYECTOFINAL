package com.argus.proyectofinaldami

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.argus.proyectofinaldami.entidad.User
import com.argus.proyectofinaldami.services.ApiServiceUser
import com.argus.proyectofinaldami.utils.ApiUtils
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Arrays


class MainActivity : AppCompatActivity() {

    //Elementos
    private lateinit var txtEmail:TextInputEditText
    private lateinit var txtPassword:TextInputEditText
    private lateinit var LoginButton:Button
    private lateinit var RegisterButton:Button
    private lateinit var apiUser: ApiServiceUser
    var callbackManager: CallbackManager? = null
    private lateinit var FBLoginBtn:LoginButton



    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback( callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    FBLoginExitoso(loginResult)
                }

                override fun onCancel() {
                    // Manejar cancelación del inicio de sesión aquí
                }

                override fun onError(exception: FacebookException) {
                    // Manejar errores aquí
                }
            }
        )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Referencias (Uff referencia)
        apiUser= ApiUtils.getUserAPIServiceTLC()
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        LoginButton = findViewById(R.id.LoginButton)
        RegisterButton = findViewById(R.id.RegisterButton)
        FBLoginBtn = findViewById(R.id.FBloginBtn)

        LoginButton.setOnClickListener{ login() }
        RegisterButton.setOnClickListener{ register() }
        FBLoginBtn.setOnClickListener{ fblogin() }

    }



    private fun FBLoginExitoso(loginResult: LoginResult) {
        val accessToken = loginResult.accessToken
        val request = GraphRequest.newMeRequest(accessToken) { jsonObject, response ->
            try {
                val nom = jsonObject?.getString("first_name")
                val ape = jsonObject?.getString("last_name")
                val email = jsonObject?.getString("email")


                if (email != null) {
                    Log.d("FBLogin", "Email obtenido: $email")
                    Log.d("FBLogin", "Nom obtenido: $nom")
                    Log.d("FBLogin", "Ape obtenido: $ape")

                    verificarExistenciaUserEmailFB(email, nom!!, ape!!)



                } else {
                    Log.d("FBLogin", "No se pudo obtener el email del usuario.")
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "email,first_name,last_name")
        request.parameters = parameters
        request.executeAsync()
    }

    //para verificar si usu existe segun email
    private fun verificarExistenciaUser(email: String) {
        val call = apiUser.findByEmail(email)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()

                    Log.d("USUARIO", "Usuario encontrado: ${user!!.userID}")

                } else {
                    Log.d("USUARIO", "Usuario no encontrado")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("USUARIO", "Error de petición")
            }
        })
    }

    private fun verificarExistenciaUserEmailFB(email: String, nom: String, ape: String) {
        val call = apiUser.findByEmail(email)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()

                    Log.d("USUARIO-FB", "Usuario encontrado: ${user!!.userID}")

                } else {
                    Log.d("USUARIO-FB", "Usuario no encontrado")
                    registrarFBUserEnBD(email, nom, ape)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("USUARIO-FB", "Error de inicio de sesión con Facebook")
            }
        })
    }

    private fun registrarFBUserEnBD(email: String, nom: String, ape: String) {

        val user = User(
            userID = 0,
            nombres = nom,
            apellidos = ape,
            email = email,
            password = "-"
        )

        val call = apiUser.register(user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Log.d("USUARIO-FB", "Usuario registrado")

                } else {
                    Log.d("USUARIO-FB", "Registro fallido")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("USUARIO-FB", "Registro fallido")
            }
        })
    }


    private fun fblogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun login() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}

