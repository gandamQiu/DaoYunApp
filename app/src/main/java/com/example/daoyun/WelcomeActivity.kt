package com.example.daoyun

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
            startActivity(Intent(this, GuideActivity::class.java))
            finish()
        },3000)

    }
}