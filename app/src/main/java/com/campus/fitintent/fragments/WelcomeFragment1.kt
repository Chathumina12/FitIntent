package com.campus.fitintent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.campus.fitintent.databinding.FragmentWelcome1Binding

class WelcomeFragment1 : Fragment() {

    private var _binding: FragmentWelcome1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcome1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.continueButton.setOnClickListener {
            // Navigate to next welcome screen
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                .replace(android.R.id.content, WelcomeFragment2())
                .commit()
        }

        binding.skipIntroText.setOnClickListener {
            // Skip to onboarding
            skipToOnboarding()
        }
    }

    private fun skipToOnboarding() {
        // Navigate directly to onboarding activity
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}