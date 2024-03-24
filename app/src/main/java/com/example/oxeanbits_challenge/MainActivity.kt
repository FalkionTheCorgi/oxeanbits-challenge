package com.example.oxeanbits_challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.oxeanbits_challenge.navigation.NavigationView
import com.example.oxeanbits_challenge.permission.LocationPermission
import com.example.oxeanbits_challenge.top_bar.TopBarView
import com.example.oxeanbits_challenge.ui.theme.OxeanbitschallengeTheme
import com.example.oxeanbits_challenge.weather.model.LocationData
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private lateinit var locationPermission : LocationPermission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permission = LocationPermission(this, this)
        setContent {

            val model: MainActivityViewModel = koinViewModel()

            LaunchedEffect(true) {
                permission.requestLocationAccess()

                permission.setOnUpdateLocationDataCallBack { latitude, longitude ->
                    model.setLocationData(
                        LocationData(
                            latitude = latitude,
                            longitude = longitude
                        )
                    )
                }
            }

            OxeanbitschallengeTheme {
                Scaffold(
                    topBar = {
                        if (model.getShowTopBar())
                            TopBarView()
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = it.calculateTopPadding(),
                            )
                    ) {
                        NavigationView()
                    }
                }
            }
        }
    }

    override fun onStart() {
        locationPermission = LocationPermission(
            activity = this,
            context = this
        )
        super.onStart()
    }
}