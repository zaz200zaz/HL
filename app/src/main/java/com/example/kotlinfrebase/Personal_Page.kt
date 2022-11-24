package com.example.kotlinfrebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.bb.*
import kotlinx.android.synthetic.main.return_bar.*
import kotlinx.android.synthetic.main.return_bar.Return

class Personal_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_page)

        val resume:Button=findViewById(R.id.resume)
        val Return:ImageView=findViewById(R.id.Return)

        resume.setOnClickListener {
                startActivity(Intent(this,Resume::class.java))
        }
        Return.setOnClickListener{
            startActivity(Intent(this,ListUser::class.java))
        }






    }
}