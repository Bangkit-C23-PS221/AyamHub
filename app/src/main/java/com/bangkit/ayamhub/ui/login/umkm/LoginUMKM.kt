package com.bangkit.ayamhub.ui.login.umkm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ayamhub.databinding.ActivityLoginUBinding

class LoginUMKM : AppCompatActivity() {

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