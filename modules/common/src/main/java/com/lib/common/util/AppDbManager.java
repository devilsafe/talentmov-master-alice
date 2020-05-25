package com.lib.common.util;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.lib.common.util.data.DiggCommentInfo;
import com.lib.common.util.data.HistoryInfo;
import com.lib.common.util.data.PlayRecordInfo;
import com.lib.common.util.data.SearchHistoryInfo;
import com.lib.common.util.room.DiggCommentDao;
import com.lib.common.util.room.HistoryDao;
import com.lib.common.util.room.RecordDao;
import com.lib.common.util.room.SearchDao;


@Database(entities = {HistoryInfo.class, DiggCommentInfo.class, SearchHistoryInfo.class, PlayRecordInfo.class}, version = 12, exportSchema = false)
public abstract class AppDbManager extends RoomDatabase {

    private static final String TABLE_NAME = "com_mac_page.db";

    public abstract HistoryDao historyDao();

    public abstract SearchDao searchDao();

    public abstract DiggCommentDao diggDao();

    public abstract RecordDao recordDao();

    private static AppDbManager instance;
    private static final Object s_Lock = new Object();

    public static synchronized AppDbManager getInstance(Context context) {
        synchronized (s_Lock) {
            if (instance == null) {
                instance =
                        Room.databaseBuilder(context, AppDbManager.class, AppDbManager.TABLE_NAME)
                                .allowMainThreadQueries()
                                .fallbackToDestructiveMigration()
                                .build();
            }
            return instance;
        }

    }
}
