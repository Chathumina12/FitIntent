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
import com.campus.fitintent.models.NutritionCategory
import com.campus.fitintent.models.NutritionTip
import com.campus.fitintent.models.TipDifficulty
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
import kotlin.text.StringBuilder
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class NutritionTipDao_Impl(
  __db: RoomDatabase,
) : NutritionTipDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfNutritionTip: EntityInsertAdapter<NutritionTip>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfNutritionTip: EntityDeleteOrUpdateAdapter<NutritionTip>

  private val __updateAdapterOfNutritionTip: EntityDeleteOrUpdateAdapter<NutritionTip>
  init {
    this.__db = __db
    this.__insertAdapterOfNutritionTip = object : EntityInsertAdapter<NutritionTip>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `nutrition_tips` (`id`,`title`,`description`,`category`,`benefits`,`difficulty`,`icon`,`imageUrl`,`tags`,`isActive`,`priority`,`seasonalTip`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: NutritionTip) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.description)
        val _tmp: String = __converters.fromNutritionCategory(entity.category)
        statement.bindText(4, _tmp)
        val _tmpBenefits: String? = entity.benefits
        if (_tmpBenefits == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpBenefits)
        }
        val _tmp_1: String = __converters.fromTipDifficulty(entity.difficulty)
        statement.bindText(6, _tmp_1)
        val _tmpIcon: String? = entity.icon
        if (_tmpIcon == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpIcon)
        }
        val _tmpImageUrl: String? = entity.imageUrl
        if (_tmpImageUrl == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpImageUrl)
        }
        val _tmpTags: String? = entity.tags
        if (_tmpTags == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmpTags)
        }
        val _tmp_2: Int = if (entity.isActive) 1 else 0
        statement.bindLong(10, _tmp_2.toLong())
        statement.bindLong(11, entity.priority.toLong())
        val _tmp_3: Int = if (entity.seasonalTip) 1 else 0
        statement.bindLong(12, _tmp_3.toLong())
        val _tmp_4: Long? = __converters.dateToTimestamp(entity.createdAt)
        if (_tmp_4 == null) {
          statement.bindNull(13)
        } else {
          statement.bindLong(13, _tmp_4)
        }
        val _tmp_5: Long? = __converters.dateToTimestamp(entity.updatedAt)
        if (_tmp_5 == null) {
          statement.bindNull(14)
        } else {
          statement.bindLong(14, _tmp_5)
        }
      }
    }
    this.__deleteAdapterOfNutritionTip = object : EntityDeleteOrUpdateAdapter<NutritionTip>() {
      protected override fun createQuery(): String = "DELETE FROM `nutrition_tips` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: NutritionTip) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfNutritionTip = object : EntityDeleteOrUpdateAdapter<NutritionTip>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `nutrition_tips` SET `id` = ?,`title` = ?,`description` = ?,`category` = ?,`benefits` = ?,`difficulty` = ?,`icon` = ?,`imageUrl` = ?,`tags` = ?,`isActive` = ?,`priority` = ?,`seasonalTip` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: NutritionTip) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.description)
        val _tmp: String = __converters.fromNutritionCategory(entity.category)
        statement.bindText(4, _tmp)
        val _tmpBenefits: String? = entity.benefits
        if (_tmpBenefits == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpBenefits)
        }
        val _tmp_1: String = __converters.fromTipDifficulty(entity.difficulty)
        statement.bindText(6, _tmp_1)
        val _tmpIcon: String? = entity.icon
        if (_tmpIcon == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpIcon)
        }
        val _tmpImageUrl: String? = entity.imageUrl
        if (_tmpImageUrl == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpImageUrl)
        }
        val _tmpTags: String? = entity.tags
        if (_tmpTags == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmpTags)
        }
        val _tmp_2: Int = if (entity.isActive) 1 else 0
        statement.bindLong(10, _tmp_2.toLong())
        statement.bindLong(11, entity.priority.toLong())
        val _tmp_3: Int = if (entity.seasonalTip) 1 else 0
        statement.bindLong(12, _tmp_3.toLong())
        val _tmp_4: Long? = __converters.dateToTimestamp(entity.createdAt)
        if (_tmp_4 == null) {
          statement.bindNull(13)
        } else {
          statement.bindLong(13, _tmp_4)
        }
        val _tmp_5: Long? = __converters.dateToTimestamp(entity.updatedAt)
        if (_tmp_5 == null) {
          statement.bindNull(14)
        } else {
          statement.bindLong(14, _tmp_5)
        }
        statement.bindLong(15, entity.id)
      }
    }
  }

  public override suspend fun insertTip(tip: NutritionTip): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfNutritionTip.insertAndReturnId(_connection, tip)
    _result
  }

  public override suspend fun insertTips(tips: List<NutritionTip>): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfNutritionTip.insert(_connection, tips)
  }

  public override suspend fun deleteTip(tip: NutritionTip): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfNutritionTip.handle(_connection, tip)
  }

  public override suspend fun updateTip(tip: NutritionTip): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfNutritionTip.handle(_connection, tip)
  }

  public override fun getAllTips(): Flow<List<NutritionTip>> {
    val _sql: String = "SELECT * FROM nutrition_tips ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("nutrition_tips")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfBenefits: Int = getColumnIndexOrThrow(_stmt, "benefits")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfSeasonalTip: Int = getColumnIndexOrThrow(_stmt, "seasonalTip")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<NutritionTip> = mutableListOf()
        while (_stmt.step()) {
          val _item: NutritionTip
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: NutritionCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toNutritionCategory(_tmp)
          val _tmpBenefits: String?
          if (_stmt.isNull(_columnIndexOfBenefits)) {
            _tmpBenefits = null
          } else {
            _tmpBenefits = _stmt.getText(_columnIndexOfBenefits)
          }
          val _tmpDifficulty: TipDifficulty
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toTipDifficulty(_tmp_1)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpIsActive: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_2 != 0
          val _tmpPriority: Int
          _tmpPriority = _stmt.getLong(_columnIndexOfPriority).toInt()
          val _tmpSeasonalTip: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfSeasonalTip).toInt()
          _tmpSeasonalTip = _tmp_3 != 0
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
          _item = NutritionTip(_tmpId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpBenefits,_tmpDifficulty,_tmpIcon,_tmpImageUrl,_tmpTags,_tmpIsActive,_tmpPriority,_tmpSeasonalTip,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTipsByCategory(category: NutritionCategory): Flow<List<NutritionTip>> {
    val _sql: String = "SELECT * FROM nutrition_tips WHERE category = ?"
    return createFlow(__db, false, arrayOf("nutrition_tips")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String = __converters.fromNutritionCategory(category)
        _stmt.bindText(_argIndex, _tmp)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfBenefits: Int = getColumnIndexOrThrow(_stmt, "benefits")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfSeasonalTip: Int = getColumnIndexOrThrow(_stmt, "seasonalTip")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<NutritionTip> = mutableListOf()
        while (_stmt.step()) {
          val _item: NutritionTip
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: NutritionCategory
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toNutritionCategory(_tmp_1)
          val _tmpBenefits: String?
          if (_stmt.isNull(_columnIndexOfBenefits)) {
            _tmpBenefits = null
          } else {
            _tmpBenefits = _stmt.getText(_columnIndexOfBenefits)
          }
          val _tmpDifficulty: TipDifficulty
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toTipDifficulty(_tmp_2)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpIsActive: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_3 != 0
          val _tmpPriority: Int
          _tmpPriority = _stmt.getLong(_columnIndexOfPriority).toInt()
          val _tmpSeasonalTip: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfSeasonalTip).toInt()
          _tmpSeasonalTip = _tmp_4 != 0
          val _tmpCreatedAt: Date
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_6: Date? = __converters.fromTimestamp(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_6
          }
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
          _item = NutritionTip(_tmpId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpBenefits,_tmpDifficulty,_tmpIcon,_tmpImageUrl,_tmpTags,_tmpIsActive,_tmpPriority,_tmpSeasonalTip,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTipsByGoal(category: NutritionCategory): Flow<List<NutritionTip>> {
    val _sql: String = "SELECT * FROM nutrition_tips WHERE category = ?"
    return createFlow(__db, false, arrayOf("nutrition_tips")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String = __converters.fromNutritionCategory(category)
        _stmt.bindText(_argIndex, _tmp)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfBenefits: Int = getColumnIndexOrThrow(_stmt, "benefits")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfSeasonalTip: Int = getColumnIndexOrThrow(_stmt, "seasonalTip")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<NutritionTip> = mutableListOf()
        while (_stmt.step()) {
          val _item: NutritionTip
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: NutritionCategory
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toNutritionCategory(_tmp_1)
          val _tmpBenefits: String?
          if (_stmt.isNull(_columnIndexOfBenefits)) {
            _tmpBenefits = null
          } else {
            _tmpBenefits = _stmt.getText(_columnIndexOfBenefits)
          }
          val _tmpDifficulty: TipDifficulty
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toTipDifficulty(_tmp_2)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpIsActive: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_3 != 0
          val _tmpPriority: Int
          _tmpPriority = _stmt.getLong(_columnIndexOfPriority).toInt()
          val _tmpSeasonalTip: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfSeasonalTip).toInt()
          _tmpSeasonalTip = _tmp_4 != 0
          val _tmpCreatedAt: Date
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_6: Date? = __converters.fromTimestamp(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_6
          }
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
          _item = NutritionTip(_tmpId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpBenefits,_tmpDifficulty,_tmpIcon,_tmpImageUrl,_tmpTags,_tmpIsActive,_tmpPriority,_tmpSeasonalTip,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTipById(tipId: Long): NutritionTip? {
    val _sql: String = "SELECT * FROM nutrition_tips WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, tipId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfBenefits: Int = getColumnIndexOrThrow(_stmt, "benefits")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfSeasonalTip: Int = getColumnIndexOrThrow(_stmt, "seasonalTip")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: NutritionTip?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: NutritionCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toNutritionCategory(_tmp)
          val _tmpBenefits: String?
          if (_stmt.isNull(_columnIndexOfBenefits)) {
            _tmpBenefits = null
          } else {
            _tmpBenefits = _stmt.getText(_columnIndexOfBenefits)
          }
          val _tmpDifficulty: TipDifficulty
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toTipDifficulty(_tmp_1)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpIsActive: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_2 != 0
          val _tmpPriority: Int
          _tmpPriority = _stmt.getLong(_columnIndexOfPriority).toInt()
          val _tmpSeasonalTip: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfSeasonalTip).toInt()
          _tmpSeasonalTip = _tmp_3 != 0
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
          _result = NutritionTip(_tmpId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpBenefits,_tmpDifficulty,_tmpIcon,_tmpImageUrl,_tmpTags,_tmpIsActive,_tmpPriority,_tmpSeasonalTip,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchTips(query: String): Flow<List<NutritionTip>> {
    val _sql: String = """
        |
        |        SELECT * FROM nutrition_tips
        |        WHERE (title LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%')
        |        ORDER BY createdAt DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("nutrition_tips")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfBenefits: Int = getColumnIndexOrThrow(_stmt, "benefits")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfSeasonalTip: Int = getColumnIndexOrThrow(_stmt, "seasonalTip")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<NutritionTip> = mutableListOf()
        while (_stmt.step()) {
          val _item: NutritionTip
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: NutritionCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toNutritionCategory(_tmp)
          val _tmpBenefits: String?
          if (_stmt.isNull(_columnIndexOfBenefits)) {
            _tmpBenefits = null
          } else {
            _tmpBenefits = _stmt.getText(_columnIndexOfBenefits)
          }
          val _tmpDifficulty: TipDifficulty
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toTipDifficulty(_tmp_1)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpIsActive: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_2 != 0
          val _tmpPriority: Int
          _tmpPriority = _stmt.getLong(_columnIndexOfPriority).toInt()
          val _tmpSeasonalTip: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfSeasonalTip).toInt()
          _tmpSeasonalTip = _tmp_3 != 0
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
          _item = NutritionTip(_tmpId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpBenefits,_tmpDifficulty,_tmpIcon,_tmpImageUrl,_tmpTags,_tmpIsActive,_tmpPriority,_tmpSeasonalTip,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getVegetarianTips(): Flow<List<NutritionTip>> {
    val _sql: String = "SELECT * FROM nutrition_tips WHERE tags LIKE '%vegetarian%'"
    return createFlow(__db, false, arrayOf("nutrition_tips")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfBenefits: Int = getColumnIndexOrThrow(_stmt, "benefits")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfSeasonalTip: Int = getColumnIndexOrThrow(_stmt, "seasonalTip")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<NutritionTip> = mutableListOf()
        while (_stmt.step()) {
          val _item: NutritionTip
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: NutritionCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toNutritionCategory(_tmp)
          val _tmpBenefits: String?
          if (_stmt.isNull(_columnIndexOfBenefits)) {
            _tmpBenefits = null
          } else {
            _tmpBenefits = _stmt.getText(_columnIndexOfBenefits)
          }
          val _tmpDifficulty: TipDifficulty
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toTipDifficulty(_tmp_1)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpIsActive: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_2 != 0
          val _tmpPriority: Int
          _tmpPriority = _stmt.getLong(_columnIndexOfPriority).toInt()
          val _tmpSeasonalTip: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfSeasonalTip).toInt()
          _tmpSeasonalTip = _tmp_3 != 0
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
          _item = NutritionTip(_tmpId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpBenefits,_tmpDifficulty,_tmpIcon,_tmpImageUrl,_tmpTags,_tmpIsActive,_tmpPriority,_tmpSeasonalTip,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getVeganTips(): Flow<List<NutritionTip>> {
    val _sql: String = "SELECT * FROM nutrition_tips WHERE tags LIKE '%vegan%'"
    return createFlow(__db, false, arrayOf("nutrition_tips")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfBenefits: Int = getColumnIndexOrThrow(_stmt, "benefits")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfSeasonalTip: Int = getColumnIndexOrThrow(_stmt, "seasonalTip")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<NutritionTip> = mutableListOf()
        while (_stmt.step()) {
          val _item: NutritionTip
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: NutritionCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toNutritionCategory(_tmp)
          val _tmpBenefits: String?
          if (_stmt.isNull(_columnIndexOfBenefits)) {
            _tmpBenefits = null
          } else {
            _tmpBenefits = _stmt.getText(_columnIndexOfBenefits)
          }
          val _tmpDifficulty: TipDifficulty
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toTipDifficulty(_tmp_1)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpIsActive: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_2 != 0
          val _tmpPriority: Int
          _tmpPriority = _stmt.getLong(_columnIndexOfPriority).toInt()
          val _tmpSeasonalTip: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfSeasonalTip).toInt()
          _tmpSeasonalTip = _tmp_3 != 0
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
          _item = NutritionTip(_tmpId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpBenefits,_tmpDifficulty,_tmpIcon,_tmpImageUrl,_tmpTags,_tmpIsActive,_tmpPriority,_tmpSeasonalTip,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getGlutenFreeTips(): Flow<List<NutritionTip>> {
    val _sql: String = "SELECT * FROM nutrition_tips WHERE tags LIKE '%gluten-free%'"
    return createFlow(__db, false, arrayOf("nutrition_tips")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfBenefits: Int = getColumnIndexOrThrow(_stmt, "benefits")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfSeasonalTip: Int = getColumnIndexOrThrow(_stmt, "seasonalTip")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: MutableList<NutritionTip> = mutableListOf()
        while (_stmt.step()) {
          val _item: NutritionTip
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: NutritionCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toNutritionCategory(_tmp)
          val _tmpBenefits: String?
          if (_stmt.isNull(_columnIndexOfBenefits)) {
            _tmpBenefits = null
          } else {
            _tmpBenefits = _stmt.getText(_columnIndexOfBenefits)
          }
          val _tmpDifficulty: TipDifficulty
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toTipDifficulty(_tmp_1)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpIsActive: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_2 != 0
          val _tmpPriority: Int
          _tmpPriority = _stmt.getLong(_columnIndexOfPriority).toInt()
          val _tmpSeasonalTip: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfSeasonalTip).toInt()
          _tmpSeasonalTip = _tmp_3 != 0
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
          _item = NutritionTip(_tmpId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpBenefits,_tmpDifficulty,_tmpIcon,_tmpImageUrl,_tmpTags,_tmpIsActive,_tmpPriority,_tmpSeasonalTip,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDailyTip(categories: List<NutritionCategory>): NutritionTip? {
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("""
        |
        |""".trimMargin())
    _stringBuilder.append("        SELECT * FROM nutrition_tips")
    _stringBuilder.append("""
        |
        |""".trimMargin())
    _stringBuilder.append("        WHERE category IN (")
    val _inputSize: Int = categories.size
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    _stringBuilder.append("""
        |
        |""".trimMargin())
    _stringBuilder.append("        AND isActive = 1")
    _stringBuilder.append("""
        |
        |""".trimMargin())
    _stringBuilder.append("        ORDER BY RANDOM()")
    _stringBuilder.append("""
        |
        |""".trimMargin())
    _stringBuilder.append("        LIMIT 1")
    _stringBuilder.append("""
        |
        |""".trimMargin())
    _stringBuilder.append("    ")
    val _sql: String = _stringBuilder.toString()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        for (_item: NutritionCategory in categories) {
          val _tmp: String = __converters.fromNutritionCategory(_item)
          _stmt.bindText(_argIndex, _tmp)
          _argIndex++
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfBenefits: Int = getColumnIndexOrThrow(_stmt, "benefits")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfIcon: Int = getColumnIndexOrThrow(_stmt, "icon")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfPriority: Int = getColumnIndexOrThrow(_stmt, "priority")
        val _columnIndexOfSeasonalTip: Int = getColumnIndexOrThrow(_stmt, "seasonalTip")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updatedAt")
        val _result: NutritionTip?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: NutritionCategory
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toNutritionCategory(_tmp_1)
          val _tmpBenefits: String?
          if (_stmt.isNull(_columnIndexOfBenefits)) {
            _tmpBenefits = null
          } else {
            _tmpBenefits = _stmt.getText(_columnIndexOfBenefits)
          }
          val _tmpDifficulty: TipDifficulty
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toTipDifficulty(_tmp_2)
          val _tmpIcon: String?
          if (_stmt.isNull(_columnIndexOfIcon)) {
            _tmpIcon = null
          } else {
            _tmpIcon = _stmt.getText(_columnIndexOfIcon)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpIsActive: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp_3 != 0
          val _tmpPriority: Int
          _tmpPriority = _stmt.getLong(_columnIndexOfPriority).toInt()
          val _tmpSeasonalTip: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfSeasonalTip).toInt()
          _tmpSeasonalTip = _tmp_4 != 0
          val _tmpCreatedAt: Date
          val _tmp_5: Long?
          if (_stmt.isNull(_columnIndexOfCreatedAt)) {
            _tmp_5 = null
          } else {
            _tmp_5 = _stmt.getLong(_columnIndexOfCreatedAt)
          }
          val _tmp_6: Date? = __converters.fromTimestamp(_tmp_5)
          if (_tmp_6 == null) {
            error("Expected NON-NULL 'java.util.Date', but it was NULL.")
          } else {
            _tmpCreatedAt = _tmp_6
          }
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
          _result = NutritionTip(_tmpId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpBenefits,_tmpDifficulty,_tmpIcon,_tmpImageUrl,_tmpTags,_tmpIsActive,_tmpPriority,_tmpSeasonalTip,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTipCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM nutrition_tips"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
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

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
