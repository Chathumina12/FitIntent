package com.campus.fitintent.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.campus.fitintent.database.Converters;
import com.campus.fitintent.models.DailyGoal;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DailyGoalDao_Impl implements DailyGoalDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DailyGoal> __insertionAdapterOfDailyGoal;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<DailyGoal> __updateAdapterOfDailyGoal;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCalories;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSteps;

  private final SharedSQLiteStatement __preparedStmtOfUpdateWater;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSleep;

  private final SharedSQLiteStatement __preparedStmtOfUpdateWorkoutMinutes;

  public DailyGoalDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDailyGoal = new EntityInsertionAdapter<DailyGoal>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `daily_goals` (`id`,`userId`,`date`,`calorieGoal`,`stepGoal`,`waterGoal`,`sleepGoal`,`workoutMinutesGoal`,`caloriesBurned`,`stepsTaken`,`waterIntake`,`sleepHours`,`workoutMinutes`,`isCalorieGoalMet`,`isStepGoalMet`,`isWaterGoalMet`,`isSleepGoalMet`,`isWorkoutGoalMet`,`overallCompletionPercentage`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyGoal entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        if (entity.getCalorieGoal() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getCalorieGoal());
        }
        if (entity.getStepGoal() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getStepGoal());
        }
        if (entity.getWaterGoal() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getWaterGoal());
        }
        if (entity.getSleepGoal() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getSleepGoal());
        }
        if (entity.getWorkoutMinutesGoal() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getWorkoutMinutesGoal());
        }
        statement.bindLong(9, entity.getCaloriesBurned());
        statement.bindLong(10, entity.getStepsTaken());
        statement.bindLong(11, entity.getWaterIntake());
        statement.bindDouble(12, entity.getSleepHours());
        statement.bindLong(13, entity.getWorkoutMinutes());
        final int _tmp_1 = entity.isCalorieGoalMet() ? 1 : 0;
        statement.bindLong(14, _tmp_1);
        final int _tmp_2 = entity.isStepGoalMet() ? 1 : 0;
        statement.bindLong(15, _tmp_2);
        final int _tmp_3 = entity.isWaterGoalMet() ? 1 : 0;
        statement.bindLong(16, _tmp_3);
        final int _tmp_4 = entity.isSleepGoalMet() ? 1 : 0;
        statement.bindLong(17, _tmp_4);
        final int _tmp_5 = entity.isWorkoutGoalMet() ? 1 : 0;
        statement.bindLong(18, _tmp_5);
        statement.bindDouble(19, entity.getOverallCompletionPercentage());
        final Long _tmp_6 = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(20);
        } else {
          statement.bindLong(20, _tmp_6);
        }
      }
    };
    this.__updateAdapterOfDailyGoal = new EntityDeletionOrUpdateAdapter<DailyGoal>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `daily_goals` SET `id` = ?,`userId` = ?,`date` = ?,`calorieGoal` = ?,`stepGoal` = ?,`waterGoal` = ?,`sleepGoal` = ?,`workoutMinutesGoal` = ?,`caloriesBurned` = ?,`stepsTaken` = ?,`waterIntake` = ?,`sleepHours` = ?,`workoutMinutes` = ?,`isCalorieGoalMet` = ?,`isStepGoalMet` = ?,`isWaterGoalMet` = ?,`isSleepGoalMet` = ?,`isWorkoutGoalMet` = ?,`overallCompletionPercentage` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyGoal entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        if (entity.getCalorieGoal() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getCalorieGoal());
        }
        if (entity.getStepGoal() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getStepGoal());
        }
        if (entity.getWaterGoal() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getWaterGoal());
        }
        if (entity.getSleepGoal() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getSleepGoal());
        }
        if (entity.getWorkoutMinutesGoal() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getWorkoutMinutesGoal());
        }
        statement.bindLong(9, entity.getCaloriesBurned());
        statement.bindLong(10, entity.getStepsTaken());
        statement.bindLong(11, entity.getWaterIntake());
        statement.bindDouble(12, entity.getSleepHours());
        statement.bindLong(13, entity.getWorkoutMinutes());
        final int _tmp_1 = entity.isCalorieGoalMet() ? 1 : 0;
        statement.bindLong(14, _tmp_1);
        final int _tmp_2 = entity.isStepGoalMet() ? 1 : 0;
        statement.bindLong(15, _tmp_2);
        final int _tmp_3 = entity.isWaterGoalMet() ? 1 : 0;
        statement.bindLong(16, _tmp_3);
        final int _tmp_4 = entity.isSleepGoalMet() ? 1 : 0;
        statement.bindLong(17, _tmp_4);
        final int _tmp_5 = entity.isWorkoutGoalMet() ? 1 : 0;
        statement.bindLong(18, _tmp_5);
        statement.bindDouble(19, entity.getOverallCompletionPercentage());
        final Long _tmp_6 = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(20);
        } else {
          statement.bindLong(20, _tmp_6);
        }
        statement.bindLong(21, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateCalories = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE daily_goals\n"
                + "        SET caloriesBurned = ?,\n"
                + "            isCalorieGoalMet = CASE WHEN ? >= calorieGoal THEN 1 ELSE 0 END\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSteps = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE daily_goals\n"
                + "        SET stepsTaken = ?,\n"
                + "            isStepGoalMet = CASE WHEN ? >= stepGoal THEN 1 ELSE 0 END\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateWater = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE daily_goals\n"
                + "        SET waterIntake = ?,\n"
                + "            isWaterGoalMet = CASE WHEN ? >= waterGoal THEN 1 ELSE 0 END\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSleep = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE daily_goals\n"
                + "        SET sleepHours = ?,\n"
                + "            isSleepGoalMet = CASE WHEN ? >= sleepGoal THEN 1 ELSE 0 END\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateWorkoutMinutes = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE daily_goals\n"
                + "        SET workoutMinutes = ?,\n"
                + "            isWorkoutGoalMet = CASE WHEN ? >= workoutMinutesGoal THEN 1 ELSE 0 END\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
  }

  @Override
  public Object insertGoal(final DailyGoal goal, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfDailyGoal.insertAndReturnId(goal);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateGoal(final DailyGoal goal, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfDailyGoal.handle(goal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCalories(final long goalId, final int calories,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCalories.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, calories);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, calories);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, goalId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateCalories.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSteps(final long goalId, final int steps,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSteps.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, steps);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, steps);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, goalId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateSteps.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWater(final long goalId, final int water,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateWater.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, water);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, water);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, goalId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateWater.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSleep(final long goalId, final float sleep,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSleep.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, sleep);
        _argIndex = 2;
        _stmt.bindDouble(_argIndex, sleep);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, goalId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateSleep.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWorkoutMinutes(final long goalId, final int minutes,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateWorkoutMinutes.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, minutes);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, minutes);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, goalId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateWorkoutMinutes.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getGoalByUserAndDate(final long userId, final Date date,
      final Continuation<? super DailyGoal> $completion) {
    final String _sql = "SELECT * FROM daily_goals WHERE userId = ? AND date = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    final Long _tmp = __converters.dateToTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DailyGoal>() {
      @Override
      @Nullable
      public DailyGoal call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCalorieGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "calorieGoal");
          final int _cursorIndexOfStepGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "stepGoal");
          final int _cursorIndexOfWaterGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "waterGoal");
          final int _cursorIndexOfSleepGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepGoal");
          final int _cursorIndexOfWorkoutMinutesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutMinutesGoal");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfStepsTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "stepsTaken");
          final int _cursorIndexOfWaterIntake = CursorUtil.getColumnIndexOrThrow(_cursor, "waterIntake");
          final int _cursorIndexOfSleepHours = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepHours");
          final int _cursorIndexOfWorkoutMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutMinutes");
          final int _cursorIndexOfIsCalorieGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isCalorieGoalMet");
          final int _cursorIndexOfIsStepGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isStepGoalMet");
          final int _cursorIndexOfIsWaterGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isWaterGoalMet");
          final int _cursorIndexOfIsSleepGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isSleepGoalMet");
          final int _cursorIndexOfIsWorkoutGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isWorkoutGoalMet");
          final int _cursorIndexOfOverallCompletionPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "overallCompletionPercentage");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final DailyGoal _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_2;
            }
            final Integer _tmpCalorieGoal;
            if (_cursor.isNull(_cursorIndexOfCalorieGoal)) {
              _tmpCalorieGoal = null;
            } else {
              _tmpCalorieGoal = _cursor.getInt(_cursorIndexOfCalorieGoal);
            }
            final Integer _tmpStepGoal;
            if (_cursor.isNull(_cursorIndexOfStepGoal)) {
              _tmpStepGoal = null;
            } else {
              _tmpStepGoal = _cursor.getInt(_cursorIndexOfStepGoal);
            }
            final Integer _tmpWaterGoal;
            if (_cursor.isNull(_cursorIndexOfWaterGoal)) {
              _tmpWaterGoal = null;
            } else {
              _tmpWaterGoal = _cursor.getInt(_cursorIndexOfWaterGoal);
            }
            final Float _tmpSleepGoal;
            if (_cursor.isNull(_cursorIndexOfSleepGoal)) {
              _tmpSleepGoal = null;
            } else {
              _tmpSleepGoal = _cursor.getFloat(_cursorIndexOfSleepGoal);
            }
            final Integer _tmpWorkoutMinutesGoal;
            if (_cursor.isNull(_cursorIndexOfWorkoutMinutesGoal)) {
              _tmpWorkoutMinutesGoal = null;
            } else {
              _tmpWorkoutMinutesGoal = _cursor.getInt(_cursorIndexOfWorkoutMinutesGoal);
            }
            final int _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            final int _tmpStepsTaken;
            _tmpStepsTaken = _cursor.getInt(_cursorIndexOfStepsTaken);
            final int _tmpWaterIntake;
            _tmpWaterIntake = _cursor.getInt(_cursorIndexOfWaterIntake);
            final float _tmpSleepHours;
            _tmpSleepHours = _cursor.getFloat(_cursorIndexOfSleepHours);
            final int _tmpWorkoutMinutes;
            _tmpWorkoutMinutes = _cursor.getInt(_cursorIndexOfWorkoutMinutes);
            final boolean _tmpIsCalorieGoalMet;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCalorieGoalMet);
            _tmpIsCalorieGoalMet = _tmp_3 != 0;
            final boolean _tmpIsStepGoalMet;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsStepGoalMet);
            _tmpIsStepGoalMet = _tmp_4 != 0;
            final boolean _tmpIsWaterGoalMet;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsWaterGoalMet);
            _tmpIsWaterGoalMet = _tmp_5 != 0;
            final boolean _tmpIsSleepGoalMet;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsSleepGoalMet);
            _tmpIsSleepGoalMet = _tmp_6 != 0;
            final boolean _tmpIsWorkoutGoalMet;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsWorkoutGoalMet);
            _tmpIsWorkoutGoalMet = _tmp_7 != 0;
            final float _tmpOverallCompletionPercentage;
            _tmpOverallCompletionPercentage = _cursor.getFloat(_cursorIndexOfOverallCompletionPercentage);
            final Date _tmpUpdatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_9 = __converters.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            _result = new DailyGoal(_tmpId,_tmpUserId,_tmpDate,_tmpCalorieGoal,_tmpStepGoal,_tmpWaterGoal,_tmpSleepGoal,_tmpWorkoutMinutesGoal,_tmpCaloriesBurned,_tmpStepsTaken,_tmpWaterIntake,_tmpSleepHours,_tmpWorkoutMinutes,_tmpIsCalorieGoalMet,_tmpIsStepGoalMet,_tmpIsWaterGoalMet,_tmpIsSleepGoalMet,_tmpIsWorkoutGoalMet,_tmpOverallCompletionPercentage,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<DailyGoal>> getRecentGoals(final long userId, final int limit) {
    final String _sql = "SELECT * FROM daily_goals WHERE userId = ? ORDER BY date DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_goals"}, new Callable<List<DailyGoal>>() {
      @Override
      @NonNull
      public List<DailyGoal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCalorieGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "calorieGoal");
          final int _cursorIndexOfStepGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "stepGoal");
          final int _cursorIndexOfWaterGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "waterGoal");
          final int _cursorIndexOfSleepGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepGoal");
          final int _cursorIndexOfWorkoutMinutesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutMinutesGoal");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfStepsTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "stepsTaken");
          final int _cursorIndexOfWaterIntake = CursorUtil.getColumnIndexOrThrow(_cursor, "waterIntake");
          final int _cursorIndexOfSleepHours = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepHours");
          final int _cursorIndexOfWorkoutMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutMinutes");
          final int _cursorIndexOfIsCalorieGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isCalorieGoalMet");
          final int _cursorIndexOfIsStepGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isStepGoalMet");
          final int _cursorIndexOfIsWaterGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isWaterGoalMet");
          final int _cursorIndexOfIsSleepGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isSleepGoalMet");
          final int _cursorIndexOfIsWorkoutGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isWorkoutGoalMet");
          final int _cursorIndexOfOverallCompletionPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "overallCompletionPercentage");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<DailyGoal> _result = new ArrayList<DailyGoal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyGoal _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final Integer _tmpCalorieGoal;
            if (_cursor.isNull(_cursorIndexOfCalorieGoal)) {
              _tmpCalorieGoal = null;
            } else {
              _tmpCalorieGoal = _cursor.getInt(_cursorIndexOfCalorieGoal);
            }
            final Integer _tmpStepGoal;
            if (_cursor.isNull(_cursorIndexOfStepGoal)) {
              _tmpStepGoal = null;
            } else {
              _tmpStepGoal = _cursor.getInt(_cursorIndexOfStepGoal);
            }
            final Integer _tmpWaterGoal;
            if (_cursor.isNull(_cursorIndexOfWaterGoal)) {
              _tmpWaterGoal = null;
            } else {
              _tmpWaterGoal = _cursor.getInt(_cursorIndexOfWaterGoal);
            }
            final Float _tmpSleepGoal;
            if (_cursor.isNull(_cursorIndexOfSleepGoal)) {
              _tmpSleepGoal = null;
            } else {
              _tmpSleepGoal = _cursor.getFloat(_cursorIndexOfSleepGoal);
            }
            final Integer _tmpWorkoutMinutesGoal;
            if (_cursor.isNull(_cursorIndexOfWorkoutMinutesGoal)) {
              _tmpWorkoutMinutesGoal = null;
            } else {
              _tmpWorkoutMinutesGoal = _cursor.getInt(_cursorIndexOfWorkoutMinutesGoal);
            }
            final int _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            final int _tmpStepsTaken;
            _tmpStepsTaken = _cursor.getInt(_cursorIndexOfStepsTaken);
            final int _tmpWaterIntake;
            _tmpWaterIntake = _cursor.getInt(_cursorIndexOfWaterIntake);
            final float _tmpSleepHours;
            _tmpSleepHours = _cursor.getFloat(_cursorIndexOfSleepHours);
            final int _tmpWorkoutMinutes;
            _tmpWorkoutMinutes = _cursor.getInt(_cursorIndexOfWorkoutMinutes);
            final boolean _tmpIsCalorieGoalMet;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsCalorieGoalMet);
            _tmpIsCalorieGoalMet = _tmp_2 != 0;
            final boolean _tmpIsStepGoalMet;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsStepGoalMet);
            _tmpIsStepGoalMet = _tmp_3 != 0;
            final boolean _tmpIsWaterGoalMet;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsWaterGoalMet);
            _tmpIsWaterGoalMet = _tmp_4 != 0;
            final boolean _tmpIsSleepGoalMet;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsSleepGoalMet);
            _tmpIsSleepGoalMet = _tmp_5 != 0;
            final boolean _tmpIsWorkoutGoalMet;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsWorkoutGoalMet);
            _tmpIsWorkoutGoalMet = _tmp_6 != 0;
            final float _tmpOverallCompletionPercentage;
            _tmpOverallCompletionPercentage = _cursor.getFloat(_cursorIndexOfOverallCompletionPercentage);
            final Date _tmpUpdatedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_8 = __converters.fromTimestamp(_tmp_7);
            if (_tmp_8 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_8;
            }
            _item = new DailyGoal(_tmpId,_tmpUserId,_tmpDate,_tmpCalorieGoal,_tmpStepGoal,_tmpWaterGoal,_tmpSleepGoal,_tmpWorkoutMinutesGoal,_tmpCaloriesBurned,_tmpStepsTaken,_tmpWaterIntake,_tmpSleepHours,_tmpWorkoutMinutes,_tmpIsCalorieGoalMet,_tmpIsStepGoalMet,_tmpIsWaterGoalMet,_tmpIsSleepGoalMet,_tmpIsWorkoutGoalMet,_tmpOverallCompletionPercentage,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<DailyGoal>> getGoalsInDateRange(final long userId, final Date startDate,
      final Date endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM daily_goals\n"
            + "        WHERE userId = ?\n"
            + "        AND date BETWEEN ? AND ?\n"
            + "        ORDER BY date DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = __converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_goals"}, new Callable<List<DailyGoal>>() {
      @Override
      @NonNull
      public List<DailyGoal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCalorieGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "calorieGoal");
          final int _cursorIndexOfStepGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "stepGoal");
          final int _cursorIndexOfWaterGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "waterGoal");
          final int _cursorIndexOfSleepGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepGoal");
          final int _cursorIndexOfWorkoutMinutesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutMinutesGoal");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfStepsTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "stepsTaken");
          final int _cursorIndexOfWaterIntake = CursorUtil.getColumnIndexOrThrow(_cursor, "waterIntake");
          final int _cursorIndexOfSleepHours = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepHours");
          final int _cursorIndexOfWorkoutMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutMinutes");
          final int _cursorIndexOfIsCalorieGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isCalorieGoalMet");
          final int _cursorIndexOfIsStepGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isStepGoalMet");
          final int _cursorIndexOfIsWaterGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isWaterGoalMet");
          final int _cursorIndexOfIsSleepGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isSleepGoalMet");
          final int _cursorIndexOfIsWorkoutGoalMet = CursorUtil.getColumnIndexOrThrow(_cursor, "isWorkoutGoalMet");
          final int _cursorIndexOfOverallCompletionPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "overallCompletionPercentage");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<DailyGoal> _result = new ArrayList<DailyGoal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyGoal _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Date _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_3;
            }
            final Integer _tmpCalorieGoal;
            if (_cursor.isNull(_cursorIndexOfCalorieGoal)) {
              _tmpCalorieGoal = null;
            } else {
              _tmpCalorieGoal = _cursor.getInt(_cursorIndexOfCalorieGoal);
            }
            final Integer _tmpStepGoal;
            if (_cursor.isNull(_cursorIndexOfStepGoal)) {
              _tmpStepGoal = null;
            } else {
              _tmpStepGoal = _cursor.getInt(_cursorIndexOfStepGoal);
            }
            final Integer _tmpWaterGoal;
            if (_cursor.isNull(_cursorIndexOfWaterGoal)) {
              _tmpWaterGoal = null;
            } else {
              _tmpWaterGoal = _cursor.getInt(_cursorIndexOfWaterGoal);
            }
            final Float _tmpSleepGoal;
            if (_cursor.isNull(_cursorIndexOfSleepGoal)) {
              _tmpSleepGoal = null;
            } else {
              _tmpSleepGoal = _cursor.getFloat(_cursorIndexOfSleepGoal);
            }
            final Integer _tmpWorkoutMinutesGoal;
            if (_cursor.isNull(_cursorIndexOfWorkoutMinutesGoal)) {
              _tmpWorkoutMinutesGoal = null;
            } else {
              _tmpWorkoutMinutesGoal = _cursor.getInt(_cursorIndexOfWorkoutMinutesGoal);
            }
            final int _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            final int _tmpStepsTaken;
            _tmpStepsTaken = _cursor.getInt(_cursorIndexOfStepsTaken);
            final int _tmpWaterIntake;
            _tmpWaterIntake = _cursor.getInt(_cursorIndexOfWaterIntake);
            final float _tmpSleepHours;
            _tmpSleepHours = _cursor.getFloat(_cursorIndexOfSleepHours);
            final int _tmpWorkoutMinutes;
            _tmpWorkoutMinutes = _cursor.getInt(_cursorIndexOfWorkoutMinutes);
            final boolean _tmpIsCalorieGoalMet;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsCalorieGoalMet);
            _tmpIsCalorieGoalMet = _tmp_4 != 0;
            final boolean _tmpIsStepGoalMet;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsStepGoalMet);
            _tmpIsStepGoalMet = _tmp_5 != 0;
            final boolean _tmpIsWaterGoalMet;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsWaterGoalMet);
            _tmpIsWaterGoalMet = _tmp_6 != 0;
            final boolean _tmpIsSleepGoalMet;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsSleepGoalMet);
            _tmpIsSleepGoalMet = _tmp_7 != 0;
            final boolean _tmpIsWorkoutGoalMet;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsWorkoutGoalMet);
            _tmpIsWorkoutGoalMet = _tmp_8 != 0;
            final float _tmpOverallCompletionPercentage;
            _tmpOverallCompletionPercentage = _cursor.getFloat(_cursorIndexOfOverallCompletionPercentage);
            final Date _tmpUpdatedAt;
            final Long _tmp_9;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_9 = null;
            } else {
              _tmp_9 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_10 = __converters.fromTimestamp(_tmp_9);
            if (_tmp_10 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_10;
            }
            _item = new DailyGoal(_tmpId,_tmpUserId,_tmpDate,_tmpCalorieGoal,_tmpStepGoal,_tmpWaterGoal,_tmpSleepGoal,_tmpWorkoutMinutesGoal,_tmpCaloriesBurned,_tmpStepsTaken,_tmpWaterIntake,_tmpSleepHours,_tmpWorkoutMinutes,_tmpIsCalorieGoalMet,_tmpIsStepGoalMet,_tmpIsWaterGoalMet,_tmpIsSleepGoalMet,_tmpIsWorkoutGoalMet,_tmpOverallCompletionPercentage,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getPerfectDaysCount(final long userId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) FROM daily_goals\n"
            + "        WHERE userId = ?\n"
            + "        AND overallCompletionPercentage >= 100\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAverageCompletionInRange(final long userId, final Date startDate,
      final Date endDate, final Continuation<? super Float> $completion) {
    final String _sql = "\n"
            + "        SELECT AVG(overallCompletionPercentage) FROM daily_goals\n"
            + "        WHERE userId = ?\n"
            + "        AND date BETWEEN ? AND ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = __converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp_2;
            if (_cursor.isNull(0)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getFloat(0);
            }
            _result = _tmp_2;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
