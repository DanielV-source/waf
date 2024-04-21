package com.judc.walkfight.fragments

import JsonFileReader
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.judc.walkfight.R
import com.judc.walkfight.Utils.Companion.replaceFragment
import com.judc.walkfight.coroutines.fetchDirections
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // TODO: Implement waypoint logic
        val view = inflater.inflate(R.layout.osm, container, false)

        val sharedPreferences =
            view.context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        //sharedPreferences.edit().clear().commit()

        var fightPoints = sharedPreferences.getString("fightPoints", null)

        if (fightPoints == null) {
            println("No coordinates loaded. Fetching...")
            val jsonObject = JsonFileReader.readJsonObject(view.context, R.raw.coordinates)
            if (jsonObject != null) {
                val pathA = jsonObject.getJSONObject("path_a")
                val start = pathA.getString("start")
                val end = pathA.getString("end")
                val coordinates = listOf(start, end)
                println("Reading path from JSON: $coordinates")

                if (coordinates.size >= 2) {
                    val fetchedFightPoints = fetchDirections(
                        getString(R.string.ors_api_key), coordinates[0], coordinates[1]
                    )
                    if (fetchedFightPoints != null) {
                        println("FETCHED FIGHT POINTS $fetchedFightPoints")
                        val serializedString =
                            fetchedFightPoints.joinToString(";") { it.joinToString(",") }
                        sharedPreferences.edit().putString("fightPoints", serializedString).apply()
                        sharedPreferences.edit().putString("difficulty", "easy").apply()
                        sharedPreferences.edit().putInt("totalScore", 0).apply()
                        fightPoints = serializedString
                    }
                }
            }
        }
        println("FIGHT POINTS $fightPoints")
        Toast.makeText(view.context, fightPoints, Toast.LENGTH_SHORT).show()

        mapView = view.findViewById(R.id.osmmap)
        val fightButton: Button = view.findViewById(R.id.fight)
        osmLocationHandler = OSMLocationHandler(view.context)
        osmMapHandler = OSMMapHandler(view.context, mapView)

        osmLocationHandler.requestLocationUpdates { location ->
            osmMapHandler.updateMapLocation(location.latitude, location.longitude)
            println("Current user location: ${osmMapHandler.getCurrentLocationMarker().position}")
        }

        val coordinateStrings = fightPoints?.split(";")
        val fightPointsList = coordinateStrings?.map { it.split(",").map { it.toDouble() } }
        val startingPoint = fightPointsList?.get(0)
        println("Starting at $startingPoint")
        sharedPreferences.edit().putInt("currentPoint", 0).apply()

        if (startingPoint != null && startingPoint.size >= 2) {
            osmMapHandler.updateNextPointLocation(startingPoint[1], startingPoint[0])
        } else {
            println("Error: Invalid starting point format")
        }

        fightButton.setOnClickListener {
            replaceFragment(
                R.id.fragment_container,
                FightFragment(),
                addToBackStack = true,
                tag = "FightFragment"
            )

            Toast.makeText(view.context, "Going to FightFragment", Toast.LENGTH_LONG).show()
            Log.d(OSMFragment().tag, "My OSM Fragment: Trying to load FightFragment")
        }

        return view
    }

    companion object {
        fun newInstance() = OSMFragment()
    }
}