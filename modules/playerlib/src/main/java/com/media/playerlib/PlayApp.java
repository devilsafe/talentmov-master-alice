package com.media.playerlib;

import android.content.Context;
import com.kk.taurus.exoplayer.ExoMediaPlayer;
import com.kk.taurus.ijkplayer.IjkPlayer;
import com.kk.taurus.playerbase.config.PlayerConfig;
import com.kk.taurus.playerbase.config.PlayerLibrary;


/**
 * createTime 2019/6/23
 */
public class PlayApp {

    //试看
    public static final int AUTH_GUEST = 0;
    //完整
    public static final int AUTH_ALL = 3;


    public static final int PLAN_ID_IJK = 100;
    public static final int PLAN_ID_EXO = 200;
    public static final int PLAN_ID_MEDIA = 0;
    public static Context getContext() {
        return context;
    }

    private static Context context;

    public static void init(Context contexts) {
        context = contexts;
        //播放记录的配置
        IjkPlayer.init(context);
        ExoMediaPlayer.init(context);
        //开启播放记录

        PlayerConfig.setDefaultPlanId(PLAN_ID_MEDIA);
        PlayerLibrary.init(context);

        PlayerConfig.playRecord(false);
        //关闭播放记录
        PlayerConfig.playRecord(false);
    }

    public static void swich(int planId) {
        PlayerConfig.setDefaultPlanId(planId);
        PlayerLibrary.init(context);
    }
}
