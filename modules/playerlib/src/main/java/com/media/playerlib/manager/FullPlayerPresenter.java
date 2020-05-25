package com.media.playerlib.manager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kk.taurus.playerbase.assist.AssistPlay;
import com.kk.taurus.playerbase.assist.OnAssistPlayEventHandler;
import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.EventDispatcher;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.player.IPlayer;
import com.kk.taurus.playerbase.receiver.BaseCover;
import com.kk.taurus.playerbase.receiver.OnReceiverEventListener;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.kk.taurus.playerbase.render.AspectRatio;
import com.lib.common.util.AppDbManager;
import com.lib.common.util.data.PlayRecordInfo;
import com.lib.common.util.room.RecordDao;
import com.lib.common.util.tool.StringUtil;
import com.media.playerlib.cover.ControllerCover;
import com.media.playerlib.cover.GestureCover;
import com.media.playerlib.cover.LoadingCover;
import com.media.playerlib.model.DataInter;
import com.media.playerlib.model.VideoPlayVo;
import com.media.playerlib.widget.OrientationSensor;

import java.util.List;


/**
 * @author huangyong
 * createTime 2019/6/23
 * 全屏播放
 */
public class FullPlayerPresenter {
    public static boolean ignoreMobile;
    private Context context;
    private RelationAssist mAssist;
    private EventDispatcher dispatcher;
    /**
     * 视频填充比，填充布局
     */
    private AspectRatio mAspectRatio = AspectRatio.AspectRatio_FIT_PARENT;
    private boolean isLandScape;
    private int originHeight;
    private ReceiverGroup receiverGroup;
    private OrientationSensor mOrientationSensor;
    private ViewGroup fullContent;
    private int progress;

