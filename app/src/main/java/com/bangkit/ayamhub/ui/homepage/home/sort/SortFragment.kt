package com.bangkit.ayamhub.ui.homepage.home.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.bangkit.ayamhub.databinding.FragmentSortBinding
import com.bangkit.ayamhub.ui.homepage.home.HomeAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortFragment(
    private val adapter: HomeAdapter
) : BottomSheetDialogFragment() {

    private var _binding: FragmentSortBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rememberLastSort()

        binding.btnSortConfirm.setOnClickListener { callAdapterSort() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun callAdapterSort() {
        getUserOption()
        adapter.sortBy()
        dismiss()
    }

    private fun getUserOption() {
        with(binding) {
            val checkedId = rgSort.checkedRadioButtonId
            when(rgSort.findViewById<RadioButton>(checkedId)) {
                rbName -> {
                    adapter.currentSort = HomeAdapter.NAME
                }
                rbPriceLow -> {
                    adapter.currentSort = HomeAdapter.CHEAPEST
                }
                rbPriceHigh -> {
                    adapter.currentSort = HomeAdapter.EXPENSIVE
                }
                rbLatest -> {
                    adapter.currentSort = HomeAdapter.LATEST
                }
                rbOldest -> {
                    adapter.currentSort = HomeAdapter.OLDEST
                }
            }
        }
    }
    
    private fun rememberLastSort() {
        when(adapter.currentSort) {
            HomeAdapter.NAME -> {
                binding.rbName.isChecked = true
            }
            HomeAdapter.CHEAPEST -> {
                binding.rbPriceLow.isChecked = true
            }
            HomeAdapter.EXPENSIVE -> {
                binding.rbPriceHigh.isChecked = true
            }
            HomeAdapter.OLDEST -> {
                binding.rbOldest.isChecked = true
            }
            else -> {
                binding.rbLatest.isChecked = true
            }
        }
    }
}