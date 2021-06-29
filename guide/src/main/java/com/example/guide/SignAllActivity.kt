package com.example.guide

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.guide.data.SignData
import com.example.network.RetrofitUtils
import com.example.network.api.ClassListApi
import com.example.network.api.NoDataResponse
import com.example.network.api.SignAllResponse
import retrofit2.Call
import retrofit2.Response

class SignAllActivity : AppCompatActivity() {
    lateinit var button: ImageButton
    lateinit var recyclerView: RecyclerView
    lateinit var adapt: SignAllAdapt
    val dataset = ArrayList<SignData>()

    lateinit var number:String
    lateinit var classnumber:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_all)
        number = intent.getStringExtra("number").toString()//教师号
        classnumber = intent.getStringExtra("classnumber").toString()//班课号
        //Toast.makeText(this, "查看签到: $number", Toast.LENGTH_SHORT).show()

        button = findViewById(R.id.signAllReturnButton)
        recyclerView = findViewById(R.id.allSignList)

        button.setOnClickListener {
            finish()
        }

        adapt = SignAllAdapt(dataset,this,classnumber)
        getSignList(adapt)
        recyclerView.adapter = adapt
    }
    fun getSignList(adapt: SignAllAdapt){
        RetrofitUtils.retrofitUtils.getService(ClassListApi::class.java).getClassSignList(classnumber)
                .enqueue(object :retrofit2.Callback<SignAllResponse?>{
                    override fun onFailure(call: Call<SignAllResponse?>, t: Throwable) {
                        Toast.makeText(this@SignAllActivity, "加载失败,请重试", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<SignAllResponse?>, response: Response<SignAllResponse?>) {
                        when(val body = response.body()){
                            null -> {
                                Toast.makeText(this@SignAllActivity, "加载失败,请重试", Toast.LENGTH_SHORT).show()
                            }else->{
                                if (body.code==RetrofitUtils.retrofitUtils.getSuccess()){
                                    when(val t = body.data){
                                        null ->{}
                                        else ->{
                                            dataset.clear()
                                            for (i in t){
                                                if (i!=null)
                                                dataset.add(0,SignData(i.retime,i.tsignid,i.state.toInt()))
                                            }
                                            adapt.refresh(dataset)
                                        }
                                    }
                                }else{
                                    Toast.makeText(this@SignAllActivity, body.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                })
    }
    class SignAllAdapt(data:ArrayList<SignData>,private val context: Context,private val id:String): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        private val dataSet = ArrayList<SignData>()
        class SignViewHolder(view: View): RecyclerView.ViewHolder(view){
            val name = view.findViewById<TextView>(R.id.allSignText)
            val button = view.findViewById<Button>(R.id.allSignButton)
            val endButton = view.findViewById<Button>(R.id.allsignEndButton)
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
                holder.button.setOnClickListener {
                    val intent = Intent(context,SignListActivity::class.java)
                    intent.putExtra("number",dataSet[position].number)
                    intent.putExtra("id",id)
                    context.startActivity(intent)
                }
                holder.endButton.setOnClickListener {
                    if (dataSet[position].type==0){
                        Toast.makeText(context,"签到已结束",Toast.LENGTH_SHORT).show()
                    }else{
                        RetrofitUtils.retrofitUtils.getService(ClassListApi::class.java).finishSign(dataSet[position].number)
                                .enqueue(object :retrofit2.Callback<NoDataResponse?>{
                                    override fun onFailure(call: Call<NoDataResponse?>, t: Throwable) {
                                        Toast.makeText(context, "操作失败,请重试", Toast.LENGTH_SHORT).show()
                                    }

                                    override fun onResponse(call: Call<NoDataResponse?>, response: Response<NoDataResponse?>) {
                                        when(val body = response.body()){
                                            null ->{
                                                Toast.makeText(context, "操作失败,请重试", Toast.LENGTH_SHORT).show()
                                            }
                                            else ->{
                                                Toast.makeText(context, body.msg, Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                })
                    }
                }
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_all_sign,parent,false)
            return SignViewHolder(view)
        }
        fun refresh(data: ArrayList<SignData>){
            dataSet.clear()
            dataSet.addAll(data)
            notifyDataSetChanged()
        }
    }
}