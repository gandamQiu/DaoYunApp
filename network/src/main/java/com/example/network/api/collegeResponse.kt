package com.example.network.api

data class collegeResponse(val code:String,val msg:String,val data:ArrayList<collegeName>){
    data class collegeName(val itemvalue:String)
}
