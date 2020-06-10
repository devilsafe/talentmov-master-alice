package com.movtalent.app.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import com.lib.common.util.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by H19 on 2018/9/5 0005.
 */

public class Jiexi {

    private Activity context;
    private int mState;
    private String mvUrl; // 待解析的地址
    private int cutIndex;// 次数
    private ParseWebUrlHelper mParseHelper;
    public Jiexi init(Activity context, OnListener listener){
        this.context = context;
        this.mListener = listener;

        // ----- init 接口列表 -----------------
        mJklist = new ArrayList<>();
        if (mJklist.size() < 1){
            mJklist.add(new DBjk("线路2","https://parse.xymov.net/?url=",2));
        }


        return this;
    };

    private List<DBjk> mJklist = new ArrayList<>();

    public void start(String t){
        if (mParseHelper != null) mParseHelper.stop();
        cutIndex = 0;
        mvUrl = t;
        start2();
    }
    private void start2(){
        // 开启定时
        isEnt = false;
        int str_time = ShareSpUtils.getInt(context,"player_parse_waiting_time",12);
        new Handler().postDelayed(parseTime,str_time * 1000);

        // 开始解析，必须在ui线程中解析
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mJklist.size() >= cutIndex) cutIndex = 0;
                String url = mJklist.get(cutIndex).getUrl() + mvUrl;
                mParseHelper = ParseWebUrlHelper.getInstance().init(context, url);
                mParseHelper.setOnParseListener(new ParseWebUrlHelper.OnParseWebUrlListener() {
                    @Override
                    public void onFindUrl(String url,final String type,Map<String, String> headers) {
                        if (type.equals("mp4") && url.contains("mp4?vkey=") && url.length() > 500){
                            url = StringUtil.getLeftText(url,".mp4?vkey=") + ".mp4";
                        }
                        final String xurl = url;
                        isEnt = true;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mListener.ent(Jiexi.this,0,xurl,type);
                            }
                        });

                    }
                    @Override
                    public void onError(String errorMsg) {
                        if (mParseHelper != null){
                            handler.sendEmptyMessage(-1);
                        }
                        mListener.ent(Jiexi.this,-1,errorMsg,null);
                    }
                });
                mParseHelper.startParse();
            }
        });
    }


    private boolean isEnt;

    // 解析计时
    Runnable parseTime = new Runnable() {
        @Override
        public void run() {
            if (!isEnt){
                mListener.ent(Jiexi.this,2,"超时",null);
                mParseHelper.stop();
                mParseHelper = null;
                handler.sendEmptyMessage(-1);
            }
        }
        public void inin(){

        }
    };

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){

                case -1: // 解析失败
                    if (cutIndex < mJklist.size()){
                        cutIndex=cutIndex+1;
                        start2();
                    }
                    break;
            }
            return false;
        }
    });


    private OnListener mListener;

    public void stop() {
        if (mParseHelper!=null){
            mParseHelper.stop();
            mParseHelper = null;
        }
    }

    public interface OnListener{
        void ent(Jiexi t, int errId, String msg, String type);
    }



    private int STATE_ING = 1; // 进行中
    private int STATE_FINISH = 2; // 完成
    private int STATE_STOP = 3; // 停止
    private int STATE_ERROR = 4; // 错误



}
