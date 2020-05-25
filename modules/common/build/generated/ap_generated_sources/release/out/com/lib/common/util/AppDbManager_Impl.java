package com.lib.common.util;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import com.lib.common.util.room.DiggCommentDao;
import com.lib.common.util.room.DiggCommentDao_Impl;
import com.lib.common.util.room.HistoryDao;
import com.lib.common.util.room.HistoryDao_Impl;
import com.lib.common.util.room.RecordDao;
import com.lib.common.util.room.RecordDao_Impl;
import com.lib.common.util.room.SearchDao;
import com.lib.common.util.room.SearchDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class AppDbManager_Impl extends AppDbManager {
  private volatile HistoryDao _historyDao;

  private volatile SearchDao _searchDao;

  private volatile DiggCommentDao _diggCommentDao;

  private volatile RecordDao _recordDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(12) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `t_vod_history` (`vod_id` INTEGER NOT NULL, `vod_index` INTEGER NOT NULL, `vod_json` TEXT, `vod_time_update` TEXT, `progress` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
        _db.execSQL("CREATE  INDEX `index_t_vod_history_vod_id` ON `t_vod_history` (`vod_id`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `t_digg_comment` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `comment_id` INTEGER NOT NULL, `status` INTEGER NOT NULL)");
        _db.execSQL("CREATE  INDEX `index_t_digg_comment_comment_id` ON `t_digg_comment` (`comment_id`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `t_search` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `searchKeyWords` TEXT)");
        _db.execSQL("CREATE  INDEX `index_t_search_searchKeyWords` ON `t_search` (`searchKeyWords`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `t_play_record` (`start_pos` INTEGER NOT NULL, `vod_id` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
        _db.execSQL("CREATE  INDEX `index_t_play_record_vod_id` ON `t_play_record` (`vod_id`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"35e523b7133b810341400e69c7047e2d\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `t_vod_history`");
        _db.execSQL("DROP TABLE IF EXISTS `t_digg_comment`");
        _db.execSQL("DROP TABLE IF EXISTS `t_search`");
        _db.execSQL("DROP TABLE IF EXISTS `t_play_record`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTVodHistory = new HashMap<String, TableInfo.Column>(6);
        _columnsTVodHistory.put("vod_id", new TableInfo.Column("vod_id", "INTEGER", true, 0));
        _columnsTVodHistory.put("vod_index", new TableInfo.Column("vod_index", "INTEGER", true, 0));
        _columnsTVodHistory.put("vod_json", new TableInfo.Column("vod_json", "TEXT", false, 0));
        _columnsTVodHistory.put("vod_time_update", new TableInfo.Column("vod_time_update", "TEXT", false, 0));
        _columnsTVodHistory.put("progress", new TableInfo.Column("progress", "INTEGER", true, 0));
        _columnsTVodHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTVodHistory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTVodHistory = new HashSet<TableInfo.Index>(1);
        _indicesTVodHistory.add(new TableInfo.Index("index_t_vod_history_vod_id", false, Arrays.asList("vod_id")));
        final TableInfo _infoTVodHistory = new TableInfo("t_vod_history", _columnsTVodHistory, _foreignKeysTVodHistory, _indicesTVodHistory);
        final TableInfo _existingTVodHistory = TableInfo.read(_db, "t_vod_history");
        if (! _infoTVodHistory.equals(_existingTVodHistory)) {
          throw new IllegalStateException("Migration didn't properly handle t_vod_history(com.lib.common.util.data.HistoryInfo).\n"
                  + " Expected:\n" + _infoTVodHistory + "\n"
                  + " Found:\n" + _existingTVodHistory);
        }
        final HashMap<String, TableInfo.Column> _columnsTDiggComment = new HashMap<String, TableInfo.Column>(3);
        _columnsTDiggComment.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsTDiggComment.put("comment_id", new TableInfo.Column("comment_id", "INTEGER", true, 0));
        _columnsTDiggComment.put("status", new TableInfo.Column("status", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTDiggComment = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTDiggComment = new HashSet<TableInfo.Index>(1);
        _indicesTDiggComment.add(new TableInfo.Index("index_t_digg_comment_comment_id", false, Arrays.asList("comment_id")));
        final TableInfo _infoTDiggComment = new TableInfo("t_digg_comment", _columnsTDiggComment, _foreignKeysTDiggComment, _indicesTDiggComment);
        final TableInfo _existingTDiggComment = TableInfo.read(_db, "t_digg_comment");
        if (! _infoTDiggComment.equals(_existingTDiggComment)) {
          throw new IllegalStateException("Migration didn't properly handle t_digg_comment(com.lib.common.util.data.DiggCommentInfo).\n"
                  + " Expected:\n" + _infoTDiggComment + "\n"
                  + " Found:\n" + _existingTDiggComment);
        }
        final HashMap<String, TableInfo.Column> _columnsTSearch = new HashMap<String, TableInfo.Column>(2);
        _columnsTSearch.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsTSearch.put("searchKeyWords", new TableInfo.Column("searchKeyWords", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTSearch = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTSearch = new HashSet<TableInfo.Index>(1);
        _indicesTSearch.add(new TableInfo.Index("index_t_search_searchKeyWords", false, Arrays.asList("searchKeyWords")));
        final TableInfo _infoTSearch = new TableInfo("t_search", _columnsTSearch, _foreignKeysTSearch, _indicesTSearch);
        final TableInfo _existingTSearch = TableInfo.read(_db, "t_search");
        if (! _infoTSearch.equals(_existingTSearch)) {
          throw new IllegalStateException("Migration didn't properly handle t_search(com.lib.common.util.data.SearchHistoryInfo).\n"
                  + " Expected:\n" + _infoTSearch + "\n"
                  + " Found:\n" + _existingTSearch);
        }
        final HashMap<String, TableInfo.Column> _columnsTPlayRecord = new HashMap<String, TableInfo.Column>(3);
        _columnsTPlayRecord.put("start_pos", new TableInfo.Column("start_pos", "INTEGER", true, 0));
        _columnsTPlayRecord.put("vod_id", new TableInfo.Column("vod_id", "TEXT", false, 0));
        _columnsTPlayRecord.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTPlayRecord = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTPlayRecord = new HashSet<TableInfo.Index>(1);
        _indicesTPlayRecord.add(new TableInfo.Index("index_t_play_record_vod_id", false, Arrays.asList("vod_id")));
        final TableInfo _infoTPlayRecord = new TableInfo("t_play_record", _columnsTPlayRecord, _foreignKeysTPlayRecord, _indicesTPlayRecord);
        final TableInfo _existingTPlayRecord = TableInfo.read(_db, "t_play_record");
        if (! _infoTPlayRecord.equals(_existingTPlayRecord)) {
          throw new IllegalStateException("Migration didn't properly handle t_play_record(com.lib.common.util.data.PlayRecordInfo).\n"
                  + " Expected:\n" + _infoTPlayRecord + "\n"
                  + " Found:\n" + _existingTPlayRecord);
        }
      }
    }, "35e523b7133b810341400e69c7047e2d", "7b4168791012cb24f637b4128b2c4317");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "t_vod_history","t_digg_comment","t_search","t_play_record");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `t_vod_history`");
      _db.execSQL("DELETE FROM `t_digg_comment`");
      _db.execSQL("DELETE FROM `t_search`");
      _db.execSQL("DELETE FROM `t_play_record`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public HistoryDao historyDao() {
    if (_historyDao != null) {
      return _historyDao;
    } else {
      synchronized(this) {
        if(_historyDao == null) {
          _historyDao = new HistoryDao_Impl(this);
        }
        return _historyDao;
      }
    }
  }

  @Override
  public SearchDao searchDao() {
    if (_searchDao != null) {
      return _searchDao;
    } else {
      synchronized(this) {
        if(_searchDao == null) {
          _searchDao = new SearchDao_Impl(this);
        }
        return _searchDao;
      }
    }
  }

  @Override
  public DiggCommentDao diggDao() {
    if (_diggCommentDao != null) {
      return _diggCommentDao;
    } else {
      synchronized(this) {
        if(_diggCommentDao == null) {
          _diggCommentDao = new DiggCommentDao_Impl(this);
        }
        return _diggCommentDao;
      }
    }
  }

  @Override
  public RecordDao recordDao() {
    if (_recordDao != null) {
      return _recordDao;
    } else {
      synchronized(this) {
        if(_recordDao == null) {
          _recordDao = new RecordDao_Impl(this);
        }
        return _recordDao;
      }
    }
  }
}
