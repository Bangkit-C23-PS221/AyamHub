package com.bangkit.ayamhub.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.ayamhub.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_detail)
    }
}