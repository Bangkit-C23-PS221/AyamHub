package com.bangkit.ayamhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ayamhub.ui.login.LoginActivity
import com.bangkit.ayamhub.databinding.ActivityMainBinding
import com.bangkit.ayamhub.ui.detail.DetailActivity
import com.bangkit.ayamhub.ui.homepage.HomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
//
//        binding.cek.setOnClickListener {
//            startActivity(Intent(this, HomeActivity::class.java))
//        }
//
//        binding.cek2.setOnClickListener {
//            startActivity(Intent(this, DetailActivity::class.java))
//        }
//
//        binding.cek4.setOnClickListener {
//        }

    }
}