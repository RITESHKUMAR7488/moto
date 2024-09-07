package com.example.motonew.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motonew.MainActivity
import com.example.motonew.databinding.BikeChildBinding

class ListingAdapter(private val listings: List<Record>,private val context:MainActivity) :
RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
    class ViewHolder(private val binding:BikeChildBinding):RecyclerView.ViewHolder(binding.root){

    }
}