package com.example.network.api

data class LoginResponse(val code:String,val msg:String, val data:Data){
    data class Data(val token:String,val user:User){
        data class User(val id:Int?,val number:Int?,val name:String?,val role:Int?)
    }
}
