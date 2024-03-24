package com.example.oxeanbits_challenge

import com.example.oxeanbits_challenge.weather.model.LocationData

fun setLocation(model: MainActivityViewModel){
    model.setLocationData(
        LocationData(
            latitude = 0.00,
            longitude = 0.00
        )
    )
}