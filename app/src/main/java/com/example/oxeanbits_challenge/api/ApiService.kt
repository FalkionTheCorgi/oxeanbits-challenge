package com.example.oxeanbits_challenge.api

import com.example.oxeanbits_challenge.api.model.response.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("/v1/forecast")
    suspend fun getCurrentForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") currentParams: String?,
        @Query("hourly") hourlyParams: String?,
        @Query("timezone") timezone: String?,
        @Query("daily") dailyParams: String?,
        @Query("start_date") startDate: String?,
        @Query("end_date") endDate: String?
    ): Response<ForecastResponse>?

}