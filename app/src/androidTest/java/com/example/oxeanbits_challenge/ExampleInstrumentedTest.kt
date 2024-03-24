package com.example.oxeanbits_challenge

import android.content.Context
import android.os.Build
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.example.oxeanbits_challenge.api.model.response.ForecastResponse
import com.example.oxeanbits_challenge.koin.generalModule
import com.example.oxeanbits_challenge.koin.networkModule
import com.example.oxeanbits_challenge.koin_modules.mockGeneralModule
import com.example.oxeanbits_challenge.koin_modules.mockNetworkModule
import com.example.oxeanbits_challenge.network_monitor.NetworkMonitor
import com.example.oxeanbits_challenge.weather.model.ForecastData
import com.google.gson.Gson
import io.mockk.every
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.net.HttpURLConnection
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest: KoinTest {

    @get:Rule
    var composeTestRule = createAndroidComposeRule(MainActivity::class.java)
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var mockWebServer: MockWebServer


    @BeforeTest
    fun setUp() {
        unloadKoinModules(listOf(networkModule, generalModule))
        loadKoinModules(listOf(mockNetworkModule, mockGeneralModule))
        mockWebServer = MockWebServer()
        mockWebServer.start(8081)
    }

    @AfterTest
    fun tearDown() {
        mockWebServer.shutdown()
        unloadKoinModules(listOf(mockNetworkModule, mockGeneralModule))
        loadKoinModules(listOf(networkModule, generalModule))
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun when_i_stated_app_verify_weather_data(){
        setNetworkOnline()
        setResponseWeatherSuccess(
            context = context,
            mockWebServer = mockWebServer
        )
        val forecast = getForecastData(getWeatherSuccessAsObject())

        composeTestRule.apply{
            waitUntilExactlyOneExists(hasText(context.getString(R.string.temperature, forecast.temperature)), 10000)

            waitUntilExactlyOneExists(hasText(context.getString(R.string.relative_humidity, forecast.relativeHumidity)), 1000)

            waitUntilExactlyOneExists(hasText(context.getString(R.string.probability_precipitation, forecast.probabilityPrecipitation)), 1000)

            waitUntilExactlyOneExists(hasText(context.getString(R.string.ultraviolet_index, forecast.ultravioletIndex)), 1000)

            waitUntilExactlyOneExists(hasText(context.getString(R.string.day_or_night, forecast.dayOrNight)), 1000)

            waitUntilExactlyOneExists(hasText(context.getString(R.string.sunrise_hour, forecast.sunrise)), 1000)

            waitUntilExactlyOneExists(hasText(context.getString(R.string.nightfall_hour, forecast.sunset)), 1000)

            waitUntilExactlyOneExists(hasText(context.getString(R.string.timezone, forecast.timezone)), 1000)
        }
    }

    private fun setResponseWeatherSuccess(
        context: Context,
        mockWebServer: MockWebServer
    ){
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                context.resources.openRawResource(R.raw.json_weather_success).bufferedReader()
                    .use { it.readText() }
            )
        mockWebServer.enqueue(response)
    }

    private fun getWeatherSuccessAsObject(): ForecastResponse {
        val jsonString =
            context.resources.openRawResource(R.raw.json_weather_success).bufferedReader()
                .use { it.readText() }
        return Gson().fromJson(jsonString, ForecastResponse::class.java)
    }

    private fun getForecastData(
        forecast: ForecastResponse
    ): ForecastData {

        return ForecastData(
            temperature = "${getFormattedDoubleTwoDecimalCases(forecast.current?.temperature2m ?: 0.00)} ${forecast.currentUnits?.temperature2m}",
            maxTemperature = "${forecast.hourly?.temperature2m?.maxOrNull() ?: "0.00"} ${forecast.hourlyUnits?.temperature2m}",
            minTemperature = "${forecast.hourly?.temperature2m?.minOrNull() ?: "0.00"} ${forecast.hourlyUnits?.temperature2m}",
            relativeHumidity = "${forecast.current?.relativeHumidity2m ?: ""}${forecast.currentUnits?.relativeHumidity2m}",
            probabilityPrecipitation = "${medianInt(forecast.hourly?.precipitation)}${forecast.hourlyUnits?.precipitation}",
            ultravioletIndex = medianDouble(
                array = forecast.daily?.uvIndexMax ?: arrayListOf()
            ),
            dayOrNight = if (forecast.current?.isDay == 0) {
                "Noite"
            } else {
                "Dia"
            },
            sunrise = getSunriseOrSunset(
                forecast.daily?.sunrise ?: arrayListOf(),
                getCurrentDate()
            ) ?: "",
            sunset = getSunriseOrSunset(
                forecast.daily?.sunset ?: arrayListOf(),
                getCurrentDate()
            ) ?: "",
            weatherCode = forecast.current?.weatherCode ?: 0,
            timezone = forecast.timezone ?: ""
        )
    }

    private fun setNetworkOnline(){
        val network: NetworkMonitor by inject()
        every { network.isNetworkAvailable() } returns true
    }

    fun grantPermission() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        if (Build.VERSION.SDK_INT >= 23) {
            val allowPermission = UiDevice.getInstance(instrumentation).findObject(
                UiSelector().text(
                    when {
                        Build.VERSION.SDK_INT == 23 -> "Allow"
                        Build.VERSION.SDK_INT <= 28 -> "ALLOW"
                        Build.VERSION.SDK_INT == 29 -> "Allow only while using the app"
                        else -> "While using the app"
                    }
                )
            )
            if (allowPermission.exists()) {
                allowPermission.click()
            }
        }
    }

}