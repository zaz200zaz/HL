package com.example.kotlinfrebase


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


import android.view.View
import android.widget.ArrayAdapter

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.aa.*


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list.*
import android.os.Bundle as Bundle1

class ListUser : AppCompatActivity() {

    private lateinit var mRef: DatabaseReference
    private lateinit var mUser: FirebaseUser
    private lateinit var userRecycleView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle1?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //userArrayList
        addList()
        //データ初期設定
        dataInit()
        //ログアウト
        logOut()
        //面接者追加
        Add()
        //画面をドラッグする ...
        swiperefresh()
    }

    private fun swiperefresh() {
        swiperefreshlayoutId.setOnRefreshListener {
            addList()
            swiperefreshlayoutId.isRefreshing = false
        }
    }

    private fun Add() {
        add.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this, FaceToFacePicker::class.java))

        })
    }

    private fun logOut() {
        btnLogOut.setOnClickListener(View.OnClickListener {

            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "ログアウト成功", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })
    }

    private fun dataInit() {
        userRecycleView = findViewById(R.id.recyclerView)

        userRecycleView.layoutManager = LinearLayoutManager(this)
        userRecycleView.setHasFixedSize(true)
    }

    private fun addList() {

        userArrayList = arrayListOf<User>()
        userArrayList.clear()

        mRef = FirebaseDatabase.getInstance().getReference("FaceToFacePick")
        mRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                    }
                    userRecycleView.adapter = MyAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}