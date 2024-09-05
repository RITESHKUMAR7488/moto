package com.example.motonew

import android.app.Application
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication:Application() {
    lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
        retrofit = Retrofit.Builder()
            .baseUrl("https://auto.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}