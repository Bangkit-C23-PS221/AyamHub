package com.bangkit.ayamhub.ui.makefarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bangkit.ayamhub.data.network.retrofit.ApiConfig
import com.bangkit.ayamhub.databinding.ActivityLocationDropDownBinding
import com.bangkit.ayamhub.helpers.Reusable

class MakeFarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationDropDownBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationDropDownBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.spKabupaten.isEnabled = false
        binding.spKecamatan.isEnabled = false

        locationSetup()
    }

    private fun locationSetup() {
        var provinsiName = ""
        var kabName = ""
        var kecName = ""

        val provinceAdapter = provideArrayAdapter()
        binding.spProvince.adapter = provinceAdapter

        val kabupatenAdapter = provideArrayAdapter()
        binding.spKabupaten.adapter = kabupatenAdapter

        val kecamatanAdapter = provideArrayAdapter()
        binding.spKecamatan.adapter = kecamatanAdapter

        getProvince().observe(this) { listProvince ->
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
                        provinsiName = sortedProvince[position-1].nama
                        kabupatenAdapter.clear()
                        numbaa.value = selectedProvince
                        binding.spKabupaten.isEnabled = true
                        binding.spKecamatan.isEnabled = false
                    } else {
                        kecamatanAdapter.clear()
                        kabupatenAdapter.clear()
                        binding.spKabupaten.isEnabled = false
                        binding.spKecamatan.isEnabled = false
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        getKabupaten().observe(this) { listKab ->
            Reusable.showToast(this@MakeFarmActivity, "Redo")
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
                        val selectedProvince = sortedKab[position-1].id.toInt()
                        kabName = sortedKab[position-1].nama
                        kecamatanAdapter.clear()
                        numbaa2.value = selectedProvince
                        binding.spKecamatan.isEnabled = true
                    } else {
                        kecamatanAdapter.clear()
                        binding.spKecamatan.isEnabled = false
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        getKecamatan().observe(this) { listKec ->
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
                    if (position != 0) {
                        kecName = sortedKec[position-1].nama
                        binding.tvLocation.text = "$provinsiName, $kabName, $kecName"
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

    private val numbaa = MutableLiveData<Int>()
    private val numbaa2 = MutableLiveData<Int>()

    private fun getProvince() = liveData {
        try {
            val response = ApiConfig.getLocationApiService().getProvince()
            emit(response)
        } catch (e: Exception) {
            Log.e("DropDownAct", "Error: ${e.message.toString()}")
        }
    }

    private fun getKabupaten() = numbaa.switchMap { selectedProvince ->
        liveData {
            try {
                val response = ApiConfig.getLocationApiService().getKabupaten(selectedProvince)
                emit(response)
            } catch (e: Exception) {
                Log.e("DropDownAct", "Error: ${e.message.toString()}")
            }
        }
    }

    private fun getKecamatan() = numbaa2.switchMap { selectedKab ->
        liveData {
            try {
                val response = ApiConfig.getLocationApiService().getKecamatan(selectedKab)
                emit(response)
            } catch (e: Exception) {
                Log.e("DropDownAct", "Error: ${e.message.toString()}")
            }
        }
    }
}