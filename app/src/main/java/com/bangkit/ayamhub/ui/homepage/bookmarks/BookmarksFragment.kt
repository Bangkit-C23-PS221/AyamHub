package com.bangkit.ayamhub.ui.homepage.bookmarks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.ayamhub.data.network.Result
import com.bangkit.ayamhub.data.network.response.BookmarkResponse
import com.bangkit.ayamhub.databinding.FragmentBookmarksBinding
import com.bangkit.ayamhub.helpers.Reusable
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.detail.DetailActivity

class BookmarksFragment : Fragment() {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BookmarksViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    private fun getData() {
        viewModel.getAllBookmark().observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> {
                    showLoading(true)
                    Log.e("getDataFun", "Loading")
                }
                is Result.Success -> {
                    showLoading(false)
                    setupAdapter(result.data)
                    Log.e("getDataFun", "Got it")
                }
                is Result.Error -> {
                    showLoading(false)
                    Reusable.showToast(requireContext(), "Gagal memuat data")
                }
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupAdapter(data: List<BookmarkResponse>) {
        binding.rvBookmark.adapter = BookmarkAdapter(data, requireContext()) { listFarm ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, listFarm.idFarm)
            startActivity(intent)
        }
        binding.rvBookmark.layoutManager = LinearLayoutManager(requireContext())
    }
}