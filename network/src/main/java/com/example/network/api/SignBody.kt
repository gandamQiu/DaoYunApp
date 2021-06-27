package com.example.network.api

data class SignBody(val classnumber:String,
                    val longitude:Int,
                    val latitude:Int,
                    val type:Int,
                    val tid:String,
                    val duration:String?)