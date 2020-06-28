package com.lib.common.util.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


/**
 */

@Entity(tableName = DiggCommentInfo.TABLE_NAME, indices = {@Index("comment_id")})
public class DiggCommentInfo {

    public static final String TABLE_NAME = "t_digg_comment";

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "comment_id")
    public int commentId;

    @ColumnInfo(name = "status")
    public int status;
}
