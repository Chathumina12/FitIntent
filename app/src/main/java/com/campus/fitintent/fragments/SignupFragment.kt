package com.campus.fitintent.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.R
import com.campus.fitintent.activities.AuthActivity
import com.campus.fitintent.activities.OnboardingActivity
import com.campus.fitintent.databinding.FragmentSignupBinding
import com.campus.fitintent.utils.Result
import com.campus.fitintent.viewmodels.ViewModelFactory
import com.campus.fitintent.viewmodels.AuthViewModel
import com.google.android.material.snackbar.Snackbar

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val app = requireActivity().application as FitIntentApplication
        val factory = ViewModelFactory.getInstance(app)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        // Continue button (main signup button)
        binding.continueButton.setOnClickListener {
            performSignup()
        }

        // Google Sign-In button (placeholder)
        binding.googleSignInButton.setOnClickListener {
            Snackbar.make(binding.root, "Google Sign-In coming soon!", Snackbar.LENGTH_SHORT).show()
        }


        // Clear errors on focus
        setupErrorClearing()
    }

    private fun setupErrorClearing() {
        binding.emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.emailInputLayout.error = null
        }

        binding.usernameInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.usernameInputLayout.error = null
        }

        binding.passwordInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.passwordInputLayout.error = null
        }
    }

    private fun observeViewModel() {
        // Loading state
        authViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.continueButton.isEnabled = !isLoading
            binding.continueButton.text = if (isLoading) "Loading..." else "Continue"
        }

        // Validation errors
        authViewModel.validationError.observe(viewLifecycleOwner) { error ->
            error?.let {
                when {
                    it.contains("email", ignoreCase = true) -> {
                        binding.emailInputLayout.error = it
                    }
                    it.contains("username", ignoreCase = true) -> {
                        binding.usernameInputLayout.error = it
                    }
                    it.contains("password", ignoreCase = true) -> {
                        binding.passwordInputLayout.error = it
                    }
                    else -> {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        // Signup result
        authViewModel.signupState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    // Navigate to onboarding after successful signup
                    val intent = Intent(requireActivity(), OnboardingActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                is Result.Error -> {
                    val errorMessage = result.message.ifEmpty { getString(R.string.error_signup_failed) }
                    Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.retry)) { performSignup() }
                        .show()
                }
                is Result.Loading -> {
                    // Handled by isLoading observer
                }
            }
        }
    }

    private fun performSignup() {
        val email = binding.emailInput.text.toString().trim()
        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()
        val staySignedIn = binding.staySignedInCheckbox.isChecked

        // Clear previous errors
        clearAllErrors()

        // Basic validation
        if (email.isEmpty()) {
            binding.emailInputLayout.error = getString(R.string.error_field_required)
            return
        }

        if (username.isEmpty()) {
            binding.usernameInputLayout.error = getString(R.string.error_field_required)
            return
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = getString(R.string.error_field_required)
            return
        }

        // Perform signup
        authViewModel.signup(username, email, password, password, true) // acceptTerms = true for now
    }

    private fun clearAllErrors() {
        binding.emailInputLayout.error = null
        binding.usernameInputLayout.error = null
        binding.passwordInputLayout.error = null
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}