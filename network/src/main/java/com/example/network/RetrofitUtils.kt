package com.example.network

import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//todo 获取baseUrl


class RetrofitUtils private constructor() {
    private val retrofit:Retrofit
    init {
        val gson = Gson().newBuilder()
                .setLenient()
                .serializeNulls()
                .create()
        retrofit = Retrofit.Builder() .client(initOkhttpClient())
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))//gson解析
                .build()
    }
    companion object{
        private val BASEURL:String = "http://139.224.2.119:8080/"
        val retrofitUtils:RetrofitUtils by lazy(){
            RetrofitUtils()
        }
    }
    private fun initOkhttpClient(): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                //.addInterceptor(initLogInterceptor())
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

    fun <T> getService(service: Class<T>): T {

        return retrofit.create(service)
    }
}