package com.example.motonew.uis

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.motonew.R
import com.example.motonew.adapters.ImageSliderAdapter
import com.example.motonew.databinding.ActivityBikeDetailsBinding
import com.example.motonew.models.Record
import me.relex.circleindicator.CircleIndicator2

class BikeDetails : AppCompatActivity() {
    lateinit var binding: ActivityBikeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bike_details)
        binding.apply {

            val model=intent.getSerializableExtra("model") as Record

            bikeSlider(this, model.photoUrls as ArrayList<String>)

        }

    }

    private fun bikeSlider(
        binding: ActivityBikeDetailsBinding,
        list: ArrayList<String>,
    ) {
        binding.apply {
            val adapter = ImageSliderAdapter(this@BikeDetails, list)
            recyclerView.setHasFixedSize(true)
            recyclerView.isNestedScrollingEnabled = true
            recyclerView.setItemViewCacheSize(10)

            val snapHelper: SnapHelper = PagerSnapHelper()
            recyclerView.onFlingListener = null
            snapHelper.attachToRecyclerView(recyclerView)
            recyclerView.adapter = adapter
            if (list.size > 1) {
                binding.indicator.visibility = View.VISIBLE
            } else {
                binding.indicator.visibility = View.GONE
            }

            val indicator: CircleIndicator2 = binding.indicator
            indicator.attachToRecyclerView(recyclerView, snapHelper)

            adapter.registerAdapterDataObserver(indicator.adapterDataObserver)

            val itemDuration = 5000
            val handler = Handler(Looper.getMainLooper())
            var currentPage = 0

            val runnable: Runnable = object : Runnable {
                override fun run() {
                    if (list.size > 0){
                        val nextItem = (currentPage + 1) % list.size
                        recyclerView.smoothScrollToPosition(nextItem)
                        currentPage = nextItem
                        handler.postDelayed(this, itemDuration.toLong())
                    }
                }
            }

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastItem: Int =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    if (lastItem == (recyclerView.layoutManager as LinearLayoutManager).itemCount - 1) {
                        handler.removeCallbacks(runnable)
                        val postHandler =
                            Handler(Looper.getMainLooper())
                        postHandler.postDelayed({
                            currentPage = 0
                            recyclerView.smoothScrollToPosition(0)
                            handler.postDelayed(runnable, itemDuration.toLong())
                        }, itemDuration.toLong())
                    }
                }
            })

            handler.postDelayed(runnable, itemDuration.toLong())

        }
    }
}