package com.example.kotlinfrebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var FirebaseUserID: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        button.setOnClickListener(View.OnClickListener {
            mAuth.createUserWithEmailAndPassword(editTextTextPersonName.text.toString().trim(),editTextTextPersonName3.text.toString().trim()).addOnCompleteListener { task->

                if (task.isSuccessful){
                    FirebaseUserID = mAuth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(FirebaseUserID)

                    val userHasMap = HashMap<String, Any>()
                    userHasMap["uid"] = FirebaseUserID
                    userHasMap["email"] = editTextTextPersonName.text.toString().trim()
                    userHasMap["password"] =editTextTextPersonName3.text.toString().trim()
                    refUsers.updateChildren(userHasMap)
                        .addOnCompleteListener{task ->
                            if (task.isSuccessful){
                                Toast.makeText(this,"登録成功", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            }
        })


    }
}