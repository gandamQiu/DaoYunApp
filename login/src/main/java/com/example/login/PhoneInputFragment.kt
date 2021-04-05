package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.login.databinding.FragmentPhoneInputBinding


class PhoneInputFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val title = arguments?.getString("title")
        (activity as AppCompatActivity).supportActionBar?.title = title
        val binding = DataBindingUtil.inflate<FragmentPhoneInputBinding>(
            inflater,R.layout.fragment_phone_input,container,false
        )
        binding.phoneNextButton.setOnClickListener{view:View ->
            Navigation.findNavController(view).navigate(R.id.action_phoneInputFragment_to_codeFragment,arguments)
        }
        return binding.root
    }
}