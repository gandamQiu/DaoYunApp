package com.example.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 手机验证码
 */
//todo 获取后台验证码路径
private const val CODE_PATH = ""
interface CodeApi {
   @GET(CODE_PATH)
   //todo 创建验证码bean
   fun getCode(@Query("phone")phone:String):Call<Any?>
}