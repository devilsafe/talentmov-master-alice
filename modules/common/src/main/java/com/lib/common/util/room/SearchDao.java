package com.lib.common.util.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.lib.common.util.data.SearchHistoryInfo;

import java.util.List;


@Dao
public interface SearchDao {

    @Insert
    void insert(SearchHistoryInfo trackData);

    @Delete
    void delete(SearchHistoryInfo trackData);


    @Query("SELECT * FROM T_SEARCH")
    List<SearchHistoryInfo> getAll();


    @Query("SELECT * FROM T_SEARCH WHERE  searchKeyWords=:keyword ")
    List<SearchHistoryInfo> getByKeywords(String keyword);

}
