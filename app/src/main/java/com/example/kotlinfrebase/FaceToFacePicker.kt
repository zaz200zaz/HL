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
import kotlinx.android.synthetic.main.activity_resume.*
import java.util.UUID

class FaceToFacePicker : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var FirebaseUserID: String = ""
    private lateinit var imageUri: Uri

    private lateinit var a: StorageReference
    private var uurl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_to_face_picker)

        mAuth = FirebaseAuth.getInstance()
        FirebaseUserID = mAuth.currentUser!!.uid
//        refUsers = FirebaseDatabase.getInstance().getReference().child("FaceToFacePick").child(UUID.randomUUID().toString())

        refUsers = FirebaseDatabase.getInstance().getReference().child("test").child(UUID.randomUUID().toString())
//        val userHasMap = HashMap<String, Any>()
//                        userHasMap["名前"] = editTextTextPersonName.text.toString()
//                        userHasMap["一次面接時間"] = x1n.text.toString()
//                        userHasMap["一次面接コメント"] = x1km.text.toString()
//                        userHasMap["一次面接結果"] = x1k.text.toString()
//
//                        userHasMap["二次面接時間"] = x2n.text.toString()
//                        userHasMap["二次面接コメント"] = x2km.text.toString()
//                        userHasMap["二次面接結果"] = x2k.text.toString()
//
//                        userHasMap["研修時間"] = x3n.text.toString()
//                        userHasMap["研修コメント"] = x3km.text.toString()
//                        userHasMap["研修結果"] = x3k.text.toString()
//
//                        userHasMap["入社時間"] = kensyuu.text.toString()
//                        userHasMap["入社コメント"] = kensyuu2.text.toString()
//                        userHasMap["resumeImage"] = "image"
//
//
//
//        button.setOnClickListener{
//
//            refUsers.updateChildren(userHasMap).addOnCompleteListener{task ->
//                if (task.isSuccessful){
//                    Toast.makeText(this,"登録成功", Toast.LENGTH_LONG).show()
//
//                }
//            }
//        }
        imageClick()


//        a.child(FirebaseUserID).putFile(imageUri)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    a.child(FirebaseUserID).downloadUrl.addOnSuccessListener {
//
//                        val userHasMap = HashMap<String, Any>()
//                        userHasMap["名前"] = editTextTextPersonName.text.toString()
//                        userHasMap["一次面接時間"] = x1n.text.toString()
//                        userHasMap["一次面接コメント"] = x1km.text.toString()
//                        userHasMap["一次面接結果"] = x1k.text.toString()
//
//                        userHasMap["二次面接時間"] = x2n.text.toString()
//                        userHasMap["二次面接コメント"] = x2km.text.toString()
//                        userHasMap["二次面接結果"] = x2k.text.toString()
//
//                        userHasMap["研修時間"] = x3n.text.toString()
//                        userHasMap["研修コメント"] = x3km.text.toString()
//                        userHasMap["研修結果"] = x3k.text.toString()
//
//                        userHasMap["入社時間"] = kensyuu.text.toString()
//                        userHasMap["入社コメント"] = kensyuu2.text.toString()
//                        userHasMap["resumeImage"] = imageUri.toString()
//
//                        refUsers.child(FirebaseUserID).updateChildren(userHasMap)
//                            .addOnCompleteListener { task ->
//                                if (task.isSuccessful) {
//                                    Toast.makeText(this, "登録成功", Toast.LENGTH_LONG)
//                                        .show()
//
//                                } else {
//                                    Toast.makeText(
//                                        this,
//                                        "エラー" + task.exception!!.message,
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                }
//                            }
//                    }
//
//
//                    refUsers.addListenerForSingleValueEvent(object :
//                        ValueEventListener {
//
//
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            val children = snapshot!!.children
//                            children.forEach {
//
//                            }
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            println(error!!.message)
//                        }
//
//                    })
//                }
//            }
    }

    private fun imageClick() {
                resumeImage.setOnClickListener {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")

            startActivityForResult(intent, 1)
        }

    }

    private fun imageUpLoad(){
        var  sss: String
        FirebaseStorage.getInstance()
            .getReference("tuanImage/"+ UUID.randomUUID().toString())
            .putFile(imageUri)
            .addOnCompleteListener { task ->


                if (task.isSuccessful){

                    task.getResult().storage.downloadUrl.addOnSuccessListener{


                            val userHasMap = HashMap<String, Any>()
                            userHasMap["名前"] = editTextTextPersonName.text.toString()
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
                            userHasMap["resumeImage"] = it


                            sss = it.toString()
                        FaceToFacePicker.namesss=sss
                            refUsers.setValue(sss)


//                            refUsers.updateChildren(userHasMap).addOnCompleteListener{task ->
//                                if (task.isSuccessful){
//                                    Toast.makeText(this,"登録成功", Toast.LENGTH_LONG).show()
//
//                                }
//                            }


                    }
                }

            }
        Log.d(TAG, "imageUpLoad:sdas "+FaceToFacePicker.namesss)


    }


