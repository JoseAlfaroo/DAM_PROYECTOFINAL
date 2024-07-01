package com.argus.proyectofinaldami.adaptador

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.argus.proyectofinaldami.R

class ViewGenero(item:View):RecyclerView.ViewHolder(item) {
    var tvCodigoGenero:TextView
    var tvNombreGenero:TextView

    init {
        tvCodigoGenero=item.findViewById(R.id.tvCodigoGenero)
        tvNombreGenero=item.findViewById(R.id.tvNombreGenero)
    }
}