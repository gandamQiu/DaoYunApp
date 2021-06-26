package com.example.login

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.login.databinding.LoginFragmentBinding
import com.example.login.viewmodel.LoginViewModel
import com.example.network.RetrofitUtils
import com.example.network.api.CodeApi
import com.example.network.api.FastSignupBody
import com.example.network.api.LoginBody
import com.example.network.api.LoginResponse
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {
    val LOGIN_SUCCESS = "200"
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

        binding.getCodeLogin.setOnClickListener {
            when(val phone = viewModel.loginAccount.get()){
                null -> {
                    binding.phoneLogin.error="手机号不能为空"
                    binding.phoneLogin.isErrorEnabled = true
                }
                else -> {
                    if (viewModel.checkPhoneNumber(phone)){
                        RetrofitUtils.retrofitUtils.getService(CodeApi::class.java).getCode(phone).enqueue(object :Callback<Any?>{
                            override fun onFailure(call: Call<Any?>, t: Throwable) {}
                            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {}
                        })
                        val duration = (1000*30).toLong()
                        val interval = (1000*1).toLong()
                        binding.getCodeLogin.isEnabled = false
                        object : CountDownTimer(duration,interval){
                            override fun onFinish() {
                                binding.getCodeLogin.isEnabled = true
                                binding.getCodeLogin.text = "获取短信验证码"
                            }
                            override fun onTick(millisUntilFinished: Long) {
                                ((millisUntilFinished/1000).toString() + "秒后可用").also { binding.getCodeLogin.text = it }
                            }
                        }.start()
                    }else{
                        binding.phoneLogin.error = "手机号格式不正确"
                        binding.phoneLogin.isErrorEnabled = true
                    }
                }
            }

        }
        binding.loginByPasswordButton.setOnClickListener { view:View ->
            when(val phone = viewModel.loginAccount.get()) {
                null -> {
                    binding.phoneLogin.error = "手机号不能为空"
                    binding.phoneLogin.isErrorEnabled = true
                }
                "" -> {
                    binding.phoneLogin.error = "手机号不能为空"
                    binding.phoneLogin.isErrorEnabled = true
                }
                else -> {
                    when(viewModel.loginPassword.get()){
                        null -> {
                            binding.passwordLogin.error = "密码不能为空"
                            binding.passwordLogin.isErrorEnabled = true
                        }
                        "" ->{
                            binding.passwordLogin.error = "密码不能为空"
                            binding.passwordLogin.isErrorEnabled = true
                        }
                        else -> {
                            RetrofitUtils.retrofitUtils.getService(CodeApi::class.java)
                                .loginByPassword(LoginBody(viewModel.loginAccount.get()!!,viewModel.loginPassword.get()!!)).enqueue(object : Callback<LoginResponse> {
                                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                        Toast.makeText(context,"登录失败,请重试",Toast.LENGTH_SHORT).show()
                                    }

                                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                                        when(val body = response.body()){
                                            null ->{
                                                Toast.makeText(context,"登录失败,请重试",Toast.LENGTH_SHORT).show()
                                            }
                                            else -> {
                                                Log.i("return:",body.toString())
                                                when(body.code){
                                                    LOGIN_SUCCESS -> {
                                                        val intent = Intent()
                                                        val name = body.data.user.name.toString()
                                                        val number =  body.data.user.number.toString()
                                                        val id = body.data.user.id.toString()
                                                        val role = body.data.user.role
                                                        intent.putExtra("name",name)
                                                        intent.putExtra("role",role.toString())
                                                        if (role==2) intent.putExtra("number",id)
                                                        else intent.putExtra("number",number)
                                                        Toast.makeText(context,"name:$name,number:$number,id:$id,role:$role",Toast.LENGTH_SHORT).show()
                                                        (activity as AppCompatActivity).setResult(777,intent)
                                                        (activity as AppCompatActivity).finish()
                                                    }
                                                    else -> {
                                                        Toast.makeText(context,body.msg,Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                })
                        }
                    }
                }
            }
        }
        binding.loginButton.setOnClickListener { view:View ->
            when(val phone = viewModel.loginAccount.get()){
                null -> {
                    binding.phoneLogin.error="手机号不能为空"
                    binding.phoneLogin.isErrorEnabled = true
                }
                "" -> {
                    binding.phoneLogin.error="手机号不能为空"
                    binding.phoneLogin.isErrorEnabled = true
                }
                else -> {
                    when(viewModel.loginCode.get()){
                        null -> {
                            binding.codeLogin.error = "验证码不能为空"
                            binding.codeLogin.isErrorEnabled = true
                        }
                        "" ->{
                            binding.codeLogin.error = "验证码不能为空"
                            binding.codeLogin.isErrorEnabled = true
                        }
                        else -> {
                            RetrofitUtils.retrofitUtils.getService(CodeApi::class.java)
                                    .loginByCode(FastSignupBody( viewModel.loginAccount.get()!!,viewModel.loginCode.get()!!)).enqueue(object : Callback<LoginResponse> {
                                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                            Toast.makeText(context,"登录失败,请重试",Toast.LENGTH_SHORT).show()
                                        }

                                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                                            when(val body = response.body()){
                                                null ->{
                                                    Toast.makeText(context,"登录失败,请重试",Toast.LENGTH_SHORT).show()
                                                }
                                                else -> {
                                                    when(body.code){
                                                        LOGIN_SUCCESS -> {
                                                            val intent = Intent()
                                                            val name = body.data.user.name.toString()
                                                            val number =  body.data.user.number.toString()
                                                            val id = body.data.user.id.toString()
                                                            val role = body.data.user.role
                                                            intent.putExtra("name",name)
                                                            intent.putExtra("role",role.toString())
                                                            if (role==2) intent.putExtra("number",id)
                                                            else intent.putExtra("number",number)
                                                            Toast.makeText(context,"name:$name,number:$number,id:$id,role:$role",Toast.LENGTH_SHORT).show()
                                                            (activity as AppCompatActivity).setResult(777,intent)
                                                            (activity as AppCompatActivity).finish()
                                                        }
                                                        else -> {
                                                            Toast.makeText(context,body.msg,Toast.LENGTH_SHORT).show()
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    })
                        }
                    }
                }
            }
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