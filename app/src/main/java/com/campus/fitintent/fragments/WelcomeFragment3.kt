package com.campus.fitintent.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.campus.fitintent.activities.OnboardingActivity
import com.campus.fitintent.databinding.FragmentWelcome3Binding

class WelcomeFragment3 : Fragment() {

    private var _binding: FragmentWelcome3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcome3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.continueButton.setOnClickListener {
            // Navigate to onboarding
            navigateToOnboarding()
        }

        binding.skipIntroText.setOnClickListener {
            // Skip to onboarding
            navigateToOnboarding()
        }
    }

    private fun navigateToOnboarding() {
        val intent = Intent(requireContext(), OnboardingActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}