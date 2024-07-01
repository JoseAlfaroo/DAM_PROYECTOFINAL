package com.argus.proyectofinaldami.adaptador

import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.R
import com.argus.proyectofinaldami.entidad.DetallePrestamo
import com.argus.proyectofinaldami.entidad.Libro
import com.argus.proyectofinaldami.services.ApiServiceLibro
import com.argus.proyectofinaldami.utils.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetallePrestamoTemporalAdapter(private var detallesPrestamo: List<DetallePrestamo>) :
    RecyclerView.Adapter<DetallePrestamoTemporalAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val imgLibroItem: ImageView = view.findViewById(R.id.imgLibroItem)

        val tvDetallePrestamoTitulo: TextView = view.findViewById(R.id.tvDetallePrestamoTitulo)
        val tvDetallePrestamoAutor: TextView = view.findViewById(R.id.tvDetallePrestamoAutor)

    }

    var apiLibro: ApiServiceLibro = ApiUtils.getAPIServiceTLC()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detalle_prestamo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detalle = detallesPrestamo[position]

        apiLibro.findLibroById(detalle.libroID).enqueue(object : Callback<Libro> {
            override fun onResponse(call: Call<Libro>, response: Response<Libro>) {
                if (response.isSuccessful) {
                    val libro = response.body()
                    holder.tvDetallePrestamoTitulo.setText('"' + libro!!.titulo + '"')
                    holder.tvDetallePrestamoAutor.setText(libro!!.autor)
                    libro?.let {
                        val decodedString = Base64.decode(it.imagen, Base64.DEFAULT)
                        val byteArray = decodedString
                        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                        holder.imgLibroItem.setImageBitmap(bitmap)
                    }
                }
            }

            override fun onFailure(call: Call<Libro>, t: Throwable) {
                Log.d("ERR", t.localizedMessage)
            }
        })


    }


    override fun getItemCount() = detallesPrestamo.size

    fun actualizarDatos(nuevosDetalles: List<DetallePrestamo>) {
        this.detallesPrestamo = nuevosDetalles
        notifyDataSetChanged()
    }
}