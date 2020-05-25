package com.lib.common.util.room;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.lib.common.util.data.DiggCommentInfo;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class DiggCommentDao_Impl implements DiggCommentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfDiggCommentInfo;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfDiggCommentInfo;

  public DiggCommentDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDiggCommentInfo = new EntityInsertionAdapter<DiggCommentInfo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `t_digg_comment`(`id`,`comment_id`,`status`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DiggCommentInfo value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.commentId);
        stmt.bindLong(3, value.status);
      }
    };
    this.__deletionAdapterOfDiggCommentInfo = new EntityDeletionOrUpdateAdapter<DiggCommentInfo>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `t_digg_comment` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DiggCommentInfo value) {
        stmt.bindLong(1, value.id);
      }
    };
  }

  @Override
  public void insert(DiggCommentInfo trackData) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfDiggCommentInfo.insert(trackData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(DiggCommentInfo trackData) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfDiggCommentInfo.handle(trackData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<DiggCommentInfo> getAll() {
    final String _sql = "SELECT * FROM T_DIGG_COMMENT";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfCommentId = _cursor.getColumnIndexOrThrow("comment_id");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final List<DiggCommentInfo> _result = new ArrayList<DiggCommentInfo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DiggCommentInfo _item;
        _item = new DiggCommentInfo();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.commentId = _cursor.getInt(_cursorIndexOfCommentId);
        _item.status = _cursor.getInt(_cursorIndexOfStatus);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<DiggCommentInfo> getByCommentId(int commentID) {
    final String _sql = "SELECT * FROM T_DIGG_COMMENT WHERE  comment_id=? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, commentID);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfCommentId = _cursor.getColumnIndexOrThrow("comment_id");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
      final List<DiggCommentInfo> _result = new ArrayList<DiggCommentInfo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DiggCommentInfo _item;
        _item = new DiggCommentInfo();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.commentId = _cursor.getInt(_cursorIndexOfCommentId);
        _item.status = _cursor.getInt(_cursorIndexOfStatus);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
