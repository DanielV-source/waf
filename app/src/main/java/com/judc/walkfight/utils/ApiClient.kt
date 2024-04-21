package com.judc.walkfight.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenRouteServiceApiClient {
    private const val BASE_URL = "https://api.openrouteservice.org/v2/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: OpenRouteServiceApi = retrofit.create(OpenRouteServiceApi::class.java)
}

