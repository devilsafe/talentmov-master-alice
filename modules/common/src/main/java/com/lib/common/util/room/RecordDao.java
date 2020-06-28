package com.lib.common.util.room;

import androidx.room.*;
import com.lib.common.util.data.PlayRecordInfo;

import java.util.List;


/**
 * Created by HuangYong on 2018/6/27.
 */
@Dao
public interface RecordDao {

    @Insert
    void insert(PlayRecordInfo recordInfo);

    @Delete
    void delete(PlayRecordInfo recordInfo);

    @Update
    void update(PlayRecordInfo recordInfo);

    @Query("SELECT * FROM T_PLAY_RECORD")
    List<PlayRecordInfo> getAll();

    @Query("SELECT * FROM T_PLAY_RECORD WHERE vod_id=:vodId ")
    List<PlayRecordInfo> getById(String vodId);
}
