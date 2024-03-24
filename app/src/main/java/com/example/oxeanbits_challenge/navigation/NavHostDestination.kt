package com.example.oxeanbits_challenge.navigation

enum class NavHostDestination {
    SPLASH_SCREEN,
    HOME
}

sealed class NavigationItem(val route : String){
    data object SplashScreen : NavigationItem(NavHostDestination.SPLASH_SCREEN.name)
    data object Home : NavigationItem(NavHostDestination.HOME.name)
}