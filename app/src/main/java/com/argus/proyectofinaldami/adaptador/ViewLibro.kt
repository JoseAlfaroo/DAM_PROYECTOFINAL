package com.argus.proyectofinaldami.adaptador

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.R

class ViewLibro(item: View): RecyclerView.ViewHolder(item) {
    var tvCodigoLibro:TextView
    var tvTituloLibro:TextView
    var imgLibroItem:ImageView
    var tvAutorLibro:TextView
    var tvGeneroLibro:TextView

    init {
        tvCodigoLibro=itemView.findViewById(R.id.tvCodigoLibro)
        tvTituloLibro=itemView.findViewById(R.id.tvTituloLibro)
        imgLibroItem=itemView.findViewById(R.id.imgLibroItem)
        tvAutorLibro=itemView.findViewById(R.id.tvAutorLibro)
        tvGeneroLibro=itemView.findViewById(R.id.tvGeneroLibro)
    }
}