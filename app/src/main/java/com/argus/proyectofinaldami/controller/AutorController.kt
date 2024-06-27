package com.argus.proyectofinaldami.controller

import com.argus.proyectofinaldami.entidad.Autor
import com.argus.proyectofinaldami.utils.appConfig

class AutorController {
    fun findAll():ArrayList<Autor>{
        var lista=ArrayList<Autor>();
        var CN=appConfig.BD.readableDatabase
        var SQL="select * from tb_autor"

        var RS=CN.rawQuery(SQL,null)
        while (RS.moveToNext()){
            var bean=Autor(RS.getInt(0),RS.getString(1))
            lista.add(bean)
        }
        return lista
    }
    fun findByName(name: String): ArrayList<Autor> {
        val lista = ArrayList<Autor>()
        val CN = appConfig.BD.readableDatabase
        val SQL = "SELECT * FROM tb_autor WHERE nomautor LIKE ?"
        val RS = CN.rawQuery(SQL, arrayOf("%$name%"))

        while (RS.moveToNext()) {
            val bean = Autor(RS.getInt(0), RS.getString(1))
            lista.add(bean)
        }
        return lista
    }
}