package com.campus.fitintent.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.campus.fitintent.database.Converters
import com.campus.fitintent.models.ActivityLevel
import com.campus.fitintent.models.FitnessGoal
import com.campus.fitintent.models.Gender
import com.campus.fitintent.models.User
import java.util.Date
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class UserDao_Impl(
  __db: RoomDatabase,
) : UserDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfUser: EntityInsertAdapter<User>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfUser: EntityDeleteOrUpdateAdapter<User>

  private val __updateAdapterOfUser: EntityDeleteOrUpdateAdapter<User>
  init {
    this.__db = __db
    this.__insertAdapterOfUser = object : EntityInsertAdapter<User>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `users` (`id`,`email`,`username`,`passwordHash`,`fullName`,`age`,`gender`,`height`,`weight`,`activityLevel`,`primaryGoal`,`targetWeight`,`weeklyWorkoutGoal`,`createdAt`,`updatedAt`,`isOnboardingComplete`,`isActive`,`profileImagePath`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: User) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.email)
        statement.bindText(3, entity.username)
        statement.bindText(4, entity.passwordHash)
        statement.bindText(5, entity.fullName)
        val _tmpAge: Int? = entity.age
        if (_tmpAge == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpAge.toLong())
        }
        val _tmpGender: Gender? = entity.gender
        val _tmp: String? = __converters.fromGender(_tmpGender)
        if (_tmp == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp)
        }
        val _tmpHeight: Float? = entity.height
        if (_tmpHeight == null) {
          statement.bindNull(8)
        } else {
          statement.bindDouble(8, _tmpHeight.toDouble())
        }
        val _tmpWeight: Float? = entity.weight
        if (_tmpWeight == null) {
          statement.bindNull(9)
        } else {
          statement.bindDouble(9, _tmpWeight.toDouble())
        }
        val _tmpActivityLevel: ActivityLevel? = entity.activityLevel
        val _tmp_1: String? = __converters.fromActivityLevel(_tmpActivityLevel)
        if (_tmp_1 == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmp_1)
        }
        val _tmpPrimaryGoal: FitnessGoal? = entity.primaryGoal
        val _tmp_2: String? = __converters.fromFitnessGoal(_tmpPrimaryGoal)
        if (_tmp_2 == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmp_2)
        }
        val _tmpTargetWeight: Float? = entity.targetWeight
        if (_tmpTargetWeight == null) {
          statement.bindNull(12)
        } else {
          statement.bindDouble(12, _tmpTargetWeight.toDouble())
        }
        statement.bindLong(13, entity.weeklyWorkoutGoal.toLong())
        val _tmp_3: Long? = __converters.dateToTimestamp(entity.createdAt)
        if (_tmp_3 == null) {
          statement.bindNull(14)
        } else {
          statement.bindLong(14, _tmp_3)
        }
        val _tmp_4: Long? = __converters.dateToTimestamp(entity.updatedAt)
        if (_tmp_4 == null) {
          statement.bindNull(15)
        } else {
          statement.bindLong(15, _tmp_4)
        }
        val _tmp_5: Int = if (entity.isOnboardingComplete) 1 else 0
        statement.bindLong(16, _tmp_5.toLong())
        val _tmp_6: Int = if (entity.isActive) 1 else 0
        statement.bindLong(17, _tmp_6.toLong())
        val _tmpProfileImagePath: String? = entity.profileImagePath
        if (_tmpProfileImagePath == null) {
          statement.bindNull(18)
        } else {
          statement.bindText(18, _tmpProfileImagePath)
        }
      }
    }
    this.__deleteAdapterOfUser = object : EntityDeleteOrUpdateAdapter<User>() {
      protected override fun createQuery(): String = "DELETE FROM `users` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: User) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfUser = object : EntityDeleteOrUpdateAdapter<User>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `users` SET `id` = ?,`email` = ?,`username` = ?,`passwordHash` = ?,`fullName` = ?,`age` = ?,`gender` = ?,`height` = ?,`weight` = ?,`activityLevel` = ?,`primaryGoal` = ?,`targetWeight` = ?,`weeklyWorkoutGoal` = ?,`createdAt` = ?,`updatedAt` = ?,`isOnboardingComplete` = ?,`isActive` = ?,`profileImagePath` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: User) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.email)
        statement.bindText(3, entity.username)
        statement.bindText(4, entity.passwordHash)
        statement.bindText(5, entity.fullName)
        val _tmpAge: Int? = entity.age
        if (_tmpAge == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpAge.toLong())
        }
        val _tmpGender: Gender? = entity.gender
        val _tmp: String? = __converters.fromGender(_tmpGender)
        if (_tmp == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmp)
        }
        val _tmpHeight: Float? = entity.height
        if (_tmpHeight == null) {
          statement.bindNull(8)
        } else {
          statement.bindDouble(8, _tmpHeight.toDouble())
        }
        val _tmpWeight: Float? = entity.weight
        if (_tmpWeight == null) {
          statement.bindNull(9)
        } else {
          statement.bindDouble(9, _tmpWeight.toDouble())
        }
        val _tmpActivityLevel: ActivityLevel? = entity.activityLevel
        val _tmp_1: String? = __converters.fromActivityLevel(_tmpActivityLevel)
        if (_tmp_1 == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmp_1)
        }
        val _tmpPrimaryGoal: FitnessGoal? = entity.primaryGoal
        val _tmp_2: String? = __converters.fromFitnessGoal(_tmpPrimaryGoal)
        if (_tmp_2 == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmp_2)
        }
        val _tmpTargetWeight: Float? = entity.targetWeight
        if (_tmpTargetWeight == null) {
          statement.bindNull(12)
        } else {
          statement.bindDouble(12, _tmpTargetWeight.toDouble())
        }
        statement.bindLong(13, entity.weeklyWorkoutGoal.toLong())
        val _tmp_3: Long? = __converters.dateToTimestamp(entity.createdAt)
        if (_tmp_3 == null) {
          statement.bindNull(14)
        } else {
          statement.bindLong(14, _tmp_3)
        }
        val _tmp_4: Long? = __converters.dateToTimestamp(entity.updatedAt)
        if (_tmp_4 == null) {
          statement.bindNull(15)
        } else {
          statement.bindLong(15, _tmp_4)
        }
        val _tmp_5: Int = if (entity.isOnboardingComplete) 1 else 0
        statement.bindLong(16, _tmp_5.toLong())
        val _tmp_6: Int = if (entity.isActive) 1 else 0
        statement.bindLong(17, _tmp_6.toLong())
        val _tmpProfileImagePath: String? = entity.profileImagePath
        if (_tmpProfileImagePath == null) {
          statement.bindNull(18)
        } else {
          statement.bindText(18, _tmpProfileImagePath)
        }
        statement.bindLong(19, entity.id)
      }
    }
  }

  public override suspend fun insertUser(user: User): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfUser.insertAndReturnId(_connection, user)
    _result
  }

  public override suspend fun deleteUser(user: User): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfUser.handle(_connection, user)
  }

  public override suspend fun updateUser(user: User): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfUser.handle(_connection, user)
  }

  public override suspend fun getUserById(userId: Long): User? {
    val _sql: String = "SELECT * FROM users WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfUsername: Int = getColumnIndexOrThrow(_stmt, "username")
        val _columnIndexOfPasswordHash: Int = getColumnIndexOrThrow(_stmt, "passwordHash")
        val _columnIndexOfFullName: Int = getColumnIndexOrThrow(_stmt, "fullName")
        val _columnIndexOfAge: Int = getColumnIndexOrThrow(_stmt, "age")
        val _columnIndexOfGender: Int = getColumnIndexOrThrow(_stmt, "gender")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfActivityLevel: Int = getColumnIndexOrThrow(_stmt, "activityLevel")
        val _columnIndexOfPrimaryGoal: Int = getColumnIndexOrThrow(_stmt, "primaryGoal")
        val _columnIndexOfTargetWeight: Int = getColumnIndexOrThrow(_stmt, "targetWeight")
        val _columnIndexOfWeeklyWorkoutGoal: Int = getColumnIndexOrThrow(_stmt, "weeklyWorkoutGoal")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _columnIndexOfIsOnboardingComplete: Int = getColumnIndexOrThrow(_stmt, "isOnboardingComplete")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfProfileImagePath: Int = getColumnIndexOrThrow(_stmt, "profileImagePath")
        val _result: User?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpUsername: String
          _tmpUsername = _stmt.getText(_columnIndexOfUsername)
          val _tmpPasswordHash: String
          _tmpPasswordHash = _stmt.getText(_columnIndexOfPasswordHash)
          val _tmpFullName: String
          _tmpFullName = _stmt.getText(_columnIndexOfFullName)
          val _tmpAge: Int?
          if (_stmt.isNull(_columnIndexOfAge)) {
            _tmpAge = null
          } else {
            _tmpAge = _stmt.getLong(_columnIndexOfAge).toInt()
          }
          val _tmpGender: Gender?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfGender)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfGender)
          }
          _tmpGender = __converters.toGender(_tmp)
          val _tmpHeight: Float?
          if (_stmt.isNull(_columnIndexOfHeight)) {
            _tmpHeight = null
          } else {
            _tmpHeight = _stmt.getDouble(_columnIndexOfHeight).toFloat()
          }
          val _tmpWeight: Float?
          if (_stmt.isNull(_columnIndexOfWeight)) {
            _tmpWeight = null
          } else {
            _tmpWeight = _stmt.getDouble(_columnIndexOfWeight).toFloat()
          }
          val _tmpActivityLevel: ActivityLevel?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfActivityLevel)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfActivityLevel)
          }
          _tmpActivityLevel = __converters.toActivityLevel(_tmp_1)
          val _tmpPrimaryGoal: FitnessGoal?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPrimaryGoal)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPrimaryGoal)
          }
          _tmpPrimaryGoal = __converters.toFitnessGoal(_tmp_2)
          val _tmpTargetWeight: Float?
          if (_stmt.isNull(_columnIndexOfTargetWeight)) {
            _tmpTargetWeight = null
          } else {
            _tmpTargetWeight = _stmt.getDouble(_columnIndexOfTargetWeight).toFloat()
          }
          val _tmpWeeklyWorkoutGoal: Int
          _tmpWeeklyWorkoutGoal = _stmt.getLong(_columnIndexOfWeeklyWorkoutGoal).toInt()
          val _tmpCreatedAt: Date
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_4: Date? = __converters.fromTimestamp(_tmp_3)
          if (_tmp_4 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_4
          }
          val _tmpUpdatedAt: Date
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_6: Date? = __converters.fromTimestamp(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_6
          }
          val _tmpIsOnboardingComplete: Boolean
          val _tmp_7: Int
          _tmp_7 = _stmt.getLong(_columnIndexOfIsOnboardingComplete).toInt()
          _tmpIsOnboardingComplete = _tmp_7 != 0
          val _tmpIsActive: Boolean
          val _tmp_8: Int
          _tmp_8 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_8 != 0
          val _tmpProfileImagePath: String?
          if (_stmt.isNull(_columnIndexOfProfileImagePath)) {
            _tmpProfileImagePath = null
          } else {
            _tmpProfileImagePath = _stmt.getText(_columnIndexOfProfileImagePath)
          }
          _result = User(_tmpId,_tmpEmail,_tmpUsername,_tmpPasswordHash,_tmpFullName,_tmpAge,_tmpGender,_tmpHeight,_tmpWeight,_tmpActivityLevel,_tmpPrimaryGoal,_tmpTargetWeight,_tmpWeeklyWorkoutGoal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsOnboardingComplete,_tmpIsActive,_tmpProfileImagePath)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUserByIdFlow(userId: Long): Flow<User?> {
    val _sql: String = "SELECT * FROM users WHERE id = ?"
    return createFlow(__db, false, arrayOf("users")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfUsername: Int = getColumnIndexOrThrow(_stmt, "username")
        val _columnIndexOfPasswordHash: Int = getColumnIndexOrThrow(_stmt, "passwordHash")
        val _columnIndexOfFullName: Int = getColumnIndexOrThrow(_stmt, "fullName")
        val _columnIndexOfAge: Int = getColumnIndexOrThrow(_stmt, "age")
        val _columnIndexOfGender: Int = getColumnIndexOrThrow(_stmt, "gender")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfActivityLevel: Int = getColumnIndexOrThrow(_stmt, "activityLevel")
        val _columnIndexOfPrimaryGoal: Int = getColumnIndexOrThrow(_stmt, "primaryGoal")
        val _columnIndexOfTargetWeight: Int = getColumnIndexOrThrow(_stmt, "targetWeight")
        val _columnIndexOfWeeklyWorkoutGoal: Int = getColumnIndexOrThrow(_stmt, "weeklyWorkoutGoal")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _columnIndexOfIsOnboardingComplete: Int = getColumnIndexOrThrow(_stmt, "isOnboardingComplete")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfProfileImagePath: Int = getColumnIndexOrThrow(_stmt, "profileImagePath")
        val _result: User?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpUsername: String
          _tmpUsername = _stmt.getText(_columnIndexOfUsername)
          val _tmpPasswordHash: String
          _tmpPasswordHash = _stmt.getText(_columnIndexOfPasswordHash)
          val _tmpFullName: String
          _tmpFullName = _stmt.getText(_columnIndexOfFullName)
          val _tmpAge: Int?
          if (_stmt.isNull(_columnIndexOfAge)) {
            _tmpAge = null
          } else {
            _tmpAge = _stmt.getLong(_columnIndexOfAge).toInt()
          }
          val _tmpGender: Gender?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfGender)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfGender)
          }
          _tmpGender = __converters.toGender(_tmp)
          val _tmpHeight: Float?
          if (_stmt.isNull(_columnIndexOfHeight)) {
            _tmpHeight = null
          } else {
            _tmpHeight = _stmt.getDouble(_columnIndexOfHeight).toFloat()
          }
          val _tmpWeight: Float?
          if (_stmt.isNull(_columnIndexOfWeight)) {
            _tmpWeight = null
          } else {
            _tmpWeight = _stmt.getDouble(_columnIndexOfWeight).toFloat()
          }
          val _tmpActivityLevel: ActivityLevel?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfActivityLevel)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfActivityLevel)
          }
          _tmpActivityLevel = __converters.toActivityLevel(_tmp_1)
          val _tmpPrimaryGoal: FitnessGoal?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPrimaryGoal)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPrimaryGoal)
          }
          _tmpPrimaryGoal = __converters.toFitnessGoal(_tmp_2)
          val _tmpTargetWeight: Float?
          if (_stmt.isNull(_columnIndexOfTargetWeight)) {
            _tmpTargetWeight = null
          } else {
            _tmpTargetWeight = _stmt.getDouble(_columnIndexOfTargetWeight).toFloat()
          }
          val _tmpWeeklyWorkoutGoal: Int
          _tmpWeeklyWorkoutGoal = _stmt.getLong(_columnIndexOfWeeklyWorkoutGoal).toInt()
          val _tmpCreatedAt: Date
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_4: Date? = __converters.fromTimestamp(_tmp_3)
          if (_tmp_4 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_4
          }
          val _tmpUpdatedAt: Date
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_6: Date? = __converters.fromTimestamp(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_6
          }
          val _tmpIsOnboardingComplete: Boolean
          val _tmp_7: Int
          _tmp_7 = _stmt.getLong(_columnIndexOfIsOnboardingComplete).toInt()
          _tmpIsOnboardingComplete = _tmp_7 != 0
          val _tmpIsActive: Boolean
          val _tmp_8: Int
          _tmp_8 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_8 != 0
          val _tmpProfileImagePath: String?
          if (_stmt.isNull(_columnIndexOfProfileImagePath)) {
            _tmpProfileImagePath = null
          } else {
            _tmpProfileImagePath = _stmt.getText(_columnIndexOfProfileImagePath)
          }
          _result = User(_tmpId,_tmpEmail,_tmpUsername,_tmpPasswordHash,_tmpFullName,_tmpAge,_tmpGender,_tmpHeight,_tmpWeight,_tmpActivityLevel,_tmpPrimaryGoal,_tmpTargetWeight,_tmpWeeklyWorkoutGoal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsOnboardingComplete,_tmpIsActive,_tmpProfileImagePath)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getUserByEmail(email: String): User? {
    val _sql: String = "SELECT * FROM users WHERE email = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, email)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfUsername: Int = getColumnIndexOrThrow(_stmt, "username")
        val _columnIndexOfPasswordHash: Int = getColumnIndexOrThrow(_stmt, "passwordHash")
        val _columnIndexOfFullName: Int = getColumnIndexOrThrow(_stmt, "fullName")
        val _columnIndexOfAge: Int = getColumnIndexOrThrow(_stmt, "age")
        val _columnIndexOfGender: Int = getColumnIndexOrThrow(_stmt, "gender")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfActivityLevel: Int = getColumnIndexOrThrow(_stmt, "activityLevel")
        val _columnIndexOfPrimaryGoal: Int = getColumnIndexOrThrow(_stmt, "primaryGoal")
        val _columnIndexOfTargetWeight: Int = getColumnIndexOrThrow(_stmt, "targetWeight")
        val _columnIndexOfWeeklyWorkoutGoal: Int = getColumnIndexOrThrow(_stmt, "weeklyWorkoutGoal")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _columnIndexOfIsOnboardingComplete: Int = getColumnIndexOrThrow(_stmt, "isOnboardingComplete")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfProfileImagePath: Int = getColumnIndexOrThrow(_stmt, "profileImagePath")
        val _result: User?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpUsername: String
          _tmpUsername = _stmt.getText(_columnIndexOfUsername)
          val _tmpPasswordHash: String
          _tmpPasswordHash = _stmt.getText(_columnIndexOfPasswordHash)
          val _tmpFullName: String
          _tmpFullName = _stmt.getText(_columnIndexOfFullName)
          val _tmpAge: Int?
          if (_stmt.isNull(_columnIndexOfAge)) {
            _tmpAge = null
          } else {
            _tmpAge = _stmt.getLong(_columnIndexOfAge).toInt()
          }
          val _tmpGender: Gender?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfGender)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfGender)
          }
          _tmpGender = __converters.toGender(_tmp)
          val _tmpHeight: Float?
          if (_stmt.isNull(_columnIndexOfHeight)) {
            _tmpHeight = null
          } else {
            _tmpHeight = _stmt.getDouble(_columnIndexOfHeight).toFloat()
          }
          val _tmpWeight: Float?
          if (_stmt.isNull(_columnIndexOfWeight)) {
            _tmpWeight = null
          } else {
            _tmpWeight = _stmt.getDouble(_columnIndexOfWeight).toFloat()
          }
          val _tmpActivityLevel: ActivityLevel?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfActivityLevel)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfActivityLevel)
          }
          _tmpActivityLevel = __converters.toActivityLevel(_tmp_1)
          val _tmpPrimaryGoal: FitnessGoal?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPrimaryGoal)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPrimaryGoal)
          }
          _tmpPrimaryGoal = __converters.toFitnessGoal(_tmp_2)
          val _tmpTargetWeight: Float?
          if (_stmt.isNull(_columnIndexOfTargetWeight)) {
            _tmpTargetWeight = null
          } else {
            _tmpTargetWeight = _stmt.getDouble(_columnIndexOfTargetWeight).toFloat()
          }
          val _tmpWeeklyWorkoutGoal: Int
          _tmpWeeklyWorkoutGoal = _stmt.getLong(_columnIndexOfWeeklyWorkoutGoal).toInt()
          val _tmpCreatedAt: Date
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_4: Date? = __converters.fromTimestamp(_tmp_3)
          if (_tmp_4 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_4
          }
          val _tmpUpdatedAt: Date
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_6: Date? = __converters.fromTimestamp(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_6
          }
          val _tmpIsOnboardingComplete: Boolean
          val _tmp_7: Int
          _tmp_7 = _stmt.getLong(_columnIndexOfIsOnboardingComplete).toInt()
          _tmpIsOnboardingComplete = _tmp_7 != 0
          val _tmpIsActive: Boolean
          val _tmp_8: Int
          _tmp_8 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_8 != 0
          val _tmpProfileImagePath: String?
          if (_stmt.isNull(_columnIndexOfProfileImagePath)) {
            _tmpProfileImagePath = null
          } else {
            _tmpProfileImagePath = _stmt.getText(_columnIndexOfProfileImagePath)
          }
          _result = User(_tmpId,_tmpEmail,_tmpUsername,_tmpPasswordHash,_tmpFullName,_tmpAge,_tmpGender,_tmpHeight,_tmpWeight,_tmpActivityLevel,_tmpPrimaryGoal,_tmpTargetWeight,_tmpWeeklyWorkoutGoal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsOnboardingComplete,_tmpIsActive,_tmpProfileImagePath)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getActiveUserByEmail(email: String): User? {
    val _sql: String = "SELECT * FROM users WHERE email = ? AND isActive = 1 LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, email)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfUsername: Int = getColumnIndexOrThrow(_stmt, "username")
        val _columnIndexOfPasswordHash: Int = getColumnIndexOrThrow(_stmt, "passwordHash")
        val _columnIndexOfFullName: Int = getColumnIndexOrThrow(_stmt, "fullName")
        val _columnIndexOfAge: Int = getColumnIndexOrThrow(_stmt, "age")
        val _columnIndexOfGender: Int = getColumnIndexOrThrow(_stmt, "gender")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfActivityLevel: Int = getColumnIndexOrThrow(_stmt, "activityLevel")
        val _columnIndexOfPrimaryGoal: Int = getColumnIndexOrThrow(_stmt, "primaryGoal")
        val _columnIndexOfTargetWeight: Int = getColumnIndexOrThrow(_stmt, "targetWeight")
        val _columnIndexOfWeeklyWorkoutGoal: Int = getColumnIndexOrThrow(_stmt, "weeklyWorkoutGoal")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _columnIndexOfIsOnboardingComplete: Int = getColumnIndexOrThrow(_stmt, "isOnboardingComplete")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfProfileImagePath: Int = getColumnIndexOrThrow(_stmt, "profileImagePath")
        val _result: User?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpUsername: String
          _tmpUsername = _stmt.getText(_columnIndexOfUsername)
          val _tmpPasswordHash: String
          _tmpPasswordHash = _stmt.getText(_columnIndexOfPasswordHash)
          val _tmpFullName: String
          _tmpFullName = _stmt.getText(_columnIndexOfFullName)
          val _tmpAge: Int?
          if (_stmt.isNull(_columnIndexOfAge)) {
            _tmpAge = null
          } else {
            _tmpAge = _stmt.getLong(_columnIndexOfAge).toInt()
          }
          val _tmpGender: Gender?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfGender)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfGender)
          }
          _tmpGender = __converters.toGender(_tmp)
          val _tmpHeight: Float?
          if (_stmt.isNull(_columnIndexOfHeight)) {
            _tmpHeight = null
          } else {
            _tmpHeight = _stmt.getDouble(_columnIndexOfHeight).toFloat()
          }
          val _tmpWeight: Float?
          if (_stmt.isNull(_columnIndexOfWeight)) {
            _tmpWeight = null
          } else {
            _tmpWeight = _stmt.getDouble(_columnIndexOfWeight).toFloat()
          }
          val _tmpActivityLevel: ActivityLevel?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfActivityLevel)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfActivityLevel)
          }
          _tmpActivityLevel = __converters.toActivityLevel(_tmp_1)
          val _tmpPrimaryGoal: FitnessGoal?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPrimaryGoal)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPrimaryGoal)
          }
          _tmpPrimaryGoal = __converters.toFitnessGoal(_tmp_2)
          val _tmpTargetWeight: Float?
          if (_stmt.isNull(_columnIndexOfTargetWeight)) {
            _tmpTargetWeight = null
          } else {
            _tmpTargetWeight = _stmt.getDouble(_columnIndexOfTargetWeight).toFloat()
          }
          val _tmpWeeklyWorkoutGoal: Int
          _tmpWeeklyWorkoutGoal = _stmt.getLong(_columnIndexOfWeeklyWorkoutGoal).toInt()
          val _tmpCreatedAt: Date
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_4: Date? = __converters.fromTimestamp(_tmp_3)
          if (_tmp_4 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_4
          }
          val _tmpUpdatedAt: Date
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_6: Date? = __converters.fromTimestamp(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_6
          }
          val _tmpIsOnboardingComplete: Boolean
          val _tmp_7: Int
          _tmp_7 = _stmt.getLong(_columnIndexOfIsOnboardingComplete).toInt()
          _tmpIsOnboardingComplete = _tmp_7 != 0
          val _tmpIsActive: Boolean
          val _tmp_8: Int
          _tmp_8 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_8 != 0
          val _tmpProfileImagePath: String?
          if (_stmt.isNull(_columnIndexOfProfileImagePath)) {
            _tmpProfileImagePath = null
          } else {
            _tmpProfileImagePath = _stmt.getText(_columnIndexOfProfileImagePath)
          }
          _result = User(_tmpId,_tmpEmail,_tmpUsername,_tmpPasswordHash,_tmpFullName,_tmpAge,_tmpGender,_tmpHeight,_tmpWeight,_tmpActivityLevel,_tmpPrimaryGoal,_tmpTargetWeight,_tmpWeeklyWorkoutGoal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsOnboardingComplete,_tmpIsActive,_tmpProfileImagePath)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun checkEmailExists(email: String): Int {
    val _sql: String = "SELECT COUNT(*) FROM users WHERE email = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, email)
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllActiveUsers(): Flow<List<User>> {
    val _sql: String = "SELECT * FROM users WHERE isActive = 1"
    return createFlow(__db, false, arrayOf("users")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfUsername: Int = getColumnIndexOrThrow(_stmt, "username")
        val _columnIndexOfPasswordHash: Int = getColumnIndexOrThrow(_stmt, "passwordHash")
        val _columnIndexOfFullName: Int = getColumnIndexOrThrow(_stmt, "fullName")
        val _columnIndexOfAge: Int = getColumnIndexOrThrow(_stmt, "age")
        val _columnIndexOfGender: Int = getColumnIndexOrThrow(_stmt, "gender")
        val _columnIndexOfHeight: Int = getColumnIndexOrThrow(_stmt, "height")
        val _columnIndexOfWeight: Int = getColumnIndexOrThrow(_stmt, "weight")
        val _columnIndexOfActivityLevel: Int = getColumnIndexOrThrow(_stmt, "activityLevel")
        val _columnIndexOfPrimaryGoal: Int = getColumnIndexOrThrow(_stmt, "primaryGoal")
        val _columnIndexOfTargetWeight: Int = getColumnIndexOrThrow(_stmt, "targetWeight")
        val _columnIndexOfWeeklyWorkoutGoal: Int = getColumnIndexOrThrow(_stmt, "weeklyWorkoutGoal")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _columnIndexOfIsOnboardingComplete: Int = getColumnIndexOrThrow(_stmt, "isOnboardingComplete")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfProfileImagePath: Int = getColumnIndexOrThrow(_stmt, "profileImagePath")
        val _result: MutableList<User> = mutableListOf()
        while (_stmt.step()) {
          val _item: User
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpEmail: String
          _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          val _tmpUsername: String
          _tmpUsername = _stmt.getText(_columnIndexOfUsername)
          val _tmpPasswordHash: String
          _tmpPasswordHash = _stmt.getText(_columnIndexOfPasswordHash)
          val _tmpFullName: String
          _tmpFullName = _stmt.getText(_columnIndexOfFullName)
          val _tmpAge: Int?
          if (_stmt.isNull(_columnIndexOfAge)) {
            _tmpAge = null
          } else {
            _tmpAge = _stmt.getLong(_columnIndexOfAge).toInt()
          }
          val _tmpGender: Gender?
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfGender)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfGender)
          }
          _tmpGender = __converters.toGender(_tmp)
          val _tmpHeight: Float?
          if (_stmt.isNull(_columnIndexOfHeight)) {
            _tmpHeight = null
          } else {
            _tmpHeight = _stmt.getDouble(_columnIndexOfHeight).toFloat()
          }
          val _tmpWeight: Float?
          if (_stmt.isNull(_columnIndexOfWeight)) {
            _tmpWeight = null
          } else {
            _tmpWeight = _stmt.getDouble(_columnIndexOfWeight).toFloat()
          }
          val _tmpActivityLevel: ActivityLevel?
          val _tmp_1: String?
          if (_stmt.isNull(_columnIndexOfActivityLevel)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getText(_columnIndexOfActivityLevel)
          }
          _tmpActivityLevel = __converters.toActivityLevel(_tmp_1)
          val _tmpPrimaryGoal: FitnessGoal?
          val _tmp_2: String?
          if (_stmt.isNull(_columnIndexOfPrimaryGoal)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getText(_columnIndexOfPrimaryGoal)
          }
          _tmpPrimaryGoal = __converters.toFitnessGoal(_tmp_2)
          val _tmpTargetWeight: Float?
          if (_stmt.isNull(_columnIndexOfTargetWeight)) {
            _tmpTargetWeight = null
          } else {
            _tmpTargetWeight = _stmt.getDouble(_columnIndexOfTargetWeight).toFloat()
          }
          val _tmpWeeklyWorkoutGoal: Int
          _tmpWeeklyWorkoutGoal = _stmt.getLong(_columnIndexOfWeeklyWorkoutGoal).toInt()
          val _tmpCreatedAt: Date
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_4: Date? = __converters.fromTimestamp(_tmp_3)
          if (_tmp_4 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_4
          }
          val _tmpUpdatedAt: Date
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_6: Date? = __converters.fromTimestamp(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_6
          }
          val _tmpIsOnboardingComplete: Boolean
          val _tmp_7: Int
          _tmp_7 = _stmt.getLong(_columnIndexOfIsOnboardingComplete).toInt()
          _tmpIsOnboardingComplete = _tmp_7 != 0
          val _tmpIsActive: Boolean
          val _tmp_8: Int
          _tmp_8 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_8 != 0
          val _tmpProfileImagePath: String?
          if (_stmt.isNull(_columnIndexOfProfileImagePath)) {
            _tmpProfileImagePath = null
          } else {
            _tmpProfileImagePath = _stmt.getText(_columnIndexOfProfileImagePath)
          }
          _item = User(_tmpId,_tmpEmail,_tmpUsername,_tmpPasswordHash,_tmpFullName,_tmpAge,_tmpGender,_tmpHeight,_tmpWeight,_tmpActivityLevel,_tmpPrimaryGoal,_tmpTargetWeight,_tmpWeeklyWorkoutGoal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsOnboardingComplete,_tmpIsActive,_tmpProfileImagePath)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateOnboardingStatus(userId: Long, isComplete: Boolean) {
    val _sql: String = "UPDATE users SET isOnboardingComplete = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (isComplete) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, userId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateProfileImage(userId: Long, imagePath: String?) {
    val _sql: String = "UPDATE users SET profileImagePath = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        if (imagePath == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, imagePath)
        }
        _argIndex = 2
        _stmt.bindLong(_argIndex, userId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateWeight(userId: Long, weight: Float) {
    val _sql: String = "UPDATE users SET weight = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindDouble(_argIndex, weight.toDouble())
        _argIndex = 2
        _stmt.bindLong(_argIndex, userId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deactivateUser(userId: Long) {
    val _sql: String = "UPDATE users SET isActive = 0 WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updatePassword(userId: Long, newPasswordHash: String) {
    val _sql: String = "UPDATE users SET passwordHash = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, newPasswordHash)
        _argIndex = 2
        _stmt.bindLong(_argIndex, userId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
