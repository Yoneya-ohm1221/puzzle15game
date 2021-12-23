package com.example.puzzle15

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.puzzle15.database.DBHelper
import com.example.puzzle15.menu.MenuActivity

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
    var button:Array<Button>?=null
    var win :Array<Int>?=null

    var btnstart:Button?=null
    var timer: Chronometer?=null
    var btnreset:ImageView?=null
    var back:ImageView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.navigationBarColor = resources.getColor(R.color.bg)

        btnstart = findViewById(R.id.btnstart)
        timer = findViewById(R.id.stoptime)
        timer?.base = SystemClock.elapsedRealtime();
        btnreset = findViewById(R.id.reset)
        back = findViewById(R.id.back)

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

        win = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0)
        button = arrayOf(a1!!,a2!!,a3!!,a4!!,b1!!,b2!!,b3!!,b4!!,c1!!,c2!!,c3!!,c4!!,d1!!,d2!!,d3!!)

        btnreset?.setOnClickListener {
            disblebuttom(false)
            btnstart?.visibility = View.VISIBLE
            timer?.stop()
            timer?.base = SystemClock.elapsedRealtime();
            game_start()
            game_set()
        }

        btnstart?.setOnClickListener {
            disblebuttom(true)
            btnstart?.visibility = View.GONE
            window.navigationBarColor = resources.getColor(R.color.bg)
            timer?.start()

        }

        back?.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        disblebuttom(false)
        game_start()
        game_set()
        controll()
    }
    fun disblebuttom(c:Boolean){
        if(c){
            button?.forEach { it.isEnabled=true }
            d4?.isEnabled=true
        }else{
            button?.forEach { it.isEnabled=false }
            d4?.isEnabled=false
        }

    }

    fun game_start(){
        game = arrayOf(
            arrayOf(0,0,0,0), // a1 a2 a3 a4
            arrayOf(0,0,0,0), // b1 b2 b3 b4
            arrayOf(0,0,0,0), // c1 c2 c3 c4
            arrayOf(0,0,0,0)  // d1 d2 d3 d4
        )
        var check = ArrayList<Int>()
        var rnds :Int=0
        rnds = (1..15).random()
        check.add(0)
        button?.forEach {
            while (check.contains(rnds)){
                rnds = (1..15).random()
            }
            case(it,rnds)
            check.add(rnds)
        }
        check!!.clear()
    }

    fun game_set(){
        a1?.text = checknull(game!![0][0])
        a2?.text = checknull(game!![0][1])
        a3?.text = checknull(game!![0][2])
        a4?.text = checknull(game!![0][3])

        b1?.text = checknull(game!![1][0])
        b2?.text = checknull(game!![1][1])
        b3?.text = checknull(game!![1][2])
        b4?.text = checknull(game!![1][3])

        c1?.text = checknull(game!![2][0])
        c2?.text = checknull(game!![2][1])
        c3?.text = checknull(game!![2][2])
        c4?.text = checknull(game!![2][3])

        d1?.text = checknull(game!![3][0])
        d2?.text = checknull(game!![3][1])
        d3?.text = checknull(game!![3][2])
        d4?.text = checknull(game!![3][3])

        game_end()
    }

    fun checknull(game: Int):String{
        var gameset=""
        gameset = if (game ==0){
            ""
        }else{
            game.toString()
        }
        return gameset
    }

    fun case(b:Button,sta:Int){
        when(b){
            a1 -> game!![0][0] = sta
            a2 -> game!![0][1] = sta
            a3 -> game!![0][2] = sta
            a4 -> game!![0][3] = sta

            b1 -> game!![1][0] = sta
            b2 -> game!![1][1] = sta
            b3 -> game!![1][2] = sta
            b4 -> game!![1][3] = sta

            c1 -> game!![2][0] = sta
            c2 -> game!![2][1] = sta
            c3 -> game!![2][2] = sta
            c4 -> game!![2][3] = sta

            d1 -> game!![3][0] = sta
            d2 -> game!![3][1] = sta
            d3 -> game!![3][2] = sta
            d4 -> game!![3][3] = sta

        }

    }

    fun Array<Array<Int>>.swap(i1: Int, i2: Int, y1: Int, y2: Int){
        this[i1][i2] = this[y1][y2].also { this[y1][y2] = this[i1][i2] }
    }

    fun controll(){
        a1?.setOnClickListener { //game[0][0]
            when {
                game!![1][0]==0 -> {
                    game!!.swap(0,0,1,0) //swap game[0][0] to game[1][0]
                }
                game!![0][1]==0 -> {
                    game!!.swap(0,0,0,1) //swap game[0][0] to game[0][1]
                }

            }
            game_set()

        }

        a2?.setOnClickListener { //game[0][1]
            when {
                game!![1][1]==0 -> {
                    game!!.swap(0,1,1,1) //swap game[0][1] to game[1][1]
                }
                game!![0][2]==0 -> {
                    game!!.swap(0,1,0,2) //swap game[0][1] to game[0][2]
                }
                game!![0][0]==0 -> {
                    game!!.swap(0,1,0,0) //swap game[0][1] to game[0][0]
                }
            }
            game_set()
        }
        a3?.setOnClickListener { //game[0][2]
            when {
                game!![1][2]==0 -> {
                    game!!.swap(0,2,1,2) //swap game[1][2] to game[1][2]
                }
                game!![0][3]==0 -> {
                    game!!.swap(0,2,0,3) //swap game[1][2] to game[0][3]
                }
                game!![0][1]==0 -> {
                    game!!.swap(0,2,0,1) //swap game[1][2] to game[0][1]
                }
            }
            game_set()
        }
        a4?.setOnClickListener { //game[0][3]
            when {
                game!![1][3]==0 -> {
                    game!!.swap(0,3,1,3) //swap game[0][3] to game[1][3]
                }
                game!![0][2]==0 -> {
                    game!!.swap(0,3,0,2) //swap game[0][3] to game[0][2]
                }


            }
            game_set()
        }
        b1?.setOnClickListener { //game[1][0]
            when {
                game!![2][0]==0 -> {
                    game!!.swap(1,0,2,0) //swap game[1][0] to game[2][0]
                }
                game!![1][1]==0 -> {
                    game!!.swap(1,0,1,1) //swap game[1][0] to game[1][1]
                }
                game!![0][0]==0 -> {
                    game!!.swap(1,0,0,0) //swap game[1][0] to game[0][0]
                }

            }
            game_set()
        }
        b2?.setOnClickListener { //game[1][1]
            when {
                game!![2][1]==0 -> {
                    game!!.swap(1,1,2,1) //swap game[2][1] to game[2][1]
                }
                game!![1][2]==0 -> {
                    game!!.swap(1,1,1,2) //swap game[1][2] to game[1][2]
                }
                game!![0][1]==0 -> {
                    game!!.swap(1,1,0,1) //swap game[0][1] to game[0][1]
                }
                game!![1][0]==0 -> {
                    game!!.swap(1,1,1,0) //swap game[1][0] to game[1][0]
                }
            }
            game_set()
        }
        b3?.setOnClickListener { //game[1][2]
            when {
                game!![2][2]==0 -> {
                    game!!.swap(1,2,2,2) //swap game[1][2] to game[2][2]
                }
                game!![1][3]==0 -> {
                    game!!.swap(1,2,1,3) //swap game[1][2] to game[1][3]
                }
                game!![0][2]==0 -> {
                    game!!.swap(1,2,0,2) //swap game[1][2] to game[0][2]
                }
                game!![1][1]==0 -> {
                    game!!.swap(1,2,1,1) //swap game[1][2] to game[1][1]
                }
            }
            game_set()
        }
        b4?.setOnClickListener { //game[1][3]
            when {
                game!![2][3]==0 -> {
                    game!!.swap(1,3,2,3) //swap game[1][3] to game[2][3]
                }
                game!![1][2]==0 -> {
                    game!!.swap(1,3,1,2) //swap game[1][3] to game[1][2]
                }
                game!![0][3]==0 -> {
                    game!!.swap(1,3,0,3) //swap game[1][3] to game[0][3]
                }

            }
            game_set()
        }
        c1?.setOnClickListener { //game[2][0]
            when {
                game!![3][0]==0 -> {
                    game!!.swap(2,0,3,0) //swap game[2][2] to game[3][0]
                }
                game!![2][1]==0 -> {
                    game!!.swap(2,0,2,1) //swap game[2][2] to game[2][1]
                }
                game!![1][0]==0 -> {
                    game!!.swap(2,0,1,0) //swap game[2][2] to game[1][0]
                }

            }
            game_set()
        }
        c2?.setOnClickListener { //game[2][1]
            when {
                game!![3][1]==0 -> {
                    game!!.swap(2,1,3,1) //swap game[2][2] to game[3][1]
                }
                game!![2][2]==0 -> {
                    game!!.swap(2,1,2,2) //swap game[2][2] to game[2][2]
                }
                game!![1][1]==0 -> {
                    game!!.swap(2,1,1,1) //swap game[2][2] to game[1][1]
                }
                game!![2][0]==0 -> {
                    game!!.swap(2,1,2,0) //swap game[2][2] to game[2][0]
                }
            }
            game_set()
        }
        c3?.setOnClickListener { //game[2][2]
            when {
                game!![3][2]==0 -> {
                    game!!.swap(2,2,3,2) //swap game[2][2] to game[3][2]
                }
                game!![2][3]==0 -> {
                    game!!.swap(2,2,2,3) //swap game[2][2] to game[2][3]
                }
                game!![1][2]==0 -> {
                    game!!.swap(2,2,1,2) //swap game[2][2] to game[1][2]
                }
                game!![2][1]==0 -> {
                    game!!.swap(2,2,2,1) //swap game[2][2] to game[2][1]
                }
            }
            game_set()
        }
        c4?.setOnClickListener { //game[2][3]
            when {
                game!![3][3]==0 -> {
                    game!!.swap(2,3,3,3) //swap game[2][3] to game[3][3]
                }
                game!![2][2]==0 -> {
                    game!!.swap(2,3,2,2) //swap game[2][3] to game[2][2]
                }
                game!![1][3]==0 -> {
                    game!!.swap(2,3,1,3) //swap game[2][3] to game[1][3]
                }
            }
            game_set()
        }

        d1?.setOnClickListener { //game[3][0]
            when {
                game!![3][1]==0 -> {
                    game!!.swap(3,0,3,1) //swap game[3][0] to game[3][1]
                }
                game!![2][0]==0 -> {
                    game!!.swap(3,0,2,0) //swap game[3][0] to game[2][0]
                }

            }
            game_set()
        }

        d2?.setOnClickListener { //game[3][1]
            when {
                game!![3][2]==0 -> {
                    game!!.swap(3,1,3,2) //swap game[3][1] to game[3][2]
                }
                game!![2][1]==0 -> {
                    game!!.swap(3,1,2,1) //swap game[3][1] to game[2][1]
                }
                game!![3][0]==0 -> {
                    game!!.swap(3,1,3,0) //swap game[3][1] to game[3][0]
                }
            }
            game_set()
        }

        d3?.setOnClickListener { //game[3][2]
            when {
                game!![3][3]==0 -> {
                    game!!.swap(3,2,3,3) //swap game[3][2] to game[3][3]
                }
                game!![2][2]==0 -> {
                    game!!.swap(3,2,2,2) //swap game[3][2] to game[2][2]
                }
                game!![3][1]==0 -> {
                    game!!.swap(3,2,3,1) //swap game[3][2] to game[3][1]
                }
            }
            game_set()
        }

        d4?.setOnClickListener { //game[3][3]
            when {
                game!![3][2]==0 -> {
                    game!!.swap(3,3,3,2) //swap game[3][2] to game[3][2]
                }
                game!![2][3]==0 -> {
                    game!!.swap(3,3,2,3) //swap game[3][2] to game[2][3]
                }
            }
            game_set()
        }

        }

    fun game_end(){
        var checkwin = ArrayList<Int>()
        checkwin.clear()
        for (i in game!!){
            for (y in i){
                checkwin.add(y)
            }
        }
        if(checkwin.map { it } == win?.map { it }){
            Dialogplayerwin(timer?.text.toString())
            val db = DBHelper(this)
            db.addhistory(timer?.text.toString())

        }


    }
    @SuppressLint("SetTextI18n")
    fun Dialogplayerwin(Time:String){
        var alertDialomenug: AlertDialog
        val inflater: LayoutInflater = this.getLayoutInflater()
        val dialogView: View = inflater.inflate(R.layout.winpopup, null)
        val time: TextView = dialogView.findViewById(R.id.time)
        time.text=Time+" minutes!!"
        timer?.stop()
        disblebuttom(false)
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        dialogBuilder.setOnDismissListener { }
        dialogBuilder.setView(dialogView)

        alertDialomenug = dialogBuilder.create();
        alertDialomenug.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialomenug.show()
    }
}