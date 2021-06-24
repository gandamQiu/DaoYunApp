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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.guide.CreateClassActivity
import com.example.guide.JoinClassActivity
import com.example.guide.R
import com.example.guide.adapt.ClassStudentListAdapt
import com.example.guide.adapt.ClassTeacherListAdapt
import com.example.guide.data.ClassStudent
import com.example.guide.data.ClassTeacher
import com.example.guide.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.huawei.hms.hmsscankit.ScanKitActivity
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions

class HomeFragment : Fragment() {
    private val CAMERA_REQ_CODE = 1000
    private val REQUEST_CODE_SCAN_DEFAULT_MODE = 0X01
    lateinit var adapt1: ClassTeacherListAdapt
    lateinit var adapt2: ClassStudentListAdapt
    private val testData1 = ArrayList<ClassTeacher>()
    private val testData2 = ArrayList<ClassStudent>()

    lateinit var number:String
    lateinit var role:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false
        )

        (activity as AppCompatActivity).setSupportActionBar(binding.homeToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)

        number = (activity as AppCompatActivity).intent.getStringExtra("number")!!
        role = (activity as AppCompatActivity).intent.getStringExtra("role")!!
        Toast.makeText(context,"number:$number, role:$role",Toast.LENGTH_SHORT).show()

        adapt1 = context?.let { ClassTeacherListAdapt(it,testData1) }!!
        adapt2 = context?.let { ClassStudentListAdapt(it,testData2) }!!
        binding.classCreate.adapter = adapt1
        binding.classJoin.adapter = adapt2
        binding.teacherRefresh.setOnRefreshListener {
            testData1.add(ClassTeacher("1", "2", "3", "4", "5", "00000001"))
            adapt1.refresh(testData1)
            binding.teacherRefresh.isRefreshing = false;
        }
        binding.studentRefresh.setOnRefreshListener {
            testData2.add(ClassStudent("1", "2", "3", "4", "5", "00000001", "test"))
            adapt2.refresh(testData2)
            binding.studentRefresh.isRefreshing = false
        }
        binding.homeTabLayout.addOnTabSelectedListener(
            tabListener(
                binding.teacherRefresh,
                binding.studentRefresh
            )
        )

        binding.iconButton.setOnClickListener{
            val popup = PopupMenu(context, it)
            popup.inflate(R.menu.add_menu)
            popup.setOnMenuItemClickListener { it1 ->
                when(it1.itemId){
                    R.id.createClassButton -> {
                        startActivityForResult(
                            Intent(activity, CreateClassActivity::class.java),
                            666
                        )
                        true
                    }
                    R.id.joinByCodeButton -> {
                        startActivity(Intent(activity,JoinClassActivity::class.java))
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
    class tabListener(
        private val list1: SwipeRefreshLayout,
        private val list2: SwipeRefreshLayout
    ):
        TabLayout.OnTabSelectedListener{
        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab!!.position==0){
                list1.visibility = View.VISIBLE
                list2.visibility = View.GONE
            }
            if (tab.position==1){
                list2.visibility = View.VISIBLE
                list1.visibility = View.GONE
            }
        }
        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

    }
}