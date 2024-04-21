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

class OSMMapHandler (private val context: Context, private val mapView: MapView) {

    private val mapController: IMapController = mapView.controller
    private var currentLocationMarker: OSMMarker = OSMMarker(context, mapView, null)
    private var nextPointLocationMarker: OSMMarker = OSMMarker(context, mapView, R.drawable.marker_default_blue_xxx)

    init {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        mapController.setZoom(15.0)

        mapController.setCenter(currentLocationMarker.getMarker().position)
        mapView.overlays.add(currentLocationMarker.getMarker())
        mapView.overlays.add(nextPointLocationMarker.getMarker())
    }

    fun updateMapLocation(latitude: Double, longitude: Double) {
        //val newPoint = GeoPoint(latitude, longitude)
        //mapController.setCenter(newPoint)
        currentLocationMarker.setPosition(latitude, longitude)
    }

    fun updateNextPointLocation(latitude: Double, longitude: Double) {
        this.updateNextPointLocation(latitude, longitude, false)
    }

    fun updateNextPointLocation(latitude: Double, longitude: Double, boss: Boolean?) {
        if (boss == true) {
            nextPointLocationMarker.setIcon(R.drawable.marker_default_red_xxx)
        }
        nextPointLocationMarker.setPosition(latitude, longitude)
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
}