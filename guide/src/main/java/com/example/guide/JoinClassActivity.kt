package com.example.guide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class JoinClassActivity : AppCompatActivity() {
    lateinit var text:EditText
    lateinit var button:Button
    lateinit var imageButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_class)
        imageButton = findViewById(R.id.joinClassReturnButton)
        imageButton.setOnClickListener {
            finish()
        }
        text = findViewById<EditText>(R.id.editTextJoinClass)
        button = findViewById<Button>(R.id.buttonJoinClass)
        button.setOnClickListener{
            when(text.text.toString()){
                "" ->{
                    Toast.makeText(this,"班课号不可为空",Toast.LENGTH_SHORT).show()
                }
                else->{
                    val intent = Intent()
                    intent.putExtra("classNumber",text.text.toString())
                    setResult(999,intent)
                    finish()
                }
            }
        }
    }
}