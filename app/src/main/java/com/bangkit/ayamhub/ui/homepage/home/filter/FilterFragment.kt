package com.bangkit.ayamhub.ui.homepage.home.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bangkit.ayamhub.databinding.FragmentFilterBinding
import com.bangkit.ayamhub.helpers.*
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.farmform.FarmFormActivity
import com.bangkit.ayamhub.ui.homepage.home.HomeAdapter
import com.bangkit.ayamhub.ui.homepage.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterFragment(
    private val adapter: HomeAdapter
) : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkFilter()
        setStatusListener()
        getCheckedStatus(adapter.currentStatusFilter)
        locationSetup(adapter.currentLocFilter)

        binding.btnAddFilter.setOnClickListener { callAdapterFilter() }
        binding.btnRemoveFilter.setOnClickListener { removeFilter() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkFilter() {
        with(binding) {
            when {
                adapter.currentStatusFilter.isNotEmpty() -> {
                    btnRemoveFilter.visibility = View.VISIBLE
                }
                adapter.currentLocFilter.isNotEmpty() -> {
                    btnRemoveFilter.visibility = View.VISIBLE
                }
                else -> {
                    btnRemoveFilter.visibility = View.GONE
                }
            }
        }
    }

    private fun getCheckedStatus(status: String) {
        if (status == HomeAdapter.ACTIVE) {
            binding.cbReady.isChecked = true
        } else if (status == HomeAdapter.NOT_ACTIVE) {
            binding.cbNotReady.isChecked = true
        }
    }

    private fun getLocation() {
        with(binding) {
            val province = getSelectedItem(spProvince)
            val city = getSelectedItem(spKabupaten)
            val district = getSelectedItem(spKecamatan)

            adapter.currentLocFilter = if (province.isEmpty()){
                ""
            } else if (city.isEmpty()) {
                province
            } else {
                "$province, $city, $district"
            }
        }
    }

    private fun getStatus() {
        with(binding) {
            if (cbReady.isChecked) {
                adapter.currentStatusFilter = HomeAdapter.ACTIVE
            } else if (cbNotReady.isChecked) {
                adapter.currentStatusFilter = HomeAdapter.NOT_ACTIVE
            } else {
                adapter.currentStatusFilter = ""
            }
        }
    }

    private fun setStatusListener() {
        with(binding) {
            cbReady.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    cbNotReady.isChecked = false
                }
            }
            cbNotReady.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    cbReady.isChecked = false
                }
            }
        }
    }

    private fun callAdapterFilter() {
        getLocation()
        getStatus()
        adapter.filterBy()
        viewModel.checkItem(adapter.isDataEmpty())
        dismiss()
    }

    private fun removeFilter() {
        adapter.currentLocFilter = ""
        adapter.currentStatusFilter = ""
        adapter.filterBy()
        viewModel.checkItem(adapter.isDataEmpty())
        dismiss()
    }

    private fun locationSetup(location: String = "") {
        val provinceAdapter = provideArrayAdapter(requireContext())
        binding.spProvince.adapter = provinceAdapter

        val kabupatenAdapter = provideArrayAdapter(requireContext())
        binding.spKabupaten.adapter = kabupatenAdapter

        val kecamatanAdapter = provideArrayAdapter(requireContext())
        binding.spKecamatan.adapter = kecamatanAdapter

        viewModel.getProvinsi().observe(this) { listProvince ->
            val sortedProvince = setupDropdown(listProvince, provinceAdapter, FarmFormActivity.PROVINCE)
            val myProvince = Reusable.getProvince(location)
            if (myProvince.isNotEmpty()) {
                val index = provinceAdapter.getPosition(myProvince)
                viewModel.provinceId.value = sortedProvince[index-1].id.toInt()
                binding.spProvince.setSelection(index)
            }

            binding.spProvince.onItemSelectedListener = onItemSelected { position ->
                if (position != 0) {
                    val selectedProvince = sortedProvince[position-1].id.toInt()
                    kabupatenAdapter.clear()
                    viewModel.provinceId.value = selectedProvince

                    binding.spKabupaten.isEnabled = true
                    binding.spKecamatan.isEnabled = false
                } else {
                    kecamatanAdapter.clear()
                    kabupatenAdapter.clear()
                    binding.spKabupaten.isEnabled = false
                    binding.spKecamatan.isEnabled = false
                }
            }
        }

        viewModel.getKabupaten().observe(this) { listKab ->
            val sortedKab = setupDropdown(listKab, kabupatenAdapter, FarmFormActivity.CITY)
            val myKab = Reusable.getCity(location)
            if (myKab.isNotEmpty()) {
                val index = kabupatenAdapter.getPosition(myKab)
                viewModel.kabupatenId.value = sortedKab[index-1].id.toInt()
                binding.spKabupaten.setSelection(index)
            }

            binding.spKabupaten.onItemSelectedListener = onItemSelected { position ->
                if (position != 0) {
                    val selectedKabupaten = sortedKab[position-1].id.toInt()
                    kecamatanAdapter.clear()
                    viewModel.kabupatenId.value = selectedKabupaten

                    binding.spKecamatan.isEnabled = true
                } else {
                    kecamatanAdapter.clear()
                    binding.spKecamatan.isEnabled = false
                }
            }
        }

        viewModel.getKecamatan().observe(this) { listKec ->
            setupDropdown(listKec, kecamatanAdapter, FarmFormActivity.DISTRICT)
            val myKec = Reusable.getDistrict(location)
            if (myKec.isNotEmpty()) {
                val index = kecamatanAdapter.getPosition(myKec)
                binding.spKecamatan.setSelection(index)
            }
        }
    }

}