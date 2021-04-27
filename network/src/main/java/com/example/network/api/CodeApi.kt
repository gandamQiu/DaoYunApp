package com.example.network.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 手机验证码
 */
interface CodeApi {
   @GET("sendsms")
   fun getCode(@Query("phone")phone:String):Call<Any?>
   @POST("fastlogin")
   fun loginByCode(@Body fastSignupBody: FastSignupBody):Call<LoginResponse>
   @POST("fastregister")
   fun fastRegister(@Query("phone")phone: String,@Query("verifycode")code:String):Call<SignupResponse>

}