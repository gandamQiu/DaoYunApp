package com.example.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ClassListApi {
    @GET("sclasslist/{student_number}")
    fun getClassListStudent(@Path("student_number")number:String): Call<ClassListResponse?>
    @GET("tclasslist/{teacherid}")
    fun getClassListTeacher(@Path("teacherid")number:String): Call<ClassListResponse?>



    //这是班级信息，学生列表没写
    @GET("classinfo/{classnumber}")
    fun getClassmateList(@Path("classnumber")number: String):Call<ClassmateResponse?>

    @GET("classinfo/{classnumber}")
    fun getClassinfo(@Path("classnumber")number: String):Call<ClassinfoResponse?>
}