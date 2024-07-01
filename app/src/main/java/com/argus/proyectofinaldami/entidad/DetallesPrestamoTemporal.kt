package com.argus.proyectofinaldami.entidad

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DetallesPrestamoTemporal {
    val detallesPrestamo: MutableList<DetallePrestamo> = mutableListOf()
    var userID: Int = UserSessionData.userID!!

    val fechaPrestamo:String
        get() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())

    fun agregarDetalle(libroID:Int){
        var detallePrestamo = DetallePrestamo(libroID = libroID)

        //Aqui se agrega un detalle
        detallesPrestamo.add(detallePrestamo)
        Log.d("DETALLES OBJETO LISTA", "Se agregó LIBRO DE ID " + libroID)
    }

    fun limpiarDetalles(){
        detallesPrestamo.clear()
    }

    fun nroElementos(): Int {
        return detallesPrestamo.size
    }

    fun registrarPrestamo():Prestamo{

        return Prestamo(
            userID = userID,
            fechaPrestamo = fechaPrestamo,
            detallesPrestamos = detallesPrestamo
        )



    }
}