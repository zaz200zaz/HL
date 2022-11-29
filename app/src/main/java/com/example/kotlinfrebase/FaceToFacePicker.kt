package com.example.kotlinfrebase

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_face_to_face_picker.*
import kotlinx.android.synthetic.main.activity_face_to_face_picker.kensyuu
import kotlinx.android.synthetic.main.activity_personal_page.*
import kotlinx.android.synthetic.main.activity_resume.*
import java.util.UUID

class FaceToFacePicker : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var FirebaseUserID: String = ""
    private lateinit var imageUri: Uri
    private lateinit var a: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_to_face_picker)

        khoiTao()
        //面接者の登録
        toroku()
    }

    private fun khoiTao() {
        mAuth = FirebaseAuth.getInstance()
        FirebaseUserID = mAuth.currentUser!!.uid
    }

    private fun toroku() {

        button.setOnClickListener{
            if (!email.text.toString().trim().isEmpty()){

                var stringEmail =email.text.toString().trim()
                refUsers = FirebaseDatabase.getInstance().getReference().child("FaceToFacePick/"+UUID.randomUUID().toString())
                val userHasMap = HashMap<String, Any>()
                userHasMap["名前"] = editTextTextPersonName.text.toString()
                userHasMap["メール"] = email.text.toString()

                userHasMap["一次面接時間"] = x1n.text.toString()
                userHasMap["一次面接コメント"] = x1km.text.toString()
                userHasMap["一次面接結果"] = x1k.text.toString()

                userHasMap["二次面接時間"] = x2n.text.toString()
                userHasMap["二次面接コメント"] = x2km.text.toString()
                userHasMap["二次面接結果"] = x2k.text.toString()

                userHasMap["研修時間"] = x3n.text.toString()
                userHasMap["研修コメント"] = x3km.text.toString()
                userHasMap["研修結果"] = x3k.text.toString()

                userHasMap["入社時間"] = kensyuu.text.toString()
                userHasMap["入社コメント"] = kensyuu2.text.toString()
                userHasMap["resumeImage"] = ""
                refUsers.updateChildren(userHasMap).addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        Toast.makeText(this,"登録成功", Toast.LENGTH_LONG).show()

                        startActivity(Intent(this,ListUser::class.java))
                    }
                }
            }
        }

    }
}