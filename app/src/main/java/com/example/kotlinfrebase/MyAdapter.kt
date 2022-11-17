package com.example.kotlinfrebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userlist:ArrayList<User>):RecyclerView.Adapter<MyAdapter.MyViewHolder> (){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val itemView=LayoutInflater.from(parent.context).inflate(R.layout.single_view,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem=userlist[position]
        holder.name.text=currentitem.name
        holder.condition.text=currentitem.condition
    }

    override fun getItemCount(): Int {
       return userlist.size
    }



    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val name:TextView=itemView.findViewById(R.id.name)
        val condition:TextView=itemView.findViewById(R.id.condition)
    }
}