package com.argus.proyectofinaldami.utils

import com.argus.proyectofinaldami.services.ApiServiceLibro

class ApiUtils {

    companion object {
        val BASE_URL="http://argus.somee.com/api/"
        fun getAPIServiceTLC(): ApiServiceLibro {
            return RetrofitClient.getClient(BASE_URL).create(ApiServiceLibro::class.java)
        }
    }
}