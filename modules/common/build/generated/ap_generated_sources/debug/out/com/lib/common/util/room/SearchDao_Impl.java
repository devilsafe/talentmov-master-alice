package com.lib.common.util.room;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.lib.common.util.data.SearchHistoryInfo;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class SearchDao_Impl implements SearchDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfSearchHistoryInfo;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfSearchHistoryInfo;

  public SearchDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSearchHistoryInfo = new EntityInsertionAdapter<SearchHistoryInfo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `t_search`(`id`,`searchKeyWords`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SearchHistoryInfo value) {
        stmt.bindLong(1, value.id);
        if (value.searchKeyWords == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.searchKeyWords);
        }
      }
    };
    this.__deletionAdapterOfSearchHistoryInfo = new EntityDeletionOrUpdateAdapter<SearchHistoryInfo>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `t_search` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SearchHistoryInfo value) {
        stmt.bindLong(1, value.id);
      }
    };
  }

  @Override
  public void insert(SearchHistoryInfo trackData) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfSearchHistoryInfo.insert(trackData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(SearchHistoryInfo trackData) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfSearchHistoryInfo.handle(trackData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<SearchHistoryInfo> getAll() {
    final String _sql = "SELECT * FROM T_SEARCH";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfSearchKeyWords = _cursor.getColumnIndexOrThrow("searchKeyWords");
      final List<SearchHistoryInfo> _result = new ArrayList<SearchHistoryInfo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SearchHistoryInfo _item;
        _item = new SearchHistoryInfo();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.searchKeyWords = _cursor.getString(_cursorIndexOfSearchKeyWords);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<SearchHistoryInfo> getByKeywords(String keyword) {
    final String _sql = "SELECT * FROM T_SEARCH WHERE  searchKeyWords=? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (keyword == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, keyword);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfSearchKeyWords = _cursor.getColumnIndexOrThrow("searchKeyWords");
      final List<SearchHistoryInfo> _result = new ArrayList<SearchHistoryInfo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SearchHistoryInfo _item;
        _item = new SearchHistoryInfo();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.searchKeyWords = _cursor.getString(_cursorIndexOfSearchKeyWords);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
