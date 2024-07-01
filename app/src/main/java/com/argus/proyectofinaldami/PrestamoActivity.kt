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
import com.argus.proyectofinaldami.adaptador.DetallePrestamoTemporalAdapter
import com.argus.proyectofinaldami.entidad.DetallePrestamo
import com.argus.proyectofinaldami.entidad.DetallesPrestamoTemporal
import com.argus.proyectofinaldami.entidad.DetallesPrestamoTemporal.registrarPrestamo
import com.argus.proyectofinaldami.entidad.Prestamo
import com.argus.proyectofinaldami.services.ApiServiceLibro
import com.argus.proyectofinaldami.services.ApiServicePrestamo
import com.argus.proyectofinaldami.utils.ApiUtils
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrestamoActivity : AppCompatActivity() {
    private lateinit var btnHome: LinearLayout
    private lateinit var btnLibro: LinearLayout
    private lateinit var btnAutor: LinearLayout
    private lateinit var btnPrestamo: LinearLayout
    private lateinit var btnPerfil: LinearLayout

    private lateinit var btnRegistrarPrestamo: Button

    private lateinit var apiLibro: ApiServiceLibro
    private lateinit var apiDetaPres: ApiServicePrestamo

    private lateinit var adapter: DetallePrestamoTemporalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_prestamo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.prestamo)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnHome = findViewById(R.id.btnHomeMenu)
        btnLibro = findViewById(R.id.btnLibroMenu)
        btnAutor = findViewById(R.id.btnAutorMenu)
        btnPrestamo = findViewById(R.id.btnPrestamoMenu)
        btnPerfil = findViewById(R.id.btnPerfilMenu)

        apiDetaPres = ApiUtils.getPrestamoAPIServiceTLC()

        btnRegistrarPrestamo = findViewById(R.id.btnRegistrarPrestamo)


        btnRegistrarPrestamo.setOnClickListener { registrarPrestamo() }
        btnHome.setOnClickListener { irHome() }
        btnLibro.setOnClickListener { irLibro() }
        btnAutor.setOnClickListener { irAutor() }
        btnPrestamo.setOnClickListener { irPrestamo() }
        btnPerfil.setOnClickListener { irPerfil() }

        // Configuracion del rv
        val rvPrestamos = findViewById<RecyclerView>(R.id.recyclerViewPrestamos)
        rvPrestamos.layoutManager = LinearLayoutManager(this)
        adapter = DetallePrestamoTemporalAdapter(DetallesPrestamoTemporal.detallesPrestamo)
        rvPrestamos.adapter = adapter


    }


    private fun registrarPrestamo() {
        val prestamo = DetallesPrestamoTemporal.registrarPrestamo()

        // Convertir el objeto prestamo a JSON
        val gson = Gson()
        val json = gson.toJson(prestamo)

        Log.d("JSON_PRESTAMO_ENVIADO", json) // Imprimir el JSON enviado

        apiDetaPres.registrarPrestamo(prestamo).enqueue(object : Callback<Prestamo> {
            override fun onResponse(call: Call<Prestamo>, response: Response<Prestamo>) {
                if (response.isSuccessful) {
                    val prestamoResponse = response.body()
                    Log.d("REGISTRO PRESTAMO", "Éxito: $prestamoResponse")
                    Toast.makeText(this@PrestamoActivity, "Préstamo registrado exitosamente", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("REGISTRO PRESTAMO", "Error en la respuesta: ${response.errorBody()?.string()}")
                    Toast.makeText(this@PrestamoActivity, "Error al registrar el préstamo", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Prestamo>, t: Throwable) {
                Log.d("REGISTRO PRESTAMO", "Fallo en la conexión: ${t.message}")
                Toast.makeText(this@PrestamoActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun agregarDetallePrestamo(libroID: Int) {
        DetallesPrestamoTemporal.agregarDetalle(libroID)
        adapter.actualizarDatos(DetallesPrestamoTemporal.detallesPrestamo)
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
        // val intent = Intent(this, PerfilActivity::class.java)
        // startActivity(intent)
    }
}