package com.argus.proyectofinaldami.adaptador

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.LibroDetailActivity
import com.argus.proyectofinaldami.LibroUpdateActivity
import com.argus.proyectofinaldami.R
import com.argus.proyectofinaldami.entidad.Libro
import com.argus.proyectofinaldami.entidad.Prestamo
import com.argus.proyectofinaldami.utils.appConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistorialPrestamoAdapter(private val prestamos: List<Prestamo>):RecyclerView.Adapter<HistorialPrestamoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val prestamoId: TextView = view.findViewById(R.id.prestamoId)
        val fechaPrestamo: TextView = view.findViewById(R.id.fechaPrestamo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prestamo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prestamo = prestamos[position]
        holder.prestamoId.text = "#" + prestamo.prestamoID.toString()
        holder.fechaPrestamo.text = formatDate(prestamo.fechaPrestamo!!)
    }

    override fun getItemCount(): Int = prestamos.size

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(dateString: String): String? {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy, hh:mm a", Locale("es", "ES"))
            val date: Date? = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) }
        } catch (e: Exception) {
            dateString // Si falla devuelve el valor bruto del json
        }
    }
}