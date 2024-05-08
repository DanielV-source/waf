package com.judc.walkfight.utils

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.widget.Toast

class GetSensorUtilities {
    private val sensorTypes = mapOf(
        Sensor.TYPE_ACCELEROMETER to "Accelerometer",
        Sensor.TYPE_GYROSCOPE to "Gyroscope"
    )

    fun isSensorAvailable(context: Context, sensorType: Int) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList = sensorManager.getSensorList(sensorType)
        val sensorAvailable = sensorList.isNotEmpty()
        if (!sensorAvailable) {
            Toast.makeText(context, "Sensor ${sensorTypes[sensorType]} NOT available, this may cause problems when playing", Toast.LENGTH_LONG).show()
        }
    }

    fun hasMicrophone(context: Context) {
        val packageManager = context.packageManager
        val hasMicrophone = packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
        if (!hasMicrophone) {
            Toast.makeText(context, "Microphone NOT available, this may cause problems when playing", Toast.LENGTH_LONG).show()
        }
    }
}