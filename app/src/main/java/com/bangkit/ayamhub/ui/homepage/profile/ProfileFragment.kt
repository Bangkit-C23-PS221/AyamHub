package com.bangkit.ayamhub.ui.homepage.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bangkit.ayamhub.R
import com.bangkit.ayamhub.databinding.FragmentProfileBinding
import com.bangkit.ayamhub.helpers.viewmodelfactory.ViewModelFactory
import com.bangkit.ayamhub.ui.farmer.FarmerActivity
import com.bangkit.ayamhub.ui.login.LoginActivity
import com.bangkit.ayamhub.ui.farmform.FarmFormActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by activityViewModels {
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

        binding.logoutButton.setOnClickListener { logOut() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buttonSetup() {
        var intent: Intent? = null
        viewModel.getLevel.observe(viewLifecycleOwner) {
            if (it == UMKM) {
                intent = Intent(requireContext(), FarmFormActivity::class.java)
            } else {
                intent = Intent(requireContext(), FarmerActivity::class.java)
                binding.profileTv.text = getString(R.string.your_farm)
                binding.imageView3.setImageResource(R.drawable.open_farm)

            }
        }

        binding.makeFarm.setOnClickListener { startActivity(intent) }
    }

    private fun logOut() {
        viewModel.logOut()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finishAffinity()
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

    companion object {
        const val UMKM = "umkm"
    }

}