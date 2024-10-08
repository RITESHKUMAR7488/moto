package com.example.motonew.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.motonew.models.ApiListingResponseModel
import com.example.motonew.repository.MyRepository

class MyViewModel(private val repository: MyRepository):ViewModel() {

    fun getListing(): MutableLiveData<ApiListingResponseModel> {
        val data=MutableLiveData<ApiListingResponseModel>()
        repository.getListing(data)
        return data
    }

    }