package com.bangkit.ayamhub.ui.homepage.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.ayamhub.data.local.FlameChaser
import com.bangkit.ayamhub.databinding.ItemRvBinding
import com.bumptech.glide.Glide

class HomeAdapter(
    private val dataDummy: List<FlameChaser>,
    private val onClick: (FlameChaser) -> Unit

) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
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
}