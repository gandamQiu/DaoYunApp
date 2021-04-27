package com.example.network.api

data class TestResponse(
    val desc: String,
    val result: Result,
    val statusCode: String
){
    data class Result(
        val fileName: String,
        val verifyCode: String
    )
}