package com.bangkit.ayamhub.ui.homepage.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.data.network.response.FarmItemResponse
import com.bangkit.ayamhub.databinding.ItemRvBinding
import com.bumptech.glide.Glide
import java.util.*

class HomeAdapter(
    private val data: List<FarmItemResponse>,
    private val context: Context,
    private val onClick: (FarmItemResponse) -> Unit

) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    private var filteredData: MutableList<FarmItemResponse> = data.toMutableList()

    class MyViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(filteredData[position]) {
            with(holder.binding) {
                Glide.with(holder.itemView.context)
                    .load(photoUrl)
                    .into(ImageView)
                farmName.text = nameFarm
                locFarm.text = addressFarm
                chickenType.text = typeChicken
                price.text = priceChicken
                statusFarm.text = status
                if (status == ACTIVE) {
                    statusFarm.setBackgroundColor(context.getColor(R.color.green))
                } else {
                    statusFarm.setBackgroundColor(context.getColor(R.color.yellow))
                }
            }

            holder.itemView.setOnClickListener { onClick(filteredData[position]) }
        }
    }

    override fun getItemCount(): Int = filteredData.size

    fun filterBySearch(text: String) {
        filteredData.clear()
        if (text.isEmpty()) {
            filteredData.addAll(data)
        } else {
            filteredData.addAll( data.filter {
                it.nameFarm.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))
            })
        }
        notifyDataSetChanged()
    }

    fun filterByLocation(location: String) {
        filteredData.clear()
        if (location.isEmpty()) {
            filteredData.addAll(data)
        } else {
            filteredData.addAll( data.filter {
                it.addressFarm.toLowerCase(Locale.ROOT).contains(location.toLowerCase(Locale.ROOT))
            })
        }
        notifyDataSetChanged()
    }

    fun removeFilter() {
        filteredData.clear()
        filteredData.addAll(data)
        notifyDataSetChanged()
    }

    companion object {
        private const val ACTIVE = "dalam masa panen"
    }
}