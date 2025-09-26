package com.campus.fitintent.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.campus.fitintent.models.*
import com.campus.fitintent.repository.*
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*
import org.mindrot.jbcrypt.BCrypt
import java.util.*

/**
 * Comprehensive unit tests for AuthViewModel
 * Tests authentication, validation, password security, and session management
 */
@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var mockUserRepository: UserRepository

    private lateinit var authViewModel: AuthViewModel

    private val testEmail = "test@example.com"
    private val testPassword = "SecurePass123!"
    private val testName = "Test User"
    private val testUserId = 1L

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        authViewModel = AuthViewModel(mockUserRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ========================
    // Login Tests
    // ========================

    @Test
    fun `login with valid credentials succeeds`() = runTest {
        // Arrange
        val hashedPassword = BCrypt.hashpw(testPassword, BCrypt.gensalt(12))
        val testUser = createTestUser(passwordHash = hashedPassword)

        whenever(mockUserRepository.getUserByEmail(testEmail)).thenReturn(testUser)
        whenever(mockUserRepository.updateUser(any())).thenReturn(Result.Success(Unit))

        val loginObserver = mock<Observer<Result<User>>>()
        val loadingObserver = mock<Observer<Boolean>>()
        authViewModel.loginState.observeForever(loginObserver)
        authViewModel.isLoading.observeForever(loadingObserver)

        // Act
        authViewModel.login(testEmail, testPassword, false)

        // Assert
        verify(mockUserRepository).getUserByEmail(testEmail)
        verify(mockUserRepository).updateUser(argThat { user ->
            user.lastLogin != null && user.failedLoginAttempts == 0
        })
        verify(loginObserver).onChanged(argThat { result ->
            result is Result.Success && result.data.email == testEmail
        })
        verify(loadingObserver, atLeast(1)).onChanged(true)
        verify(loadingObserver, atLeast(1)).onChanged(false)
    }

    @Test
    fun `login with remember me saves session`() = runTest {
        // Arrange
        val hashedPassword = BCrypt.hashpw(testPassword, BCrypt.gensalt(12))
        val testUser = createTestUser(passwordHash = hashedPassword)

        whenever(mockUserRepository.getUserByEmail(testEmail)).thenReturn(testUser)
        whenever(mockUserRepository.updateUser(any())).thenReturn(Result.Success(Unit))
        whenever(mockUserRepository.saveUserSession(testUserId)).thenReturn(Unit)

        // Act
        authViewModel.login(testEmail, testPassword, true)

        // Assert
        verify(mockUserRepository).saveUserSession(testUserId)
    }

    @Test
    fun `login with invalid email shows validation error`() = runTest {
        // Arrange
        val invalidEmail = "invalid-email"
        val errorObserver = mock<Observer<String?>>()
        authViewModel.validationError.observeForever(errorObserver)

        // Act
        authViewModel.login(invalidEmail, testPassword)

        // Assert
        verify(errorObserver).onChanged(argThat { error ->
            error?.contains("email", ignoreCase = true) == true
        })
        verify(mockUserRepository, never()).getUserByEmail(any())
    }

    @Test
    fun `login with invalid password shows validation error`() = runTest {
        // Arrange
        val weakPassword = "123"
        val errorObserver = mock<Observer<String?>>()
        authViewModel.validationError.observeForever(errorObserver)

        // Act
        authViewModel.login(testEmail, weakPassword)

        // Assert
        verify(errorObserver).onChanged(argThat { error ->
            error?.contains("password", ignoreCase = true) == true
        })
        verify(mockUserRepository, never()).getUserByEmail(any())
    }

    @Test
    fun `login with wrong credentials fails`() = runTest {
        // Arrange
        val hashedPassword = BCrypt.hashpw("DifferentPassword123!", BCrypt.gensalt(12))
        val testUser = createTestUser(passwordHash = hashedPassword)

        whenever(mockUserRepository.getUserByEmail(testEmail)).thenReturn(testUser)
        whenever(mockUserRepository.updateUser(any())).thenReturn(Result.Success(Unit))

        val loginObserver = mock<Observer<Result<User>>>()
        authViewModel.loginState.observeForever(loginObserver)

        // Act
        authViewModel.login(testEmail, testPassword)

        // Assert
        verify(loginObserver).onChanged(argThat { result ->
            result is Result.Error && result.exception?.message?.contains("Invalid email or password") == true
        })
        verify(mockUserRepository).updateUser(argThat { user ->
            user.failedLoginAttempts == 1
        })
    }

    @Test
    fun `login locks account after multiple failed attempts`() = runTest {
        // Arrange
        val hashedPassword = BCrypt.hashpw("DifferentPassword123!", BCrypt.gensalt(12))
        val testUser = createTestUser(
            passwordHash = hashedPassword,
            failedLoginAttempts = 4 // Already 4 failed attempts
        )

        whenever(mockUserRepository.getUserByEmail(testEmail)).thenReturn(testUser)
        whenever(mockUserRepository.updateUser(any())).thenReturn(Result.Success(Unit))

        val loginObserver = mock<Observer<Result<User>>>()
        authViewModel.loginState.observeForever(loginObserver)

        // Act
        authViewModel.login(testEmail, testPassword)

        // Assert
        verify(mockUserRepository).updateUser(argThat { user ->
            user.failedLoginAttempts == 5 && user.isLocked
        })
        verify(loginObserver).onChanged(argThat { result ->
            result is Result.Error && result.exception?.message?.contains("Account locked") == true
        })
    }

    @Test
    fun `login with non-existent user fails`() = runTest {
        // Arrange
        whenever(mockUserRepository.getUserByEmail(testEmail)).thenReturn(null)

        val loginObserver = mock<Observer<Result<User>>>()
        authViewModel.loginState.observeForever(loginObserver)

        // Act
        authViewModel.login(testEmail, testPassword)

        // Assert
        verify(loginObserver).onChanged(argThat { result ->
            result is Result.Error && result.exception?.message?.contains("Invalid email or password") == true
        })
    }

    @Test
    fun `login handles repository exception gracefully`() = runTest {
        // Arrange
        val exception = RuntimeException("Database error")
        whenever(mockUserRepository.getUserByEmail(testEmail)).thenThrow(exception)

        val loginObserver = mock<Observer<Result<User>>>()
        authViewModel.loginState.observeForever(loginObserver)

        // Act
        authViewModel.login(testEmail, testPassword)

        // Assert
        verify(loginObserver).onChanged(argThat { result ->
            result is Result.Error && result.exception == exception
        })
    }

    // ========================
    // Signup Tests
    // ========================

    @Test
    fun `signup with valid data succeeds`() = runTest {
        // Arrange
        whenever(mockUserRepository.getUserByEmail(testEmail)).thenReturn(null) // Email doesn't exist
        whenever(mockUserRepository.createUser(any())).thenReturn(testUserId)
        whenever(mockUserRepository.saveUserSession(testUserId)).thenReturn(Unit)

        val signupObserver = mock<Observer<Result<User>>>()
        val loadingObserver = mock<Observer<Boolean>>()
        authViewModel.signupState.observeForever(signupObserver)
        authViewModel.isLoading.observeForever(loadingObserver)

        // Act
        authViewModel.signup(testName, testEmail, testPassword, testPassword, true)

        // Assert
        verify(mockUserRepository).createUser(argThat { user ->
            user.email == testEmail &&
            user.fullName == testName &&
            BCrypt.checkpw(testPassword, user.passwordHash)
        })
        verify(mockUserRepository).saveUserSession(testUserId)
        verify(signupObserver).onChanged(argThat { result ->
            result is Result.Success && result.data.email == testEmail
        })
        verify(loadingObserver, atLeast(1)).onChanged(true)
        verify(loadingObserver, atLeast(1)).onChanged(false)
    }

    @Test
    fun `signup with invalid name shows validation error`() = runTest {
        // Arrange
        val invalidName = ""
        val errorObserver = mock<Observer<String?>>()
        authViewModel.validationError.observeForever(errorObserver)

        // Act
        authViewModel.signup(invalidName, testEmail, testPassword, testPassword, true)

        // Assert
        verify(errorObserver).onChanged(argThat { error ->
            error?.contains("name", ignoreCase = true) == true
        })
        verify(mockUserRepository, never()).getUserByEmail(any())
    }

    @Test
    fun `signup with invalid email shows validation error`() = runTest {
        // Arrange
        val invalidEmail = "invalid-email"
        val errorObserver = mock<Observer<String?>>()
        authViewModel.validationError.observeForever(errorObserver)

        // Act
        authViewModel.signup(testName, invalidEmail, testPassword, testPassword, true)

        // Assert
        verify(errorObserver).onChanged(argThat { error ->
            error?.contains("email", ignoreCase = true) == true
        })
        verify(mockUserRepository, never()).getUserByEmail(any())
    }

    @Test
    fun `signup with weak password shows validation error`() = runTest {
        // Arrange
        val weakPassword = "123"
        val errorObserver = mock<Observer<String?>>()
        authViewModel.validationError.observeForever(errorObserver)

        // Act
        authViewModel.signup(testName, testEmail, weakPassword, weakPassword, true)

        // Assert
        verify(errorObserver).onChanged(argThat { error ->
            error?.contains("password", ignoreCase = true) == true
        })
        verify(mockUserRepository, never()).getUserByEmail(any())
    }

    @Test
    fun `signup with password mismatch shows error`() = runTest {
        // Arrange
        val confirmPassword = "DifferentPassword123!"
        val errorObserver = mock<Observer<String?>>()
        authViewModel.validationError.observeForever(errorObserver)

        // Act
        authViewModel.signup(testName, testEmail, testPassword, confirmPassword, true)

        // Assert
        verify(errorObserver).onChanged("Passwords don't match")
        verify(mockUserRepository, never()).getUserByEmail(any())
    }

    @Test
    fun `signup without accepting terms shows error`() = runTest {
        // Arrange
        val errorObserver = mock<Observer<String?>>()
        authViewModel.validationError.observeForever(errorObserver)

        // Act
        authViewModel.signup(testName, testEmail, testPassword, testPassword, false)

        // Assert
        verify(errorObserver).onChanged("You must accept the Terms and Conditions")
        verify(mockUserRepository, never()).getUserByEmail(any())
    }

    @Test
    fun `signup with existing email fails`() = runTest {
        // Arrange
        val existingUser = createTestUser()
        whenever(mockUserRepository.getUserByEmail(testEmail)).thenReturn(existingUser)

        val signupObserver = mock<Observer<Result<User>>>()
        authViewModel.signupState.observeForever(signupObserver)

        // Act
        authViewModel.signup(testName, testEmail, testPassword, testPassword, true)

        // Assert
        verify(signupObserver).onChanged(argThat { result ->
            result is Result.Error && result.exception?.message?.contains("already exists") == true
        })
        verify(mockUserRepository, never()).createUser(any())
    }

    @Test
    fun `signup handles repository exception gracefully`() = runTest {
        // Arrange
        val exception = RuntimeException("Database error")
        whenever(mockUserRepository.getUserByEmail(testEmail)).thenReturn(null)
        whenever(mockUserRepository.createUser(any())).thenThrow(exception)

        val signupObserver = mock<Observer<Result<User>>>()
        authViewModel.signupState.observeForever(signupObserver)

        // Act
        authViewModel.signup(testName, testEmail, testPassword, testPassword, true)

        // Assert
        verify(signupObserver).onChanged(argThat { result ->
            result is Result.Error && result.exception == exception
        })
    }

    // ========================
    // Password Strength Tests
    // ========================

    @Test
    fun `checkPasswordStrength returns WEAK for simple passwords`() {
        assertEquals(AuthViewModel.PasswordStrength.WEAK, authViewModel.checkPasswordStrength("123"))
        assertEquals(AuthViewModel.PasswordStrength.WEAK, authViewModel.checkPasswordStrength("password"))
        assertEquals(AuthViewModel.PasswordStrength.WEAK, authViewModel.checkPasswordStrength("abcdefg"))
    }

    @Test
    fun `checkPasswordStrength returns MEDIUM for moderate passwords`() {
        assertEquals(AuthViewModel.PasswordStrength.MEDIUM, authViewModel.checkPasswordStrength("Password1"))
        assertEquals(AuthViewModel.PasswordStrength.MEDIUM, authViewModel.checkPasswordStrength("abcdefg123"))
        assertEquals(AuthViewModel.PasswordStrength.MEDIUM, authViewModel.checkPasswordStrength("MyPass123"))
    }

    @Test
    fun `checkPasswordStrength returns STRONG for good passwords`() {
        assertEquals(AuthViewModel.PasswordStrength.STRONG, authViewModel.checkPasswordStrength("MyPassword123"))
        assertEquals(AuthViewModel.PasswordStrength.STRONG, authViewModel.checkPasswordStrength("SecurePass1"))
        assertEquals(AuthViewModel.PasswordStrength.STRONG, authViewModel.checkPasswordStrength("GoodPassword123"))
    }

    @Test
    fun `checkPasswordStrength returns VERY_STRONG for excellent passwords`() {
        assertEquals(AuthViewModel.PasswordStrength.VERY_STRONG, authViewModel.checkPasswordStrength("MySecure@Pass123"))
        assertEquals(AuthViewModel.PasswordStrength.VERY_STRONG, authViewModel.checkPasswordStrength("SuperStrong#Password1"))
        assertEquals(AuthViewModel.PasswordStrength.VERY_STRONG, authViewModel.checkPasswordStrength("Very$Strong@Pass123"))
    }

    // ========================
    // Utility Function Tests
    // ========================

    @Test
    fun `clearErrors clears validation error`() {
        // Arrange
        val errorObserver = mock<Observer<String?>>()
        authViewModel.validationError.observeForever(errorObserver)

        // Trigger an error first
        authViewModel.login("invalid-email", testPassword)

        // Act
        authViewModel.clearErrors()

        // Assert
        verify(errorObserver, atLeast(1)).onChanged(null)
    }

    @Test
    fun `logout clears user session`() = runTest {
        // Arrange
        whenever(mockUserRepository.clearUserSession()).thenReturn(Unit)

        // Act
        authViewModel.logout()

        // Assert
        verify(mockUserRepository).clearUserSession()
    }

    // ========================
    // Loading State Tests
    // ========================

    @Test
    fun `login sets loading state correctly during operation`() = runTest {
        // Arrange
        val hashedPassword = BCrypt.hashpw(testPassword, BCrypt.gensalt(12))
        val testUser = createTestUser(passwordHash = hashedPassword)

        whenever(mockUserRepository.getUserByEmail(testEmail)).thenReturn(testUser)
        whenever(mockUserRepository.updateUser(any())).thenReturn(Result.Success(Unit))

        val loadingObserver = mock<Observer<Boolean>>()
        authViewModel.isLoading.observeForever(loadingObserver)

        // Act
        authViewModel.login(testEmail, testPassword)

        // Assert
        verify(loadingObserver).onChanged(true)
        verify(loadingObserver).onChanged(false)
    }

    @Test
    fun `signup sets loading state correctly during operation`() = runTest {
        // Arrange
        whenever(mockUserRepository.getUserByEmail(testEmail)).thenReturn(null)
        whenever(mockUserRepository.createUser(any())).thenReturn(testUserId)
        whenever(mockUserRepository.saveUserSession(testUserId)).thenReturn(Unit)

        val loadingObserver = mock<Observer<Boolean>>()
        authViewModel.isLoading.observeForever(loadingObserver)

        // Act
        authViewModel.signup(testName, testEmail, testPassword, testPassword, true)

        // Assert
        verify(loadingObserver).onChanged(true)
        verify(loadingObserver).onChanged(false)
    }

    @Test
    fun `loading state is set to false even when exception occurs`() = runTest {
        // Arrange
        val exception = RuntimeException("Database error")
        whenever(mockUserRepository.getUserByEmail(testEmail)).thenThrow(exception)

        val loadingObserver = mock<Observer<Boolean>>()
        authViewModel.isLoading.observeForever(loadingObserver)

        // Act
        authViewModel.login(testEmail, testPassword)

        // Assert
        verify(loadingObserver).onChanged(true)
        verify(loadingObserver).onChanged(false)
    }

    // ========================
    // Edge Cases and Error Handling
    // ========================

    @Test
    fun `multiple consecutive login attempts are handled correctly`() = runTest {
        // Arrange
        val hashedPassword = BCrypt.hashpw(testPassword, BCrypt.gensalt(12))
        val testUser = createTestUser(passwordHash = hashedPassword)

        whenever(mockUserRepository.getUserByEmail(testEmail)).thenReturn(testUser)
        whenever(mockUserRepository.updateUser(any())).thenReturn(Result.Success(Unit))

        val loginObserver = mock<Observer<Result<User>>>()
        authViewModel.loginState.observeForever(loginObserver)

        // Act - Multiple rapid login attempts
        authViewModel.login(testEmail, testPassword)
        authViewModel.login(testEmail, testPassword)
        authViewModel.login(testEmail, testPassword)

        // Assert - Should handle all attempts without crashing
        verify(mockUserRepository, atLeast(3)).getUserByEmail(testEmail)
        verify(loginObserver, atLeast(3)).onChanged(any())
    }

    @Test
    fun `login with empty credentials shows validation errors`() = runTest {
        // Arrange
        val errorObserver = mock<Observer<String?>>()
        authViewModel.validationError.observeForever(errorObserver)

        // Act
        authViewModel.login("", "")

        // Assert
        verify(errorObserver).onChanged(argThat { error ->
            error?.isNotEmpty() == true
        })
        verify(mockUserRepository, never()).getUserByEmail(any())
    }

    @Test
    fun `signup with empty credentials shows validation errors`() = runTest {
        // Arrange
        val errorObserver = mock<Observer<String?>>()
        authViewModel.validationError.observeForever(errorObserver)

        // Act
        authViewModel.signup("", "", "", "", false)

        // Assert
        verify(errorObserver).onChanged(argThat { error ->
            error?.isNotEmpty() == true
        })
        verify(mockUserRepository, never()).getUserByEmail(any())
    }

    // ========================
    // Helper Methods
    // ========================

    private fun createTestUser(
        id: Long = testUserId,
        email: String = testEmail,
        username: String = testEmail.substringBefore("@"),
        passwordHash: String = BCrypt.hashpw(testPassword, BCrypt.gensalt(12)),
        fullName: String = testName,
        failedLoginAttempts: Int = 0,
        isLocked: Boolean = false
    ): User {
        return User(
            id = id,
            email = email,
            username = username,
            passwordHash = passwordHash,
            fullName = fullName,
            profileImageUrl = null,
            createdAt = Date(),
            updatedAt = Date(),
            lastLogin = null,
            isEmailVerified = false,
            isActive = true,
            failedLoginAttempts = failedLoginAttempts,
            isLocked = isLocked,
            // Quiz answers can be null initially
            mainGoal = null,
            exerciseFrequency = null,
            workoutPreference = null,
            fitnessLevel = null,
            timeAvailable = null,
            hasCompletedOnboarding = false
        )
    }
}