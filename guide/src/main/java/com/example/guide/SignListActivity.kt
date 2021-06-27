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
import com.example.guide.data.SignStudent
import com.example.network.RetrofitUtils
import com.example.network.api.ClassListApi
import com.example.network.api.NoSignBody
import com.example.network.api.NoSignResponse
import com.example.network.api.SignListResponse
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Response

class SignListActivity : AppCompatActivity() {
    lateinit var tabLayout:TabLayout
    lateinit var refresh:SwipeRefreshLayout
    lateinit var list1:RecyclerView
    lateinit var list2:RecyclerView
    lateinit var data1:ArrayList<String>
    lateinit var data2:ArrayList<SignStudent>
    lateinit var adapt1: SignListAdapt1
    lateinit var adapt2: SignListAdapt2
    lateinit var button:ImageButton

    lateinit var number:String
    lateinit var id:String
    var flag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_list)

        number = intent.getStringExtra("number").toString()
        id = intent.getStringExtra("id").toString()
        //Toast.makeText(this,"签到列表: $number",Toast.LENGTH_SHORT).show()

        tabLayout = findViewById<TabLayout>(R.id.signListTabLayout)
        refresh = findViewById<SwipeRefreshLayout>(R.id.signListRefresh)
        list1 = findViewById<RecyclerView>(R.id.signList1)
        list2 = findViewById<RecyclerView>(R.id.signList2)
        button = findViewById(R.id.signListReturnButton)

        data1 = ArrayList<String>()
        data2 = ArrayList<SignStudent>()
        adapt1 = SignListAdapt1(data1)
        adapt2 = SignListAdapt2(data2)
        getUnSignList(adapt1)
        list1.adapter = adapt1
        list2.adapter = adapt2

        refresh.setOnRefreshListener {
            if (flag){
                getUnSignList(adapt1).let {
                    refresh.isRefreshing = false
                }
            }else{
                getSignList(adapt2).let {
                    refresh.isRefreshing = false
                }
            }
        }

        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->{
                        list1.visibility = View.VISIBLE
                        list2.visibility = View.GONE
                        flag = true
                        getUnSignList(adapt1)
                    }
                    1 ->{
                        list1.visibility = View.GONE
                        list2.visibility = View.VISIBLE
                        flag = false
                        getSignList(adapt2)
                    }
                    else ->{}
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
        button.setOnClickListener {
            finish()
        }
    }
    fun getUnSignList(adapt1: SignListAdapt1){
        RetrofitUtils.retrofitUtils.getService(ClassListApi::class.java).getNoSignList(NoSignBody(number,id))
                .enqueue(object :retrofit2.Callback<NoSignResponse?>{
                    override fun onFailure(call: Call<NoSignResponse?>, t: Throwable) {
                        Toast.makeText(this@SignListActivity,"加载失败，请重试",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<NoSignResponse?>, response: Response<NoSignResponse?>) {
                        when(val body = response.body()){
                            null ->{
                                Toast.makeText(this@SignListActivity,"加载失败，请重试",Toast.LENGTH_SHORT).show()
                            }
                            else->{
                                if (body.code==RetrofitUtils.retrofitUtils.getSuccess()){
                                    when(val t = body.data){
                                        null -> {}
                                        else ->{
                                            data1.clear()
                                            for (i in t){
                                                data1.add("${i.number} ${i.name}")
                                            }
                                            adapt1.refresh(data1)
                                        }
                                    }
                                }else{
                                    Toast.makeText(this@SignListActivity,body.msg,Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                })
    }
    fun getSignList(adapt2: SignListAdapt2){
        RetrofitUtils.retrofitUtils.getService(ClassListApi::class.java).getSignList(number)
                .enqueue(object :retrofit2.Callback<SignListResponse?>{
                    override fun onFailure(call: Call<SignListResponse?>, t: Throwable) {
                        Toast.makeText(this@SignListActivity,"加载失败，请重试",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<SignListResponse?>, response: Response<SignListResponse?>) {
                        when(val body = response.body()){
                            null ->{
                                Toast.makeText(this@SignListActivity,"加载失败，请重试",Toast.LENGTH_SHORT).show()
                            }
                            else->{
                                if (body.code==RetrofitUtils.retrofitUtils.getSuccess()){
                                    when(val t = body.data){
                                        null -> {}
                                        else ->{
                                            when(val tt=t.studentSignList){
                                                null -> {}
                                                else ->{
                                                    data2.clear()
                                                    for(i in tt){
                                                        data2.add(SignStudent("${i.number} ${i.name}",i.distance))
                                                    }
                                                    adapt2.refresh(data2)
                                                }
                                            }
                                        }
                                    }
                                }else{
                                    Toast.makeText(this@SignListActivity,body.msg,Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                })
    }
    class SignListAdapt1(data:ArrayList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        private val dataSet = ArrayList<String>()
        class SignViewHolder(view: View):RecyclerView.ViewHolder(view){
            val name = view.findViewById<TextView>(R.id.signStudent)
        }
        init {
            dataSet.addAll(data)
        }
        override fun getItemCount(): Int {
            return dataSet.size
        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is SignViewHolder){
                holder.name.text = dataSet[position]
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_sign_student1,parent,false)
            return SignViewHolder(view)
        }
        fun refresh(newData:ArrayList<String>){
            dataSet.clear()
            dataSet.addAll(newData)
            notifyDataSetChanged()
        }
    }
    class SignListAdapt2(data:ArrayList<SignStudent>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        private val dataSet = ArrayList<SignStudent>()
        class SignViewHolder(view: View):RecyclerView.ViewHolder(view){
            val name = view.findViewById<TextView>(R.id.signStudent)
            val distance = view.findViewById<TextView>(R.id.signStudent2)
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
                holder.distance.text = "距离"+dataSet[position].distance+"公里"
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_sign_student2,parent,false)
            return SignViewHolder(view)
        }
        fun refresh(newData:ArrayList<SignStudent>){
            dataSet.clear()
            dataSet.addAll(newData)
            notifyDataSetChanged()
        }
    }
}