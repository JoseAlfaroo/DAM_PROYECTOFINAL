package com.argus.proyectofinaldami.adaptador

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.AutorUpdateActivity
import com.argus.proyectofinaldami.R
import com.argus.proyectofinaldami.entidad.Autor
import com.argus.proyectofinaldami.utils.appConfig

class AutorAdapter(var lista:ArrayList<Autor>):RecyclerView.Adapter<ViewAutor>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAutor {
        var info= LayoutInflater.from(parent.context).inflate(R.layout.item_autor,parent,false)
        return ViewAutor(info)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewAutor, position: Int) {
        holder.tvCodigoAutor.setText(lista.get(position).codigo_autor.toString() + " - " + lista.get(position).nombre_autor)


        holder.itemView.setOnClickListener{
            var intent=Intent(appConfig.CONTEXTO,AutorUpdateActivity::class.java)
            intent.putExtra("codigo_autor",lista.get(position).codigo_autor)

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            ContextCompat.startActivity(appConfig.CONTEXTO,intent,null)
        }
    }
}