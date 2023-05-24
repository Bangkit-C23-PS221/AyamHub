package com.bangkit.ayamhub.ui.login.peternak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bangkit.ayamhub.databinding.ActivityLoginPBinding
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.helpers.Reusable

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
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        viewModel.saveToken(result.data.accessToken)
                        /*TODO:
                        *  1. intent to home */
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Reusable.showToast(this, "Oops gagal login")
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {

    }
}