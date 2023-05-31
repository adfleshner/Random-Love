package com.flesh.dataaboutapp.dataaboutapp

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import com.squareup.seismic.ShakeDetector

fun AppCompatActivity.bindShake(listener: ShakeDetector.Listener){
    val sensorManager = getSystemService(Application.SENSOR_SERVICE) as SensorManager
    val sd = ShakeDetector(listener)
    sd.start(sensorManager, SensorManager.SENSOR_DELAY_GAME)
}

fun Context.bindShake(listener: ShakeDetector.Listener){
    val sensorManager = getSystemService(Application.SENSOR_SERVICE) as SensorManager
    val sd = ShakeDetector(listener)
    sd.start(sensorManager, SensorManager.SENSOR_DELAY_GAME)
}