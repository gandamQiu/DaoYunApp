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
import com.example.login.databinding.FragmentCodeBinding
import com.example.login.viewmodel.LoginViewModel


class CodeFragment : Fragment() {
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
        viewModel.clickCodeButton(binding.getCodeCode)
        binding.codeNextButton.setOnClickListener{view:View->
            Navigation.findNavController(view).navigate(R.id.action_codeFragment_to_passwordFragment)
        }
        return binding.root
    }


}