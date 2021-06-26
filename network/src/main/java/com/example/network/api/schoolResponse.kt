package com.example.network.api

data class schoolResponse (val code:String,val msg:String,val data:ArrayList<schoolName>){
    data class schoolName(val itemvalue:String)
}