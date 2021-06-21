package com.example.guide.adapt

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.guide.R
import com.example.guide.SignActivity
import com.example.guide.data.ClassTeacher

class ClassTeacherListAdapt(private val context: Context,data:ArrayList<ClassTeacher>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataSet = ArrayList<ClassTeacher>()
    class ClassTeacherViewHolder(view: View):RecyclerView.ViewHolder(view){
        val date:TextView = view.findViewById(R.id.classTeacherDate)//学期
        val classname:TextView = view.findViewById(R.id.classTeacherClassname)//班课
        val course:TextView = view.findViewById(R.id.classTeacherCourse)//课程
        val school:TextView = view.findViewById(R.id.classTeacherSchool)//学校
        val college:TextView = view.findViewById(R.id.classTeacherCollege)//学院
        val layout: LinearLayout = view.findViewById(R.id.classTeacherLayout)//点击弹出菜单
    }
    class EmptyViewHolder(view: View):RecyclerView.ViewHolder(view){}
    class NomoreViewHolder(view: View):RecyclerView.ViewHolder(view){}
    init {
        dataSet.addAll(data)
    }
    override fun getItemCount(): Int {
        return dataSet.size+1
    }

    override fun getItemViewType(position: Int): Int {
        if (dataSet.isEmpty()) return 0
        if (dataSet.lastIndex+1==position) return 1
        return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType){
            0 ->{
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_nodata,parent,false)
                return EmptyViewHolder(view)
            }
            1 ->{
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_nomoredata,parent,false)
                return NomoreViewHolder(view)
            }
            else ->{
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_class_teacher,parent,false)
                return ClassTeacherViewHolder(view)
            }
        }
    }

    fun refresh(newData:ArrayList<ClassTeacher>){
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ClassTeacherViewHolder){
            holder.college.text = dataSet[position].college
            holder.school.text = dataSet[position].school
            holder.course.text = dataSet[position].course
            holder.classname.text = dataSet[position].classname
            holder.date.text = dataSet[position].date
            holder.layout.setOnClickListener { v ->
                val popup = PopupMenu(context, v)
                popup.inflate(R.menu.class_teacher_menu)
                popup.setOnMenuItemClickListener { it1 ->
                    when (it1.itemId) {
                        R.id.classTeacherSignButton -> {
                            context.startActivity(Intent(context,SignActivity::class.java))
                            Toast.makeText(context, "签到 " + dataSet[position].course, Toast.LENGTH_SHORT).show()
                            true
                        }
                        R.id.classTeacherSettingButton -> {
                            Toast.makeText(context, "设置 " + dataSet[position].course, Toast.LENGTH_SHORT).show()
                            true
                        }
                        R.id.classTeacherViewButton -> {
                            Toast.makeText(context, "查看 " + dataSet[position].course, Toast.LENGTH_SHORT).show()
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }
    }
}