package com.example.daoyun

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.guide.GuideActivity
import com.example.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {
    lateinit var sp:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Handler().postDelayed({
            //
            sp = this.getSharedPreferences("daoyun", Context.MODE_PRIVATE)
            val number = sp.getString("number","none")
            val role = sp.getString("role","none")
            val name = sp.getString("name","none")
            val id = sp.getString("id","none")
            if(number=="none"||id=="none"){
                startActivityForResult(Intent(this,LoginActivity::class.java),777)
            }else if (number=="0"||number=="null"){
                val intent = Intent(this, UserinfoActivity::class.java)
                intent.putExtra("id",id)
                startActivityForResult(intent,888)
            }
            else{
                val intent = Intent(this, GuideActivity::class.java)
                intent.putExtra("number",number)
                intent.putExtra("role",role)
                intent.putExtra("name",name)
                startActivityForResult(intent,666)
            }
        },1000)
    }

    //解决循环嵌套
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==666){
            if (resultCode==666){
                sp.edit().putString("number","none").apply()
                startActivityForResult(Intent(this, LoginActivity::class.java),777)
            }else{
                finish()
            }
        }else if (requestCode==777) {
            if (resultCode==777){
                val number = data!!.getStringExtra("number")!!
                val role = data.getStringExtra("role")!!
                val name = data.getStringExtra("name")!!
                val id = data.getStringExtra("id")
                val edit = sp.edit()
                edit.putString("number",number)
                edit.putString("role",role)
                edit.putString("name",name)
                edit.putString("id",id)
                edit.apply()
                if (number=="0"||number=="null"){
                    val intent = Intent(this, UserinfoActivity::class.java)
                    intent.putExtra("id",id)
                    startActivityForResult(intent,888)
                }else{
                    val intent = Intent(this, GuideActivity::class.java)
                    intent.putExtra("number",number)
                    intent.putExtra("role",role)
                    intent.putExtra("name",name)
                    startActivityForResult(intent,666)
                }
            }else{
                finish()
            }
        }else if (requestCode==888){
            if (resultCode==888){
                val number = data!!.getStringExtra("number")!!
                val name = data.getStringExtra("name")!!
                val edit = sp.edit()
                edit.putString("number",number)
                edit.putString("name",name)
                edit.apply()
                val role = sp.getString("role","3")
                val intent = Intent(this, GuideActivity::class.java)
                intent.putExtra("number",number)
                intent.putExtra("role",role)
                intent.putExtra("name",name)
                startActivityForResult(intent,666)
            }else{
                startActivityForResult(Intent(this, LoginActivity::class.java),777)
            }
        }
    }
}