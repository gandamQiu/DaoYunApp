package com.example.network.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST

/**
 * 手机验证码
 */
//todo 获取后台验证码路径
private const val CODE_PATH = ""
interface CodeApi {
   @POST(CODE_PATH)
   fun getCode(@Field("phone")phone:String):Call<Any?>
   @POST
   fun loginByCode(@Field("phone")phone: String,@Field("code")code:String):Call<Any?>
   @POST
   fun loginByPassword(@Field("phone")phone: String,@Field("password")password:String):Call<Any?>
}