package com.bangkit.ayamhub.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.data.network.response.DetailFarmResponse
import com.bangkit.ayamhub.data.network.response.MessageResponse
import com.bangkit.ayamhub.databinding.ActivityDetailBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var farmId = -1
    private var isBookmarked: Boolean? = null
    private var bookmarkId = -1
    private var phone = ""
    private var address = ""
    private var name = ""
    private val vIewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        checkBookmark()

        binding.ivBookmark.setOnClickListener { setupBookmarkButton() }
        binding.WA.setOnClickListener { whatsAppIntent() }
        binding.maps.setOnClickListener { mapsIntent() }
    }

    private fun whatsAppIntent() {
        if (phone.isNotEmpty()) {
            phone = "62" + phone.substring(1)
            val options = arrayOf("WhatsApp", "Telepon")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pilih Aksi")
            builder.setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=${Uri.encode(getString(R.string.whatsapp_msg, name))}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                    1 -> {
                        val dialIntent = Intent(Intent.ACTION_DIAL)
                        dialIntent.data = Uri.parse("tel:$phone")
                        startActivity(dialIntent)
                    }
                }
            }
            builder.show()
        } else {
            Reusable.showToast(this@DetailActivity, "Tidak dapat memuat nomor WhatsApp")
        }
    }

    private fun mapsIntent() {
        if (address.isNotEmpty()) {
            val uri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps") // Specify package to ensure Google Maps app is opened
            startActivity(intent)
        } else {
            Reusable.showToast(this@DetailActivity, "Tidak dapat memuat alamat peternakan")
        }
    }

    private fun getData() {
        farmId = intent.getIntExtra(EXTRA_ID, -1)
        if (farmId >= 0) {
            vIewModel.getFarmDetail(farmId).observe(this) { result ->
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        setupView(result.data)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Log.e("DetailActivity", "OnFailure: ${result.error}")
                    }
                }
            }
        }
    }

    private fun setupView(data: DetailFarmResponse) {
        with(binding) {
            Glide.with(this@DetailActivity)
                .load(data.photoUrl)
                .into(farmIv)
            farmName.text = data.nameFarm
            detailWeichtTv.text = getString(R.string.chickenWeightTv, data.weightChicken)
            age.text = getString(R.string.chickenAgeTv, Reusable.getChickenAge(data.ageChicken))
            locFarm.text = data.addressFarm
            chickenType.text = getString(R.string.chickenTypeTv, data.typeChicken)
            stock.text = getString(R.string.chickenStokTv, data.stockChicken)
            farmersNote.text = data.descFarm
            harga.text = getString(R.string.chickengPriceTv, data.priceChicken)
            phone = data.tbUser.phone
            address = data.addressFarm
            name = data.nameFarm
        }
    }

    private fun checkBookmark() {
        if (farmId >= 0) {
            vIewModel.checkBookmark(farmId).observe(this@DetailActivity) { result ->
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        isBookmarked = result.data.isBookmarked
                        bookmarkId = result.data.idBookmark ?: bookmarkId

                        if (isBookmarked == true) {
                            binding.ivBookmark.setBackgroundResource(R.drawable.ic_bookmark_after)
                        } else if (isBookmarked == false){
                            binding.ivBookmark.setBackgroundResource(R.drawable.ic_bookmark_before)
                        }
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Log.e("DetailActivity", "OnFailure: ${result.error}")
                    }
                }
            }
        }
    }

    private fun setupBookmarkButton() {
        when(isBookmarked) {
            true -> {
                removeBookmark()
                Log.e("BookmarkButton", "is True: $isBookmarked")
            }
            false -> {
                addBookmark()
                Log.e("BookmarkButton", "is Fasle: $isBookmarked")
            }
            else -> { Log.e("BookmarkButton", "Else: $isBookmarked") }
        }
    }

    private fun addBookmark() {
        if (farmId >= 0) {
            vIewModel.addBookmark(farmId).observe(this@DetailActivity) { result ->
                processResult(result, "Oops gagal menambahkan bookmark!") {
                    binding.ivBookmark.setBackgroundResource(R.drawable.ic_bookmark_after)
                    isBookmarked = true
                }
            }
        }
    }

    private fun removeBookmark() {
        Log.e("Remove", "$bookmarkId")
        if (farmId >= 0) {
            vIewModel.removeBookmark(farmId).observe(this@DetailActivity) { result ->
                processResult(result, "Oops gagal menghapus bookmark!") {
                    binding.ivBookmark.setBackgroundResource(R.drawable.ic_bookmark_before)
                    isBookmarked = false
                }
            }
        }
    }

    private fun processResult(
        result: Result<MessageResponse>,
        errorMessage: String,
        onClick: (data: MessageResponse) -> Unit
    ) {
        when(result) {
            is Result.Loading -> {
                showLoading(true)
            }
            is Result.Success -> {
                showLoading(false)
                onClick(result.data)
            }
            is Result.Error -> {
                showLoading(false)
                Reusable.showToast(this@DetailActivity, errorMessage)
                Log.e("DetailActivity", "OnFailure: ${result.error}")
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_ID = "ID_FARM"
    }
}