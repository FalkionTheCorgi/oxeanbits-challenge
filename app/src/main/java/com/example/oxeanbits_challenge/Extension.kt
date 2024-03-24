package com.example.oxeanbits_challenge

import android.content.Context
import com.example.oxeanbits_challenge.weather.model.DaysOfWeek
import com.example.oxeanbits_challenge.weather.model.arrayNameOfDays
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun medianInt(array: ArrayList<Int>?): Double{
    return if(array != null && array.size > 0) {
        array.sort()

        val size = array.size
        return if (size % 2 == 0) {
            val mid = size / 2
            (array[mid - 1] + array[mid]) / 2.0
        } else {
            array[size / 2].toDouble()
        }
    } else {
        0.00
    }
}

fun medianDouble(array: ArrayList<Double>?): Double{
    return if(array != null && array.size > 0) {
        array.sort()
        val size = array.size
        if (size % 2 == 0) {
            val mid = size / 2
            (array[mid - 1] + array[mid]) / 2.0
        } else {
            array[size / 2]
        }
    } else {
        0.00
    }
}

fun getSunriseOrSunset(array: ArrayList<String>, date: String): String? {
    return array.find { it.startsWith(date) }?.substring(11)
}

fun getDaysOfWeek(context: Context): MutableList<DaysOfWeek> {
    val currentDate = LocalDate.now()
    val startOfWeek = currentDate.minusDays(currentDate.dayOfWeek.value.toLong() - 1)
    val endOfWeek = startOfWeek.plusDays(6)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val daysOfWeek = mutableListOf<DaysOfWeek>()
    var currentDay = startOfWeek
    var indexName = 0
    while (currentDay <= endOfWeek) {
        val formattedDate = currentDay.format(formatter)
        daysOfWeek.add(
            DaysOfWeek(
                name = arrayNameOfDays[indexName].getFormattedName(context),
                day = formattedDate
            )
        )
        indexName += 1
        currentDay = currentDay.plusDays(1)
    }
    return daysOfWeek
}

fun getArrayHours(): List<String> {
    val array = mutableListOf(
        "Selecione"
    )
    array.addAll(Array(24) { i -> "%02d:00".format(i) })
    return array
}

fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}

fun hourToInt(horario: String): Int {
    return horario.substringBefore(":").toInt()
}

fun compareHours(hourActual: String, sunrise: String, sunset: String): Int {
    val (hour, minute) = hourActual.split(":").map { it.toInt() }
    val (hourSunrise, minuteSunrise) = sunrise.split(":").map { it.toInt() }
    val (hourSunset, minuteSunset) = sunset.split(":").map { it.toInt() }

    if (hour > hourSunrise || (hour == hourSunrise && minute >= minuteSunrise)) {
        if (hour < hourSunset || (hour == hourSunset && minute <= minuteSunset)) {
            return 0
        }
        return 1
    }
    return -1
}

fun getIndexByDate(date: String, array: ArrayList<String>): Int{
    return array.indexOfFirst { it == date }
}

fun getFormattedDoubleTwoDecimalCases(value: Double): String {
    val format = "%.2f"
    return format
        .format(value)
        .replace(".", ",")
}