package com.campus.fitintent.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.campus.fitintent.database.Converters
import com.campus.fitintent.models.Habit
import com.campus.fitintent.models.HabitCategory
import com.campus.fitintent.models.HabitFrequency
import java.util.Date
import javax.`annotation`.processing.Generated
import kotlin.Boolean
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
public class HabitDao_Impl(
  __db: RoomDatabase,
) : HabitDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfHabit: EntityInsertAdapter<Habit>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfHabit: EntityDeleteOrUpdateAdapter<Habit>

  private val __updateAdapterOfHabit: EntityDeleteOrUpdateAdapter<Habit>
  init {
    this.__db = __db
    this.__insertAdapterOfHabit = object : EntityInsertAdapter<Habit>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `habits` (`id`,`userId`,`name`,`description`,`category`,`targetDays`,`frequency`,`reminderTime`,`reminderEnabled`,`currentStreak`,`longestStreak`,`completedDays`,`skippedDays`,`isActive`,`isPaused`,`isCompleted`,`createdAt`,`updatedAt`,`completedAt`,`color`,`icon`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Habit) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        statement.bindText(3, entity.name)
        statement.bindText(4, entity.description)
        val _tmp: String = __converters.fromHabitCategory(entity.category)
        statement.bindText(5, _tmp)
        statement.bindLong(6, entity.targetDays.toLong())
        val _tmp_1: String = __converters.fromHabitFrequency(entity.frequency)
        statement.bindText(7, _tmp_1)
        val _tmpReminderTime: String? = entity.reminderTime
        if (_tmpReminderTime == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpReminderTime)
        }
        val _tmp_2: Int = if (entity.reminderEnabled) 1 else 0
        statement.bindLong(9, _tmp_2.toLong())
        statement.bindLong(10, entity.currentStreak.toLong())
        statement.bindLong(11, entity.longestStreak.toLong())
        statement.bindLong(12, entity.completedDays.toLong())
        statement.bindLong(13, entity.skippedDays.toLong())
        val _tmp_3: Int = if (entity.isActive) 1 else 0
        statement.bindLong(14, _tmp_3.toLong())
        val _tmp_4: Int = if (entity.isPaused) 1 else 0
        statement.bindLong(15, _tmp_4.toLong())
        val _tmp_5: Int = if (entity.isCompleted) 1 else 0
        statement.bindLong(16, _tmp_5.toLong())
        val _tmp_6: Long? = __converters.dateToTimestamp(entity.createdAt)
        if (_tmp_6 == null) {
          statement.bindNull(17)
        } else {
          statement.bindLong(17, _tmp_6)
        }
        val _tmp_7: Long? = __converters.dateToTimestamp(entity.updatedAt)
        if (_tmp_7 == null) {
          statement.bindNull(18)
        } else {
          statement.bindLong(18, _tmp_7)
        }
        val _tmpCompletedAt: Date? = entity.completedAt
        val _tmp_8: Long? = __converters.dateToTimestamp(_tmpCompletedAt)
        if (_tmp_8 == null) {
          statement.bindNull(19)
        } else {
          statement.bindLong(19, _tmp_8)
        }
        statement.bindText(20, entity.color)
        val _tmpIcon: String? = entity.icon
        if (_tmpIcon == null) {
          statement.bindNull(21)
        } else {
          statement.bindText(21, _tmpIcon)
        }
      }
    }
    this.__deleteAdapterOfHabit = object : EntityDeleteOrUpdateAdapter<Habit>() {
      protected override fun createQuery(): String = "DELETE FROM `habits` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Habit) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfHabit = object : EntityDeleteOrUpdateAdapter<Habit>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `habits` SET `id` = ?,`userId` = ?,`name` = ?,`description` = ?,`category` = ?,`targetDays` = ?,`frequency` = ?,`reminderTime` = ?,`reminderEnabled` = ?,`currentStreak` = ?,`longestStreak` = ?,`completedDays` = ?,`skippedDays` = ?,`isActive` = ?,`isPaused` = ?,`isCompleted` = ?,`createdAt` = ?,`updatedAt` = ?,`completedAt` = ?,`color` = ?,`icon` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Habit) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        statement.bindText(3, entity.name)
        statement.bindText(4, entity.description)
        val _tmp: String = __converters.fromHabitCategory(entity.category)
        statement.bindText(5, _tmp)
        statement.bindLong(6, entity.targetDays.toLong())
        val _tmp_1: String = __converters.fromHabitFrequency(entity.frequency)
        statement.bindText(7, _tmp_1)
        val _tmpReminderTime: String? = entity.reminderTime
        if (_tmpReminderTime == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpReminderTime)
        }
        val _tmp_2: Int = if (entity.reminderEnabled) 1 else 0
        statement.bindLong(9, _tmp_2.toLong())
        statement.bindLong(10, entity.currentStreak.toLong())
        statement.bindLong(11, entity.longestStreak.toLong())
        statement.bindLong(12, entity.completedDays.toLong())
        statement.bindLong(13, entity.skippedDays.toLong())
        val _tmp_3: Int = if (entity.isActive) 1 else 0
        statement.bindLong(14, _tmp_3.toLong())
        val _tmp_4: Int = if (entity.isPaused) 1 else 0
        statement.bindLong(15, _tmp_4.toLong())
        val _tmp_5: Int = if (entity.isCompleted) 1 else 0
        statement.bindLong(16, _tmp_5.toLong())
        val _tmp_6: Long? = __converters.dateToTimestamp(entity.createdAt)
        if (_tmp_6 == null) {
          statement.bindNull(17)
        } else {
          statement.bindLong(17, _tmp_6)
        }
        val _tmp_7: Long? = __converters.dateToTimestamp(entity.updatedAt)
        if (_tmp_7 == null) {
          statement.bindNull(18)
        } else {
          statement.bindLong(18, _tmp_7)
        }
        val _tmpCompletedAt: Date? = entity.completedAt
        val _tmp_8: Long? = __converters.dateToTimestamp(_tmpCompletedAt)
        if (_tmp_8 == null) {
          statement.bindNull(19)
        } else {
          statement.bindLong(19, _tmp_8)
        }
        statement.bindText(20, entity.color)
        val _tmpIcon: String? = entity.icon
        if (_tmpIcon == null) {
          statement.bindNull(21)
        } else {
          statement.bindText(21, _tmpIcon)
        }
        statement.bindLong(22, entity.id)
      }
    }
  }

  public override suspend fun insertHabit(habit: Habit): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfHabit.insertAndReturnId(_connection, habit)
    _result
  }

  public override suspend fun deleteHabit(habit: Habit): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfHabit.handle(_connection, habit)
  }

  public override suspend fun updateHabit(habit: Habit): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfHabit.handle(_connection, habit)
  }

  public override fun getActiveHabitsByUser(userId: Long): Flow<List<Habit>> {
    val _sql: String = "SELECT * FROM habits WHERE userId = ? AND isActive = 1 ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("habits")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfTargetDays: Int = getColumnIndexOrThrow(_stmt, "targetDays")
        val _columnIndexOfFrequency: Int = getColumnIndexOrThrow(_stmt, "frequency")
        val _columnIndexOfReminderTime: Int = getColumnIndexOrThrow(_stmt, "reminderTime")
        val _columnIndexOfReminderEnabled: Int = getColumnIndexOrThrow(_stmt, "reminderEnabled")
        val _columnIndexOfCurrentStreak: Int = getColumnIndexOrThrow(_stmt, "currentStreak")
        val _columnIndexOfLongestStreak: Int = getColumnIndexOrThrow(_stmt, "longestStreak")
        val _columnIndexOfCompletedDays: Int = getColumnIndexOrThrow(_stmt, "completedDays")
        val _columnIndexOfSkippedDays: Int = getColumnIndexOrThrow(_stmt, "skippedDays")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfIsPaused: Int = getColumnIndexOrThrow(_stmt, "isPaused")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _columnIndexOfCompletedAt: Int = getColumnIndexOrThrow(_stmt, "completedAt")
        val _columnIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _result: MutableList<Habit> = mutableListOf()
        while (_stmt.step()) {
          val _item: Habit
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: HabitCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toHabitCategory(_tmp)
          val _tmpTargetDays: Int
          _tmpTargetDays = _stmt.getLong(_columnIndexOfTargetDays).toInt()
          val _tmpFrequency: HabitFrequency
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfFrequency)
          _tmpFrequency = __converters.toHabitFrequency(_tmp_1)
          val _tmpReminderTime: String?
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime)
          }
          val _tmpReminderEnabled: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfReminderEnabled).toInt()
          _tmpReminderEnabled = _tmp_2 != 0
          val _tmpCurrentStreak: Int
          _tmpCurrentStreak = _stmt.getLong(_columnIndexOfCurrentStreak).toInt()
          val _tmpLongestStreak: Int
          _tmpLongestStreak = _stmt.getLong(_columnIndexOfLongestStreak).toInt()
          val _tmpCompletedDays: Int
          _tmpCompletedDays = _stmt.getLong(_columnIndexOfCompletedDays).toInt()
          val _tmpSkippedDays: Int
          _tmpSkippedDays = _stmt.getLong(_columnIndexOfSkippedDays).toInt()
          val _tmpIsActive: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_3 != 0
          val _tmpIsPaused: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsPaused).toInt()
          _tmpIsPaused = _tmp_4 != 0
          val _tmpIsCompleted: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_5 != 0
          val _tmpCreatedAt: Date
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_7: Date? = __converters.fromTimestamp(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_7
          }
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
          val _tmpCompletedAt: Date?
          val _tmp_10: Long?
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_10 = null
          } else {
            _tmp_10 = _stmt.getLong(_columnIndexOfCompletedAt)
          }
          _tmpCompletedAt = __converters.fromTimestamp(_tmp_10)
          val _tmpColor: String
          _tmpColor = _stmt.getText(_columnIndexOfColor)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          _item = Habit(_tmpId,_tmpUserId,_tmpName,_tmpDescription,_tmpCategory,_tmpTargetDays,_tmpFrequency,_tmpReminderTime,_tmpReminderEnabled,_tmpCurrentStreak,_tmpLongestStreak,_tmpCompletedDays,_tmpSkippedDays,_tmpIsActive,_tmpIsPaused,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpColor,_tmpIcon)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllHabitsByUser(userId: Long): Flow<List<Habit>> {
    val _sql: String = "SELECT * FROM habits WHERE userId = ? ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("habits")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfTargetDays: Int = getColumnIndexOrThrow(_stmt, "targetDays")
        val _columnIndexOfFrequency: Int = getColumnIndexOrThrow(_stmt, "frequency")
        val _columnIndexOfReminderTime: Int = getColumnIndexOrThrow(_stmt, "reminderTime")
        val _columnIndexOfReminderEnabled: Int = getColumnIndexOrThrow(_stmt, "reminderEnabled")
        val _columnIndexOfCurrentStreak: Int = getColumnIndexOrThrow(_stmt, "currentStreak")
        val _columnIndexOfLongestStreak: Int = getColumnIndexOrThrow(_stmt, "longestStreak")
        val _columnIndexOfCompletedDays: Int = getColumnIndexOrThrow(_stmt, "completedDays")
        val _columnIndexOfSkippedDays: Int = getColumnIndexOrThrow(_stmt, "skippedDays")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfIsPaused: Int = getColumnIndexOrThrow(_stmt, "isPaused")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _columnIndexOfCompletedAt: Int = getColumnIndexOrThrow(_stmt, "completedAt")
        val _columnIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _result: MutableList<Habit> = mutableListOf()
        while (_stmt.step()) {
          val _item: Habit
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: HabitCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toHabitCategory(_tmp)
          val _tmpTargetDays: Int
          _tmpTargetDays = _stmt.getLong(_columnIndexOfTargetDays).toInt()
          val _tmpFrequency: HabitFrequency
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfFrequency)
          _tmpFrequency = __converters.toHabitFrequency(_tmp_1)
          val _tmpReminderTime: String?
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime)
          }
          val _tmpReminderEnabled: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfReminderEnabled).toInt()
          _tmpReminderEnabled = _tmp_2 != 0
          val _tmpCurrentStreak: Int
          _tmpCurrentStreak = _stmt.getLong(_columnIndexOfCurrentStreak).toInt()
          val _tmpLongestStreak: Int
          _tmpLongestStreak = _stmt.getLong(_columnIndexOfLongestStreak).toInt()
          val _tmpCompletedDays: Int
          _tmpCompletedDays = _stmt.getLong(_columnIndexOfCompletedDays).toInt()
          val _tmpSkippedDays: Int
          _tmpSkippedDays = _stmt.getLong(_columnIndexOfSkippedDays).toInt()
          val _tmpIsActive: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_3 != 0
          val _tmpIsPaused: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsPaused).toInt()
          _tmpIsPaused = _tmp_4 != 0
          val _tmpIsCompleted: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_5 != 0
          val _tmpCreatedAt: Date
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_7: Date? = __converters.fromTimestamp(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_7
          }
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
          val _tmpCompletedAt: Date?
          val _tmp_10: Long?
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_10 = null
          } else {
            _tmp_10 = _stmt.getLong(_columnIndexOfCompletedAt)
          }
          _tmpCompletedAt = __converters.fromTimestamp(_tmp_10)
          val _tmpColor: String
          _tmpColor = _stmt.getText(_columnIndexOfColor)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          _item = Habit(_tmpId,_tmpUserId,_tmpName,_tmpDescription,_tmpCategory,_tmpTargetDays,_tmpFrequency,_tmpReminderTime,_tmpReminderEnabled,_tmpCurrentStreak,_tmpLongestStreak,_tmpCompletedDays,_tmpSkippedDays,_tmpIsActive,_tmpIsPaused,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpColor,_tmpIcon)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getHabitById(habitId: Long): Habit? {
    val _sql: String = "SELECT * FROM habits WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, habitId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfTargetDays: Int = getColumnIndexOrThrow(_stmt, "targetDays")
        val _columnIndexOfFrequency: Int = getColumnIndexOrThrow(_stmt, "frequency")
        val _columnIndexOfReminderTime: Int = getColumnIndexOrThrow(_stmt, "reminderTime")
        val _columnIndexOfReminderEnabled: Int = getColumnIndexOrThrow(_stmt, "reminderEnabled")
        val _columnIndexOfCurrentStreak: Int = getColumnIndexOrThrow(_stmt, "currentStreak")
        val _columnIndexOfLongestStreak: Int = getColumnIndexOrThrow(_stmt, "longestStreak")
        val _columnIndexOfCompletedDays: Int = getColumnIndexOrThrow(_stmt, "completedDays")
        val _columnIndexOfSkippedDays: Int = getColumnIndexOrThrow(_stmt, "skippedDays")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfIsPaused: Int = getColumnIndexOrThrow(_stmt, "isPaused")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _columnIndexOfCompletedAt: Int = getColumnIndexOrThrow(_stmt, "completedAt")
        val _columnIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _result: Habit?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: HabitCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toHabitCategory(_tmp)
          val _tmpTargetDays: Int
          _tmpTargetDays = _stmt.getLong(_columnIndexOfTargetDays).toInt()
          val _tmpFrequency: HabitFrequency
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfFrequency)
          _tmpFrequency = __converters.toHabitFrequency(_tmp_1)
          val _tmpReminderTime: String?
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime)
          }
          val _tmpReminderEnabled: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfReminderEnabled).toInt()
          _tmpReminderEnabled = _tmp_2 != 0
          val _tmpCurrentStreak: Int
          _tmpCurrentStreak = _stmt.getLong(_columnIndexOfCurrentStreak).toInt()
          val _tmpLongestStreak: Int
          _tmpLongestStreak = _stmt.getLong(_columnIndexOfLongestStreak).toInt()
          val _tmpCompletedDays: Int
          _tmpCompletedDays = _stmt.getLong(_columnIndexOfCompletedDays).toInt()
          val _tmpSkippedDays: Int
          _tmpSkippedDays = _stmt.getLong(_columnIndexOfSkippedDays).toInt()
          val _tmpIsActive: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_3 != 0
          val _tmpIsPaused: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsPaused).toInt()
          _tmpIsPaused = _tmp_4 != 0
          val _tmpIsCompleted: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_5 != 0
          val _tmpCreatedAt: Date
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_7: Date? = __converters.fromTimestamp(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_7
          }
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
          val _tmpCompletedAt: Date?
          val _tmp_10: Long?
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_10 = null
          } else {
            _tmp_10 = _stmt.getLong(_columnIndexOfCompletedAt)
          }
          _tmpCompletedAt = __converters.fromTimestamp(_tmp_10)
          val _tmpColor: String
          _tmpColor = _stmt.getText(_columnIndexOfColor)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          _result = Habit(_tmpId,_tmpUserId,_tmpName,_tmpDescription,_tmpCategory,_tmpTargetDays,_tmpFrequency,_tmpReminderTime,_tmpReminderEnabled,_tmpCurrentStreak,_tmpLongestStreak,_tmpCompletedDays,_tmpSkippedDays,_tmpIsActive,_tmpIsPaused,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpColor,_tmpIcon)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getHabitsWithReminders(userId: Long): List<Habit> {
    val _sql: String = "SELECT * FROM habits WHERE userId = ? AND reminderEnabled = 1 AND reminderTime IS NOT NULL"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfTargetDays: Int = getColumnIndexOrThrow(_stmt, "targetDays")
        val _columnIndexOfFrequency: Int = getColumnIndexOrThrow(_stmt, "frequency")
        val _columnIndexOfReminderTime: Int = getColumnIndexOrThrow(_stmt, "reminderTime")
        val _columnIndexOfReminderEnabled: Int = getColumnIndexOrThrow(_stmt, "reminderEnabled")
        val _columnIndexOfCurrentStreak: Int = getColumnIndexOrThrow(_stmt, "currentStreak")
        val _columnIndexOfLongestStreak: Int = getColumnIndexOrThrow(_stmt, "longestStreak")
        val _columnIndexOfCompletedDays: Int = getColumnIndexOrThrow(_stmt, "completedDays")
        val _columnIndexOfSkippedDays: Int = getColumnIndexOrThrow(_stmt, "skippedDays")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfIsPaused: Int = getColumnIndexOrThrow(_stmt, "isPaused")
        val _columnIndexOfIsCompleted: Int = getColumnIndexOrThrow(_stmt, "isCompleted")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _columnIndexOfCompletedAt: Int = getColumnIndexOrThrow(_stmt, "completedAt")
        val _columnIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _result: MutableList<Habit> = mutableListOf()
        while (_stmt.step()) {
          val _item: Habit
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: HabitCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toHabitCategory(_tmp)
          val _tmpTargetDays: Int
          _tmpTargetDays = _stmt.getLong(_columnIndexOfTargetDays).toInt()
          val _tmpFrequency: HabitFrequency
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfFrequency)
          _tmpFrequency = __converters.toHabitFrequency(_tmp_1)
          val _tmpReminderTime: String?
          if (_stmt.isNull(_columnIndexOfReminderTime)) {
            _tmpReminderTime = null
          } else {
            _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime)
          }
          val _tmpReminderEnabled: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfReminderEnabled).toInt()
          _tmpReminderEnabled = _tmp_2 != 0
          val _tmpCurrentStreak: Int
          _tmpCurrentStreak = _stmt.getLong(_columnIndexOfCurrentStreak).toInt()
          val _tmpLongestStreak: Int
          _tmpLongestStreak = _stmt.getLong(_columnIndexOfLongestStreak).toInt()
          val _tmpCompletedDays: Int
          _tmpCompletedDays = _stmt.getLong(_columnIndexOfCompletedDays).toInt()
          val _tmpSkippedDays: Int
          _tmpSkippedDays = _stmt.getLong(_columnIndexOfSkippedDays).toInt()
          val _tmpIsActive: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_3 != 0
          val _tmpIsPaused: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsPaused).toInt()
          _tmpIsPaused = _tmp_4 != 0
          val _tmpIsCompleted: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsCompleted).toInt()
          _tmpIsCompleted = _tmp_5 != 0
          val _tmpCreatedAt: Date
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_7: Date? = __converters.fromTimestamp(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_7
          }
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
          val _tmpCompletedAt: Date?
          val _tmp_10: Long?
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_10 = null
          } else {
            _tmp_10 = _stmt.getLong(_columnIndexOfCompletedAt)
          }
          _tmpCompletedAt = __converters.fromTimestamp(_tmp_10)
          val _tmpColor: String
          _tmpColor = _stmt.getText(_columnIndexOfColor)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          _item = Habit(_tmpId,_tmpUserId,_tmpName,_tmpDescription,_tmpCategory,_tmpTargetDays,_tmpFrequency,_tmpReminderTime,_tmpReminderEnabled,_tmpCurrentStreak,_tmpLongestStreak,_tmpCompletedDays,_tmpSkippedDays,_tmpIsActive,_tmpIsPaused,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpCompletedAt,_tmpColor,_tmpIcon)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getActiveHabitCount(userId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM habits WHERE userId = ? AND isActive = 1"
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

  public override suspend fun getCompletedHabitCount(userId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM habits WHERE userId = ? AND isCompleted = 1"
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

  public override suspend fun updateStreak(habitId: Long, streak: Int) {
    val _sql: String = "UPDATE habits SET currentStreak = ?, longestStreak = MAX(longestStreak, ?) WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, streak.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, streak.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, habitId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun incrementCompletedDays(habitId: Long) {
    val _sql: String = "UPDATE habits SET completedDays = completedDays + 1 WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, habitId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun incrementSkippedDays(habitId: Long) {
    val _sql: String = "UPDATE habits SET skippedDays = skippedDays + 1 WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, habitId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun setHabitActive(habitId: Long, isActive: Boolean) {
    val _sql: String = "UPDATE habits SET isActive = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (isActive) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, habitId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun setHabitPaused(habitId: Long, isPaused: Boolean) {
    val _sql: String = "UPDATE habits SET isPaused = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (isPaused) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, habitId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun markHabitCompleted(habitId: Long, completedAt: Date) {
    val _sql: String = "UPDATE habits SET isCompleted = 1, completedAt = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Long? = __converters.dateToTimestamp(completedAt)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 2
        _stmt.bindLong(_argIndex, habitId)
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
