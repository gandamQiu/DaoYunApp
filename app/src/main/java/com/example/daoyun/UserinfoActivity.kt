package com.example.daoyun

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.network.RetrofitUtils
import com.example.network.api.CodeApi
import com.example.network.api.NoDataResponse
import com.example.network.api.UserinfoBody
import retrofit2.Call
import retrofit2.Response

class UserinfoActivity : AppCompatActivity() {
    lateinit var name:EditText
    lateinit var number: EditText
    lateinit var button: Button
    lateinit var id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)
        name = findViewById(R.id.infoName)
        number = findViewById(R.id.infoNumber)
        button = findViewById(R.id.infoButton)
        id = this.intent.getStringExtra("id")!!

        button.setOnClickListener {
            if (name.text.toString()==""){
                Toast.makeText(this,"姓名不能为空",Toast.LENGTH_SHORT).show()
            }else if (number.text.toString()==""){
                Toast.makeText(this,"学号不能为空",Toast.LENGTH_SHORT).show()
            }else{
                RetrofitUtils.retrofitUtils.getService(CodeApi::class.java).completeUserInfo(UserinfoBody(id,number.text.toString(),name.text.toString()))
                        .enqueue(object :retrofit2.Callback<NoDataResponse?>{
                            override fun onFailure(call: Call<NoDataResponse?>, t: Throwable) {
                                Toast.makeText(this@UserinfoActivity,"操作失败，请重试",Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(call: Call<NoDataResponse?>, response: Response<NoDataResponse?>) {
                                when(val body = response.body()){
                                    null -> {
                                        Toast.makeText(this@UserinfoActivity,"操作失败，请重试",Toast.LENGTH_SHORT).show()
                                    }
                                    else -> {
                                        if (body.code == RetrofitUtils.retrofitUtils.getSuccess()){
                                            val intent = Intent()
                                            intent.putExtra("number",number.text.toString())
                                            intent.putExtra("name",name.text.toString())
                                            setResult(888,intent)
                                            finish()
                                        }else{
                                            Toast.makeText(this@UserinfoActivity,body.msg,Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        })
            }
        }
    }
}