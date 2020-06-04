package com.movtalent.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.lib.common.BuildConfig;
import com.media.playerlib.PlayApp;
import com.movtalent.app.util.MapBuilder;
import com.simple.spiderman.SpiderMan;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import jaygoo.library.m3u8downloader.M3U8Library;
import kale.sharelogin.ShareLoginLib;
import kale.sharelogin.qq.QQPlatform;
import kale.sharelogin.weibo.WeiBoPlatform;
import kale.sharelogin.weixin.WeiXinPlatform;

import java.util.Arrays;
import java.util.List;



/**
 * @author huangyong
 * createTime 2019/6/18
 */
public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //放在其他库初始化前
        SpiderMan.init(this).setTheme(R.style.SpiderManTheme_Dark);
        //初始化友盟统计、推送
        //初始化友盟统计
//        UMConfigure.init(this, App_Config.UMENKEY, App_Config.UMEN_APP_NAME, UMConfigure.DEVICE_TYPE_PHONE, App_Config.UMEN_PUSH_KEY);
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String s) {
//                Log.e("umentregist","注册成功：deviceToken：-------->  " + s);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                Log.e("umentregist","注册失败：deviceTokens：-------->  " + s);
//            }
//        });
//        mPushAgent.setMessageHandler(messageHandler);

        if (isRunningInMainProcess()){


            PlayApp.init(this);

            // 初始该库的基础常量
            ShareLoginLib.init(this, getString(R.string.app_name), null, BuildConfig.DEBUG);

            //m3u8缓存
            M3U8Library.init(context);
            // 初始化第三方平台的信息
            ShareLoginLib.initPlatforms(
                    // map
                    MapBuilder.of(
                            QQPlatform.KEY_APP_ID, App_Config.QQ_APP_ID,
                            QQPlatform.KEY_SCOPE, App_Config.QQ_APP_SCOP,

                            WeiBoPlatform.KEY_APP_KEY, App_Config.WEIBO_APP_KEY,
                            WeiBoPlatform.KEY_SCOPE, App_Config.WEIBO_APP_SCOP,
                            WeiBoPlatform.KEY_REDIRECT_URL, App_Config.WEIBO_REDIRECT_URL,

                            WeiXinPlatform.KEY_APP_ID, App_Config.WEICHAT_APP_ID,
                            WeiXinPlatform.KEY_SECRET, App_Config.WEICHAT_APP_SECRET
                    ),
                    // list
                    Arrays.asList(
                            QQPlatform.class,
                            WeiBoPlatform.class,
                            WeiXinPlatform.class)
            );
        }
    }

    /**
     * 判断当前进程是否是主进程，而不是推送等 service 所在的 remote 进程
     *
     * @return 当前进程是 com.xiachufang 主进程
     */
    public static boolean isRunningInMainProcess() {
        try {
            Context context = getContext();
            ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
            List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
            String mainProcessName = context.getPackageName();
            int myPid = android.os.Process.myPid();
            for (ActivityManager.RunningAppProcessInfo info : processInfos) {
                if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                    return true;
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return false;
    }

//    UmengMessageHandler messageHandler = new UmengMessageHandler(){
//
//        @Override
//        public void dealWithCustomMessage(final Context context, final UMessage msg) {
//            new Handler(getMainLooper()).post(new Runnable() {
//
//                @Override
//                public void run() {
//                    // 对于自定义消息，PushSDK默认只统计送达。若开发者需要统计点击和忽略，则需手动调用统计方法。
//                    boolean isClickOrDismissed = true;
//                    if(isClickOrDismissed) {
//                        //自定义消息的点击统计
//                        UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
//                    } else {
//                        //自定义消息的忽略统计
//                        UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
//                    }
//                    Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    };
}
