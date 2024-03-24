package com.example.oxeanbits_challenge.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.oxeanbits_challenge.MainActivityViewModel
import com.example.oxeanbits_challenge.R
import com.example.oxeanbits_challenge.bottom_sheet.filter.BottomSheetFilterWeatherViewModel
import com.example.oxeanbits_challenge.components.OxeanbitsSnackBar
import com.example.oxeanbits_challenge.components.SelectItem
import com.example.oxeanbits_challenge.components.SetColorStatusBar
import com.example.oxeanbits_challenge.components.shimmerEffect
import com.example.oxeanbits_challenge.getArrayHours
import com.example.oxeanbits_challenge.getCurrentDate
import com.example.oxeanbits_challenge.getDaysOfWeek
import com.example.oxeanbits_challenge.ui.theme.BackgroundColor
import com.example.oxeanbits_challenge.ui.theme.ColorWord
import com.example.oxeanbits_challenge.ui.theme.Typography
import com.example.oxeanbits_challenge.weather.model.LocationData
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherView(){

    val model: WeatherViewModel = koinViewModel()
    val activityViewModel: MainActivityViewModel = koinViewModel()
    val scrollState = rememberScrollState()

    SetColorStatusBar(colorPage = Color.Black)

    LaunchedEffect(true) {
        model.setLocationData(
            LocationData(
                latitude = activityViewModel.getLocationData()?.latitude ?: 0.00,
                longitude = activityViewModel.getLocationData()?.longitude ?: 0.00
            )
        )
        model.getCurrentForecast()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(
                BackgroundColor(
                    model.defineClimate(
                        state = model.getForecastData()?.weatherCode ?: 0,
                        isDay = if (model.getForecastData()?.dayOrNight == "Dia") true else if (model.getForecastData()?.dayOrNight == "Noite") false else null
                    )
                )
            )
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        FilterBySelector()

        ClimateInfo()

        GeneralInfo()

    }

    if (model.getErrorMessage().isNotEmpty())
        OxeanbitsSnackBar(
            message = model.getErrorMessage(),
            onClick = { },
            dismiss = { model.setErrorMessage("") }
        )
}

@Composable
fun FilterBySelector(){

    val model: WeatherViewModel = koinViewModel()
    val filterModel: BottomSheetFilterWeatherViewModel = koinViewModel()
    val activityViewModel: MainActivityViewModel = koinViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val array = getDaysOfWeek(context)
    val wordColor = ColorWord(
        model.defineClimate(
            state = model.getForecastData()?.weatherCode ?: 0,
            isDay = when (model.getForecastData()?.dayOrNight) {
                stringResource(id = R.string.day) -> true
                stringResource(id = R.string.night) -> false
                else -> null
            }
        )
    )

    LaunchedEffect(model.getResetActualDay()) {
        model.setDay(array.map { it.day }.indexOfFirst { it == getCurrentDate() })
        model.setIndexHour(0)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {

        SelectItem(
            modifier = Modifier
                .fillMaxWidth(),
            textWidth = 150.dp,
            index = model.getIndexDay(),
            array = array.map { it.name },
            colorItems = wordColor,
            click = { before, after ->
                coroutineScope.launch {
                    model.applyDay(
                        before = before,
                        after = after,
                        array = array,
                        city = filterModel.getCity(),
                        latitude = activityViewModel.getLocationData()?.latitude.toString(),
                        longitude = activityViewModel.getLocationData()?.longitude.toString()
                    )
                }
            }
        )
        SelectItem(
            modifier = Modifier
                .fillMaxWidth(),
            index = model.getIndexHour(),
            textWidth = 150.dp,
            array = getArrayHours(),
            colorItems = wordColor,
            click = { before, after ->
                model.applyHour(
                    before = before,
                    after = after
                )
            },
        )

    }

}

@Composable
fun ClimateInfo(){

    val model: WeatherViewModel = koinViewModel()
    val climate by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            model.defineClimate(
                state = model.getForecastData()?.weatherCode ?: 0,
                isDay = if (model.getForecastData()?.dayOrNight == "Dia") true else if (model.getForecastData()?.dayOrNight == "Noite") false else null
            ).getArchive()
        )
    )

    if (model.getIsLoading()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shimmerEffect(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .shimmerEffect(),
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .shimmerEffect()
            ) {
                Box(
                    modifier = Modifier.shimmerEffect()
                )
                Box(
                    modifier = Modifier.shimmerEffect()
                )

            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            LottieAnimation(
                modifier = Modifier
                    .size(200.dp),
                composition = climate,
                iterations = LottieConstants.IterateForever
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(
                    text = model.getForecastData()?.maxTemperature ?: "0.00",
                    style = Typography.headlineMedium,
                    color = Color.Red
                )
                Text(
                    text = model.getForecastData()?.minTemperature ?: "0.00",
                    style = Typography.headlineMedium,
                    color = Color.Blue
                )

            }
        }

    }

}

@Composable
fun GeneralInfo(){

    val model: WeatherViewModel = koinViewModel()
    val wordColor = ColorWord(
        model.defineClimate(
            state = model.getForecastData()?.weatherCode ?: 0,
            isDay = when (model.getForecastData()?.dayOrNight) {
                stringResource(id = R.string.day) -> true
                stringResource(id = R.string.night) -> false
                else -> null
            }
        )
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WeatherRowView(
            isLoading = model.getIsLoading(),
            icon = R.raw.temperature,
            text = stringResource(id = R.string.temperature, model.getForecastData()?.temperature ?: ""),
            wordColor = wordColor
        )

        WeatherRowView(
            isLoading = model.getIsLoading(),
            icon = R.raw.humidity,
            text = stringResource(id = R.string.relative_humidity, model.getForecastData()?.relativeHumidity ?: ""),
            wordColor = wordColor
        )

        WeatherRowView(
            isLoading = model.getIsLoading(),
            icon = R.raw.rain_probability,
            text = stringResource(id = R.string.probability_precipitation, model.getForecastData()?.probabilityPrecipitation ?: ""),
            wordColor = wordColor
        )

        WeatherRowView(
            isLoading = model.getIsLoading(),
            icon = R.raw.ultraviolet,
            text = stringResource(id = R.string.ultraviolet_index, model.getForecastData()?.ultravioletIndex ?: ""),
            wordColor = wordColor
        )

        if (model.getForecastData()?.dayOrNight != null)
            WeatherRowView(
                isLoading = model.getIsLoading(),
                icon = R.raw.day_or_night,
                text = stringResource(id = R.string.day_or_night, model.getForecastData()?.dayOrNight ?: ""),
                wordColor = wordColor
            )

        WeatherRowView(
            isLoading = model.getIsLoading(),
            icon = R.raw.sunrise,
            text = stringResource(id = R.string.sunrise_hour, model.getForecastData()?.sunrise ?: ""),
            wordColor = wordColor
        )

        WeatherRowView(
            isLoading = model.getIsLoading(),
            icon = R.raw.nightfall,
            text = stringResource(id = R.string.nightfall_hour, model.getForecastData()?.sunset ?: ""),
            wordColor = wordColor
        )

        WeatherRowView(
            isLoading = model.getIsLoading(),
            icon = R.raw.timezone,
            text = stringResource(id = R.string.timezone, model.getForecastData()?.timezone ?: ""),
            wordColor = wordColor
        )

    }



}

@Preview(showBackground = true)
@Composable
fun WeatherPreview() {
    WeatherView()
}