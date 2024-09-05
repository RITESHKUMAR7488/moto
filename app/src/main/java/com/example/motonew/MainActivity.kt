package com.example.motonew

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.motonew.adapters.ListingAdapter
import com.example.motonew.databinding.ActivityMainBinding
import com.example.motonew.`interface`.ApiInterface
import com.example.motonew.models.ApiListingResponseModel
import com.example.motonew.repository.MyRepository
import com.example.motonew.viewModelFactory.MyViewModelFactory
import com.example.motonew.viewModels.MyViewModel
import com.google.android.material.navigation.DrawerLayoutUtils
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        with(binding){
            val application = application as MyApplication
            val retrofitBuilder = application.retrofit
            val apiInterface = retrofitBuilder.create(ApiInterface::class.java)

            val repository=MyRepository(apiInterface)
            viewModel=ViewModelProvider(
                this@MainActivity,
                MyViewModelFactory(repository)
            )[MyViewModel::class.java]
            viewModel.getListing().observe(this@MainActivity){
                val adapter=ListingAdapter(it.records,this@MainActivity)
                rvBike.adapter=adapter
            }

            val drawerLayout: DrawerLayout =binding.drawerLayout
            val navView: NavigationView = binding.navView

            binding.hamburgerIcon.setOnClickListener{
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START)
                } else {
                    Log.d("Hamburger", "Drawer already open")
                }
            }


        }

    }
}