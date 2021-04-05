package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.login.databinding.LoginFragmentBinding


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "登录"
        val binding = DataBindingUtil.inflate<LoginFragmentBinding>(
            inflater,R.layout.login_fragment,container,false
        )
        binding.registerText.setOnClickListener{view:View ->
            val registerBundle = Bundle()
            registerBundle.putString("title","注册")
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_phoneInputFragment,registerBundle)

        }
        binding.forgivePasswordText.setOnClickListener{view:View ->
            val registerBundle = Bundle()
            registerBundle.putString("title","忘记密码")
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_phoneInputFragment,registerBundle)
        }
        binding.loginButton.setOnClickListener { view:View ->
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_guideActivity)
            (activity as AppCompatActivity).finish()
        }
        return binding.root
    }

}