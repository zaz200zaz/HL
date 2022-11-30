package com.example.kotlinfrebase

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_personal_page.*
import kotlinx.android.synthetic.main.activity_resume.*
import kotlinx.android.synthetic.main.return_bar.*
import java.util.*


class Resume:AppCompatActivity() {


    private lateinit var imageUri: Uri
    private lateinit var mRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)
        //imageのデータを取得
        var dataImage =intent.getStringExtra("Personal_Page_Image_Data")
        //もし写真のデータがなければ初期設定の写真を表示する,あったら登録された写真を表示する
        if (intent.getStringExtra("Personal_Page_Image_Data") !=""){
            Glide.with(applicationContext).load(dataImage).into(resumeImags)
        }else{

        }

        //mRef設定
        mRefInit()
        //戻る
        Return.setOnClickListener {
            startActivity(Intent(this, Personal_Page::class.java))
        }

        //写真をクリックされたらアルバイトに入る
        resumeImags.setOnClickListener {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT)
            startActivityForResult(intent, 1)
        }
    }

    private fun mRefInit() {
        mRef = FirebaseDatabase.getInstance().getReference("FaceToFacePick")
    }

    private fun emailFunsion(): String {
       return intent.getStringExtra("Personal_Page_Email_Data").toString()
    }


    //写真のデータを返す
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data!!.getData()!! != null) {
            imageUri = data!!.getData()!!
            resumeImags.setImageURI(imageUri)
        }
    }

    override fun onStart() {
        super.onStart()

        save.setOnClickListener {
            Toast.makeText(this,"保存",Toast.LENGTH_LONG).show()
            up()
            startActivity(Intent(this,Personal_Page::class.java).putExtra("Personal_Page_Email_Data",emailFunsion()))
            finish()
        }
    }

    private fun up() {
        mRef = FirebaseDatabase.getInstance().getReference("FaceToFacePick")
        mRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (userSnapshot in snapshot.children) {


                val userEmail = userSnapshot.getValue(User::class.java)!!.メール



                if (userEmail.equals(emailFunsion())) {
                    println("userSnapshot.ref:   "+userSnapshot.ref.toString())
                    val (a1, b,c,d, e) = userSnapshot.ref.toString().split("/")
                    println("e:  "+e)


                         FirebaseStorage.getInstance()
                        .getReference("tuanImages/" + UUID.randomUUID().toString())
                        .putFile(imageUri)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                task.getResult().storage.downloadUrl.addOnSuccessListener {

                                    Log.d(TAG, "onDataChange: "+it)

                                    FirebaseDatabase.getInstance().getReference("FaceToFacePick/"+e+"/resumeImage").setValue(it.toString())
                                }
                                println("ok")
                            } else {
                                Log.d(TAG, "error:" + task.exception)
                            }
                        }

                    break
                }
            }
    }

    override fun onCancelled(error: DatabaseError) {
        Log.d(TAG, "error:" + error.message)
    }
    })
    }
}
