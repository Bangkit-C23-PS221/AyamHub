package com.bangkit.ayamhub.ui.register.peternak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bangkit.ayamhub.data.online.Result
import com.bangkit.ayamhub.databinding.ActivityRegisterPeternakBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory

class RegisterPeternak : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterPeternakBinding
    private val viewModel: RegisterPeternakViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPeternakBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Reusable.showToast(this, "Fail to Register")
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {

    }
}