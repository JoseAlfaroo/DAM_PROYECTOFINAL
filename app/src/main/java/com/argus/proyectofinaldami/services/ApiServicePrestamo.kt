package com.argus.proyectofinaldami.services

import com.argus.proyectofinaldami.entidad.Prestamo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiServicePrestamo {
    @POST("Prestamo")
    fun registrarPrestamo(@Body prestamo: Prestamo): Call<Prestamo>
}