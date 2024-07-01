package com.argus.proyectofinaldami

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.adaptador.HistorialPrestamoAdapter
import com.argus.proyectofinaldami.entidad.Prestamo
import com.argus.proyectofinaldami.entidad.UserSessionData
import com.argus.proyectofinaldami.services.ApiServicePrestamo
import com.argus.proyectofinaldami.utils.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistorialPrestamoActivity : AppCompatActivity() {
    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout
    private lateinit var btnVolverDetallesPrestamos: Button

    private lateinit var recyclerViewHistorialPrestamos: RecyclerView
    private lateinit var apiServicePrestamo: ApiServicePrestamo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historialprestamo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.historialprestamo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnVolverDetallesPrestamos = findViewById(R.id.btnVolverDetallesPrestamos)

        btnHome = findViewById(R.id.btnHomeMenu)
        btnLibro = findViewById(R.id.btnLibroMenu)
        btnAutor = findViewById(R.id.btnAutorMenu)
        btnPrestamo = findViewById(R.id.btnPrestamoMenu)
        btnPerfil = findViewById(R.id.btnPerfilMenu)

        apiServicePrestamo = ApiUtils.getPrestamoAPIServiceTLC()

        recyclerViewHistorialPrestamos = findViewById(R.id.recyclerViewHistorialPrestamos)
        recyclerViewHistorialPrestamos.layoutManager = LinearLayoutManager(this)

        obtenerHistorialPrestamos(UserSessionData.userID!!)

        btnVolverDetallesPrestamos.setOnClickListener { irPrestamo() }

        btnHome.setOnClickListener { irHome() }
        btnLibro.setOnClickListener { irLibro() }
        btnAutor.setOnClickListener { irAutor() }
        btnPrestamo.setOnClickListener { irPrestamo() }
        btnPerfil.setOnClickListener { irPerfil() }
    }

    private fun obtenerHistorialPrestamos(userId: Int) {
        apiServicePrestamo.obtenerPrestamosPorUsuario(userId).enqueue(object : Callback<List<Prestamo>> {
            override fun onResponse(call: Call<List<Prestamo>>, response: Response<List<Prestamo>>) {
                if (response.isSuccessful) {
                    val prestamos = response.body()
                    if (prestamos != null) {
                        val adapter = HistorialPrestamoAdapter(prestamos)
                        recyclerViewHistorialPrestamos.adapter = adapter
                    } else {
                        Toast.makeText(this@HistorialPrestamoActivity, "No se encontraron préstamos.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("API Error", "Error al obtener los datos: ${response.errorBody()?.string()}")
                    Toast.makeText(this@HistorialPrestamoActivity, "Error al obtener los datos.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Prestamo>>, t: Throwable) {
                Log.e("Network Error", "Error de red: ${t.message}")
                Toast.makeText(this@HistorialPrestamoActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun irHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun irLibro() {
        val intent = Intent(this, LibroActivity::class.java)
        startActivity(intent)
    }

    private fun irAutor() {
        val intent = Intent(this, AutorActivity::class.java)
        startActivity(intent)
    }

    private fun irPrestamo() {
        val intent = Intent(this, PrestamoActivity::class.java)
        startActivity(intent)
    }

    private fun irPerfil() {
        val intent = Intent(this, PerfilActivity::class.java)
        startActivity(intent)
    }
}