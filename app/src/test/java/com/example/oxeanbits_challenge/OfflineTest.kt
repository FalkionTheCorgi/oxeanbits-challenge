package com.example.oxeanbits_challenge

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.oxeanbits_challenge.api.model.response.ResponseError
import com.example.oxeanbits_challenge.koin.generalModule
import com.example.oxeanbits_challenge.koin_modules.mockNetworkModule
import com.example.oxeanbits_challenge.koin.networkModule
import com.example.oxeanbits_challenge.koin_modules.mockGeneralModule
import com.example.oxeanbits_challenge.network_monitor.NetworkMonitor
import com.google.gson.Gson
import io.mockk.every
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
class OfflineTest: KoinTest {

    @get:Rule
    var composeTestRule = createAndroidComposeRule(MainActivity::class.java)
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private lateinit var mockWebServer: MockWebServer
    private val mainVM: MainActivityViewModel by inject()


    @BeforeTest
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8081)
        unloadKoinModules(listOf(networkModule, generalModule))
        loadKoinModules(listOf(mockNetworkModule, mockGeneralModule))
        setLocation(mainVM)
    }

    @AfterTest
    fun tearDown() {
        mockWebServer.shutdown()
        unloadKoinModules(listOf(mockNetworkModule, mockGeneralModule))
        loadKoinModules(listOf(networkModule, generalModule))
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun when_i_stated_app_verify_weather_data_no_network_connection(){
        setNetworkOffline()
        val reason = getWeatherNoNetworkAsObject()

        composeTestRule.apply {
            waitUntilExactlyOneExists(hasText(reason?.reason ?: context.getString(R.string.no_connection)), 10000)
        }


    }

    private fun getWeatherNoNetworkAsObject(): ResponseError? {
        val jsonString =
            context.resources.openRawResource(R.raw.json_weather_success).bufferedReader()
                .use { it.readText() }
        return Gson().fromJson(jsonString, ResponseError::class.java)
    }

    private fun setNetworkOffline(){
        val network: NetworkMonitor by inject()
        every { network.isNetworkAvailable() } returns false
    }




}