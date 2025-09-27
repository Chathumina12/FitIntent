package com.campus.fitintent.repository

import com.campus.fitintent.database.dao.UserDao
import com.campus.fitintent.models.User
import com.campus.fitintent.models.Gender
import com.campus.fitintent.models.ActivityLevel
import com.campus.fitintent.models.FitnessGoal
import com.campus.fitintent.utils.PreferencesManager
import com.campus.fitintent.utils.Result
import com.campus.fitintent.utils.ValidationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.mindrot.jbcrypt.BCrypt
import java.util.Date

/**
 * Repository for User-related operations
 * Handles business logic, validation, and data access
 */
class UserRepository(
    private val userDao: UserDao,
    private val preferencesManager: PreferencesManager
) {
    /**
     * Authenticate user with email and password
     */
    suspend fun login(email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            // Validate input
            if (!ValidationUtils.isValidEmail(email)) {
                return@withContext Result.Error("Invalid email format")
            }

            if (password.length < 6) {
                return@withContext Result.Error("Password must be at least 6 characters")
            }

            // Get user by email
            val user = userDao.getActiveUserByEmail(email.lowercase())
                ?: return@withContext Result.Error("User not found or account deactivated")

            // Verify password
            if (!BCrypt.checkpw(password, user.passwordHash)) {
                return@withContext Result.Error("Invalid email or password")
            }

            // Save user session
            preferencesManager.saveUserId(user.id)
            preferencesManager.saveUserEmail(user.email)

            Result.Success(user)
        } catch (e: Exception) {
            Result.Error("Login failed: ${e.message}")
        }
    }

    /**
     * Register new user
     */
    suspend fun register(
        email: String,
        password: String,
        username: String,
        fullName: String
    ): Result<User> = withContext(Dispatchers.IO) {
        try {
            // Validate input
            val emailValidation = ValidationUtils.validateEmail(email)
            if (!emailValidation.isValid) {
                return@withContext Result.Error(emailValidation.message)
            }

            val passwordValidation = ValidationUtils.validatePassword(password)
            if (!passwordValidation.isValid) {
                return@withContext Result.Error(passwordValidation.message)
            }

            if (username.length < 3) {
                return@withContext Result.Error("Username must be at least 3 characters")
            }

            if (fullName.isBlank()) {
                return@withContext Result.Error("Full name is required")
            }

            // Check if email already exists
            if (userDao.checkEmailExists(email.lowercase()) > 0) {
                return@withContext Result.Error("Email already registered")
            }

            // Hash password with BCrypt
            val passwordHash = BCrypt.hashpw(password, BCrypt.gensalt(12))

            // Create user
            val user = User(
                email = email.lowercase(),
                username = username,
                passwordHash = passwordHash,
                fullName = fullName,
                createdAt = Date(),
                updatedAt = Date()
            )

            val userId = userDao.insertUser(user)
            val createdUser = user.copy(id = userId)

            // Save user session
            preferencesManager.saveUserId(userId)
            preferencesManager.saveUserEmail(email)

            Result.Success(createdUser)
        } catch (e: Exception) {
            Result.Error("Registration failed: ${e.message}")
        }
    }

    /**
     * Get current logged-in user
     */
    suspend fun getCurrentUser(): Result<User> = withContext(Dispatchers.IO) {
        try {
            val userId = preferencesManager.getUserId()
            if (userId == -1L) {
                return@withContext Result.Error("No user logged in")
            }

            val user = userDao.getUserById(userId)
                ?: return@withContext Result.Error("User not found")

            Result.Success(user)
        } catch (e: Exception) {
            Result.Error("Failed to get user: ${e.message}")
        }
    }

    /**
     * Get current user as Flow for reactive updates
     */
    fun getCurrentUserFlow(): Flow<User?>? {
        val userId = preferencesManager.getUserId()
        return if (userId != -1L) {
            userDao.getUserByIdFlow(userId)
        } else null
    }

    /**
     * Update user profile
     */
    suspend fun updateProfile(
        age: Int?,
        gender: String?,
        height: Float?,
        weight: Float?,
        activityLevel: String?,
        primaryGoal: String?,
        targetWeight: Float?,
        weeklyWorkoutGoal: Int?
    ): Result<User> = withContext(Dispatchers.IO) {
        try {
            val currentUser = getCurrentUser()
            if (currentUser !is Result.Success) {
                return@withContext Result.Error("User not found")
            }

            val updatedUser = currentUser.data.copy(
                age = age ?: currentUser.data.age,
                gender = gender?.let {
                    try { Gender.valueOf(it) } catch (e: Exception) { null }
                } ?: currentUser.data.gender,
                height = height ?: currentUser.data.height,
                weight = weight ?: currentUser.data.weight,
                activityLevel = activityLevel?.let {
                    try { ActivityLevel.valueOf(it) } catch (e: Exception) { null }
                } ?: currentUser.data.activityLevel,
                primaryGoal = primaryGoal?.let {
                    try { FitnessGoal.valueOf(it) } catch (e: Exception) { null }
                } ?: currentUser.data.primaryGoal,
                targetWeight = targetWeight ?: currentUser.data.targetWeight,
                weeklyWorkoutGoal = weeklyWorkoutGoal ?: currentUser.data.weeklyWorkoutGoal,
                updatedAt = Date()
            )

            userDao.updateUser(updatedUser)
            Result.Success(updatedUser)
        } catch (e: Exception) {
            Result.Error("Failed to update profile: ${e.message}")
        }
    }

    /**
     * Complete onboarding for user
     */
    suspend fun completeOnboarding(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val userId = preferencesManager.getUserId()
            if (userId == -1L) {
                return@withContext Result.Error("No user logged in")
            }

            userDao.updateOnboardingStatus(userId, true)
            preferencesManager.setOnboardingComplete(true)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error("Failed to complete onboarding: ${e.message}")
        }
    }

    /**
     * Change user password
     */
    suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val currentUser = getCurrentUser()
            if (currentUser !is Result.Success) {
                return@withContext Result.Error("User not found")
            }

            // Verify current password
            if (!BCrypt.checkpw(currentPassword, currentUser.data.passwordHash)) {
                return@withContext Result.Error("Current password is incorrect")
            }

            // Validate new password
            val passwordValidation = ValidationUtils.validatePassword(newPassword)
            if (!passwordValidation.isValid) {
                return@withContext Result.Error(passwordValidation.message)
            }

            // Hash and update password
            val newPasswordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt(12))
            userDao.updatePassword(currentUser.data.id, newPasswordHash)

            Result.Success(true)
        } catch (e: Exception) {
            Result.Error("Failed to change password: ${e.message}")
        }
    }

    /**
     * Logout current user
     */
    suspend fun logout(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            preferencesManager.clearUserSession()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error("Failed to logout: ${e.message}")
        }
    }

    /**
     * Check if user is logged in
     */
    fun isLoggedIn(): Boolean {
        return preferencesManager.getUserId() != -1L
    }

    /**
     * Wrapper for isLoggedIn for compatibility
     */
    fun isUserLoggedIn(): Boolean {
        return isLoggedIn()
    }

    /**
     * Check if this is the first launch of the app
     */
    fun isFirstLaunch(): Boolean {
        return preferencesManager.isFirstLaunch()
    }

    /**
     * Check if onboarding is complete
     */
    fun isOnboardingComplete(): Boolean {
        return preferencesManager.isOnboardingComplete()
    }

    /**
     * Get user by email address
     */
    suspend fun getUserByEmail(email: String): User? = withContext(Dispatchers.IO) {
        try {
            userDao.getActiveUserByEmail(email.lowercase())
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Create new user (alias for register)
     */
    suspend fun createUser(
        email: String,
        password: String,
        username: String,
        fullName: String
    ): Result<User> {
        return register(email, password, username, fullName)
    }

    /**
     * Save user session
     */
    suspend fun saveUserSession(user: User): Boolean {
        return try {
            preferencesManager.saveUserId(user.id)
            preferencesManager.saveUserEmail(user.email)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Clear user session
     */
    suspend fun clearUserSession(): Boolean {
        return try {
            preferencesManager.clearUserSession()
            true
        } catch (e: Exception) {
            false
        }
    }
}