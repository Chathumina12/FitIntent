package com.campus.fitintent.viewmodels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.models.User
import com.campus.fitintent.repository.ProgressRepository
import com.campus.fitintent.repository.UserRepository
import com.campus.fitintent.utils.PreferencesManager
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ViewModel for Profile Fragment
 * Handles user profile data, progress statistics, and profile-related operations
 */
class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as FitIntentApplication
    private val userRepository = app.userRepository
    private val progressRepository = app.progressRepository
    private val preferencesManager = app.preferencesManager

    // LiveData for UI observation
    private val _userProfile = MutableLiveData<Result<User>>()
    val userProfile: LiveData<Result<User>> = _userProfile

    private val _progressStats = MutableLiveData<Result<ProgressStats>>()
    val progressStats: LiveData<Result<ProgressStats>> = _progressStats

    private val _profileImageUpdate = MutableLiveData<Result<String>>()
    val profileImageUpdate: LiveData<Result<String>> = _profileImageUpdate

    private val _dataExportResult = MutableLiveData<Result<File>>()
    val dataExportResult: LiveData<Result<File>> = _dataExportResult

    private val _logoutResult = MutableLiveData<Result<Boolean>>()
    val logoutResult: LiveData<Result<Boolean>> = _logoutResult

    /**
     * Data class for progress statistics
     */
    data class ProgressStats(
        val totalWorkouts: Int = 0,
        val currentStreak: Int = 0,
        val totalPoints: Int = 0,
        val daysActive: Int = 0,
        val badgesEarned: Int = 0,
        val weeklyGoalProgress: Float = 0f
    )

    /**
     * Load current user profile from database
     */
    fun loadUserProfile() {
        viewModelScope.launch {
            _userProfile.value = Result.Loading

            try {
                val currentUserId = preferencesManager.getUserId()
                if (currentUserId != -1L) {
                    when (val result = userRepository.getCurrentUser()) {
                        is Result.Success -> {
                            _userProfile.value = Result.Success(result.data)
                        }
                        is Result.Error -> {
                            _userProfile.value = Result.Error(result.message)
                        }
                        is Result.Loading -> {
                            // This shouldn't happen in getCurrentUser(), but handle it
                            _userProfile.value = Result.Error("Unexpected loading state")
                        }
                    }
                } else {
                    _userProfile.value = Result.Error("No user logged in")
                }
            } catch (e: Exception) {
                _userProfile.value = Result.Error("Failed to load user profile: ${e.message}")
            }
        }
    }

    /**
     * Load progress statistics for the current user
     */
    fun loadProgressStats() {
        viewModelScope.launch {
            _progressStats.value = Result.Loading

            try {
                val currentUserId = preferencesManager.getUserId()
                if (currentUserId != -1L) {
                    val stats = calculateProgressStats(currentUserId)
                    _progressStats.value = Result.Success(stats)
                } else {
                    _progressStats.value = Result.Error("No user logged in")
                }
            } catch (e: Exception) {
                _progressStats.value = Result.Error("Failed to load progress stats: ${e.message}")
            }
        }
    }

    /**
     * Update user profile image
     */
    fun updateProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            _profileImageUpdate.value = Result.Loading

            try {
                val currentUserId = preferencesManager.getUserId()
                if (currentUserId != -1L) {
                    val imagePath = saveProfileImage(imageUri, currentUserId)
                    // TODO: Implement updateProfileImage in repository
                    // userRepository.updateProfileImage(currentUserId, imagePath)
                    _profileImageUpdate.value = Result.Success(imagePath)
                } else {
                    _profileImageUpdate.value = Result.Error("No user logged in")
                }
            } catch (e: Exception) {
                _profileImageUpdate.value = Result.Error("Failed to update profile image: ${e.message}")
            }
        }
    }

    /**
     * Remove user profile image
     */
    fun removeProfileImage() {
        viewModelScope.launch {
            try {
                val currentUserId = preferencesManager.getUserId()
                if (currentUserId != -1L) {
                    // Get current user to delete old image file
                    when (val result = userRepository.getCurrentUser()) {
                        is Result.Success -> {
                            result.data.profileImagePath?.let { imagePath ->
                                val imageFile = File(imagePath)
                                if (imageFile.exists()) {
                                    imageFile.delete()
                                }
                            }
                        }
                        is Result.Error -> {
                            _profileImageUpdate.value = Result.Error("Failed to get user profile: ${result.message}")
                            return@launch
                        }
                        is Result.Loading -> {
                            // This shouldn't happen, but handle it
                            _profileImageUpdate.value = Result.Error("Unexpected loading state")
                            return@launch
                        }
                    }

                    // TODO: Implement updateProfileImage in repository
                    // userRepository.updateProfileImage(currentUserId, null)
                    _profileImageUpdate.value = Result.Success("")
                } else {
                    _profileImageUpdate.value = Result.Error("No user logged in")
                }
            } catch (e: Exception) {
                _profileImageUpdate.value = Result.Error("Failed to remove profile image: ${e.message}")
            }
        }
    }

    /**
     * Export user data as JSON file
     */
    fun exportUserData() {
        viewModelScope.launch {
            _dataExportResult.value = Result.Loading

            try {
                val currentUserId = preferencesManager.getUserId()
                if (currentUserId != -1L) {
                    val exportFile = createDataExport(currentUserId)
                    _dataExportResult.value = Result.Success(exportFile)
                } else {
                    _dataExportResult.value = Result.Error("No user logged in")
                }
            } catch (e: Exception) {
                _dataExportResult.value = Result.Error("Failed to export data: ${e.message}")
            }
        }
    }

    /**
     * Logout current user
     */
    fun logout() {
        viewModelScope.launch {
            _logoutResult.value = Result.Loading

            try {
                // Clear any cached data if needed
                preferencesManager.clearSession()
                _logoutResult.value = Result.Success(true)
            } catch (e: Exception) {
                _logoutResult.value = Result.Error("Failed to logout: ${e.message}")
            }
        }
    }

    /**
     * Calculate comprehensive progress statistics
     */
    private suspend fun calculateProgressStats(userId: Long): ProgressStats = withContext(Dispatchers.IO) {
        // TODO: Implement actual database queries
        // For now, using mock data until DAO methods are properly implemented
        val totalWorkouts = 15
        val currentStreak = 7
        val totalPoints = (totalWorkouts * 50) + (currentStreak * 10)
        val badgesEarned = 5
        val daysActive = 22
        val weeklyGoalProgress = 80f

        ProgressStats(
            totalWorkouts = totalWorkouts,
            currentStreak = currentStreak,
            totalPoints = totalPoints,
            daysActive = daysActive,
            badgesEarned = badgesEarned,
            weeklyGoalProgress = weeklyGoalProgress
        )
    }

    /**
     * Calculate weekly progress percentage
     */
    private suspend fun calculateWeeklyProgress(userId: Long): Float = withContext(Dispatchers.IO) {
        val userResult = userRepository.getCurrentUser()
        if (userResult !is Result.Success) return@withContext 0f
        val user = userResult.data
        val weeklyGoal = user.weeklyWorkoutGoal

        if (weeklyGoal <= 0) return@withContext 0f

        // TODO: Implement actual database query
        // For now, using mock data
        val workoutsThisWeek = 3
        return@withContext (workoutsThisWeek.toFloat() / weeklyGoal.toFloat() * 100f).coerceAtMost(100f)
    }

    /**
     * Save profile image to internal storage
     */
    private suspend fun saveProfileImage(imageUri: Uri, userId: Long): String = withContext(Dispatchers.IO) {
        val context = getApplication<Application>()
        val inputStream = context.contentResolver.openInputStream(imageUri)
            ?: throw IOException("Cannot open image")

        // Create profile images directory
        val profileDir = File(context.filesDir, "profile_images")
        if (!profileDir.exists()) {
            profileDir.mkdirs()
        }

        // Create file with user ID and timestamp
        val timestamp = System.currentTimeMillis()
        val imageFile = File(profileDir, "profile_${userId}_$timestamp.jpg")

        // Compress and save image
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val resizedBitmap = resizeBitmap(bitmap, 400, 400) // Resize to 400x400

        FileOutputStream(imageFile).use { outputStream ->
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        }

        bitmap.recycle()
        resizedBitmap.recycle()

        return@withContext imageFile.absolutePath
    }

    /**
     * Resize bitmap to specified dimensions while maintaining aspect ratio
     */
    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val scale = Math.min(maxWidth.toFloat() / width, maxHeight.toFloat() / height)

        val newWidth = (width * scale).toInt()
        val newHeight = (height * scale).toInt()

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    /**
     * Create data export file
     */
    private suspend fun createDataExport(userId: Long): File = withContext(Dispatchers.IO) {
        val context = getApplication<Application>()
        val exportDir = File(context.getExternalFilesDir(null), "exports")
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }

        val timestamp = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())
        val exportFile = File(exportDir, "fitintent_data_$timestamp.json")

        // Gather all user data
        val userResult = userRepository.getCurrentUser()
        val user = if (userResult is Result.Success) userResult.data else null
        // TODO: Implement actual DAO methods
        val workoutSessions = emptyList<com.campus.fitintent.models.WorkoutSession>() // database.workoutSessionDao().getAllWorkoutSessionsForUser(userId)
        val habits = emptyList<com.campus.fitintent.models.Habit>() // database.habitDao().getHabitsForUser(userId)
        val badges = emptyList<com.campus.fitintent.models.UserBadge>() // database.userBadgeDao().getBadgesForUser(userId)
        val streaks = emptyList<com.campus.fitintent.models.Streak>() // database.streakDao().getStreaksForUser(userId)

        // Create JSON export
        val exportData = JSONObject().apply {
            put("exportDate", Date().toString())
            put("user", user?.let { userToJson(it) } ?: JSONObject())
            put("workoutSessions", workoutSessions.map { workoutSessionToJson(it) })
            put("habits", habits.map { habitToJson(it) })
            put("badges", badges.map { badgeToJson(it) })
            put("streaks", streaks.map { streakToJson(it) })
        }

        // Write to file
        exportFile.writeText(exportData.toString(2))

        return@withContext exportFile
    }

    // JSON conversion helper methods
    private fun userToJson(user: User) = JSONObject().apply {
        put("id", user.id)
        put("email", user.email)
        put("username", user.username)
        put("fullName", user.fullName)
        put("age", user.age)
        put("gender", user.gender?.name)
        put("height", user.height)
        put("weight", user.weight)
        put("primaryGoal", user.primaryGoal?.name)
        put("createdAt", user.createdAt.toString())
        put("weeklyWorkoutGoal", user.weeklyWorkoutGoal)
        // Don't export sensitive data like password hash
    }

    private fun workoutSessionToJson(session: com.campus.fitintent.models.WorkoutSession) = JSONObject().apply {
        put("id", session.id)
        put("workoutId", session.workoutId)
        put("startTime", session.startTime.toString())
        put("endTime", session.endTime?.toString())
        put("durationMinutes", session.durationMinutes)
        put("completed", session.isCompleted)
        put("caloriesBurned", session.caloriesBurned)
    }

    private fun habitToJson(habit: com.campus.fitintent.models.Habit) = JSONObject().apply {
        put("id", habit.id)
        put("name", habit.name)
        put("description", habit.description)
        put("category", habit.category.name)
        put("targetDays", habit.targetDays)
        put("completedDays", habit.completedDays)
        put("currentStreak", habit.currentStreak)
        put("isActive", habit.isActive)
        put("createdAt", habit.createdAt.toString())
    }

    private fun badgeToJson(userBadge: com.campus.fitintent.models.UserBadge) = JSONObject().apply {
        put("badgeId", userBadge.badgeId)
        put("unlockedAt", userBadge.unlockedAt?.toString())
        put("currentProgress", userBadge.currentProgress)
        put("isUnlocked", userBadge.isUnlocked)
    }

    private fun streakToJson(streak: com.campus.fitintent.models.Streak) = JSONObject().apply {
        put("id", streak.id)
        put("type", streak.type.name)
        put("currentStreak", streak.currentStreak)
        put("longestStreak", streak.longestStreak)
        put("lastActivityDate", streak.lastActivityDate?.toString())
        put("isActive", streak.isActive)
    }
}