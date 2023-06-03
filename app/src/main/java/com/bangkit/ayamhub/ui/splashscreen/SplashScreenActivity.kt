package com.bangkit.ayamhub.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.ui.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.orange2)
        // Menunggu selama beberapa detik (misalnya, 2 detik) dengan menggunakan handler atau timer
        Handler().postDelayed({
            // Setelah waktu yang ditentukan, pindah ke activity utama
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // Menutup activity splash agar tidak dapat kembali ke splash screen dengan tombol back
        }, 2000) // Ubah waktu penundaan sesuai kebutuhan Anda
    }
}