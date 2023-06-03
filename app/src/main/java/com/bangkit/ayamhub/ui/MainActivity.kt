package com.bangkit.ayamhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ayamhub.ui.login.LoginActivity
import com.bangkit.ayamhub.databinding.ActivityMainBinding
import com.bangkit.ayamhub.helpers.injection.Injection
import com.bangkit.ayamhub.ui.detail.DetailActivity
import com.bangkit.ayamhub.ui.homepage.HomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener { startApp() }
    }

    private fun startApp() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}