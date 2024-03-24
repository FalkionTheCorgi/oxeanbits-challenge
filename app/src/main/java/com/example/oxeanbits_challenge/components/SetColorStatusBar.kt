package com.example.oxeanbits_challenge.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetColorStatusBar(
    colorPage: Color,
){

    val systemUiController = rememberSystemUiController()

    DisposableEffect(Unit){
        systemUiController.setStatusBarColor(
            color = colorPage,
            darkIcons = false
        )
        onDispose {  }
    }

}