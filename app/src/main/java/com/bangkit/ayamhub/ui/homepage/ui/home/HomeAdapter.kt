package com.bangkit.ayamhub.ui.homepage.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.ayamhub.data.local.FlameChaser
import com.bangkit.ayamhub.databinding.ItemRvBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bumptech.glide.Glide
import java.util.*

class HomeAdapter(
    private val data: List<FlameChaser>,
    private val context: Context,
    private val onClick: (FlameChaser) -> Unit

) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    private var dataDummy: MutableList<FlameChaser> = data.toMutableList()

    class MyViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val photo = dataDummy[position].photo
        val title = dataDummy[position].name
        val location = dataDummy[position].signet
        val priceData = dataDummy[position].id

        with(holder.binding) {
            Glide.with(holder.itemView.context)
                .load(photo)
                .into(ImageView)
            farmName.text = title
            locFarm.text = location
            chickenType.text = "Ayam Goreng"
            price.text = priceData.toString()
        }

        holder.itemView.setOnClickListener { onClick(dataDummy[position]) }
    }

    override fun getItemCount(): Int = dataDummy.size

    fun filterBySearch(text: String) {
        dataDummy.clear()
        if (text.isEmpty()) {
            dataDummy.addAll(data)
        } else {
            dataDummy.addAll( data.filter {
                it.name.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))
            })
        }
        notifyDataSetChanged()
    }

    fun filterByLocation(location: String) {
        Reusable.showToast(context, location)
    }

    fun removeFilter() {
        dataDummy.clear()
        dataDummy.addAll(data)
        notifyDataSetChanged()
    }
}