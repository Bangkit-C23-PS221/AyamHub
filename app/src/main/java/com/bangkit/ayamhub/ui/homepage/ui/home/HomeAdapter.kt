package com.bangkit.ayamhub.ui.homepage.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.data.local.FlameChaser
import com.bangkit.ayamhub.data.network.response.ListFarmResponse
import com.bangkit.ayamhub.databinding.ItemRvBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bumptech.glide.Glide
import java.util.*

class HomeAdapter(
    private val data: List<ListFarmResponse>,
    private val context: Context,
    private val onClick: (ListFarmResponse) -> Unit

) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    private var filteredData: MutableList<ListFarmResponse> = data.toMutableList()

    class MyViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(filteredData[position]) {
            val pic = picFarm
            val name = usernameFarm
            val location = addressFarm
            val type = typeChicken
            val priceChicken = priceChicken
            val statusChicken = status

            with(holder.binding) {
                Glide.with(holder.itemView.context)
                    .load(pic)
                    .into(ImageView)
                farmName.text = name
                locFarm.text = location
                chickenType.text = type
                price.text = priceChicken
                status.text = statusChicken
                if (statusChicken == "Siap Panen") {
                    status.setBackgroundColor(context.getColor(R.color.green))
                } else {
                    status.setBackgroundColor(context.getColor(R.color.yellow))
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
                it.usernameFarm.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))
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
}