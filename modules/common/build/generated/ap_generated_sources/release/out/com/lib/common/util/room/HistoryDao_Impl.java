package com.lib.common.util.room;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.lib.common.util.data.HistoryInfo;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class HistoryDao_Impl implements HistoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfHistoryInfo;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfHistoryInfo;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfHistoryInfo;

  public HistoryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHistoryInfo = new EntityInsertionAdapter<HistoryInfo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `t_vod_history`(`vod_id`,`vod_index`,`vod_json`,`vod_time_update`,`progress`,`id`) VALUES (?,?,?,?,?,nullif(?, 0))";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HistoryInfo value) {
        stmt.bindLong(1, value.getVodId());
        stmt.bindLong(2, value.getIndex());
        if (value.getVodJson() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getVodJson());
        }
        if (value.getUpdateTime() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUpdateTime());
        }
        stmt.bindLong(5, value.getProgress());
        stmt.bindLong(6, value.getId());
      }
    };
    this.__deletionAdapterOfHistoryInfo = new EntityDeletionOrUpdateAdapter<HistoryInfo>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `t_vod_history` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HistoryInfo value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfHistoryInfo = new EntityDeletionOrUpdateAdapter<HistoryInfo>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `t_vod_history` SET `vod_id` = ?,`vod_index` = ?,`vod_json` = ?,`vod_time_update` = ?,`progress` = ?,`id` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HistoryInfo value) {
        stmt.bindLong(1, value.getVodId());
        stmt.bindLong(2, value.getIndex());
        if (value.getVodJson() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getVodJson());
        }
        if (value.getUpdateTime() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getUpdateTime());
        }
        stmt.bindLong(5, value.getProgress());
        stmt.bindLong(6, value.getId());
        stmt.bindLong(7, value.getId());
      }
    };
  }

  @Override
  public void insert(HistoryInfo historyInfo) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfHistoryInfo.insert(historyInfo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(HistoryInfo historyInfo) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfHistoryInfo.handle(historyInfo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(HistoryInfo historyInfo) {
    __db.beginTransaction();
    try {
      __updateAdapterOfHistoryInfo.handle(historyInfo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<HistoryInfo> getAll() {
    final String _sql = "SELECT * FROM T_VOD_HISTORY order by vod_time_update DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfVodId = _cursor.getColumnIndexOrThrow("vod_id");
      final int _cursorIndexOfIndex = _cursor.getColumnIndexOrThrow("vod_index");
      final int _cursorIndexOfVodJson = _cursor.getColumnIndexOrThrow("vod_json");
      final int _cursorIndexOfUpdateTime = _cursor.getColumnIndexOrThrow("vod_time_update");
      final int _cursorIndexOfProgress = _cursor.getColumnIndexOrThrow("progress");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final List<HistoryInfo> _result = new ArrayList<HistoryInfo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final HistoryInfo _item;
        _item = new HistoryInfo();
        final int _tmpVodId;
        _tmpVodId = _cursor.getInt(_cursorIndexOfVodId);
        _item.setVodId(_tmpVodId);
        final int _tmpIndex;
        _tmpIndex = _cursor.getInt(_cursorIndexOfIndex);
        _item.setIndex(_tmpIndex);
        final String _tmpVodJson;
        _tmpVodJson = _cursor.getString(_cursorIndexOfVodJson);
        _item.setVodJson(_tmpVodJson);
        final String _tmpUpdateTime;
        _tmpUpdateTime = _cursor.getString(_cursorIndexOfUpdateTime);
        _item.setUpdateTime(_tmpUpdateTime);
        final int _tmpProgress;
        _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
        _item.setProgress(_tmpProgress);
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
  public List<HistoryInfo> getById(int vodId) {
    final String _sql = "SELECT * FROM T_VOD_HISTORY WHERE vod_id=? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, vodId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfVodId = _cursor.getColumnIndexOrThrow("vod_id");
      final int _cursorIndexOfIndex = _cursor.getColumnIndexOrThrow("vod_index");
      final int _cursorIndexOfVodJson = _cursor.getColumnIndexOrThrow("vod_json");
      final int _cursorIndexOfUpdateTime = _cursor.getColumnIndexOrThrow("vod_time_update");
      final int _cursorIndexOfProgress = _cursor.getColumnIndexOrThrow("progress");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final List<HistoryInfo> _result = new ArrayList<HistoryInfo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final HistoryInfo _item;
        _item = new HistoryInfo();
        final int _tmpVodId;
        _tmpVodId = _cursor.getInt(_cursorIndexOfVodId);
        _item.setVodId(_tmpVodId);
        final int _tmpIndex;
        _tmpIndex = _cursor.getInt(_cursorIndexOfIndex);
        _item.setIndex(_tmpIndex);
        final String _tmpVodJson;
        _tmpVodJson = _cursor.getString(_cursorIndexOfVodJson);
        _item.setVodJson(_tmpVodJson);
        final String _tmpUpdateTime;
        _tmpUpdateTime = _cursor.getString(_cursorIndexOfUpdateTime);
        _item.setUpdateTime(_tmpUpdateTime);
        final int _tmpProgress;
        _tmpProgress = _cursor.getInt(_cursorIndexOfProgress);
        _item.setProgress(_tmpProgress);
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
