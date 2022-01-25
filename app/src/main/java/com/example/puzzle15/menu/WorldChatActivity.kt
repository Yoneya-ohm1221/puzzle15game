package com.example.puzzle15.menu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.puzzle15.R
import com.example.puzzle15.database.DBHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WorldChatActivity : AppCompatActivity() {
    var back: ImageView?=null
    var data = ArrayList<Data>()
    var recyclerView: RecyclerView?=null
    var editmes:EditText?=null
    var btnsend:Button?=null
    var Fdatabase:FirebaseDatabase? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_chat)
        window.navigationBarColor = resources.getColor(R.color.bg)
        window.statusBarColor = resources.getColor(R.color.bg)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        recyclerView=findViewById(R.id.recycleview)
        back = findViewById(R.id.back2)
        editmes =findViewById(R.id.editmes)
        btnsend = findViewById(R.id.btnsend)

        back?.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        btnsend?.setOnClickListener {
            addfirebase(editmes?.text.toString())
            editmes?.setText("")
        }
        getdata()
    }
    class Data(
        var name: String? = null,
        var message: String? = null,
        var time:String? = null
    )

    @SuppressLint("Range")
    fun getdata(){
        val refFeatured1 = FirebaseDatabase.getInstance().getReference("chats").limitToFirst(50)
        refFeatured1!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    data.clear()
                    for (i in snapshot.children){
                        var user = i.getValue(Data::class.java)
                        data.add(Data(user?.name, user?.message, user?.time))

                    }
                    recyclerView!!.adapter = DataAdapter(data)
                    recyclerView?.scrollToPosition(data.size - 1);
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
    internal inner class DataAdapter(private val list: List<Data>) :
        RecyclerView.Adapter<DataAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.cardview_chat,
                parent, false
            )
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val data = list[position]
            holder.data = data
            holder.txtchat.text = data.message
            holder.txtname.text = data.name
            holder.txtdate.text = data.time

        }


        override fun getItemCount(): Int {
            return list.size
        }

        internal inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

            var data: Data? = null
            var txtname: TextView = itemView.findViewById(R.id.txtnamechat)
            var txtchat: TextView = itemView.findViewById(R.id.txtchat)
            var txtdate: TextView = itemView.findViewById(R.id.txtdate1)

        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun addfirebase(message: String){
        val db =DBHelper(this)
        var name =db.getPlayerName()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val currentDate = sdf.format(Date())

            Fdatabase = FirebaseDatabase.getInstance()
            var databaseReference =Fdatabase?.reference?.child("chats")?.push()
            databaseReference?.child("message")?.setValue(message)
            databaseReference?.child("name")?.setValue(name)
            databaseReference?.child("time")?.setValue(currentDate)

    }

}