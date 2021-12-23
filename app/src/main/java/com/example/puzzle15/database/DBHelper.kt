package com.example.puzzle15.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class DBHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Database"

        //Table Phone adding
        val TABLE_History = "history"
        val History_ID = "id"
        val History_TIME = "time"
        val History_DATE = "date"


    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_History + "("
                + History_ID + " INTEGER PRIMARY KEY," + History_TIME + " TEXT,"
                + History_DATE + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_History)
        onCreate(db)
    }

    fun addhistory(time: String){
        val values = ContentValues()
        values.put(History_TIME, time)
        values.put(History_DATE, getCurrentDate())

        val db = this.writableDatabase
        db.insert(TABLE_History, null, values)
        db.close()
    }


    fun getAlldate():Cursor{
        val db= this.readableDatabase
        var sql ="SELECT * FROM "+ TABLE_History
        return  db.rawQuery(sql,null)
    }

    fun getCurrentDate():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return sdf.format(Date())
    }
}