package com.example.currencyz.data.remote.network_module

import com.example.currencyz.data.remote.dto.ApiData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET

class NetworkModule : NetworkModuleResponses {

    companion object {
        private const val BASE_URL = "https://www.cbr-xml-daily.ru"
    }

    private interface CbrCurrencyApi {
        @GET("/daily_json.js")    // allData
        suspend fun loadDataFromApi(): ApiData

    }

    private object RetrofitModule {
        private val json = Json {
            ignoreUnknownKeys = true
        }

        private val httpClient = OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY) // simple interceptor for logs
            )
            .build()

        @Suppress("EXPERIMENTAL_API_USAGE")
        private val retrofit: Retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        val cbrCurrencyApi: CbrCurrencyApi = retrofit.create()
    }

    override suspend fun getAllData(): ApiData = RetrofitModule.cbrCurrencyApi.loadDataFromApi()

}

