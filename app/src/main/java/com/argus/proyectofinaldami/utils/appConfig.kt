package com.argus.proyectofinaldami.utils

import android.app.Application
import android.content.Context
import com.argus.proyectofinaldami.data.InitBD

class appConfig:Application() {
    companion object{
        lateinit var CONTEXTO: Context
        lateinit var BD:InitBD
        var BD_NAME="proyectifinaldami.bd"
        var VERSION=1
    }
    //PODER INSTANCIAR
    override fun onCreate(){
        super.onCreate()
        CONTEXTO=applicationContext
        BD=InitBD()
    }
}