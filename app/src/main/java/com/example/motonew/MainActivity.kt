package com.example.motonew

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
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
import com.example.motonew.uis.SignIn
import com.example.motonew.viewModelFactory.MyViewModelFactory
import com.example.motonew.viewModels.MyViewModel
import com.google.android.material.navigation.DrawerLayoutUtils
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        with(binding){
            auth = FirebaseAuth.getInstance()
            val application = application as MyApplication
            val retrofitBuilder = application.retrofit
            val apiInterface = retrofitBuilder.create(ApiInterface::class.java)

            val repository=MyRepository(apiInterface)
            viewModel=ViewModelProvider(
                this@MainActivity,
                MyViewModelFactory(repository)
            )[MyViewModel::class.java]
            viewModel.getListing().observe(this@MainActivity) {
                val adapter = ListingAdapter(it.records, this@MainActivity)
                rvBike.adapter = adapter



                searchView.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        // No need to implement
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        // No need to implement
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val query = s.toString().lowercase()
                        val filteredRecords = it.records.filter { record ->
                            record.make.lowercase().contains(query) || record.model.lowercase().contains(query)
                        }
                        adapter.filterList(filteredRecords)
                    }
                })
            }

            val drawerLayout: DrawerLayout =binding.drawerLayout
            binding.hamburgerIcon.setOnClickListener{
                if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.openDrawer(GravityCompat.START)
                } else {
                    Log.d("Hamburger", "Drawer already open")
                }
            }

            val logout=findViewById<Button>(R.id.btn_logout)

            logout.setOnClickListener{
                logout()

            }


        }

    }
    private fun logout() {
        // Sign out from Firebase
        auth.signOut()

        // Clear the logged-in state from shared preferences
        val sharedPreferences = getSharedPreferences("imdb", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("loggedIn", false).apply()

        // Redirect to FirstScreen and clear the activity stack
        val intent = Intent(this, SignIn::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}