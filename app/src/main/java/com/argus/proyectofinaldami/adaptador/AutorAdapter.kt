package com.argus.proyectofinaldami.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.R
import com.argus.proyectofinaldami.entidad.Autor

class AutorAdapter(var lista:ArrayList<Autor>):RecyclerView.Adapter<ViewAutor>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAutor {
        var info= LayoutInflater.from(parent.context).inflate(R.layout.item_autor,parent,false)
        return ViewAutor(info)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewAutor, position: Int) {
        holder.tvCodigoAutor.setText(lista.get(position).codigo_autor.toString())
        holder.tvNombreAutor.setText(lista.get(position).nombre_autor)
    }
}