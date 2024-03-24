package com.example.oxeanbits_challenge.weather

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.oxeanbits_challenge.R
import com.example.oxeanbits_challenge.components.shimmerEffect

@Composable
fun WeatherRowView(
    isLoading: Boolean,
    icon: Int,
    text: String,
    wordColor: Color
){

    val lottieIcon by rememberLottieComposition(LottieCompositionSpec.RawRes(icon))

    if (isLoading){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shimmerEffect(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start)
        ) {

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .shimmerEffect(),
            )

            Box(
                modifier = Modifier
                    .shimmerEffect()
            )

        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(
                    "${stringResource(id = R.string.test_tag_weather_data_)}$text"
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start)
        ) {

            LottieAnimation(
                modifier = Modifier.size(32.dp),
                composition = lottieIcon,
                iterations = LottieConstants.IterateForever
            )

            Text(
                text = text,
                color = wordColor
            )

        }

    }

}