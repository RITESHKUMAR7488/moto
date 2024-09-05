package com.example.motonew

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.motonew.adapters.ListingAdapter
import com.example.motonew.databinding.ActivityMainBinding
import com.example.motonew.`interface`.ApiInterface
import com.example.motonew.models.ApiListingResponseModel
import com.example.motonew.repository.MyRepository
import com.example.motonew.viewModelFactory.MyViewModelFactory
import com.example.motonew.viewModels.MyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        with(binding){
            val data=intent.getSerializableExtra("KEY") as ApiListingResponseModel
            val application = application as MyApplication
            val retrofitBuilder = application.retrofit
            val apiInterface = retrofitBuilder.create(ApiInterface::class.java)

            val repository=MyRepository(apiInterface)
            viewModel=ViewModelProvider(
                this@MainActivity,
                MyViewModelFactory(repository)
            )[MyViewModel::class.java]
            viewModel.getListing()
            val adapter=ListingAdapter(data.records,this@MainActivity)
            rvBike.adapter=adapter

        }

    }
}