package com.argus.proyectofinaldami.services

import com.argus.proyectofinaldami.entidad.User
import com.argus.proyectofinaldami.entidad.UserLogin
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceUser {
    @GET("User/email")
    fun findByEmail(@Query("email") email: String?): Call<User>

    @POST("User/register")
    fun register(@Body model: User): Call<User>
    @POST("User/login")
    fun login(@Body model: UserLogin): Call<User>
}