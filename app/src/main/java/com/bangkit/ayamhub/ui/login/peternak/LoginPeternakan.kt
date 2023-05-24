package com.bangkit.ayamhub.ui.login.peternak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ayamhub.databinding.ActivityLoginPBinding

class LoginPeternakan : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}