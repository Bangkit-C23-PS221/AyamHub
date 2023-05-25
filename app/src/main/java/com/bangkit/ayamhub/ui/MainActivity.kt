package com.bangkit.ayamhub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ayamhub.ui.login.umkm.LoginUMKMActivity
import com.bangkit.ayamhub.databinding.ActivityMainBinding
import com.bangkit.ayamhub.ui.login.peternak.LoginPeternakanActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginUMKMActivity::class.java))
            finish()
        }

        binding.peternak.setOnClickListener {
            startActivity(Intent(this, LoginPeternakanActivity::class.java))
        }
    }
}