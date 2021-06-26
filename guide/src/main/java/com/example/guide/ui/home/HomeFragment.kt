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
import com.example.guide.JoinClassInformationActivity
import com.example.guide.R
import com.example.guide.adapt.ClassStudentListAdapt
import com.example.guide.adapt.ClassTeacherListAdapt
import com.example.guide.data.ClassStudent
import com.example.guide.data.ClassTeacher
import com.example.guide.databinding.FragmentHomeBinding
import com.example.network.RetrofitUtils
import com.example.network.api.ClassListApi
import com.example.network.api.ClassListResponse
import com.google.android.material.tabs.TabLayout
import com.huawei.hms.hmsscankit.ScanKitActivity
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        //Toast.makeText(context,"number:$number, role:$role",Toast.LENGTH_SHORT).show()

            adapt1 = context?.let { ClassTeacherListAdapt(it,testData1,number) }!!
            adapt2 = context?.let { ClassStudentListAdapt(it,testData2,number) }!!
            binding.classCreate.adapter = adapt1
            binding.classJoin.adapter = adapt2
        if (role=="2")  getListTeacher(adapt1)
        else if(role=="3") getListStudent(adapt2)
        binding.teacherRefresh.setOnRefreshListener {
            getListTeacher(adapt1).let {
                binding.teacherRefresh.isRefreshing = false;
            }
        }
        binding.studentRefresh.setOnRefreshListener {
            getListStudent(adapt2).let {
                binding.studentRefresh.isRefreshing = false
            }
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
                        if (role=="3"){
                            Toast.makeText(context,"学生只能加入班课",Toast.LENGTH_SHORT).show()
                        }else{
                            val intent = Intent(activity, CreateClassActivity::class.java)
                            intent.putExtra("number",number)
                            startActivityForResult(intent,222)
                        }
                        true
                    }
                    R.id.joinByCodeButton -> {
                        if (role=="2"){
                            Toast.makeText(context,"教师只能创建班课",Toast.LENGTH_SHORT).show()
                        }else{
                            startActivityForResult(Intent(activity, JoinClassActivity::class.java),999)
                        }
                        true
                    }
                    R.id.joinByScanButton -> {
                        if (role=="2"){
                            Toast.makeText(context,"教师只能创建班课",Toast.LENGTH_SHORT).show()
                        }else{
                            val options =
                                    HmsScanAnalyzerOptions.Creator()
                                            .setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE).create()
                            val scanIntent = Intent(activity, ScanKitActivity::class.java)
                            scanIntent.putExtra("ScanFormatValue",options.mode)
                            startActivityForResult(scanIntent,REQUEST_CODE_SCAN_DEFAULT_MODE)
                        }
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
                    joinClass(hmsScan?.getOriginalValue().toString())
                }
            }
            999 -> {
                if (resultCode==999)
                    joinClass(data?.getStringExtra("classNumber").toString())
            }
            111 -> {
                getListStudent(adapt2)
            }
            222->{
                getListTeacher(adapt1)
            }
            else ->{}
        }
    }
    private fun joinClass(string: String){
        val temp = Intent(context, JoinClassInformationActivity::class.java)
        temp.putExtra("number",number)
        temp.putExtra("classNumber",string)
        startActivityForResult(temp,111)
    }
    private fun getListTeacher(adapt: ClassTeacherListAdapt){
        if (role=="2")
        RetrofitUtils.retrofitUtils.getService(ClassListApi::class.java).getClassListTeacher(number)
                .enqueue(object :Callback<ClassListResponse?>{
                    override fun onFailure(call: Call<ClassListResponse?>, t: Throwable) {
                        Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
                    }
                    override fun onResponse(call: Call<ClassListResponse?>, response: Response<ClassListResponse?>) {
                        when(val body = response.body()) {
                            null -> {
                                Toast.makeText(context, "加载失败,请重试", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                //Log.i("return:", body.toString())
                                testData1.clear()
                                when(val t = body.data){
                                    null ->{
                                        Toast.makeText(context, body.msg, Toast.LENGTH_SHORT).show()
                                    }else -> {
                                    for(i in t){
                                        //Log.i("classnumber",i.classnumber.toString())
                                        testData1.add(ClassTeacher(i.classsemester.toString(),"",i.classname.toString(),i.school.toString(),i.college.toString(),i.classnumber.toString()))
                                    }
                                    }
                                }
                                adapt.refresh(testData1)
                            }
                        }
                    }
                })
    }
    private fun getListStudent(adapt: ClassStudentListAdapt){
        if(role=="3")
            RetrofitUtils.retrofitUtils.getService(ClassListApi::class.java).getClassListStudent(number)
                    .enqueue(object :Callback<ClassListResponse?>{
                        override fun onFailure(call: Call<ClassListResponse?>, t: Throwable) {
                            Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<ClassListResponse?>, response: Response<ClassListResponse?>) {
                            when(val body = response.body()) {
                                null -> {
                                    Toast.makeText(context, "加载失败,请重试", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    //Log.i("return:", body.toString())
                                    testData2.clear()
                                    when(val t = body.data){
                                        null ->{
                                            Toast.makeText(context, body.msg, Toast.LENGTH_SHORT).show()
                                        }else -> {
                                        for(i in t){
                                            //Log.i("classnumber",i.classnumber.toString())
                                            testData2.add(ClassStudent(i.classsemester.toString(),"",i.classname.toString(),i.school.toString(),i.college.toString(),i.classnumber.toString(),teacher=""))
                                        }
                                    }
                                    }
                                    adapt.refresh(testData2)
                                }
                            }
                        }
                    })
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