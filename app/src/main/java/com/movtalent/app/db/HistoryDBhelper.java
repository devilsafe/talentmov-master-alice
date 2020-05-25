package com.movtalent.app.db;

import com.google.gson.Gson;
import com.lib.common.util.AppDbManager;
import com.lib.common.util.data.HistoryInfo;
import com.lib.common.util.room.HistoryDao;
import com.movtalent.app.App;
import com.movtalent.app.model.vo.CommonVideoVo;

import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-22
 */
public class HistoryDBhelper {


    public static void saveHistory(CommonVideoVo videoVo, int currentPosition,int duration, int currentIndex) {
        HistoryDao historyDao = AppDbManager.getInstance(App.getContext()).historyDao();
        List<HistoryInfo> videos = historyDao.getById(Integer.parseInt(videoVo.getMovId()));
        if (videos!=null&&videos.size()>0){


            videos.get(0).setProgress(currentPosition);
            videos.get(0).setIndex(currentIndex);
            videos.get(0).setUpdateTime(System.currentTimeMillis()+"");
            historyDao.update(videos.get(0));
        }else {
            HistoryInfo info = new HistoryInfo();
            info.setProgress(currentPosition);
            info.setIndex(currentIndex);
            info.setUpdateTime(System.currentTimeMillis()+"");
            info.setVodId(Integer.parseInt(videoVo.getMovId()));
            info.setVodJson(new Gson().toJson(videoVo));
            historyDao.insert(info);
        }
    }

    public static void checkHistoryAndPlay(int vodId,OnPlaySwitchListener playSwitchListener) {
        HistoryDao historyDao = AppDbManager.getInstance(App.getContext()).historyDao();
        List<HistoryInfo> infos = historyDao.getById(vodId);
        if (infos!=null&&infos.size()>0){
            int index = infos.get(0).getIndex();
            playSwitchListener.onPlay(index);
        }else {
            playSwitchListener.onPlay(0);
        }
    }

    public static void delete(String vodId) {
        HistoryDao historyDao = AppDbManager.getInstance(App.getContext()).historyDao();
        List<HistoryInfo> infos = historyDao.getById(Integer.parseInt(vodId));
        if (infos!=null&&infos.size()>0){
            historyDao.delete(infos.get(0));
        }
    }

    public interface OnPlaySwitchListener{
        void onPlay(int index);
    }
}
