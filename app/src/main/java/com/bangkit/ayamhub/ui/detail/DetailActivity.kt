package com.bangkit.ayamhub.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.data.network.response.DetailFarmResponse
import com.bangkit.ayamhub.data.network.response.MessageResponse
import com.bangkit.ayamhub.databinding.ActivityDetailBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bumptech.glide.Glide
import java.time.LocalDate
import java.time.temporal.ChronoUnit
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var farmId = -1
    private var isBookmarked: Boolean? = null
    private var bookmarkId = -1
    private var phone = 0L
    private val vIewModel: DetailVIewModel by viewModels {
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
        binding.WA.setOnClickListener { whatsAppInent() }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun whatsAppInent() {
        if (phone != 0L) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/$phone")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Reusable.showToast(this@DetailActivity, "Tidak dapat membuka aplikasi WhatsApp")
            }
        } else {
            Reusable.showToast(this@DetailActivity, "Tidak dapat memuat nomor WhatsApp")
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
                    else -> {}
                }
            }
        }
    }

    private fun setupView(data: DetailFarmResponse) {
        with(binding) {
            Glide.with(this@DetailActivity)
                .load(data.picFarm)
                .into(farmIv)
            farmName.text = data.nameFarm
            detailWeichtTv.text = getString(R.string.chickenWeightTv, data.weightChicken)
            age.text = getString(R.string.chickenAgeTv, getChickenAge(data.ageChicken))
            locFarm.text = data.addressFarm
            chickenType.text = getString(R.string.chickenTypeTv, data.typeChicken)
            stock.text = getString(R.string.chickenStokTv, data.stockChicken)
            farmersNote.text = data.descFarm
            harga.text = getString(R.string.chickengPriceTv, data.priceChicken)
            //TODO: add phone number
//            phone = data.
        }
    }

    private fun getChickenAge(date: String): String {
        val actualDate = date.split("T")
        val chickenDate = LocalDate.parse(actualDate[0])
        val currentDate = LocalDate.now()
        return ChronoUnit.DAYS.between(chickenDate, currentDate).toString()
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
                            binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_bookmarks))
                        } else if (isBookmarked == false){
                            binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_bookmark_before))
                        }
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Log.e("DetailActivity", "OnFailure: ${result.error}")
                    }
                    else -> {}
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
                    binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_bookmarks))
                    isBookmarked = true
                }
            }
        }
    }

    private fun removeBookmark() {
        Log.e("Remove", "$bookmarkId")
        if (bookmarkId >= 0) {
            vIewModel.removeBookmark(bookmarkId).observe(this@DetailActivity) { result ->
                processResult(result, "Oops gagal menghapus bookmark!") {
                    binding.ivBookmark.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.ic_bookmark_before))
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
        //TODO: Uncomment This Line of Code
//        if (loading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
    }

    companion object {
        const val EXTRA_ID = "ID_FARM"
        const val FALSE = "false"
        const val TRUE = "true"
    }
}