package com.bangkit.ayamhub.ui.homepage.bookmarks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.data.network.response.BookmarkResponse
import com.bangkit.ayamhub.databinding.ItemRvBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.ui.homepage.home.HomeAdapter
import com.bumptech.glide.Glide

class BookmarkAdapter(
    private val farmList: List<BookmarkResponse>,
    private val context: Context,
    private val onClick: (BookmarkResponse) -> Unit

) : RecyclerView.Adapter<BookmarkAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(farmList[position].farmItem) {
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

            holder.itemView.setOnClickListener {
                onClick(farmList[position])
            }
        }
    }

    override fun getItemCount(): Int = farmList.size

    companion object {
        private const val ACTIVE = "dalam masa panen"
    }
}