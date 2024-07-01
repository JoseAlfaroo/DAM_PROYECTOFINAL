package com.argus.proyectofinaldami.controller

import android.content.ContentValues
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
        val SQL = "select * from tb_autor where nomautor like ?"
        val RS = CN.rawQuery(SQL, arrayOf("%$name%"))

        while (RS.moveToNext()) {
            val bean = Autor(RS.getInt(0), RS.getString(1))
            lista.add(bean)
        }
        return lista
    }

    fun save(bean:Autor):Int{
        var estado=-1
        var CN=appConfig.BD.writableDatabase
        var conte= ContentValues()
        conte.put("nomautor",bean.nombre_autor)
        estado=CN.insert("tb_autor","codautor",conte).toInt()
        return estado;
    }

    fun findById(codigo_autor:Int): Autor? {
        var bean:Autor? = null
        var CN=appConfig.BD.readableDatabase
        var SQL="select * from tb_autor where codautor=?"
        var RS=CN.rawQuery(SQL, arrayOf(codigo_autor.toString()))
        if (RS.moveToNext()){
            bean=Autor(RS.getInt(0),RS.getString(1))
        }
        return bean;
    }

    fun update(bean:Autor):Int{
        var estado=-1
        var CN=appConfig.BD.writableDatabase
        var conte= ContentValues()
        conte.put("nomautor",bean.nombre_autor)
        estado=CN.update("tb_autor",conte,"codautor=?", arrayOf(bean.codigo_autor.toString()))

        return estado;
    }

    fun delete(codigo_autor: Int):Int{
        var estado=-1
        var CN=appConfig.BD.writableDatabase
        estado=CN.delete("tb_autor","codautor=?", arrayOf(codigo_autor.toString()))
        return estado;
    }
}