package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.login.databinding.FragmentCodeBinding


class CodeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "验证码"
        val binding: FragmentCodeBinding = DataBindingUtil.inflate<FragmentCodeBinding>(
            inflater,R.layout.fragment_code,container,false
        )
        binding.codeNextButton.setOnClickListener{view:View->
            Navigation.findNavController(view).navigate(R.id.action_codeFragment_to_passwordFragment,arguments)
        }
        return binding.root
    }


}