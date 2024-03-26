package com.judc.walkfight.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.judc.walkfight.R
import com.judc.walkfight.Utils.Companion.replaceFragment
import com.judc.walkfight.maps.OSMLocationHandler
import com.judc.walkfight.maps.OSMMapHandler
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class OSMFragment : Fragment() {
    private lateinit var mapView: MapView
    private lateinit var osmLocationHandler: OSMLocationHandler
    private lateinit var osmMapHandler: OSMMapHandler

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: Implement waypoint logic
        val view = inflater.inflate(R.layout.osm, container, false)

        mapView = view.findViewById(R.id.osmmap)
        val fightButton: Button = view.findViewById(R.id.fight)

        osmLocationHandler = view.context?.let { OSMLocationHandler(it) }!!
        osmMapHandler = view.context?.let { OSMMapHandler(it, mapView) }!!

        val currentLocationMarker = osmMapHandler.getCurrentLocationMarker()

        osmLocationHandler.requestLocationUpdates { location ->
            osmMapHandler.updateMapLocation(
                location.latitude,
                location.longitude
            )
            currentLocationMarker.position = GeoPoint(location.latitude, location.longitude)
        }

        fightButton.setOnClickListener {
            replaceFragment(R.id.fragment_container, FightFragment(), addToBackStack = true,
                tag = "FightFragment")

            Toast.makeText(view.context, "Going to FightFragment", Toast.LENGTH_LONG).show()
            Log.d(OSMFragment().tag, "My OSM Fragment: Trying to load FightFragment")
        }

        return view
    }

    companion object {
        fun newInstance() = OSMFragment()
    }
}