    public void initView(Context context, FrameLayout fullContainer) {
        this.fullContent = fullContainer;
        this.context = context;

        receiverGroup = new ReceiverGroup(null);
     //   receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_ERROR_COVER, new ErrorCover(context));
        receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER, new GestureCover(context));
        receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_LOADING_COVER, new LoadingCover(context));


        dispatcher = new EventDispatcher(receiverGroup);

        mAssist = new RelationAssist(context);
        mAssist.setAspectRatio(mAspectRatio);
        mAssist.setOnReceiverEventListener(receiverEventListener);
        mAssist.setReceiverGroup(receiverGroup);
        mAssist.setEventAssistHandler(eventHandler);
        mAssist.setOnPlayerEventListener(this.eventListener);
        mAssist.attachContainer(fullContent);
        changeMode(true);

    }

    OnAssistPlayEventHandler eventHandler = new OnAssistPlayEventHandler() {
        @Override
        public void onAssistHandle(AssistPlay assist, int eventCode, Bundle bundle) {
            super.onAssistHandle(assist, eventCode, bundle);
            switch (eventCode) {
                case DataInter.Event.EVENT_CODE_SERI_NEXT:
                    String playUrl = bundle.getString(DataInter.Key.KEY_CURRENTPLAY_URL);
                    int anInt = mAssist.getReceiverGroup().getGroupValue().getInt(DataInter.Key.KEY_CURRENTPLAY_INDEX);
                    if (playUrl.endsWith(".html")) {
                        switchPlayFirst((Activity)context,(FrameLayout) fullContent,playUrl,anInt+1);
                        return;
                    }
                    refreshStartPosition(playUrl);
                    switchPlay(playUrl,anInt);
                    break;
                case DataInter.Event.KEY_CHOSE_SPEED:
                    int speedUp = bundle.getInt(DataInter.Key.KEY_SPEED_UP, 0);
                    float[] speedItem = {
                            1.0f,
                            1.3f,
                            1.5f,
                            1.8f,
                            2.0f
                    };
                    mAssist.setSpeed(speedItem[speedUp]);
                    break;
                case DataInter.Event.EVENT_CODE_SAVE_PROGRESS:
                    String url = bundle.getString(DataInter.Key.KEY_CURRENTPLAY_URL);
                    saveProgress(url, mAssist.getCurrentPosition());
                    break;
                default:
                    break;
            }
        }
    };
    public void switchPlayFirst(Activity smartDetailActivity, FrameLayout loadingweb, String url,int index) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(smartDetailActivity, "初始化出错，请退出重试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (playParseUrl(smartDetailActivity, loadingweb, url,index)) return;
        if (mAssist != null) {
            mAssist.play();
        }

    }

    private boolean playParseUrl(Activity smartDetailActivity, FrameLayout loadingweb, String playUrl,int index) {
        if (playUrl.endsWith(".html")) {
            mAssist.pause();
            Toast.makeText(smartDetailActivity, "正在解析，请耐心等待", Toast.LENGTH_SHORT).show();
            ParsePlayUtils.getInstance().toParsePlay(smartDetailActivity, playUrl, loadingweb, new ParsePlayUtils.OnPlayUrlFindListener() {
                @Override
                public void onFindUrl(String url) {
                    switchPlay(url, index);
                }

                @Override
                public void onError() {

                }
            });
            return true;
        }else {
            switchPlay(playUrl,index);
        }
        return false;
    }
    private void saveProgress(String playUrl, int progress) {
        //进度记录
        if (!TextUtils.isEmpty(playUrl)) {
            RecordDao recordDao = AppDbManager.getInstance(context).recordDao();
            List<PlayRecordInfo> prgInfo = recordDao.getById(StringUtil.stringToMD5(playUrl));
            //有记录
            if (prgInfo != null && prgInfo.size() > 0) {
                PlayRecordInfo recordInfo = prgInfo.get(0);
                recordInfo.setStartPos(progress);
                recordDao.update(recordInfo);
            } else {
                //新记录
                PlayRecordInfo historyInfo2 = new PlayRecordInfo();
                historyInfo2.setVodId(StringUtil.stringToMD5(playUrl));
                historyInfo2.setStartPos(progress);
                recordDao.insert(historyInfo2);
            }

        }
    }




    /**
     * 填充播放数据
     *
     * @param videoPlayVo
     */
    public void initData(VideoPlayVo videoPlayVo) {

        Gson gson = new Gson();
        String json = gson.toJson(videoPlayVo);
        mAssist.getReceiverGroup().getGroupValue().putString(DataInter.Key.MOVIE_INFO, json);
        mAssist.getReceiverGroup().getGroupValue().putString(DataInter.Key.KEY_CURRENTPLAY_URL, videoPlayVo.getPlayUrl());
        mAssist.getReceiverGroup().getGroupValue().putString(DataInter.Key.KEY_CURRENTPLAY_TITLE, videoPlayVo.getTitle());

        //创建播放源
        DataSource dataSource = new DataSource(videoPlayVo.getPlayUrl());
        dataSource.setTitle(videoPlayVo.getTitle());
        //设置播放地址
        mAssist.setDataSource(dataSource);
        //加入容器view，显示出来
        mAssist.attachContainer(fullContent);
    }


    /**
     * 自定义事件接收监听，这个事件可以是cover发出的
     * 需要根据自己需要自定义事件的 eventCode-bundle,如果没有bundle可以传null
     * example: {@link BaseCover#requestResume(Bundle)}
     * 例如：这里是接收了静音的事件
     */
    private OnReceiverEventListener receiverEventListener = new OnReceiverEventListener() {
        @Override
        public void onReceiverEvent(int eventCode, Bundle bundle) {
            switch (eventCode) {
                case DataInter.Event.EVENT_CODE_DOUBLE_CLICK:
                    if (mAssist.isPlaying()) {
                        pause();
                    } else {
                        resume();
                    }
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
                    toggleFull();
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                    onBackPress();
                    break;
                case DataInter.Event.FULLSCREEN:
                    toggleFull();
                    break;
                case DataInter.Event.EVENT_CODE_SHOW_DANMU_INPUT:
                    break;
                case DataInter.Event.EVENT_CODE_CLOSE_SMALL_WINDOW:
                    if (controlListener != null) {
                        controlListener.closeSwd();
                        mAssist.pause();
                        mAssist.stop();
                    }
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_START:
                    mAssist.play();
                default:
                    break;
            }
        }
    };

    /**
     * 返回键事件
     */
    public void onBackPress() {
        if (isLandScape) {

            toggleFull();
        } else {
            ((Activity) context).onBackPressed();
        }
    }


    /**
     * 全屏切换
     */
    private void toggleFull() {

        ((Activity) context).setRequestedOrientation(isLandScape ?
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT :
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 播放器自身播放过程中的事件，比如，播放完成、暂停、启播、重播等事件。
     * 不需要自定义，添加监听后可直接获取这些事件
     */
    private OnPlayerEventListener eventListener = new OnPlayerEventListener() {
        @Override
        public void onPlayerEvent(int eventCode, Bundle bundle) {
            switch (eventCode) {
                case OnPlayerEventListener.PLAYER_EVENT_ON_AUDIO_RENDER_START:
                    fullContent.setBackgroundColor(Color.BLACK);
                    break;
                case OnPlayerEventListener.PLAYER_EVENT_ON_PREPARED:
                    if (progress > 100) {
                        mAssist.seekTo(progress-100);
                    }else {
                        mAssist.seekTo(progress);
                    }
                    break;
                case OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET:
                    updateCurrentIndexToDetail();
                    break;
                case OnPlayerEventListener.PLAYER_EVENT_ON_STOP:
                case OnPlayerEventListener.PLAYER_EVENT_ON_PAUSE:

                    String url = mAssist.getReceiverGroup().getGroupValue().getString(DataInter.Key.KEY_CURRENTPLAY_URL);
                    int progress = mAssist.getReceiverGroup().getGroupValue().getInt(DataInter.Key.KEY_CURRENTPLAY_PROGRESS);
                    saveProgress(url, mAssist.getCurrentPosition());
                    break;
                default:
                    break;
            }
        }
    };

    private void updateCurrentIndexToDetail() {
    }


    public void pause() {
        if (mAssist == null) {
            return;
        }
        int state = mAssist.getState();
        if (state == IPlayer.STATE_PLAYBACK_COMPLETE) {
            return;
        }
        if (mAssist.isInPlaybackState()) {
            mAssist.pause();
        } else {
            mAssist.stop();
        }
    }

    public void resume() {
        if (mAssist == null) {
            return;
        }
        int state = mAssist.getState();
        if (state == IPlayer.STATE_PLAYBACK_COMPLETE) {
            return;
        }
        if (mAssist.isInPlaybackState()) {
            mAssist.resume();
        } else {
            mAssist.rePlay(0);
        }
    }

    public void destroy() {
        if (mAssist != null) {

            if (playListener != null) {
                //保存进度
                playListener.onExit(mAssist.getCurrentPosition(),mAssist.getDuration(), mAssist.getReceiverGroup().getGroupValue().getInt(DataInter.Key.KEY_CURRENTPLAY_INDEX));
                //影片独立保存的进度
            }
            mAssist.reset();
            mAssist.destroy();
            fullContent.removeAllViews();
            //重置网络判断
            ignoreMobile = false;
        }
    }

    /**
     * 选集切换
     *
     * @param url
     * @param index
     */
    public void switchPlay(String url, int index) {
        if (mAssist != null) {
            mAssist.pause();
            mAssist.seekTo(0);
            mAssist.reset();
            progress = 0;
            refreshStartPosition(url);
            DataSource dataSource = new DataSource(url);
            mAssist.setDataSource(dataSource);
            mAssist.getReceiverGroup().getGroupValue().putString(DataInter.Key.KEY_CURRENTPLAY_URL, url);
            mAssist.getReceiverGroup().getGroupValue().putInt(DataInter.Key.KEY_CURRENTPLAY_INDEX, index);
            mAssist.play();
        }
    }

    private void refreshStartPosition(String url) {
        if (!TextUtils.isEmpty(url)) {
            RecordDao dao2 = AppDbManager.getInstance(context).recordDao();
            List<PlayRecordInfo> info2s = dao2.getById(StringUtil.stringToMD5(url));
            if (info2s != null && info2s.size() > 0) {
                progress = info2s.get(0).getStartPos();
            }
        }else {
            Toast.makeText(context, "当前影片无法播放", Toast.LENGTH_SHORT).show();
        }
    }


    private void changeMode(boolean isLandscape) {
        if (isLandscape) {
            mAssist.getReceiverGroup().clearReceivers();
            mAssist.getReceiverGroup().addReceiver(DataInter.ReceiverKey.KEY_CONTROLLER_COVER, new ControllerCover(context));
            //receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_ERROR_COVER, new ErrorCover(context));
            receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER, new GestureCover(context));
            receiverGroup.addReceiver(DataInter.ReceiverKey.KEY_LOADING_COVER, new LoadingCover(context));
        } else {
        }
    }



    public void setControlListener(OnControlListener controlListener) {
        this.controlListener = controlListener;
    }

    private OnControlListener controlListener;

    public void onConfigurationChanged(Configuration newConfig) {
        isLandScape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;
        ViewGroup.LayoutParams params = fullContent.getLayoutParams();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;

        }
        fullContent.setLayoutParams(params);
        mAssist.getReceiverGroup().getGroupValue().putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, isLandScape);
    }

    public interface OnControlListener {

        void closeSwd();

        void onPlayParseUrl(String parseUrl, int anInt);
    }

    public void setPlayListener(OnPlayListener playListener) {
        this.playListener = playListener;
    }

    private OnPlayListener playListener;
    public interface OnPlayListener{
        void onExit(int currentPosition, int duration, int currentIndex);
    }
}
