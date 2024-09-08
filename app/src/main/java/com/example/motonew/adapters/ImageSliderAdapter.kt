package com.example.motonew.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.motonew.R
import com.example.motonew.databinding.DetailCrouselBinding


class ImageSliderAdapter(
    private val context: Context,
    private val list: ArrayList<String>,

    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return UpComingEventViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.detail_crousel,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val upComingEventViewHolder: UpComingEventViewHolder = holder as UpComingEventViewHolder
        upComingEvent(upComingEventViewHolder.binding, list[position])
    }

    inner class UpComingEventViewHolder(val binding: DetailCrouselBinding) :
        RecyclerView.ViewHolder(binding.root)

    private fun upComingEvent(binding: DetailCrouselBinding,image:String) {
        binding.apply {
            Glide.with(context)
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivTrendingImage)
        }
    }
}