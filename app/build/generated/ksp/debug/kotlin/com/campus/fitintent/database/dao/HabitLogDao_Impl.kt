package com.campus.fitintent.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.appendPlaceholders
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.campus.fitintent.database.Converters
import com.campus.fitintent.models.HabitLog
import com.campus.fitintent.models.HabitLogStatus
import java.util.Date
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlin.text.StringBuilder
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class HabitLogDao_Impl(
  __db: RoomDatabase,
) : HabitLogDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfHabitLog: EntityInsertAdapter<HabitLog>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfHabitLog: EntityDeleteOrUpdateAdapter<HabitLog>

  private val __updateAdapterOfHabitLog: EntityDeleteOrUpdateAdapter<HabitLog>
  init {
    this.__db = __db
    this.__insertAdapterOfHabitLog = object : EntityInsertAdapter<HabitLog>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `habit_logs` (`id`,`habitId`,`date`,`status`,`notes`,`completedAt`,`skippedReason`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: HabitLog) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.habitId)
        val _tmp: Long? = __converters.dateToTimestamp(entity.date)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmp)
        }
        val _tmp_1: String = __converters.fromHabitLogStatus(entity.status)
        statement.bindText(4, _tmp_1)
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpNotes)
        }
        val _tmpCompletedAt: Date? = entity.completedAt
        val _tmp_2: Long? = __converters.dateToTimestamp(_tmpCompletedAt)
        if (_tmp_2 == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmp_2)
        }
        val _tmpSkippedReason: String? = entity.skippedReason
        if (_tmpSkippedReason == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpSkippedReason)
        }
      }
    }
    this.__deleteAdapterOfHabitLog = object : EntityDeleteOrUpdateAdapter<HabitLog>() {
      protected override fun createQuery(): String = "DELETE FROM `habit_logs` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: HabitLog) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfHabitLog = object : EntityDeleteOrUpdateAdapter<HabitLog>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `habit_logs` SET `id` = ?,`habitId` = ?,`date` = ?,`status` = ?,`notes` = ?,`completedAt` = ?,`skippedReason` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: HabitLog) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.habitId)
        val _tmp: Long? = __converters.dateToTimestamp(entity.date)
        if (_tmp == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmp)
        }
        val _tmp_1: String = __converters.fromHabitLogStatus(entity.status)
        statement.bindText(4, _tmp_1)
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpNotes)
        }
        val _tmpCompletedAt: Date? = entity.completedAt
        val _tmp_2: Long? = __converters.dateToTimestamp(_tmpCompletedAt)
        if (_tmp_2 == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmp_2)
        }
        val _tmpSkippedReason: String? = entity.skippedReason
        if (_tmpSkippedReason == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpSkippedReason)
        }
        statement.bindLong(8, entity.id)
      }
    }
  }

  public override suspend fun insertLog(log: HabitLog): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfHabitLog.insertAndReturnId(_connection, log)
    _result
  }

  public override suspend fun deleteLog(log: HabitLog): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfHabitLog.handle(_connection, log)
  }

  public override suspend fun updateLog(log: HabitLog): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfHabitLog.handle(_connection, log)
  }

  public override fun getLogsByHabit(habitId: Long): Flow<List<HabitLog>> {
    val _sql: String = "SELECT * FROM habit_logs WHERE habitId = ? ORDER BY date DESC"
    return createFlow(__db, false, arrayOf("habit_logs")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, habitId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfHabitId: Int = getColumnIndexOrThrow(_stmt, "habitId")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfCompletedAt: Int = getColumnIndexOrThrow(_stmt, "completedAt")
        val _columnIndexOfSkippedReason: Int = getColumnIndexOrThrow(_stmt, "skippedReason")
        val _result: MutableList<HabitLog> = mutableListOf()
        while (_stmt.step()) {
          val _item: HabitLog
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpHabitId: Long
          _tmpHabitId = _stmt.getLong(_columnIndexOfHabitId)
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
          val _tmpStatus: HabitLogStatus
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfStatus)
          _tmpStatus = __converters.toHabitLogStatus(_tmp_2)
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          val _tmpCompletedAt: Date?
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfCompletedAt)
          }
          _tmpCompletedAt = __converters.fromTimestamp(_tmp_3)
          val _tmpSkippedReason: String?
          if (_stmt.isNull(_columnIndexOfSkippedReason)) {
            _tmpSkippedReason = null
          } else {
            _tmpSkippedReason = _stmt.getText(_columnIndexOfSkippedReason)
          }
          _item = HabitLog(_tmpId,_tmpHabitId,_tmpDate,_tmpStatus,_tmpNotes,_tmpCompletedAt,_tmpSkippedReason)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLogByHabitAndDate(habitId: Long, date: Date): HabitLog? {
    val _sql: String = "SELECT * FROM habit_logs WHERE habitId = ? AND date = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, habitId)
        _argIndex = 2
        val _tmp: Long? = __converters.dateToTimestamp(date)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfHabitId: Int = getColumnIndexOrThrow(_stmt, "habitId")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfCompletedAt: Int = getColumnIndexOrThrow(_stmt, "completedAt")
        val _columnIndexOfSkippedReason: Int = getColumnIndexOrThrow(_stmt, "skippedReason")
        val _result: HabitLog?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpHabitId: Long
          _tmpHabitId = _stmt.getLong(_columnIndexOfHabitId)
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
          val _tmpStatus: HabitLogStatus
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfStatus)
          _tmpStatus = __converters.toHabitLogStatus(_tmp_3)
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          val _tmpCompletedAt: Date?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfCompletedAt)
          }
          _tmpCompletedAt = __converters.fromTimestamp(_tmp_4)
          val _tmpSkippedReason: String?
          if (_stmt.isNull(_columnIndexOfSkippedReason)) {
            _tmpSkippedReason = null
          } else {
            _tmpSkippedReason = _stmt.getText(_columnIndexOfSkippedReason)
          }
          _result = HabitLog(_tmpId,_tmpHabitId,_tmpDate,_tmpStatus,_tmpNotes,_tmpCompletedAt,_tmpSkippedReason)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLogsForHabitsOnDate(habitIds: List<Long>, date: Date): List<HabitLog> {
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT * FROM habit_logs WHERE habitId IN (")
    val _inputSize: Int = habitIds.size
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(") AND date = ")
    _stringBuilder.append("?")
    val _sql: String = _stringBuilder.toString()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        for (_item: Long in habitIds) {
          _stmt.bindLong(_argIndex, _item)
          _argIndex++
        }
        _argIndex = 1 + _inputSize
        val _tmp: Long? = __converters.dateToTimestamp(date)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfHabitId: Int = getColumnIndexOrThrow(_stmt, "habitId")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfCompletedAt: Int = getColumnIndexOrThrow(_stmt, "completedAt")
        val _columnIndexOfSkippedReason: Int = getColumnIndexOrThrow(_stmt, "skippedReason")
        val _result: MutableList<HabitLog> = mutableListOf()
        while (_stmt.step()) {
          val _item_1: HabitLog
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpHabitId: Long
          _tmpHabitId = _stmt.getLong(_columnIndexOfHabitId)
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
          val _tmpStatus: HabitLogStatus
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfStatus)
          _tmpStatus = __converters.toHabitLogStatus(_tmp_3)
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          val _tmpCompletedAt: Date?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfCompletedAt)
          }
          _tmpCompletedAt = __converters.fromTimestamp(_tmp_4)
          val _tmpSkippedReason: String?
          if (_stmt.isNull(_columnIndexOfSkippedReason)) {
            _tmpSkippedReason = null
          } else {
            _tmpSkippedReason = _stmt.getText(_columnIndexOfSkippedReason)
          }
          _item_1 = HabitLog(_tmpId,_tmpHabitId,_tmpDate,_tmpStatus,_tmpNotes,_tmpCompletedAt,_tmpSkippedReason)
          _result.add(_item_1)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getCompletedCount(habitId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM habit_logs WHERE habitId = ? AND status = 'COMPLETED'"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, habitId)
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

  public override suspend fun getSkippedCount(habitId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM habit_logs WHERE habitId = ? AND status = 'SKIPPED'"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, habitId)
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

  public override suspend fun getCompletedCountInRange(
    habitId: Long,
    startDate: Date,
    endDate: Date,
  ): Int {
    val _sql: String = """
        |
        |        SELECT COUNT(*) FROM habit_logs
        |        WHERE habitId = ?
        |        AND date BETWEEN ? AND ?
        |        AND status = 'COMPLETED'
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, habitId)
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
        val _result: Int
        if (_stmt.step()) {
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(0).toInt()
          _result = _tmp_2
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getRecentLogs(habitId: Long, startDate: Date): List<HabitLog> {
    val _sql: String = """
        |
        |        SELECT * FROM habit_logs
        |        WHERE habitId = ?
        |        AND date >= ?
        |        ORDER BY date ASC
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, habitId)
        _argIndex = 2
        val _tmp: Long? = __converters.dateToTimestamp(startDate)
        if (_tmp == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp)
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfHabitId: Int = getColumnIndexOrThrow(_stmt, "habitId")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfCompletedAt: Int = getColumnIndexOrThrow(_stmt, "completedAt")
        val _columnIndexOfSkippedReason: Int = getColumnIndexOrThrow(_stmt, "skippedReason")
        val _result: MutableList<HabitLog> = mutableListOf()
        while (_stmt.step()) {
          val _item: HabitLog
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpHabitId: Long
          _tmpHabitId = _stmt.getLong(_columnIndexOfHabitId)
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
          val _tmpStatus: HabitLogStatus
          val _tmp_3: String
          _tmp_3 = _stmt.getText(_columnIndexOfStatus)
          _tmpStatus = __converters.toHabitLogStatus(_tmp_3)
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
          }
          val _tmpCompletedAt: Date?
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfCompletedAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfCompletedAt)
          }
          _tmpCompletedAt = __converters.fromTimestamp(_tmp_4)
          val _tmpSkippedReason: String?
          if (_stmt.isNull(_columnIndexOfSkippedReason)) {
            _tmpSkippedReason = null
          } else {
            _tmpSkippedReason = _stmt.getText(_columnIndexOfSkippedReason)
          }
          _item = HabitLog(_tmpId,_tmpHabitId,_tmpDate,_tmpStatus,_tmpNotes,_tmpCompletedAt,_tmpSkippedReason)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateLogStatus(
    logId: Long,
    status: HabitLogStatus,
    completedAt: Date?,
  ) {
    val _sql: String = "UPDATE habit_logs SET status = ?, completedAt = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String = __converters.fromHabitLogStatus(status)
        _stmt.bindText(_argIndex, _tmp)
        _argIndex = 2
        val _tmp_1: Long? = __converters.dateToTimestamp(completedAt)
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, _tmp_1)
        }
        _argIndex = 3
        _stmt.bindLong(_argIndex, logId)
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
