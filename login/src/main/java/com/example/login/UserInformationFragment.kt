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
import com.example.login.databinding.FragmentUserInformationBinding
import com.example.login.viewmodel.LoginViewModel


class UserInformationFragment : Fragment() {
    lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity as AppCompatActivity).get(LoginViewModel::class.java)
        val title = viewModel.phoneTitle.value
        (activity as AppCompatActivity).supportActionBar?.title = title
        val binding: FragmentUserInformationBinding = DataBindingUtil.inflate<FragmentUserInformationBinding>(
            inflater,R.layout.fragment_user_information,container,false
        )
        binding.viewmodel = viewModel
        binding.userNextButton.setOnClickListener{view:View ->
            Navigation.findNavController(view).navigate(R.id.action_userInformationFragment_to_successFragment)
        }
        return binding.root
    }
}