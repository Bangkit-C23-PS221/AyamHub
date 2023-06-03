package com.bangkit.ayamhub.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.MainActivity
import com.bangkit.ayamhub.ui.homepage.HomeActivity


class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: SplashScreenViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setView()
        setDuration()
    }

    private fun setView() {
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.orange2)

    }

    private fun setDuration() {
        val duration = 2000L
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getUserData.observe(this) {
                val intent: Intent = if (it.isNotEmpty()) {
                    Intent(this@SplashScreenActivity, HomeActivity::class.java)
                } else {
                    Intent(this@SplashScreenActivity, MainActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }, duration)
    }

}