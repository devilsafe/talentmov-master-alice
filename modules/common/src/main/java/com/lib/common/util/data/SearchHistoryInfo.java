package com.lib.common.util.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


/**
 */

@Entity(tableName = SearchHistoryInfo.TABLE_NAME, indices = {@Index("searchKeyWords")})
public class SearchHistoryInfo {

    public static final String TABLE_NAME = "t_search";

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "searchKeyWords")
    public String searchKeyWords;
}
