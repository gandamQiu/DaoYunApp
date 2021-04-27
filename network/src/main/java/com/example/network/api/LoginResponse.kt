package com.example.network.api

data class LoginResponse(val code:String,val msg:String, val data:Data){
    data class Data(val token:String)
}
