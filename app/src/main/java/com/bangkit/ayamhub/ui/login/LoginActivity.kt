package com.bangkit.ayamhub.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.databinding.ActivityLoginUBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.home.HomeActivity
import com.bangkit.ayamhub.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginUBinding
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.daftar.setOnClickListener { toRegister() }
        binding.loginButton.setOnClickListener { validateInput() }
    }

    private fun toRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun validateInput() {
        with (binding) {
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()
            when {
                email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    loginEmail.error = "Masukkan Email Dengan Benar"
                }
                password.isEmpty() || password.length < 8 -> {
                    loginPassword.error = "Masukkan Password Dengan Benar"
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