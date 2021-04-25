package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.login.databinding.LoginFragmentBinding
import com.example.login.viewmodel.LoginViewModel
import com.google.android.material.tabs.TabLayout


class LoginFragment : Fragment() {
    lateinit var viewModel:LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(activity as AppCompatActivity).get(LoginViewModel::class.java)
        (activity as AppCompatActivity).supportActionBar?.title = "登录"
        val binding = DataBindingUtil.inflate<LoginFragmentBinding>(
            inflater,R.layout.login_fragment,container,false
        )
        binding.viewmodel = viewModel
        binding.loginTabLayout.addOnTabSelectedListener(tabSelectedListener(binding.passwordLayoutLogin,binding.codeLayoutLogin))
        binding.registerText.setOnClickListener{view:View ->
            viewModel.phoneTitle.postValue("注册")
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_phoneInputFragment)

        }
        binding.forgivePasswordText.setOnClickListener{view:View ->
            viewModel.phoneTitle.postValue("忘记密码")
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_phoneInputFragment)
        }
        binding.fastLoginText.setOnClickListener {view:View->
            viewModel.phoneTitle.postValue("快速注册")
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_phoneInputFragment)
        }
        viewModel.checkInputEmpty(binding.phoneLogin,binding.phoneTextLogin,"手机号",viewModel.loginAccount)
        viewModel.checkInputEmpty(binding.passwordLogin,binding.passwordTextLogin,"密码",viewModel.loginPassword)
        viewModel.checkInputEmpty(binding.codeLogin,binding.codeTextLogin,"验证码",viewModel.loginCode)

        viewModel.clickCodeButton(binding.getCodeLogin)

        binding.loginButton.setOnClickListener { view:View ->
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_guideActivity)
            (activity as AppCompatActivity).finish()
        }
        return binding.root
    }
    class tabSelectedListener(private val layout0:LinearLayout,private val layout1:LinearLayout): TabLayout.OnTabSelectedListener{
        override fun onTabSelected(tab: TabLayout.Tab?) {
            when(tab!!.position){
                0 ->{
                    layout0.visibility = View.VISIBLE
                    layout1.visibility = View.GONE
                }
                1 ->{
                    layout0.visibility = View.GONE
                    layout1.visibility = View.VISIBLE
                }
                else -> {}
            }
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }
    }
}