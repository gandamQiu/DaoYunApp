package com.example.login.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class LoginViewModel : ViewModel() {


    val loginAccount = ObservableField<String>()//登录手机号
    val loginPassword = ObservableField<String>()//登录密码
    val loginCode = ObservableField<String>()//登录验证码

    val phoneTitle:MutableLiveData<String> = MutableLiveData()//标题

    val signupPhone = ObservableField<String>()
    val signupCode = ObservableField<String>()
    val newPassword = ObservableField<String>()
    val confirmPassword = ObservableField<String>()

    fun checkInputEmpty(textInputLayout: TextInputLayout, textInputEditText: TextInputEditText, str:String, observableField: ObservableField<String>){
        textInputEditText.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val text = observableField.get()
                if (text.equals("")){
                    textInputLayout.error = str+"不能为空"
                    textInputLayout.isErrorEnabled = true
                }else{
                    textInputLayout.isErrorEnabled = false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        textInputLayout.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus){
                    val text = observableField.get()
                    if (text.equals("")){
                        textInputLayout.error = str+"不能为空"
                        textInputLayout.isErrorEnabled = true
                    }else{
                        textInputLayout.isErrorEnabled = false
                    }
                }
            }
    }
    /*
    fun clickCodeButton(button: Button){
        button.setOnClickListener { view: View ->
            if (checkPhoneNumber(loginAccount.get())){
                RetrofitUtils.retrofitUtils.getService(CodeApi::class.java).getCode(loginAccount.get()!!).enqueue(object :Callback<Any?>{
                    override fun onFailure(call: Call<Any?>, t: Throwable) {}
                    override fun onResponse(call: Call<Any?>, response: Response<Any?>) {}
                })
                val duration = (1000*30).toLong()
                val interval = (1000*1).toLong()
                button.isEnabled = false
                object : CountDownTimer(duration,interval){
                    override fun onFinish() {
                        button.isEnabled = true
                        button.text = "获取短信验证码"
                    }
                    override fun onTick(millisUntilFinished: Long) {
                        ((millisUntilFinished/1000).toString() + "秒后可用").also { button.text = it }
                    }
                }.start()
            }
        }
    }
    */
    fun checkPhoneNumber(phone:String?):Boolean{
        return when(phone){
            null -> false
            else -> {
                val regex = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5-9])|(166)|(19[8,9])|)\\d{8}$"
                Pattern.compile(regex).matcher(phone).matches()
            }
        }
    }
    /*
    private fun apiTest(){
        RetrofitTest.instance.getService(TestApi::class.java).getCode("lSsrlQB5be072b5ba017caa7217e4cdefab4b678ebb4e36",1).enqueue(object :Callback<TestResponse>{
            override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(
                    call: Call<TestResponse>,
                    response: Response<TestResponse>
            ) {
                when (val body = response.body()) {
                    null -> Log.i("failed", "no response")
                    else -> Log.i("success", body.statusCode + body.result.verifyCode)
                }
            }
        })
    }
    */
}