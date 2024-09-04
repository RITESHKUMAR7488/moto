package com.example.motonew.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.motonew.`interface`.ApiInterface
import com.example.motonew.models.ApiListingResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRepository(private val api:ApiInterface) {

    fun getListing(apiKey:String,data:MutableLiveData<ApiListingResponseModel>){
        api.getListing(apiKey).enqueue(object : Callback<ApiListingResponseModel>{
            override fun onResponse(
                p0: Call<ApiListingResponseModel>,
                p1: Response<ApiListingResponseModel>
            ) {
                data.value=p1.body()
            }

            override fun onFailure(p0: Call<ApiListingResponseModel>, p1: Throwable) {
                Log.d("Error", p1.message.toString())              }

        })


    }
}