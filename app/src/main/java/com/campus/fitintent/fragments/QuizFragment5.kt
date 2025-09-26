package com.campus.fitintent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.R
import com.campus.fitintent.databinding.FragmentQuiz5Binding
import com.campus.fitintent.viewmodels.ViewModelFactory
import com.campus.fitintent.viewmodels.OnboardingViewModel

class QuizFragment5 : Fragment() {

    private var _binding: FragmentQuiz5Binding? = null
    private val binding get() = _binding!!
    private lateinit var onboardingViewModel: OnboardingViewModel
    private var selectedOption: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuiz5Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val app = requireActivity().application as FitIntentApplication
        val factory = ViewModelFactory.getInstance(app)
        onboardingViewModel = ViewModelProvider(requireActivity(), factory)[OnboardingViewModel::class.java]

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Option 1: 15-30 minutes
        binding.option1Card.setOnClickListener {
            selectOption("15_30_min", binding.option1Check)
        }

        // Option 2: 30-45 minutes
        binding.option2Card.setOnClickListener {
            selectOption("30_45_min", binding.option2Check)
        }

        // Option 3: 45-60 minutes
        binding.option3Card.setOnClickListener {
            selectOption("45_60_min", binding.option3Check)
        }

        // Option 4: 60+ minutes
        binding.option4Card.setOnClickListener {
            selectOption("60_plus_min", binding.option4Check)
        }

        // Finish button
        binding.finishButton.setOnClickListener {
            selectedOption?.let { option ->
                onboardingViewModel.setTimeAvailability(option)
                onboardingViewModel.completeOnboarding()
                // Navigation will be handled by the parent activity
            }
        }

        // Skip question
        binding.skipQuestionText.setOnClickListener {
            onboardingViewModel.setTimeAvailability("") // Empty for skipped
            onboardingViewModel.completeOnboarding()
        }
    }

    private fun selectOption(option: String, checkView: View) {
        // Clear all selections
        clearAllSelections()

        // Set selected option
        selectedOption = option
        checkView.visibility = View.VISIBLE

        // Update card background and enable finish button
        updateCardSelection(option)
        binding.finishButton.isEnabled = true
    }

    private fun clearAllSelections() {
        binding.option1Check.visibility = View.GONE
        binding.option2Check.visibility = View.GONE
        binding.option3Check.visibility = View.GONE
        binding.option4Check.visibility = View.GONE

        // Reset card backgrounds
        binding.option1Card.strokeColor = ContextCompat.getColor(requireContext(), R.color.divider)
        binding.option2Card.strokeColor = ContextCompat.getColor(requireContext(), R.color.divider)
        binding.option3Card.strokeColor = ContextCompat.getColor(requireContext(), R.color.divider)
        binding.option4Card.strokeColor = ContextCompat.getColor(requireContext(), R.color.divider)
    }

    private fun updateCardSelection(option: String) {
        val primaryColor = ContextCompat.getColor(requireContext(), R.color.primary)

        when (option) {
            "15_30_min" -> binding.option1Card.strokeColor = primaryColor
            "30_45_min" -> binding.option2Card.strokeColor = primaryColor
            "45_60_min" -> binding.option3Card.strokeColor = primaryColor
            "60_plus_min" -> binding.option4Card.strokeColor = primaryColor
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}