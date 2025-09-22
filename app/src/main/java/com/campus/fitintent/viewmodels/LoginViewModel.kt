package com.campus.fitintent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.fitintent.models.User
import com.campus.fitintent.repository.UserRepository
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.launch

/**
 * ViewModel for Login/Registration screen
 */
class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<Result<User>>()
    val loginState: LiveData<Result<User>> = _loginState

    private val _registrationState = MutableLiveData<Result<User>>()
    val registrationState: LiveData<Result<User>> = _registrationState

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * Login user
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _loginState.value = Result.Loading

            val result = userRepository.login(email, password)
            _loginState.value = result

            _isLoading.value = false
        }
    }

    /**
     * Register new user
     */
    fun register(
        email: String,
        password: String,
        confirmPassword: String,
        username: String,
        fullName: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _registrationState.value = Result.Loading

            // Validate passwords match
            if (password != confirmPassword) {
                _registrationState.value = Result.Error("Passwords do not match")
                _isLoading.value = false
                return@launch
            }

            val result = userRepository.register(email, password, username, fullName)
            _registrationState.value = result

            _isLoading.value = false
        }
    }

    /**
     * Check if user is already logged in
     */
    fun checkLoginStatus(): Boolean {
        return userRepository.isLoggedIn()
    }

    /**
     * Check if onboarding is complete
     */
    fun isOnboardingComplete(): Boolean {
        return userRepository.isOnboardingComplete()
    }

    /**
     * Clear error states
     */
    fun clearErrors() {
        if (_loginState.value is Result.Error) {
            _loginState.value = null
        }
        if (_registrationState.value is Result.Error) {
            _registrationState.value = null
        }
    }
}