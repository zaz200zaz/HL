package com.example.kotlinfrebase

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.cast.framework.media.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_resume.*


class Resume:AppCompatActivity(){

    private val mStoRef=FirebaseStorage.getInstance().reference
    private  lateinit var mProgressDialog: ProgressDialog
    private val TAG="Resume"

    companion object{
        const val REQUEST_FROM_CAMERA=101
        const val REQUEST_FROM_GALLERY=102
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)

        resumeImage.setOnClickListener{
            pickImageFromGallery()
        }
    }





    override fun onActivityResult(requestCode:Int,resultCode: Int,data:Intent?){
        super.onActivityResult(requestCode,resultCode,data)
        if (resultCode==Activity.RESULT_OK){
            when(requestCode){
                REQUEST_FROM_GALLERY ->{
                    resumeImage.setImageURI(data!!.data)
                    Resume().uploadImage(this,data.data!!)
                }
            }
        }
    }

    fun uploadImage(mContext: Context,imageURI:Uri){
        mProgressDialog=ProgressDialog(mContext)
        mProgressDialog.setMessage("please wait...")
        mProgressDialog.show()

       val uploadTask= mStoRef.child("FaceToFacePick/resumeImg").putFile(imageURI)
        uploadTask.addOnSuccessListener {
            Log.e(TAG,"Successfully")
            mProgressDialog.dismiss()
        }.addOnFailureListener{
            Log.e(TAG,"Failed")
            mProgressDialog.dismiss()
        }

    }
}