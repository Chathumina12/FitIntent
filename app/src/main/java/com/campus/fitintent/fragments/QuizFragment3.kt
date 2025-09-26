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
import com.campus.fitintent.databinding.FragmentQuiz3Binding
import com.campus.fitintent.viewmodels.ViewModelFactory
import com.campus.fitintent.viewmodels.OnboardingViewModel

class QuizFragment3 : Fragment() {

    private var _binding: FragmentQuiz3Binding? = null
    private val binding get() = _binding!!
    private lateinit var onboardingViewModel: OnboardingViewModel
    private var selectedOption: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuiz3Binding.inflate(inflater, container, false)
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
        // Option 1: Cardio
        binding.option1Card.setOnClickListener {
            selectOption("cardio", binding.option1Check)
        }

        // Option 2: Strength Training
        binding.option2Card.setOnClickListener {
            selectOption("strength", binding.option2Check)
        }

        // Option 3: Flexibility/Yoga
        binding.option3Card.setOnClickListener {
            selectOption("flexibility", binding.option3Check)
        }

        // Option 4: HIIT
        binding.option4Card.setOnClickListener {
            selectOption("hiit", binding.option4Check)
        }

        // Next button
        binding.nextButton.setOnClickListener {
            selectedOption?.let { option ->
                onboardingViewModel.setWorkoutPreference(option)
                // Navigation will be handled by the parent activity
            }
        }

        // Skip question
        binding.skipQuestionText.setOnClickListener {
            onboardingViewModel.setWorkoutPreference("") // Empty for skipped
        }
    }

    private fun selectOption(option: String, checkView: View) {
        // Clear all selections
        clearAllSelections()

        // Set selected option
        selectedOption = option
        checkView.visibility = View.VISIBLE

        // Update card background and enable next button
        updateCardSelection(option)
        binding.nextButton.isEnabled = true
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
            "cardio" -> binding.option1Card.strokeColor = primaryColor
            "strength" -> binding.option2Card.strokeColor = primaryColor
            "flexibility" -> binding.option3Card.strokeColor = primaryColor
            "hiit" -> binding.option4Card.strokeColor = primaryColor
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}