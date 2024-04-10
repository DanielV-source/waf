package com.judc.walkfight.coroutines
import com.judc.walkfight.utils.DirectionsResponse
import com.judc.walkfight.utils.OpenRouteServiceApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Response

fun fetchDirections(apiKey: String, start: String, end: String) {
    runBlocking {
        try {
            val response: Response<DirectionsResponse> = withContext(Dispatchers.IO) {
                OpenRouteServiceApiClient.apiService.getDirections(
                    apiKey,
                    start,
                    end
                )
            }

            if (response.isSuccessful) {
                val directionsResponse = response.body()
                directionsResponse?.let {
                    val fullPathPoints = it.features[0].geometry.coordinates
                    val fullPathPointsSize = fullPathPoints.size

                    // Get elements from the calculated positions
                    val startingPoint = fullPathPoints.first()
                    val fightPoint1 = fullPathPoints[fullPathPointsSize / 4]
                    val fightPoint2 = fullPathPoints[fullPathPointsSize / 2]
                    val fightPoint3 = fullPathPoints[(fullPathPointsSize / 1.2).toInt()]
                    val finalBoss = fullPathPoints.last()

                    println("Starting point: $startingPoint")
                    println("First challenge: $fightPoint1")
                    println("Second challenge: $fightPoint2")
                    println("Third challenge: $fightPoint3")
                    println("Final boss: $finalBoss")
                }
            } else {
                println("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}