package com.example.network.api

data class NoSignResponse(val code:String,val msg:String,val data:ArrayList<NoSignStudent>){
    data class NoSignStudent(
            val number:String,
            val name:String
    )
}
