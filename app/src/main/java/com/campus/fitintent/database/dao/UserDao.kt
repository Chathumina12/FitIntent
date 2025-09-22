package com.campus.fitintent.database.dao

import androidx.room.*
import com.campus.fitintent.models.User
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for User operations
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserByIdFlow(userId: Long): Flow<User?>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND isActive = 1 LIMIT 1")
    suspend fun getActiveUserByEmail(email: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE users SET isOnboardingComplete = :isComplete WHERE id = :userId")
    suspend fun updateOnboardingStatus(userId: Long, isComplete: Boolean)

    @Query("UPDATE users SET profileImagePath = :imagePath WHERE id = :userId")
    suspend fun updateProfileImage(userId: Long, imagePath: String?)

    @Query("UPDATE users SET weight = :weight WHERE id = :userId")
    suspend fun updateWeight(userId: Long, weight: Float)

    @Query("UPDATE users SET isActive = 0 WHERE id = :userId")
    suspend fun deactivateUser(userId: Long)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun checkEmailExists(email: String): Int

    @Query("SELECT * FROM users WHERE isActive = 1")
    fun getAllActiveUsers(): Flow<List<User>>

    @Query("UPDATE users SET passwordHash = :newPasswordHash WHERE id = :userId")
    suspend fun updatePassword(userId: Long, newPasswordHash: String)
}