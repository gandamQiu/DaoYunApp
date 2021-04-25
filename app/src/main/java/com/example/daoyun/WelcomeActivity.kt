package com.example.daoyun

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.login.LoginActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val time:Long = measureTimeMillis {
            GlobalScope.launch {
                Thread.sleep(200)
                startActivity(Intent(this@WelcomeActivity,LoginActivity::class.java))
                finish()
            }
        }
    }
}