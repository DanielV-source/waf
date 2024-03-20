package com.judc.walkfight.maps

import android.content.Context
import android.preference.PreferenceManager
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class OSMMapHandler (private val context: Context, private val mapView: MapView) {

    private val mapController: IMapController = mapView.controller
    private lateinit var currentLocationMarker: Marker

    init {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapController.setZoom(15.0)

        // Inicializar el marcador de ubicación actual
        currentLocationMarker = Marker(mapView)
        //currentLocationMarker.icon = context.resources.getDrawable(R.drawable.ic_location)
        currentLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mapView.overlays.add(currentLocationMarker)
    }

    fun updateMapLocation(latitude: Double, longitude: Double) {
        val newPoint = GeoPoint(latitude, longitude)
        mapController.setCenter(newPoint)
        currentLocationMarker.position = newPoint
    }

    fun getCurrentLocationMarker(): Marker {
        return currentLocationMarker
    }
}