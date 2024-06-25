package com.roman.mainsearch.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.roman.entity.MusicFlay
import com.roman.entity.OffersValue
import com.roman.mainsearch.R
import com.roman.mainsearch.databinding.MusicFlyItemBinding

class MusicFlyAdapter : Adapter<MusicFlyViewHolder>() {
    private var data: List<OffersValue> = emptyList()
    private val mapData = mutableMapOf<Int, Int>()
    fun setData(values: MusicFlay) {
        data = values.offers

        //setImages
        mapData[1] = R.drawable.image_1
        mapData[2] = R.drawable.image_2

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicFlyViewHolder {
        val binding =
            MusicFlyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicFlyViewHolder(binding)

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MusicFlyViewHolder, position: Int) {
        val musicFlyElement = data[position]
        val image = mapData.getOrDefault(musicFlyElement.id, R.drawable.default_image)

        with(holder)
        {
            Glide.with(binding.imagePosterMusicFly)
                .load(image)
                .centerCrop()
                .into(binding.imagePosterMusicFly)
            Log.d("1111", image.toString())
            val textMusicFlyName = musicFlyElement.title ?: let { "-" }
            val townText = musicFlyElement.town ?: let { "-" }
            val textFrom = itemView.context.getString(R.string.from_price)
            val textPrice = textFrom + " " + musicFlyElement.price.getValue()


            binding.musicFlyName.text = textMusicFlyName
            binding.musicFlyCity.text = townText
            binding.priceValueMusicFly.text = textPrice

        }

    }
}

class MusicFlyViewHolder(val binding: MusicFlyItemBinding) : ViewHolder(binding.root)