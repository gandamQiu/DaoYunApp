package com.example.network

import com.example.network.api.CodeApi
import com.example.network.interceptor.HeaderInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

//todo 获取baseUrl
private const val BASEURL:String = ""

class RetrofitUtils {
    companion object{
        val httpClient:OkHttpClient = OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)//完整请求超时时长
            .connectTimeout(10, TimeUnit.SECONDS)//与服务器建立连接时长
            .readTimeout(10, TimeUnit.SECONDS)//读取服务器返回数据时长
            .writeTimeout(10, TimeUnit.SECONDS)//向服务器写入数据时长
            .retryOnConnectionFailure(true)//重连
            .followRedirects(false)//重定向
            .cache(Cache(File("sdcard/cache","okhttp"),1024))//cache
            .cookieJar(LocalCookieJar())//持久化cookieJar
            .addInterceptor(HeaderInterceptor())//添加header拦截器
            .build()
        val retrofit:Retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())//gson解析
            .build()
        val api = retrofit.create(CodeApi::class.java)
    }
    fun getApi():CodeApi = api
    /*
    fun getCode(phone:String){

        api.getCode(phone).enqueue(object :Callback<Any?>{
            override fun onFailure(call: Call<Any?>, t: Throwable) {

            }
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {

            }
        })
    }
    */
}