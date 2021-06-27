package com.example.guide.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.guide.R

class NotificationsFragment : Fragment() {
    lateinit var number:String

    lateinit var button: Button
    lateinit var name:TextView
    lateinit var checkUpdate:TextView
    lateinit var aboutUs:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        number = (activity as AppCompatActivity).intent.getStringExtra("number")!!
        button = root.findViewById(R.id.mineButton)
        name = root.findViewById(R.id.mineName)
        checkUpdate = root.findViewById(R.id.mineCheckUpdate)
        aboutUs = root.findViewById(R.id.mineAbout)
        name.text = (activity as AppCompatActivity).intent.getStringExtra("name")!!
        //name.text = "个人中心"

        checkUpdate.setOnClickListener {
            Toast.makeText(context,"版本号:1.0.0 \n已是最新版本",Toast.LENGTH_LONG).show()
        }
        aboutUs.setOnClickListener {
            Toast.makeText(context,"工程实践1班11组：郑路伟 邱钧毅 王允斌 郭晗 \n移动端主要用于签到相关功能，以及创建和加入班课  \n所有组员都是跨专业学生，如有不足之处，请多包涵",Toast.LENGTH_LONG).show()
        }
        button.setOnClickListener {
            (activity as AppCompatActivity).setResult(666)
            (activity as AppCompatActivity).finish()
        }
        return root
    }
}