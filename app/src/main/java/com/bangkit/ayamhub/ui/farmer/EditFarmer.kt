package com.bangkit.ayamhub.ui.makefarm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.ayamhub.databinding.ActivityEditFarmerBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.farmer.FarmerActivity
import com.bangkit.ayamhub.ui.register.RegisterActivity


class EditFarmer : AppCompatActivity() {

    private lateinit var binding: ActivityEditFarmerBinding
    private var provinsi = ""
    private var kabupaten = ""
    private var kecamatan = ""
    private val viewModel: MakeFarmViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFarmerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.spKabupaten.isEnabled = false
        binding.spKecamatan.isEnabled = false

        locationSetup()
        getInputedLocation()

        binding.gambarButton.setOnClickListener { getInputedLocation() }

        binding.saveButton.setOnClickListener {
            val intent = Intent(this@EditFarmer, FarmerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun locationSetup() {

        val provinceAdapter = provideArrayAdapter()
        binding.spProvince.adapter = provinceAdapter

        val kabupatenAdapter = provideArrayAdapter()
        binding.spKabupaten.adapter = kabupatenAdapter

        val kecamatanAdapter = provideArrayAdapter()
        binding.spKecamatan.adapter = kecamatanAdapter

        viewModel.getProvinsi.observe(this) { listProvince ->
            val sortedProvince = listProvince.sortedBy { it.nama }
            provinceAdapter.add("Pilih Provinsi")
            provinceAdapter.addAll(sortedProvince.map { it.nama })
            provinceAdapter.notifyDataSetChanged()

            binding.spProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        val selectedProvince = sortedProvince[position-1].id.toInt()
                        kabupatenAdapter.clear()
                        viewModel.provinceId.value = selectedProvince

                        binding.spKabupaten.isEnabled = true
                        binding.spKecamatan.isEnabled = false

                        provinsi = sortedProvince[position-1].nama
                        kabupaten = ""
                        kecamatan = ""
                    } else {
                        provinsi = ""
                        kabupaten = ""
                        kecamatan = ""
                        kecamatanAdapter.clear()
                        kabupatenAdapter.clear()
                        binding.spKabupaten.isEnabled = false
                        binding.spKecamatan.isEnabled = false
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        viewModel.getKabupaten.observe(this) { listKab ->
            val sortedKab = listKab.sortedBy { it.nama }
            kabupatenAdapter.add("Pilih Kabupaten")
            kabupatenAdapter.addAll(sortedKab.map { it.nama })
            kabupatenAdapter.notifyDataSetChanged()

            binding.spKabupaten.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        val selectedKabupaten = sortedKab[position-1].id.toInt()
                        kecamatanAdapter.clear()
                        viewModel.kabupatenId.value = selectedKabupaten

                        binding.spKecamatan.isEnabled = true

                        kabupaten = sortedKab[position-1].nama
                        kecamatan = ""
                    } else {
                        kabupaten = ""
                        kecamatan = ""
                        kecamatanAdapter.clear()
                        binding.spKecamatan.isEnabled = false
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        viewModel.getKecamatan.observe(this) { listKec ->
            val sortedKec = listKec.sortedBy { it.nama }
            kecamatanAdapter.add("Pilih Kecamatan")
            kecamatanAdapter.addAll(sortedKec.map { it.nama })
            kecamatanAdapter.notifyDataSetChanged()

            binding.spKecamatan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    kecamatan = if (position != 0) {
                        sortedKec[position-1].nama
                    } else {
                        ""
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun provideArrayAdapter(): ArrayAdapter<String> {
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    private fun getInputedLocation() {
        Reusable.showToast(this, "$provinsi, $kabupaten, $kecamatan")
    }


}