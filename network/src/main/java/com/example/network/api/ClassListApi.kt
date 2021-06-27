package com.example.network.api

import retrofit2.Call
import retrofit2.http.*

interface ClassListApi {
    @GET("sclasslist/{student_number}")
    fun getClassListStudent(@Path("student_number")number:String): Call<ClassListResponse?>
    @GET("tclasslist/{teacherid}")
    fun getClassListTeacher(@Path("teacherid")number:String): Call<ClassListResponse?>


    @GET("studentlist/{classnumber}")
    fun getClassmateList(@Path("classnumber")number: String):Call<ClassmateResponse?>

    @GET("classinfo/{classnumber}")
    fun getClassinfo(@Path("classnumber")number: String):Call<ClassinfoResponse?>

    @POST("joinclass")
    fun joinClass(@Body classAndStudentNumberBody: ClassAndStudentNumberBody):Call<NoDataResponse?>

    @HTTP(method = "DELETE", path = "exitclass", hasBody = true)
    fun exitClass(@Body classAndStudentNumberBody: ClassAndStudentNumberBody):Call<NoDataResponse?>

    @GET("detailbycode/school")
    fun schoolList():Call<schoolResponse?>

    @GET("detailbycode/college")
    fun collegeList():Call<collegeResponse?>

    @POST("newclass")
    fun createNewClass(@Body newClassBody: NewClassBody):Call<NewClassResponse?>

    @POST("teachersign")
    fun newSign(@Body signBody: SignBody):Call<NoDataResponse?>

    @GET("classsignlist/{classnumber}")
    fun getClassSignList(@Path("classnumber")number: String):Call<SignAllResponse?>

    @PUT("finishsign/{tsignid}")
    fun finishSign(@Path("tsignid")tsignid: String):Call<NoDataResponse?>

    @POST( "nosignlist")
    fun getNoSignList(@Body noSignBody: NoSignBody):Call<NoSignResponse?>

    @GET("studentsignlist/{tsignid}")
    fun getSignList(@Path("tsignid")tsignid: String):Call<SignListResponse?>

    @POST("studentsign")
    fun studentSign(@Body studentSignBody: StudentSignBody):Call<NoDataResponse?>
}