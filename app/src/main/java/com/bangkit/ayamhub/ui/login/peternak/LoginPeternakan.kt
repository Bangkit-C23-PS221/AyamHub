package com.bangkit.ayamhub.ui.login.peternak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bangkit.ayamhub.databinding.ActivityLoginPBinding
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.data.online.Result

class LoginPeternakan : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPBinding
    private val viewModel: LoginPeternakanViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPBinding.inflate(layoutInflater)
        setContentView(binding.root)
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