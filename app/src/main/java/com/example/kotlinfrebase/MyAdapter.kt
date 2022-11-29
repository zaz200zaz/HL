package com.example.kotlinfrebase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.jar.Attributes


class MyAdapter( val userlist:ArrayList<User>):RecyclerView.Adapter<MyAdapter.MyViewHolder> (){

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val itemView=LayoutInflater.from(parent.context).inflate(R.layout.single_view,parent,false)
        return MyViewHolder(itemView)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userlist[position]

        holder.name.text = currentitem.名前
        holder.email.text = currentitem.メール

        if (currentitem.研修結果.isNullOrEmpty() && currentitem.二次面接結果.isNullOrEmpty() && currentitem.一次面接結果.isNullOrEmpty()) {

            holder.condition.text = "未定"
        }
        if (currentitem.研修結果.isNullOrEmpty() && currentitem.二次面接結果.isNullOrEmpty() && !currentitem.一次面接結果.isNullOrEmpty()) {

            holder.condition.text = "一次面接" + currentitem.一次面接結果

        }
        if (currentitem.研修結果.isNullOrEmpty() && !currentitem.二次面接結果.isNullOrEmpty() && !currentitem.一次面接結果.isNullOrEmpty()) {
            holder.condition.text = "二次面接" + currentitem.二次面接結果
        }
        if (!currentitem.研修結果.isNullOrEmpty() && !currentitem.二次面接結果.isNullOrEmpty() && !currentitem.一次面接結果.isNullOrEmpty()) {
            holder.condition.text = "研修結果" + currentitem.研修結果
        }


    }


    override fun getItemCount(): Int {
       return userlist.size
    }


    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        var name:TextView
        var condition:TextView
        var email:TextView

        init {
            name =itemView.findViewById(R.id.name)
            condition = itemView.findViewById(R.id.condition)
            email = itemView.findViewById(R.id.singViewEmaiId)
            itemView.setOnClickListener{

                val position: Int = adapterPosition
                var intern= Intent(itemView.context,Personal_Page::class.java)

//                intern.putExtra("singViewNameData",name.text.toString())
                intern.putExtra("Personal_Page_Email_Data",email.text.toString())

                itemView.context.startActivity(intern)
            }
        }

    }

}

