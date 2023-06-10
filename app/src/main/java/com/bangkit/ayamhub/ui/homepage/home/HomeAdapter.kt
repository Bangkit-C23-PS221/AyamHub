package com.bangkit.ayamhub.ui.homepage.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.data.network.response.FarmItemResponse
import com.bangkit.ayamhub.databinding.ItemRvBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bumptech.glide.Glide
import java.util.*

class HomeAdapter(
    private val data: List<FarmItemResponse>,
    private val context: Context,
    private val onClick: (FarmItemResponse) -> Unit

) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    private var filteredData: MutableList<FarmItemResponse> = data.toMutableList()
    var currentSort = ""
    var currentLocFilter = ""
    var currentStatusFilter = ""

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
                locFarm.text = Reusable.getCity(addressFarm)
                chickenType.text = typeChicken
                price.text = priceChicken
                statusFarm.text = status
                if (status == ACTIVE) {
                    statusFarm.setBackgroundResource(R.drawable.bg_status_ready)
                    statusFarm.setTextColor(context.getColor(R.color.white))
                } else {
                    statusFarm.setBackgroundResource(R.drawable.bg_status)
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
            sortBy()
        }
        notifyDataSetChanged()
    }

    fun filterBy() {
        filteredData.clear()
        when {
            currentLocFilter.isNotEmpty() && currentStatusFilter.isNotEmpty() -> {
                filteredData.addAll(data.filter {
                    it.status.contains(currentStatusFilter) && it.addressFarm.toLowerCase(Locale.ROOT).contains(currentLocFilter.toLowerCase(Locale.ROOT))
                })
            }
            currentLocFilter.isNotEmpty() -> {
                filteredData.addAll(data.filter {
                    it.addressFarm.contains(currentLocFilter, true)
                })
            }
            currentStatusFilter.isNotEmpty() -> {
                if (currentStatusFilter == NOT_ACTIVE) {
                    filteredData.addAll(data.filter {
                        it.status.contains(NOT_ACTIVE)
                    })
                } else {
                    filteredData.addAll(data.filterNot {
                        it.status.contains(NOT_ACTIVE)
                    })
                }

            }
            else -> {
                filteredData.addAll(data)
            }
        }
        sortBy()
        notifyDataSetChanged()
    }

    fun sortBy() {
        when(currentSort) {
            NAME -> {
                filteredData.sortBy { it.nameFarm }
                currentSort = NAME
            }
            CHEAPEST -> {
                filteredData.sortBy { it.priceChicken.toInt() }
                currentSort = CHEAPEST
            }
            EXPENSIVE -> {
                filteredData.sortByDescending { it.priceChicken.toInt() }
                currentSort = EXPENSIVE
            }
            LATEST -> {
                filteredData.sortBy { it.idFarm }
                currentSort = LATEST
            }
            OLDEST -> {
                filteredData.sortByDescending { it.idFarm }
                currentSort = OLDEST
            }
            else -> {
                return
            }
        }
        notifyDataSetChanged()
    }

    companion object {
        const val ACTIVE = "Siap Panen"
        const val NOT_ACTIVE = "Belum Siap Panen"
        const val NAME = "name"
        const val CHEAPEST = "murah"
        const val EXPENSIVE = "mahal"
        const val LATEST = "terbaru"
        const val OLDEST = "terlama"
    }
}