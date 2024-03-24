package com.example.oxeanbits_challenge.top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oxeanbits_challenge.MainActivityViewModel
import com.example.oxeanbits_challenge.R
import com.example.oxeanbits_challenge.ui.theme.BackgroundColor
import com.example.oxeanbits_challenge.ui.theme.ColorWord
import com.example.oxeanbits_challenge.ui.theme.Typography
import com.example.oxeanbits_challenge.bottom_sheet.filter.BottomSheetFilterWeather
import com.example.oxeanbits_challenge.bottom_sheet.filter.BottomSheetFilterWeatherViewModel
import com.example.oxeanbits_challenge.weather.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopBarView(){

    val weatherModel: WeatherViewModel = koinViewModel()
    var showSheet by remember { mutableStateOf(false) }
    val filterModel: BottomSheetFilterWeatherViewModel = koinViewModel()
    val actViewModel: MainActivityViewModel = koinViewModel()

    if (showSheet)
        BottomSheetFilterWeather {
            showSheet = false
        }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                BackgroundColor(
                    weatherModel.defineClimate(
                        state = weatherModel.getForecastData()?.weatherCode ?: 0,
                        isDay = when (weatherModel.getForecastData()?.dayOrNight) {
                            stringResource(id = R.string.day) -> true
                            stringResource(id = R.string.night) -> false
                            else -> null
                        }
                    )
                )
            )
            .height(80.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        
        Text(
            text = filterModel.getCity(),
            color = ColorWord(
                weatherModel.defineClimate(
                    state = weatherModel.getForecastData()?.weatherCode ?: 0,
                    isDay = when (weatherModel.getForecastData()?.dayOrNight) {
                        stringResource(id = R.string.day) -> true
                        stringResource(id = R.string.night) -> false
                        else -> null
                    }
                )
            ),
            style = Typography.titleLarge
        )
        
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            modifier = Modifier
                .clickable {
                    filterModel.clickFilter(
                        latitude = "",
                        longitude = "",
                        isNotEmpty = {},
                        isEmpty = {
                            weatherModel.setFilterLocation(
                                latitude = actViewModel.getLocationData()?.latitude ?: 0.00,
                                longitude = actViewModel.getLocationData()?.longitude ?: 0.00,
                            )
                        }
                    )
                },
            imageVector = Icons.Filled.Refresh,
            contentDescription = null,
            tint = ColorWord(
                weatherModel.defineClimate(
                    state = weatherModel.getForecastData()?.weatherCode ?: 0,
                    isDay = when (weatherModel.getForecastData()?.dayOrNight) {
                        stringResource(id = R.string.day) -> true
                        stringResource(id = R.string.night) -> false
                        else -> null
                    }
                )
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            modifier = Modifier
                .clickable {
                    showSheet = true
                },
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            tint = ColorWord(
                weatherModel.defineClimate(
                    state = weatherModel.getForecastData()?.weatherCode ?: 0,
                    isDay = when (weatherModel.getForecastData()?.dayOrNight) {
                        stringResource(id = R.string.day) -> true
                        stringResource(id = R.string.night) -> false
                        else -> null
                    }
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBarView()
}