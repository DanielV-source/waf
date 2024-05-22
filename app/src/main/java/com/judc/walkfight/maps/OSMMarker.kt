package com.judc.walkfight.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class OSMMarker (private val context: Context, private val mapView: MapView, newIconResource: Int?){
    private var osmMarker: Marker = Marker(mapView)

    init {
        osmMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        if (newIconResource != null) {
            setIcon(newIconResource)
        }
    }

    fun setIcon(newIconResource: Int) {
        val defaultIconWidth = osmMarker.icon.intrinsicWidth * 2
        val defaultIconHeight = (osmMarker.icon.intrinsicHeight * 1.25).toInt()

        val newIconBitmap = BitmapFactory.decodeResource(context.resources, newIconResource)
        val scaledNewIconBitmap = Bitmap.createScaledBitmap(newIconBitmap, defaultIconWidth, defaultIconHeight, true)
        val newIconDrawable = BitmapDrawable(context.resources, scaledNewIconBitmap)
        osmMarker.icon = newIconDrawable
    }

    fun getMarker() : Marker {
        return osmMarker
    }

    fun setPosition(latitude: Double, longitude: Double) {
        osmMarker.position = GeoPoint(latitude, longitude)
    }
}