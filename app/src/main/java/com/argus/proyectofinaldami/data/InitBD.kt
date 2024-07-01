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
        db.execSQL("insert into tb_autor values(null,'Edgar Allan Poe')")
        db.execSQL("insert into tb_autor values(null,'Paulo Coelho')")
        db.execSQL("insert into tb_autor values(null,'William Shakespeare')")
        db.execSQL("insert into tb_autor values(null,'Oscar Wilde')")
        db.execSQL("insert into tb_autor values(null,'Fyodor Dostoevsky')")
        db.execSQL("insert into tb_autor values(null,'William Faulkner')")
        db.execSQL("insert into tb_autor values(null,'Franz Kafka')")
        db.execSQL("insert into tb_autor values(null,'James Joyce')")
        db.execSQL("insert into tb_autor values(null,'Oscar Wilde')")
        db.execSQL("insert into tb_autor values(null,'George Orwell')")
        db.execSQL("insert into tb_autor values(null,'Fiódor Dostoievski')")
        db.execSQL("insert into tb_autor values(null,'Charles Dickens')")
        db.execSQL("insert into tb_autor values(null,'Mary Shelley')")
        db.execSQL("insert into tb_autor values(null,'Stephen King')")
        db.execSQL("insert into tb_autor values(null,'J.R.R. Tolkien')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}