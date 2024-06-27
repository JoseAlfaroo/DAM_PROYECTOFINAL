package com.argus.proyectofinaldami.adaptador

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.R

class ViewAutor(item:View):RecyclerView.ViewHolder(item) {
    var tvCodigoAutor:TextView
    var tvNombreAutor:TextView

    init {
        tvCodigoAutor=item.findViewById(R.id.tvCodigoAutor)
        tvNombreAutor=item.findViewById(R.id.tvNombreAutor)
    }
}