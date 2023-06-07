package com.bangkit.ayamhub.ui.farmer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.data.network.response.DetailFarmResponse
import com.bangkit.ayamhub.data.network.response.MyFarmResponse
import com.bangkit.ayamhub.databinding.ActivityFarmerBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.farmform.FarmFormActivity
import com.bumptech.glide.Glide

class FarmerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFarmerBinding
    private val vIewModel: FarmerVIewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityFarmerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Edit.setOnClickListener{ goToEdit() }
    }

    override fun onStart() {
        super.onStart()

        getData()
    }

    private fun goToEdit() {
        val intent = Intent(this, FarmFormActivity::class.java)
        intent.putExtra(FarmFormActivity.EXTRA_CALLER, FarmFormActivity.EDIT)
        startActivity(intent)
    }

    private fun getData() {
        vIewModel.getMyFarm().observe(this) { result ->
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

    private fun setupView(data: MyFarmResponse) {
        with(binding) {
            Glide.with(this@FarmerActivity)
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
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }
}