//        mAuth = FirebaseAuth.getInstance()
//
//        button.setOnClickListener(View.OnClickListener {
//            Toast.makeText(this,"click",Toast.LENGTH_LONG).show()
//            FirebaseUserID = mAuth.currentUser!!.uid
//
//            refUsers = FirebaseDatabase.getInstance().reference.child("FaceToFacePick").child(UUID.randomUUID().toString())
//            val userHasMap = HashMap<String, Any>()
//            userHasMap["名前"] = editTextTextPersonName.text.toString()
//            userHasMap["一次面接時間"] = x1n.text.toString()
//            userHasMap["一次面接コメント"] = x1km.text.toString()
//            userHasMap["一次面接結果"] =x1k.text.toString()
//
//            userHasMap["二次面接時間"] = x2n.text.toString()
//            userHasMap["二次面接コメント"] = x2km.text.toString()
//            userHasMap["二次面接結果"] =x2k.text.toString()
//
//            userHasMap["研修時間"] = x3n.text.toString()
//            userHasMap["研修コメント"] = x3km.text.toString()
//            userHasMap["研修結果"] =x3k.text.toString()
//
//            userHasMap["入社時間"] = kensyuu.text.toString()
//            userHasMap["入社コメント"] = kensyuu2.text.toString()
//            userHasMap["resumeImage"] = imageUri.toString()
//
//
//            refUsers.updateChildren(userHasMap).addOnCompleteListener{task ->
//                if (task.isSuccessful){
//                    Toast.makeText(this,"登録成功", Toast.LENGTH_LONG).show()
//
//                }else{
//                    Toast.makeText(this,"エラー"+ task.exception!!.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//
//        refUsers = FirebaseDatabase.getInstance().reference.child("FaceToFacePick")
//        refUsers.addListenerForSingleValueEvent(object : ValueEventListener{
//
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val children = snapshot!!.children
//                children.forEach {
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                println(error!!.message)
//            }
//
//        })
//        resumeImage.setOnClickListener {
//
//            val intent = Intent(Intent.ACTION_GET_CONTENT)
//            intent.setType("image/*")
//            startActivityForResult(intent, 1)
//        }



//    }
//    override fun onStart() {
//        super.onStart()
//        val d=FirebaseAuth.getInstance().currentUser
//        val c = FirebaseDatabase.getInstance().getReference().child("FaceToFacePick")
//        c.child(d!!.uid)
//            .addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    if (dataSnapshot.exists()){
//                        uurl=dataSnapshot.child("resumeImage").getValue().toString()
//                        Picasso.get().load(uurl).into(resumeImage)
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(this@FaceToFacePicker, "Sorry,something going wrong", Toast.LENGTH_SHORT).show()
//                }
//            })
//
//    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == 1 && resultCode == RESULT_OK && data != null && data!!.getData()!! != null) {
        imageUri = data!!.getData()!!
        resumeImage.setImageURI(imageUri)
        imageUpLoad()

//                button.setOnClickListener{
//                    upload()
//                }

    }
}
    companion object {
        @JvmStatic
        var namesss:String = ""
    }
}