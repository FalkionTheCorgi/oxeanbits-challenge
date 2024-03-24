package com.example.oxeanbits_challenge.bottom_sheet.filter

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.oxeanbits_challenge.bottom_sheet.model.CapitalData

class BottomSheetFilterWeatherViewModel: ViewModel() {

    private var latitude = mutableStateOf("")
    private var longitude = mutableStateOf("")
    private var lockCoordinates = mutableStateOf(false)
    private var selectedOptionText = mutableStateOf("Selecione")
    private var city = mutableStateOf("")

    fun setLatitude(newLatitude: String) { latitude.value = newLatitude }
    fun setLongitude(newLongitude: String) { longitude.value = newLongitude }
    private fun setLockCoordinates(isLock: Boolean) { lockCoordinates.value = isLock }
    private fun setCity(newCity: String) { city.value = newCity }
    fun setSelectedOption(newSelect: String) { selectedOptionText.value = newSelect }

    fun getLatitude() = latitude.value
    fun getLongitude() = longitude.value
    fun getLockCoordinates() = lockCoordinates.value
    fun getCity() = city.value.ifEmpty { "WeatherApp" }
    fun getSelectedOption() = selectedOptionText.value

    fun clickFilter(latitude: String, longitude: String, isNotEmpty: () -> Unit, isEmpty: () -> Unit){
        if (latitude.isNotEmpty() && longitude.isNotEmpty()){
            isNotEmpty()
        } else {
            setCity("")
            isEmpty()
        }
        setLockCoordinates(false)
        setLatitude("")
        setLongitude("")
    }


    fun selectCapital(data: CapitalData){
        if (data.capital == "Selecione"){
            setLockCoordinates(false)
            setCity("")
            setLatitude("")
            setLongitude("")
        } else {
            setLockCoordinates(true)
            setCity(data.capital)
            setLatitude(data.latitude.toString())
            setLongitude(data.longitude.toString())
        }
    }

    fun verifyLatitudeLongitude(
        array: Array<CapitalData>
    ){

        val exist = array.filter { it.latitude.toString() == getLatitude() && it.longitude.toString() == getLongitude() }
        if (exist.isNotEmpty()){
            setCity(exist.first().capital)
            setSelectedOption(exist.first().capital)
        } else {
            setCity("")
        }

    }


}