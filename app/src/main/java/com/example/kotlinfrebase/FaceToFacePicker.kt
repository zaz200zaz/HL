package com.example.kotlinfrebase

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_face_to_face_picker.*
import java.util.UUID

class FaceToFacePicker : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var FirebaseUserID: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_to_face_picker)

        mAuth = FirebaseAuth.getInstance()

        button.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"click",Toast.LENGTH_LONG).show()
            FirebaseUserID = mAuth.currentUser!!.uid

            refUsers = FirebaseDatabase.getInstance().reference.child("FaceToFacePick").child(UUID.randomUUID().toString())
            val userHasMap = HashMap<String, Any>()
            userHasMap["name"] = editTextTextPersonName.text.toString()
            userHasMap["email"] = editTextTextPersonName3.text.toString()
            userHasMap["phoneNumber"] = editTextTextPersonName4.text.toString()
            userHasMap["Photo"] =textView.text.toString()

            refUsers.updateChildren(userHasMap)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        Toast.makeText(this,"登録成功", Toast.LENGTH_LONG).show()

                    }else{
                        Toast.makeText(this,"エラー"+ task.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }
        })

        refUsers = FirebaseDatabase.getInstance().reference.child("FaceToFacePick")
        refUsers.addListenerForSingleValueEvent(object : ValueEventListener{


            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot!!.children
                children.forEach {
                    var s:String = it.getValue(User::class.java)!!.getName()
                    Log.d(TAG, "onDataChange: " + s)
                    println("name :"+ it.getValue(User::class.java)!!.getName())
                    textView2.setText(textView2.text.toString()+","+s)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println(error!!.message)
            }

        })

    }
}