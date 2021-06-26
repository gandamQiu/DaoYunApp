package com.example.network.api

data class ClassinfoResponse(val code:String,val msg:String,val data:Info){
    data class Info(
            val id:Int?,
            val classnumber:String?,
            val classname:String?,
            val classsemester:String?,
            val teacherid:Int?,
            val school:String?,
            val college:String?,
            val state:Int?
    )
}
