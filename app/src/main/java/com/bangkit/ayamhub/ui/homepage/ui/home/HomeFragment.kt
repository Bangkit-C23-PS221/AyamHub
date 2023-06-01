package com.bangkit.ayamhub.ui.homepage.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.ayamhub.data.local.DummyFlameChaser
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.data.network.response.ListFarmResponse
import com.bangkit.ayamhub.databinding.FragmentHomeBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.detail.DetailActivity
import com.bangkit.ayamhub.ui.homepage.ui.home.HomeViewModel
import com.bangkit.ayamhub.ui.homepage.ui.home.filter.LocationFilterFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
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

        getListFarm()
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

    private fun getListFarm() {
        viewModel.getListFarm.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    setupAdapter(result.data)
                }
                is Result.Error -> {
                    showLoading(false)
                    Log.e("HomeFragment", "OnFailure: ${result.error}")
                }
            }
        }
    }

    private fun setupAdapter(data: List<ListFarmResponse>) {
        homeAdapter = HomeAdapter(data, requireContext()) { item ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, item.idFarm)
            startActivity(Intent(requireContext(), DetailActivity::class.java))
        }
        binding.rvPeternak.adapter = homeAdapter
        binding.rvPeternak.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setSearchFilter(filter: String) {
        homeAdapter.filterBySearch(filter)
    }

    private fun showFilter() {
        val fragment = LocationFilterFragment(homeAdapter)
        fragment.show(childFragmentManager, "FilterFragment")
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}