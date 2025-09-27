package com.campus.fitintent.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.campus.fitintent.models.User
import com.campus.fitintent.repository.UserRepository
import com.campus.fitintent.utils.Result
import com.campus.fitintent.utils.ValidationUtils
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<Result<User>>()
    val loginState: LiveData<Result<User>> = _loginState

    private val _signupState = MutableLiveData<Result<User>>()
    val signupState: LiveData<Result<User>> = _signupState

    private val _validationError = MutableLiveData<String?>()
    val validationError: LiveData<String?> = _validationError

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String, rememberMe: Boolean = false) {
        // Clear previous errors
        _validationError.value = null

        // Validate inputs
        val emailValidation = ValidationUtils.validateEmail(email)
        if (!emailValidation.isValid) {
            _validationError.value = emailValidation.message
            return
        }

        val passwordValidation = ValidationUtils.validatePassword(password)
        if (!passwordValidation.isValid) {
            _validationError.value = passwordValidation.message
            return
        }

        // Perform login
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val user = userRepository.getUserByEmail(email)
                if (user != null && BCrypt.checkpw(password, user.passwordHash)) {
                    // Save session if remember me is checked
                    if (rememberMe) {
                        userRepository.saveUserSession(user)
                    }

                    _loginState.value = Result.Success(user)
                } else {
                    // Handle failed login
                    _loginState.value = Result.Error("Invalid email or password")
                }
            } catch (e: Exception) {
                _loginState.value = Result.Error(e.message ?: "Login failed")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signup(name: String, email: String, password: String, confirmPassword: String, acceptTerms: Boolean) {
        // Clear previous errors
        _validationError.value = null

        // Validate name
        val nameValidation = ValidationUtils.validateFullName(name)
        if (!nameValidation.isValid) {
            _validationError.value = nameValidation.message
            return
        }

        // Validate email
        val emailValidation = ValidationUtils.validateEmail(email)
        if (!emailValidation.isValid) {
            _validationError.value = emailValidation.message
            return
        }

        // Validate password
        val passwordValidation = ValidationUtils.validatePassword(password)
        if (!passwordValidation.isValid) {
            _validationError.value = passwordValidation.message
            return
        }

        // Check password match
        if (password != confirmPassword) {
            _validationError.value = "Passwords don't match"
            return
        }

        // Check terms acceptance
        if (!acceptTerms) {
            _validationError.value = "You must accept the Terms and Conditions"
            return
        }

        // Perform signup
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Check if email already exists
                val existingUser = userRepository.getUserByEmail(email)
                if (existingUser != null) {
                    _signupState.value = Result.Error("An account with this email already exists")
                    return@launch
                }

                // Create new user using repository method
                val createUserResult = userRepository.createUser(
                    email = email,
                    password = password,
                    username = email.substringBefore("@"),
                    fullName = name
                )

                when (createUserResult) {
                    is Result.Success -> {
                        val createdUser = createUserResult.data

                        // Auto login after successful signup
                        userRepository.saveUserSession(createdUser)

                        _signupState.value = Result.Success(createdUser)
                    }
                    is Result.Error -> {
                        _signupState.value = Result.Error(createUserResult.message)
                    }
                    else -> {
                        _signupState.value = Result.Error("Unknown error occurred during signup")
                    }
                }
            } catch (e: Exception) {
                _signupState.value = Result.Error(e.message ?: "Signup failed")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun checkPasswordStrength(password: String): PasswordStrength {
        var strength = 0

        if (password.length >= 8) strength++
        if (password.length >= 12) strength++
        if (password.any { it.isUpperCase() }) strength++
        if (password.any { it.isLowerCase() }) strength++
        if (password.any { it.isDigit() }) strength++
        if (password.any { !it.isLetterOrDigit() }) strength++

        return when (strength) {
            0, 1 -> PasswordStrength.WEAK
            2, 3 -> PasswordStrength.MEDIUM
            4, 5 -> PasswordStrength.STRONG
            else -> PasswordStrength.VERY_STRONG
        }
    }

    fun clearErrors() {
        _validationError.value = null
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.clearUserSession()
        }
    }

    enum class PasswordStrength {
        WEAK,
        MEDIUM,
        STRONG,
        VERY_STRONG
    }
}