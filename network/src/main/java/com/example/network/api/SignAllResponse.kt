package com.example.network.api

data class SignAllResponse(val code:String,val msg:String,val data:ArrayList<signList>){
    data class signList(
            val tsignid:String,
            val retime:String,
            val state:String
    )
}
