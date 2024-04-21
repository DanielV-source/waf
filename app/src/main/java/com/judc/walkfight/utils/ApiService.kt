package com.judc.walkfight.utils

import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

data class DirectionsResponse(
    val features: List<Feature>,
)

data class Feature(
    val geometry: Geometry,
)

data class Geometry(
    val coordinates: List<List<Double>>,
)

interface OpenRouteServiceApi {
    @GET("directions/foot-walking")
    suspend fun getDirections(
        @Query("api_key") apiKey: String,
        @Query("start") start: String,
        @Query("end") end: String
    ): Response<DirectionsResponse>
}
