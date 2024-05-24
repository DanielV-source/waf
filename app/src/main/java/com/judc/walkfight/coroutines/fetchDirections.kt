package com.judc.walkfight.coroutines

import com.judc.walkfight.utils.DirectionsResponse
import com.judc.walkfight.utils.OpenRouteServiceApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Response

fun fetchDirections(apiKey: String, start: String, end: String): List<List<Double>>? {
    return runBlocking {
        try {
            val response: Response<DirectionsResponse> =
                withContext(Dispatchers.IO) {
                    OpenRouteServiceApiClient.apiService.getDirections(apiKey, start, end)
                }

            if (response.isSuccessful) {
                val directionsResponse = response.body()
                directionsResponse?.let {
                    val fullPathPoints = it.features[0].geometry.coordinates
                    val fullPathPointsSize = fullPathPoints.size

                    // Calculate fight points
                    val startingPoint = fullPathPoints.first()
                    val fightPoint1 = fullPathPoints[fullPathPointsSize / 4]
                    val fightPoint2 = fullPathPoints[fullPathPointsSize / 2]
                    val fightPoint3 =
                        fullPathPoints[((fullPathPointsSize * 5) / 6)] // Changed from /1.2 to * 5/6
                    val finalBoss = fullPathPoints.last()

                    // Return the list of fight points
                    listOf(startingPoint, fightPoint1, fightPoint2, fightPoint3, finalBoss)
                }
            } else {
                println("Error: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
