package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.login.databinding.FragmentUserInformationBinding


class UserInformationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "设置个人信息"
        val binding: FragmentUserInformationBinding = DataBindingUtil.inflate<FragmentUserInformationBinding>(
            inflater,R.layout.fragment_user_information,container,false
        )
        binding.userNextButton.setOnClickListener{view:View ->
            Navigation.findNavController(view).navigate(R.id.action_userInformationFragment_to_successFragment,arguments)
        }
        return binding.root
    }
}