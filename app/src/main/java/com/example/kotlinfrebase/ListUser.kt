package com.example.kotlinfrebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.aa.*
import kotlinx.android.synthetic.main.activity_list.*

import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

class ListUser : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRef: DatabaseReference
    private lateinit var mUser: FirebaseUser
    val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
    var name=findViewById<EditText>(R.id.name)
    var condition=findViewById<TextView>(R.id.condition)
    Firebasecycler Adapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        btnLogOut.setOnClickListener(View.OnClickListener {

            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "ログイン成功", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        })
        add.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,FaceToFacePicker::class.java))
        })

    }
}