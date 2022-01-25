package com.example.puzzle15.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.puzzle15.R
import com.google.firebase.database.*
import java.lang.reflect.Constructor

class RankingActivity : AppCompatActivity() {
    var back: ImageView?=null
    var Fdatabase: DatabaseReference? = null
    var userarray = ArrayList<Data>()
    var recyclerView :RecyclerView?=null
    var name1:TextView?=null
    var name2:TextView?=null
    var name3:TextView?=null
    var time1:TextView?=null
    var time2:TextView?=null
    var time3:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        back = findViewById(R.id.back2)
        window.navigationBarColor = resources.getColor(R.color.bg)
        recyclerView = findViewById(R.id.recycleview)
        name1=findViewById(R.id.name1)
        name2=findViewById(R.id.name2)
        name3=findViewById(R.id.name3)

        time1=findViewById(R.id.time1)
        time2=findViewById(R.id.time2)
        time3=findViewById(R.id.time3)



        back?.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        userarray = arrayListOf<Data>()
        getData()

    }
    class Data (
        var name: String?=null,
        var time: String?=null,
        var Number:String?=null
    )
    fun getData(){

        val refFeatured1 = FirebaseDatabase.getInstance().getReference("users")
            .orderByChild("time").limitToFirst(50)

       // Fdatabase = FirebaseDatabase.getInstance().getReference("users")
        refFeatured1!!.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    userarray.clear()
                   var y=1
                    for (i in snapshot.children){
                        var user = i.getValue(Data::class.java)
                        userarray.add(Data(user?.name,user?.time,y.toString()))
                        y++

                    }
                    serBorad()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }



        })


}

    fun serBorad(){
        name1?.text = userarray[0].name
        name2?.text = userarray[1].name
        name3?.text = userarray[2].name

        time1?.text = userarray[0].time
        time2?.text = userarray[1].time
        time3?.text = userarray[2].time

        userarray.removeAt(0)
        userarray.removeAt(0)
        userarray.removeAt(0)

        recyclerView!!.adapter = DataAdapter(userarray)
    }

    internal inner class DataAdapter(private val list: List<Data>) :
        RecyclerView.Adapter<DataAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.cardview_ranking,
                parent, false
            )
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val data = list[position]
            holder.data = data
            holder.txtname.text = data.name
            holder.txttime.text = data.time
            holder.txtrank.text = data.Number

        }


        override fun getItemCount(): Int {
            return list.size
        }

        internal inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

            var data: Data? = null
            var txtname: TextView = itemView.findViewById(R.id.textView22)
            var txttime: TextView = itemView.findViewById(R.id.textView23)
            var txtrank: TextView = itemView.findViewById(R.id.txtnorank)


        }
    }


}