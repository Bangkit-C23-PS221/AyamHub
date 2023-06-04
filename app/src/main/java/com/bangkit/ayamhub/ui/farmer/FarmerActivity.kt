package com.bangkit.ayamhub.ui.farmer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.bangkit.ayamhub.databinding.ActivityFarmerBinding
import com.bangkit.ayamhub.ui.homepage.HomeActivity
import com.bangkit.ayamhub.ui.farmform.EditFarmer

class FarmerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFarmerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityFarmerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Edit.setOnClickListener{
            startActivity(Intent(this, EditFarmer::class.java))
        }
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@FarmerActivity, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                finish() // Optional: End the VVIPFormActivity
            }
        }
        this.onBackPressedDispatcher.addCallback(this, backCallback)
    }

}