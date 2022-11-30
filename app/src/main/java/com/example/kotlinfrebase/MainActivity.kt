package com.example.kotlinfrebase

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.List

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var FirebaseUserID: String = ""
    @JvmField var x = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        gotoRegister.isGone


        mAuth = FirebaseAuth.getInstance()
        loginCheck()

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
//            signUp()
            //アカウント制限関数
            checkss()
//            Toast.makeText(this,"textClick",Toast.LENGTH_LONG).show()
        })
    }



    private fun checkss() {

        var intern22 = Intent(this@MainActivity,Register::class.java)

        FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                var count:Int=0

                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        count++
                        println("dasdasd"+count)


                        if (count>1){

//                            var intern22 = Intent(this@MainActivity,Register::class.java)
                            intern22.putExtra("checkUser","アカウント越えている")



                        }else{

                            intern22.putExtra("checkUser","アカウント")
//                            startActivity(intern22)

                        }
                    }


                }

                startActivity(intern22)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

    private fun loginCheck() {

        if (mAuth.currentUser != null){

            startActivity(Intent(this,ListUser::class.java))
            finish()
        }
    }

    fun loginHandle() {
        var email: String = inputEmail.text.toString().trim()
        var password: String = inputPassword.text.toString().trim()

        if (inputEmail.text.toString().trim().isEmpty()||inputPassword.text.toString().trim().isEmpty()){
            Toast.makeText(this, "エラー", Toast.LENGTH_LONG).show()
        }else{
            val progre = ProgressDialog(this@MainActivity)
            progre.setTitle("❣❣❣❣ログイン中...❣❣❣❣")
            progre.setMessage("今、ログインしています。少々お待ちください。")
            progre.show()
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
                if (task.isSuccessful){
                    Toast.makeText(this, "ログイン成功", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, ListUser::class.java))
                    finish()
                }else{
                    Toast.makeText(this, "ログイン失敗", Toast.LENGTH_LONG).show()
                }
                progre.dismiss()
            }
        }
    }
    fun signUp(){
//        var intern22 = Intent(this@MainActivity,Register::class.java)



    }




    fun logOut(){

    }
}