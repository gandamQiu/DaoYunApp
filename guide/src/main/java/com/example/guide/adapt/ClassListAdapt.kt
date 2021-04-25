package com.example.guide.adapt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guide.R
import com.example.guide.data.MyClass

class ClassListAdapt(private var dataSet:ArrayList<MyClass>, private val type:Int):
    RecyclerView.Adapter<ClassListAdapt.ClassViewHolder>() {
    class ClassViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nameText: TextView
        val descriptionText:TextView
        val button:Button
        init {
            nameText = view.findViewById(R.id.classItemName)
            descriptionText = view.findViewById(R.id.classItemDescription)
            button = view.findViewById(R.id.classItemButton)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.nameText.text = dataSet[position].className
        holder.descriptionText.text = dataSet[position].classDescription
        when(type){
            0 -> holder.button.text = "发布签到"
            1 -> holder.button.text = "签到"
            else -> {}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_class,parent,false)
        return ClassViewHolder(view)
    }

    fun addItem(myClass: MyClass){
        dataSet.add(0,myClass)
        notifyItemInserted(0)
    }
}