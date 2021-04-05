package com.example.network

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class LocalCookieJar:CookieJar {
    //cookie本地化存储
    private val cache:MutableList<Cookie> = mutableListOf<Cookie>()
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        //失效的cookie
        val invalidCookies:MutableList<Cookie> = ArrayList()
        //有效的cookie
        val validCookies:MutableList<Cookie> = ArrayList()
        for (cookie in cache){
            //判断是否过期
            if (cookie.expiresAt<System.currentTimeMillis()){
                invalidCookies.add(cookie)
            }else if (cookie.matches(url)){//匹配cookie对应url
                validCookies.add(cookie)
            }
        }
        //移除缓存中过期的cookie
        cache.removeAll(invalidCookies)
        //返回cookie列表
        return validCookies
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cache.addAll(cookies)
    }

}