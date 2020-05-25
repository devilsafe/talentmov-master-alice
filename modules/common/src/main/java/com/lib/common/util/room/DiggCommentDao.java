package com.lib.common.util.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
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
