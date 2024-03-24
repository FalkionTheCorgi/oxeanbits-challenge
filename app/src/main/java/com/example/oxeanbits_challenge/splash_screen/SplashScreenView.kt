package com.example.oxeanbits_challenge.splash_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.oxeanbits_challenge.MainActivityViewModel
import com.example.oxeanbits_challenge.R
import com.example.oxeanbits_challenge.navigation.NavigationItem
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreenView(
    navHostController: NavHostController
){

    val splash by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
    val activityModel: MainActivityViewModel = koinViewModel()

    LaunchedEffect(activityModel.getLocationData()?.latitude) {

        if (activityModel.getLocationData()?.latitude != null) {
            delay(5000)
            navHostController.navigate(NavigationItem.Home.route)
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            modifier = Modifier
                .size(200.dp),
            composition = splash,
            iterations = LottieConstants.IterateForever
        )

    }

}