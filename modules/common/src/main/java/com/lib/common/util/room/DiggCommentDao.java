package com.lib.common.util.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.lib.common.util.data.DiggCommentInfo;

import java.util.List;


@Dao
public interface DiggCommentDao {

    @Insert
    void insert(DiggCommentInfo trackData);

    @Delete
    void delete(DiggCommentInfo trackData);


    @Query("SELECT * FROM T_DIGG_COMMENT")
    List<DiggCommentInfo> getAll();


    @Query("SELECT * FROM T_DIGG_COMMENT WHERE  comment_id=:commentID ")
    List<DiggCommentInfo> getByCommentId(int commentID);

}
