package com.bangkit.ayamhub.ui.login.umkm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bangkit.ayamhub.data.online.Result
import com.bangkit.ayamhub.databinding.ActivityLoginUBinding
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory

class LoginUMKM : AppCompatActivity() {

    private lateinit var binding: ActivityLoginUBinding
    private val viewModel: LoginUMKMViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.daftar.setOnClickListener {

        }
    }

    private fun signIn(email: String, password: String) {
        viewModel.signIn(email, password).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        /*TODO:
                        *  1. add loading*/
                    }
                    is Result.Success -> {
                        /*TODO:
                        *  1. stop loading
                        *  2. intent to home */
                        viewModel.saveToken(result.data.accessToken.toString())
                    }
                    is Result.Error -> {
                        /*TODO:
                        *  1. stop loading
                        *  2. show toast error*/
                    }
                }
            }
        }
    }
}