package com.example.oxeanbits_challenge.koin

import com.example.oxeanbits_challenge.MainActivityViewModel
import com.example.oxeanbits_challenge.bottom_sheet.filter.BottomSheetFilterWeatherViewModel
import com.example.oxeanbits_challenge.weather.WeatherViewModel
import org.koin.dsl.module

val viewModelModule = module {

    single {
        MainActivityViewModel()
    }

    single {
        WeatherViewModel(get(), get())
    }

    single{
        BottomSheetFilterWeatherViewModel()
    }

}