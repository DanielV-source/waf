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
import com.judc.walkfight.R
import com.judc.walkfight.Utils.Companion.replaceFragment
import com.judc.walkfight.coroutines.fetchDirections
import com.judc.walkfight.maps.OSMLocationHandler
import com.judc.walkfight.maps.OSMMapHandler
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
                        println("Fetched fight points $fetchedFightPoints")
                        val serializedString =
                            fetchedFightPoints.joinToString(";") { it.joinToString(",") }
                        sharedPreferences.edit().putString("fightPoints", serializedString).apply()
                        sharedPreferences.edit().putInt("difficulty", 0).apply()
                        sharedPreferences.edit().putInt("totalScore", 0).apply()
                        fightPoints = serializedString
                    }
                }
            }
        }

        mapView = view.findViewById(R.id.osmmap)
        val fightButton: Button = view.findViewById(R.id.fight)
        osmLocationHandler = OSMLocationHandler(view.context)
        osmMapHandler = OSMMapHandler(view.context, mapView)

        osmLocationHandler.requestLocationUpdates { location ->
            osmMapHandler.updateMapLocation(location.latitude, location.longitude)
            var fightReached = osmMapHandler.userReachedFigth(location.latitude, location.longitude)
            if (fightReached) {
                fightButton.visibility = View.VISIBLE
            } else {
                fightButton.visibility = View.GONE
            }
            println("Current user location: ${osmMapHandler.getCurrentLocationMarker().position}")
        }

        val coordinateStrings = fightPoints?.split(";")
        val fightPointsList = coordinateStrings?.map { it.split(",").map { it.toDouble() } }
        val startingPoint = fightPointsList?.get(0)
        var currentPoint = sharedPreferences.getInt("currentPoint", 0)
        val nextPoint = fightPointsList?.get(currentPoint)
        if (currentPoint == 0) {
            if (startingPoint != null && startingPoint.size >= 2) {
                osmMapHandler.updateNextPointLocation(startingPoint[1], startingPoint[0], false)
            } else {
                println("Error: Invalid point format")
            }
        } else {
            if (nextPoint != null && nextPoint.size >= 2) {
                osmMapHandler.updateNextPointLocation(nextPoint[1], nextPoint[0], false)
            } else {
                println("Error: Invalid point format")
            }
        }
        sharedPreferences.edit().putBoolean("finishFight", false).apply()
        sharedPreferences.edit().putInt("currentPoint", currentPoint + 1).apply()

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