package com.bangkit.ayamhub.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.databinding.ActivityLoginBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.homepage.HomeActivity
import com.bangkit.ayamhub.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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
                    loginEmail.error = getString(R.string.form_email_error)
                }
                password.isEmpty() || password.length < 8 -> {
                    loginPassword.error = getString(R.string.form_password_error)
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
                        with(result.data) {
                            saveData(accessToken, idUser.toString(), name, email, phone, userLevel)
                        }
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

    private fun saveData(
        token: String,
        id: String,
        name: String,
        email: String,
        phone: String,
        level: String
    ) {
        with(viewModel) {
            setToken(token)
            setId(id)
            setName(name)
            setEmail(email)
            setPhone(phone)
            setLevel(level)
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