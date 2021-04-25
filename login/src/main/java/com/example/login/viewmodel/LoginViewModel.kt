package com.example.login.viewmodel

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.network.RetrofitUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private var actionType = 0
    fun getType() = actionType
    fun setType(value:Int){
        actionType = value
    }
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

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
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
    fun clickCodeButton(button: Button){
        button.setOnClickListener { view: View ->
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
    fun getLoginCodeFromNetwork(){
        val retrofitUtils:RetrofitUtils = RetrofitUtils()
        retrofitUtils.getApi().getCode(loginAccount.get()?:"").enqueue(object : Callback<Any?> {
            override fun onFailure(call: Call<Any?>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                0
            }
        })
    }
}