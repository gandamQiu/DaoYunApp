package com.example.guide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class JoinClassActivity : AppCompatActivity() {
    lateinit var text:EditText
    lateinit var button:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_class)
        text = findViewById<EditText>(R.id.editTextJoinClass)
        button = findViewById<Button>(R.id.buttonJoinClass)
        button.setOnClickListener{
            when(text.text.toString()){
                "86459875" ->{
                    startActivity(Intent(this,JoinClassInformationActivity::class.java))
                    finish()
                }
                else -> {
                    Toast.makeText(this,"班课不存在",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}