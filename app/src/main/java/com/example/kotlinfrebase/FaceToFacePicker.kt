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
            userHasMap["名前"] = editTextTextPersonName.text.toString()
            userHasMap["一次面接時間"] = x1n.text.toString()
            userHasMap["一次面接コメント"] = x1km.text.toString()
            userHasMap["一次面接結果"] =x1k.text.toString()

            userHasMap["二次面接時間"] = x2n.text.toString()
            userHasMap["二次面接コメント"] = x2km.text.toString()
            userHasMap["二次面接結果"] =x2k.text.toString()

            userHasMap["研修時間"] = x3n.text.toString()
            userHasMap["研修コメント"] = x3km.text.toString()
            userHasMap["研修結果"] =x3k.text.toString()

            userHasMap["入社時間"] = kensyuu.text.toString()
            userHasMap["入社コメント"] = kensyuu2.text.toString()


            refUsers.updateChildren(userHasMap).addOnCompleteListener{task ->
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

                }
            }

            override fun onCancelled(error: DatabaseError) {
                println(error!!.message)
            }

        })

    }
}