package com.example.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val title:LiveData<String>
    get() = _title

    private var _title = MutableLiveData<String>()
    fun setTitle(value:String){
        _title.value = value
    }
    fun getTitle():String{
        return _title.value!!
    }
}