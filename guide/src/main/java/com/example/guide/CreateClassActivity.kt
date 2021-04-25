package com.example.guide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CreateClassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_class)
        val button1 = findViewById<Button>(R.id.addClassButton1)
        val button2 = findViewById<Button>(R.id.addClassButton2)
        button1.setOnClickListener {
            val intent = Intent()
            intent.putExtra("name",findViewById<EditText>(R.id.editTextTextPersonName).text.toString())
            intent.putExtra("description",findViewById<EditText>(R.id.editTextTextPersonName2).text.toString())
            setResult(2,intent)
            finish()
        }
        button2.setOnClickListener {
            finish()
        }
    }
}