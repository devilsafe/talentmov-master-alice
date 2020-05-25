package com.lib.common.util.room;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.lib.common.util.data.PlayRecordInfo;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class RecordDao_Impl implements RecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPlayRecordInfo;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPlayRecordInfo;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfPlayRecordInfo;

  public RecordDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlayRecordInfo = new EntityInsertionAdapter<PlayRecordInfo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `t_play_record`(`start_pos`,`vod_id`,`id`) VALUES (?,?,nullif(?, 0))";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PlayRecordInfo value) {
        stmt.bindLong(1, value.getStartPos());
        if (value.getVodId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVodId());
        }
        stmt.bindLong(3, value.getId());
      }
    };
    this.__deletionAdapterOfPlayRecordInfo = new EntityDeletionOrUpdateAdapter<PlayRecordInfo>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `t_play_record` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PlayRecordInfo value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfPlayRecordInfo = new EntityDeletionOrUpdateAdapter<PlayRecordInfo>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `t_play_record` SET `start_pos` = ?,`vod_id` = ?,`id` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PlayRecordInfo value) {
        stmt.bindLong(1, value.getStartPos());
        if (value.getVodId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVodId());
        }
        stmt.bindLong(3, value.getId());
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public void insert(PlayRecordInfo recordInfo) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPlayRecordInfo.insert(recordInfo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(PlayRecordInfo recordInfo) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPlayRecordInfo.handle(recordInfo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(PlayRecordInfo recordInfo) {
    __db.beginTransaction();
    try {
      __updateAdapterOfPlayRecordInfo.handle(recordInfo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<PlayRecordInfo> getAll() {
    final String _sql = "SELECT * FROM T_PLAY_RECORD";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfStartPos = _cursor.getColumnIndexOrThrow("start_pos");
      final int _cursorIndexOfVodId = _cursor.getColumnIndexOrThrow("vod_id");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final List<PlayRecordInfo> _result = new ArrayList<PlayRecordInfo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PlayRecordInfo _item;
        _item = new PlayRecordInfo();
        final int _tmpStartPos;
        _tmpStartPos = _cursor.getInt(_cursorIndexOfStartPos);
        _item.setStartPos(_tmpStartPos);
        final String _tmpVodId;
        _tmpVodId = _cursor.getString(_cursorIndexOfVodId);
        _item.setVodId(_tmpVodId);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PlayRecordInfo> getById(String vodId) {
    final String _sql = "SELECT * FROM T_PLAY_RECORD WHERE vod_id=? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (vodId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, vodId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfStartPos = _cursor.getColumnIndexOrThrow("start_pos");
      final int _cursorIndexOfVodId = _cursor.getColumnIndexOrThrow("vod_id");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final List<PlayRecordInfo> _result = new ArrayList<PlayRecordInfo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PlayRecordInfo _item;
        _item = new PlayRecordInfo();
        final int _tmpStartPos;
        _tmpStartPos = _cursor.getInt(_cursorIndexOfStartPos);
        _item.setStartPos(_tmpStartPos);
        final String _tmpVodId;
        _tmpVodId = _cursor.getString(_cursorIndexOfVodId);
        _item.setVodId(_tmpVodId);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
