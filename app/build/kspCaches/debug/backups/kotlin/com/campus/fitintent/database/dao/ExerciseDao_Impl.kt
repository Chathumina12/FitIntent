package com.campus.fitintent.database.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.campus.fitintent.models.Exercise
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
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ExerciseDao_Impl(
  __db: RoomDatabase,
) : ExerciseDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfExercise: EntityInsertAdapter<Exercise>

  private val __deleteAdapterOfExercise: EntityDeleteOrUpdateAdapter<Exercise>

  private val __updateAdapterOfExercise: EntityDeleteOrUpdateAdapter<Exercise>
  init {
    this.__db = __db
    this.__insertAdapterOfExercise = object : EntityInsertAdapter<Exercise>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `exercises` (`id`,`workoutId`,`name`,`description`,`orderIndex`,`sets`,`reps`,`durationSeconds`,`restSeconds`,`targetHeartRate`,`notes`,`imageUrl`,`videoUrl`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Exercise) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.workoutId)
        statement.bindText(3, entity.name)
        statement.bindText(4, entity.description)
        statement.bindLong(5, entity.orderIndex.toLong())
        val _tmpSets: Int? = entity.sets
        if (_tmpSets == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpSets.toLong())
        }
        val _tmpReps: Int? = entity.reps
        if (_tmpReps == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpReps.toLong())
        }
        val _tmpDurationSeconds: Int? = entity.durationSeconds
        if (_tmpDurationSeconds == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpDurationSeconds.toLong())
        }
        statement.bindLong(9, entity.restSeconds.toLong())
        val _tmpTargetHeartRate: Int? = entity.targetHeartRate
        if (_tmpTargetHeartRate == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpTargetHeartRate.toLong())
        }
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpNotes)
        }
        val _tmpImageUrl: String? = entity.imageUrl
        if (_tmpImageUrl == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpImageUrl)
        }
        val _tmpVideoUrl: String? = entity.videoUrl
        if (_tmpVideoUrl == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmpVideoUrl)
        }
      }
    }
    this.__deleteAdapterOfExercise = object : EntityDeleteOrUpdateAdapter<Exercise>() {
      protected override fun createQuery(): String = "DELETE FROM `exercises` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Exercise) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfExercise = object : EntityDeleteOrUpdateAdapter<Exercise>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `exercises` SET `id` = ?,`workoutId` = ?,`name` = ?,`description` = ?,`orderIndex` = ?,`sets` = ?,`reps` = ?,`durationSeconds` = ?,`restSeconds` = ?,`targetHeartRate` = ?,`notes` = ?,`imageUrl` = ?,`videoUrl` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Exercise) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.workoutId)
        statement.bindText(3, entity.name)
        statement.bindText(4, entity.description)
        statement.bindLong(5, entity.orderIndex.toLong())
        val _tmpSets: Int? = entity.sets
        if (_tmpSets == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpSets.toLong())
        }
        val _tmpReps: Int? = entity.reps
        if (_tmpReps == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpReps.toLong())
        }
        val _tmpDurationSeconds: Int? = entity.durationSeconds
        if (_tmpDurationSeconds == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpDurationSeconds.toLong())
        }
        statement.bindLong(9, entity.restSeconds.toLong())
        val _tmpTargetHeartRate: Int? = entity.targetHeartRate
        if (_tmpTargetHeartRate == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpTargetHeartRate.toLong())
        }
        val _tmpNotes: String? = entity.notes
        if (_tmpNotes == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpNotes)
        }
        val _tmpImageUrl: String? = entity.imageUrl
        if (_tmpImageUrl == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpImageUrl)
        }
        val _tmpVideoUrl: String? = entity.videoUrl
        if (_tmpVideoUrl == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmpVideoUrl)
        }
        statement.bindLong(14, entity.id)
      }
    }
  }

  public override suspend fun insertExercise(exercise: Exercise): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfExercise.insertAndReturnId(_connection, exercise)
    _result
  }

  public override suspend fun insertExercises(exercises: List<Exercise>): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfExercise.insert(_connection, exercises)
  }

  public override suspend fun deleteExercise(exercise: Exercise): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfExercise.handle(_connection, exercise)
  }

  public override suspend fun updateExercise(exercise: Exercise): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfExercise.handle(_connection, exercise)
  }

  public override suspend fun getExercisesByWorkout(workoutId: Long): List<Exercise> {
    val _sql: String = "SELECT * FROM exercises WHERE workoutId = ? ORDER BY orderIndex"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, workoutId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfWorkoutId: Int = getColumnIndexOrThrow(_stmt, "workoutId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfOrderIndex: Int = getColumnIndexOrThrow(_stmt, "orderIndex")
        val _columnIndexOfSets: Int = getColumnIndexOrThrow(_stmt, "sets")
        val _columnIndexOfReps: Int = getColumnIndexOrThrow(_stmt, "reps")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfRestSeconds: Int = getColumnIndexOrThrow(_stmt, "restSeconds")
        val _columnIndexOfTargetHeartRate: Int = getColumnIndexOrThrow(_stmt, "targetHeartRate")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfVideoUrl: Int = getColumnIndexOrThrow(_stmt, "videoUrl")
        val _result: MutableList<Exercise> = mutableListOf()
        while (_stmt.step()) {
          val _item: Exercise
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpWorkoutId: Long
          _tmpWorkoutId = _stmt.getLong(_columnIndexOfWorkoutId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpOrderIndex: Int
          _tmpOrderIndex = _stmt.getLong(_columnIndexOfOrderIndex).toInt()
          val _tmpSets: Int?
          if (_stmt.isNull(_columnIndexOfSets)) {
            _tmpSets = null
          } else {
            _tmpSets = _stmt.getLong(_columnIndexOfSets).toInt()
          }
          val _tmpReps: Int?
          if (_stmt.isNull(_columnIndexOfReps)) {
            _tmpReps = null
          } else {
            _tmpReps = _stmt.getLong(_columnIndexOfReps).toInt()
          }
          val _tmpDurationSeconds: Int?
          if (_stmt.isNull(_columnIndexOfDurationSeconds)) {
            _tmpDurationSeconds = null
          } else {
            _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          }
          val _tmpRestSeconds: Int
          _tmpRestSeconds = _stmt.getLong(_columnIndexOfRestSeconds).toInt()
          val _tmpTargetHeartRate: Int?
          if (_stmt.isNull(_columnIndexOfTargetHeartRate)) {
            _tmpTargetHeartRate = null
          } else {
            _tmpTargetHeartRate = _stmt.getLong(_columnIndexOfTargetHeartRate).toInt()
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
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
          _item = Exercise(_tmpId,_tmpWorkoutId,_tmpName,_tmpDescription,_tmpOrderIndex,_tmpSets,_tmpReps,_tmpDurationSeconds,_tmpRestSeconds,_tmpTargetHeartRate,_tmpNotes,_tmpImageUrl,_tmpVideoUrl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getExercisesByWorkoutFlow(workoutId: Long): Flow<List<Exercise>> {
    val _sql: String = "SELECT * FROM exercises WHERE workoutId = ? ORDER BY orderIndex"
    return createFlow(__db, false, arrayOf("exercises")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, workoutId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfWorkoutId: Int = getColumnIndexOrThrow(_stmt, "workoutId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfOrderIndex: Int = getColumnIndexOrThrow(_stmt, "orderIndex")
        val _columnIndexOfSets: Int = getColumnIndexOrThrow(_stmt, "sets")
        val _columnIndexOfReps: Int = getColumnIndexOrThrow(_stmt, "reps")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfRestSeconds: Int = getColumnIndexOrThrow(_stmt, "restSeconds")
        val _columnIndexOfTargetHeartRate: Int = getColumnIndexOrThrow(_stmt, "targetHeartRate")
        val _columnIndexOfNotes: Int = getColumnIndexOrThrow(_stmt, "notes")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfVideoUrl: Int = getColumnIndexOrThrow(_stmt, "videoUrl")
        val _result: MutableList<Exercise> = mutableListOf()
        while (_stmt.step()) {
          val _item: Exercise
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpWorkoutId: Long
          _tmpWorkoutId = _stmt.getLong(_columnIndexOfWorkoutId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpOrderIndex: Int
          _tmpOrderIndex = _stmt.getLong(_columnIndexOfOrderIndex).toInt()
          val _tmpSets: Int?
          if (_stmt.isNull(_columnIndexOfSets)) {
            _tmpSets = null
          } else {
            _tmpSets = _stmt.getLong(_columnIndexOfSets).toInt()
          }
          val _tmpReps: Int?
          if (_stmt.isNull(_columnIndexOfReps)) {
            _tmpReps = null
          } else {
            _tmpReps = _stmt.getLong(_columnIndexOfReps).toInt()
          }
          val _tmpDurationSeconds: Int?
          if (_stmt.isNull(_columnIndexOfDurationSeconds)) {
            _tmpDurationSeconds = null
          } else {
            _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds).toInt()
          }
          val _tmpRestSeconds: Int
          _tmpRestSeconds = _stmt.getLong(_columnIndexOfRestSeconds).toInt()
          val _tmpTargetHeartRate: Int?
          if (_stmt.isNull(_columnIndexOfTargetHeartRate)) {
            _tmpTargetHeartRate = null
          } else {
            _tmpTargetHeartRate = _stmt.getLong(_columnIndexOfTargetHeartRate).toInt()
          }
          val _tmpNotes: String?
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes)
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
          _item = Exercise(_tmpId,_tmpWorkoutId,_tmpName,_tmpDescription,_tmpOrderIndex,_tmpSets,_tmpReps,_tmpDurationSeconds,_tmpRestSeconds,_tmpTargetHeartRate,_tmpNotes,_tmpImageUrl,_tmpVideoUrl)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteExercisesByWorkout(workoutId: Long) {
    val _sql: String = "DELETE FROM exercises WHERE workoutId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, workoutId)
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
