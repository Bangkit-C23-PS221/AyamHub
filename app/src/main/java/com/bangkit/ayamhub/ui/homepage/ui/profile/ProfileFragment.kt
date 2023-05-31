package com.bangkit.ayamhub.ui.homepage.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.ayamhub.databinding.FragmentProfileBinding
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.makefarm.MakeFarmActivity


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileSetup()
        buttonSetup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buttonSetup() {
        binding.makeFarm.setOnClickListener {
            startActivity(Intent(requireContext(), MakeFarmActivity::class.java))
        }
    }

    private fun profileSetup() {
        with(binding) {
            viewModel.getName.observe(viewLifecycleOwner) {
                tvName.text = it
            }

            viewModel.getEmail.observe(viewLifecycleOwner) {
                tvEmail.text = it
            }

            viewModel.getPhone.observe(viewLifecycleOwner) {
                tvPhone.text = it
            }
        }
    }

}