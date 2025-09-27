package com.campus.fitintent.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.campus.fitintent.database.Converters
import com.campus.fitintent.models.DailyGoal
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
public class DailyGoalDao_Impl(
  __db: RoomDatabase,
) : DailyGoalDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfDailyGoal: EntityInsertAdapter<DailyGoal>

  private val __converters: Converters = Converters()

  private val __updateAdapterOfDailyGoal: EntityDeleteOrUpdateAdapter<DailyGoal>
  init {
    this.__db = __db
    this.__insertAdapterOfDailyGoal = object : EntityInsertAdapter<DailyGoal>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `daily_goals` (`id`,`userId`,`date`,`calorieGoal`,`stepGoal`,`waterGoal`,`sleepGoal`,`workoutMinutesGoal`,`caloriesBurned`,`stepsTaken`,`waterIntake`,`sleepHours`,`workoutMinutes`,`isCalorieGoalMet`,`isStepGoalMet`,`isWaterGoalMet`,`isSleepGoalMet`,`isWorkoutGoalMet`,`overallCompletionPercentage`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: DailyGoal) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        val _tmp: Long? = __converters.dateToTimestamp(entity.date)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmp)
        }
        val _tmpCalorieGoal: Int? = entity.calorieGoal
        if (_tmpCalorieGoal == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpCalorieGoal.toLong())
        }
        val _tmpStepGoal: Int? = entity.stepGoal
        if (_tmpStepGoal == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpStepGoal.toLong())
        }
        val _tmpWaterGoal: Int? = entity.waterGoal
        if (_tmpWaterGoal == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpWaterGoal.toLong())
        }
        val _tmpSleepGoal: Float? = entity.sleepGoal
        if (_tmpSleepGoal == null) {
          statement.bindNull(7)
        } else {
          statement.bindDouble(7, _tmpSleepGoal.toDouble())
        }
        val _tmpWorkoutMinutesGoal: Int? = entity.workoutMinutesGoal
        if (_tmpWorkoutMinutesGoal == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpWorkoutMinutesGoal.toLong())
        }
        statement.bindLong(9, entity.caloriesBurned.toLong())
        statement.bindLong(10, entity.stepsTaken.toLong())
        statement.bindLong(11, entity.waterIntake.toLong())
        statement.bindDouble(12, entity.sleepHours.toDouble())
        statement.bindLong(13, entity.workoutMinutes.toLong())
        val _tmp_1: Int = if (entity.isCalorieGoalMet) 1 else 0
        statement.bindLong(14, _tmp_1.toLong())
        val _tmp_2: Int = if (entity.isStepGoalMet) 1 else 0
        statement.bindLong(15, _tmp_2.toLong())
        val _tmp_3: Int = if (entity.isWaterGoalMet) 1 else 0
        statement.bindLong(16, _tmp_3.toLong())
        val _tmp_4: Int = if (entity.isSleepGoalMet) 1 else 0
        statement.bindLong(17, _tmp_4.toLong())
        val _tmp_5: Int = if (entity.isWorkoutGoalMet) 1 else 0
        statement.bindLong(18, _tmp_5.toLong())
        statement.bindDouble(19, entity.overallCompletionPercentage.toDouble())
        val _tmp_6: Long? = __converters.dateToTimestamp(entity.updatedAt)
        if (_tmp_6 == null) {
          statement.bindNull(20)
        } else {
          statement.bindLong(20, _tmp_6)
        }
      }
    }
    this.__updateAdapterOfDailyGoal = object : EntityDeleteOrUpdateAdapter<DailyGoal>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `daily_goals` SET `id` = ?,`userId` = ?,`date` = ?,`calorieGoal` = ?,`stepGoal` = ?,`waterGoal` = ?,`sleepGoal` = ?,`workoutMinutesGoal` = ?,`caloriesBurned` = ?,`stepsTaken` = ?,`waterIntake` = ?,`sleepHours` = ?,`workoutMinutes` = ?,`isCalorieGoalMet` = ?,`isStepGoalMet` = ?,`isWaterGoalMet` = ?,`isSleepGoalMet` = ?,`isWorkoutGoalMet` = ?,`overallCompletionPercentage` = ?,`updatedAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: DailyGoal) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        val _tmp: Long? = __converters.dateToTimestamp(entity.date)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmp)
        }
        val _tmpCalorieGoal: Int? = entity.calorieGoal
        if (_tmpCalorieGoal == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpCalorieGoal.toLong())
        }
        val _tmpStepGoal: Int? = entity.stepGoal
        if (_tmpStepGoal == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpStepGoal.toLong())
        }
        val _tmpWaterGoal: Int? = entity.waterGoal
        if (_tmpWaterGoal == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpWaterGoal.toLong())
        }
        val _tmpSleepGoal: Float? = entity.sleepGoal
        if (_tmpSleepGoal == null) {
          statement.bindNull(7)
        } else {
          statement.bindDouble(7, _tmpSleepGoal.toDouble())
        }
        val _tmpWorkoutMinutesGoal: Int? = entity.workoutMinutesGoal
        if (_tmpWorkoutMinutesGoal == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpWorkoutMinutesGoal.toLong())
        }
        statement.bindLong(9, entity.caloriesBurned.toLong())
        statement.bindLong(10, entity.stepsTaken.toLong())
        statement.bindLong(11, entity.waterIntake.toLong())
        statement.bindDouble(12, entity.sleepHours.toDouble())
        statement.bindLong(13, entity.workoutMinutes.toLong())
        val _tmp_1: Int = if (entity.isCalorieGoalMet) 1 else 0
        statement.bindLong(14, _tmp_1.toLong())
        val _tmp_2: Int = if (entity.isStepGoalMet) 1 else 0
        statement.bindLong(15, _tmp_2.toLong())
        val _tmp_3: Int = if (entity.isWaterGoalMet) 1 else 0
        statement.bindLong(16, _tmp_3.toLong())
        val _tmp_4: Int = if (entity.isSleepGoalMet) 1 else 0
        statement.bindLong(17, _tmp_4.toLong())
        val _tmp_5: Int = if (entity.isWorkoutGoalMet) 1 else 0
        statement.bindLong(18, _tmp_5.toLong())
        statement.bindDouble(19, entity.overallCompletionPercentage.toDouble())
        val _tmp_6: Long? = __converters.dateToTimestamp(entity.updatedAt)
        if (_tmp_6 == null) {
          statement.bindNull(20)
        } else {
          statement.bindLong(20, _tmp_6)
        }
        statement.bindLong(21, entity.id)
      }
    }
  }

  public override suspend fun insertGoal(goal: DailyGoal): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfDailyGoal.insertAndReturnId(_connection, goal)
    _result
  }

  public override suspend fun updateGoal(goal: DailyGoal): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfDailyGoal.handle(_connection, goal)
  }

  public override suspend fun getGoalByUserAndDate(userId: Long, date: Date): DailyGoal? {
    val _sql: String = "SELECT * FROM daily_goals WHERE userId = ? AND date = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        val _tmp: Long? = __converters.dateToTimestamp(date)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfCalorieGoal: Int = getColumnIndexOrThrow(_stmt, "calorieGoal")
        val _columnIndexOfStepGoal: Int = getColumnIndexOrThrow(_stmt, "stepGoal")
        val _columnIndexOfWaterGoal: Int = getColumnIndexOrThrow(_stmt, "waterGoal")
        val _columnIndexOfSleepGoal: Int = getColumnIndexOrThrow(_stmt, "sleepGoal")
        val _columnIndexOfWorkoutMinutesGoal: Int = getColumnIndexOrThrow(_stmt, "workoutMinutesGoal")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfStepsTaken: Int = getColumnIndexOrThrow(_stmt, "stepsTaken")
        val _columnIndexOfWaterIntake: Int = getColumnIndexOrThrow(_stmt, "waterIntake")
        val _columnIndexOfSleepHours: Int = getColumnIndexOrThrow(_stmt, "sleepHours")
        val _columnIndexOfWorkoutMinutes: Int = getColumnIndexOrThrow(_stmt, "workoutMinutes")
        val _columnIndexOfIsCalorieGoalMet: Int = getColumnIndexOrThrow(_stmt, "isCalorieGoalMet")
        val _columnIndexOfIsStepGoalMet: Int = getColumnIndexOrThrow(_stmt, "isStepGoalMet")
        val _columnIndexOfIsWaterGoalMet: Int = getColumnIndexOrThrow(_stmt, "isWaterGoalMet")
        val _columnIndexOfIsSleepGoalMet: Int = getColumnIndexOrThrow(_stmt, "isSleepGoalMet")
        val _columnIndexOfIsWorkoutGoalMet: Int = getColumnIndexOrThrow(_stmt, "isWorkoutGoalMet")
        val _columnIndexOfOverallCompletionPercentage: Int = getColumnIndexOrThrow(_stmt, "overallCompletionPercentage")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: DailyGoal?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpDate: Date
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfDate)
          }
          val _tmp_2: Date? = __converters.fromTimestamp(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpDate = _tmp_2
          }
          val _tmpCalorieGoal: Int?
          if (_stmt.isNull(_columnIndexOfCalorieGoal)) {
            _tmpCalorieGoal = null
          } else {
            _tmpCalorieGoal = _stmt.getLong(_columnIndexOfCalorieGoal).toInt()
          }
          val _tmpStepGoal: Int?
          if (_stmt.isNull(_columnIndexOfStepGoal)) {
            _tmpStepGoal = null
          } else {
            _tmpStepGoal = _stmt.getLong(_columnIndexOfStepGoal).toInt()
          }
          val _tmpWaterGoal: Int?
          if (_stmt.isNull(_columnIndexOfWaterGoal)) {
            _tmpWaterGoal = null
          } else {
            _tmpWaterGoal = _stmt.getLong(_columnIndexOfWaterGoal).toInt()
          }
          val _tmpSleepGoal: Float?
          if (_stmt.isNull(_columnIndexOfSleepGoal)) {
            _tmpSleepGoal = null
          } else {
            _tmpSleepGoal = _stmt.getDouble(_columnIndexOfSleepGoal).toFloat()
          }
          val _tmpWorkoutMinutesGoal: Int?
          if (_stmt.isNull(_columnIndexOfWorkoutMinutesGoal)) {
            _tmpWorkoutMinutesGoal = null
          } else {
            _tmpWorkoutMinutesGoal = _stmt.getLong(_columnIndexOfWorkoutMinutesGoal).toInt()
          }
          val _tmpCaloriesBurned: Int
          _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          val _tmpStepsTaken: Int
          _tmpStepsTaken = _stmt.getLong(_columnIndexOfStepsTaken).toInt()
          val _tmpWaterIntake: Int
          _tmpWaterIntake = _stmt.getLong(_columnIndexOfWaterIntake).toInt()
          val _tmpSleepHours: Float
          _tmpSleepHours = _stmt.getDouble(_columnIndexOfSleepHours).toFloat()
          val _tmpWorkoutMinutes: Int
          _tmpWorkoutMinutes = _stmt.getLong(_columnIndexOfWorkoutMinutes).toInt()
          val _tmpIsCalorieGoalMet: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsCalorieGoalMet).toInt()
          _tmpIsCalorieGoalMet = _tmp_3 != 0
          val _tmpIsStepGoalMet: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsStepGoalMet).toInt()
          _tmpIsStepGoalMet = _tmp_4 != 0
          val _tmpIsWaterGoalMet: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsWaterGoalMet).toInt()
          _tmpIsWaterGoalMet = _tmp_5 != 0
          val _tmpIsSleepGoalMet: Boolean
          val _tmp_6: Int
          _tmp_6 = _stmt.getLong(_columnIndexOfIsSleepGoalMet).toInt()
          _tmpIsSleepGoalMet = _tmp_6 != 0
          val _tmpIsWorkoutGoalMet: Boolean
          val _tmp_7: Int
          _tmp_7 = _stmt.getLong(_columnIndexOfIsWorkoutGoalMet).toInt()
          _tmpIsWorkoutGoalMet = _tmp_7 != 0
          val _tmpOverallCompletionPercentage: Float
          _tmpOverallCompletionPercentage = _stmt.getDouble(_columnIndexOfOverallCompletionPercentage).toFloat()
          val _tmpUpdatedAt: Date
          val _tmp_8: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_8 = null
          } else {
            _tmp_8 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_9: Date? = __converters.fromTimestamp(_tmp_8)
          if (_tmp_9 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_9
          }
          _result = DailyGoal(_tmpId,_tmpUserId,_tmpDate,_tmpCalorieGoal,_tmpStepGoal,_tmpWaterGoal,_tmpSleepGoal,_tmpWorkoutMinutesGoal,_tmpCaloriesBurned,_tmpStepsTaken,_tmpWaterIntake,_tmpSleepHours,_tmpWorkoutMinutes,_tmpIsCalorieGoalMet,_tmpIsStepGoalMet,_tmpIsWaterGoalMet,_tmpIsSleepGoalMet,_tmpIsWorkoutGoalMet,_tmpOverallCompletionPercentage,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getRecentGoals(userId: Long, limit: Int): Flow<List<DailyGoal>> {
    val _sql: String = "SELECT * FROM daily_goals WHERE userId = ? ORDER BY date DESC LIMIT ?"
    return createFlow(__db, false, arrayOf("daily_goals")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, limit.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfCalorieGoal: Int = getColumnIndexOrThrow(_stmt, "calorieGoal")
        val _columnIndexOfStepGoal: Int = getColumnIndexOrThrow(_stmt, "stepGoal")
        val _columnIndexOfWaterGoal: Int = getColumnIndexOrThrow(_stmt, "waterGoal")
        val _columnIndexOfSleepGoal: Int = getColumnIndexOrThrow(_stmt, "sleepGoal")
        val _columnIndexOfWorkoutMinutesGoal: Int = getColumnIndexOrThrow(_stmt, "workoutMinutesGoal")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfStepsTaken: Int = getColumnIndexOrThrow(_stmt, "stepsTaken")
        val _columnIndexOfWaterIntake: Int = getColumnIndexOrThrow(_stmt, "waterIntake")
        val _columnIndexOfSleepHours: Int = getColumnIndexOrThrow(_stmt, "sleepHours")
        val _columnIndexOfWorkoutMinutes: Int = getColumnIndexOrThrow(_stmt, "workoutMinutes")
        val _columnIndexOfIsCalorieGoalMet: Int = getColumnIndexOrThrow(_stmt, "isCalorieGoalMet")
        val _columnIndexOfIsStepGoalMet: Int = getColumnIndexOrThrow(_stmt, "isStepGoalMet")
        val _columnIndexOfIsWaterGoalMet: Int = getColumnIndexOrThrow(_stmt, "isWaterGoalMet")
        val _columnIndexOfIsSleepGoalMet: Int = getColumnIndexOrThrow(_stmt, "isSleepGoalMet")
        val _columnIndexOfIsWorkoutGoalMet: Int = getColumnIndexOrThrow(_stmt, "isWorkoutGoalMet")
        val _columnIndexOfOverallCompletionPercentage: Int = getColumnIndexOrThrow(_stmt, "overallCompletionPercentage")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<DailyGoal> = mutableListOf()
        while (_stmt.step()) {
          val _item: DailyGoal
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpDate: Date
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDate)
          }
          val _tmp_1: Date? = __converters.fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpDate = _tmp_1
          }
          val _tmpCalorieGoal: Int?
          if (_stmt.isNull(_columnIndexOfCalorieGoal)) {
            _tmpCalorieGoal = null
          } else {
            _tmpCalorieGoal = _stmt.getLong(_columnIndexOfCalorieGoal).toInt()
          }
          val _tmpStepGoal: Int?
          if (_stmt.isNull(_columnIndexOfStepGoal)) {
            _tmpStepGoal = null
          } else {
            _tmpStepGoal = _stmt.getLong(_columnIndexOfStepGoal).toInt()
          }
          val _tmpWaterGoal: Int?
          if (_stmt.isNull(_columnIndexOfWaterGoal)) {
            _tmpWaterGoal = null
          } else {
            _tmpWaterGoal = _stmt.getLong(_columnIndexOfWaterGoal).toInt()
          }
          val _tmpSleepGoal: Float?
          if (_stmt.isNull(_columnIndexOfSleepGoal)) {
            _tmpSleepGoal = null
          } else {
            _tmpSleepGoal = _stmt.getDouble(_columnIndexOfSleepGoal).toFloat()
          }
          val _tmpWorkoutMinutesGoal: Int?
          if (_stmt.isNull(_columnIndexOfWorkoutMinutesGoal)) {
            _tmpWorkoutMinutesGoal = null
          } else {
            _tmpWorkoutMinutesGoal = _stmt.getLong(_columnIndexOfWorkoutMinutesGoal).toInt()
          }
          val _tmpCaloriesBurned: Int
          _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          val _tmpStepsTaken: Int
          _tmpStepsTaken = _stmt.getLong(_columnIndexOfStepsTaken).toInt()
          val _tmpWaterIntake: Int
          _tmpWaterIntake = _stmt.getLong(_columnIndexOfWaterIntake).toInt()
          val _tmpSleepHours: Float
          _tmpSleepHours = _stmt.getDouble(_columnIndexOfSleepHours).toFloat()
          val _tmpWorkoutMinutes: Int
          _tmpWorkoutMinutes = _stmt.getLong(_columnIndexOfWorkoutMinutes).toInt()
          val _tmpIsCalorieGoalMet: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsCalorieGoalMet).toInt()
          _tmpIsCalorieGoalMet = _tmp_2 != 0
          val _tmpIsStepGoalMet: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsStepGoalMet).toInt()
          _tmpIsStepGoalMet = _tmp_3 != 0
          val _tmpIsWaterGoalMet: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsWaterGoalMet).toInt()
          _tmpIsWaterGoalMet = _tmp_4 != 0
          val _tmpIsSleepGoalMet: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsSleepGoalMet).toInt()
          _tmpIsSleepGoalMet = _tmp_5 != 0
          val _tmpIsWorkoutGoalMet: Boolean
          val _tmp_6: Int
          _tmp_6 = _stmt.getLong(_columnIndexOfIsWorkoutGoalMet).toInt()
          _tmpIsWorkoutGoalMet = _tmp_6 != 0
          val _tmpOverallCompletionPercentage: Float
          _tmpOverallCompletionPercentage = _stmt.getDouble(_columnIndexOfOverallCompletionPercentage).toFloat()
          val _tmpUpdatedAt: Date
          val _tmp_7: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_7 = null
          } else {
            _tmp_7 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_8: Date? = __converters.fromTimestamp(_tmp_7)
          if (_tmp_8 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_8
          }
          _item = DailyGoal(_tmpId,_tmpUserId,_tmpDate,_tmpCalorieGoal,_tmpStepGoal,_tmpWaterGoal,_tmpSleepGoal,_tmpWorkoutMinutesGoal,_tmpCaloriesBurned,_tmpStepsTaken,_tmpWaterIntake,_tmpSleepHours,_tmpWorkoutMinutes,_tmpIsCalorieGoalMet,_tmpIsStepGoalMet,_tmpIsWaterGoalMet,_tmpIsSleepGoalMet,_tmpIsWorkoutGoalMet,_tmpOverallCompletionPercentage,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getGoalsInDateRange(
    userId: Long,
    startDate: Date,
    endDate: Date,
  ): Flow<List<DailyGoal>> {
    val _sql: String = """
        |
        |        SELECT * FROM daily_goals
        |        WHERE userId = ?
        |        AND date BETWEEN ? AND ?
        |        ORDER BY date DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("daily_goals")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        val _tmp: Long? = __converters.dateToTimestamp(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 3
        val _tmp_1: Long? = __converters.dateToTimestamp(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp_1)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfCalorieGoal: Int = getColumnIndexOrThrow(_stmt, "calorieGoal")
        val _columnIndexOfStepGoal: Int = getColumnIndexOrThrow(_stmt, "stepGoal")
        val _columnIndexOfWaterGoal: Int = getColumnIndexOrThrow(_stmt, "waterGoal")
        val _columnIndexOfSleepGoal: Int = getColumnIndexOrThrow(_stmt, "sleepGoal")
        val _columnIndexOfWorkoutMinutesGoal: Int = getColumnIndexOrThrow(_stmt, "workoutMinutesGoal")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfStepsTaken: Int = getColumnIndexOrThrow(_stmt, "stepsTaken")
        val _columnIndexOfWaterIntake: Int = getColumnIndexOrThrow(_stmt, "waterIntake")
        val _columnIndexOfSleepHours: Int = getColumnIndexOrThrow(_stmt, "sleepHours")
        val _columnIndexOfWorkoutMinutes: Int = getColumnIndexOrThrow(_stmt, "workoutMinutes")
        val _columnIndexOfIsCalorieGoalMet: Int = getColumnIndexOrThrow(_stmt, "isCalorieGoalMet")
        val _columnIndexOfIsStepGoalMet: Int = getColumnIndexOrThrow(_stmt, "isStepGoalMet")
        val _columnIndexOfIsWaterGoalMet: Int = getColumnIndexOrThrow(_stmt, "isWaterGoalMet")
        val _columnIndexOfIsSleepGoalMet: Int = getColumnIndexOrThrow(_stmt, "isSleepGoalMet")
        val _columnIndexOfIsWorkoutGoalMet: Int = getColumnIndexOrThrow(_stmt, "isWorkoutGoalMet")
        val _columnIndexOfOverallCompletionPercentage: Int = getColumnIndexOrThrow(_stmt, "overallCompletionPercentage")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<DailyGoal> = mutableListOf()
        while (_stmt.step()) {
          val _item: DailyGoal
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpDate: Date
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfDate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfDate)
          }
          val _tmp_3: Date? = __converters.fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpDate = _tmp_3
          }
          val _tmpCalorieGoal: Int?
          if (_stmt.isNull(_columnIndexOfCalorieGoal)) {
            _tmpCalorieGoal = null
          } else {
            _tmpCalorieGoal = _stmt.getLong(_columnIndexOfCalorieGoal).toInt()
          }
          val _tmpStepGoal: Int?
          if (_stmt.isNull(_columnIndexOfStepGoal)) {
            _tmpStepGoal = null
          } else {
            _tmpStepGoal = _stmt.getLong(_columnIndexOfStepGoal).toInt()
          }
          val _tmpWaterGoal: Int?
          if (_stmt.isNull(_columnIndexOfWaterGoal)) {
            _tmpWaterGoal = null
          } else {
            _tmpWaterGoal = _stmt.getLong(_columnIndexOfWaterGoal).toInt()
          }
          val _tmpSleepGoal: Float?
          if (_stmt.isNull(_columnIndexOfSleepGoal)) {
            _tmpSleepGoal = null
          } else {
            _tmpSleepGoal = _stmt.getDouble(_columnIndexOfSleepGoal).toFloat()
          }
          val _tmpWorkoutMinutesGoal: Int?
          if (_stmt.isNull(_columnIndexOfWorkoutMinutesGoal)) {
            _tmpWorkoutMinutesGoal = null
          } else {
            _tmpWorkoutMinutesGoal = _stmt.getLong(_columnIndexOfWorkoutMinutesGoal).toInt()
          }
          val _tmpCaloriesBurned: Int
          _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          val _tmpStepsTaken: Int
          _tmpStepsTaken = _stmt.getLong(_columnIndexOfStepsTaken).toInt()
          val _tmpWaterIntake: Int
          _tmpWaterIntake = _stmt.getLong(_columnIndexOfWaterIntake).toInt()
          val _tmpSleepHours: Float
          _tmpSleepHours = _stmt.getDouble(_columnIndexOfSleepHours).toFloat()
          val _tmpWorkoutMinutes: Int
          _tmpWorkoutMinutes = _stmt.getLong(_columnIndexOfWorkoutMinutes).toInt()
          val _tmpIsCalorieGoalMet: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsCalorieGoalMet).toInt()
          _tmpIsCalorieGoalMet = _tmp_4 != 0
          val _tmpIsStepGoalMet: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsStepGoalMet).toInt()
          _tmpIsStepGoalMet = _tmp_5 != 0
          val _tmpIsWaterGoalMet: Boolean
          val _tmp_6: Int
          _tmp_6 = _stmt.getLong(_columnIndexOfIsWaterGoalMet).toInt()
          _tmpIsWaterGoalMet = _tmp_6 != 0
          val _tmpIsSleepGoalMet: Boolean
          val _tmp_7: Int
          _tmp_7 = _stmt.getLong(_columnIndexOfIsSleepGoalMet).toInt()
          _tmpIsSleepGoalMet = _tmp_7 != 0
          val _tmpIsWorkoutGoalMet: Boolean
          val _tmp_8: Int
          _tmp_8 = _stmt.getLong(_columnIndexOfIsWorkoutGoalMet).toInt()
          _tmpIsWorkoutGoalMet = _tmp_8 != 0
          val _tmpOverallCompletionPercentage: Float
          _tmpOverallCompletionPercentage = _stmt.getDouble(_columnIndexOfOverallCompletionPercentage).toFloat()
          val _tmpUpdatedAt: Date
          val _tmp_9: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_9 = null
          } else {
            _tmp_9 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_10: Date? = __converters.fromTimestamp(_tmp_9)
          if (_tmp_10 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_10
          }
          _item = DailyGoal(_tmpId,_tmpUserId,_tmpDate,_tmpCalorieGoal,_tmpStepGoal,_tmpWaterGoal,_tmpSleepGoal,_tmpWorkoutMinutesGoal,_tmpCaloriesBurned,_tmpStepsTaken,_tmpWaterIntake,_tmpSleepHours,_tmpWorkoutMinutes,_tmpIsCalorieGoalMet,_tmpIsStepGoalMet,_tmpIsWaterGoalMet,_tmpIsSleepGoalMet,_tmpIsWorkoutGoalMet,_tmpOverallCompletionPercentage,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getPerfectDaysCount(userId: Long): Int {
    val _sql: String = """
        |
        |        SELECT COUNT(*) FROM daily_goals
        |        WHERE userId = ?
        |        AND overallCompletionPercentage >= 100
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
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

  public override suspend fun getAverageCompletionInRange(
    userId: Long,
    startDate: Date,
    endDate: Date,
  ): Float? {
    val _sql: String = """
        |
        |        SELECT AVG(overallCompletionPercentage) FROM daily_goals
        |        WHERE userId = ?
        |        AND date BETWEEN ? AND ?
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        val _tmp: Long? = __converters.dateToTimestamp(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 3
        val _tmp_1: Long? = __converters.dateToTimestamp(endDate)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp_1)
        }
        val _result: Float?
        if (_stmt.step()) {
          val _tmp_2: Float?
          if (_stmt.isNull(0)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getDouble(0).toFloat()
          }
          _result = _tmp_2
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateCalories(goalId: Long, calories: Int) {
    val _sql: String = """
        |
        |        UPDATE daily_goals
        |        SET caloriesBurned = ?,
        |            isCalorieGoalMet = CASE WHEN ? >= calorieGoal THEN 1 ELSE 0 END
        |        WHERE id = ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, calories.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, calories.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, goalId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateSteps(goalId: Long, steps: Int) {
    val _sql: String = """
        |
        |        UPDATE daily_goals
        |        SET stepsTaken = ?,
        |            isStepGoalMet = CASE WHEN ? >= stepGoal THEN 1 ELSE 0 END
        |        WHERE id = ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, steps.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, steps.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, goalId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateWater(goalId: Long, water: Int) {
    val _sql: String = """
        |
        |        UPDATE daily_goals
        |        SET waterIntake = ?,
        |            isWaterGoalMet = CASE WHEN ? >= waterGoal THEN 1 ELSE 0 END
        |        WHERE id = ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, water.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, water.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, goalId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateSleep(goalId: Long, sleep: Float) {
    val _sql: String = """
        |
        |        UPDATE daily_goals
        |        SET sleepHours = ?,
        |            isSleepGoalMet = CASE WHEN ? >= sleepGoal THEN 1 ELSE 0 END
        |        WHERE id = ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindDouble(_argIndex, sleep.toDouble())
        _argIndex = 2
        _stmt.bindDouble(_argIndex, sleep.toDouble())
        _argIndex = 3
        _stmt.bindLong(_argIndex, goalId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateWorkoutMinutes(goalId: Long, minutes: Int) {
    val _sql: String = """
        |
        |        UPDATE daily_goals
        |        SET workoutMinutes = ?,
        |            isWorkoutGoalMet = CASE WHEN ? >= workoutMinutesGoal THEN 1 ELSE 0 END
        |        WHERE id = ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, minutes.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, minutes.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, goalId)
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
