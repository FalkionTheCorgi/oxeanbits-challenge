package com.example.oxeanbits_challenge

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.oxeanbits_challenge.weather.model.LocationData

class MainActivityViewModel : ViewModel(){
    private val locationData = mutableStateOf<LocationData?>(null)
    private var showTopBar = mutableStateOf(false)

    fun setLocationData(newLocation: LocationData?){ locationData.value = newLocation }
    fun setShowTopBar(isShow: Boolean){ showTopBar.value = isShow }

    fun getLocationData() = locationData.value
    fun getShowTopBar() = showTopBar.value
}
