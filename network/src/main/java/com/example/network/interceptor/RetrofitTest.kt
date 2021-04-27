package com.example.network.interceptor

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitTest private constructor(){
    private val BASEURL:String = "https://api.apishop.net/"
    private val retrofit:Retrofit
    init {
        retrofit = Retrofit.Builder() .client(initOkhttpClient())
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())//gson解析
            .build()
    }
    companion object{
        val instance:RetrofitTest by lazy (){
            RetrofitTest()
        }
    }
    private fun initOkhttpClient(): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(initLogInterceptor())
            .build()


        return okHttpClient
    }


    /*
    * 日志拦截器
    * */
    private fun initLogInterceptor(): HttpLoggingInterceptor {

        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.i("RetrofitTest", message)
            }
        })

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }


    /*
    * 具体服务实例化
    * */
    fun <T> getService(service: Class<T>): T {

        return retrofit.create(service)
    }
}