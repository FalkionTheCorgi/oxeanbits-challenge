package com.example.oxeanbits_challenge.weather.model

import android.content.Context
import com.example.oxeanbits_challenge.R

val arrayNameOfDays = arrayOf(
    NameOfDays.MONDAY,
    NameOfDays.TUESDAY,
    NameOfDays.WEDNESDAY,
    NameOfDays.THURSDAY,
    NameOfDays.FRIDAY,
    NameOfDays.SATURDAY,
    NameOfDays.SUNDAY,
)

enum class NameOfDays {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    fun getFormattedName(context: Context): String {
        return when(this){
            MONDAY    -> context.getString(R.string.monday)
            TUESDAY   -> context.getString(R.string.tuesday)
            WEDNESDAY -> context.getString(R.string.wednesday)
            THURSDAY  -> context.getString(R.string.thursday)
            FRIDAY    -> context.getString(R.string.friday)
            SATURDAY  -> context.getString(R.string.saturday)
            SUNDAY    -> context.getString(R.string.sunday)
        }
    }
}