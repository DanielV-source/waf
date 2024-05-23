package com.judc.walkfight.maps

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class OSMMarker (private val context: Context, private val mapView: MapView, newIconResource: Int?){
    private var resourceId: Int = 0
    private var osmMarker: Marker = Marker(mapView)

    init {
        osmMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        if (newIconResource != null) {
            setIconWithDefaultWidthAndHeight(newIconResource)
        }
    }

    fun setIcon(newIconResource: Int?, paramIconWidth: Int?, paramIconHeight: Int?) {
        val iconResource = newIconResource ?: resourceId
        val iconWidth = paramIconWidth ?: osmMarker.icon.intrinsicWidth
        val iconHeight = paramIconHeight ?: osmMarker.icon.intrinsicHeight

        val newIconBitmap = BitmapFactory.decodeResource(context.resources, iconResource)
        val scaledNewIconBitmap = Bitmap.createScaledBitmap(newIconBitmap, iconWidth, iconHeight, true)
        val newIconDrawable = BitmapDrawable(context.resources, scaledNewIconBitmap)

        osmMarker.icon = newIconDrawable
        resourceId = iconResource
    }

    fun setIconWithDefaultWidthAndHeight(newIconResource: Int) {
        setIcon(newIconResource, null, null)
    }

    fun setIconSize(paramIconWidth: Int, paramIconHeight: Int) {
        setIcon(null, paramIconWidth,paramIconHeight)
    }

    fun getMarker() : Marker {
        return osmMarker
    }

    fun setPosition(latitude: Double, longitude: Double) {
        osmMarker.position = GeoPoint(latitude, longitude)
    }
}