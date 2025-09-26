package com.campus.fitintent.fragments

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.R
import com.campus.fitintent.activities.AuthActivity
import com.campus.fitintent.activities.OnboardingActivity
import com.campus.fitintent.databinding.FragmentSignupBinding
import com.campus.fitintent.utils.Result
<<<<<<< HEAD
import com.campus.fitintent.utils.ViewModelFactory
=======
import com.campus.fitintent.viewmodels.ViewModelFactory
>>>>>>> 818ab1f (Updated)
import com.campus.fitintent.viewmodels.AuthViewModel
import com.google.android.material.snackbar.Snackbar

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

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
<<<<<<< HEAD
        val factory = ViewModelFactory(app.userRepository)
=======
        val factory = ViewModelFactory.getInstance(app)
>>>>>>> 818ab1f (Updated)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        // Password visibility toggles
        binding.passwordVisibilityToggle.setOnClickListener {
            togglePasswordVisibility()
        }

        binding.confirmPasswordVisibilityToggle.setOnClickListener {
            toggleConfirmPasswordVisibility()
        }

        // Password strength indicator
        binding.passwordInput.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                updatePasswordStrengthIndicator(s.toString())
            }
        })

        // Sign up button
        binding.signupButton.setOnClickListener {
            performSignup()
        }

        // Login link
        binding.loginLink.setOnClickListener {
            (requireActivity() as AuthActivity).showLoginFragment()
        }

        // Google Sign-In button (placeholder)
        binding.googleSignInButton.setOnClickListener {
            Snackbar.make(binding.root, "Google Sign-In coming soon!", Snackbar.LENGTH_SHORT).show()
        }

        // Terms link
        binding.termsLink.setOnClickListener {
            showTermsDialog()
        }

        // Clear errors on focus
        setupErrorClearing()
    }

    private fun setupErrorClearing() {
        binding.nameInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.nameInputLayout.error = null
        }

        binding.emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.emailInputLayout.error = null
        }

        binding.passwordInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.passwordInputLayout.error = null
        }

        binding.confirmPasswordInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.confirmPasswordInputLayout.error = null
        }
    }

    private fun observeViewModel() {
        // Loading state
        authViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.signupButton.isEnabled = !isLoading
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.signupButton.text = if (isLoading) "" else getString(R.string.signup)
        }

        // Validation errors
        authViewModel.validationError.observe(viewLifecycleOwner) { error ->
            error?.let {
                when {
                    it.contains("name", ignoreCase = true) -> {
                        binding.nameInputLayout.error = it
                    }
                    it.contains("email", ignoreCase = true) -> {
                        binding.emailInputLayout.error = it
                    }
                    it.contains("password", ignoreCase = true) && !it.contains("match") -> {
                        binding.passwordInputLayout.error = it
                    }
                    it.contains("match", ignoreCase = true) -> {
                        binding.confirmPasswordInputLayout.error = it
                    }
                    it.contains("terms", ignoreCase = true) -> {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
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
<<<<<<< HEAD
                    val errorMessage = result.exception.message ?: getString(R.string.error_signup_failed)
=======
                    val errorMessage = result.message.ifEmpty { getString(R.string.error_signup_failed) }
>>>>>>> 818ab1f (Updated)
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
        val name = binding.nameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()
        val acceptTerms = binding.termsCheckbox.isChecked

        // Clear previous errors
        clearAllErrors()

        // Basic validation
        if (name.isEmpty()) {
            binding.nameInputLayout.error = getString(R.string.error_field_required)
            return
        }

        if (email.isEmpty()) {
            binding.emailInputLayout.error = getString(R.string.error_field_required)
            return
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = getString(R.string.error_field_required)
            return
        }

        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordInputLayout.error = getString(R.string.error_field_required)
            return
        }

        // Perform signup
        authViewModel.signup(name, email, password, confirmPassword, acceptTerms)
    }

    private fun clearAllErrors() {
        binding.nameInputLayout.error = null
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
        binding.confirmPasswordInputLayout.error = null
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        if (isPasswordVisible) {
            binding.passwordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.passwordVisibilityToggle.setImageResource(R.drawable.ic_visibility_off)
        } else {
            binding.passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.passwordVisibilityToggle.setImageResource(R.drawable.ic_visibility)
        }
        binding.passwordInput.setSelection(binding.passwordInput.text?.length ?: 0)
    }

    private fun toggleConfirmPasswordVisibility() {
        isConfirmPasswordVisible = !isConfirmPasswordVisible
        if (isConfirmPasswordVisible) {
            binding.confirmPasswordInput.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.confirmPasswordVisibilityToggle.setImageResource(R.drawable.ic_visibility_off)
        } else {
            binding.confirmPasswordInput.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.confirmPasswordVisibilityToggle.setImageResource(R.drawable.ic_visibility)
        }
        binding.confirmPasswordInput.setSelection(binding.confirmPasswordInput.text?.length ?: 0)
    }

    private fun updatePasswordStrengthIndicator(password: String) {
        val strength = authViewModel.checkPasswordStrength(password)

        binding.passwordStrengthIndicator.visibility = if (password.isNotEmpty()) View.VISIBLE else View.GONE

        val (progress, color, text) = when (strength) {
            AuthViewModel.PasswordStrength.WEAK -> Triple(25, R.color.error, "Weak")
            AuthViewModel.PasswordStrength.MEDIUM -> Triple(50, R.color.warning, "Medium")
            AuthViewModel.PasswordStrength.STRONG -> Triple(75, R.color.success, "Strong")
            AuthViewModel.PasswordStrength.VERY_STRONG -> Triple(100, R.color.success, "Very Strong")
        }

        binding.passwordStrengthIndicator.progress = progress
        binding.passwordStrengthIndicator.progressTintList =
            ContextCompat.getColorStateList(requireContext(), color)
        binding.passwordStrengthText.text = text
        binding.passwordStrengthText.setTextColor(ContextCompat.getColor(requireContext(), color))
    }

    private fun showTermsDialog() {
        // TODO: Show actual terms and conditions
        Snackbar.make(
            binding.root,
            "Terms and Conditions:\n\n• Use the app responsibly\n• Track your fitness progress\n• Stay motivated!\n• Have fun!",
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}