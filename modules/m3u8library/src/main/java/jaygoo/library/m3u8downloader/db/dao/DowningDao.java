package jaygoo.library.m3u8downloader.db.dao;

import android.arch.persistence.room.*;
import jaygoo.library.m3u8downloader.db.table.M3u8DownloadingInfo;

import java.util.List;


/**
 * Created by HuangYong on 2018/6/27.
 */
@Dao
public interface DowningDao {

    @Insert
    void insert(M3u8DownloadingInfo downloadingInfo);

    @Delete
    void delete(M3u8DownloadingInfo downloadingInfo);

    @Update
    void update(M3u8DownloadingInfo downloadingInfo);

    @Query("SELECT * FROM T_M3U8_DOWNING")
    List<M3u8DownloadingInfo> getAll();

    @Query("SELECT * FROM T_M3U8_DOWNING WHERE task_id=:taskId ")
    List<M3u8DownloadingInfo> getById(String taskId);
}
