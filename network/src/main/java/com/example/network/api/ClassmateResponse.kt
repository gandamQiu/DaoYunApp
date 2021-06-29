package com.example.network.api

data class ClassmateResponse(val code:String,val msg:String, val data:ArrayList<ClassmateInfo>){
    data class ClassmateInfo(
            val name:String?,
            val number:String?,
            )
}
