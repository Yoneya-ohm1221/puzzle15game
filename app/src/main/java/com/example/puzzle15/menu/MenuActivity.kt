package com.example.puzzle15.menu

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.puzzle15.MainActivity
import com.example.puzzle15.R
import com.example.puzzle15.database.DBHelper
import com.google.android.material.bottomsheet.BottomSheetDialog

class MenuActivity : AppCompatActivity() {
    var btnplay:Button?=null
    var btnhis :Button?=null
    var btnrank:Button?=null
    var playername:TextView?=null
    var name =""
    var editoption:ImageView?=null
    var imgchat:ImageView?=null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        window.navigationBarColor = resources.getColor(R.color.bg)
        window.statusBarColor = resources.getColor(R.color.bg)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        btnplay = findViewById(R.id.button)
         btnhis = findViewById(R.id.btnhis)
         btnrank = findViewById(R.id.btnranking)
         playername = findViewById(R.id.txtplayername)
         editoption = findViewById(R.id.imgeditoption)
        imgchat = findViewById(R.id.imgchat)

        setName()
        btnplay?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnhis?.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        btnrank?.setOnClickListener {
            if (isOnline(this)){
                val intent = Intent(this, RankingActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "ไม่ได้เชื่อมต่อ Internet", Toast.LENGTH_SHORT).show()
            }

        }
        imgchat?.setOnClickListener {
            if (isOnline(this)){
                val intent = Intent(this, WorldChatActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "ไม่ได้เชื่อมต่อ Internet", Toast.LENGTH_SHORT).show()
            }
        }

        editoption?.setOnClickListener {
            dialog()
        }
    }

    fun setName(){
        val  db = DBHelper(this)
        name = db.getPlayerName()
        Log.d("yyyy","name $name")
        if(name==""||name.isNullOrEmpty()){
            db.addplayer("player1")
            name = db.getPlayerName()
        }

        playername?.text= name
    }
    fun dialog(){
        val btnsheet = layoutInflater.inflate(R.layout.popupaddlist, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(btnsheet)
        var btnclose: ImageView? = dialog.findViewById(R.id.btnclose)
        var btnok: ImageView?=dialog.findViewById(R.id.btnok)
        var edittitle:TextView?=dialog.findViewById(R.id.edittitle)

        btnclose?.setOnClickListener {
            dialog.dismiss()
        }
        btnok?.setOnClickListener {
            val db= DBHelper(this)
            db.updatePlayerName(edittitle?.text.toString())
            setName()
            dialog.dismiss()

        }
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}