package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.login.databinding.FragmentSuccessBinding

class SuccessFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSuccessBinding = DataBindingUtil.inflate<FragmentSuccessBinding>(
            inflater,R.layout.fragment_success,container,false
        )
        (activity as AppCompatActivity).supportActionBar?.title ="成功"
        binding.successNextButton.setOnClickListener{view:View->
            Navigation.findNavController(view).navigate(R.id.action_successFragment_to_loginFragment)
        }
        return binding.root
    }


}