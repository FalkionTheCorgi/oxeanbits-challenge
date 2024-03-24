package com.example.oxeanbits_challenge.api.repository

import com.example.oxeanbits_challenge.api.ApiService
import com.example.oxeanbits_challenge.api.model.response.ForecastResponse
import com.example.oxeanbits_challenge.api.model.response.ResponseError

class GetRepository(
    private val apiService: ApiService
) {

    suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        currentParams: String,
        hourlyParams: String,
        dailyParams: String,
        timezone: String,
        startDate: String,
        endDate: String
    ): Pair<ForecastResponse?, ResponseError?> {

        val call = apiService.getCurrentForecast(
            latitude = latitude,
            longitude = longitude,
            currentParams = currentParams,
            hourlyParams = hourlyParams,
            timezone = timezone,
            dailyParams = dailyParams,
            startDate = startDate,
            endDate = endDate
        )

        return if (call?.isSuccessful == true){

            val forecast: ForecastResponse? = call.body()

            Pair(forecast, null)

        } else {

            val response = ResponseError(
                error = true,
                reason = call?.message()
            )

            Pair(null, response)

        }

    }

}