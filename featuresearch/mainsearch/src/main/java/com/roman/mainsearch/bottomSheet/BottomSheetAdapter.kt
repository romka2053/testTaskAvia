package com.roman.mainsearch.bottomSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roman.core.recommendations
import com.roman.mainsearch.databinding.RecommendationsItemBinding

class BottomSheetAdapter(private val onclick: (String) -> Unit) :
    RecyclerView.Adapter<RecommendationsViewHolder>() {

    private val data = recommendations
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val binding =
            RecommendationsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationsViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int) {
        with(holder)
        {
            binding.posterRecommendation.setImageResource(data[position].idImage)
            binding.popularName.text = data[position].name
            binding.descriptionText.text = data[position].description

            binding.root.setOnClickListener {
                onclick(data[position].name)
            }
        }
    }
}

class RecommendationsViewHolder(val binding: RecommendationsItemBinding) :
    RecyclerView.ViewHolder(binding.root)