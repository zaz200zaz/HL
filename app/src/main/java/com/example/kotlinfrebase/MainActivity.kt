package com.example.kotlinfrebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.List

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var FirebaseUserID: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
//        loginCheck()

        var inputEmail: EditText =findViewById(R.id.inputEmail)
        var inputPassword: EditText =findViewById(R.id.inputPassword)
        val btnLogin: Button =findViewById(R.id.btnLogin)
        val gotoRegister: TextView =findViewById(R.id.gotoRegister)


        //login機能
        btnLogin.setOnClickListener(View.OnClickListener {
            loginHandle()
        })

        //新規登録機能
        gotoRegister.setOnClickListener(View.OnClickListener {
            signUp()
        })
    }

//    private fun loginCheck() {
//
//        if (mAuth.currentUser)
//    }

    fun loginHandle() {
        var email: String = inputEmail.text.toString().trim()
        var password: String = inputPassword.text.toString().trim()

        if (inputEmail.text.toString().trim().isEmpty()||inputPassword.text.toString().trim().isEmpty()){
            Toast.makeText(this, "エラー", Toast.LENGTH_LONG).show()
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                if (task.isSuccessful){
                    Toast.makeText(this, "ログイン成功", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, ListUser::class.java))
                    finish()
                }else{
                    Toast.makeText(this, "ログイン失敗", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun signUp(){
        val intern = Intent(this,Register::class.java)
        startActivity(intern)
    }
    fun logOut(){


    }
}