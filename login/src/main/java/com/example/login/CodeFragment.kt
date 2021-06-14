package com.example.login

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.login.databinding.FragmentCodeBinding
import com.example.login.viewmodel.LoginViewModel
import com.example.network.RetrofitUtils
import com.example.network.api.CodeApi
import com.example.network.api.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CodeFragment : Fragment() {
    val SIGNUP_SUCCESS = "200"
    lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as AppCompatActivity).get(LoginViewModel::class.java)
        (activity as AppCompatActivity).supportActionBar?.title = viewModel.phoneTitle.value
        val binding: FragmentCodeBinding = DataBindingUtil.inflate<FragmentCodeBinding>(
            inflater,R.layout.fragment_code,container,false
        )
        binding.viewmodel = viewModel
        viewModel.checkInputEmpty(binding.codeCode,binding.codeTextCode,"验证码",viewModel.signupCode)
        binding.getCodeCode.setOnClickListener {
            RetrofitUtils.retrofitUtils.getService(CodeApi::class.java).getCode(viewModel.signupPhone.get()!!).enqueue(object : Callback<Any?> {
                override fun onFailure(call: Call<Any?>, t: Throwable) {}
                override fun onResponse(call: Call<Any?>, response: Response<Any?>) {}
            })
            val duration = (1000*30).toLong()
            val interval = (1000*1).toLong()
            binding.getCodeCode.isEnabled = false
            object : CountDownTimer(duration,interval){
                override fun onFinish() {
                    binding.getCodeCode.isEnabled = true
                    binding.getCodeCode.text = "获取短信验证码"
                }
                override fun onTick(millisUntilFinished: Long) {
                    ((millisUntilFinished/1000).toString() + "秒后可用").also { binding.getCodeCode.text = it }
                }
            }.start()
        }
        binding.codeNextButton.setOnClickListener{view:View->
            when(val code = viewModel.signupCode.get()){
                null -> {
                    binding.codeCode.error = "验证码不能为空"
                    binding.codeCode.isErrorEnabled = true
                }

                "" -> {
                    binding.codeCode.error = "验证码不能为空"
                    binding.codeCode.isErrorEnabled = true
                }


                else-> {
                    when(viewModel.phoneTitle.value){
                        null -> {}
                        "快速注册" ->{
                            RetrofitUtils.retrofitUtils.getService(CodeApi::class.java).fastRegister(viewModel.signupPhone.get()!!,code)
                                    .enqueue(object :Callback<SignupResponse>{
                                        override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                                            Toast.makeText(context,"注册失败,请重试", Toast.LENGTH_SHORT).show()
                                        }

                                        override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                                            when(val body = response.body()){
                                                null ->{
                                                    Toast.makeText(context,"注册失败,请重试",Toast.LENGTH_SHORT).show()
                                                }
                                                else -> {
                                                    when(body.code){
                                                        SIGNUP_SUCCESS -> {
                                                            Navigation.findNavController(view).navigate(R.id.action_codeFragment_to_successFragment  )
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
                        else -> { Navigation.findNavController(view).navigate(R.id.action_codeFragment_to_passwordFragment) }
                    }

                }
            }
        }
        return binding.root
    }


}