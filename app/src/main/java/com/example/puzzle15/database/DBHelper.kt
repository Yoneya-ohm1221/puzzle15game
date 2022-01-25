package com.example.puzzle15.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class DBHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        val DATABASE_VERSION = 2
        val DATABASE_NAME = "Database"

        //Table Phone adding
        val TABLE_History = "history"
        val History_ID = "id"
        val History_TIME = "time"
        val History_DATE = "date"

        val Table_PLAYER = "player"
        val PLAYER_id ="id"
        val PLAYER_name = "name"


    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_History + "("
                + History_ID + " INTEGER PRIMARY KEY," + History_TIME + " TEXT,"
                + History_DATE + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)

        val CREATE_PLAYER_TABLE = ("CREATE TABLE " + Table_PLAYER + "("
                + PLAYER_id + " INTEGER PRIMARY KEY," + PLAYER_name + " TEXT DEFAULT 'Player1')")
        db?.execSQL(CREATE_PLAYER_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_History)
        db!!.execSQL("DROP TABLE IF EXISTS " + Table_PLAYER)
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
        var sql ="SELECT * FROM "+ TABLE_History +" ORDER BY id DESC"
        return  db.rawQuery(sql,null)
    }

    fun getCurrentDate():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return sdf.format(Date())
    }
    //
    @SuppressLint("Range")
    fun getPlayerName():String{
        var name =""
        val db= this.readableDatabase
        var sql ="SELECT name FROM "+ Table_PLAYER +" ORDER BY id DESC LIMIT 1"
        var data = db.rawQuery(sql,null)
           while (data.moveToNext()){
               name = data.getString(data.getColumnIndex("name"))
           }

        return name
    }

    fun addplayer(name: String){
        val values = ContentValues()
        values.put(PLAYER_name, name)
        val db = this.writableDatabase
        db.insert(Table_PLAYER, null, values)
        db.close()
    }
    fun updatePlayerName(name:String){
        val values = ContentValues()
        values.put(PLAYER_name, name)
        val db = this.writableDatabase
      //  val whereclause = "$PLAYER_name=?"
       // val whereargs = arrayOf(name)
        db.update(Table_PLAYER,values,null,null)
        db.close()
    }

}