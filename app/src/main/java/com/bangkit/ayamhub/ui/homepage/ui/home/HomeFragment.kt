package com.bangkit.ayamhub.ui.homepage.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.ayamhub.data.local.DummyFlameChaser
import com.bangkit.ayamhub.databinding.FragmentHomeBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.homepage.ui.home.HomeViewModel
import com.bangkit.ayamhub.ui.homepage.ui.home.filter.LocationFilterFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val flameChaser = DummyFlameChaser.myFlameChaser
    private lateinit var homeAdapter: HomeAdapter
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        processSearchBar()
        binding.btnFilter.setOnClickListener { showFilter() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun processSearchBar() {
        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val enteredText = binding.search.text.toString()
                setSearchFilter(enteredText)
                true
            } else {
                false
            }
        }
    }

    private fun setupAdapter() {
        homeAdapter = HomeAdapter(flameChaser, requireContext()) {
            Reusable.showToast(requireContext(), it.name)
        }
        binding.rvPeternak.adapter = homeAdapter
        binding.rvPeternak.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setSearchFilter(filter: String) {
        homeAdapter.filterBySearch(filter)
        Reusable.showToast(requireContext(), "Uyeah")
    }

    private fun showFilter() {
        val fragment = LocationFilterFragment(homeAdapter)
        fragment.show(childFragmentManager, "tag")
    }
}