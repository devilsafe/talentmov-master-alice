package com.lib.common.util.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = HistoryInfo.TABLE_NAME, indices = {@Index("vod_id")})
public class HistoryInfo {

    public static final String TABLE_NAME = "t_vod_history";

    @ColumnInfo(name = "vod_id")
    private int vodId;

    public int getVodId() {
        return vodId;
    }

    public void setVodId(int vodId) {
        this.vodId = vodId;
    }

    @ColumnInfo(name = "vod_index")
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @ColumnInfo(name = "vod_json")
    private String vodJson;

    @ColumnInfo(name = "vod_time_update")
    private String updateTime;


    public String getVodJson() {
        return vodJson;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setVodJson(String vodJson) {
        this.vodJson = vodJson;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @ColumnInfo(name = "progress")
    private int progress;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
