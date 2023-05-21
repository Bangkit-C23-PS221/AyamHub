package com.bangkit.ayamhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ayamhub.databinding.ActivityLoginUBinding
import com.bangkit.ayamhub.databinding.ActivityMainBinding

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