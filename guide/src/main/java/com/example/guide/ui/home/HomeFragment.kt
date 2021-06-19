package com.example.guide.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.guide.JoinClassActivity
import com.example.guide.LocalTestData
import com.example.guide.R
import com.example.guide.adapt.ClassListAdapt
import com.example.guide.adapt.ClassTeacherListAdapt
import com.example.guide.data.ClassTeacher
import com.example.guide.data.MyClass
import com.example.guide.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.huawei.hms.hmsscankit.ScanKitActivity
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions

class HomeFragment : Fragment() {
    private val CAMERA_REQ_CODE = 1000
    private val REQUEST_CODE_SCAN_DEFAULT_MODE = 0X01
    private lateinit var homeViewModel: HomeViewModel
    lateinit var adapt1: ClassTeacherListAdapt
    lateinit var adapt2: ClassListAdapt
    private val testData1 = LocalTestData.classTeacherList
    private val testData2 = arrayListOf<MyClass>(MyClass("测试班课2", "测试2"))
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false
        )

        (activity as AppCompatActivity).setSupportActionBar(binding.homeToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)

        adapt1 = context?.let { ClassTeacherListAdapt(it,testData1) }!!
        adapt2 = ClassListAdapt(testData2, 1)
        binding.classCreate.adapter = adapt1
        binding.classJoin.adapter = adapt2

        binding.homeTabLayout.addOnTabSelectedListener(
            tabListener(
                binding.classCreate,
                binding.classJoin
            )
        )

        binding.iconButton.setOnClickListener{
            val popup = PopupMenu(context, it)
            popup.inflate(R.menu.add_menu)
            popup.setOnMenuItemClickListener { it1 ->
                when(it1.itemId){
                    R.id.createClassButton -> {
                        /*
                        startActivityForResult(
                            Intent(activity, CreateClassActivity::class.java),
                            666
                        )
                         */
                        testData1.add(ClassTeacher("1","2","3","4","5"))
                        adapt1.refresh(testData1)
                        true
                    }
                    R.id.joinByCodeButton -> {
                        startActivity(Intent(activity,JoinClassActivity::class.java))
                        adapt2.addItem(MyClass("工程实践","2020-1"))
                        true
                    }
                    R.id.joinByScanButton -> {
                        val options =
                            HmsScanAnalyzerOptions.Creator()
                                .setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE).create()
                        val scanIntent = Intent(activity, ScanKitActivity::class.java)
                        scanIntent.putExtra("ScanFormatValue",options.mode)
                        startActivityForResult(scanIntent,REQUEST_CODE_SCAN_DEFAULT_MODE)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //adapt1.addItem(MyClass(requestCode.toString(), resultCode.toString()))
        when (requestCode) {
            REQUEST_CODE_SCAN_DEFAULT_MODE -> {
                val hmsScan: HmsScan? = data?.getParcelableExtra(ScanUtil.RESULT)
                if (!TextUtils.isEmpty(hmsScan?.getOriginalValue())) {
                    when(hmsScan?.getOriginalValue().toString()){
                        "86459875" -> Toast.makeText(
                            activity,
                            "班课不允许加入",
                            Toast.LENGTH_SHORT
                        ).show()
                        else -> Toast.makeText(
                            activity,
                            "班课已结束",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    //创建班课返回
    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==2)
            if (requestCode==666){
            val value1 = data!!.getStringExtra("name")
            val value2 = data!!.getStringExtra("description")
            adapt1.addItem(MyClass(value1?:"NotName",value2?:"NotDescription"))
        }

    }
     */

    class tabListener(
        private val recyclerView: RecyclerView,
        private val recyclerView2: RecyclerView
    ):
        TabLayout.OnTabSelectedListener{
        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab!!.position==0){
                recyclerView.visibility = View.VISIBLE
                recyclerView2.visibility = View.GONE
            }
            if (tab.position==1){
                recyclerView2.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        }
        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

    }
}