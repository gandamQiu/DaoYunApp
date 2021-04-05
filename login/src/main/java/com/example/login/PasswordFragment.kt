package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.login.databinding.FragmentPasswordBinding


class PasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "设置密码"
        val binding: FragmentPasswordBinding = DataBindingUtil.inflate<FragmentPasswordBinding>(
            inflater, R.layout.fragment_password, container, false
        )
        binding.passwordNextButton.setOnClickListener{view:View->
            when(arguments?.getString("title")){
                "注册" -> Navigation.findNavController(view).navigate(R.id.action_passwordFragment_to_userInformationFragment,arguments)
                "忘记密码" -> Navigation.findNavController(view).navigate(R.id.action_passwordFragment_to_successFragment,arguments)
            }
        }
        return binding.root
    }


}