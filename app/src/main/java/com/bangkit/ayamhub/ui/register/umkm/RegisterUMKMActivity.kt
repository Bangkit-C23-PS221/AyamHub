package com.bangkit.ayamhub.ui.register.umkm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.databinding.ActivityRegisterUmkmBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.login.peternak.LoginPeternakanActivity

class RegisterUMKMActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUmkmBinding
    private val viewModel: RegisterUMKMViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUmkmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener { validateInput() }
    }

    private fun signUp(
        name: String,
        username: String,
        password: String,
        email: String,
        phoneNumber: String
    ) {
        viewModel.signUp(name, username, password, email, phoneNumber).observe(this){result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        val intent = Intent(this, LoginPeternakanActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Log.e( "RegisterUMKMActivity","OnFailure: ${result.error}")
                        Reusable.showToast(this, "Oops gagal mendaftar")
                    }
                }
            }
        }
    }

    private fun validateInput() {
        with (binding) {
            val name = etName.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()

            when {
                name.isEmpty() -> {
                    etName.error = "Tolong isi namanya ya"
                }
                username.isEmpty() -> {
                    etUsername.error = "Tolong isi usernamenya"
                }
                password.isEmpty() || password.length < 8 -> {
                    etPassword.error = "Tolong isi passwordnya dengan benar ya"
                }
                email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    etEmail.error = "Tolong isi email dengan benar ya"
                }
                phone.isEmpty() -> {
                    etPhone.error = "Tolong isi nomor handphone dengan benar ya"
                }
                else -> {
                    signUp(name, username, password, email, phone)
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