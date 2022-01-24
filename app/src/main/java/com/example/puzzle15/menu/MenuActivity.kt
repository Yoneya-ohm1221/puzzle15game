package com.example.puzzle15.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.puzzle15.MainActivity
import com.example.puzzle15.R

class MenuActivity : AppCompatActivity() {
    var btnplay:Button?=null
    var btnhis :Button?=null
    var btnrank:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        window.navigationBarColor = resources.getColor(R.color.bg)

         btnplay = findViewById(R.id.button)
         btnhis = findViewById(R.id.btnhis)
         btnrank = findViewById(R.id.btnranking)

        btnplay?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnhis?.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        btnrank?.setOnClickListener {
            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)
        }
    }
}