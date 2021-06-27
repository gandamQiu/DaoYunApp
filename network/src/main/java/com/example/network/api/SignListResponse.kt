package com.example.network.api

data class SignListResponse(val code:String,val msg:String,val data:SignListData){
    data class SignListData(val number:Int,val studentSignList:ArrayList<SignStudent>){
        data class SignStudent(
                val name:String,
                val number: String,
                val distance:String
        )
    }
}
