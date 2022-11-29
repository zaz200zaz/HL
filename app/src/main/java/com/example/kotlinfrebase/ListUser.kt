package com.example.kotlinfrebase


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


import android.view.View

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

        Loadlist()
        userRecycleView = findViewById(R.id.recyclerView)
        userRecycleView.layoutManager = LinearLayoutManager(this)
        userRecycleView.setHasFixedSize(true)


        btnLogOut.setOnClickListener(View.OnClickListener {

            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "ログアウト成功", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })


        add.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this, FaceToFacePicker::class.java))

        })
        swiperefreshlayoutId.setOnRefreshListener {
            Loadlist()
            swiperefreshlayoutId.isRefreshing = false
        }
    }

    private fun Loadlist() {

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