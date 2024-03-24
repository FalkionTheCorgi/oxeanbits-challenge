package com.example.oxeanbits_challenge.api

import com.example.oxeanbits_challenge.api.model.response.ResponseError
import com.example.oxeanbits_challenge.network_monitor.NetworkMonitor
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.URLDecoder

class InterceptorRetrofit(
    private val network: NetworkMonitor
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val request = chain.request()
        val headers = arrayListOf(
            Pair("Content-Type", "application/json"),
            Pair("Accept", "application/json")
        )
        val modifyRequest = request.newBuilder()
            .apply {
                headers.forEach {
                    addHeader(it.first, it.second)
                }
                url(url = URLDecoder.decode(request.url.toString(), "UTF-8"))
            }.build()

        if (network.isNetworkAvailable()) {
            val response = chain.proceed(modifyRequest)
            response
        } else {
            val responseError = ResponseError(error = true, reason = "Sem conexão com a internet")
            val gson = Gson()
            val jsonBody = gson.toJson(responseError)
            val mediaType = "application/json".toMediaTypeOrNull()
            val responseBody = jsonBody.toResponseBody(mediaType)
            Response.Builder()
                .code(500)
                .message("Sem conexão com a internet")
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .body(responseBody)
                .build()
        }

    }

}
