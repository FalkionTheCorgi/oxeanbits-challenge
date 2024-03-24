package com.example.oxeanbits_challenge.bottom_sheet.filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.oxeanbits_challenge.MainActivityViewModel
import com.example.oxeanbits_challenge.R
import com.example.oxeanbits_challenge.bottom_sheet.model.capitalsData
import com.example.oxeanbits_challenge.ui.theme.BackgroundColor
import com.example.oxeanbits_challenge.ui.theme.ColorWord
import com.example.oxeanbits_challenge.ui.theme.Typography
import com.example.oxeanbits_challenge.weather.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFilterWeather(onDismiss: () -> Unit){

    val modalBottomSheetState = rememberModalBottomSheetState()
    val weatherModel: WeatherViewModel = koinViewModel()
    val model: BottomSheetFilterWeatherViewModel = koinViewModel()
    val actViewModel: MainActivityViewModel = koinViewModel()
    val wordColor = ColorWord(
        weatherModel.defineClimate(
            state = weatherModel.getForecastData()?.weatherCode ?: 0,
            isDay = when (weatherModel.getForecastData()?.dayOrNight) {
                stringResource(id = R.string.day) -> true
                stringResource(id = R.string.night) -> false
                else -> null
            }
        )
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            LongitudeLatitudeFields()
            
            Spacer(modifier = Modifier.height(32.dp))

            DropDownCapital()

            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    model.clickFilter(
                        latitude = model.getLatitude(),
                        longitude = model.getLongitude(),
                        isNotEmpty = {
                            weatherModel.setFilterLocation(
                                latitude = model.getLatitude().toDouble(),
                                longitude = model.getLongitude().toDouble()
                            )
                        },
                        isEmpty = {
                            weatherModel.setFilterLocation(
                                latitude = actViewModel.getLocationData()?.latitude ?: 0.00,
                                longitude = actViewModel.getLocationData()?.longitude ?: 0.00
                            )
                        }
                    )
                    onDismiss()
                },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = BackgroundColor(
                        weatherModel.defineClimate(
                            state = weatherModel.getForecastData()?.weatherCode ?: 0,
                            isDay = when (weatherModel.getForecastData()?.dayOrNight) {
                                stringResource(id = R.string.day) -> true
                                stringResource(id = R.string.night) -> false
                                else -> null
                            }
                        )
                    ),
                ),
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.filter),
                            color = wordColor,
                            style = Typography.titleLarge
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(64.dp))

        }

    }

}

@Composable
fun LongitudeLatitudeFields(){

    val model: BottomSheetFilterWeatherViewModel = koinViewModel()

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.45f),
            value = model.getLatitude(),
            readOnly = model.getLockCoordinates(),
            placeholder = { Text(text = stringResource(id = R.string.latitude)) },
            label = { Text(text = stringResource(id = R.string.latitude)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                model.setLatitude(it)
                model.verifyLatitudeLongitude(capitalsData)
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .weight(0.1f))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.45f),
            value = model.getLongitude(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null
                )
            },
            readOnly = model.getLockCoordinates(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text(text = stringResource(id = R.string.longitude)) },
            label = { Text(text = stringResource(id = R.string.longitude)) },
            onValueChange = {
                model.setLongitude(it)
                model.verifyLatitudeLongitude(capitalsData)
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Black,
                unfocusedPlaceholderColor = Color.Black
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownCapital(){

    var isExpanded by remember {
        mutableStateOf(false)
    }
    val model: BottomSheetFilterWeatherViewModel = koinViewModel()

    LaunchedEffect(true) {
        model.setSelectedOption(capitalsData.first().capital)
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { newValue ->
            isExpanded = newValue
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = model.getSelectedOption(),
            onValueChange = {},
            label = { Text(stringResource(id = R.string.capitals)) },
            trailingIcon = { TrailingIcon(expanded = isExpanded) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Black,
                unfocusedPlaceholderColor = Color.Black
            )
        )
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            capitalsData.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.capital)
                    },
                    onClick = {
                        isExpanded = false
                        model.setSelectedOption(it.capital)
                        model.selectCapital(it)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }

}