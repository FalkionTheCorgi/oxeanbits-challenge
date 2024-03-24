package com.example.oxeanbits_challenge.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oxeanbits_challenge.MainActivityViewModel
import com.example.oxeanbits_challenge.components.SetColorStatusBar
import com.example.oxeanbits_challenge.splash_screen.SplashScreenView
import com.example.oxeanbits_challenge.weather.WeatherView
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationView() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationItem.SplashScreen.route) {

        composable(
            route = NavigationItem.SplashScreen.route
        ) {
            PrevinePressBackButton()
            ShowTopBar(isShow = false)
            SetColorStatusBar(colorPage = Color.White)
            SplashScreenView(navHostController = navController)
        }

        composable(
            route = NavigationItem.Home.route
        ) {
            PrevinePressBackButton()
            ShowTopBar(isShow = true)
            WeatherView()
        }

    }

}

@Composable
fun PrevinePressBackButton() = BackHandler(onBack = {})

@Composable
fun ShowTopBar(isShow: Boolean){
    
    val actModel: MainActivityViewModel = koinViewModel()
    
    actModel.setShowTopBar(isShow)
    
}