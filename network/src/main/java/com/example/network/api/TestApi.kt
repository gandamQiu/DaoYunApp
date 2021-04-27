package com.example.network.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

private const val CODE_PATH = "common/verify/getSimpleVerifyImage"
interface TestApi {
    @POST(CODE_PATH)
    fun getCode(@Query("apiKey")key:String, @Query("codeType")type:Int): Call<TestResponse>
}