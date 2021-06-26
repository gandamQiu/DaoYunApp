package com.example.network.api

import retrofit2.Call
import retrofit2.http.*

/**
 * 手机验证码
 */
interface CodeApi {
   @GET("sendsms")
   fun getCode(@Query("phone")phone:String):Call<Any?>
   @POST("fastlogin")
   fun loginByCode(@Query("phone")phone: String,@Query("verifycode")code:String):Call<LoginResponse>
   @POST("fastregister")
   fun fastRegister(@Query("phone")phone: String,@Query("verifycode")code:String):Call<SignupResponse>
   @POST("userlogin")
   fun loginByPassword(@Body loginBody:LoginBody):Call<LoginResponse>
   @POST("register")
   fun register(@Body signupBody: SignupBody):Call<SignupResponse>

   @PUT("userinfo")
   fun completeUserInfo(@Body userinfoBody: UserinfoBody):Call<NoDataResponse?>
}