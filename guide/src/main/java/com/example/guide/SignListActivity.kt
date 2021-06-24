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
import com.google.android.material.tabs.TabLayout

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_list)

        number = intent.getStringExtra("number").toString()
        Toast.makeText(this,"签到列表: $number",Toast.LENGTH_SHORT).show()

        tabLayout = findViewById<TabLayout>(R.id.signListTabLayout)
        refresh = findViewById<SwipeRefreshLayout>(R.id.signListRefresh)
        list1 = findViewById<RecyclerView>(R.id.signList1)
        list2 = findViewById<RecyclerView>(R.id.signList2)
        button = findViewById(R.id.signListReturnButton)

        data1 = ArrayList<String>()
        data2 = ArrayList<SignStudent>()
        adapt1 = SignListAdapt1(data1)
        adapt2 = SignListAdapt2(data2)
        list1.adapter = adapt1
        list2.adapter = adapt2

        refresh.setOnRefreshListener {
            data1.add("未签到学生")
            data2.add(SignStudent("已签到学生","1.5"))
            adapt1.refresh(data1)
            adapt2.refresh(data2)
            refresh.isRefreshing = false
        }

        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->{
                        list1.visibility = View.VISIBLE
                        list2.visibility = View.GONE
                    }
                    1 ->{
                        list1.visibility = View.GONE
                        list2.visibility = View.VISIBLE
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