package com.movtalent.app.db;

import com.lib.common.util.AppDbManager;
import com.lib.common.util.data.HistoryInfo;
import com.lib.common.util.data.SearchHistoryInfo;
import com.lib.common.util.room.HistoryDao;
import com.movtalent.app.App;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * creator huangyong
 */
public class DbHelper {

    public static ArrayList<SearchHistoryInfo> getAllHistory() {

        ArrayList<SearchHistoryInfo> searchHistories = (ArrayList<SearchHistoryInfo>) AppDbManager.getInstance(App.getContext()).searchDao().getAll();
        if (searchHistories != null && searchHistories.size() > 0) {
            return searchHistories;
        } else {
            return new ArrayList<>();
        }
    }


    public static boolean checkKeyWords(String keyword) {

        ArrayList<SearchHistoryInfo> byKeywords = (ArrayList<SearchHistoryInfo>) AppDbManager.getInstance(App.getContext()).searchDao().getByKeywords(keyword);
        return byKeywords != null && byKeywords.size() > 0;
    }


    public static void addKeywords(String keyword) {
        ArrayList<SearchHistoryInfo> allHistory = getAllHistory();
        if (allHistory.size() > 16) {
            AppDbManager.getInstance(App.getContext()).searchDao().delete(allHistory.get(0));
        }

        SearchHistoryInfo searchHistory = new SearchHistoryInfo();
        searchHistory.searchKeyWords = keyword;
        AppDbManager.getInstance(App.getContext()).searchDao().insert(searchHistory);
    }


    public static void clearKeywords() {
        ArrayList<SearchHistoryInfo> allHistory = getAllHistory();
        if (allHistory != null && allHistory.size() > 0) {
            for (SearchHistoryInfo history : allHistory) {
                AppDbManager.getInstance(App.getContext()).searchDao().delete(history);
            }
        }
    }


    public static void clearHistory() {
        HistoryDao historyDao = AppDbManager.getInstance(App.getContext()).historyDao();
        List<HistoryInfo> infos = historyDao.getAll();
        Iterator<HistoryInfo> iterator = infos.iterator();
        while (iterator.hasNext()){
            HistoryInfo next = iterator.next();
            if (next!=null){
                historyDao.delete(next);
            }
        }
    }
}
