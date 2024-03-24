package com.example.oxeanbits_challenge.koin

import com.example.oxeanbits_challenge.BuildConfig
import com.example.oxeanbits_challenge.api.ApiService
import com.example.oxeanbits_challenge.api.InterceptorRetrofit
import com.example.oxeanbits_challenge.api.repository.GetRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory {
        InterceptorRetrofit(get())
    }
    factory {
        createOkHttpClient(get())
    }
    single {
        createApiService(get())
    }
    factory {
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
        .baseUrl(BuildConfig.API_LINK)
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