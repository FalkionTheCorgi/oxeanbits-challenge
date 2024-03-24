package com.example.oxeanbits_challenge.koin_modules

import com.example.oxeanbits_challenge.api.ApiService
import com.example.oxeanbits_challenge.api.InterceptorRetrofit
import com.example.oxeanbits_challenge.api.repository.GetRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mockNetworkModule =
    module {
        single {
            InterceptorRetrofit(get())
        }
        single {
            createOkHttpClient(get())
        }
        single {
            createApiService(get())
        }
        single {
            GetRepository(get())
        }
    }

private fun createApiService(
    okHttpClient: OkHttpClient
): ApiService {
    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:8081/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)

}

private fun createOkHttpClient(loggingInterceptor: InterceptorRetrofit): OkHttpClient {

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

}