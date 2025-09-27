package com.campus.fitintent.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.campus.fitintent.database.Converters
import com.campus.fitintent.models.WorkoutSession
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
public class WorkoutSessionDao_Impl(
  __db: RoomDatabase,
) : WorkoutSessionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfWorkoutSession: EntityInsertAdapter<WorkoutSession>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfWorkoutSession: EntityDeleteOrUpdateAdapter<WorkoutSession>

  private val __updateAdapterOfWorkoutSession: EntityDeleteOrUpdateAdapter<WorkoutSession>
  init {
    this.__db = __db
    this.__insertAdapterOfWorkoutSession = object : EntityInsertAdapter<WorkoutSession>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `workout_sessions` (`id`,`userId`,`workoutId`,`startTime`,`endTime`,`durationMinutes`,`caloriesBurned`,`averageHeartRate`,`maxHeartRate`,`notes`,`feelingRating`,`difficultyRating`,`isCompleted`,`completionPercentage`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: WorkoutSession) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        val _tmpWorkoutId: Long? = entity.workoutId
        if (_tmpWorkoutId == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmpWorkoutId)
        }
        val _tmp: Long? = __converters.dateToTimestamp(entity.startTime)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmp)
        }
        val _tmpEndTime: Date? = entity.endTime
        val _tmp_1: Long? = __converters.dateToTimestamp(_tmpEndTime)
        if (_tmp_1 == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmp_1)
        }
        val _tmpDurationMinutes: Int? = entity.durationMinutes
        if (_tmpDurationMinutes == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpDurationMinutes.toLong())
        }
        val _tmpCaloriesBurned: Int? = entity.caloriesBurned
        if (_tmpCaloriesBurned == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpCaloriesBurned.toLong())
        }
        val _tmpAverageHeartRate: Int? = entity.averageHeartRate
        if (_tmpAverageHeartRate == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpAverageHeartRate.toLong())
        }
        val _tmpMaxHeartRate: Int? = entity.maxHeartRate
        if (_tmpMaxHeartRate == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpMaxHeartRate.toLong())
        }
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmpNotes)
        }
        val _tmpFeelingRating: Int? = entity.feelingRating
        if (_tmpFeelingRating == null) {
          statement.bindNull(11)
        } else {
          statement.bindLong(11, _tmpFeelingRating.toLong())
        }
        val _tmpDifficultyRating: Int? = entity.difficultyRating
        if (_tmpDifficultyRating == null) {
          statement.bindNull(12)
        } else {
          statement.bindLong(12, _tmpDifficultyRating.toLong())
        }
        val _tmp_2: Int = if (entity.isCompleted) 1 else 0
        statement.bindLong(13, _tmp_2.toLong())
        statement.bindDouble(14, entity.completionPercentage.toDouble())
      }
    }
    this.__deleteAdapterOfWorkoutSession = object : EntityDeleteOrUpdateAdapter<WorkoutSession>() {
      protected override fun createQuery(): String = "DELETE FROM `workout_sessions` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: WorkoutSession) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfWorkoutSession = object : EntityDeleteOrUpdateAdapter<WorkoutSession>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `workout_sessions` SET `id` = ?,`userId` = ?,`workoutId` = ?,`startTime` = ?,`endTime` = ?,`durationMinutes` = ?,`caloriesBurned` = ?,`averageHeartRate` = ?,`maxHeartRate` = ?,`notes` = ?,`feelingRating` = ?,`difficultyRating` = ?,`isCompleted` = ?,`completionPercentage` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: WorkoutSession) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        val _tmpWorkoutId: Long? = entity.workoutId
        if (_tmpWorkoutId == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmpWorkoutId)
        }
        val _tmp: Long? = __converters.dateToTimestamp(entity.startTime)
        if (_tmp == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmp)
        }
        val _tmpEndTime: Date? = entity.endTime
        val _tmp_1: Long? = __converters.dateToTimestamp(_tmpEndTime)
        if (_tmp_1 == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmp_1)
        }
        val _tmpDurationMinutes: Int? = entity.durationMinutes
        if (_tmpDurationMinutes == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpDurationMinutes.toLong())
        }
        val _tmpCaloriesBurned: Int? = entity.caloriesBurned
        if (_tmpCaloriesBurned == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpCaloriesBurned.toLong())
        }
        val _tmpAverageHeartRate: Int? = entity.averageHeartRate
        if (_tmpAverageHeartRate == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpAverageHeartRate.toLong())
        }
        val _tmpMaxHeartRate: Int? = entity.maxHeartRate
        if (_tmpMaxHeartRate == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpMaxHeartRate.toLong())
        }
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmpNotes)
        }
        val _tmpFeelingRating: Int? = entity.feelingRating
        if (_tmpFeelingRating == null) {
          statement.bindNull(11)
        } else {
          statement.bindLong(11, _tmpFeelingRating.toLong())
        }
        val _tmpDifficultyRating: Int? = entity.difficultyRating
        if (_tmpDifficultyRating == null) {
          statement.bindNull(12)
        } else {
          statement.bindLong(12, _tmpDifficultyRating.toLong())
        }
        val _tmp_2: Int = if (entity.isCompleted) 1 else 0
        statement.bindLong(13, _tmp_2.toLong())
        statement.bindDouble(14, entity.completionPercentage.toDouble())
        statement.bindLong(15, entity.id)
      }
    }
  }

  public override suspend fun insertSession(session: WorkoutSession): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfWorkoutSession.insertAndReturnId(_connection, session)
    _result
  }

  public override suspend fun deleteSession(session: WorkoutSession): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfWorkoutSession.handle(_connection, session)
  }

  public override suspend fun updateSession(session: WorkoutSession): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfWorkoutSession.handle(_connection, session)
  }

  public override fun getSessionsByUser(userId: Long): Flow<List<WorkoutSession>> {
    val _sql: String = "SELECT * FROM workout_sessions WHERE userId = ? ORDER BY startTime DESC"
    return createFlow(__db, false, arrayOf("workout_sessions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfWorkoutId: Int = getColumnIndexOrThrow(_stmt, "workoutId")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfDurationMinutes: Int = getColumnIndexOrThrow(_stmt, "durationMinutes")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfAverageHeartRate: Int = getColumnIndexOrThrow(_stmt, "averageHeartRate")
        val _columnIndexOfMaxHeartRate: Int = getColumnIndexOrThrow(_stmt, "maxHeartRate")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfFeelingRating: Int = getColumnIndexOrThrow(_stmt, "feelingRating")
        val _columnIndexOfDifficultyRating: Int = getColumnIndexOrThrow(_stmt, "difficultyRating")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _columnIndexOfCompletionPercentage: Int = getColumnIndexOrThrow(_stmt, "completionPercentage")
        val _result: MutableList<WorkoutSession> = mutableListOf()
        while (_stmt.step()) {
          val _item: WorkoutSession
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpWorkoutId: Long?
          if (_stmt.isNull(_columnIndexOfWorkoutId)) {
            _tmpWorkoutId = null
          } else {
            _tmpWorkoutId = _stmt.getLong(_columnIndexOfWorkoutId)
          }
          val _tmpStartTime: Date
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfStartTime)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfStartTime)
          }
          val _tmp_1: Date? = __converters.fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpStartTime = _tmp_1
          }
          val _tmpEndTime: Date?
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfEndTime)
          }
          _tmpEndTime = __converters.fromTimestamp(_tmp_2)
          val _tmpDurationMinutes: Int?
          if (_stmt.isNull(_columnIndexOfDurationMinutes)) {
            _tmpDurationMinutes = null
          } else {
            _tmpDurationMinutes = _stmt.getLong(_columnIndexOfDurationMinutes).toInt()
          }
          val _tmpCaloriesBurned: Int?
          if (_stmt.isNull(_columnIndexOfCaloriesBurned)) {
            _tmpCaloriesBurned = null
          } else {
            _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          }
          val _tmpAverageHeartRate: Int?
          if (_stmt.isNull(_columnIndexOfAverageHeartRate)) {
            _tmpAverageHeartRate = null
          } else {
            _tmpAverageHeartRate = _stmt.getLong(_columnIndexOfAverageHeartRate).toInt()
          }
          val _tmpMaxHeartRate: Int?
          if (_stmt.isNull(_columnIndexOfMaxHeartRate)) {
            _tmpMaxHeartRate = null
          } else {
            _tmpMaxHeartRate = _stmt.getLong(_columnIndexOfMaxHeartRate).toInt()
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          val _tmpFeelingRating: Int?
          if (_stmt.isNull(_columnIndexOfFeelingRating)) {
            _tmpFeelingRating = null
          } else {
            _tmpFeelingRating = _stmt.getLong(_columnIndexOfFeelingRating).toInt()
          }
          val _tmpDifficultyRating: Int?
          if (_stmt.isNull(_columnIndexOfDifficultyRating)) {
            _tmpDifficultyRating = null
          } else {
            _tmpDifficultyRating = _stmt.getLong(_columnIndexOfDifficultyRating).toInt()
          }
          val _tmpIsCompleted: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_3 != 0
          val _tmpCompletionPercentage: Float
          _tmpCompletionPercentage = _stmt.getDouble(_columnIndexOfCompletionPercentage).toFloat()
          _item = WorkoutSession(_tmpId,_tmpUserId,_tmpWorkoutId,_tmpStartTime,_tmpEndTime,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpAverageHeartRate,_tmpMaxHeartRate,_tmpNotes,_tmpFeelingRating,_tmpDifficultyRating,_tmpIsCompleted,_tmpCompletionPercentage)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSessionsByUserAndDate(userId: Long, date: Date): List<WorkoutSession> {
    val _sql: String = "SELECT * FROM workout_sessions WHERE userId = ? AND DATE(startTime / 1000, 'unixepoch') = DATE(? / 1000, 'unixepoch')"
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
        val _columnIndexOfWorkoutId: Int = getColumnIndexOrThrow(_stmt, "workoutId")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfDurationMinutes: Int = getColumnIndexOrThrow(_stmt, "durationMinutes")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfAverageHeartRate: Int = getColumnIndexOrThrow(_stmt, "averageHeartRate")
        val _columnIndexOfMaxHeartRate: Int = getColumnIndexOrThrow(_stmt, "maxHeartRate")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfFeelingRating: Int = getColumnIndexOrThrow(_stmt, "feelingRating")
        val _columnIndexOfDifficultyRating: Int = getColumnIndexOrThrow(_stmt, "difficultyRating")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _columnIndexOfCompletionPercentage: Int = getColumnIndexOrThrow(_stmt, "completionPercentage")
        val _result: MutableList<WorkoutSession> = mutableListOf()
        while (_stmt.step()) {
          val _item: WorkoutSession
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpWorkoutId: Long?
          if (_stmt.isNull(_columnIndexOfWorkoutId)) {
            _tmpWorkoutId = null
          } else {
            _tmpWorkoutId = _stmt.getLong(_columnIndexOfWorkoutId)
          }
          val _tmpStartTime: Date
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfStartTime)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfStartTime)
          }
          val _tmp_2: Date? = __converters.fromTimestamp(_tmp_1)
          if (_tmp_2 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpStartTime = _tmp_2
          }
          val _tmpEndTime: Date?
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfEndTime)
          }
          _tmpEndTime = __converters.fromTimestamp(_tmp_3)
          val _tmpDurationMinutes: Int?
          if (_stmt.isNull(_columnIndexOfDurationMinutes)) {
            _tmpDurationMinutes = null
          } else {
            _tmpDurationMinutes = _stmt.getLong(_columnIndexOfDurationMinutes).toInt()
          }
          val _tmpCaloriesBurned: Int?
          if (_stmt.isNull(_columnIndexOfCaloriesBurned)) {
            _tmpCaloriesBurned = null
          } else {
            _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          }
          val _tmpAverageHeartRate: Int?
          if (_stmt.isNull(_columnIndexOfAverageHeartRate)) {
            _tmpAverageHeartRate = null
          } else {
            _tmpAverageHeartRate = _stmt.getLong(_columnIndexOfAverageHeartRate).toInt()
          }
          val _tmpMaxHeartRate: Int?
          if (_stmt.isNull(_columnIndexOfMaxHeartRate)) {
            _tmpMaxHeartRate = null
          } else {
            _tmpMaxHeartRate = _stmt.getLong(_columnIndexOfMaxHeartRate).toInt()
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          val _tmpFeelingRating: Int?
          if (_stmt.isNull(_columnIndexOfFeelingRating)) {
            _tmpFeelingRating = null
          } else {
            _tmpFeelingRating = _stmt.getLong(_columnIndexOfFeelingRating).toInt()
          }
          val _tmpDifficultyRating: Int?
          if (_stmt.isNull(_columnIndexOfDifficultyRating)) {
            _tmpDifficultyRating = null
          } else {
            _tmpDifficultyRating = _stmt.getLong(_columnIndexOfDifficultyRating).toInt()
          }
          val _tmpIsCompleted: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_4 != 0
          val _tmpCompletionPercentage: Float
          _tmpCompletionPercentage = _stmt.getDouble(_columnIndexOfCompletionPercentage).toFloat()
          _item = WorkoutSession(_tmpId,_tmpUserId,_tmpWorkoutId,_tmpStartTime,_tmpEndTime,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpAverageHeartRate,_tmpMaxHeartRate,_tmpNotes,_tmpFeelingRating,_tmpDifficultyRating,_tmpIsCompleted,_tmpCompletionPercentage)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSessionById(sessionId: Long): WorkoutSession? {
    val _sql: String = "SELECT * FROM workout_sessions WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, sessionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfWorkoutId: Int = getColumnIndexOrThrow(_stmt, "workoutId")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfDurationMinutes: Int = getColumnIndexOrThrow(_stmt, "durationMinutes")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfAverageHeartRate: Int = getColumnIndexOrThrow(_stmt, "averageHeartRate")
        val _columnIndexOfMaxHeartRate: Int = getColumnIndexOrThrow(_stmt, "maxHeartRate")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfFeelingRating: Int = getColumnIndexOrThrow(_stmt, "feelingRating")
        val _columnIndexOfDifficultyRating: Int = getColumnIndexOrThrow(_stmt, "difficultyRating")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _columnIndexOfCompletionPercentage: Int = getColumnIndexOrThrow(_stmt, "completionPercentage")
        val _result: WorkoutSession?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpWorkoutId: Long?
          if (_stmt.isNull(_columnIndexOfWorkoutId)) {
            _tmpWorkoutId = null
          } else {
            _tmpWorkoutId = _stmt.getLong(_columnIndexOfWorkoutId)
          }
          val _tmpStartTime: Date
          val _tmp: Long?
          if (_stmt.isNull(_columnIndexOfStartTime)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfStartTime)
          }
          val _tmp_1: Date? = __converters.fromTimestamp(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpStartTime = _tmp_1
          }
          val _tmpEndTime: Date?
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfEndTime)
          }
          _tmpEndTime = __converters.fromTimestamp(_tmp_2)
          val _tmpDurationMinutes: Int?
          if (_stmt.isNull(_columnIndexOfDurationMinutes)) {
            _tmpDurationMinutes = null
          } else {
            _tmpDurationMinutes = _stmt.getLong(_columnIndexOfDurationMinutes).toInt()
          }
          val _tmpCaloriesBurned: Int?
          if (_stmt.isNull(_columnIndexOfCaloriesBurned)) {
            _tmpCaloriesBurned = null
          } else {
            _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          }
          val _tmpAverageHeartRate: Int?
          if (_stmt.isNull(_columnIndexOfAverageHeartRate)) {
            _tmpAverageHeartRate = null
          } else {
            _tmpAverageHeartRate = _stmt.getLong(_columnIndexOfAverageHeartRate).toInt()
          }
          val _tmpMaxHeartRate: Int?
          if (_stmt.isNull(_columnIndexOfMaxHeartRate)) {
            _tmpMaxHeartRate = null
          } else {
            _tmpMaxHeartRate = _stmt.getLong(_columnIndexOfMaxHeartRate).toInt()
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          val _tmpFeelingRating: Int?
          if (_stmt.isNull(_columnIndexOfFeelingRating)) {
            _tmpFeelingRating = null
          } else {
            _tmpFeelingRating = _stmt.getLong(_columnIndexOfFeelingRating).toInt()
          }
          val _tmpDifficultyRating: Int?
          if (_stmt.isNull(_columnIndexOfDifficultyRating)) {
            _tmpDifficultyRating = null
          } else {
            _tmpDifficultyRating = _stmt.getLong(_columnIndexOfDifficultyRating).toInt()
          }
          val _tmpIsCompleted: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_3 != 0
          val _tmpCompletionPercentage: Float
          _tmpCompletionPercentage = _stmt.getDouble(_columnIndexOfCompletionPercentage).toFloat()
          _result = WorkoutSession(_tmpId,_tmpUserId,_tmpWorkoutId,_tmpStartTime,_tmpEndTime,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpAverageHeartRate,_tmpMaxHeartRate,_tmpNotes,_tmpFeelingRating,_tmpDifficultyRating,_tmpIsCompleted,_tmpCompletionPercentage)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getCompletedSessionCount(userId: Long): Int {
    val _sql: String = """
        |
        |        SELECT COUNT(*) FROM workout_sessions
        |        WHERE userId = ?
        |        AND isCompleted = 1
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

  public override suspend fun getTotalWorkoutMinutes(userId: Long): Int? {
    val _sql: String = """
        |
        |        SELECT SUM(durationMinutes) FROM workout_sessions
        |        WHERE userId = ?
        |        AND isCompleted = 1
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _result: Int?
        if (_stmt.step()) {
          val _tmp: Int?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(0).toInt()
          }
          _result = _tmp
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTotalCaloriesBurned(userId: Long): Int? {
    val _sql: String = """
        |
        |        SELECT SUM(caloriesBurned) FROM workout_sessions
        |        WHERE userId = ?
        |        AND isCompleted = 1
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _result: Int?
        if (_stmt.step()) {
          val _tmp: Int?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(0).toInt()
          }
          _result = _tmp
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getSessionsInDateRange(
    userId: Long,
    startDate: Date,
    endDate: Date,
  ): Flow<List<WorkoutSession>> {
    val _sql: String = """
        |
        |        SELECT * FROM workout_sessions
        |        WHERE userId = ?
        |        AND startTime BETWEEN ? AND ?
        |        ORDER BY startTime DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("workout_sessions")) { _connection ->
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
        val _columnIndexOfWorkoutId: Int = getColumnIndexOrThrow(_stmt, "workoutId")
        val _columnIndexOfStartTime: Int = getColumnIndexOrThrow(_stmt, "startTime")
        val _columnIndexOfEndTime: Int = getColumnIndexOrThrow(_stmt, "endTime")
        val _columnIndexOfDurationMinutes: Int = getColumnIndexOrThrow(_stmt, "durationMinutes")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfAverageHeartRate: Int = getColumnIndexOrThrow(_stmt, "averageHeartRate")
        val _columnIndexOfMaxHeartRate: Int = getColumnIndexOrThrow(_stmt, "maxHeartRate")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfFeelingRating: Int = getColumnIndexOrThrow(_stmt, "feelingRating")
        val _columnIndexOfDifficultyRating: Int = getColumnIndexOrThrow(_stmt, "difficultyRating")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _columnIndexOfCompletionPercentage: Int = getColumnIndexOrThrow(_stmt, "completionPercentage")
        val _result: MutableList<WorkoutSession> = mutableListOf()
        while (_stmt.step()) {
          val _item: WorkoutSession
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpWorkoutId: Long?
          if (_stmt.isNull(_columnIndexOfWorkoutId)) {
            _tmpWorkoutId = null
          } else {
            _tmpWorkoutId = _stmt.getLong(_columnIndexOfWorkoutId)
          }
          val _tmpStartTime: Date
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfStartTime)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfStartTime)
          }
          val _tmp_3: Date? = __converters.fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpStartTime = _tmp_3
          }
          val _tmpEndTime: Date?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfEndTime)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfEndTime)
          }
          _tmpEndTime = __converters.fromTimestamp(_tmp_4)
          val _tmpDurationMinutes: Int?
          if (_stmt.isNull(_columnIndexOfDurationMinutes)) {
            _tmpDurationMinutes = null
          } else {
            _tmpDurationMinutes = _stmt.getLong(_columnIndexOfDurationMinutes).toInt()
          }
          val _tmpCaloriesBurned: Int?
          if (_stmt.isNull(_columnIndexOfCaloriesBurned)) {
            _tmpCaloriesBurned = null
          } else {
            _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          }
          val _tmpAverageHeartRate: Int?
          if (_stmt.isNull(_columnIndexOfAverageHeartRate)) {
            _tmpAverageHeartRate = null
          } else {
            _tmpAverageHeartRate = _stmt.getLong(_columnIndexOfAverageHeartRate).toInt()
          }
          val _tmpMaxHeartRate: Int?
          if (_stmt.isNull(_columnIndexOfMaxHeartRate)) {
            _tmpMaxHeartRate = null
          } else {
            _tmpMaxHeartRate = _stmt.getLong(_columnIndexOfMaxHeartRate).toInt()
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          val _tmpFeelingRating: Int?
          if (_stmt.isNull(_columnIndexOfFeelingRating)) {
            _tmpFeelingRating = null
          } else {
            _tmpFeelingRating = _stmt.getLong(_columnIndexOfFeelingRating).toInt()
          }
          val _tmpDifficultyRating: Int?
          if (_stmt.isNull(_columnIndexOfDifficultyRating)) {
            _tmpDifficultyRating = null
          } else {
            _tmpDifficultyRating = _stmt.getLong(_columnIndexOfDifficultyRating).toInt()
          }
          val _tmpIsCompleted: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_5 != 0
          val _tmpCompletionPercentage: Float
          _tmpCompletionPercentage = _stmt.getDouble(_columnIndexOfCompletionPercentage).toFloat()
          _item = WorkoutSession(_tmpId,_tmpUserId,_tmpWorkoutId,_tmpStartTime,_tmpEndTime,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpAverageHeartRate,_tmpMaxHeartRate,_tmpNotes,_tmpFeelingRating,_tmpDifficultyRating,_tmpIsCompleted,_tmpCompletionPercentage)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAverageFeelingRating(userId: Long): Float? {
    val _sql: String = """
        |
        |        SELECT AVG(feelingRating) FROM workout_sessions
        |        WHERE userId = ?
        |        AND isCompleted = 1
        |        AND feelingRating IS NOT NULL
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _result: Float?
        if (_stmt.step()) {
          val _tmp: Float?
          if (_stmt.isNull(0)) {
            _tmp = null
          } else {
            _tmp = _stmt.getDouble(0).toFloat()
          }
          _result = _tmp
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun completeSession(
    sessionId: Long,
    endTime: Date,
    duration: Int,
    calories: Int,
    isCompleted: Boolean,
    percentage: Float,
  ) {
    val _sql: String = """
        |
        |        UPDATE workout_sessions
        |        SET endTime = ?,
        |            durationMinutes = ?,
        |            caloriesBurned = ?,
        |            isCompleted = ?,
        |            completionPercentage = ?
        |        WHERE id = ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters.dateToTimestamp(endTime)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindLong(_argIndex, duration.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, calories.toLong())
        _argIndex = 4
        val _tmp_1: Int = if (isCompleted) 1 else 0
        _stmt.bindLong(_argIndex, _tmp_1.toLong())
        _argIndex = 5
        _stmt.bindDouble(_argIndex, percentage.toDouble())
        _argIndex = 6
        _stmt.bindLong(_argIndex, sessionId)
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
