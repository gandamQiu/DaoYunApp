package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.login.databinding.FragmentPhoneInputBinding
import com.example.login.viewmodel.LoginViewModel


class PhoneInputFragment : Fragment() {
    lateinit var viewModel:LoginViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(activity as AppCompatActivity).get(LoginViewModel::class.java)
        val title = viewModel.phoneTitle.value
        (activity as AppCompatActivity).supportActionBar?.title = title
        val binding = DataBindingUtil.inflate<FragmentPhoneInputBinding>(
            inflater,R.layout.fragment_phone_input,container,false
        )
        binding.viewmodel = viewModel
        viewModel.checkInputEmpty(binding.phonePhone,binding.phoneTextPhone,"手机号",viewModel.signupPhone)
        binding.phoneNextButton.setOnClickListener{view:View ->
            when(val phone = viewModel.signupPhone.get()){
                null -> {
                    binding.phonePhone.error="手机号不能为空"
                    binding.phonePhone.isErrorEnabled = true
                }
                "" -> {
                    binding.phonePhone.error="手机号不能为空"
                    binding.phonePhone.isErrorEnabled = true
                }
                else -> {
                    if (viewModel.checkPhoneNumber(phone)){
                        Navigation.findNavController(view).navigate(R.id.action_phoneInputFragment_to_codeFragment)
                    }else{
                        binding.phonePhone.error = "手机号格式不正确"
                        binding.phonePhone.isErrorEnabled = true
                    }
                }
            }
        }
        return binding.root
    }
}