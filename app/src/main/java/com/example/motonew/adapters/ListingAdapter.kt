package com.example.motonew.adapters

import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.motonew.MainActivity
import com.example.motonew.databinding.BikeChildBinding
import com.example.motonew.models.Record
import com.example.motonew.uis.BikeDetails

class ListingAdapter(private var list: List<Record>, private val context: MainActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var filteredList: List<Record> = list

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
        return filteredList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = filteredList[position]
        val binding = (holder as ViewHolder).binding
        Glide.with(context).load(item.photoUrls[0]).into(binding.ivBike)
        binding.tvName.text = item.make
        binding.tvModel.text = item.model
        binding.ivBike.setOnClickListener {
            val intent = Intent(context, BikeDetails::class.java)
            intent.putExtra("model",item)
            context.startActivity(intent)
        }
    }

    fun filterList(filteredList: List<Record>) {
        this.filteredList = filteredList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: BikeChildBinding) : RecyclerView.ViewHolder(binding.root)
}