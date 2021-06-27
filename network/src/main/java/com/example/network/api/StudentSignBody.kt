package com.example.network.api

data class StudentSignBody(
        val classnumber:String,
        val number:String,
        val longitude:Int,
        val latitude:Int,
        val name:String
)
