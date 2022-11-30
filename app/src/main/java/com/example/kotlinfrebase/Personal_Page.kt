package com.example.kotlinfrebase


import android.app.Dialog
import android.content.Intent

import android.widget.ImageView
import kotlinx.android.synthetic.main.bb.*

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_personal_page.*
import kotlinx.android.synthetic.main.result.*


class Personal_Page : AppCompatActivity() {
    lateinit var name:String
    private lateinit var mRef: DatabaseReference
    private lateinit var mRef2: DatabaseReference
    private lateinit var userArrayList:ArrayList<User>
    val storageReference = FirebaseStorage.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_page)

        Loadlist(intent.getStringExtra("Personal_Page_Email_Data").toString().trim())
        val Return:ImageView=findViewById(R.id.Return)

        //chuyen qua chon anh 履歴書写真
        imageChonAnh()
        //ListUser戻る
        back(Return)
        //chay lay ket qua sau khi thay doi du lieu
        var dataString:String =intent.getStringExtra("Personal_Page_Email_Data").toString().trim()
        Loadlist(dataString)
        // lam moi du lieu
        thayDoiVaLamMoiDuLieu()
        //hien thi bang ket qua
        hienThiBangKetQua()
        kesu.setOnClickListener{
            kesuData(intent.getStringExtra("Personal_Page_Email_Data").toString().trim())
            startActivity(Intent(this,ListUser::class.java))
            Toast.makeText(this,"面接者のデータ削除完了",Toast.LENGTH_LONG).show()
        }


    }

    private fun kesuData(trim: String) {

        mRef= FirebaseDatabase.getInstance().getReference("FaceToFacePick")
        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {

                        val userEmail = userSnapshot.getValue(User::class.java)!!.メール

                        val (a, b, c, d, e) = userSnapshot.ref.toString().split("/")
                        Log.d(TAG, "onDataChange1: " + e)
                        if (userEmail.equals(trim)) {
                            FirebaseDatabase.getInstance().getReference("FaceToFacePick").child(e).removeValue()
                            break
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        )

    }

    private fun checkResutl(
        toString: String,
        resultOfFirstInterview2: TextView,
        resultOfFirstInterview: TextView
    ) {
        if (toString!=""){
            resultOfFirstInterview2.isGone
            resultOfFirstInterview.visibility
            resultOfFirstInterview.setText(toString)
        }else{
            resultOfFirstInterview2.visibility
            resultOfFirstInterview.isGone
        }
    }

    private fun back(Return: ImageView) {
        Return.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,ListUser::class.java))
            finish()
        })
    }

    private fun imageChonAnh() {
        resume.setOnClickListener {
            var intern =Intent(this,Resume::class.java)
            var data:String =edtEmailId.text.toString()
            intern.putExtra("Personal_Page_Email_Data",edtEmailId.text.toString())
            intern.putExtra("Personal_Page_Image_Data",imageURl.text.toString())
            startActivity(intern)
        }
        img.setOnClickListener {
            var intern =Intent(this,Resume::class.java)
            var data:String =edtEmailId.text.toString()
            intern.putExtra("Personal_Page_Email_Data",edtEmailId.text.toString())
            intern.putExtra("Personal_Page_Image_Data",imageURl.text.toString())
            startActivity(intern)
        }
    }

    private fun thayDoiVaLamMoiDuLieu() {
        hozon.setOnClickListener(View.OnClickListener {

            hozonData(intent.getStringExtra("Personal_Page_Email_Data").toString().trim())
            startActivity(Intent(this,ListUser::class.java))
            Toast.makeText(this,"データ更新成功",Toast.LENGTH_LONG).show()

        })
        hozon2.setOnClickListener(View.OnClickListener {

            hozonData(intent.getStringExtra("Personal_Page_Email_Data").toString().trim())
            startActivity(Intent(this,ListUser::class.java))
            Toast.makeText(this,"データ更新成功",Toast.LENGTH_LONG).show()

        })
    }

    private fun hienThiBangKetQua() {
        textClick.setOnClickListener{

            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.result)

            dialog.button2.setOnClickListener(View.OnClickListener {
                resultOfFirstInterview.setText(dialog.button2.text.toString())
                dialog.dismiss()
            })
            dialog.button3.setOnClickListener{
                resultOfFirstInterview.setText(dialog.button3.text.toString())
                dialog.dismiss()
            }
            dialog.button4.setOnClickListener{
                resultOfFirstInterview.setText(dialog.button4.text.toString())
                dialog.dismiss()
            }


            dialog.show()
        }

        textClick2.setOnClickListener{


            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.result)

            dialog.button2.setOnClickListener(View.OnClickListener {
                resultOfSecondInterview.setText(dialog.button2.text.toString())
                dialog.dismiss()
            })
            dialog.button3.setOnClickListener{
                resultOfSecondInterview.setText(dialog.button3.text.toString())
                dialog.dismiss()
            }
            dialog.button4.setOnClickListener{
                resultOfSecondInterview.setText(dialog.button4.text.toString())
                dialog.dismiss()
            }


            dialog.show()
        }

        textClick3.setOnClickListener{


            val dialog = Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.result)

            dialog.button2.setOnClickListener(View.OnClickListener {
                resultOfKensyuu.setText(dialog.button2.text.toString())
                dialog.dismiss()
            })
            dialog.button3.setOnClickListener{
                resultOfKensyuu.setText(dialog.button3.text.toString())
                dialog.dismiss()
            }
            dialog.button4.setOnClickListener{
                resultOfKensyuu.setText(dialog.button4.text.toString())
                dialog.dismiss()
            }


            dialog.show()
        }
    }


    private fun Loadlist(name:String) {
        mRef= FirebaseDatabase.getInstance().getReference("FaceToFacePick")
        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        println("test"+userSnapshot)

                        val  userEmail= userSnapshot.getValue(User::class.java)!!.メール

                        println("test2"+userEmail)
                        println("test2"+name)
                        if (userEmail.toString().trim().equals(name.trim())){
                            println("test2"+userSnapshot)
                            val  user= userSnapshot.getValue(User::class.java)!!.名前
                            val  user1= userSnapshot.getValue(User::class.java)!!.一次面接結果
                            val  user11= userSnapshot.getValue(User::class.java)!!.一次面接時間
                            val  user111= userSnapshot.getValue(User::class.java)!!.一次面接コメント

                            val  user2= userSnapshot.getValue(User::class.java)!!.二次面接結果
                            val  user22= userSnapshot.getValue(User::class.java)!!.二次面接時間
                            val  user222= userSnapshot.getValue(User::class.java)!!.二次面接コメント

                            val  user3= userSnapshot.getValue(User::class.java)!!.研修結果
                            val  user33= userSnapshot.getValue(User::class.java)!!.研修時間
                            val  user333= userSnapshot.getValue(User::class.java)!!.研修コメント

                            val  user4= userSnapshot.getValue(User::class.java)!!.入社時間
                            val  user44= userSnapshot.getValue(User::class.java)!!.入社コメント
                            val  user444= userSnapshot.getValue(User::class.java)!!.resumeImage


                            edtNameId.setText(user)
                            edtEmailId.setText(userEmail)

                            if (user1 != "") {
                                    resultOfFirstInterview.setText(user1)
                                resultOfFirstInterview.visibility
//                                resultOfFirstInterview2.isGone
                            }else{
                                resultOfFirstInterview.isGone
//                                resultOfFirstInterview2.visibility
                            }
                            firstInterviewCalendar.setText(user11)
                            commentOfFirstInterview.setText(user111)

                            if (user2 != "") {
                                resultOfSecondInterview.setText(user2)
                            }else{
                                resultOfSecondInterview.isGone
//                                resultOfSecondInterview2.visibility
                            }
                            secondInterviewCalendar.setText(user22)
                            commentOfSecondInterview.setText(user222)

                            if (user3 !="") {
                                resultOfKensyuu.setText(user3)
                            }else{
                                resultOfKensyuu.isGone
//                                resultOfKensyuu2.visibility
                            }
                            kensyuuCalendar.setText(user33)
                            commentOfKensyuu.setText(user333)

                            nyuusyaCalendar.setText(user4)
                            comment.setText(user44)
//                            var testImage:ImageView = findViewById(com.google.firebase.database.R.id.)
                            if (user444 !=""){
                                imageURl.setText(user444)
                                Glide.with(applicationContext).load(user444).into(img)
                            }else{

                            }

                            break
                        }

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun hozonData(email:String) {
        mRef= FirebaseDatabase.getInstance().getReference("FaceToFacePick")
        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){

                        val  userEmail= userSnapshot.getValue(User::class.java)!!.メール

                        val (a,b,c,d,e) = userSnapshot.ref.toString().split("/")
                        Log.d(TAG, "onDataChange1: "+e)
                        if (userEmail.equals(email)){

                            val userHasMap = HashMap<String, Any>()

                            userHasMap.put("名前",edtNameId.text.toString().trim())
                            userHasMap.put("メール",edtEmailId.text.toString().trim())

                            userHasMap.put("一次面接結果",checkKetQua(resultOfFirstInterview.text.toString().trim()))
                            userHasMap.put("一次面接時間",firstInterviewCalendar.text.toString().trim())
                            userHasMap.put("一次面接コメント",commentOfFirstInterview.text.toString().trim())

                            userHasMap.put("二次面接結果",checkKetQua(resultOfSecondInterview.text.toString().trim()))
                            userHasMap.put("二次面接時間",secondInterviewCalendar.text.toString().trim())
                            userHasMap.put("二次面接コメント",commentOfSecondInterview.text.toString().trim())

                            userHasMap.put("研修結果",checkKetQua(resultOfKensyuu.text.toString().trim()))
                            userHasMap.put("研修時間",kensyuuCalendar.text.toString().trim())
                            userHasMap.put("研修コメント",commentOfKensyuu.text.toString().trim())

                            userHasMap.put("入社時間",nyuusyaCalendar.text.toString().trim())
                            userHasMap.put("入社コメント",comment.text.toString().trim())
                            userHasMap.put("resumeImage",imageURl.text.toString().trim())

                            FirebaseDatabase.getInstance().getReference("FaceToFacePick").child(e).updateChildren(userHasMap)


                            break
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: エラー："+error.message)
            }
        })
    }

    private fun checkKetQua(string: String): Any {
        if (string!="結果選び"){
            return string
        }
        return ""
    }
}