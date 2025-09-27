package com.campus.fitintent.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.campus.fitintent.database.Converters
import com.campus.fitintent.models.Streak
import com.campus.fitintent.models.StreakType
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
public class StreakDao_Impl(
  __db: RoomDatabase,
) : StreakDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfStreak: EntityInsertAdapter<Streak>

  private val __converters: Converters = Converters()

  private val __updateAdapterOfStreak: EntityDeleteOrUpdateAdapter<Streak>
  init {
    this.__db = __db
    this.__insertAdapterOfStreak = object : EntityInsertAdapter<Streak>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `streaks` (`id`,`userId`,`type`,`currentStreak`,`longestStreak`,`lastActivityDate`,`startDate`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Streak) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        val _tmp: String = __converters.fromStreakType(entity.type)
        statement.bindText(3, _tmp)
        statement.bindLong(4, entity.currentStreak.toLong())
        statement.bindLong(5, entity.longestStreak.toLong())
        val _tmpLastActivityDate: Date? = entity.lastActivityDate
        val _tmp_1: Long? = __converters.dateToTimestamp(_tmpLastActivityDate)
        if (_tmp_1 == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmp_1)
        }
        val _tmp_2: Long? = __converters.dateToTimestamp(entity.startDate)
        if (_tmp_2 == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmp_2)
        }
        val _tmp_3: Int = if (entity.isActive) 1 else 0
        statement.bindLong(8, _tmp_3.toLong())
      }
    }
    this.__updateAdapterOfStreak = object : EntityDeleteOrUpdateAdapter<Streak>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `streaks` SET `id` = ?,`userId` = ?,`type` = ?,`currentStreak` = ?,`longestStreak` = ?,`lastActivityDate` = ?,`startDate` = ?,`isActive` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Streak) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        val _tmp: String = __converters.fromStreakType(entity.type)
        statement.bindText(3, _tmp)
        statement.bindLong(4, entity.currentStreak.toLong())
        statement.bindLong(5, entity.longestStreak.toLong())
        val _tmpLastActivityDate: Date? = entity.lastActivityDate
        val _tmp_1: Long? = __converters.dateToTimestamp(_tmpLastActivityDate)
        if (_tmp_1 == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmp_1)
        }
        val _tmp_2: Long? = __converters.dateToTimestamp(entity.startDate)
        if (_tmp_2 == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmp_2)
        }
        val _tmp_3: Int = if (entity.isActive) 1 else 0
        statement.bindLong(8, _tmp_3.toLong())
        statement.bindLong(9, entity.id)
      }
    }
  }

  public override suspend fun insertStreak(streak: Streak): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfStreak.insertAndReturnId(_connection, streak)
    _result
  }

  public override suspend fun updateStreak(streak: Streak): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfStreak.handle(_connection, streak)
  }

  public override suspend fun getActiveStreak(userId: Long, type: StreakType): Streak? {
    val _sql: String = "SELECT * FROM streaks WHERE userId = ? AND type = ? AND isActive = 1 LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        val _tmp: String = __converters.fromStreakType(type)
        _stmt.bindText(_argIndex, _tmp)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfCurrentStreak: Int = getColumnIndexOrThrow(_stmt, "currentStreak")
        val _columnIndexOfLongestStreak: Int = getColumnIndexOrThrow(_stmt, "longestStreak")
        val _columnIndexOfLastActivityDate: Int = getColumnIndexOrThrow(_stmt, "lastActivityDate")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _result: Streak?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpType: StreakType
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfType)
          _tmpType = __converters.toStreakType(_tmp_1)
          val _tmpCurrentStreak: Int
          _tmpCurrentStreak = _stmt.getLong(_columnIndexOfCurrentStreak).toInt()
          val _tmpLongestStreak: Int
          _tmpLongestStreak = _stmt.getLong(_columnIndexOfLongestStreak).toInt()
          val _tmpLastActivityDate: Date?
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfLastActivityDate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfLastActivityDate)
          }
          _tmpLastActivityDate = __converters.fromTimestamp(_tmp_2)
          val _tmpStartDate: Date
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfStartDate)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfStartDate)
          }
          val _tmp_4: Date? = __converters.fromTimestamp(_tmp_3)
          if (_tmp_4 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpStartDate = _tmp_4
          }
          val _tmpIsActive: Boolean
          val _tmp_5: Int
          _tmp_5 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_5 != 0
          _result = Streak(_tmpId,_tmpUserId,_tmpType,_tmpCurrentStreak,_tmpLongestStreak,_tmpLastActivityDate,_tmpStartDate,_tmpIsActive)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getActiveStreaksByUser(userId: Long): Flow<List<Streak>> {
    val _sql: String = "SELECT * FROM streaks WHERE userId = ? AND isActive = 1"
    return createFlow(__db, false, arrayOf("streaks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfCurrentStreak: Int = getColumnIndexOrThrow(_stmt, "currentStreak")
        val _columnIndexOfLongestStreak: Int = getColumnIndexOrThrow(_stmt, "longestStreak")
        val _columnIndexOfLastActivityDate: Int = getColumnIndexOrThrow(_stmt, "lastActivityDate")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _result: MutableList<Streak> = mutableListOf()
        while (_stmt.step()) {
          val _item: Streak
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpType: StreakType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfType)
          _tmpType = __converters.toStreakType(_tmp)
          val _tmpCurrentStreak: Int
          _tmpCurrentStreak = _stmt.getLong(_columnIndexOfCurrentStreak).toInt()
          val _tmpLongestStreak: Int
          _tmpLongestStreak = _stmt.getLong(_columnIndexOfLongestStreak).toInt()
          val _tmpLastActivityDate: Date?
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfLastActivityDate)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfLastActivityDate)
          }
          _tmpLastActivityDate = __converters.fromTimestamp(_tmp_1)
          val _tmpStartDate: Date
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfStartDate)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfStartDate)
          }
          val _tmp_3: Date? = __converters.fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpStartDate = _tmp_3
          }
          val _tmpIsActive: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_4 != 0
          _item = Streak(_tmpId,_tmpUserId,_tmpType,_tmpCurrentStreak,_tmpLongestStreak,_tmpLastActivityDate,_tmpStartDate,_tmpIsActive)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLongestStreakByType(userId: Long, type: StreakType): Int? {
    val _sql: String = "SELECT MAX(longestStreak) FROM streaks WHERE userId = ? AND type = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        val _tmp: String = __converters.fromStreakType(type)
        _stmt.bindText(_argIndex, _tmp)
        val _result: Int?
        if (_stmt.step()) {
          val _tmp_1: Int?
          if (_stmt.isNull(0)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(0).toInt()
          }
          _result = _tmp_1
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTotalActiveStreakDays(userId: Long): Int? {
    val _sql: String = "SELECT SUM(currentStreak) FROM streaks WHERE userId = ? AND isActive = 1"
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

  public override suspend fun updateStreakCount(
    streakId: Long,
    current: Int,
    date: Date,
  ) {
    val _sql: String = """
        |
        |        UPDATE streaks
        |        SET currentStreak = ?,
        |            longestStreak = MAX(longestStreak, ?),
        |            lastActivityDate = ?
        |        WHERE id = ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, current.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, current.toLong())
        _argIndex = 3
        val _tmp: Long? = __converters.dateToTimestamp(date)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        _argIndex = 4
        _stmt.bindLong(_argIndex, streakId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun breakStreak(streakId: Long) {
    val _sql: String = "UPDATE streaks SET currentStreak = 0, isActive = 0 WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, streakId)
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
