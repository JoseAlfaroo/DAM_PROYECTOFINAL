package com.argus.proyectofinaldami.services

import com.argus.proyectofinaldami.entidad.Libro
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceLibro {

    @GET("Libro")
    fun findLibros(@Query("buscar") buscar: String?): Call<List<Libro>>

    @GET("Libro/{id}")
    fun findLibroById(@Path("id") id: Int): Call<Libro>

    @POST("Libro")
    fun saveLibro(@Body libro: Libro): Call<Libro>

    @PUT("Libro/{id}")
    fun updateLibro(@Path("id") id: Int, @Body libro: Libro): Call<Libro>

    @DELETE("Libro/{id}")
    fun deleteLibro(@Path("id") id: Int): Call<Void>

}