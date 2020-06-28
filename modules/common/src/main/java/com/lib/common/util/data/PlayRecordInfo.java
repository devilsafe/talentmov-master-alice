package com.lib.common.util.data;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = PlayRecordInfo.TABLE_NAME, indices = {@Index("vod_id")})
public class PlayRecordInfo {

    public static final String TABLE_NAME = "t_play_record";


    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    @ColumnInfo(name = "start_pos")
    private int startPos;


    @ColumnInfo(name = "vod_id")
    private String vodId;

    public String getVodId() {
        return vodId;
    }

    public void setVodId(String vodId) {
        this.vodId = vodId;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
