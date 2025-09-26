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
import com.campus.fitintent.databinding.FragmentQuiz2Binding
import com.campus.fitintent.viewmodels.ViewModelFactory
import com.campus.fitintent.viewmodels.OnboardingViewModel

class QuizFragment2 : Fragment() {

    private var _binding: FragmentQuiz2Binding? = null
    private val binding get() = _binding!!
    private lateinit var onboardingViewModel: OnboardingViewModel
    private var selectedOption: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuiz2Binding.inflate(inflater, container, false)
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
        // Option 1: Rarely
        binding.option1Card.setOnClickListener {
            selectOption("rarely", binding.option1Check)
        }

        // Option 2: 1-2 times per week
        binding.option2Card.setOnClickListener {
            selectOption("1_2_times_week", binding.option2Check)
        }

        // Option 3: 3-5 times per week
        binding.option3Card.setOnClickListener {
            selectOption("3_5_times_week", binding.option3Check)
        }

        // Option 4: Daily
        binding.option4Card.setOnClickListener {
            selectOption("daily", binding.option4Check)
        }

        // Next button
        binding.nextButton.setOnClickListener {
            selectedOption?.let { option ->
                onboardingViewModel.setExerciseFrequency(option)
                // Navigation will be handled by the parent activity
            }
        }

        // Skip question
        binding.skipQuestionText.setOnClickListener {
            onboardingViewModel.setExerciseFrequency("") // Empty for skipped
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
            "rarely" -> binding.option1Card.strokeColor = primaryColor
            "1_2_times_week" -> binding.option2Card.strokeColor = primaryColor
            "3_5_times_week" -> binding.option3Card.strokeColor = primaryColor
            "daily" -> binding.option4Card.strokeColor = primaryColor
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}