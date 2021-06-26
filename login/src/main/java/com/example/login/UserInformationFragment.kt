package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.login.databinding.FragmentUserInformationBinding
import com.example.login.viewmodel.LoginViewModel
import com.example.network.RetrofitUtils
import com.example.network.api.CodeApi
import com.example.network.api.SignupBody
import com.example.network.api.SignupResponse
import retrofit2.Call
import retrofit2.Response


class UserInformationFragment : Fragment() {
    lateinit var viewModel: LoginViewModel
    lateinit var name:EditText
    lateinit var studentNumber:EditText
    lateinit var radioGroup: RadioGroup
    lateinit var manRadio:RadioButton
    lateinit var womanRadio:RadioButton
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
        manRadio = binding.userStudentButton
        womanRadio = binding.userTeacherButton

        binding.userNextButton.setOnClickListener{view:View ->
            if (name.text.toString()==""){
                Toast.makeText(context,"姓名不能为空",Toast.LENGTH_SHORT).show()
            }else if(studentNumber.text.toString()==""){
                Toast.makeText(context,"学号不能为空",Toast.LENGTH_SHORT).show()
            }else{
                val sex = when(radioGroup.checkedRadioButtonId){
                    manRadio.id -> "1"
                    womanRadio.id -> "0"
                    else -> "1"
                }
                RetrofitUtils.retrofitUtils.getService(CodeApi::class.java).register(SignupBody(viewModel.signupPhone.get().toString(),viewModel.newPassword.get().toString(),name.text.toString(),studentNumber.text.toString(),sex) )
                        .enqueue(object :retrofit2.Callback<SignupResponse>{
                            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                                Toast.makeText(context,"注册失败,请重试",Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                                when(val body = response.body()){
                                    null -> {
                                        Toast.makeText(context,"注册失败，请重试",Toast.LENGTH_SHORT).show()
                                    }
                                    else ->{
                                        Toast.makeText(context,body.msg,Toast.LENGTH_SHORT).show()
                                        if (body.code==RetrofitUtils.retrofitUtils.getSuccess()){
                                            Navigation.findNavController(view).navigate(R.id.action_userInformationFragment_to_successFragment)
                                        }
                                    }
                                }
                            }
                        })
            }
        }
        return binding.root
    }
}