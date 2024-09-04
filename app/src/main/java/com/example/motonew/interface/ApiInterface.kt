package com.example.motonew.`interface`

import com.example.motonew.models.ApiListingResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("listings")
    fun getListing(@Query("apikey") apiKey: String="ZrQEPSkKbml0ZXNobWFuMjlAZ21haWwuY29t"): Call<ApiListingResponseModel>

}