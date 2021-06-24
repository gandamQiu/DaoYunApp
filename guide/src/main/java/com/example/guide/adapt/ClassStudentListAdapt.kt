package com.example.guide.adapt

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guide.ClassmateActivity
import com.example.guide.R
import com.example.guide.StudentSignActivity
import com.example.guide.data.ClassStudent

class ClassStudentListAdapt(private val context: Context, data:ArrayList<ClassStudent>,private val studentNumber:String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataSet = ArrayList<ClassStudent>()
    class ClassStudentViewHolder(view: View):RecyclerView.ViewHolder(view){
        val date: TextView = view.findViewById(R.id.classStudentDate)//学期
        val teacher:TextView = view.findViewById(R.id.classStudentTeacher)//教师
        val classname: TextView = view.findViewById(R.id.classStudentClassname)//班课
        val course: TextView = view.findViewById(R.id.classStudentCourse)//课程
        val school: TextView = view.findViewById(R.id.classStudentSchool)//学校
        val college: TextView = view.findViewById(R.id.classStudentCollege)//学院
        val layout: LinearLayout = view.findViewById(R.id.classStudentLayout)//点击弹出菜单
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
                        .inflate(R.layout.item_class_student,parent,false)
                return ClassStudentListAdapt.ClassStudentViewHolder(view)
            }
        }
    }

    fun refresh(newData:ArrayList<ClassStudent>){
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ClassStudentListAdapt.ClassStudentViewHolder){
            holder.college.text = dataSet[position].college
            holder.school.text = dataSet[position].school
            holder.course.text = dataSet[position].course
            holder.classname.text = dataSet[position].classname
            holder.date.text = dataSet[position].date
            holder.teacher.text = dataSet[position].teacher
            holder.layout.setOnClickListener { v ->
                val popup = PopupMenu(context, v)
                popup.inflate(R.menu.class_student_menu)
                popup.setOnMenuItemClickListener { it1 ->
                    when (it1.itemId) {
                        R.id.classStudentSignButton -> {
                            val intent = Intent(context, StudentSignActivity::class.java)
                            intent.putExtra("number",dataSet[position].number)
                            intent.putExtra("studentNumber",studentNumber)
                            context.startActivity(intent)
                            true
                        }
                        R.id.classStudentExitButton -> {
                            dataSet.removeAt(position)
                            notifyDataSetChanged()
                            true
                        }
                        R.id.classStudentViewButton -> {
                            val intent = Intent(context, ClassmateActivity::class.java)
                            intent.putExtra("number",dataSet[position].number)
                            context.startActivity(intent)
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