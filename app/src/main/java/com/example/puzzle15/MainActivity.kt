package com.example.puzzle15

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var a1:Button?=null
    var a2:Button?=null
    var a3:Button?=null
    var a4:Button?=null

    var b1:Button?=null
    var b2:Button?=null
    var b3:Button?=null
    var b4:Button?=null

    var c1:Button?=null
    var c2:Button?=null
    var c3:Button?=null
    var c4:Button?=null

    var d1:Button?=null
    var d2:Button?=null
    var d3:Button?=null
    var d4:Button?=null

    var game:Array<Array<Int>>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        a1 = findViewById(R.id.a1)
        a2 = findViewById(R.id.a2)
        a3 = findViewById(R.id.a3)
        a4 = findViewById(R.id.a4)

        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        b3 = findViewById(R.id.b3)
        b4 = findViewById(R.id.b4)

        c1 = findViewById(R.id.c1)
        c2 = findViewById(R.id.c2)
        c3 = findViewById(R.id.c3)
        c4 = findViewById(R.id.c4)

        d1 = findViewById(R.id.d1)
        d2 = findViewById(R.id.d2)
        d3 = findViewById(R.id.d3)
        d4 = findViewById(R.id.d4)

        game = arrayOf(
            arrayOf(0,0,0,0), // a1 a2 a3 a4
            arrayOf(0,0,0,0), // b1 b2 b3 b4
            arrayOf(0,0,0,0), // c1 c2 c3 c4
            arrayOf(0,0,0,0) // d1 d2 d3 d4
        )

    }

    fun case(b:Button,sta:Int){
        when(b){
            a1 -> game!![0][0] = sta
            a2 -> game!![0][1] = sta
            a3 -> game!![0][2] = sta
            a4 -> game!![0][3] = sta



        }

    }
}