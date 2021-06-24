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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.guide.data.SignData

class SignAllActivity : AppCompatActivity() {
    lateinit var layout: ConstraintLayout
    lateinit var button: ImageButton
    lateinit var textView: TextView
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var recyclerView: RecyclerView
    lateinit var adapt: SignAllAdapt
    lateinit var data: SignData
    lateinit var dataset:ArrayList<SignData>

    lateinit var number:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_all)
        number = intent.getStringExtra("number").toString()
        Toast.makeText(this, "查看签到: $number", Toast.LENGTH_SHORT).show()

        button = findViewById(R.id.signAllReturnButton)
        layout = findViewById(R.id.signNow)
        textView = findViewById(R.id.signNowText)
        button1 = findViewById(R.id.signNowButton1)
        button2 = findViewById(R.id.signNowButton2)
        recyclerView = findViewById(R.id.allSignList)

        button.setOnClickListener {
            finish()
        }

        data = SignData("签到","12345678")
        textView.text = data.name
        button1.setOnClickListener {
            val intent = Intent(this,SignListActivity::class.java)
            intent.putExtra("number",data.number)
            startActivity(intent)
        }
        button2.setOnClickListener {
            finish()
        }

        dataset = arrayListOf(SignData("历史签到","00000001"), SignData("历史签到","00000002"))
        adapt = SignAllAdapt(dataset,this)
        recyclerView.adapter = adapt
    }
    class SignAllAdapt(data:ArrayList<SignData>,private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        private val dataSet = ArrayList<SignData>()
        class SignViewHolder(view: View): RecyclerView.ViewHolder(view){
            val name = view.findViewById<TextView>(R.id.allSignText)
            val button = view.findViewById<Button>(R.id.allSignButton)
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
                    context.startActivity(intent)
                }
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_all_sign,parent,false)
            return SignViewHolder(view)
        }
    }
}