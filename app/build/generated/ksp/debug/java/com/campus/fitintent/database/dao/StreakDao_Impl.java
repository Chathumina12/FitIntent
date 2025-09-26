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
import com.campus.fitintent.models.Streak;
import com.campus.fitintent.models.StreakType;
import java.lang.Class;
import java.lang.Exception;
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
public final class StreakDao_Impl implements StreakDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Streak> __insertionAdapterOfStreak;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Streak> __updateAdapterOfStreak;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStreakCount;

  private final SharedSQLiteStatement __preparedStmtOfBreakStreak;

  public StreakDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStreak = new EntityInsertionAdapter<Streak>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `streaks` (`id`,`userId`,`type`,`currentStreak`,`longestStreak`,`lastActivityDate`,`startDate`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Streak entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        final String _tmp = __converters.fromStreakType(entity.getType());
        statement.bindString(3, _tmp);
        statement.bindLong(4, entity.getCurrentStreak());
        statement.bindLong(5, entity.getLongestStreak());
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getLastActivityDate());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getStartDate());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_2);
        }
        final int _tmp_3 = entity.isActive() ? 1 : 0;
        statement.bindLong(8, _tmp_3);
      }
    };
    this.__updateAdapterOfStreak = new EntityDeletionOrUpdateAdapter<Streak>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `streaks` SET `id` = ?,`userId` = ?,`type` = ?,`currentStreak` = ?,`longestStreak` = ?,`lastActivityDate` = ?,`startDate` = ?,`isActive` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Streak entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        final String _tmp = __converters.fromStreakType(entity.getType());
        statement.bindString(3, _tmp);
        statement.bindLong(4, entity.getCurrentStreak());
        statement.bindLong(5, entity.getLongestStreak());
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getLastActivityDate());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getStartDate());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_2);
        }
        final int _tmp_3 = entity.isActive() ? 1 : 0;
        statement.bindLong(8, _tmp_3);
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateStreakCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE streaks\n"
                + "        SET currentStreak = ?,\n"
                + "            longestStreak = MAX(longestStreak, ?),\n"
                + "            lastActivityDate = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfBreakStreak = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE streaks SET currentStreak = 0, isActive = 0 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertStreak(final Streak streak, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfStreak.insertAndReturnId(streak);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStreak(final Streak streak, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStreak.handle(streak);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStreakCount(final long streakId, final int current, final Date date,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStreakCount.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, current);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, current);
        _argIndex = 3;
        final Long _tmp = __converters.dateToTimestamp(date);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 4;
        _stmt.bindLong(_argIndex, streakId);
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
          __preparedStmtOfUpdateStreakCount.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object breakStreak(final long streakId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfBreakStreak.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, streakId);
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
          __preparedStmtOfBreakStreak.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getActiveStreak(final long userId, final StreakType type,
      final Continuation<? super Streak> $completion) {
    final String _sql = "SELECT * FROM streaks WHERE userId = ? AND type = ? AND isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    final String _tmp = __converters.fromStreakType(type);
    _statement.bindString(_argIndex, _tmp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Streak>() {
      @Override
      @Nullable
      public Streak call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLongestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "longestStreak");
          final int _cursorIndexOfLastActivityDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActivityDate");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final Streak _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final StreakType _tmpType;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toStreakType(_tmp_1);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpLongestStreak;
            _tmpLongestStreak = _cursor.getInt(_cursorIndexOfLongestStreak);
            final Date _tmpLastActivityDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfLastActivityDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfLastActivityDate);
            }
            _tmpLastActivityDate = __converters.fromTimestamp(_tmp_2);
            final Date _tmpStartDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfStartDate);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_4;
            }
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            _result = new Streak(_tmpId,_tmpUserId,_tmpType,_tmpCurrentStreak,_tmpLongestStreak,_tmpLastActivityDate,_tmpStartDate,_tmpIsActive);
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
  public Flow<List<Streak>> getActiveStreaksByUser(final long userId) {
    final String _sql = "SELECT * FROM streaks WHERE userId = ? AND isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"streaks"}, new Callable<List<Streak>>() {
      @Override
      @NonNull
      public List<Streak> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfCurrentStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentStreak");
          final int _cursorIndexOfLongestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "longestStreak");
          final int _cursorIndexOfLastActivityDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActivityDate");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<Streak> _result = new ArrayList<Streak>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Streak _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final StreakType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toStreakType(_tmp);
            final int _tmpCurrentStreak;
            _tmpCurrentStreak = _cursor.getInt(_cursorIndexOfCurrentStreak);
            final int _tmpLongestStreak;
            _tmpLongestStreak = _cursor.getInt(_cursorIndexOfLongestStreak);
            final Date _tmpLastActivityDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfLastActivityDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfLastActivityDate);
            }
            _tmpLastActivityDate = __converters.fromTimestamp(_tmp_1);
            final Date _tmpStartDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfStartDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_3;
            }
            final boolean _tmpIsActive;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_4 != 0;
            _item = new Streak(_tmpId,_tmpUserId,_tmpType,_tmpCurrentStreak,_tmpLongestStreak,_tmpLastActivityDate,_tmpStartDate,_tmpIsActive);
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
  public Object getLongestStreakByType(final long userId, final StreakType type,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT MAX(longestStreak) FROM streaks WHERE userId = ? AND type = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    final String _tmp = __converters.fromStreakType(type);
    _statement.bindString(_argIndex, _tmp);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp_1;
            if (_cursor.isNull(0)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(0);
            }
            _result = _tmp_1;
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
  public Object getTotalActiveStreakDays(final long userId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(currentStreak) FROM streaks WHERE userId = ? AND isActive = 1";
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
