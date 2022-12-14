package com.example.kotlinfrebase

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var FirebaseUserID: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //データを初期設定(これがないとエラーがでます)
        dataInit()
        //アカウントがあるならログイン画面に飛ぶ
        alreadyHaveAccount()
        //登録ボタンが押されたら条件を見て登録出来るかどうか判断する
        register()
    }

    private fun register() {
        btnRegister.setOnClickListener(View.OnClickListener {

            check(
                inputEmail.text.toString().trim(),
                InputConfirmEmail.text.toString().trim(),
                inputPasswords.text.toString().trim(),
                InputConfirmPassword.text.toString().trim()
            )

        })
    }

    private fun alreadyHaveAccount() {
        AlreadyHaveAccount.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        })
    }

    private fun dataInit() {
        mAuth = FirebaseAuth.getInstance()
    }

    private fun check(trim: String, trim1: String, trim2: String, trim3: String) {
        var checkEmail: Boolean = false
        var checkPassword: Boolean = false
        if (trim.isEmpty()||trim1.isEmpty()){
            Toast.makeText(this,"メールエラー",Toast.LENGTH_LONG).show()
            inputEmail.setHintTextColor(resources.getColor(android.R.color.holo_red_dark))
            InputConfirmEmail.setHintTextColor(resources.getColor(android.R.color.holo_red_dark))
            checkEmail = false
        }else{

            if (trim.equals(trim1)){
                InputConfirmEmail.setTextColor(resources.getColor(R.color.black))
                inputEmail.setTextColor(resources.getColor(R.color.black))
                checkEmail = true
            }else{
                InputConfirmEmail.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                inputEmail.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                checkEmail = false
            }
        }

        if (trim2.isEmpty()||trim3.isEmpty()){
            Toast.makeText(this,"パスワードエラー",Toast.LENGTH_LONG).show()
            inputPasswords.setHintTextColor(resources.getColor(android.R.color.holo_red_dark))
            InputConfirmPassword.setHintTextColor(resources.getColor(android.R.color.holo_red_dark))
            checkPassword = false
        }else{

            if (trim2.equals(trim3)){
                inputPasswords.setTextColor(resources.getColor(R.color.black))
                InputConfirmPassword.setTextColor(resources.getColor(R.color.black))
                checkPassword = true
            }else{
                inputPasswords.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                InputConfirmPassword.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                checkPassword = false
            }
        }
        if (checkEmail && checkPassword){


            mAuth.createUserWithEmailAndPassword(trim,trim2).addOnCompleteListener { task->

                if (task.isSuccessful){
                    FirebaseUserID = mAuth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(FirebaseUserID)

                    val userHasMap = HashMap<String, Any>()
                    userHasMap["uid"] = FirebaseUserID
                    userHasMap["email"] = trim
                    userHasMap["password"] =trim2
                    refUsers.updateChildren(userHasMap)
                        .addOnCompleteListener{task ->
                            if (task.isSuccessful){
                                Toast.makeText(this,"登録成功", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this,MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
                                finish()
                            }
                        }
                }else{
                    Log.d(ContentValues.TAG, "check22: "+task.exception!!.message.toString())
                    Toast.makeText(this,"エラー：" + task.exception!!.message.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}