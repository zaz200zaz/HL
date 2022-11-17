package com.example.kotlinfrebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.aa.*
import kotlinx.android.synthetic.main.activity_list.*

class ListUser : AppCompatActivity() {
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