package com.example.guide.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.guide.CreateClassActivity
import com.example.guide.R
import com.example.guide.adapt.ClassListAdapt
import com.example.guide.data.MyClass
import com.example.guide.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var adapt1:ClassListAdapt
    lateinit var adapt2: ClassListAdapt
    private val testData1 = arrayListOf<MyClass>(MyClass("测试班课","测试"))
    private val testData2 = arrayListOf<MyClass>(MyClass("测试班课2","测试2"))
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,R.layout.fragment_home,container,false
        )

        (activity as AppCompatActivity).setSupportActionBar(binding.homeToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)

        adapt1 = ClassListAdapt(testData1,0)
        adapt2 = ClassListAdapt(testData2,1)
        binding.classCreate.adapter = adapt1
        binding.classJoin.adapter = adapt2

        binding.homeTabLayout.addOnTabSelectedListener(tabListener(binding.classCreate,binding.classJoin))

        binding.iconButton.setOnClickListener{
            val popup = PopupMenu(context,it)
            popup.inflate(R.menu.add_menu)
            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.createClassButton -> {
                        startActivityForResult(Intent(activity,CreateClassActivity::class.java),666)
                        adapt1.addItem(MyClass("test","test"))
                        true
                    }
                    R.id.joinByCodeButton -> {
                        true
                    }
                    R.id.joinByScanButton ->{
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
        if (resultCode==2)
            if (requestCode==666){
            val value1 = data!!.getStringExtra("name")
            val value2 = data!!.getStringExtra("description")
            adapt1.addItem(MyClass(value1?:"NotName",value2?:"NotDescription"))
        }

    }
    class tabListener(private val recyclerView: RecyclerView, private val recyclerView2: RecyclerView):
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