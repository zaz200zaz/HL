package com.example.kotlinfrebase

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_resume.*
import kotlinx.android.synthetic.main.return_bar.*
import java.util.*


class Resume:AppCompatActivity() {



    private lateinit var imageUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)



        Return.setOnClickListener {
            startActivity(Intent(this, Personal_Page::class.java))
        }

        resumeImags.setOnClickListener {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 1)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data!!.getData()!! != null) {
            imageUri = data!!.getData()!!
            resumeImags.setImageURI(imageUri)
            save.setOnClickListener {
                UpLoadImg()

            }

        }
    }

    private fun UpLoadImg() {

        val a = FirebaseStorage.getInstance().getReference().child("resumeImage")
        val b = FirebaseDatabase.getInstance().getReference().child("FaceToFacePick")
        a.child(FirebaseAuth.getInstance().currentUser!!.uid).putFile(imageUri)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    a.child(FirebaseAuth.getInstance().currentUser!!.uid).downloadUrl.addOnSuccessListener {

                        val userHasMap = HashMap<String, Any>()
                        userHasMap["resumeImage"] = imageUri.toString()
                        b.child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .updateChildren(userHasMap).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this@Resume, "ok", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    Toast.makeText(this@Resume, "no", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            }
    }


    override fun onStart() {
        super.onStart()
        val c = FirebaseDatabase.getInstance().getReference().child("FaceToFacePick")
        c.child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()){
                        Picasso.get().load(dataSnapshot.child("resumeImage").getValue().toString())
                            .into(resumeImags)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Resume, "no", Toast.LENGTH_SHORT).show()
                }
            })

    }

}