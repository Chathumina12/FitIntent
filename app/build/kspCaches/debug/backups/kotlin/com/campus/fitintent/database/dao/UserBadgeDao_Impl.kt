package com.campus.fitintent.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.campus.fitintent.database.Converters
import com.campus.fitintent.models.Badge
import com.campus.fitintent.models.BadgeRarity
import com.campus.fitintent.models.BadgeType
import com.campus.fitintent.models.UserBadge
import java.util.Date
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.IllegalArgumentException
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
public class UserBadgeDao_Impl(
  __db: RoomDatabase,
) : UserBadgeDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfUserBadge: EntityInsertAdapter<UserBadge>

  private val __converters: Converters = Converters()

  private val __updateAdapterOfUserBadge: EntityDeleteOrUpdateAdapter<UserBadge>
  init {
    this.__db = __db
    this.__insertAdapterOfUserBadge = object : EntityInsertAdapter<UserBadge>() {
      protected override fun createQuery(): String = "INSERT OR IGNORE INTO `user_badges` (`id`,`userId`,`badgeId`,`currentProgress`,`isUnlocked`,`unlockedAt`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: UserBadge) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        statement.bindLong(3, entity.badgeId)
        statement.bindLong(4, entity.currentProgress.toLong())
        val _tmp: Int = if (entity.isUnlocked) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        val _tmpUnlockedAt: Date? = entity.unlockedAt
        val _tmp_1: Long? = __converters.dateToTimestamp(_tmpUnlockedAt)
        if (_tmp_1 == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmp_1)
        }
        val _tmp_2: Long? = __converters.dateToTimestamp(entity.createdAt)
        if (_tmp_2 == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmp_2)
        }
        val _tmp_3: Long? = __converters.dateToTimestamp(entity.updatedAt)
        if (_tmp_3 == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmp_3)
        }
      }
    }
    this.__updateAdapterOfUserBadge = object : EntityDeleteOrUpdateAdapter<UserBadge>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `user_badges` SET `id` = ?,`userId` = ?,`badgeId` = ?,`currentProgress` = ?,`isUnlocked` = ?,`unlockedAt` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: UserBadge) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        statement.bindLong(3, entity.badgeId)
        statement.bindLong(4, entity.currentProgress.toLong())
        val _tmp: Int = if (entity.isUnlocked) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        val _tmpUnlockedAt: Date? = entity.unlockedAt
        val _tmp_1: Long? = __converters.dateToTimestamp(_tmpUnlockedAt)
        if (_tmp_1 == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmp_1)
        }
        val _tmp_2: Long? = __converters.dateToTimestamp(entity.createdAt)
        if (_tmp_2 == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmp_2)
        }
        val _tmp_3: Long? = __converters.dateToTimestamp(entity.updatedAt)
        if (_tmp_3 == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmp_3)
        }
        statement.bindLong(9, entity.id)
      }
    }
  }

  public override suspend fun insertUserBadge(userBadge: UserBadge): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfUserBadge.insertAndReturnId(_connection, userBadge)
    _result
  }

  public override suspend fun updateUserBadge(userBadge: UserBadge): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfUserBadge.handle(_connection, userBadge)
  }

  public override fun getUserBadgesByUser(userId: Long): Flow<List<UserBadge>> {
    val _sql: String = "SELECT * FROM user_badges WHERE userId = ? ORDER BY unlockedAt DESC"
    return createFlow(__db, false, arrayOf("user_badges")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfBadgeId: Int = getColumnIndexOrThrow(_stmt, "badgeId")
        val _columnIndexOfCurrentProgress: Int = getColumnIndexOrThrow(_stmt, "currentProgress")
        val _columnIndexOfIsUnlocked: Int = getColumnIndexOrThrow(_stmt, "isUnlocked")
        val _columnIndexOfUnlockedAt: Int = getColumnIndexOrThrow(_stmt, "unlockedAt")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<UserBadge> = mutableListOf()
        while (_stmt.step()) {
          val _item: UserBadge
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpBadgeId: Long
          _tmpBadgeId = _stmt.getLong(_columnIndexOfBadgeId)
          val _tmpCurrentProgress: Int
          _tmpCurrentProgress = _stmt.getLong(_columnIndexOfCurrentProgress).toInt()
          val _tmpIsUnlocked: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsUnlocked).toInt()
          _tmpIsUnlocked = _tmp != 0
          val _tmpUnlockedAt: Date?
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfUnlockedAt)
          }
          _tmpUnlockedAt = __converters.fromTimestamp(_tmp_1)
          val _tmpCreatedAt: Date
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_3: Date? = __converters.fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_3
          }
          val _tmpUpdatedAt: Date
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_5: Date? = __converters.fromTimestamp(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_5
          }
          _item = UserBadge(_tmpId,_tmpUserId,_tmpBadgeId,_tmpCurrentProgress,_tmpIsUnlocked,_tmpUnlockedAt,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getBadgesByUser(userId: Long): Flow<List<Badge>> {
    val _sql: String = "SELECT b.* FROM badges b INNER JOIN user_badges ub ON b.id = ub.badgeId WHERE ub.userId = ? ORDER BY ub.unlockedAt DESC"
    return createFlow(__db, false, arrayOf("badges", "user_badges")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfRarity: Int = getColumnIndexOrThrow(_stmt, "rarity")
        val _columnIndexOfIconResId: Int = getColumnIndexOrThrow(_stmt, "iconResId")
        val _columnIndexOfTargetProgress: Int = getColumnIndexOrThrow(_stmt, "targetProgress")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<Badge> = mutableListOf()
        while (_stmt.step()) {
          val _item: Badge
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpType: BadgeType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfType)
          _tmpType = __converters.toBadgeType(_tmp)
          val _tmpRarity: BadgeRarity
          _tmpRarity = __BadgeRarity_stringToEnum(_stmt.getText(_columnIndexOfRarity))
          val _tmpIconResId: String
          _tmpIconResId = _stmt.getText(_columnIndexOfIconResId)
          val _tmpTargetProgress: Int
          _tmpTargetProgress = _stmt.getLong(_columnIndexOfTargetProgress).toInt()
          val _tmpIsActive: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_1 != 0
          val _tmpCreatedAt: Date
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_3: Date? = __converters.fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_3
          }
          val _tmpUpdatedAt: Date
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_5: Date? = __converters.fromTimestamp(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_5
          }
          _item = Badge(_tmpId,_tmpName,_tmpDescription,_tmpType,_tmpRarity,_tmpIconResId,_tmpTargetProgress,_tmpIsActive,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getUserBadgeByBadgeId(userId: Long, badgeId: Long): UserBadge? {
    val _sql: String = "SELECT * FROM user_badges WHERE userId = ? AND badgeId = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, badgeId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfBadgeId: Int = getColumnIndexOrThrow(_stmt, "badgeId")
        val _columnIndexOfCurrentProgress: Int = getColumnIndexOrThrow(_stmt, "currentProgress")
        val _columnIndexOfIsUnlocked: Int = getColumnIndexOrThrow(_stmt, "isUnlocked")
        val _columnIndexOfUnlockedAt: Int = getColumnIndexOrThrow(_stmt, "unlockedAt")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: UserBadge?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpBadgeId: Long
          _tmpBadgeId = _stmt.getLong(_columnIndexOfBadgeId)
          val _tmpCurrentProgress: Int
          _tmpCurrentProgress = _stmt.getLong(_columnIndexOfCurrentProgress).toInt()
          val _tmpIsUnlocked: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsUnlocked).toInt()
          _tmpIsUnlocked = _tmp != 0
          val _tmpUnlockedAt: Date?
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfUnlockedAt)
          }
          _tmpUnlockedAt = __converters.fromTimestamp(_tmp_1)
          val _tmpCreatedAt: Date
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_3: Date? = __converters.fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_3
          }
          val _tmpUpdatedAt: Date
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_5: Date? = __converters.fromTimestamp(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_5
          }
          _result = UserBadge(_tmpId,_tmpUserId,_tmpBadgeId,_tmpCurrentProgress,_tmpIsUnlocked,_tmpUnlockedAt,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getUnlockedBadgeCount(userId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM user_badges WHERE userId = ? AND isUnlocked = 1"
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

  public override suspend fun getGoldBadgeCount(userId: Long): Int {
    val _sql: String = """
        |
        |        SELECT COUNT(*) FROM user_badges ub
        |        INNER JOIN badges b ON ub.badgeId = b.id
        |        WHERE ub.userId = ? AND ub.isUnlocked = 1 AND b.rarity = 'LEGENDARY'
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

  public override suspend fun getLockedUserBadges(userId: Long): List<UserBadge> {
    val _sql: String = "SELECT * FROM user_badges WHERE userId = ? AND isUnlocked = 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _columnIndexOfBadgeId: Int = getColumnIndexOrThrow(_stmt, "badgeId")
        val _columnIndexOfCurrentProgress: Int = getColumnIndexOrThrow(_stmt, "currentProgress")
        val _columnIndexOfIsUnlocked: Int = getColumnIndexOrThrow(_stmt, "isUnlocked")
        val _columnIndexOfUnlockedAt: Int = getColumnIndexOrThrow(_stmt, "unlockedAt")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<UserBadge> = mutableListOf()
        while (_stmt.step()) {
          val _item: UserBadge
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpBadgeId: Long
          _tmpBadgeId = _stmt.getLong(_columnIndexOfBadgeId)
          val _tmpCurrentProgress: Int
          _tmpCurrentProgress = _stmt.getLong(_columnIndexOfCurrentProgress).toInt()
          val _tmpIsUnlocked: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsUnlocked).toInt()
          _tmpIsUnlocked = _tmp != 0
          val _tmpUnlockedAt: Date?
          val _tmp_1: Long?
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfUnlockedAt)
          }
          _tmpUnlockedAt = __converters.fromTimestamp(_tmp_1)
          val _tmpCreatedAt: Date
          val _tmp_2: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_2 = null
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_3: Date? = __converters.fromTimestamp(_tmp_2)
          if (_tmp_3 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_3
          }
          val _tmpUpdatedAt: Date
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_5: Date? = __converters.fromTimestamp(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_5
          }
          _item = UserBadge(_tmpId,_tmpUserId,_tmpBadgeId,_tmpCurrentProgress,_tmpIsUnlocked,_tmpUnlockedAt,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUserBadgesUnlockedInRange(
    userId: Long,
    startDate: Date,
    endDate: Date,
  ): Flow<List<UserBadge>> {
    val _sql: String = """
        |
        |        SELECT * FROM user_badges
        |        WHERE userId = ?
        |        AND unlockedAt BETWEEN ? AND ?
        |        ORDER BY unlockedAt DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("user_badges")) { _connection ->
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
        val _columnIndexOfBadgeId: Int = getColumnIndexOrThrow(_stmt, "badgeId")
        val _columnIndexOfCurrentProgress: Int = getColumnIndexOrThrow(_stmt, "currentProgress")
        val _columnIndexOfIsUnlocked: Int = getColumnIndexOrThrow(_stmt, "isUnlocked")
        val _columnIndexOfUnlockedAt: Int = getColumnIndexOrThrow(_stmt, "unlockedAt")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<UserBadge> = mutableListOf()
        while (_stmt.step()) {
          val _item: UserBadge
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpBadgeId: Long
          _tmpBadgeId = _stmt.getLong(_columnIndexOfBadgeId)
          val _tmpCurrentProgress: Int
          _tmpCurrentProgress = _stmt.getLong(_columnIndexOfCurrentProgress).toInt()
          val _tmpIsUnlocked: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsUnlocked).toInt()
          _tmpIsUnlocked = _tmp_2 != 0
          val _tmpUnlockedAt: Date?
          val _tmp_3: Long?
          if (_stmt.isNull(_columnIndexOfUnlockedAt)) {
            _tmp_3 = null
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfUnlockedAt)
          }
          _tmpUnlockedAt = __converters.fromTimestamp(_tmp_3)
          val _tmpCreatedAt: Date
          val _tmp_4: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_4 = null
          } else {
            _tmp_4 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_5: Date? = __converters.fromTimestamp(_tmp_4)
          if (_tmp_5 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_5
          }
          val _tmpUpdatedAt: Date
          val _tmp_6: Long?
          if (_stmt.isNull(_columnIndexOfUpdatedAt)) {
            _tmp_6 = null
          } else {
            _tmp_6 = _stmt.getLong(_columnIndexOfUpdatedAt)
          }
          val _tmp_7: Date? = __converters.fromTimestamp(_tmp_6)
          if (_tmp_7 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpUpdatedAt = _tmp_7
          }
          _item = UserBadge(_tmpId,_tmpUserId,_tmpBadgeId,_tmpCurrentProgress,_tmpIsUnlocked,_tmpUnlockedAt,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateUserBadgeProgress(userBadgeId: Long, progress: Int) {
    val _sql: String = "UPDATE user_badges SET currentProgress = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, progress.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, userBadgeId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  private fun __BadgeRarity_stringToEnum(_value: String): BadgeRarity = when (_value) {
    "COMMON" -> BadgeRarity.COMMON
    "RARE" -> BadgeRarity.RARE
    "EPIC" -> BadgeRarity.EPIC
    "LEGENDARY" -> BadgeRarity.LEGENDARY
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
