package jaygoo.library.m3u8downloader.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import jaygoo.library.m3u8downloader.db.dao.DoneDao;
import jaygoo.library.m3u8downloader.db.dao.DowningDao;
import jaygoo.library.m3u8downloader.db.table.M3u8DoneInfo;
import jaygoo.library.m3u8downloader.db.table.M3u8DownloadingInfo;


@Database(entities = {M3u8DownloadingInfo.class, M3u8DoneInfo.class}, version = 4, exportSchema = false)
public abstract class M3U8dbManager extends RoomDatabase {

    private static final String TABLE_NAME = "m3u8_cache.db";

    public abstract DoneDao doneDao();

    public abstract DowningDao downingDao();

    private static M3U8dbManager instance;
    private static final Object s_Lock = new Object();

    public static synchronized M3U8dbManager getInstance(Context context) {
        synchronized (s_Lock) {
            if (instance == null) {
                instance =
                        Room.databaseBuilder(context, M3U8dbManager.class, M3U8dbManager.TABLE_NAME)
                                .allowMainThreadQueries()
                                .fallbackToDestructiveMigration()
                                .build();
            }
            return instance;
        }

    }
}
