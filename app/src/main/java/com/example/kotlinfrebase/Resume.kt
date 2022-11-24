package com.example.kotlinfrebase

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_resume.*
import kotlinx.android.synthetic.main.return_bar.*
import java.util.*


class Resume:AppCompatActivity() {


    val storage = FirebaseStorage.getInstance()
    val storageReference = storage.getReference()

    private lateinit var imageUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)

        LoadImage()

        Return.setOnClickListener {
            startActivity(Intent(this, Personal_Page::class.java))
        }

        resumeImage.setOnClickListener {
            choosePicture()
        }
    }

    private fun choosePicture() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data!!.getData()!! != null) {
            imageUri = data!!.getData()!!
            resumeImage.setImageURI(imageUri)
            save.setOnClickListener{
                UpLoadImg()
            }

        }
    }

    private fun UpLoadImg() {
        val randomkey: String = UUID.randomUUID().toString()

        val riversRef: StorageReference = storageReference.child("images/" + randomkey)
        riversRef.putFile(imageUri).addOnSuccessListener { taskSnapshot ->

            val userHasMap = HashMap<String, Any>()
            userHasMap["imageUrl"] = imageUri


            Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show()

        }.addOnFailureListener {

            Toast.makeText(applicationContext, "Failed To Upload", Toast.LENGTH_LONG).show()
        }
    }

    private fun LoadImage() {


    }
}
