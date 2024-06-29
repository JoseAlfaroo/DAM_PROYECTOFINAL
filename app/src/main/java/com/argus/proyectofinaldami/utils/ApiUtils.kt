package com.argus.proyectofinaldami.utils

import com.argus.proyectofinaldami.services.ApiServiceLibro
import com.argus.proyectofinaldami.services.ApiServiceUser

class ApiUtils {

    companion object {
        val BASE_URL="http://argus.somee.com/api/"
        fun getAPIServiceTLC(): ApiServiceLibro {
            return RetrofitClient.getClient(BASE_URL).create(ApiServiceLibro::class.java)
        }

        fun getUserAPIServiceTLC(): ApiServiceUser {
            return RetrofitClient.getClient(BASE_URL).create(ApiServiceUser::class.java)
        }
    }
}