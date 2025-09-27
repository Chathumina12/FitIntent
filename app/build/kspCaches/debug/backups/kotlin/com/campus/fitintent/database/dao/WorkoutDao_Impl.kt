package com.campus.fitintent.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.campus.fitintent.database.Converters
import com.campus.fitintent.models.DifficultyLevel
import com.campus.fitintent.models.Workout
import com.campus.fitintent.models.WorkoutCategory
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
public class WorkoutDao_Impl(
  __db: RoomDatabase,
) : WorkoutDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfWorkout: EntityInsertAdapter<Workout>

  private val __converters: Converters = Converters()

  private val __deleteAdapterOfWorkout: EntityDeleteOrUpdateAdapter<Workout>

  private val __updateAdapterOfWorkout: EntityDeleteOrUpdateAdapter<Workout>
  init {
    this.__db = __db
    this.__insertAdapterOfWorkout = object : EntityInsertAdapter<Workout>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `workouts` (`id`,`name`,`description`,`category`,`difficulty`,`durationMinutes`,`caloriesBurned`,`equipment`,`imageUrl`,`videoUrl`,`instructions`,`targetMuscles`,`isCustom`,`createdBy`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Workout) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.description)
        val _tmp: String = __converters.fromWorkoutCategory(entity.category)
        statement.bindText(4, _tmp)
        val _tmp_1: String = __converters.fromDifficultyLevel(entity.difficulty)
        statement.bindText(5, _tmp_1)
        statement.bindLong(6, entity.durationMinutes.toLong())
        statement.bindLong(7, entity.caloriesBurned.toLong())
        val _tmpEquipment: String? = entity.equipment
        if (_tmpEquipment == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpEquipment)
        }
        val _tmpImageUrl: String? = entity.imageUrl
        if (_tmpImageUrl == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmpImageUrl)
        }
        val _tmpVideoUrl: String? = entity.videoUrl
        if (_tmpVideoUrl == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmpVideoUrl)
        }
        statement.bindText(11, entity.instructions)
        val _tmpTargetMuscles: String? = entity.targetMuscles
        if (_tmpTargetMuscles == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpTargetMuscles)
        }
        val _tmp_2: Int = if (entity.isCustom) 1 else 0
        statement.bindLong(13, _tmp_2.toLong())
        val _tmpCreatedBy: Long? = entity.createdBy
        if (_tmpCreatedBy == null) {
          statement.bindNull(14)
        } else {
          statement.bindLong(14, _tmpCreatedBy)
        }
      }
    }
    this.__deleteAdapterOfWorkout = object : EntityDeleteOrUpdateAdapter<Workout>() {
      protected override fun createQuery(): String = "DELETE FROM `workouts` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Workout) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfWorkout = object : EntityDeleteOrUpdateAdapter<Workout>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `workouts` SET `id` = ?,`name` = ?,`description` = ?,`category` = ?,`difficulty` = ?,`durationMinutes` = ?,`caloriesBurned` = ?,`equipment` = ?,`imageUrl` = ?,`videoUrl` = ?,`instructions` = ?,`targetMuscles` = ?,`isCustom` = ?,`createdBy` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Workout) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.description)
        val _tmp: String = __converters.fromWorkoutCategory(entity.category)
        statement.bindText(4, _tmp)
        val _tmp_1: String = __converters.fromDifficultyLevel(entity.difficulty)
        statement.bindText(5, _tmp_1)
        statement.bindLong(6, entity.durationMinutes.toLong())
        statement.bindLong(7, entity.caloriesBurned.toLong())
        val _tmpEquipment: String? = entity.equipment
        if (_tmpEquipment == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpEquipment)
        }
        val _tmpImageUrl: String? = entity.imageUrl
        if (_tmpImageUrl == null) {
          statement.bindNull(9)
        } else {
          statement.bindText(9, _tmpImageUrl)
        }
        val _tmpVideoUrl: String? = entity.videoUrl
        if (_tmpVideoUrl == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmpVideoUrl)
        }
        statement.bindText(11, entity.instructions)
        val _tmpTargetMuscles: String? = entity.targetMuscles
        if (_tmpTargetMuscles == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpTargetMuscles)
        }
        val _tmp_2: Int = if (entity.isCustom) 1 else 0
        statement.bindLong(13, _tmp_2.toLong())
        val _tmpCreatedBy: Long? = entity.createdBy
        if (_tmpCreatedBy == null) {
          statement.bindNull(14)
        } else {
          statement.bindLong(14, _tmpCreatedBy)
        }
        statement.bindLong(15, entity.id)
      }
    }
  }

  public override suspend fun insertWorkout(workout: Workout): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfWorkout.insertAndReturnId(_connection, workout)
    _result
  }

  public override suspend fun deleteWorkout(workout: Workout): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfWorkout.handle(_connection, workout)
  }

  public override suspend fun updateWorkout(workout: Workout): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfWorkout.handle(_connection, workout)
  }

  public override fun getAllPredefinedWorkouts(): Flow<List<Workout>> {
    val _sql: String = "SELECT * FROM workouts WHERE isCustom = 0 ORDER BY category, difficulty"
    return createFlow(__db, false, arrayOf("workouts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfDurationMinutes: Int = getColumnIndexOrThrow(_stmt, "durationMinutes")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfEquipment: Int = getColumnIndexOrThrow(_stmt, "equipment")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfVideoUrl: Int = getColumnIndexOrThrow(_stmt, "videoUrl")
        val _columnIndexOfInstructions: Int = getColumnIndexOrThrow(_stmt, "instructions")
        val _columnIndexOfTargetMuscles: Int = getColumnIndexOrThrow(_stmt, "targetMuscles")
        val _columnIndexOfIsCustom: Int = getColumnIndexOrThrow(_stmt, "isCustom")
        val _columnIndexOfCreatedBy: Int = getColumnIndexOrThrow(_stmt, "createdBy")
        val _result: MutableList<Workout> = mutableListOf()
        while (_stmt.step()) {
          val _item: Workout
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: WorkoutCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toWorkoutCategory(_tmp)
          val _tmpDifficulty: DifficultyLevel
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toDifficultyLevel(_tmp_1)
          val _tmpDurationMinutes: Int
          _tmpDurationMinutes = _stmt.getLong(_columnIndexOfDurationMinutes).toInt()
          val _tmpCaloriesBurned: Int
          _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          val _tmpEquipment: String?
          if (_stmt.isNull(_columnIndexOfEquipment)) {
            _tmpEquipment = null
          } else {
            _tmpEquipment = _stmt.getText(_columnIndexOfEquipment)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpVideoUrl: String?
          if (_stmt.isNull(_columnIndexOfVideoUrl)) {
            _tmpVideoUrl = null
          } else {
            _tmpVideoUrl = _stmt.getText(_columnIndexOfVideoUrl)
          }
          val _tmpInstructions: String
          _tmpInstructions = _stmt.getText(_columnIndexOfInstructions)
          val _tmpTargetMuscles: String?
          if (_stmt.isNull(_columnIndexOfTargetMuscles)) {
            _tmpTargetMuscles = null
          } else {
            _tmpTargetMuscles = _stmt.getText(_columnIndexOfTargetMuscles)
          }
          val _tmpIsCustom: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsCustom).toInt()
          _tmpIsCustom = _tmp_2 != 0
          val _tmpCreatedBy: Long?
          if (_stmt.isNull(_columnIndexOfCreatedBy)) {
            _tmpCreatedBy = null
          } else {
            _tmpCreatedBy = _stmt.getLong(_columnIndexOfCreatedBy)
          }
          _item = Workout(_tmpId,_tmpName,_tmpDescription,_tmpCategory,_tmpDifficulty,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpEquipment,_tmpImageUrl,_tmpVideoUrl,_tmpInstructions,_tmpTargetMuscles,_tmpIsCustom,_tmpCreatedBy)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getWorkoutsByCategory(category: WorkoutCategory): Flow<List<Workout>> {
    val _sql: String = "SELECT * FROM workouts WHERE category = ? AND isCustom = 0"
    return createFlow(__db, false, arrayOf("workouts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String = __converters.fromWorkoutCategory(category)
        _stmt.bindText(_argIndex, _tmp)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfDurationMinutes: Int = getColumnIndexOrThrow(_stmt, "durationMinutes")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfEquipment: Int = getColumnIndexOrThrow(_stmt, "equipment")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfVideoUrl: Int = getColumnIndexOrThrow(_stmt, "videoUrl")
        val _columnIndexOfInstructions: Int = getColumnIndexOrThrow(_stmt, "instructions")
        val _columnIndexOfTargetMuscles: Int = getColumnIndexOrThrow(_stmt, "targetMuscles")
        val _columnIndexOfIsCustom: Int = getColumnIndexOrThrow(_stmt, "isCustom")
        val _columnIndexOfCreatedBy: Int = getColumnIndexOrThrow(_stmt, "createdBy")
        val _result: MutableList<Workout> = mutableListOf()
        while (_stmt.step()) {
          val _item: Workout
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: WorkoutCategory
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toWorkoutCategory(_tmp_1)
          val _tmpDifficulty: DifficultyLevel
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toDifficultyLevel(_tmp_2)
          val _tmpDurationMinutes: Int
          _tmpDurationMinutes = _stmt.getLong(_columnIndexOfDurationMinutes).toInt()
          val _tmpCaloriesBurned: Int
          _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          val _tmpEquipment: String?
          if (_stmt.isNull(_columnIndexOfEquipment)) {
            _tmpEquipment = null
          } else {
            _tmpEquipment = _stmt.getText(_columnIndexOfEquipment)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpVideoUrl: String?
          if (_stmt.isNull(_columnIndexOfVideoUrl)) {
            _tmpVideoUrl = null
          } else {
            _tmpVideoUrl = _stmt.getText(_columnIndexOfVideoUrl)
          }
          val _tmpInstructions: String
          _tmpInstructions = _stmt.getText(_columnIndexOfInstructions)
          val _tmpTargetMuscles: String?
          if (_stmt.isNull(_columnIndexOfTargetMuscles)) {
            _tmpTargetMuscles = null
          } else {
            _tmpTargetMuscles = _stmt.getText(_columnIndexOfTargetMuscles)
          }
          val _tmpIsCustom: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsCustom).toInt()
          _tmpIsCustom = _tmp_3 != 0
          val _tmpCreatedBy: Long?
          if (_stmt.isNull(_columnIndexOfCreatedBy)) {
            _tmpCreatedBy = null
          } else {
            _tmpCreatedBy = _stmt.getLong(_columnIndexOfCreatedBy)
          }
          _item = Workout(_tmpId,_tmpName,_tmpDescription,_tmpCategory,_tmpDifficulty,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpEquipment,_tmpImageUrl,_tmpVideoUrl,_tmpInstructions,_tmpTargetMuscles,_tmpIsCustom,_tmpCreatedBy)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getWorkoutsByDifficulty(difficulty: DifficultyLevel): Flow<List<Workout>> {
    val _sql: String = "SELECT * FROM workouts WHERE difficulty = ? AND isCustom = 0"
    return createFlow(__db, false, arrayOf("workouts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String = __converters.fromDifficultyLevel(difficulty)
        _stmt.bindText(_argIndex, _tmp)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfDurationMinutes: Int = getColumnIndexOrThrow(_stmt, "durationMinutes")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfEquipment: Int = getColumnIndexOrThrow(_stmt, "equipment")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfVideoUrl: Int = getColumnIndexOrThrow(_stmt, "videoUrl")
        val _columnIndexOfInstructions: Int = getColumnIndexOrThrow(_stmt, "instructions")
        val _columnIndexOfTargetMuscles: Int = getColumnIndexOrThrow(_stmt, "targetMuscles")
        val _columnIndexOfIsCustom: Int = getColumnIndexOrThrow(_stmt, "isCustom")
        val _columnIndexOfCreatedBy: Int = getColumnIndexOrThrow(_stmt, "createdBy")
        val _result: MutableList<Workout> = mutableListOf()
        while (_stmt.step()) {
          val _item: Workout
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: WorkoutCategory
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toWorkoutCategory(_tmp_1)
          val _tmpDifficulty: DifficultyLevel
          val _tmp_2: String
          _tmp_2 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toDifficultyLevel(_tmp_2)
          val _tmpDurationMinutes: Int
          _tmpDurationMinutes = _stmt.getLong(_columnIndexOfDurationMinutes).toInt()
          val _tmpCaloriesBurned: Int
          _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          val _tmpEquipment: String?
          if (_stmt.isNull(_columnIndexOfEquipment)) {
            _tmpEquipment = null
          } else {
            _tmpEquipment = _stmt.getText(_columnIndexOfEquipment)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpVideoUrl: String?
          if (_stmt.isNull(_columnIndexOfVideoUrl)) {
            _tmpVideoUrl = null
          } else {
            _tmpVideoUrl = _stmt.getText(_columnIndexOfVideoUrl)
          }
          val _tmpInstructions: String
          _tmpInstructions = _stmt.getText(_columnIndexOfInstructions)
          val _tmpTargetMuscles: String?
          if (_stmt.isNull(_columnIndexOfTargetMuscles)) {
            _tmpTargetMuscles = null
          } else {
            _tmpTargetMuscles = _stmt.getText(_columnIndexOfTargetMuscles)
          }
          val _tmpIsCustom: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsCustom).toInt()
          _tmpIsCustom = _tmp_3 != 0
          val _tmpCreatedBy: Long?
          if (_stmt.isNull(_columnIndexOfCreatedBy)) {
            _tmpCreatedBy = null
          } else {
            _tmpCreatedBy = _stmt.getLong(_columnIndexOfCreatedBy)
          }
          _item = Workout(_tmpId,_tmpName,_tmpDescription,_tmpCategory,_tmpDifficulty,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpEquipment,_tmpImageUrl,_tmpVideoUrl,_tmpInstructions,_tmpTargetMuscles,_tmpIsCustom,_tmpCreatedBy)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getCustomWorkouts(userId: Long): Flow<List<Workout>> {
    val _sql: String = "SELECT * FROM workouts WHERE createdBy = ? AND isCustom = 1"
    return createFlow(__db, false, arrayOf("workouts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfDurationMinutes: Int = getColumnIndexOrThrow(_stmt, "durationMinutes")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfEquipment: Int = getColumnIndexOrThrow(_stmt, "equipment")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfVideoUrl: Int = getColumnIndexOrThrow(_stmt, "videoUrl")
        val _columnIndexOfInstructions: Int = getColumnIndexOrThrow(_stmt, "instructions")
        val _columnIndexOfTargetMuscles: Int = getColumnIndexOrThrow(_stmt, "targetMuscles")
        val _columnIndexOfIsCustom: Int = getColumnIndexOrThrow(_stmt, "isCustom")
        val _columnIndexOfCreatedBy: Int = getColumnIndexOrThrow(_stmt, "createdBy")
        val _result: MutableList<Workout> = mutableListOf()
        while (_stmt.step()) {
          val _item: Workout
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: WorkoutCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toWorkoutCategory(_tmp)
          val _tmpDifficulty: DifficultyLevel
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toDifficultyLevel(_tmp_1)
          val _tmpDurationMinutes: Int
          _tmpDurationMinutes = _stmt.getLong(_columnIndexOfDurationMinutes).toInt()
          val _tmpCaloriesBurned: Int
          _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          val _tmpEquipment: String?
          if (_stmt.isNull(_columnIndexOfEquipment)) {
            _tmpEquipment = null
          } else {
            _tmpEquipment = _stmt.getText(_columnIndexOfEquipment)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpVideoUrl: String?
          if (_stmt.isNull(_columnIndexOfVideoUrl)) {
            _tmpVideoUrl = null
          } else {
            _tmpVideoUrl = _stmt.getText(_columnIndexOfVideoUrl)
          }
          val _tmpInstructions: String
          _tmpInstructions = _stmt.getText(_columnIndexOfInstructions)
          val _tmpTargetMuscles: String?
          if (_stmt.isNull(_columnIndexOfTargetMuscles)) {
            _tmpTargetMuscles = null
          } else {
            _tmpTargetMuscles = _stmt.getText(_columnIndexOfTargetMuscles)
          }
          val _tmpIsCustom: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsCustom).toInt()
          _tmpIsCustom = _tmp_2 != 0
          val _tmpCreatedBy: Long?
          if (_stmt.isNull(_columnIndexOfCreatedBy)) {
            _tmpCreatedBy = null
          } else {
            _tmpCreatedBy = _stmt.getLong(_columnIndexOfCreatedBy)
          }
          _item = Workout(_tmpId,_tmpName,_tmpDescription,_tmpCategory,_tmpDifficulty,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpEquipment,_tmpImageUrl,_tmpVideoUrl,_tmpInstructions,_tmpTargetMuscles,_tmpIsCustom,_tmpCreatedBy)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getWorkoutById(workoutId: Long): Workout? {
    val _sql: String = "SELECT * FROM workouts WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, workoutId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfDurationMinutes: Int = getColumnIndexOrThrow(_stmt, "durationMinutes")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfEquipment: Int = getColumnIndexOrThrow(_stmt, "equipment")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfVideoUrl: Int = getColumnIndexOrThrow(_stmt, "videoUrl")
        val _columnIndexOfInstructions: Int = getColumnIndexOrThrow(_stmt, "instructions")
        val _columnIndexOfTargetMuscles: Int = getColumnIndexOrThrow(_stmt, "targetMuscles")
        val _columnIndexOfIsCustom: Int = getColumnIndexOrThrow(_stmt, "isCustom")
        val _columnIndexOfCreatedBy: Int = getColumnIndexOrThrow(_stmt, "createdBy")
        val _result: Workout?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: WorkoutCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toWorkoutCategory(_tmp)
          val _tmpDifficulty: DifficultyLevel
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toDifficultyLevel(_tmp_1)
          val _tmpDurationMinutes: Int
          _tmpDurationMinutes = _stmt.getLong(_columnIndexOfDurationMinutes).toInt()
          val _tmpCaloriesBurned: Int
          _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          val _tmpEquipment: String?
          if (_stmt.isNull(_columnIndexOfEquipment)) {
            _tmpEquipment = null
          } else {
            _tmpEquipment = _stmt.getText(_columnIndexOfEquipment)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpVideoUrl: String?
          if (_stmt.isNull(_columnIndexOfVideoUrl)) {
            _tmpVideoUrl = null
          } else {
            _tmpVideoUrl = _stmt.getText(_columnIndexOfVideoUrl)
          }
          val _tmpInstructions: String
          _tmpInstructions = _stmt.getText(_columnIndexOfInstructions)
          val _tmpTargetMuscles: String?
          if (_stmt.isNull(_columnIndexOfTargetMuscles)) {
            _tmpTargetMuscles = null
          } else {
            _tmpTargetMuscles = _stmt.getText(_columnIndexOfTargetMuscles)
          }
          val _tmpIsCustom: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsCustom).toInt()
          _tmpIsCustom = _tmp_2 != 0
          val _tmpCreatedBy: Long?
          if (_stmt.isNull(_columnIndexOfCreatedBy)) {
            _tmpCreatedBy = null
          } else {
            _tmpCreatedBy = _stmt.getLong(_columnIndexOfCreatedBy)
          }
          _result = Workout(_tmpId,_tmpName,_tmpDescription,_tmpCategory,_tmpDifficulty,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpEquipment,_tmpImageUrl,_tmpVideoUrl,_tmpInstructions,_tmpTargetMuscles,_tmpIsCustom,_tmpCreatedBy)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchWorkouts(query: String): Flow<List<Workout>> {
    val _sql: String = """
        |
        |        SELECT * FROM workouts
        |        WHERE (name LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%')
        |        AND isCustom = 0
        |        ORDER BY name
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("workouts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfDurationMinutes: Int = getColumnIndexOrThrow(_stmt, "durationMinutes")
        val _columnIndexOfCaloriesBurned: Int = getColumnIndexOrThrow(_stmt, "caloriesBurned")
        val _columnIndexOfEquipment: Int = getColumnIndexOrThrow(_stmt, "equipment")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfVideoUrl: Int = getColumnIndexOrThrow(_stmt, "videoUrl")
        val _columnIndexOfInstructions: Int = getColumnIndexOrThrow(_stmt, "instructions")
        val _columnIndexOfTargetMuscles: Int = getColumnIndexOrThrow(_stmt, "targetMuscles")
        val _columnIndexOfIsCustom: Int = getColumnIndexOrThrow(_stmt, "isCustom")
        val _columnIndexOfCreatedBy: Int = getColumnIndexOrThrow(_stmt, "createdBy")
        val _result: MutableList<Workout> = mutableListOf()
        while (_stmt.step()) {
          val _item: Workout
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCategory: WorkoutCategory
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfCategory)
          _tmpCategory = __converters.toWorkoutCategory(_tmp)
          val _tmpDifficulty: DifficultyLevel
          val _tmp_1: String
          _tmp_1 = _stmt.getText(_columnIndexOfDifficulty)
          _tmpDifficulty = __converters.toDifficultyLevel(_tmp_1)
          val _tmpDurationMinutes: Int
          _tmpDurationMinutes = _stmt.getLong(_columnIndexOfDurationMinutes).toInt()
          val _tmpCaloriesBurned: Int
          _tmpCaloriesBurned = _stmt.getLong(_columnIndexOfCaloriesBurned).toInt()
          val _tmpEquipment: String?
          if (_stmt.isNull(_columnIndexOfEquipment)) {
            _tmpEquipment = null
          } else {
            _tmpEquipment = _stmt.getText(_columnIndexOfEquipment)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_columnIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          }
          val _tmpVideoUrl: String?
          if (_stmt.isNull(_columnIndexOfVideoUrl)) {
            _tmpVideoUrl = null
          } else {
            _tmpVideoUrl = _stmt.getText(_columnIndexOfVideoUrl)
          }
          val _tmpInstructions: String
          _tmpInstructions = _stmt.getText(_columnIndexOfInstructions)
          val _tmpTargetMuscles: String?
          if (_stmt.isNull(_columnIndexOfTargetMuscles)) {
            _tmpTargetMuscles = null
          } else {
            _tmpTargetMuscles = _stmt.getText(_columnIndexOfTargetMuscles)
          }
          val _tmpIsCustom: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsCustom).toInt()
          _tmpIsCustom = _tmp_2 != 0
          val _tmpCreatedBy: Long?
          if (_stmt.isNull(_columnIndexOfCreatedBy)) {
            _tmpCreatedBy = null
          } else {
            _tmpCreatedBy = _stmt.getLong(_columnIndexOfCreatedBy)
          }
          _item = Workout(_tmpId,_tmpName,_tmpDescription,_tmpCategory,_tmpDifficulty,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpEquipment,_tmpImageUrl,_tmpVideoUrl,_tmpInstructions,_tmpTargetMuscles,_tmpIsCustom,_tmpCreatedBy)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllCategories(): List<WorkoutCategory> {
    val _sql: String = "SELECT DISTINCT category FROM workouts WHERE isCustom = 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: MutableList<WorkoutCategory> = mutableListOf()
        while (_stmt.step()) {
          val _item: WorkoutCategory
          val _tmp: String
          _tmp = _stmt.getText(0)
          _item = __converters.toWorkoutCategory(_tmp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getCustomWorkoutCount(userId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM workouts WHERE createdBy = ? AND isCustom = 1"
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

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
