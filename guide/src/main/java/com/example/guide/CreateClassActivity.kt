package com.example.guide

import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.network.RetrofitUtils
import com.example.network.api.*
import retrofit2.Call
import retrofit2.Response

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

    val courseNameList = ArrayList<String>()
    val schoolList = ArrayList<String>()
    val collegeList = ArrayList<String>()

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
        dateText = dateList[0].toString()


        schoolList.add("请选择大学")
        schoolText = schoolList[0]


        collegeList.add("请选择学院")
        collegeText = collegeList[0]

        courseNameList.add("请选择课程")
        courseNameList.add("工程实践")
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

        RetrofitUtils.retrofitUtils.getService(ClassListApi::class.java).schoolList()
                .enqueue(object :retrofit2.Callback<schoolResponse?>{
                    override fun onResponse(call: Call<schoolResponse?>, response: Response<schoolResponse?>) {
                        when(val body = response.body()){
                            null -> {
                                Toast.makeText(this@CreateClassActivity,"获取列表出错，请重试", Toast.LENGTH_SHORT).show()
                            }
                            else ->{
                                if (body.code == RetrofitUtils.retrofitUtils.getSuccess()){
                                    when(val t = body.data){
                                        null->{}
                                        else->{
                                            for (i in t){
                                                schoolList.add(i.itemvalue)
                                            }
                                            schoolAdapter = ArrayAdapter<String>(this@CreateClassActivity,R.layout.support_simple_spinner_dropdown_item,schoolList)
                                        }
                                    }
                                }else{
                                    Toast.makeText(this@CreateClassActivity,body.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<schoolResponse?>, t: Throwable) {
                        Toast.makeText(this@CreateClassActivity,"获取列表出错，请重试", Toast.LENGTH_SHORT).show()
                    }
                })
        RetrofitUtils.retrofitUtils.getService(ClassListApi::class.java).collegeList()
                .enqueue(object :retrofit2.Callback<collegeResponse?>{
                    override fun onResponse(call: Call<collegeResponse?>, response: Response<collegeResponse?>) {
                        when(val body = response.body()){
                            null -> {
                                Toast.makeText(this@CreateClassActivity,"获取列表出错，请重试", Toast.LENGTH_SHORT).show()
                            }
                            else ->{
                                if (body.code == RetrofitUtils.retrofitUtils.getSuccess()){
                                    when(val t = body.data){
                                        null->{}
                                        else->{
                                            for (i in t){
                                                collegeList.add(i.itemvalue)
                                            }
                                            collegeAdapter = ArrayAdapter<String>(this@CreateClassActivity,R.layout.support_simple_spinner_dropdown_item,collegeList)
                                        }
                                    }
                                }else{
                                    Toast.makeText(this@CreateClassActivity,body.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<collegeResponse?>, t: Throwable) {
                        Toast.makeText(this@CreateClassActivity,"获取列表出错，请重试", Toast.LENGTH_SHORT).show()
                    }
                })

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
                    courseText = courseNameList[position]
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
                val classnameText:String = className.text.toString()
                RetrofitUtils.retrofitUtils.getService(ClassListApi::class.java).createNewClass(NewClassBody("$classnameText $courseText",dateText,number,schoolText,collegeText))
                        .enqueue(object :retrofit2.Callback<NewClassResponse?>{
                            override fun onFailure(call: Call<NewClassResponse?>, t: Throwable) {
                                Toast.makeText(this@CreateClassActivity,"创建班课出错，请重试", Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(call: Call<NewClassResponse?>, response: Response<NewClassResponse?>) {
                                when(val body = response.body()){
                                    null -> {
                                        Toast.makeText(this@CreateClassActivity,"创建班课出错，请重试", Toast.LENGTH_SHORT).show()
                                    }else ->{
                                        if (body.code==RetrofitUtils.retrofitUtils.getSuccess()){
                                            val classnumber = body.data.toString()
                                            val intent = Intent(this@CreateClassActivity,ClassNumberActivity::class.java)
                                            intent.putExtra("number",classnumber)
                                            startActivity(intent)
                                            finish()
                                        }else{
                                            Toast.makeText(this@CreateClassActivity,body.msg, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        })
                //Toast.makeText(this,"class:${className.text.toString()},date:$dateText,school:$schoolText,college:$collegeText,course:$courseText", Toast.LENGTH_SHORT).show()
            }
        }
        button2.setOnClickListener {
            finish()
        }
    }

}