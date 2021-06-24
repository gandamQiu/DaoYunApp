package com.example.guide

import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class CreateClassActivity : AppCompatActivity() {
    lateinit var dateSpinner: Spinner
    lateinit var schoolSpinner:Spinner
    lateinit var collegeSpinner:Spinner
    lateinit var courseNameSpinner: Spinner
    lateinit var dateAdapter:ArrayAdapter<String>
    lateinit var schoolAdapter: ArrayAdapter<String>
    lateinit var collegeAdapter: ArrayAdapter<String>
    lateinit var courseNameAdapter: ArrayAdapter<String>
    lateinit var className:EditText
    lateinit var courseName:EditText

    lateinit var number:String
    var courseType:Boolean = false
    lateinit var courseText:String
    lateinit var dateText:String
    lateinit var schoolText: String
    lateinit var collegeText:String


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_class)

        number = this.intent.getStringExtra("number")!!

        val button1 = findViewById<Button>(R.id.addClassButton1)
        val button2 = findViewById<ImageButton>(R.id.createClassReturnButton)

        val dateList = ArrayList<String>()
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        if (month>=9){
            dateList.add((year+1).toString()+"-"+(year+2).toString()+"-1")
            dateList.add((year+1).toString()+"-"+(year+2).toString()+"-2")
            dateList.add((year+2).toString()+"-"+(year+3).toString()+"-1")
        }else{
            dateList.add(year.toString()+"-"+(year+1).toString()+"-2")
            dateList.add((year+1).toString()+"-"+(year+2).toString()+"-1")
            dateList.add((year+1).toString()+"-"+(year+2).toString()+"-2")
        }
        dateText = dateList[0]

        val schoolList = ArrayList<String>()
        schoolList.add("福州大学")
        schoolText = schoolList[0]

        val collegeList = ArrayList<String>()
        collegeList.add("数学与计算机科学学院")
        collegeText = collegeList[0]

        val courseNameList = ArrayList<String>()
        //classNameList.add("工程实践")
        courseNameList.add("其他")
        if (courseNameList.size==1){
            courseType = true
        }else{
            courseText = courseNameList[0]
        }

        dateSpinner = findViewById<Spinner>(R.id.spinner_create_class_date)
        schoolSpinner = findViewById<Spinner>(R.id.spinner_create_class_school)
        collegeSpinner = findViewById<Spinner>(R.id.spinner_create_class_college)
        courseNameSpinner = findViewById<Spinner>(R.id.spinner_create_class_classname)

        dateAdapter = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,dateList)
        schoolAdapter = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,schoolList)
        collegeAdapter = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,collegeList)
        courseNameAdapter = ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,courseNameList)

        dateSpinner.adapter = dateAdapter
        schoolSpinner.adapter = schoolAdapter
        collegeSpinner.adapter = collegeAdapter
        courseNameSpinner.adapter = courseNameAdapter

        className = findViewById(R.id.createClassName)
        courseName = findViewById<EditText>(R.id.create_class_newClass)

        dateSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dateText = dateList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        schoolSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                schoolText = schoolList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        collegeSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                collegeText = collegeList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        courseNameSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position==courseNameList.lastIndex){
                    courseName.visibility = View.VISIBLE
                    courseType = true
                }else{
                    courseName.visibility = View.GONE
                    courseType = false
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        button1.setOnClickListener {
            if (courseType&&courseName.text.toString()==""){
                Toast.makeText(this,"课程名不可为空",Toast.LENGTH_SHORT).show()
            }else if (className.text.toString()==""){
                Toast.makeText(this,"班课名不可为空",Toast.LENGTH_SHORT).show()
            }else{
                if(courseType) courseText = courseName.text.toString()
                Toast.makeText(this,"class:${className.text.toString()},date:$dateText,school:$schoolText,college:$collegeText,course:$courseText",
                    Toast.LENGTH_SHORT).show()
                val intent = Intent(this,ClassNumberActivity::class.java)
                intent.putExtra("number",number)
                startActivity(intent)
                finish()
            }
        }
        button2.setOnClickListener {
            finish()
        }
    }
}