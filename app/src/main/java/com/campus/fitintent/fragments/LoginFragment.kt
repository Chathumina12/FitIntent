package com.campus.fitintent.fragments

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.R
import com.campus.fitintent.activities.AuthActivity
import com.campus.fitintent.activities.MainActivity
import com.campus.fitintent.activities.OnboardingActivity
import com.campus.fitintent.databinding.FragmentLoginBinding
import com.campus.fitintent.utils.Result
import com.campus.fitintent.utils.ViewModelFactory
import com.campus.fitintent.viewmodels.AuthViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val app = requireActivity().application as FitIntentApplication
        val factory = ViewModelFactory(app.userRepository)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        // Password visibility toggle
        binding.passwordVisibilityToggle.setOnClickListener {
            togglePasswordVisibility()
        }

        // Login button
        binding.loginButton.setOnClickListener {
            performLogin()
        }

        // Sign up link
        binding.signupLink.setOnClickListener {
            (requireActivity() as AuthActivity).showSignupFragment()
        }

        // Forgot password link
        binding.forgotPasswordLink.setOnClickListener {
            showForgotPasswordDialog()
        }

        // Google Sign-In button (placeholder)
        binding.googleSignInButton.setOnClickListener {
            Snackbar.make(binding.root, "Google Sign-In coming soon!", Snackbar.LENGTH_SHORT).show()
        }

        // Clear error on text change
        binding.emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.emailInputLayout.error = null
            }
        }

        binding.passwordInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.passwordInputLayout.error = null
            }
        }
    }

    private fun observeViewModel() {
        // Loading state
        authViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loginButton.isEnabled = !isLoading
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.loginButton.text = if (isLoading) "" else getString(R.string.login)
        }

        // Validation errors
        authViewModel.validationError.observe(viewLifecycleOwner) { error ->
            error?.let {
                when {
                    it.contains("email", ignoreCase = true) -> {
                        binding.emailInputLayout.error = it
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

        // Login result
        authViewModel.loginState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    val user = result.data
                    val intent = if (user.isOnboardingComplete) {
                        Intent(requireActivity(), MainActivity::class.java)
                    } else {
                        Intent(requireActivity(), OnboardingActivity::class.java)
                    }
                    startActivity(intent)
                    requireActivity().finish()
                }
                is Result.Error -> {
                    val errorMessage = result.exception.message ?: getString(R.string.error_login_failed)
                    Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.retry)) { performLogin() }
                        .show()
                }
                is Result.Loading -> {
                    // Handled by isLoading observer
                }
            }
        }
    }

    private fun performLogin() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()
        val rememberMe = binding.rememberMeCheckbox.isChecked

        // Clear previous errors
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null

        // Basic validation
        if (email.isEmpty()) {
            binding.emailInputLayout.error = getString(R.string.error_field_required)
            return
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = getString(R.string.error_field_required)
            return
        }

        // Perform login
        authViewModel.login(email, password, rememberMe)
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
        // Move cursor to end
        binding.passwordInput.setSelection(binding.passwordInput.text?.length ?: 0)
    }

    private fun showForgotPasswordDialog() {
        // TODO: Implement password reset functionality
        Snackbar.make(
            binding.root,
            "Password reset feature coming soon!",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}