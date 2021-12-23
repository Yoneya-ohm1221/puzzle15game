package com.example.puzzle15.menu

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.puzzle15.MainActivity
import com.example.puzzle15.R
import com.example.puzzle15.database.DBHelper

class HistoryActivity : AppCompatActivity() {
    var back:ImageView?=null
    var data = ArrayList<Data>()
    var recyclerView:RecyclerView?=null
    var emptry:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView=findViewById(R.id.recycleview)
        emptry = findViewById(R.id.empty)
        back = findViewById(R.id.back2)
        back?.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        getdata()
        if (!data.isNullOrEmpty()){
            emptry?.visibility = View.GONE
        }else{
            emptry?.visibility = View.VISIBLE
        }
    }

    class Data(
        var id: String,
        var time: String,
        var date: String
    )

    @SuppressLint("Range")
    fun getdata(){
        data.clear()
        val db= DBHelper(this)
        val datasms =db.getAlldate()
        while (datasms.moveToNext()){
            val id = datasms.getString(datasms.getColumnIndex("id"))
            val time = datasms.getString(datasms.getColumnIndex("time"))
            val date = datasms.getString(datasms.getColumnIndex("date"))
            data.add((Data(id,time,date)))

        }
        recyclerView!!.adapter = DataAdapter(data)
    }
    internal inner class DataAdapter(private val list: List<Data>) :
        RecyclerView.Adapter<DataAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.cardview_history,
                parent, false
            )
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val data = list[position]
            holder.data = data
            holder.txttime.text = "Time: ${data.time}"
            holder.txtdate.text = data.date

        }


        override fun getItemCount(): Int {
            return list.size
        }

        internal inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

            var data: Data? = null
            var txttime: TextView = itemView.findViewById(R.id.txttime)
            var txtdate: TextView = itemView.findViewById(R.id.txtdate)


        }
    }

}