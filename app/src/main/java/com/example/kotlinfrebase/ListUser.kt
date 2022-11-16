package com.example.kotlinfrebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.aa.*

import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ListUser : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference
    private lateinit var mUser: FirebaseUser
    val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)

    var condition=findViewById<TextView>(R.id.condition)
    var name=findViewById<EditText>(R.id.name)
    val add:Button=findViewById(R.id.add)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        add.setOnClickListener(View.OnClickListener {
            Addlist()
            Loadlist()
        })

        btnLogOut.setOnClickListener(View.OnClickListener {

            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "ログイン成功", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        })
    }

    private fun Loadlist() {


    }


    private fun Addlist() {

    }


}


