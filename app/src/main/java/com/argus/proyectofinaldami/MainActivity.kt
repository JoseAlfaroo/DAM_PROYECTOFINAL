package com.argus.proyectofinaldami

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.textfield.TextInputEditText
import java.util.Arrays


class MainActivity : AppCompatActivity() {

    //Elementos
    private lateinit var txtEmail:TextInputEditText
    private lateinit var txtPassword:TextInputEditText
    private lateinit var LoginButton:Button
    private lateinit var RegisterButton:Button

    private lateinit var FBLoginBtn:LoginButton
    private lateinit var callbackManager : CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        callbackManager = CallbackManager.Factory.create()

        val facebookCallback = object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val accessToken = loginResult.accessToken
                // Manejar el éxito del inicio de sesión aquí
            }

            override fun onCancel() {
                // Manejar cancelación del inicio de sesión aquí
            }

            override fun onError(exception: FacebookException) {
                // Manejar errores aquí
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Referencias (Uff referencia)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        LoginButton = findViewById(R.id.LoginButton)
        RegisterButton = findViewById(R.id.RegisterButton)
        FBLoginBtn = findViewById(R.id.FBloginBtn)

        LoginButton.setOnClickListener{ login() }
        RegisterButton.setOnClickListener{ register() }
        FBLoginBtn.setOnClickListener{ fblogin() }

    }

    private fun fblogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun login() {
        Toast.makeText(this, "Probando botón iniciar sesión", Toast.LENGTH_SHORT).show()
    }

    private fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}