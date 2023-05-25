package com.bangkit.ayamhub.ui.login.peternak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import com.bangkit.ayamhub.databinding.ActivityLoginPBinding
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.ui.home.HomeActivity

class LoginPeternakanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPBinding
    private val viewModel: LoginPeternakanViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { validateInput() }
    }

    private fun validateInput() {
        with (binding) {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            when {
                email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    etEmail.error = "Masukkan Email Dengan Benar"
                }
                password.isEmpty() || password.length < 8 -> {
                    etPassword.error = "Masukkan Password Dengan Benar"
                }
                else -> {
                    signIn(email, password)
                }
            }
        }
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
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
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
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}