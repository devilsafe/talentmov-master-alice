package com.lib.common.util.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


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
