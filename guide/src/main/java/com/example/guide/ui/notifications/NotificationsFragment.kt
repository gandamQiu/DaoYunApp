package com.example.guide.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.guide.R

class NotificationsFragment : Fragment() {
    lateinit var number:String

    lateinit var button: Button
    lateinit var name:TextView
    lateinit var info:TextView
    lateinit var password:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        number = (activity as AppCompatActivity).intent.getStringExtra("number")!!
        button = root.findViewById(R.id.mineButton)
        name = root.findViewById(R.id.mineName)
        info = root.findViewById(R.id.mineInfo)
        password = root.findViewById(R.id.minePassword)
        name.text = (activity as AppCompatActivity).intent.getStringExtra("name")!!
        button.setOnClickListener {
            (activity as AppCompatActivity).setResult(666)
            (activity as AppCompatActivity).finish()
        }
        return root
    }
}