package com.example.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.login.databinding.FragmentPasswordBinding
import com.example.login.viewmodel.LoginViewModel


class PasswordFragment : Fragment() {
    lateinit var viewModel: LoginViewModel
    lateinit var binding: FragmentPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as AppCompatActivity).get(LoginViewModel::class.java)
        val title = viewModel.phoneTitle.value
        (activity as AppCompatActivity).supportActionBar?.title = title
        binding = DataBindingUtil.inflate<FragmentPasswordBinding>(
            inflater, R.layout.fragment_password, container, false
        )
        binding.viewmodel = viewModel
        binding.newPasswordPassword.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val str = viewModel.newPassword.get()
                if (str.equals("")){
                    binding.passwordLayoutPassword.error = "密码不能为空"
                    binding.passwordLayoutPassword.isErrorEnabled = true
                }else{
                    binding.passwordLayoutPassword.isErrorEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        binding.confirmPasswordPassword.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val s1 = viewModel.confirmPassword.get()
                if (s1.equals("")){
                    binding.confirmLayoutPassword.error="确认密码不能为空"
                    binding.confirmLayoutPassword.isErrorEnabled = true
                }else{
                    val s2 = viewModel.newPassword.get()
                    if (s1.equals(s2)){
                        binding.confirmLayoutPassword.isErrorEnabled = false
                    }else{
                        binding.confirmLayoutPassword.error="两次密码不一致"
                        binding.confirmLayoutPassword.isErrorEnabled = true
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        binding.passwordNextButton.setOnClickListener{view:View->
            when(title){
                "注册" -> Navigation.findNavController(view).navigate(R.id.action_passwordFragment_to_userInformationFragment)
                "忘记密码" -> Navigation.findNavController(view).navigate(R.id.action_passwordFragment_to_successFragment)
                "快速注册" -> Navigation.findNavController(view).navigate(R.id.action_passwordFragment_to_successFragment)
                else ->{}
            }
        }
        return binding.root
    }

}