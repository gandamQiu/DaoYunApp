package com.example.guide

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.network.RetrofitUtils
import com.example.network.api.ClassListApi
import com.example.network.api.ClassinfoResponse
import retrofit2.Call
import retrofit2.Response

class JoinClassInformationActivity : AppCompatActivity() {
    lateinit var className:TextView
    lateinit var classJoin:TextView
    lateinit var date:TextView
    lateinit var teacher:TextView
    lateinit var button: Button
    lateinit var imageButton: ImageButton

    lateinit var number:String
    lateinit var classNumber:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_class_information)

        number = intent.getStringExtra("number")!!
        classNumber = intent.getStringExtra("classNumber")!!
        Toast.makeText(this,"number:$number, classNumber:$classNumber",Toast.LENGTH_SHORT).show()

        className = findViewById(R.id.classnameJoinClass)
        classJoin = findViewById(R.id.classJoinClass)
        date = findViewById(R.id.dateJoinClass)
        teacher = findViewById(R.id.teacherJoinClass)
        button = findViewById(R.id.buttonJoinClassInformation)
        imageButton = findViewById(R.id.joinClassInfoReturnButton)
        imageButton.setOnClickListener {
            finish()
        }

        className.setText("加载中。。。")
        classJoin.setText("加载中。。。")
        date.setText("加载中。。。")
        teacher.setText("加载中。。。")
        button.setOnClickListener {
            finish()
        }
        getInfo()
    }
    fun getInfo(){
        RetrofitUtils.retrofitUtils.getService(ClassListApi::class.java).getClassinfo(classNumber)
                .enqueue(object : retrofit2.Callback<ClassinfoResponse?> {
                    override fun onFailure(call: Call<ClassinfoResponse?>, t: Throwable) {
                        Toast.makeText(this@JoinClassInformationActivity, "加载失败,请重试", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<ClassinfoResponse?>, response: Response<ClassinfoResponse?>) {
                        when(val body = response.body()){
                            null -> {
                                Toast.makeText(this@JoinClassInformationActivity, "加载失败,请重试", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                if (body.code == RetrofitUtils.retrofitUtils.getSuccess()){
                                    className.text = body.data.classname.toString()
                                    classJoin.text = "${body.data.school.toString()} ${body.data.college.toString()}"
                                    date.text = body.data.classsemester.toString()
                                }else{
                                    Toast.makeText(this@JoinClassInformationActivity,body.msg.toString(),Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                        }
                    }
                })
    }
}