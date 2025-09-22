package com.campus.fitintent.viewmodels

import androidx.lifecycle.*
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.ProgressRepository
import com.campus.fitintent.repository.UserRepository
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.launch

/**
 * ViewModel for Profile screen
 */
class ProfileViewModel(
    private val userRepository: UserRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    // Placeholder implementation - extend as needed
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
}