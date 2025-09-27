package com.campus.fitintent.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.campus.fitintent.database.dao.DailyGoalDao
import com.campus.fitintent.database.dao.DailyGoalDao_Impl
import com.campus.fitintent.database.dao.ExerciseDao
import com.campus.fitintent.database.dao.ExerciseDao_Impl
import com.campus.fitintent.database.dao.HabitDao
import com.campus.fitintent.database.dao.HabitDao_Impl
import com.campus.fitintent.database.dao.HabitLogDao
import com.campus.fitintent.database.dao.HabitLogDao_Impl
import com.campus.fitintent.database.dao.NutritionTipDao
import com.campus.fitintent.database.dao.NutritionTipDao_Impl
import com.campus.fitintent.database.dao.StreakDao
import com.campus.fitintent.database.dao.StreakDao_Impl
import com.campus.fitintent.database.dao.UserBadgeDao
import com.campus.fitintent.database.dao.UserBadgeDao_Impl
import com.campus.fitintent.database.dao.UserDao
import com.campus.fitintent.database.dao.UserDao_Impl
import com.campus.fitintent.database.dao.WorkoutDao
import com.campus.fitintent.database.dao.WorkoutDao_Impl
import com.campus.fitintent.database.dao.WorkoutSessionDao
import com.campus.fitintent.database.dao.WorkoutSessionDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class FitIntentDatabase_Impl : FitIntentDatabase() {
  private val _userDao: Lazy<UserDao> = lazy {
    UserDao_Impl(this)
  }

  private val _habitDao: Lazy<HabitDao> = lazy {
    HabitDao_Impl(this)
  }

  private val _habitLogDao: Lazy<HabitLogDao> = lazy {
    HabitLogDao_Impl(this)
  }

  private val _workoutDao: Lazy<WorkoutDao> = lazy {
    WorkoutDao_Impl(this)
  }

  private val _exerciseDao: Lazy<ExerciseDao> = lazy {
    ExerciseDao_Impl(this)
  }

  private val _workoutSessionDao: Lazy<WorkoutSessionDao> = lazy {
    WorkoutSessionDao_Impl(this)
  }

  private val _dailyGoalDao: Lazy<DailyGoalDao> = lazy {
    DailyGoalDao_Impl(this)
  }

  private val _streakDao: Lazy<StreakDao> = lazy {
    StreakDao_Impl(this)
  }

  private val _userBadgeDao: Lazy<UserBadgeDao> = lazy {
    UserBadgeDao_Impl(this)
  }

  private val _nutritionTipDao: Lazy<NutritionTipDao> = lazy {
    NutritionTipDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "9592f8a3baebf39aed01aa5a26cbe3b6", "969643d29017f97b1660e3250344401a") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `email` TEXT NOT NULL, `username` TEXT NOT NULL, `passwordHash` TEXT NOT NULL, `fullName` TEXT NOT NULL, `age` INTEGER, `gender` TEXT, `height` REAL, `weight` REAL, `activityLevel` TEXT, `primaryGoal` TEXT, `targetWeight` REAL, `weeklyWorkoutGoal` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `isOnboardingComplete` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, `profileImagePath` TEXT)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_users_email` ON `users` (`email`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `habits` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `category` TEXT NOT NULL, `targetDays` INTEGER NOT NULL, `frequency` TEXT NOT NULL, `reminderTime` TEXT, `reminderEnabled` INTEGER NOT NULL, `currentStreak` INTEGER NOT NULL, `longestStreak` INTEGER NOT NULL, `completedDays` INTEGER NOT NULL, `skippedDays` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, `isPaused` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `completedAt` INTEGER, `color` TEXT NOT NULL, `icon` TEXT, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_habits_userId` ON `habits` (`userId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `habit_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `habitId` INTEGER NOT NULL, `date` INTEGER NOT NULL, `status` TEXT NOT NULL, `notes` TEXT, `completedAt` INTEGER, `skippedReason` TEXT, FOREIGN KEY(`habitId`) REFERENCES `habits`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_habit_logs_habitId` ON `habit_logs` (`habitId`)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_habit_logs_habitId_date` ON `habit_logs` (`habitId`, `date`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `workouts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `category` TEXT NOT NULL, `difficulty` TEXT NOT NULL, `durationMinutes` INTEGER NOT NULL, `caloriesBurned` INTEGER NOT NULL, `equipment` TEXT, `imageUrl` TEXT, `videoUrl` TEXT, `instructions` TEXT NOT NULL, `targetMuscles` TEXT, `isCustom` INTEGER NOT NULL, `createdBy` INTEGER)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `exercises` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `workoutId` INTEGER NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `orderIndex` INTEGER NOT NULL, `sets` INTEGER, `reps` INTEGER, `durationSeconds` INTEGER, `restSeconds` INTEGER NOT NULL, `targetHeartRate` INTEGER, `notes` TEXT, `imageUrl` TEXT, `videoUrl` TEXT, FOREIGN KEY(`workoutId`) REFERENCES `workouts`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_exercises_workoutId` ON `exercises` (`workoutId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `workout_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `workoutId` INTEGER, `startTime` INTEGER NOT NULL, `endTime` INTEGER, `durationMinutes` INTEGER, `caloriesBurned` INTEGER, `averageHeartRate` INTEGER, `maxHeartRate` INTEGER, `notes` TEXT, `feelingRating` INTEGER, `difficultyRating` INTEGER, `isCompleted` INTEGER NOT NULL, `completionPercentage` REAL NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`workoutId`) REFERENCES `workouts`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_workout_sessions_userId` ON `workout_sessions` (`userId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_workout_sessions_workoutId` ON `workout_sessions` (`workoutId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `daily_goals` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `date` INTEGER NOT NULL, `calorieGoal` INTEGER, `stepGoal` INTEGER, `waterGoal` INTEGER, `sleepGoal` REAL, `workoutMinutesGoal` INTEGER, `caloriesBurned` INTEGER NOT NULL, `stepsTaken` INTEGER NOT NULL, `waterIntake` INTEGER NOT NULL, `sleepHours` REAL NOT NULL, `workoutMinutes` INTEGER NOT NULL, `isCalorieGoalMet` INTEGER NOT NULL, `isStepGoalMet` INTEGER NOT NULL, `isWaterGoalMet` INTEGER NOT NULL, `isSleepGoalMet` INTEGER NOT NULL, `isWorkoutGoalMet` INTEGER NOT NULL, `overallCompletionPercentage` REAL NOT NULL, `updatedAt` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_daily_goals_userId` ON `daily_goals` (`userId`)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_daily_goals_userId_date` ON `daily_goals` (`userId`, `date`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `streaks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `type` TEXT NOT NULL, `currentStreak` INTEGER NOT NULL, `longestStreak` INTEGER NOT NULL, `lastActivityDate` INTEGER, `startDate` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_streaks_userId` ON `streaks` (`userId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `badges` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `type` TEXT NOT NULL, `rarity` TEXT NOT NULL, `iconResId` TEXT NOT NULL, `targetProgress` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `user_badges` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `badgeId` INTEGER NOT NULL, `currentProgress` INTEGER NOT NULL, `isUnlocked` INTEGER NOT NULL, `unlockedAt` INTEGER, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`badgeId`) REFERENCES `badges`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_user_badges_userId` ON `user_badges` (`userId`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_user_badges_badgeId` ON `user_badges` (`badgeId`)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_user_badges_userId_badgeId` ON `user_badges` (`userId`, `badgeId`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `nutrition_tips` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `category` TEXT NOT NULL, `benefits` TEXT, `difficulty` TEXT NOT NULL, `icon` TEXT, `imageUrl` TEXT, `tags` TEXT, `isActive` INTEGER NOT NULL, `priority` INTEGER NOT NULL, `seasonalTip` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '9592f8a3baebf39aed01aa5a26cbe3b6')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `users`")
        connection.execSQL("DROP TABLE IF EXISTS `habits`")
        connection.execSQL("DROP TABLE IF EXISTS `habit_logs`")
        connection.execSQL("DROP TABLE IF EXISTS `workouts`")
        connection.execSQL("DROP TABLE IF EXISTS `exercises`")
        connection.execSQL("DROP TABLE IF EXISTS `workout_sessions`")
        connection.execSQL("DROP TABLE IF EXISTS `daily_goals`")
        connection.execSQL("DROP TABLE IF EXISTS `streaks`")
        connection.execSQL("DROP TABLE IF EXISTS `badges`")
        connection.execSQL("DROP TABLE IF EXISTS `user_badges`")
        connection.execSQL("DROP TABLE IF EXISTS `nutrition_tips`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsUsers: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsUsers.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("email", TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("username", TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("passwordHash", TableInfo.Column("passwordHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("fullName", TableInfo.Column("fullName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("age", TableInfo.Column("age", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("gender", TableInfo.Column("gender", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("height", TableInfo.Column("height", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("weight", TableInfo.Column("weight", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("activityLevel", TableInfo.Column("activityLevel", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("primaryGoal", TableInfo.Column("primaryGoal", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("targetWeight", TableInfo.Column("targetWeight", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("weeklyWorkoutGoal", TableInfo.Column("weeklyWorkoutGoal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("isOnboardingComplete", TableInfo.Column("isOnboardingComplete", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("isActive", TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsers.put("profileImagePath", TableInfo.Column("profileImagePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUsers: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesUsers: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesUsers.add(TableInfo.Index("index_users_email", true, listOf("email"), listOf("ASC")))
        val _infoUsers: TableInfo = TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers)
        val _existingUsers: TableInfo = read(connection, "users")
        if (!_infoUsers.equals(_existingUsers)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |users(com.campus.fitintent.models.User).
              | Expected:
              |""".trimMargin() + _infoUsers + """
              |
              | Found:
              |""".trimMargin() + _existingUsers)
        }
        val _columnsHabits: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsHabits.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("userId", TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("description", TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("category", TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("targetDays", TableInfo.Column("targetDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("frequency", TableInfo.Column("frequency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("reminderTime", TableInfo.Column("reminderTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("reminderEnabled", TableInfo.Column("reminderEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("currentStreak", TableInfo.Column("currentStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("longestStreak", TableInfo.Column("longestStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("completedDays", TableInfo.Column("completedDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("skippedDays", TableInfo.Column("skippedDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("isActive", TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("isPaused", TableInfo.Column("isPaused", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("isCompleted", TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("completedAt", TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("color", TableInfo.Column("color", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabits.put("icon", TableInfo.Column("icon", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysHabits: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysHabits.add(TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", listOf("userId"), listOf("id")))
        val _indicesHabits: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesHabits.add(TableInfo.Index("index_habits_userId", false, listOf("userId"), listOf("ASC")))
        val _infoHabits: TableInfo = TableInfo("habits", _columnsHabits, _foreignKeysHabits, _indicesHabits)
        val _existingHabits: TableInfo = read(connection, "habits")
        if (!_infoHabits.equals(_existingHabits)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |habits(com.campus.fitintent.models.Habit).
              | Expected:
              |""".trimMargin() + _infoHabits + """
              |
              | Found:
              |""".trimMargin() + _existingHabits)
        }
        val _columnsHabitLogs: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsHabitLogs.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabitLogs.put("habitId", TableInfo.Column("habitId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabitLogs.put("date", TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabitLogs.put("status", TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabitLogs.put("notes", TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabitLogs.put("completedAt", TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHabitLogs.put("skippedReason", TableInfo.Column("skippedReason", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysHabitLogs: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysHabitLogs.add(TableInfo.ForeignKey("habits", "CASCADE", "NO ACTION", listOf("habitId"), listOf("id")))
        val _indicesHabitLogs: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesHabitLogs.add(TableInfo.Index("index_habit_logs_habitId", false, listOf("habitId"), listOf("ASC")))
        _indicesHabitLogs.add(TableInfo.Index("index_habit_logs_habitId_date", true, listOf("habitId", "date"), listOf("ASC", "ASC")))
        val _infoHabitLogs: TableInfo = TableInfo("habit_logs", _columnsHabitLogs, _foreignKeysHabitLogs, _indicesHabitLogs)
        val _existingHabitLogs: TableInfo = read(connection, "habit_logs")
        if (!_infoHabitLogs.equals(_existingHabitLogs)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |habit_logs(com.campus.fitintent.models.HabitLog).
              | Expected:
              |""".trimMargin() + _infoHabitLogs + """
              |
              | Found:
              |""".trimMargin() + _existingHabitLogs)
        }
        val _columnsWorkouts: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsWorkouts.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("description", TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("category", TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("difficulty", TableInfo.Column("difficulty", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("durationMinutes", TableInfo.Column("durationMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("caloriesBurned", TableInfo.Column("caloriesBurned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("equipment", TableInfo.Column("equipment", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("imageUrl", TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("videoUrl", TableInfo.Column("videoUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("instructions", TableInfo.Column("instructions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("targetMuscles", TableInfo.Column("targetMuscles", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("isCustom", TableInfo.Column("isCustom", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkouts.put("createdBy", TableInfo.Column("createdBy", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysWorkouts: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesWorkouts: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoWorkouts: TableInfo = TableInfo("workouts", _columnsWorkouts, _foreignKeysWorkouts, _indicesWorkouts)
        val _existingWorkouts: TableInfo = read(connection, "workouts")
        if (!_infoWorkouts.equals(_existingWorkouts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |workouts(com.campus.fitintent.models.Workout).
              | Expected:
              |""".trimMargin() + _infoWorkouts + """
              |
              | Found:
              |""".trimMargin() + _existingWorkouts)
        }
        val _columnsExercises: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsExercises.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("workoutId", TableInfo.Column("workoutId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("description", TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("orderIndex", TableInfo.Column("orderIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("sets", TableInfo.Column("sets", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("reps", TableInfo.Column("reps", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("durationSeconds", TableInfo.Column("durationSeconds", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("restSeconds", TableInfo.Column("restSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("targetHeartRate", TableInfo.Column("targetHeartRate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("notes", TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("imageUrl", TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsExercises.put("videoUrl", TableInfo.Column("videoUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysExercises: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysExercises.add(TableInfo.ForeignKey("workouts", "CASCADE", "NO ACTION", listOf("workoutId"), listOf("id")))
        val _indicesExercises: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesExercises.add(TableInfo.Index("index_exercises_workoutId", false, listOf("workoutId"), listOf("ASC")))
        val _infoExercises: TableInfo = TableInfo("exercises", _columnsExercises, _foreignKeysExercises, _indicesExercises)
        val _existingExercises: TableInfo = read(connection, "exercises")
        if (!_infoExercises.equals(_existingExercises)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |exercises(com.campus.fitintent.models.Exercise).
              | Expected:
              |""".trimMargin() + _infoExercises + """
              |
              | Found:
              |""".trimMargin() + _existingExercises)
        }
        val _columnsWorkoutSessions: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsWorkoutSessions.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("userId", TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("workoutId", TableInfo.Column("workoutId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("startTime", TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("endTime", TableInfo.Column("endTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("durationMinutes", TableInfo.Column("durationMinutes", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("caloriesBurned", TableInfo.Column("caloriesBurned", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("averageHeartRate", TableInfo.Column("averageHeartRate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("maxHeartRate", TableInfo.Column("maxHeartRate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("notes", TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("feelingRating", TableInfo.Column("feelingRating", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("difficultyRating", TableInfo.Column("difficultyRating", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("isCompleted", TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWorkoutSessions.put("completionPercentage", TableInfo.Column("completionPercentage", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysWorkoutSessions: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysWorkoutSessions.add(TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", listOf("userId"), listOf("id")))
        _foreignKeysWorkoutSessions.add(TableInfo.ForeignKey("workouts", "SET NULL", "NO ACTION", listOf("workoutId"), listOf("id")))
        val _indicesWorkoutSessions: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesWorkoutSessions.add(TableInfo.Index("index_workout_sessions_userId", false, listOf("userId"), listOf("ASC")))
        _indicesWorkoutSessions.add(TableInfo.Index("index_workout_sessions_workoutId", false, listOf("workoutId"), listOf("ASC")))
        val _infoWorkoutSessions: TableInfo = TableInfo("workout_sessions", _columnsWorkoutSessions, _foreignKeysWorkoutSessions, _indicesWorkoutSessions)
        val _existingWorkoutSessions: TableInfo = read(connection, "workout_sessions")
        if (!_infoWorkoutSessions.equals(_existingWorkoutSessions)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |workout_sessions(com.campus.fitintent.models.WorkoutSession).
              | Expected:
              |""".trimMargin() + _infoWorkoutSessions + """
              |
              | Found:
              |""".trimMargin() + _existingWorkoutSessions)
        }
        val _columnsDailyGoals: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsDailyGoals.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("userId", TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("date", TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("calorieGoal", TableInfo.Column("calorieGoal", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("stepGoal", TableInfo.Column("stepGoal", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("waterGoal", TableInfo.Column("waterGoal", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("sleepGoal", TableInfo.Column("sleepGoal", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("workoutMinutesGoal", TableInfo.Column("workoutMinutesGoal", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("caloriesBurned", TableInfo.Column("caloriesBurned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("stepsTaken", TableInfo.Column("stepsTaken", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("waterIntake", TableInfo.Column("waterIntake", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("sleepHours", TableInfo.Column("sleepHours", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("workoutMinutes", TableInfo.Column("workoutMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("isCalorieGoalMet", TableInfo.Column("isCalorieGoalMet", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("isStepGoalMet", TableInfo.Column("isStepGoalMet", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("isWaterGoalMet", TableInfo.Column("isWaterGoalMet", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("isSleepGoalMet", TableInfo.Column("isSleepGoalMet", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("isWorkoutGoalMet", TableInfo.Column("isWorkoutGoalMet", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("overallCompletionPercentage", TableInfo.Column("overallCompletionPercentage", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDailyGoals.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysDailyGoals: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysDailyGoals.add(TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", listOf("userId"), listOf("id")))
        val _indicesDailyGoals: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesDailyGoals.add(TableInfo.Index("index_daily_goals_userId", false, listOf("userId"), listOf("ASC")))
        _indicesDailyGoals.add(TableInfo.Index("index_daily_goals_userId_date", true, listOf("userId", "date"), listOf("ASC", "ASC")))
        val _infoDailyGoals: TableInfo = TableInfo("daily_goals", _columnsDailyGoals, _foreignKeysDailyGoals, _indicesDailyGoals)
        val _existingDailyGoals: TableInfo = read(connection, "daily_goals")
        if (!_infoDailyGoals.equals(_existingDailyGoals)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |daily_goals(com.campus.fitintent.models.DailyGoal).
              | Expected:
              |""".trimMargin() + _infoDailyGoals + """
              |
              | Found:
              |""".trimMargin() + _existingDailyGoals)
        }
        val _columnsStreaks: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsStreaks.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStreaks.put("userId", TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStreaks.put("type", TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStreaks.put("currentStreak", TableInfo.Column("currentStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStreaks.put("longestStreak", TableInfo.Column("longestStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStreaks.put("lastActivityDate", TableInfo.Column("lastActivityDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStreaks.put("startDate", TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsStreaks.put("isActive", TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysStreaks: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysStreaks.add(TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", listOf("userId"), listOf("id")))
        val _indicesStreaks: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesStreaks.add(TableInfo.Index("index_streaks_userId", false, listOf("userId"), listOf("ASC")))
        val _infoStreaks: TableInfo = TableInfo("streaks", _columnsStreaks, _foreignKeysStreaks, _indicesStreaks)
        val _existingStreaks: TableInfo = read(connection, "streaks")
        if (!_infoStreaks.equals(_existingStreaks)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |streaks(com.campus.fitintent.models.Streak).
              | Expected:
              |""".trimMargin() + _infoStreaks + """
              |
              | Found:
              |""".trimMargin() + _existingStreaks)
        }
        val _columnsBadges: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsBadges.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBadges.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBadges.put("description", TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBadges.put("type", TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBadges.put("rarity", TableInfo.Column("rarity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBadges.put("iconResId", TableInfo.Column("iconResId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBadges.put("targetProgress", TableInfo.Column("targetProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBadges.put("isActive", TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBadges.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBadges.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysBadges: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesBadges: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoBadges: TableInfo = TableInfo("badges", _columnsBadges, _foreignKeysBadges, _indicesBadges)
        val _existingBadges: TableInfo = read(connection, "badges")
        if (!_infoBadges.equals(_existingBadges)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |badges(com.campus.fitintent.models.Badge).
              | Expected:
              |""".trimMargin() + _infoBadges + """
              |
              | Found:
              |""".trimMargin() + _existingBadges)
        }
        val _columnsUserBadges: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsUserBadges.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserBadges.put("userId", TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserBadges.put("badgeId", TableInfo.Column("badgeId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserBadges.put("currentProgress", TableInfo.Column("currentProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserBadges.put("isUnlocked", TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserBadges.put("unlockedAt", TableInfo.Column("unlockedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserBadges.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserBadges.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUserBadges: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysUserBadges.add(TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", listOf("userId"), listOf("id")))
        _foreignKeysUserBadges.add(TableInfo.ForeignKey("badges", "CASCADE", "NO ACTION", listOf("badgeId"), listOf("id")))
        val _indicesUserBadges: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesUserBadges.add(TableInfo.Index("index_user_badges_userId", false, listOf("userId"), listOf("ASC")))
        _indicesUserBadges.add(TableInfo.Index("index_user_badges_badgeId", false, listOf("badgeId"), listOf("ASC")))
        _indicesUserBadges.add(TableInfo.Index("index_user_badges_userId_badgeId", true, listOf("userId", "badgeId"), listOf("ASC", "ASC")))
        val _infoUserBadges: TableInfo = TableInfo("user_badges", _columnsUserBadges, _foreignKeysUserBadges, _indicesUserBadges)
        val _existingUserBadges: TableInfo = read(connection, "user_badges")
        if (!_infoUserBadges.equals(_existingUserBadges)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |user_badges(com.campus.fitintent.models.UserBadge).
              | Expected:
              |""".trimMargin() + _infoUserBadges + """
              |
              | Found:
              |""".trimMargin() + _existingUserBadges)
        }
        val _columnsNutritionTips: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsNutritionTips.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("title", TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("description", TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("category", TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("benefits", TableInfo.Column("benefits", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("difficulty", TableInfo.Column("difficulty", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("icon", TableInfo.Column("icon", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("imageUrl", TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("tags", TableInfo.Column("tags", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("isActive", TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("priority", TableInfo.Column("priority", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("seasonalTip", TableInfo.Column("seasonalTip", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsNutritionTips.put("updatedAt", TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysNutritionTips: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesNutritionTips: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoNutritionTips: TableInfo = TableInfo("nutrition_tips", _columnsNutritionTips, _foreignKeysNutritionTips, _indicesNutritionTips)
        val _existingNutritionTips: TableInfo = read(connection, "nutrition_tips")
        if (!_infoNutritionTips.equals(_existingNutritionTips)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |nutrition_tips(com.campus.fitintent.models.NutritionTip).
              | Expected:
              |""".trimMargin() + _infoNutritionTips + """
              |
              | Found:
              |""".trimMargin() + _existingNutritionTips)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "users", "habits", "habit_logs", "workouts", "exercises", "workout_sessions", "daily_goals", "streaks", "badges", "user_badges", "nutrition_tips")
  }

  public override fun clearAllTables() {
    super.performClear(true, "users", "habits", "habit_logs", "workouts", "exercises", "workout_sessions", "daily_goals", "streaks", "badges", "user_badges", "nutrition_tips")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(UserDao::class, UserDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(HabitDao::class, HabitDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(HabitLogDao::class, HabitLogDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(WorkoutDao::class, WorkoutDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ExerciseDao::class, ExerciseDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(WorkoutSessionDao::class, WorkoutSessionDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(DailyGoalDao::class, DailyGoalDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(StreakDao::class, StreakDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(UserBadgeDao::class, UserBadgeDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(NutritionTipDao::class, NutritionTipDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun userDao(): UserDao = _userDao.value

  public override fun habitDao(): HabitDao = _habitDao.value

  public override fun habitLogDao(): HabitLogDao = _habitLogDao.value

  public override fun workoutDao(): WorkoutDao = _workoutDao.value

  public override fun exerciseDao(): ExerciseDao = _exerciseDao.value

  public override fun workoutSessionDao(): WorkoutSessionDao = _workoutSessionDao.value

  public override fun dailyGoalDao(): DailyGoalDao = _dailyGoalDao.value

  public override fun streakDao(): StreakDao = _streakDao.value

  public override fun userBadgeDao(): UserBadgeDao = _userBadgeDao.value

  public override fun nutritionTipDao(): NutritionTipDao = _nutritionTipDao.value
}
