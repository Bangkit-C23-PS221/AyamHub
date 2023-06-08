package com.bangkit.ayamhub.ui.homepage.detection

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.bangkit.ayamhub.data.suggestion.DataPenyakit
import com.bangkit.ayamhub.databinding.ActivitySuggestionBinding

class SuggestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val virusType = intent.getStringExtra("data")

        val penyakit = DataPenyakit.daftarPenyakit.find { it.nama == virusType }

        if (penyakit != null) {
            binding.imgPenyakit.setImageResource(penyakit.gambar)
            binding.penyakit.text = penyakit.nama
            binding.deskripsi.text = penyakit.deskripsi
            val gejalaAdapter = ArrayAdapter(this, R.layout.simple_list_item_1, penyakit.gejala)
            binding.listGejala.adapter = gejalaAdapter
            val saranAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, penyakit.saran)
            binding.lisSaran.adapter = saranAdapter
        }

    }
}