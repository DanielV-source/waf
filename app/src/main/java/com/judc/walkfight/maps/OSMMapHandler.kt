package com.judc.walkfight.maps

import com.judc.walkfight.R
import android.content.Context
import android.preference.PreferenceManager
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import android.content.pm.PackageManager

import org.osmdroid.views.overlay.Polygon
import kotlin.math.cos
import kotlin.math.sin
import android.app.PendingIntent
import android.content.Intent
import android.Manifest
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import kotlin.math.atan2
import kotlin.math.sqrt

class OSMMapHandler(private val context: Context, private val mapView: MapView) {

    private val mapController: IMapController = mapView.controller
    private var currentLocationMarker: OSMMarker = OSMMarker(context, mapView, null)
    private var nextPointLocationMarker: OSMMarker =
        OSMMarker(context, mapView, R.drawable.marker_default_blue_xxx)
    val sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    init {
        Configuration.getInstance()
            .load(context, PreferenceManager.getDefaultSharedPreferences(context))
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        mapController.setZoom(15.0)

        mapController.setCenter(currentLocationMarker.getMarker().position)
        mapView.overlays.add(currentLocationMarker.getMarker())
        mapView.overlays.add(nextPointLocationMarker.getMarker())
    }

    fun updateMapLocation(latitude: Double, longitude: Double) {
        val newPoint = GeoPoint(latitude, longitude)
        mapController.setCenter(newPoint)
        currentLocationMarker.setPosition(latitude, longitude)
        var isUserNear = isUserInsideRadius(
            nextPointLocationMarker.getMarker().position, 0.05, getCurrentLocationMarker().position
        )
        if (isUserNear) {
            Toast.makeText(context, "User reached the fight point", Toast.LENGTH_LONG).show();
            var currentPoint = sharedPreferences.getInt("currentPoint", 0)
            println("Current point $currentPoint")
            var fightPoints = sharedPreferences.getString("fightPoints", null)
            val coordinateStrings = fightPoints?.split(";")
            val fightPointsList = coordinateStrings?.map { it.split(",").map { it.toDouble() } }
            val nextPoint = fightPointsList?.get(currentPoint + 1)
            println("Next point at $nextPoint")
            if (nextPoint != null && nextPoint.size >= 2) {
                updateNextPointLocation(nextPoint[1], nextPoint[0], false)
            } else {
                println("Error: Invalid point format")
            }
            sharedPreferences.edit().putInt("currentPoint", currentPoint + 1).apply()
        }
    }

    fun updateNextPointLocation(latitude: Double, longitude: Double) {
        this.updateNextPointLocation(latitude, longitude, false)
    }

    fun updateNextPointLocation(latitude: Double, longitude: Double, boss: Boolean?) {
        // Remove previous circle radius
        mapView.overlays.removeAll { it is Polygon }

        if (boss == true) {
            nextPointLocationMarker.setIcon(R.drawable.marker_default_red_xxx)
        }
        nextPointLocationMarker.setPosition(latitude, longitude)
        var difficulty = sharedPreferences.getString("difficulty", null)
        println("Current difficulty: $difficulty")
        var totalScore = sharedPreferences.getInt("totalScore", 0)
        println("Game score: $totalScore")
        var circle: Polygon = createCircle(getNextPointLocationMarker().position, 0.05)
        mapView.overlays.add(circle)
    }

    fun setMapControllerCenter(latitude: Double, longitude: Double) {
        mapController.setCenter(GeoPoint(latitude, longitude))
    }

    fun getCurrentLocationMarker(): Marker {
        return currentLocationMarker.getMarker()
    }

    fun getNextPointLocationMarker(): Marker {
        return nextPointLocationMarker.getMarker()
    }

    fun createCircle(center: GeoPoint, radius: Double): Polygon {
        val numberOfSides = 100
        val circlePoints = mutableListOf<GeoPoint>()
        val distanceX = radius / (111.32 * cos(center.latitude * Math.PI / 180))
        val distanceY = radius / 110.574

        for (i in 0 until numberOfSides) {
            val theta = (2.0 * Math.PI * i / numberOfSides).toFloat()
            val x = distanceX * sin(theta)
            val y = distanceY * cos(theta)
            val point = GeoPoint(center.latitude + y, center.longitude + x)
            circlePoints.add(point)
        }

        val polygon = Polygon()
        polygon.points = circlePoints
        polygon.strokeColor = 0xFF0000FF.toInt()
        return polygon
    }

    private fun isUserInsideRadius(
        center: GeoPoint, radius: Double, userPosition: GeoPoint
    ): Boolean {
        val R = 6371
        val latDistance = Math.toRadians(userPosition.latitude - center.latitude)
        val lonDistance = Math.toRadians(userPosition.longitude - center.longitude)
        val a =
            sin(latDistance / 2) * sin(latDistance / 2) + (cos(Math.toRadians(center.latitude)) * cos(
                Math.toRadians(userPosition.latitude)
            ) * sin(lonDistance / 2) * sin(
                lonDistance / 2
            ))
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val distance = R * c
        return distance <= radius
    }

}