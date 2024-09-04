package com.example.motonew

import android.app.Application
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

class MyApplication:Application() {
    private lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
        // Initialize Retrofit with the base URL, OkHttpClient, and Gson converter
        retrofit = Retrofit.Builder()
            .baseUrl("https://auto.dev/") // Base URL for the API
            .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter
            .build()
    }
}