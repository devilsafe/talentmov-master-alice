package com.lib.common.util.room;

import androidx.room.*;
import com.lib.common.util.data.HistoryInfo;

import java.util.List;


/**
 * Created by HuangYong on 2018/6/27.
 */
@Dao
public interface HistoryDao {

    @Insert
    void insert(HistoryInfo historyInfo);

    @Delete
    void delete(HistoryInfo historyInfo);

    @Update
    void update(HistoryInfo historyInfo);

    @Query("SELECT * FROM T_VOD_HISTORY order by vod_time_update DESC")
    List<HistoryInfo> getAll();

    @Query("SELECT * FROM T_VOD_HISTORY WHERE vod_id=:vodId ")
    List<HistoryInfo> getById(int vodId);
}
