package com.argus.proyectofinaldami.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.R
import com.argus.proyectofinaldami.entidad.DetallePrestamo

class DetallePrestamoTemporalAdapter(private var detallesPrestamo: List<DetallePrestamo>) :
    RecyclerView.Adapter<DetallePrestamoTemporalAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDetallePrestamoID: TextView = view.findViewById(R.id.tvDetallePrestamoID)
        val tvPrestamoID: TextView = view.findViewById(R.id.tvPrestamoID)
        val tvLibroID: TextView = view.findViewById(R.id.tvLibroID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detalle_prestamo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detalle = detallesPrestamo[position]
        holder.tvDetallePrestamoID.text = "Detalle Prestamo ID: ${detalle.detallePrestamoID}"
        holder.tvPrestamoID.text = "Prestamo ID: ${detalle.prestamoID}"
        holder.tvLibroID.text = "Libro ID: ${detalle.libroID}"
    }

    override fun getItemCount() = detallesPrestamo.size

    fun actualizarDatos(nuevosDetalles: List<DetallePrestamo>) {
        this.detallesPrestamo = nuevosDetalles
        notifyDataSetChanged()
    }
}