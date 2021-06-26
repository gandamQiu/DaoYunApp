package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.login.databinding.FragmentUserInformationBinding
import com.example.login.viewmodel.LoginViewModel


class UserInformationFragment : Fragment() {
    lateinit var viewModel: LoginViewModel
    lateinit var name:EditText
    lateinit var studentNumber:EditText
    lateinit var radioGroup: RadioGroup
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
        name = binding.userNameInput
        studentNumber = binding.userNumberInput
        radioGroup = binding.radioGroup

        binding.userNextButton.setOnClickListener{view:View ->
            if (name.text.toString()==""){
                Toast.makeText(context,"姓名不能为空",Toast.LENGTH_SHORT).show()
            }else if(studentNumber.text.toString()==""){
                Toast.makeText(context,"学号不能为空",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"name:${name.text},number:${studentNumber.text},${radioGroup.checkedRadioButtonId.toString()}",Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_userInformationFragment_to_successFragment)
            }
        }
        return binding.root
    }
}