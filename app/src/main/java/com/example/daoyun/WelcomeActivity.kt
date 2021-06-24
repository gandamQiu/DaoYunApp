package com.example.daoyun

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.guide.GuideActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Handler().postDelayed({
            //startActivity(Intent(this,LoginActivity::class.java))
            val sp = this.getSharedPreferences("daoyun", Context.MODE_PRIVATE)
            val number = sp.getString("number","13388888888")
            val intent = Intent(this, GuideActivity::class.java)
            intent.putExtra("number",number)
            intent.putExtra("role","2")
            startActivity(intent)
            finish()
        },1000)

    }
}