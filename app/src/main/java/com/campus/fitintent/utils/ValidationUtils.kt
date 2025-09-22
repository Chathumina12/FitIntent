package com.campus.fitintent.utils

import android.util.Patterns
import java.util.regex.Pattern

/**
 * Utility class for input validation
 */
object ValidationUtils {

    data class ValidationResult(
        val isValid: Boolean,
        val message: String = ""
    )

    /**
     * Validate email format
     */
    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, "Email is required")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                ValidationResult(false, "Invalid email format")
            email.length > 100 ->
                ValidationResult(false, "Email is too long")
            else -> ValidationResult(true)
        }
    }

    /**
     * Validate password strength
     */
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() ->
                ValidationResult(false, "Password is required")
            password.length < 6 ->
                ValidationResult(false, "Password must be at least 6 characters")
            password.length > 50 ->
                ValidationResult(false, "Password is too long")
            !containsLetterAndNumber(password) ->
                ValidationResult(false, "Password must contain both letters and numbers")
            else -> ValidationResult(true)
        }
    }

    /**
     * Validate strong password (for enhanced security)
     */
    fun validateStrongPassword(password: String): ValidationResult {
        return when {
            password.isBlank() ->
                ValidationResult(false, "Password is required")
            password.length < 8 ->
                ValidationResult(false, "Password must be at least 8 characters")
            !hasUpperCase(password) ->
                ValidationResult(false, "Password must contain at least one uppercase letter")
            !hasLowerCase(password) ->
                ValidationResult(false, "Password must contain at least one lowercase letter")
            !hasDigit(password) ->
                ValidationResult(false, "Password must contain at least one number")
            !hasSpecialChar(password) ->
                ValidationResult(false, "Password must contain at least one special character")
            else -> ValidationResult(true)
        }
    }

    /**
     * Validate username
     */
    fun validateUsername(username: String): ValidationResult {
        val usernamePattern = "^[a-zA-Z0-9_]{3,20}$"
        return when {
            username.isBlank() ->
                ValidationResult(false, "Username is required")
            username.length < 3 ->
                ValidationResult(false, "Username must be at least 3 characters")
            username.length > 20 ->
                ValidationResult(false, "Username must be less than 20 characters")
            !Pattern.matches(usernamePattern, username) ->
                ValidationResult(false, "Username can only contain letters, numbers, and underscores")
            else -> ValidationResult(true)
        }
    }

    /**
     * Validate full name
     */
    fun validateFullName(name: String): ValidationResult {
        return when {
            name.isBlank() ->
                ValidationResult(false, "Name is required")
            name.length < 2 ->
                ValidationResult(false, "Name is too short")
            name.length > 50 ->
                ValidationResult(false, "Name is too long")
            !name.matches(Regex("^[a-zA-Z\\s]+$")) ->
                ValidationResult(false, "Name can only contain letters and spaces")
            else -> ValidationResult(true)
        }
    }

    /**
     * Validate age
     */
    fun validateAge(age: Int?): ValidationResult {
        return when {
            age == null ->
                ValidationResult(false, "Age is required")
            age < 13 ->
                ValidationResult(false, "Must be at least 13 years old")
            age > 120 ->
                ValidationResult(false, "Invalid age")
            else -> ValidationResult(true)
        }
    }

    /**
     * Validate height (in cm)
     */
    fun validateHeight(height: Float?): ValidationResult {
        return when {
            height == null ->
                ValidationResult(false, "Height is required")
            height < 50 ->
                ValidationResult(false, "Invalid height")
            height > 300 ->
                ValidationResult(false, "Invalid height")
            else -> ValidationResult(true)
        }
    }

    /**
     * Validate weight (in kg)
     */
    fun validateWeight(weight: Float?): ValidationResult {
        return when {
            weight == null ->
                ValidationResult(false, "Weight is required")
            weight < 20 ->
                ValidationResult(false, "Invalid weight")
            weight > 500 ->
                ValidationResult(false, "Invalid weight")
            else -> ValidationResult(true)
        }
    }

    /**
     * Validate phone number (basic validation)
     */
    fun validatePhoneNumber(phone: String): ValidationResult {
        val phonePattern = "^[+]?[0-9]{10,15}$"
        return when {
            phone.isBlank() ->
                ValidationResult(false, "Phone number is required")
            !Pattern.matches(phonePattern, phone) ->
                ValidationResult(false, "Invalid phone number format")
            else -> ValidationResult(true)
        }
    }

    // Helper functions
    private fun containsLetterAndNumber(password: String): Boolean {
        return password.any { it.isLetter() } && password.any { it.isDigit() }
    }

    private fun hasUpperCase(password: String): Boolean {
        return password.any { it.isUpperCase() }
    }

    private fun hasLowerCase(password: String): Boolean {
        return password.any { it.isLowerCase() }
    }

    private fun hasDigit(password: String): Boolean {
        return password.any { it.isDigit() }
    }

    private fun hasSpecialChar(password: String): Boolean {
        val specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?"
        return password.any { specialChars.contains(it) }
    }
}