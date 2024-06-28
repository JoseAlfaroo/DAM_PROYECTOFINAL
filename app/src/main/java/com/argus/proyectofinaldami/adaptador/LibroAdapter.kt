package com.argus.proyectofinaldami.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.R
import com.argus.proyectofinaldami.entidad.Libro

class LibroAdapter(var lista:List<Libro>):RecyclerView.Adapter<ViewLibro>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewLibro {
        var vista= LayoutInflater.from(parent.context).
        inflate(R.layout.item_libro,parent,false)
        return ViewLibro(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewLibro, position: Int) {
        holder.tvCodigoLibro.setText(lista.get(position).libroID.toString())
        holder.tvTituloLibro.setText(lista.get(position).titulo)
        holder.tvAutorLibro.setText(lista.get(position).autor)
        holder.tvGeneroLibro.setText(lista.get(position).genero)

        /*
        holder.itemView.setOnClickListener{
            var intent=Intent(appConfig.CONTEXT,LibroUpdateActivity::class.java)
            intent.putExtra("libroID",lista.get(position).libroID)

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            ContextCompat.startActivity(appConfig.CONTEXT,intent,null)
        }
         */
    }
}