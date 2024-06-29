package com.argus.proyectofinaldami

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.argus.proyectofinaldami.entidad.User
import com.argus.proyectofinaldami.services.ApiServiceUser
import com.argus.proyectofinaldami.utils.ApiUtils
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    //Elementos
    private lateinit var txtName: TextInputEditText
    private lateinit var txtLastName: TextInputEditText
    private lateinit var txtPassword: TextInputEditText
    private lateinit var txtEmail:TextInputEditText
    private lateinit var txtConfirmPassword: TextInputEditText
    private lateinit var RegisterButton: Button
    private lateinit var LoginInsteadButton: Button
    private lateinit var apiUser: ApiServiceUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Referencias (No voy a repetir eso)
        apiUser= ApiUtils.getUserAPIServiceTLC()
        txtName = findViewById(R.id.txtName)
        txtLastName = findViewById(R.id.txtLastName)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword)
        RegisterButton = findViewById(R.id.RegisterButton)
        LoginInsteadButton = findViewById(R.id.LoginInsteadButton)

        RegisterButton.setOnClickListener{ register() }
        LoginInsteadButton.setOnClickListener{ login() }
    }

    private fun register() {


        if (txtName.text.toString().isEmpty() || txtName.text.toString().length < 3) {
            return Toast.makeText(this, "Nombres a partir de 3 caracteres.", Toast.LENGTH_SHORT).show()
        }
        if (txtLastName.text.toString().isEmpty() || txtLastName.text.toString().length < 3) {
            return Toast.makeText(this, "Apellidos a partir de 3 caracteres.", Toast.LENGTH_SHORT).show()
        }
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+(\\.[a-z]+)+"
        if (txtEmail.text.toString().isEmpty() || !txtEmail.text.toString().matches(emailPattern.toRegex())) {
            return Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show()
        }
        if (txtPassword.text.toString().isEmpty() || txtPassword.text.toString().length < 8 || txtPassword.text.toString().length > 12) {
            return Toast.makeText(this, "Contraseña debe tener 8-12 caracteres", Toast.LENGTH_SHORT).show()
        }
        if (txtConfirmPassword.text.toString().isEmpty()) {
            return Toast.makeText(this, "Confirme su contraseña", Toast.LENGTH_SHORT).show()
        }
        if (txtPassword.text.toString() != txtConfirmPassword.text.toString()) {
            return Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }

        Log.d("USUARIO", "Datos validados")
        email()


    }


private fun email(){
    apiUser.findByEmail(txtEmail.text.toString().trim()).enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            if (response.isSuccessful) {
                val user = response.body()

                Log.d("USUARIO", "No se registro usuario, ya existe ID: ${user!!.userID}")
                return Toast.makeText(this@RegisterActivity, "Este correo ya se encuentra registrado", Toast.LENGTH_SHORT).show()

            } else {

                Log.d("USUARIO", "Usuario registrandose")

                // Si to anda bn
                val user = User(
                    userID = 0,
                    nombres = txtName.text.toString().replaceFirstChar { it.uppercase() },
                    apellidos = txtLastName.text.toString().replaceFirstChar { it.uppercase() },
                    email = txtEmail.text.toString(),
                    password = txtPassword.text.toString()
                )

                val call = apiUser.register(user)
                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            Log.d("USUARIO", "Usuario registrado")
                            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                            return Toast.makeText(this@RegisterActivity, "Registro exitoso, inicie sesión", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("USUARIO", "Registro fallido")
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.d("USUARIO", "Registro fallido")
                    }
                })
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Log.d("USUARIO", "Error de petición")
        }
    })
}


    private fun login() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}