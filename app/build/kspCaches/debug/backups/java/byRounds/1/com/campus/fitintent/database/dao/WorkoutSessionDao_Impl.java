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
import com.campus.fitintent.models.WorkoutSession;
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
public final class WorkoutSessionDao_Impl implements WorkoutSessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WorkoutSession> __insertionAdapterOfWorkoutSession;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<WorkoutSession> __deletionAdapterOfWorkoutSession;

  private final EntityDeletionOrUpdateAdapter<WorkoutSession> __updateAdapterOfWorkoutSession;

  private final SharedSQLiteStatement __preparedStmtOfCompleteSession;

  public WorkoutSessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWorkoutSession = new EntityInsertionAdapter<WorkoutSession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `workout_sessions` (`id`,`userId`,`workoutId`,`startTime`,`endTime`,`durationMinutes`,`caloriesBurned`,`averageHeartRate`,`maxHeartRate`,`notes`,`feelingRating`,`difficultyRating`,`isCompleted`,`completionPercentage`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutSession entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        if (entity.getWorkoutId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getWorkoutId());
        }
        final Long _tmp = __converters.dateToTimestamp(entity.getStartTime());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getEndTime());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp_1);
        }
        if (entity.getDurationMinutes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getDurationMinutes());
        }
        if (entity.getCaloriesBurned() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCaloriesBurned());
        }
        if (entity.getAverageHeartRate() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getAverageHeartRate());
        }
        if (entity.getMaxHeartRate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getMaxHeartRate());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getNotes());
        }
        if (entity.getFeelingRating() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getFeelingRating());
        }
        if (entity.getDifficultyRating() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getDifficultyRating());
        }
        final int _tmp_2 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(13, _tmp_2);
        statement.bindDouble(14, entity.getCompletionPercentage());
      }
    };
    this.__deletionAdapterOfWorkoutSession = new EntityDeletionOrUpdateAdapter<WorkoutSession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `workout_sessions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutSession entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfWorkoutSession = new EntityDeletionOrUpdateAdapter<WorkoutSession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `workout_sessions` SET `id` = ?,`userId` = ?,`workoutId` = ?,`startTime` = ?,`endTime` = ?,`durationMinutes` = ?,`caloriesBurned` = ?,`averageHeartRate` = ?,`maxHeartRate` = ?,`notes` = ?,`feelingRating` = ?,`difficultyRating` = ?,`isCompleted` = ?,`completionPercentage` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WorkoutSession entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        if (entity.getWorkoutId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getWorkoutId());
        }
        final Long _tmp = __converters.dateToTimestamp(entity.getStartTime());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getEndTime());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp_1);
        }
        if (entity.getDurationMinutes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getDurationMinutes());
        }
        if (entity.getCaloriesBurned() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCaloriesBurned());
        }
        if (entity.getAverageHeartRate() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getAverageHeartRate());
        }
        if (entity.getMaxHeartRate() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getMaxHeartRate());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getNotes());
        }
        if (entity.getFeelingRating() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getFeelingRating());
        }
        if (entity.getDifficultyRating() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getDifficultyRating());
        }
        final int _tmp_2 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(13, _tmp_2);
        statement.bindDouble(14, entity.getCompletionPercentage());
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfCompleteSession = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE workout_sessions\n"
                + "        SET endTime = ?,\n"
                + "            durationMinutes = ?,\n"
                + "            caloriesBurned = ?,\n"
                + "            isCompleted = ?,\n"
                + "            completionPercentage = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
  }

  @Override
  public Object insertSession(final WorkoutSession session,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWorkoutSession.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSession(final WorkoutSession session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWorkoutSession.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSession(final WorkoutSession session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWorkoutSession.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object completeSession(final long sessionId, final Date endTime, final int duration,
      final int calories, final boolean isCompleted, final float percentage,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfCompleteSession.acquire();
        int _argIndex = 1;
        final Long _tmp = __converters.dateToTimestamp(endTime);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, duration);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, calories);
        _argIndex = 4;
        final int _tmp_1 = isCompleted ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp_1);
        _argIndex = 5;
        _stmt.bindDouble(_argIndex, percentage);
        _argIndex = 6;
        _stmt.bindLong(_argIndex, sessionId);
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
          __preparedStmtOfCompleteSession.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WorkoutSession>> getSessionsByUser(final long userId) {
    final String _sql = "SELECT * FROM workout_sessions WHERE userId = ? ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"workout_sessions"}, new Callable<List<WorkoutSession>>() {
      @Override
      @NonNull
      public List<WorkoutSession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfWorkoutId = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfAverageHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "averageHeartRate");
          final int _cursorIndexOfMaxHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHeartRate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfFeelingRating = CursorUtil.getColumnIndexOrThrow(_cursor, "feelingRating");
          final int _cursorIndexOfDifficultyRating = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyRating");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCompletionPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "completionPercentage");
          final List<WorkoutSession> _result = new ArrayList<WorkoutSession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WorkoutSession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Long _tmpWorkoutId;
            if (_cursor.isNull(_cursorIndexOfWorkoutId)) {
              _tmpWorkoutId = null;
            } else {
              _tmpWorkoutId = _cursor.getLong(_cursorIndexOfWorkoutId);
            }
            final Date _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartTime = _tmp_1;
            }
            final Date _tmpEndTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_2);
            final Integer _tmpDurationMinutes;
            if (_cursor.isNull(_cursorIndexOfDurationMinutes)) {
              _tmpDurationMinutes = null;
            } else {
              _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            }
            final Integer _tmpCaloriesBurned;
            if (_cursor.isNull(_cursorIndexOfCaloriesBurned)) {
              _tmpCaloriesBurned = null;
            } else {
              _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            }
            final Integer _tmpAverageHeartRate;
            if (_cursor.isNull(_cursorIndexOfAverageHeartRate)) {
              _tmpAverageHeartRate = null;
            } else {
              _tmpAverageHeartRate = _cursor.getInt(_cursorIndexOfAverageHeartRate);
            }
            final Integer _tmpMaxHeartRate;
            if (_cursor.isNull(_cursorIndexOfMaxHeartRate)) {
              _tmpMaxHeartRate = null;
            } else {
              _tmpMaxHeartRate = _cursor.getInt(_cursorIndexOfMaxHeartRate);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Integer _tmpFeelingRating;
            if (_cursor.isNull(_cursorIndexOfFeelingRating)) {
              _tmpFeelingRating = null;
            } else {
              _tmpFeelingRating = _cursor.getInt(_cursorIndexOfFeelingRating);
            }
            final Integer _tmpDifficultyRating;
            if (_cursor.isNull(_cursorIndexOfDifficultyRating)) {
              _tmpDifficultyRating = null;
            } else {
              _tmpDifficultyRating = _cursor.getInt(_cursorIndexOfDifficultyRating);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_3 != 0;
            final float _tmpCompletionPercentage;
            _tmpCompletionPercentage = _cursor.getFloat(_cursorIndexOfCompletionPercentage);
            _item = new WorkoutSession(_tmpId,_tmpUserId,_tmpWorkoutId,_tmpStartTime,_tmpEndTime,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpAverageHeartRate,_tmpMaxHeartRate,_tmpNotes,_tmpFeelingRating,_tmpDifficultyRating,_tmpIsCompleted,_tmpCompletionPercentage);
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
  public Object getSessionsByUserAndDate(final long userId, final Date date,
      final Continuation<? super List<WorkoutSession>> $completion) {
    final String _sql = "SELECT * FROM workout_sessions WHERE userId = ? AND DATE(startTime / 1000, 'unixepoch') = DATE(? / 1000, 'unixepoch')";
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
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<WorkoutSession>>() {
      @Override
      @NonNull
      public List<WorkoutSession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfWorkoutId = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfAverageHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "averageHeartRate");
          final int _cursorIndexOfMaxHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHeartRate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfFeelingRating = CursorUtil.getColumnIndexOrThrow(_cursor, "feelingRating");
          final int _cursorIndexOfDifficultyRating = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyRating");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCompletionPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "completionPercentage");
          final List<WorkoutSession> _result = new ArrayList<WorkoutSession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WorkoutSession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Long _tmpWorkoutId;
            if (_cursor.isNull(_cursorIndexOfWorkoutId)) {
              _tmpWorkoutId = null;
            } else {
              _tmpWorkoutId = _cursor.getLong(_cursorIndexOfWorkoutId);
            }
            final Date _tmpStartTime;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartTime = _tmp_2;
            }
            final Date _tmpEndTime;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_3);
            final Integer _tmpDurationMinutes;
            if (_cursor.isNull(_cursorIndexOfDurationMinutes)) {
              _tmpDurationMinutes = null;
            } else {
              _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            }
            final Integer _tmpCaloriesBurned;
            if (_cursor.isNull(_cursorIndexOfCaloriesBurned)) {
              _tmpCaloriesBurned = null;
            } else {
              _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            }
            final Integer _tmpAverageHeartRate;
            if (_cursor.isNull(_cursorIndexOfAverageHeartRate)) {
              _tmpAverageHeartRate = null;
            } else {
              _tmpAverageHeartRate = _cursor.getInt(_cursorIndexOfAverageHeartRate);
            }
            final Integer _tmpMaxHeartRate;
            if (_cursor.isNull(_cursorIndexOfMaxHeartRate)) {
              _tmpMaxHeartRate = null;
            } else {
              _tmpMaxHeartRate = _cursor.getInt(_cursorIndexOfMaxHeartRate);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Integer _tmpFeelingRating;
            if (_cursor.isNull(_cursorIndexOfFeelingRating)) {
              _tmpFeelingRating = null;
            } else {
              _tmpFeelingRating = _cursor.getInt(_cursorIndexOfFeelingRating);
            }
            final Integer _tmpDifficultyRating;
            if (_cursor.isNull(_cursorIndexOfDifficultyRating)) {
              _tmpDifficultyRating = null;
            } else {
              _tmpDifficultyRating = _cursor.getInt(_cursorIndexOfDifficultyRating);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_4 != 0;
            final float _tmpCompletionPercentage;
            _tmpCompletionPercentage = _cursor.getFloat(_cursorIndexOfCompletionPercentage);
            _item = new WorkoutSession(_tmpId,_tmpUserId,_tmpWorkoutId,_tmpStartTime,_tmpEndTime,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpAverageHeartRate,_tmpMaxHeartRate,_tmpNotes,_tmpFeelingRating,_tmpDifficultyRating,_tmpIsCompleted,_tmpCompletionPercentage);
            _result.add(_item);
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
  public Object getSessionById(final long sessionId,
      final Continuation<? super WorkoutSession> $completion) {
    final String _sql = "SELECT * FROM workout_sessions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WorkoutSession>() {
      @Override
      @Nullable
      public WorkoutSession call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfWorkoutId = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfAverageHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "averageHeartRate");
          final int _cursorIndexOfMaxHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHeartRate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfFeelingRating = CursorUtil.getColumnIndexOrThrow(_cursor, "feelingRating");
          final int _cursorIndexOfDifficultyRating = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyRating");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCompletionPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "completionPercentage");
          final WorkoutSession _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Long _tmpWorkoutId;
            if (_cursor.isNull(_cursorIndexOfWorkoutId)) {
              _tmpWorkoutId = null;
            } else {
              _tmpWorkoutId = _cursor.getLong(_cursorIndexOfWorkoutId);
            }
            final Date _tmpStartTime;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartTime = _tmp_1;
            }
            final Date _tmpEndTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_2);
            final Integer _tmpDurationMinutes;
            if (_cursor.isNull(_cursorIndexOfDurationMinutes)) {
              _tmpDurationMinutes = null;
            } else {
              _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            }
            final Integer _tmpCaloriesBurned;
            if (_cursor.isNull(_cursorIndexOfCaloriesBurned)) {
              _tmpCaloriesBurned = null;
            } else {
              _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            }
            final Integer _tmpAverageHeartRate;
            if (_cursor.isNull(_cursorIndexOfAverageHeartRate)) {
              _tmpAverageHeartRate = null;
            } else {
              _tmpAverageHeartRate = _cursor.getInt(_cursorIndexOfAverageHeartRate);
            }
            final Integer _tmpMaxHeartRate;
            if (_cursor.isNull(_cursorIndexOfMaxHeartRate)) {
              _tmpMaxHeartRate = null;
            } else {
              _tmpMaxHeartRate = _cursor.getInt(_cursorIndexOfMaxHeartRate);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Integer _tmpFeelingRating;
            if (_cursor.isNull(_cursorIndexOfFeelingRating)) {
              _tmpFeelingRating = null;
            } else {
              _tmpFeelingRating = _cursor.getInt(_cursorIndexOfFeelingRating);
            }
            final Integer _tmpDifficultyRating;
            if (_cursor.isNull(_cursorIndexOfDifficultyRating)) {
              _tmpDifficultyRating = null;
            } else {
              _tmpDifficultyRating = _cursor.getInt(_cursorIndexOfDifficultyRating);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_3 != 0;
            final float _tmpCompletionPercentage;
            _tmpCompletionPercentage = _cursor.getFloat(_cursorIndexOfCompletionPercentage);
            _result = new WorkoutSession(_tmpId,_tmpUserId,_tmpWorkoutId,_tmpStartTime,_tmpEndTime,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpAverageHeartRate,_tmpMaxHeartRate,_tmpNotes,_tmpFeelingRating,_tmpDifficultyRating,_tmpIsCompleted,_tmpCompletionPercentage);
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
  public Object getCompletedSessionCount(final long userId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) FROM workout_sessions\n"
            + "        WHERE userId = ?\n"
            + "        AND isCompleted = 1\n"
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
  public Object getTotalWorkoutMinutes(final long userId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT SUM(durationMinutes) FROM workout_sessions\n"
            + "        WHERE userId = ?\n"
            + "        AND isCompleted = 1\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public Object getTotalCaloriesBurned(final long userId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT SUM(caloriesBurned) FROM workout_sessions\n"
            + "        WHERE userId = ?\n"
            + "        AND isCompleted = 1\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public Flow<List<WorkoutSession>> getSessionsInDateRange(final long userId, final Date startDate,
      final Date endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM workout_sessions\n"
            + "        WHERE userId = ?\n"
            + "        AND startTime BETWEEN ? AND ?\n"
            + "        ORDER BY startTime DESC\n"
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"workout_sessions"}, new Callable<List<WorkoutSession>>() {
      @Override
      @NonNull
      public List<WorkoutSession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfWorkoutId = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfAverageHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "averageHeartRate");
          final int _cursorIndexOfMaxHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "maxHeartRate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfFeelingRating = CursorUtil.getColumnIndexOrThrow(_cursor, "feelingRating");
          final int _cursorIndexOfDifficultyRating = CursorUtil.getColumnIndexOrThrow(_cursor, "difficultyRating");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCompletionPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "completionPercentage");
          final List<WorkoutSession> _result = new ArrayList<WorkoutSession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WorkoutSession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Long _tmpWorkoutId;
            if (_cursor.isNull(_cursorIndexOfWorkoutId)) {
              _tmpWorkoutId = null;
            } else {
              _tmpWorkoutId = _cursor.getLong(_cursorIndexOfWorkoutId);
            }
            final Date _tmpStartTime;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartTime = _tmp_3;
            }
            final Date _tmpEndTime;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.fromTimestamp(_tmp_4);
            final Integer _tmpDurationMinutes;
            if (_cursor.isNull(_cursorIndexOfDurationMinutes)) {
              _tmpDurationMinutes = null;
            } else {
              _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            }
            final Integer _tmpCaloriesBurned;
            if (_cursor.isNull(_cursorIndexOfCaloriesBurned)) {
              _tmpCaloriesBurned = null;
            } else {
              _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            }
            final Integer _tmpAverageHeartRate;
            if (_cursor.isNull(_cursorIndexOfAverageHeartRate)) {
              _tmpAverageHeartRate = null;
            } else {
              _tmpAverageHeartRate = _cursor.getInt(_cursorIndexOfAverageHeartRate);
            }
            final Integer _tmpMaxHeartRate;
            if (_cursor.isNull(_cursorIndexOfMaxHeartRate)) {
              _tmpMaxHeartRate = null;
            } else {
              _tmpMaxHeartRate = _cursor.getInt(_cursorIndexOfMaxHeartRate);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Integer _tmpFeelingRating;
            if (_cursor.isNull(_cursorIndexOfFeelingRating)) {
              _tmpFeelingRating = null;
            } else {
              _tmpFeelingRating = _cursor.getInt(_cursorIndexOfFeelingRating);
            }
            final Integer _tmpDifficultyRating;
            if (_cursor.isNull(_cursorIndexOfDifficultyRating)) {
              _tmpDifficultyRating = null;
            } else {
              _tmpDifficultyRating = _cursor.getInt(_cursorIndexOfDifficultyRating);
            }
            final boolean _tmpIsCompleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_5 != 0;
            final float _tmpCompletionPercentage;
            _tmpCompletionPercentage = _cursor.getFloat(_cursorIndexOfCompletionPercentage);
            _item = new WorkoutSession(_tmpId,_tmpUserId,_tmpWorkoutId,_tmpStartTime,_tmpEndTime,_tmpDurationMinutes,_tmpCaloriesBurned,_tmpAverageHeartRate,_tmpMaxHeartRate,_tmpNotes,_tmpFeelingRating,_tmpDifficultyRating,_tmpIsCompleted,_tmpCompletionPercentage);
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
  public Object getAverageFeelingRating(final long userId,
      final Continuation<? super Float> $completion) {
    final String _sql = "\n"
            + "        SELECT AVG(feelingRating) FROM workout_sessions\n"
            + "        WHERE userId = ?\n"
            + "        AND isCompleted = 1\n"
            + "        AND feelingRating IS NOT NULL\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
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
