package com.example.figmatest.data

import retrofit2.http.GET

interface ApiServices {

    @GET("/testAndroidData")
    suspend fun getItems(): ItemJsonModel

}