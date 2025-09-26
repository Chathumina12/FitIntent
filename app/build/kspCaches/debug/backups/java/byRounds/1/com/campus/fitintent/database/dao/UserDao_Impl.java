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
import com.campus.fitintent.models.ActivityLevel;
import com.campus.fitintent.models.FitnessGoal;
import com.campus.fitintent.models.Gender;
import com.campus.fitintent.models.User;
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
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdateOnboardingStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateProfileImage;

  private final SharedSQLiteStatement __preparedStmtOfUpdateWeight;

  private final SharedSQLiteStatement __preparedStmtOfDeactivateUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePassword;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`email`,`username`,`passwordHash`,`fullName`,`age`,`gender`,`height`,`weight`,`activityLevel`,`primaryGoal`,`targetWeight`,`weeklyWorkoutGoal`,`createdAt`,`updatedAt`,`isOnboardingComplete`,`isActive`,`profileImagePath`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getEmail());
        statement.bindString(3, entity.getUsername());
        statement.bindString(4, entity.getPasswordHash());
        statement.bindString(5, entity.getFullName());
        if (entity.getAge() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getAge());
        }
        final String _tmp = __converters.fromGender(entity.getGender());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp);
        }
        if (entity.getHeight() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getHeight());
        }
        if (entity.getWeight() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getWeight());
        }
        final String _tmp_1 = __converters.fromActivityLevel(entity.getActivityLevel());
        if (_tmp_1 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_1);
        }
        final String _tmp_2 = __converters.fromFitnessGoal(entity.getPrimaryGoal());
        if (_tmp_2 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_2);
        }
        if (entity.getTargetWeight() == null) {
          statement.bindNull(12);
        } else {
          statement.bindDouble(12, entity.getTargetWeight());
        }
        statement.bindLong(13, entity.getWeeklyWorkoutGoal());
        final Long _tmp_3 = __converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, _tmp_3);
        }
        final Long _tmp_4 = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_4 == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, _tmp_4);
        }
        final int _tmp_5 = entity.isOnboardingComplete() ? 1 : 0;
        statement.bindLong(16, _tmp_5);
        final int _tmp_6 = entity.isActive() ? 1 : 0;
        statement.bindLong(17, _tmp_6);
        if (entity.getProfileImagePath() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getProfileImagePath());
        }
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `users` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`email` = ?,`username` = ?,`passwordHash` = ?,`fullName` = ?,`age` = ?,`gender` = ?,`height` = ?,`weight` = ?,`activityLevel` = ?,`primaryGoal` = ?,`targetWeight` = ?,`weeklyWorkoutGoal` = ?,`createdAt` = ?,`updatedAt` = ?,`isOnboardingComplete` = ?,`isActive` = ?,`profileImagePath` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getEmail());
        statement.bindString(3, entity.getUsername());
        statement.bindString(4, entity.getPasswordHash());
        statement.bindString(5, entity.getFullName());
        if (entity.getAge() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getAge());
        }
        final String _tmp = __converters.fromGender(entity.getGender());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp);
        }
        if (entity.getHeight() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getHeight());
        }
        if (entity.getWeight() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getWeight());
        }
        final String _tmp_1 = __converters.fromActivityLevel(entity.getActivityLevel());
        if (_tmp_1 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_1);
        }
        final String _tmp_2 = __converters.fromFitnessGoal(entity.getPrimaryGoal());
        if (_tmp_2 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_2);
        }
        if (entity.getTargetWeight() == null) {
          statement.bindNull(12);
        } else {
          statement.bindDouble(12, entity.getTargetWeight());
        }
        statement.bindLong(13, entity.getWeeklyWorkoutGoal());
        final Long _tmp_3 = __converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, _tmp_3);
        }
        final Long _tmp_4 = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_4 == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, _tmp_4);
        }
        final int _tmp_5 = entity.isOnboardingComplete() ? 1 : 0;
        statement.bindLong(16, _tmp_5);
        final int _tmp_6 = entity.isActive() ? 1 : 0;
        statement.bindLong(17, _tmp_6);
        if (entity.getProfileImagePath() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getProfileImagePath());
        }
        statement.bindLong(19, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateOnboardingStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET isOnboardingComplete = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateProfileImage = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET profileImagePath = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateWeight = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET weight = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeactivateUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET isActive = 0 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePassword = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET passwordHash = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertUser(final User user, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfUser.insertAndReturnId(user);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteUser(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfUser.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUser(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUser.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateOnboardingStatus(final long userId, final boolean isComplete,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateOnboardingStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isComplete ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, userId);
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
          __preparedStmtOfUpdateOnboardingStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProfileImage(final long userId, final String imagePath,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateProfileImage.acquire();
        int _argIndex = 1;
        if (imagePath == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, imagePath);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, userId);
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
          __preparedStmtOfUpdateProfileImage.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWeight(final long userId, final float weight,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateWeight.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, weight);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, userId);
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
          __preparedStmtOfUpdateWeight.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deactivateUser(final long userId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeactivateUser.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, userId);
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
          __preparedStmtOfDeactivateUser.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePassword(final long userId, final String newPasswordHash,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePassword.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, newPasswordHash);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, userId);
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
          __preparedStmtOfUpdatePassword.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getUserById(final long userId, final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfActivityLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "activityLevel");
          final int _cursorIndexOfPrimaryGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryGoal");
          final int _cursorIndexOfTargetWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "targetWeight");
          final int _cursorIndexOfWeeklyWorkoutGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "weeklyWorkoutGoal");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsOnboardingComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnboardingComplete");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfProfileImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImagePath");
          final User _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPasswordHash;
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            final String _tmpFullName;
            _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            final Integer _tmpAge;
            if (_cursor.isNull(_cursorIndexOfAge)) {
              _tmpAge = null;
            } else {
              _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            }
            final Gender _tmpGender;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfGender);
            }
            _tmpGender = __converters.toGender(_tmp);
            final Float _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            }
            final Float _tmpWeight;
            if (_cursor.isNull(_cursorIndexOfWeight)) {
              _tmpWeight = null;
            } else {
              _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            }
            final ActivityLevel _tmpActivityLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfActivityLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfActivityLevel);
            }
            _tmpActivityLevel = __converters.toActivityLevel(_tmp_1);
            final FitnessGoal _tmpPrimaryGoal;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPrimaryGoal)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPrimaryGoal);
            }
            _tmpPrimaryGoal = __converters.toFitnessGoal(_tmp_2);
            final Float _tmpTargetWeight;
            if (_cursor.isNull(_cursorIndexOfTargetWeight)) {
              _tmpTargetWeight = null;
            } else {
              _tmpTargetWeight = _cursor.getFloat(_cursorIndexOfTargetWeight);
            }
            final int _tmpWeeklyWorkoutGoal;
            _tmpWeeklyWorkoutGoal = _cursor.getInt(_cursorIndexOfWeeklyWorkoutGoal);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __converters.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final boolean _tmpIsOnboardingComplete;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsOnboardingComplete);
            _tmpIsOnboardingComplete = _tmp_7 != 0;
            final boolean _tmpIsActive;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_8 != 0;
            final String _tmpProfileImagePath;
            if (_cursor.isNull(_cursorIndexOfProfileImagePath)) {
              _tmpProfileImagePath = null;
            } else {
              _tmpProfileImagePath = _cursor.getString(_cursorIndexOfProfileImagePath);
            }
            _result = new User(_tmpId,_tmpEmail,_tmpUsername,_tmpPasswordHash,_tmpFullName,_tmpAge,_tmpGender,_tmpHeight,_tmpWeight,_tmpActivityLevel,_tmpPrimaryGoal,_tmpTargetWeight,_tmpWeeklyWorkoutGoal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsOnboardingComplete,_tmpIsActive,_tmpProfileImagePath);
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
  public Flow<User> getUserByIdFlow(final long userId) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfActivityLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "activityLevel");
          final int _cursorIndexOfPrimaryGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryGoal");
          final int _cursorIndexOfTargetWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "targetWeight");
          final int _cursorIndexOfWeeklyWorkoutGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "weeklyWorkoutGoal");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsOnboardingComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnboardingComplete");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfProfileImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImagePath");
          final User _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPasswordHash;
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            final String _tmpFullName;
            _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            final Integer _tmpAge;
            if (_cursor.isNull(_cursorIndexOfAge)) {
              _tmpAge = null;
            } else {
              _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            }
            final Gender _tmpGender;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfGender);
            }
            _tmpGender = __converters.toGender(_tmp);
            final Float _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            }
            final Float _tmpWeight;
            if (_cursor.isNull(_cursorIndexOfWeight)) {
              _tmpWeight = null;
            } else {
              _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            }
            final ActivityLevel _tmpActivityLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfActivityLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfActivityLevel);
            }
            _tmpActivityLevel = __converters.toActivityLevel(_tmp_1);
            final FitnessGoal _tmpPrimaryGoal;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPrimaryGoal)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPrimaryGoal);
            }
            _tmpPrimaryGoal = __converters.toFitnessGoal(_tmp_2);
            final Float _tmpTargetWeight;
            if (_cursor.isNull(_cursorIndexOfTargetWeight)) {
              _tmpTargetWeight = null;
            } else {
              _tmpTargetWeight = _cursor.getFloat(_cursorIndexOfTargetWeight);
            }
            final int _tmpWeeklyWorkoutGoal;
            _tmpWeeklyWorkoutGoal = _cursor.getInt(_cursorIndexOfWeeklyWorkoutGoal);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __converters.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final boolean _tmpIsOnboardingComplete;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsOnboardingComplete);
            _tmpIsOnboardingComplete = _tmp_7 != 0;
            final boolean _tmpIsActive;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_8 != 0;
            final String _tmpProfileImagePath;
            if (_cursor.isNull(_cursorIndexOfProfileImagePath)) {
              _tmpProfileImagePath = null;
            } else {
              _tmpProfileImagePath = _cursor.getString(_cursorIndexOfProfileImagePath);
            }
            _result = new User(_tmpId,_tmpEmail,_tmpUsername,_tmpPasswordHash,_tmpFullName,_tmpAge,_tmpGender,_tmpHeight,_tmpWeight,_tmpActivityLevel,_tmpPrimaryGoal,_tmpTargetWeight,_tmpWeeklyWorkoutGoal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsOnboardingComplete,_tmpIsActive,_tmpProfileImagePath);
          } else {
            _result = null;
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
  public Object getUserByEmail(final String email, final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, email);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfActivityLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "activityLevel");
          final int _cursorIndexOfPrimaryGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryGoal");
          final int _cursorIndexOfTargetWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "targetWeight");
          final int _cursorIndexOfWeeklyWorkoutGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "weeklyWorkoutGoal");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsOnboardingComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnboardingComplete");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfProfileImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImagePath");
          final User _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPasswordHash;
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            final String _tmpFullName;
            _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            final Integer _tmpAge;
            if (_cursor.isNull(_cursorIndexOfAge)) {
              _tmpAge = null;
            } else {
              _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            }
            final Gender _tmpGender;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfGender);
            }
            _tmpGender = __converters.toGender(_tmp);
            final Float _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            }
            final Float _tmpWeight;
            if (_cursor.isNull(_cursorIndexOfWeight)) {
              _tmpWeight = null;
            } else {
              _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            }
            final ActivityLevel _tmpActivityLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfActivityLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfActivityLevel);
            }
            _tmpActivityLevel = __converters.toActivityLevel(_tmp_1);
            final FitnessGoal _tmpPrimaryGoal;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPrimaryGoal)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPrimaryGoal);
            }
            _tmpPrimaryGoal = __converters.toFitnessGoal(_tmp_2);
            final Float _tmpTargetWeight;
            if (_cursor.isNull(_cursorIndexOfTargetWeight)) {
              _tmpTargetWeight = null;
            } else {
              _tmpTargetWeight = _cursor.getFloat(_cursorIndexOfTargetWeight);
            }
            final int _tmpWeeklyWorkoutGoal;
            _tmpWeeklyWorkoutGoal = _cursor.getInt(_cursorIndexOfWeeklyWorkoutGoal);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __converters.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final boolean _tmpIsOnboardingComplete;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsOnboardingComplete);
            _tmpIsOnboardingComplete = _tmp_7 != 0;
            final boolean _tmpIsActive;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_8 != 0;
            final String _tmpProfileImagePath;
            if (_cursor.isNull(_cursorIndexOfProfileImagePath)) {
              _tmpProfileImagePath = null;
            } else {
              _tmpProfileImagePath = _cursor.getString(_cursorIndexOfProfileImagePath);
            }
            _result = new User(_tmpId,_tmpEmail,_tmpUsername,_tmpPasswordHash,_tmpFullName,_tmpAge,_tmpGender,_tmpHeight,_tmpWeight,_tmpActivityLevel,_tmpPrimaryGoal,_tmpTargetWeight,_tmpWeeklyWorkoutGoal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsOnboardingComplete,_tmpIsActive,_tmpProfileImagePath);
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
  public Object getActiveUserByEmail(final String email,
      final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE email = ? AND isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, email);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfActivityLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "activityLevel");
          final int _cursorIndexOfPrimaryGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryGoal");
          final int _cursorIndexOfTargetWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "targetWeight");
          final int _cursorIndexOfWeeklyWorkoutGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "weeklyWorkoutGoal");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsOnboardingComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnboardingComplete");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfProfileImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImagePath");
          final User _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPasswordHash;
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            final String _tmpFullName;
            _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            final Integer _tmpAge;
            if (_cursor.isNull(_cursorIndexOfAge)) {
              _tmpAge = null;
            } else {
              _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            }
            final Gender _tmpGender;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfGender);
            }
            _tmpGender = __converters.toGender(_tmp);
            final Float _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            }
            final Float _tmpWeight;
            if (_cursor.isNull(_cursorIndexOfWeight)) {
              _tmpWeight = null;
            } else {
              _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            }
            final ActivityLevel _tmpActivityLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfActivityLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfActivityLevel);
            }
            _tmpActivityLevel = __converters.toActivityLevel(_tmp_1);
            final FitnessGoal _tmpPrimaryGoal;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPrimaryGoal)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPrimaryGoal);
            }
            _tmpPrimaryGoal = __converters.toFitnessGoal(_tmp_2);
            final Float _tmpTargetWeight;
            if (_cursor.isNull(_cursorIndexOfTargetWeight)) {
              _tmpTargetWeight = null;
            } else {
              _tmpTargetWeight = _cursor.getFloat(_cursorIndexOfTargetWeight);
            }
            final int _tmpWeeklyWorkoutGoal;
            _tmpWeeklyWorkoutGoal = _cursor.getInt(_cursorIndexOfWeeklyWorkoutGoal);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __converters.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final boolean _tmpIsOnboardingComplete;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsOnboardingComplete);
            _tmpIsOnboardingComplete = _tmp_7 != 0;
            final boolean _tmpIsActive;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_8 != 0;
            final String _tmpProfileImagePath;
            if (_cursor.isNull(_cursorIndexOfProfileImagePath)) {
              _tmpProfileImagePath = null;
            } else {
              _tmpProfileImagePath = _cursor.getString(_cursorIndexOfProfileImagePath);
            }
            _result = new User(_tmpId,_tmpEmail,_tmpUsername,_tmpPasswordHash,_tmpFullName,_tmpAge,_tmpGender,_tmpHeight,_tmpWeight,_tmpActivityLevel,_tmpPrimaryGoal,_tmpTargetWeight,_tmpWeeklyWorkoutGoal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsOnboardingComplete,_tmpIsActive,_tmpProfileImagePath);
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
  public Object checkEmailExists(final String email,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM users WHERE email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, email);
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
  public Flow<List<User>> getAllActiveUsers() {
    final String _sql = "SELECT * FROM users WHERE isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<List<User>>() {
      @Override
      @NonNull
      public List<User> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfPasswordHash = CursorUtil.getColumnIndexOrThrow(_cursor, "passwordHash");
          final int _cursorIndexOfFullName = CursorUtil.getColumnIndexOrThrow(_cursor, "fullName");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfActivityLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "activityLevel");
          final int _cursorIndexOfPrimaryGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "primaryGoal");
          final int _cursorIndexOfTargetWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "targetWeight");
          final int _cursorIndexOfWeeklyWorkoutGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "weeklyWorkoutGoal");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsOnboardingComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isOnboardingComplete");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfProfileImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImagePath");
          final List<User> _result = new ArrayList<User>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final User _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpPasswordHash;
            _tmpPasswordHash = _cursor.getString(_cursorIndexOfPasswordHash);
            final String _tmpFullName;
            _tmpFullName = _cursor.getString(_cursorIndexOfFullName);
            final Integer _tmpAge;
            if (_cursor.isNull(_cursorIndexOfAge)) {
              _tmpAge = null;
            } else {
              _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            }
            final Gender _tmpGender;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfGender);
            }
            _tmpGender = __converters.toGender(_tmp);
            final Float _tmpHeight;
            if (_cursor.isNull(_cursorIndexOfHeight)) {
              _tmpHeight = null;
            } else {
              _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            }
            final Float _tmpWeight;
            if (_cursor.isNull(_cursorIndexOfWeight)) {
              _tmpWeight = null;
            } else {
              _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            }
            final ActivityLevel _tmpActivityLevel;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfActivityLevel)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfActivityLevel);
            }
            _tmpActivityLevel = __converters.toActivityLevel(_tmp_1);
            final FitnessGoal _tmpPrimaryGoal;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPrimaryGoal)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPrimaryGoal);
            }
            _tmpPrimaryGoal = __converters.toFitnessGoal(_tmp_2);
            final Float _tmpTargetWeight;
            if (_cursor.isNull(_cursorIndexOfTargetWeight)) {
              _tmpTargetWeight = null;
            } else {
              _tmpTargetWeight = _cursor.getFloat(_cursorIndexOfTargetWeight);
            }
            final int _tmpWeeklyWorkoutGoal;
            _tmpWeeklyWorkoutGoal = _cursor.getInt(_cursorIndexOfWeeklyWorkoutGoal);
            final Date _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_6 = __converters.fromTimestamp(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_6;
            }
            final boolean _tmpIsOnboardingComplete;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsOnboardingComplete);
            _tmpIsOnboardingComplete = _tmp_7 != 0;
            final boolean _tmpIsActive;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_8 != 0;
            final String _tmpProfileImagePath;
            if (_cursor.isNull(_cursorIndexOfProfileImagePath)) {
              _tmpProfileImagePath = null;
            } else {
              _tmpProfileImagePath = _cursor.getString(_cursorIndexOfProfileImagePath);
            }
            _item = new User(_tmpId,_tmpEmail,_tmpUsername,_tmpPasswordHash,_tmpFullName,_tmpAge,_tmpGender,_tmpHeight,_tmpWeight,_tmpActivityLevel,_tmpPrimaryGoal,_tmpTargetWeight,_tmpWeeklyWorkoutGoal,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsOnboardingComplete,_tmpIsActive,_tmpProfileImagePath);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
