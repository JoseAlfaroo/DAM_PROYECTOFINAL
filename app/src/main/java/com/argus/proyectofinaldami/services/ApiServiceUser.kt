package com.argus.proyectofinaldami.services

import com.argus.proyectofinaldami.entidad.User
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceUser {
    @GET("User/email")
    fun findByEmail(@Query("email") email: String?): Call<User>

    @POST("User/register")
    fun register(@Body model: User): Call<User>
}