package com.example.guide

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class JoinClassInformationActivity : AppCompatActivity() {
    lateinit var className:TextView
    lateinit var classJoin:TextView
    lateinit var date:TextView
    lateinit var teacher:TextView
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_class_information)
        className = findViewById(R.id.classnameJoinClass)
        classJoin = findViewById(R.id.classJoinClass)
        date = findViewById(R.id.dateJoinClass)
        teacher = findViewById(R.id.teacherJoinClass)
        button = findViewById(R.id.buttonJoinClassInformation)

        className.setText("工程实践")
        classJoin.setText("2020-1")
        date.setText("2020-2021-2")
        teacher.setText("测试教师")
        button.setOnClickListener {
            finish()
        }

    }
}