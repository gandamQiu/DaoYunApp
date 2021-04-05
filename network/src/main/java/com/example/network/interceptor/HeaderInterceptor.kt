package com.example.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * header拦截器
 */
class HeaderInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest:Request = chain.request()
        //todo 设置header参数
        val attachHeaders = mutableListOf<Pair<String,String>>(
        )
        //todo 从本地存储获得token
        val localToken = ""
        if (localToken.isNotEmpty())
            attachHeaders.add("token" to localToken)

        //生成新request
        val newBuilder = originalRequest.newBuilder()
        //设置header
        attachHeaders.forEach{newBuilder.header(it.first,it.second)}

        return chain.proceed(newBuilder.build())
    }
}