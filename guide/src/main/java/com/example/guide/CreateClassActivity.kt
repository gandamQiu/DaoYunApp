package com.example.guide

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CreateClassActivity : AppCompatActivity() {
    lateinit var dateSpinner: Spinner
    lateinit var schoolSpinner:Spinner
    lateinit var collegeSpinner:Spinner
    lateinit var classNameSpinner: Spinner
    lateinit var dateAdapter:ArrayAdapter<String>
    lateinit var schoolAdapter: ArrayAdapter<String>
    lateinit var collegeAdapter: ArrayAdapter<String>
    lateinit var classNameAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_class)
        val button1 = findViewById<Button>(R.id.addClassButton1)
        val button2 = findViewById<Button>(R.id.addClassButton2)
        button1.setOnClickListener {
            startActivity(Intent(this,ClassNumberActivity::class.java))
            finish()
        }
        button2.setOnClickListener {
            finish()
        }
        var dateList = ArrayList<String>()
        dateList.add("2020-2021-2")
        dateList.add("2021-2022-1")
        var schoolList = ArrayList<String>()
        schoolList.add("福州大学")
        var collegeList = ArrayList<String>()
        collegeList.add("数学与计算机科学学院")
        var classNameList = ArrayList<String>()

        //classNameList.add("工程实践")
        classNameList.add("其他")

        dateSpinner = findViewById<Spinner>(R.id.spinner_create_class_date)
        schoolSpinner = findViewById<Spinner>(R.id.spinner_create_class_school)
        collegeSpinner = findViewById<Spinner>(R.id.spinner_create_class_college)
        classNameSpinner = findViewById<Spinner>(R.id.spinner_create_class_classname)

        dateAdapter = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,dateList)
        schoolAdapter = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,schoolList)
        collegeAdapter = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,collegeList)
        classNameAdapter = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,classNameList)

        dateSpinner.adapter = dateAdapter
        schoolSpinner.adapter = schoolAdapter
        collegeSpinner.adapter = collegeAdapter
        classNameSpinner.adapter = classNameAdapter

        val newClassName = findViewById<EditText>(R.id.create_class_newClass)

        classNameSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position==classNameList.lastIndex){
                    newClassName.visibility = View.VISIBLE
                }else{
                    newClassName.visibility = View.GONE
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}