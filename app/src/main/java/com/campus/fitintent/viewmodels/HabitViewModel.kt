package com.campus.fitintent.viewmodels

import androidx.lifecycle.*
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.HabitRepository
import com.campus.fitintent.repository.ProgressRepository
import com.campus.fitintent.repository.UserRepository
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.launch

/**
 * ViewModel for Habit management screens
 */
class HabitViewModel(
    private val habitRepository: HabitRepository,
    private val userRepository: UserRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    // Current user
    private val currentUser = userRepository.getCurrentUserFlow()?.asLiveData()

    // Selected habit for detail view
    private val _selectedHabit = MutableLiveData<Habit?>()
    val selectedHabit: LiveData<Habit?> = _selectedHabit

    // Habit logs for selected habit
    val habitLogs: LiveData<List<HabitLog>> = _selectedHabit.switchMap { habit ->
        if (habit != null) {
            habitRepository.getHabitLogs(habit.id).asLiveData()
        } else {
            MutableLiveData(emptyList())
        }
    }

    // All user habits
    val allHabits: LiveData<List<Habit>> = currentUser?.switchMap { user ->
        if (user != null) {
            habitRepository.getActiveHabits(user.id).asLiveData()
        } else {
            MutableLiveData(emptyList())
        }
    } ?: MutableLiveData(emptyList())

    // Creation state
    private val _creationState = MutableLiveData<Result<Habit>?>()
    val creationState: LiveData<Result<Habit>?> = _creationState

    // Action state for operations
    private val _actionState = MutableLiveData<Result<Boolean>?>()
    val actionState: LiveData<Result<Boolean>?> = _actionState

    // Loading state
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    // Error state
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    /**
     * Create new habit
     */
    fun createHabit(
        name: String,
        description: String,
        category: String,
        frequency: String = "DAILY",
        reminderTime: String? = null,
        reminderEnabled: Boolean = false,
        color: String = "#4CAF50"
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _creationState.value = Result.Loading

            currentUser?.value?.let { user ->
                val habitCategory = try {
                    HabitCategory.valueOf(category)
                } catch (e: Exception) {
                    HabitCategory.OTHER
                }

                val habitFrequency = try {
                    HabitFrequency.valueOf(frequency)
                } catch (e: Exception) {
                    HabitFrequency.DAILY
                }

                val result = habitRepository.createHabit(
                    userId = user.id,
                    name = name,
                    description = description,
                    category = habitCategory,
                    frequency = habitFrequency,
                    reminderTime = reminderTime,
                    reminderEnabled = reminderEnabled,
                    color = color
                )

                _creationState.value = result

                if (result is Result.Success) {
                    // Award habit starter badge
                    progressRepository.awardBadge(user.id, BadgeType.HABIT_STARTER, 1)
                }
            } ?: run {
                _creationState.value = Result.Error("User not logged in")
            }

            _isLoading.value = false
        }
    }

    /**
     * Complete habit for today
     */
    fun completeHabitToday(habitId: Long) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = habitRepository.completeHabitForToday(habitId)

            when (result) {
                is Result.Success -> {
                    _actionState.value = Result.Success(true)

                    // Update streaks
                    currentUser?.value?.let { user ->
                        progressRepository.updateStreak(user.id, StreakType.HABIT_COMPLETION)
                    }

                    // Refresh selected habit if this is the one
                    if (_selectedHabit.value?.id == habitId) {
                        loadHabitDetails(habitId)
                    }
                }
                is Result.Error -> {
                    _error.value = result.message
                    _actionState.value = Result.Error(result.message)
                }
                else -> {}
            }

            _isLoading.value = false
        }
    }

    /**
     * Skip habit for today
     */
    fun skipHabitToday(habitId: Long, reason: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = habitRepository.skipHabitForToday(habitId, reason)

            when (result) {
                is Result.Success -> {
                    _actionState.value = Result.Success(true)

                    // Refresh selected habit if this is the one
                    if (_selectedHabit.value?.id == habitId) {
                        loadHabitDetails(habitId)
                    }
                }
                is Result.Error -> {
                    _error.value = result.message
                    _actionState.value = Result.Error(result.message)
                }
                else -> {}
            }

            _isLoading.value = false
        }
    }

    /**
     * Toggle habit pause state
     */
    fun toggleHabitPause(habitId: Long) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = habitRepository.toggleHabitPause(habitId)

            when (result) {
                is Result.Success -> {
                    _selectedHabit.value = result.data
                    _actionState.value = Result.Success(true)
                }
                is Result.Error -> {
                    _error.value = result.message
                    _actionState.value = Result.Error(result.message)
                }
                else -> {}
            }

            _isLoading.value = false
        }
    }

    /**
     * Delete habit
     */
    fun deleteHabit(habitId: Long) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = habitRepository.deleteHabit(habitId)

            when (result) {
                is Result.Success -> {
                    _actionState.value = Result.Success(true)
                    _selectedHabit.value = null
                }
                is Result.Error -> {
                    _error.value = result.message
                    _actionState.value = Result.Error(result.message)
                }
                else -> {}
            }

            _isLoading.value = false
        }
    }

    /**
     * Load habit details
     */
    fun loadHabitDetails(habitId: Long) {
        viewModelScope.launch {
            _isLoading.value = true

            val habit = habitRepository.getActiveHabits(
                currentUser?.value?.id ?: return@launch
            ).asLiveData().value?.find { it.id == habitId }

            _selectedHabit.value = habit

            _isLoading.value = false
        }
    }

    /**
     * Get habit statistics
     */
    fun getHabitStats(): LiveData<HabitStatistics> {
        return allHabits.map { habits ->
            val totalHabits = habits.size
            val activeHabits = habits.count { it.isActive && !it.isPaused }
            val completedHabits = habits.count { it.isCompleted }
            val pausedHabits = habits.count { it.isPaused }
            val averageStreak = if (habits.isNotEmpty()) {
                habits.map { it.currentStreak }.average().toFloat()
            } else 0f
            val averageCompletion = if (habits.isNotEmpty()) {
                habits.map { it.getCompletionPercentage() }.average().toFloat()
            } else 0f

            HabitStatistics(
                totalHabits = totalHabits,
                activeHabits = activeHabits,
                completedHabits = completedHabits,
                pausedHabits = pausedHabits,
                averageStreak = averageStreak,
                averageCompletion = averageCompletion
            )
        }
    }

    /**
     * Clear states
     */
    fun clearStates() {
        _creationState.value = null
        _actionState.value = null
        _error.value = null
    }

    /**
     * Clear error
     */
    fun clearError() {
        _error.value = null
    }
}

/**
 * Habit statistics data class
 */
data class HabitStatistics(
    val totalHabits: Int,
    val activeHabits: Int,
    val completedHabits: Int,
    val pausedHabits: Int,
    val averageStreak: Float,
    val averageCompletion: Float
)