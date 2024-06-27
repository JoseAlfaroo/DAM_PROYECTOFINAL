package com.argus.proyectofinaldami.data

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.argus.proyectofinaldami.utils.appConfig

class InitBD:SQLiteOpenHelper(appConfig.CONTEXTO,appConfig.BD_NAME,null,appConfig.VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table tb_autor(" +
                "codautor integer primary key autoincrement,"+
                "nomautor varchar(50))")

        db.execSQL("insert into tb_autor values(null,'Gabriel García Marquez')")
        db.execSQL("insert into tb_autor values(null,'Mario Vargas Llosa')")
        db.execSQL("insert into tb_autor values(null,'César Vallejo')")
        db.execSQL("insert into tb_autor values(null,'Eiichiro Oda')")
        db.execSQL("insert into tb_autor values(null,'Tatsuki Fujimoto')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}