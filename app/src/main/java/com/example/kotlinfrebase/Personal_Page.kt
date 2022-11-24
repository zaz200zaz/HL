package com.example.kotlinfrebase

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_personal_page.*

class Personal_Page : AppCompatActivity() {
    lateinit var name:String
    private lateinit var mRef: DatabaseReference
    private lateinit var mRef2: DatabaseReference
    private lateinit var userArrayList:ArrayList<User>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_page)

        val test = arrayOf("合格","不合格","未定")
        var adapter = ArrayAdapter(this,R.layout.drop_dow_item,test)

        resultOfFirstInterview.threshold = 0

        resultOfFirstInterview.setAdapter(adapter)
        resultOfFirstInterview.isFocusable = false

        Loadlist(intent.getStringExtra("name").toString())

        hozon.setOnClickListener(View.OnClickListener {

            hozonData(intent.getStringExtra("name").toString())
        })

        resultOfFirstInterview.setOnClickListener{
            val test = arrayOf("合格","不合格","未定")
            var adapter = ArrayAdapter(this,R.layout.drop_dow_item,test)

            resultOfFirstInterview.threshold = 0

            resultOfFirstInterview.setAdapter(adapter)
            resultOfFirstInterview.isFocusable = false
        }
        resultOfSecondInterview.setOnClickListener{
            val test = arrayOf("合格","不合格","未定")
            var adapter = ArrayAdapter(this,R.layout.drop_dow_item,test)

            resultOfSecondInterview.threshold = 0

            resultOfSecondInterview.setAdapter(adapter)
            resultOfSecondInterview.isFocusable = false
        }
        resultOfKensyuu.setOnClickListener{
            val test = arrayOf("合格","不合格","未定")
            var adapter = ArrayAdapter(this,R.layout.drop_dow_item,test)

            resultOfKensyuu.threshold = 0

            resultOfKensyuu.setAdapter(adapter)
            resultOfKensyuu.isFocusable = false
        }

//        resultOfFirstInterview.setOnClickListener{
//            val test = arrayOf("合格","不合格","未定")
//            var adapter = ArrayAdapter(this,R.layout.drop_dow_item,test)
//
//            resultOfFirstInterview.threshold = 0
//
//            resultOfFirstInterview.setAdapter(adapter)
//            resultOfFirstInterview.isFocusable = false
//        }


//        firstInterviewCalendar.setText(intent.getStringExtra("name").toString())

    }


    private fun Loadlist(name:String) {
        mRef= FirebaseDatabase.getInstance().getReference("FaceToFacePick")
        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        var f = userSnapshot.children.toString()


                        for (i in userSnapshot.ref.toString().split("/")){   //Listなので直接for文で回せる
                            println(i)
                            Log.d(TAG, "onDataChange1: "+i)
                        }

                        val (a,b,c,d,e) = userSnapshot.ref.toString().split("/")
                        Log.d(TAG, "onDataChange1: "+e)

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
                        if (user.equals(name)){
//                            firstInterviewCalendar.setText(user)

                            resultOfFirstInterview.setText(user1?.let { checkResut(it) })
                            firstInterviewCalendar.setText(user11)
                            commentOfFirstInterview.setText(user111)

                            resultOfSecondInterview.setText(user2?.let { checkResut(it) })
                            secondInterviewCalendar.setText(user22)
                            commentOfSecondInterview.setText(user222)

                            resultOfKensyuu.setText(user3?.let { checkResut(it) })
                            kensyuuCalendar.setText(user33)
                            commentOfKensyuu.setText(user333)

                            nyuusyaCalendar.setText(user4)
                            comment.setText(user44)


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

    private fun hozonData(name:String) {
        mRef= FirebaseDatabase.getInstance().getReference("FaceToFacePick")
        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){

                        val  user= userSnapshot.getValue(User::class.java)!!.名前

                        val (a,b,c,d,e) = userSnapshot.ref.toString().split("/")
                        Log.d(TAG, "onDataChange1: "+e)
                        if (user.equals(name)){

                            val userHasMap = HashMap<String, Any>()

                            userHasMap.put("名前",name)

                            userHasMap.put("一次面接結果",resultOfFirstInterview.text.toString().trim())
                            userHasMap.put("一次面接時間",firstInterviewCalendar.text.toString().trim())
                            userHasMap.put("一次面接コメント",commentOfFirstInterview.text.toString().trim())

                            userHasMap.put("二次面接結果",resultOfSecondInterview.text.toString().trim())
                            userHasMap.put("二次面接時間",secondInterviewCalendar.text.toString().trim())
                            userHasMap.put("二次面接コメント",commentOfSecondInterview.text.toString().trim())

                            userHasMap.put("研修結果",resultOfKensyuu.text.toString().trim())
                            userHasMap.put("研修時間",kensyuuCalendar.text.toString().trim())
                            userHasMap.put("研修コメント",commentOfKensyuu.text.toString().trim())

                            userHasMap.put("入社時間",nyuusyaCalendar.text.toString().trim())
                            userHasMap.put("入社コメント",comment.text.toString().trim())


                            writeNewUser(
                                name,
                                resultOfFirstInterview.text.toString().trim(),
                                firstInterviewCalendar.text.toString().trim(),
                                commentOfFirstInterview.text.toString().trim(),

                                resultOfSecondInterview.text.toString().trim(),
                                secondInterviewCalendar.text.toString().trim(),
                                commentOfSecondInterview.text.toString().trim(),

                                resultOfKensyuu.text.toString().trim(),
                                kensyuuCalendar.text.toString().trim(),
                                commentOfKensyuu.text.toString().trim(),

                                nyuusyaCalendar.text.toString().trim(),
                                comment.text.toString().trim(),
                                e)
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
    fun checkResut(result:String): String {
        if (result.isEmpty()){
            return "結果選び"
        }
        return result
    }
    fun writeNewUser(name: String,
                     m1: String,m11: String,m111: String,
                     m2: String,m22: String,m222: String,
                     m3: String,m33: String,m333: String,
                     m4: String,m44: String,
                     past: String) {
        val user = User(name, m1,m11,m111,m2,m22,m222,m3,m33,m333,m4,m44)

        FirebaseDatabase.getInstance().getReference("FaceToFacePick").child(past).setValue(user)
    }
}