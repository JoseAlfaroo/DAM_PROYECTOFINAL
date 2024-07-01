package com.argus.proyectofinaldami.adaptador

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.AutorUpdateActivity
import com.argus.proyectofinaldami.GeneroUpdateActivity
import com.argus.proyectofinaldami.R
import com.argus.proyectofinaldami.entidad.Autor
import com.argus.proyectofinaldami.entidad.Genero
import com.argus.proyectofinaldami.utils.appConfig

class GeneroAdapter(var lista:ArrayList<Genero>):RecyclerView.Adapter<ViewGenero>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewGenero {
        var info= LayoutInflater.from(parent.context).inflate(R.layout.item_genero,parent,false)
        return ViewGenero(info)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewGenero, position: Int) {
        holder.tvCodigoGenero.setText(lista.get(position).codigo_genero + " - " + lista.get(position).nombre_genero)



        holder.itemView.setOnClickListener{
            var intent=Intent(appConfig.CONTEXTO,GeneroUpdateActivity::class.java)
            intent.putExtra("codigo_genero",lista.get(position).codigo_genero)

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            ContextCompat.startActivity(appConfig.CONTEXTO,intent,null)
        }


    }
}