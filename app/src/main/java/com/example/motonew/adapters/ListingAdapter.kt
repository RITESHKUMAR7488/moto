package com.example.motonew.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.motonew.MainActivity
import com.example.motonew.databinding.BikeChildBinding
import com.example.motonew.models.Record

class ListingAdapter(private val list: List<Record>,private val context:MainActivity) :
RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            BikeChildBinding.inflate(
                context.layoutInflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        val binding=(holder as ViewHolder).binding
        Glide.with(context).load(item.photoUrls[0]).into(binding.ivBike)
        binding.tvName.text=item.make
        binding.tvModel.text=item.model

    }
    class ViewHolder(val binding:BikeChildBinding):RecyclerView.ViewHolder(binding.root){

    }
}