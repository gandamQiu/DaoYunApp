package com.example.guide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.guide.data.Classmate
import com.example.network.RetrofitUtils
import com.example.network.api.ClassListApi
import com.example.network.api.ClassmateResponse
import retrofit2.Call
import retrofit2.Response

class ClassmateActivity : AppCompatActivity() {
    lateinit var button:ImageButton
    lateinit var refreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var adapt: ClassmateAdapt
    lateinit var data:ArrayList<Classmate>
    lateinit var number:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classmate)
        number = this.intent.getStringExtra("number")!!
        //Toast.makeText(this,"number:$number",Toast.LENGTH_SHORT).show()

        data = ArrayList<Classmate>()

        adapt = ClassmateAdapt(data)
        recyclerView = findViewById(R.id.classmateList)
        recyclerView.adapter = adapt

        refreshLayout = findViewById(R.id.classmateRefresh)
        refreshLayout.setOnRefreshListener {
            getClassmateList(adapt).let {
                refreshLayout.isRefreshing = false
            }
        }
        //getClassmateList()
        button = findViewById(R.id.classmateReturnButton)
        button.setOnClickListener {
            finish()
        }
        getClassmateList(adapt)
    }
    fun getClassmateList(adapt: ClassmateAdapt){
        RetrofitUtils.retrofitUtils.getService(ClassListApi::class.java).getClassmateList(number)
                .enqueue(object :retrofit2.Callback<ClassmateResponse?>{
                    override fun onFailure(call: Call<ClassmateResponse?>, t: Throwable) {
                        Toast.makeText(this@ClassmateActivity, "加载失败,请重试", Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<ClassmateResponse?>, response: Response<ClassmateResponse?>) {
                        when(val body = response.body()){
                            null -> {
                                Toast.makeText(this@ClassmateActivity, "加载失败,请重试", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                if (body.code == RetrofitUtils.retrofitUtils.getSuccess()){
                                    data.clear()
                                    when(val t = body.data){
                                        null ->{
                                            Toast.makeText(this@ClassmateActivity, body.msg, Toast.LENGTH_SHORT).show()
                                        }else -> {
                                        for(i in t){
                                            if (i!=null)
                                                data.add(Classmate(i.name.toString(),"学号${i.number.toString()}"))
                                            }
                                        }
                                    }
                                    adapt.refresh(data)
                                }
                            }
                        }
                    }
                })
    }
    class ClassmateAdapt(data:ArrayList<Classmate>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        private val dataSet = ArrayList<Classmate>()
        class SignViewHolder(view: View):RecyclerView.ViewHolder(view){
            val name = view.findViewById<TextView>(R.id.classmateName)
            val exp = view.findViewById<TextView>(R.id.classmateExp)
        }
        init {
            dataSet.addAll(data)
        }
        override fun getItemCount(): Int {
            return dataSet.size
        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is SignViewHolder){
                holder.name.text = dataSet[position].name
                holder.exp.text = dataSet[position].exp
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_classmate,parent,false)
            return SignViewHolder(view)
        }
        fun refresh(newData:ArrayList<Classmate>){
            dataSet.clear()
            dataSet.addAll(newData)
            notifyDataSetChanged()
        }
    }

}