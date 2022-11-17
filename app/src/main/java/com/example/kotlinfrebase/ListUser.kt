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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class ListUser : AppCompatActivity() {

    private lateinit var mRef: DatabaseReference
    private lateinit var userRecycleView:RecyclerView
    private lateinit var userArrayList:ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        userRecycleView=findViewById(R.id.recyclerView)
        userRecycleView.layoutManager=LinearLayoutManager(this)
        userRecycleView.setHasFixedSize(true)

        userArrayList= arrayListOf<User>()
        Loadlist()

        btnLogOut.setOnClickListener(View.OnClickListener {

            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "ログアウト成功", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        })

        add.setOnClickListener(View.OnClickListener {
            Addlist()
        })
    }

    private fun Loadlist() {
        mRef=FirebaseDatabase.getInstance().getReference("Users")
        mRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()){
                   for (userSnapshot in snapshot.children){
                       val  user=userSnapshot.getValue(User::class.java)
                       userArrayList.add(user!!)
                   }
                   userRecycleView.adapter=MyAdapter(userArrayList)
               }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun Addlist() {
    }
}


