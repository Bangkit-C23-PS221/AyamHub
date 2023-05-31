package com.bangkit.ayamhub.ui.farmer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.databinding.ActivityFarmerBinding
import com.bangkit.ayamhub.databinding.ActivityMainBinding
import com.bangkit.ayamhub.ui.homepage.HomeActivity
import com.bangkit.ayamhub.ui.makefarm.EditFarmer

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
    }
}