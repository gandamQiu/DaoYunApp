package com.example.network.api

data class ClassListResponse(val code:String,val msg:String, val data:ArrayList<ClassInfo>?){
    data class ClassInfo(
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
