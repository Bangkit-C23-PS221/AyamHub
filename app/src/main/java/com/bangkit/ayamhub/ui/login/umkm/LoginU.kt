package com.bangkit.ayamhub.ui.login.umkm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ayamhub.databinding.ActivityLoginUBinding

class LoginU : AppCompatActivity() {

    private lateinit var binding: ActivityLoginUBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.daftar.setOnClickListener {

        }
    }
}