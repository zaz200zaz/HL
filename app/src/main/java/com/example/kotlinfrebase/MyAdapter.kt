package com.example.kotlinfrebase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userlist:ArrayList<User>):RecyclerView.Adapter<MyAdapter.MyViewHolder> (){


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val itemView=LayoutInflater.from(parent.context).inflate(R.layout.single_view,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userlist[position]
        holder.name.text = currentitem.名前

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

        val name:TextView=itemView.findViewById(R.id.name)
        val condition:TextView=itemView.findViewById(R.id.email)

        init {

            itemView.setOnClickListener{
                var intent = Intent(Intent(itemView.context,Personal_Page::class.java))
                intent.putExtra("name",name.text.toString().trim())
                itemView.context.startActivity(intent)
            }
        }




    }

}


