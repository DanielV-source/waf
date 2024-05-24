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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.judc.walkfight.R
import com.judc.walkfight.Utils.Companion.replaceFragment
import com.judc.walkfight.coroutines.fetchDirections
import com.judc.walkfight.maps.OSMLocationHandler
import com.judc.walkfight.maps.OSMMapHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

                CoroutineScope(Dispatchers.Main).launch {
                    val fetchedFightPoints = fetchDirections(
                        getString(R.string.ors_api_key), coordinates[0], coordinates[1]
                    )

                    if (fetchedFightPoints != null) {
                        println("Fetched fight points $fetchedFightPoints")
                        val serializedString =
                            fetchedFightPoints.joinToString(";") { it.joinToString(",") }
                        sharedPreferences.edit().putString("fightPoints", serializedString).apply()
                        fightPoints = serializedString
                    }
                }
            }
        }

        mapView = view.findViewById(R.id.osmmap)
        val fightButton: Button = view.findViewById(R.id.fight)
        val centerMapButton: FloatingActionButton = view.findViewById(R.id.center_map_button)
        val navigateButton: Button = view.findViewById(R.id.navigate_button)
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
        val currentPoint = sharedPreferences.getInt("currentPoint", 0)
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

            Log.d(OSMFragment().tag, "My OSM Fragment: Trying to load FightFragment")
        }

        centerMapButton.setOnClickListener {
            Log.d(OSMFragment().tag, "My OSM Fragment: Trying to center to the current location")
            val latitude = osmMapHandler.getCurrentLocationMarker().position.latitude
            val longitude = osmMapHandler.getCurrentLocationMarker().position.longitude
            osmMapHandler.setMapControllerCenter(latitude, longitude)
            Toast.makeText(view.context, "Here you are!", Toast.LENGTH_LONG).show()
        }

        navigateButton.setOnClickListener {
            Log.d(OSMFragment().tag, "My OSM Fragment: Trying change Navigate to " + osmMapHandler.getNavigate())

            osmMapHandler.setNavigate()

            navigateButton.setBackgroundResource(R.drawable.btn_navigate)
            navigateButton.text = "Navigate "
            if (osmMapHandler.getNavigate()) {
                navigateButton.setBackgroundResource(R.drawable.btn_navigate_false)
                navigateButton.text = "Unlock "

                val latitude = osmMapHandler.getCurrentLocationMarker().position.latitude
                val longitude = osmMapHandler.getCurrentLocationMarker().position.longitude
                osmMapHandler.setMapControllerCenter(latitude, longitude)
            }
        }

        return view
    }

    companion object {

        fun isBoss(context: Context?): Boolean {
            if(context == null) {
                return false
            }

            val sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
            val currentPoint = sharedPreferences.getInt("currentPoint", 0)
            val fightPoints = sharedPreferences.getString("fightPoints", "") ?: return false
            val coordinateStrings = fightPoints.split(";")
            val fightPointsList = coordinateStrings.map { it.split(",").map { it.toDouble() } }

            return (fightPointsList.size == currentPoint)
        }

    }
}