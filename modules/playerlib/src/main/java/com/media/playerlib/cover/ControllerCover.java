package com.media.playerlib.cover;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kk.taurus.playerbase.event.BundlePool;
import com.kk.taurus.playerbase.event.EventKey;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.log.PLog;
import com.kk.taurus.playerbase.player.IPlayer;
import com.kk.taurus.playerbase.player.OnTimerUpdateListener;
import com.kk.taurus.playerbase.receiver.BaseCover;
import com.kk.taurus.playerbase.receiver.IReceiverGroup;
import com.kk.taurus.playerbase.touch.OnTouchGestureListener;
import com.kk.taurus.playerbase.utils.TimeUtil;
import com.lib.common.util.AppUtils;
import com.media.playerlib.R;
import com.media.playerlib.adapter.PlayListAdapter;
import com.media.playerlib.model.DataInter;
import com.media.playerlib.model.VideoPlayVo;
import com.media.playerlib.receiver.BatteryReceiver;
import com.media.playerlib.widget.ChangeClarityDialog;
import per.goweii.anylayer.AnimHelper;
import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.LayerManager;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.media.playerlib.model.DataInter.Key.*;


/**
 * Created by Taurus on 2018/4/15.
 */

public class ControllerCover extends BaseCover implements OnTimerUpdateListener, OnTouchGestureListener, View.OnClickListener {


    private static final String TAG ="播放控制" ;
    /**
     * 全局播放索引，0开始，显示时记得加1
     */
    public static int CurrentIndex = 0;
    private static final boolean TIME_DELAY_FINISH = false;
    private final int MSG_CODE_DELAY_HIDDEN_CONTROLLER = 101;


    int EVENT_CODE_UPDATE_SEEK = -201;
    String KEY_IS_LANDSCAPE = "isLandscape";

    String KEY_DATA_SOURCE = "data_source";

    String KEY_ERROR_SHOW = "error_show";

    String KEY_COMPLETE_SHOW = "complete_show";
    String KEY_CONTROLLER_TOP_ENABLE = "controller_top_enable";
    String KEY_CONTROLLER_SCREEN_SWITCH_ENABLE = "screen_switch_enable";

    String KEY_TIMER_UPDATE_ENABLE = "timer_update_enable";

    String KEY_NETWORK_RESOURCE = "network_resource";

    private int mBufferPercentage;

    private int mSeekProgress = -1;

    private boolean mTimerUpdateProgressEnable = true;

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CODE_DELAY_HIDDEN_CONTROLLER:
                    PLog.d(getTag().toString(), "msg_delay_hidden...");
                    setControllerState(false);
                    setLockIconState(false);
                    break;
            }
        }
    };

    private boolean mGestureEnable = true;

    private String mTimeFormat;

    private boolean mControllerTopEnable;
    private ObjectAnimator mBottomAnimator;
    private ObjectAnimator mTopAnimator;
    private ObjectAnimator mLockAnimator;
    private TextView netSpeeds;
    private String netSpeed;
    private Gson gson;
    private VideoPlayVo videoPlayVo;
    private String urlListJson;
    private TextView choseList;
    private String title;
    private TextView speedUp;
    private ChangeClarityDialog mClarityDialog;
    private ImageView dlanCast;
    private ImageView playNext;
    private TextView sysTime;
    private final StringBuilder mFormatBuilder;
    private final Formatter mFormatter;
    private ImageView mBattery;
    private BatteryReceiver mBatteryReciver;
    private ImageView mLock;
    private TextView sendDanmu;

    public ControllerCover(Context context) {
        super(context);

        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    View mTopContainer;
    View mBottomContainer;
    ImageView mBackIcon;
    TextView mTopTitle;
    ImageView mStateIcon;
    TextView mCurrTime;
    TextView mTotalTime;
    SeekBar mSeekBar;
    SeekBar mBottomSeekBar;

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();
        gson = new Gson();
        View view = getView();
        mTopContainer = view.findViewById(R.id.cover_player_controller_top_container);
        mBottomContainer = view.findViewById(R.id.cover_player_controller_bottom_container);
        mBackIcon = view.findViewById(R.id.cover_player_controller_image_view_back_icon);
        mTopTitle = view.findViewById(R.id.cover_player_controller_text_view_video_title);
        mStateIcon = view.findViewById(R.id.cover_player_controller_image_view_play_state);
        mCurrTime = view.findViewById(R.id.cover_player_controller_text_view_curr_time);
        mTotalTime = view.findViewById(R.id.cover_player_controller_text_view_total_time);
        mSeekBar = view.findViewById(R.id.cover_player_controller_seek_bar);
        mBottomSeekBar = view.findViewById(R.id.cover_bottom_seek_bar);
        playNext = view.findViewById(R.id.cover_player_play_next);
        speedUp = view.findViewById(R.id.speed_up);
        netSpeeds = view.findViewById(R.id.net_speed);
        choseList = view.findViewById(R.id.chose_list);
        sysTime = view.findViewById(R.id.sys_time);
        mBattery = view.findViewById(R.id.iv_battery);
        mLock = view.findViewById(R.id.video_lock);
        sendDanmu = view.findViewById(R.id.send_danmu);
        mBatteryReciver = new BatteryReceiver(mBattery);
        choseList.setOnClickListener(this);
        speedUp.setOnClickListener(this);
        mBackIcon.setOnClickListener(this);
        mStateIcon.setOnClickListener(this);
        sendDanmu.setOnClickListener(this);
        playNext.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        getGroupValue().registerOnGroupValueUpdateListener(mOnGroupValueUpdateListener);
        mLock.setOnClickListener(this);
    }

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        //设置标题
        boolean topEnable = getGroupValue().getBoolean(KEY_CONTROLLER_TOP_ENABLE, true);
        boolean choseSeriEnable = getGroupValue().getBoolean(KEY_CHOSE_SERI_ENABLE, false);
        mControllerTopEnable = topEnable;
        //顶部控制条
        if (!topEnable) {
            setTopContainerState(false);
        }
        initSpeedWindow();
        getContext().registerReceiver(mBatteryReciver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        title = getGroupValue().getString(DataInter.Key.KEY_CURRENTPLAY_TITLE);
        setTitle(title);
        //获取全局播放信息
        String onlineMovie = getGroupValue().getString(DataInter.Key.MOVIE_INFO);
       // Log.d("全局信息", onlineMovie);
        setChoseList(onlineMovie);
    }

    private void initSpeedWindow() {
        List<String> speedItems = new ArrayList<>();
        speedItems.add("1.0 正常");
        speedItems.add("x 1.3");
        speedItems.add("x 1.5");
        speedItems.add("x 1.8");
        speedItems.add("x 2.0");
        mClarityDialog = new ChangeClarityDialog(getContext());
        mClarityDialog.setClarityGrade(speedItems, 0);
    }

    @Override
    protected void onCoverDetachedToWindow() {
        super.onCoverDetachedToWindow();
        mTopContainer.setVisibility(View.GONE);
        mBottomContainer.setVisibility(View.GONE);
        removeDelayHiddenMessage();
        getContext().unregisterReceiver(mBatteryReciver);
    }

    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();

        cancelTopAnimation();
        cancelBottomAnimation();

        getGroupValue().unregisterOnGroupValueUpdateListener(mOnGroupValueUpdateListener);
        removeDelayHiddenMessage();
        mHandler.removeCallbacks(mSeekEventRunnable);


    }

    int EVENT_CODE_REQUEST_BACK = -100;
    int EVENT_CODE_REQUEST_CLOSE = -101;

    int EVENT_CODE_REQUEST_TOGGLE_SCREEN = -104;

    int EVENT_CODE_ERROR_SHOW = -111;

    int EVENT_CODE_TO_FLOAT_MODE = 221;

    /**
     * 注册事件接收key，注册后才能接收到事件
     */
    private final IReceiverGroup.OnGroupValueUpdateListener mOnGroupValueUpdateListener =
            new IReceiverGroup.OnGroupValueUpdateListener() {
                @Override
                public String[] filterKeys() {
                    return new String[]{
                            KEY_COMPLETE_SHOW,
                            KEY_TIMER_UPDATE_ENABLE,
                            KEY_CURRENTPLAY_INDEX,
                            KEY_DATA_SOURCE,
                            KEY_IS_LANDSCAPE,
                            KEY_WINDOW_MODE,
                            KEY_CONTROLLER_TOP_ENABLE};
                }

                @Override
                public void onValueUpdate(String key, Object value) {
                    if (key.equals(KEY_COMPLETE_SHOW)) {
                        boolean show = (boolean) value;
                        if (show) {
                            setControllerState(false);
                        }
                        setGestureEnable(!show);
                    } else if (key.equals(KEY_CONTROLLER_TOP_ENABLE)) {
                        mControllerTopEnable = (boolean) value;
                        if (!mControllerTopEnable) {
                            setTopContainerState(false);
                        }
                    } else if (key.equals(KEY_IS_LANDSCAPE)) {
                        setSwitchScreenIcon((Boolean) value);
                    } else if (key.equals(KEY_TIMER_UPDATE_ENABLE)) {
                        mTimerUpdateProgressEnable = (boolean) value;
                    } else if (key.equals(KEY_DATA_SOURCE)) {

                    } else if (key.equals(KEY_WINDOW_MODE)) {
                        setScreenSwitchEnable((Boolean) value);
                        choseList.setVisibility(!(Boolean) value && videoPlayVo != null && videoPlayVo.getSeriUrls().size() > 1 ? View.VISIBLE : View.GONE);
                    } else if (key.equals(KEY_CURRENTPLAY_INDEX)) {
                        int index = getGroupValue().getInt(KEY_CURRENTPLAY_INDEX);
                        //索引更新，更新标题
                        setTitle(title + " 第" + (index + 1) + "集");
                        //如果是最后一集，不再显示下一集按钮
                        if (videoPlayVo != null && videoPlayVo.getSeriUrls() != null && index == videoPlayVo.getSeriUrls().size() - 1) {
                            playNext.setVisibility(View.GONE);
                        } else {
                            playNext.setVisibility(View.VISIBLE);
                        }

                    }

                }
            };

    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser)
                        updateUI(progress, seekBar.getMax());
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    sendSeekEvent(seekBar.getProgress());
                }
            };

    private void sendSeekEvent(int progress) {
        mTimerUpdateProgressEnable = false;
        mSeekProgress = progress;
        mHandler.removeCallbacks(mSeekEventRunnable);
        mHandler.postDelayed(mSeekEventRunnable, 300);
    }

    private final Runnable mSeekEventRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSeekProgress < 0)
                return;
            Bundle bundle = BundlePool.obtain();
            bundle.putInt(EventKey.INT_DATA, mSeekProgress);
            requestSeek(bundle);
        }
    };

    private void setTitle(String text) {
        mTopTitle.post(new Runnable() {
            @Override
            public void run() {
                mTopTitle.setText(text);
            }
        });

    }

    private void setSwitchScreenIcon(boolean isFullScreen) {
        //mSwitchScreen.setImageResource(isFullScreen ? R.mipmap.icon_exit_full_screen : R.mipmap.icon_full_screen);
    }

    private void setScreenSwitchEnable(boolean screenSwitchEnable) {
    }

    private void setBottomSeekBarState(boolean state) {
        mBottomSeekBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    private void setGestureEnable(boolean gestureEnable) {
        this.mGestureEnable = gestureEnable;
    }

    private void cancelTopAnimation() {
        if (mTopAnimator != null) {
            mTopAnimator.cancel();
            mTopAnimator.removeAllListeners();
            mTopAnimator.removeAllUpdateListeners();
        }
    }

    private void cancelLockAnimation() {
        if (mLockAnimator != null) {
            mLockAnimator.cancel();
            mLockAnimator.removeAllListeners();
            mLockAnimator.removeAllUpdateListeners();
        }
    }

    private void setTopContainerState(final boolean state) {
        if (mControllerTopEnable) {
            mTopContainer.clearAnimation();
            cancelTopAnimation();
            mTopAnimator = ObjectAnimator.ofFloat(mTopContainer,
                    "alpha", state ? 0 : 1, state ? 1 : 0).setDuration(300);
            mTopAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if (state) {
                        mTopContainer.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (!state) {
                        mTopContainer.setVisibility(View.GONE);
                    }
                }
            });
            mTopAnimator.start();
        } else {
            mTopContainer.setVisibility(View.GONE);
        }
    }

    private void setLockIconState(final boolean state) {
        mLock.clearAnimation();
        cancelLockAnimation();
        mLockAnimator = ObjectAnimator.ofFloat(mLock,
                "alpha", state ? 0 : 1, state ? 1 : 0).setDuration(300);
        mLockAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (state) {
                    mLock.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!state) {
                    mLock.setVisibility(View.GONE);
                }
            }
        });
        mLockAnimator.start();
    }

    private void cancelBottomAnimation() {
        if (mBottomAnimator != null) {
            mBottomAnimator.cancel();
            mBottomAnimator.removeAllListeners();
            mBottomAnimator.removeAllUpdateListeners();
        }
    }

    private void setBottomContainerState(final boolean state) {
        mBottomContainer.clearAnimation();
        cancelBottomAnimation();
        mBottomAnimator = ObjectAnimator.ofFloat(mBottomContainer,
                "alpha", state ? 0 : 1, state ? 1 : 0).setDuration(300);
        mBottomAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (state) {
                    mBottomContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!state) {
                    mBottomContainer.setVisibility(View.GONE);
                }
            }
        });
        mBottomAnimator.start();
        setBottomSeekBarState(!state);
    }

    private void setControllerState(boolean state) {
        if (state) {
            sendDelayHiddenMessage();
        } else {
            removeDelayHiddenMessage();
        }
        setTopContainerState(state);
        setBottomContainerState(state);
    }

    private void setLockState(boolean state) {
        if (state) {
            sendDelayHiddenMessage();
        } else {
            removeDelayHiddenMessage();
        }
        setLockIconState(state);
    }

    private boolean isControllerShow() {
        return mBottomContainer.getVisibility() == View.VISIBLE;
    }

    private void toggleController() {
        if (isControllerShow()) {
            setControllerState(false);
            setLockIconState(false);
        } else {
            setControllerState(true);
            setLockIconState(true);
        }
    }

    private void sendDelayHiddenMessage() {
        removeDelayHiddenMessage();
        mHandler.sendEmptyMessageDelayed(MSG_CODE_DELAY_HIDDEN_CONTROLLER, 5000);
    }

    private void removeDelayHiddenMessage() {
        mHandler.removeMessages(MSG_CODE_DELAY_HIDDEN_CONTROLLER);
    }

    private void setCurrTime(int curr) {
        mCurrTime.setText(TimeUtil.getTime(mTimeFormat, curr));
    }

    private void setTotalTime(int duration) {
        mTotalTime.setText(TimeUtil.getTime(mTimeFormat, duration));
    }

    private void setSeekProgress(int curr, int duration) {
        mSeekBar.setMax(duration);
        mSeekBar.setProgress(curr);
        float secondProgress = mBufferPercentage * 1.0f / 100 * duration;
        setSecondProgress((int) secondProgress);
    }

    private void setSecondProgress(int secondProgress) {
        mSeekBar.setSecondaryProgress(secondProgress);
    }

    private void setBottomSeekProgress(int curr, int duration) {
        mBottomSeekBar.setMax(duration);
        mBottomSeekBar.setProgress(curr);
        float secondProgress = mBufferPercentage * 1.0f / 100 * duration;
        mBottomSeekBar.setSecondaryProgress((int) secondProgress);
    }

    @Override
    public void onTimerUpdate(int curr, int duration, int bufferPercentage) {
        if (!mTimerUpdateProgressEnable)
            return;
        if (mTimeFormat == null || duration != mSeekBar.getMax()) {
            mTimeFormat = TimeUtil.getFormat(duration);
        }
        mBufferPercentage = bufferPercentage;
        updateUI(curr, duration);
        updateSysTime();
    }

    private void updateSysTime() {
        sysTime.setText(getCurrentSystemTime());
    }

    private void updateUI(int curr, int duration) {
        setSeekProgress(curr, duration);
        setBottomSeekProgress(curr, duration);
        setCurrTime(curr);
        setTotalTime(duration);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_DATA_SOURCE_SET:
                mBufferPercentage = 0;
                mTimeFormat = null;
                updateUI(0, 0);
                setBottomSeekBarState(true);
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE:
                int status = bundle.getInt(EventKey.INT_DATA);
                if (status == IPlayer.STATE_PAUSED) {
                    mStateIcon.setSelected(true);
                } else if (status == IPlayer.STATE_STARTED) {
                    mStateIcon.setSelected(false);
                }
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
            case OnPlayerEventListener.PLAYER_EVENT_ON_SEEK_COMPLETE:
                mTimerUpdateProgressEnable = true;
                break;
            default:
                break;
        }
    }

    /**
     * 设置选集和播放下一个
     *
     * @param movieInfo
     */
    private void setChoseList(String movieInfo) {
        if (TextUtils.isEmpty(movieInfo)) {
            return;
        }
        videoPlayVo = new Gson().fromJson(movieInfo, VideoPlayVo.class);
        if (videoPlayVo != null) {
            title = videoPlayVo.getTitle();
            CurrentIndex = getGroupValue().getInt(KEY_CURRENTPLAY_INDEX);
        }
        //选集播放
        if (videoPlayVo != null && videoPlayVo.getSeriUrls() != null) {
            choseList.setVisibility(videoPlayVo.getSeriUrls().size() > 1 ? View.VISIBLE : View.GONE);
            if (videoPlayVo.getSeriUrls().size() > 1) {
                setTitle(String.format(Locale.getDefault(), "%s 第%d集", title, CurrentIndex + 1));
            } else {
                setTitle(title);
            }
        } else {
            choseList.setVisibility(View.INVISIBLE);
            playNext.setVisibility(View.GONE);
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {
        if (eventCode == DataInter.Event.KEY_UPDATE_NET_SPEED) {
            netSpeed = bundle.getString(DataInter.Key.KEY_UPDATE_NET_SPEED_CONTENT);
            netSpeeds.post(new Runnable() {
                @Override
                public void run() {
                    if (netSpeeds.isShown()) {
                        netSpeeds.setText(netSpeed);
                    }
                }
            });
        }
    }

    @Override
    public Bundle onPrivateEvent(int eventCode, Bundle bundle) {

        if (eventCode == EVENT_CODE_UPDATE_SEEK) {
            if (bundle != null) {
                int curr = bundle.getInt(EventKey.INT_ARG1);
                int duration = bundle.getInt(EventKey.INT_ARG2);
                updateUI(curr, duration);
            }
        }
        return null;
    }


    @Override
    public View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_controller_cover, null);
    }

    @Override
    public int getCoverLevel() {
        return levelLow(1);
    }

    @Override
    public void onSingleTapUp(MotionEvent event) {
        if (!mGestureEnable) {
            toggleLock();
            return;
        }
        toggleLock();
        toggleController();
    }

    private void toggleLock() {
        setLockState(!mLock.isShown());
    }

    @Override
    public void onDoubleTap(MotionEvent event) {
    }

    @Override
    public void onDown(MotionEvent event) {
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!mGestureEnable)
            return;
    }

    @Override
    public void onEndGesture() {
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cover_player_controller_image_view_back_icon) {
            notifyReceiverEvent(EVENT_CODE_REQUEST_BACK, null);

        } else if (i == R.id.cover_player_controller_image_view_play_state) {
            boolean selected = mStateIcon.isSelected();
            if (selected) {
                requestResume(null);
            } else {
                requestPause(null);
            }
            mStateIcon.setSelected(!selected);

        } else if (i == R.id.chose_list) {
            showChoseListWindow();
        } else if (i == R.id.speed_up) {
            //倍速
            showSpeedUpWindow();
        } else if (i == R.id.cover_player_play_next) {
            //播放下一个
            playNextFilm();
        } else if (i == R.id.video_lock) {
            //锁定屏幕触摸
            lockScreen();
        } else if (i == R.id.send_danmu) {
            if (AppUtils.checkLogin(getContext())) {
                shoDanmuWindow();
            } else {
                Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void shoDanmuWindow() {
        notifyReceiverEvent(DataInter.Event.EVENT_CODE_SHOW_DANMU_INPUT, null);
    }

    private void lockScreen() {
        if (mLock.isSelected()) {
            mLock.setSelected(false);
            setGestureEnable(true);
            setControllerState(true);
            getGroupValue().putBoolean(DataInter.Key.KEY_GESTURE_ENABLE, true);
        } else {
            mLock.setSelected(true);
            setControllerState(false);
            sendDelayHiddenMessage();
            setGestureEnable(false);
            getGroupValue().putBoolean(DataInter.Key.KEY_GESTURE_ENABLE, false);
        }
    }
    private void playNextFilm() {
        String currentUrl = getGroupValue().getString(DataInter.Key.KEY_CURRENTPLAY_URL);
        if (videoPlayVo != null && videoPlayVo.getSeriUrls().size() > 1) {
            for (int i = 0; i < videoPlayVo.getSeriUrls().size() - 1; i++) {
                if (currentUrl.equals(videoPlayVo.getSeriUrls().get(i))) {
                    final String nextUrl = videoPlayVo.getSeriUrls().get(i + 1);
                    final int finalI = i;
                    mTopTitle.post(new Runnable() {
                        @Override
                        public void run() {
                            setTitle(String.format(Locale.getDefault(), "%s 第%d集", title, finalI + 1));
                            getGroupValue().putString(DataInter.Key.KEY_CURRENTPLAY_TITLE, String.format(Locale.getDefault(), "%s 第%d集", title, finalI + 1));
                            getGroupValue().putString(DataInter.Key.KEY_CURRENTPLAY_URL, nextUrl);
                            Bundle bundle = new Bundle();
                            bundle.putString(DataInter.Key.KEY_CURRENTPLAY_URL, nextUrl);
                            notifyReceiverEvent(DataInter.Event.EVENT_CODE_SERI_NEXT, bundle);

                            //更新当前播放索引
                            ControllerCover.CurrentIndex = finalI + 1;
                            getGroupValue().putInt(DataInter.Key.KEY_CURRENTPLAY_INDEX, CurrentIndex);
                            Log.d(TAG, "run: "+currentUrl);

                        }
                    });
                    break;
                }
            }
        }

    }



    /**
     * 倍速对话框
     */
    private void showSpeedUpWindow() {

        mClarityDialog.setOnClarityCheckedListener(new ChangeClarityDialog.OnClarityChangedListener() {
            @Override
            public void onClarityChanged(int clarityIndex) {
                Bundle bundle = new Bundle();
                bundle.putInt(DataInter.Key.KEY_SPEED_UP, clarityIndex);
                notifyReceiverEvent(DataInter.Event.KEY_CHOSE_SPEED, bundle);
                mClarityDialog.dismiss();
            }

            @Override
            public void onClarityNotChanged() {
                mClarityDialog.dismiss();
            }
        });
        mClarityDialog.show();
    }

    private void showChoseListWindow() {

        final AnyLayer anyLayer = AnyLayer.with(getContext())
                .contentView(R.layout.play_list_layout)
                .gravity(Gravity.RIGHT)
                .contentAnim(new LayerManager.IAnim() {
                    @Override
                    public Animator inAnim(View content) {
                        return AnimHelper.createRightInAnim(content);
                    }

                    @Override
                    public Animator outAnim(View content) {
                        return AnimHelper.createRightOutAnim(content);
                    }
                });
        anyLayer.show();


        if (videoPlayVo == null) {
            return;
        }

        RecyclerView playList = anyLayer.getView(R.id.play_list);
        PlayListAdapter adapter = new PlayListAdapter(videoPlayVo, getContext(), new OnItemClickedListener() {
            @Override
            public void clicked(final String url, final int position) {

                //播放切换之前应该保存记录
                Bundle save = new Bundle();
                save.putInt(DataInter.Key.KEY_CURRENTPLAY_PROGRESS, getPlayerStateGetter().getCurrentPosition());
                save.putString(DataInter.Key.KEY_CURRENTPLAY_URL, getGroupValue().getString(DataInter.Key.KEY_CURRENTPLAY_URL));
                notifyReceiverEvent(DataInter.Event.EVENT_CODE_SAVE_PROGRESS, save);


                Bundle bundle = new Bundle();
                bundle.putString(DataInter.Key.KEY_CURRENTPLAY_URL, url);
                notifyReceiverEvent(DataInter.Event.EVENT_CODE_SERI_NEXT, bundle);

                mTopTitle.post(new Runnable() {
                    @Override
                    public void run() {
                        setTitle(String.format(Locale.getDefault(), "%s 第%d集", title, position + 1));
                        getGroupValue().putString(DataInter.Key.KEY_CURRENTPLAY_TITLE, String.format(Locale.getDefault(), "%s 第%d集", title, position + 1));
                        getGroupValue().putString(DataInter.Key.KEY_CURRENTPLAY_URL, url);
                        getGroupValue().putInt(DataInter.Key.KEY_CURRENTPLAY_INDEX, position);
                    }
                });
                anyLayer.dismiss();


            }
        });
        playList.setLayoutManager(new GridLayoutManager(getContext(), 6));
        playList.setAdapter(adapter);

        playList.smoothScrollToPosition(CurrentIndex);

        anyLayer.getContentView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && anyLayer != null && anyLayer.isShow()) {
                    float y = event.getRawY();
                    int[] location = new int[2];

                    View view = anyLayer.getView(R.id.play_list);
                    view.getLocationInWindow(location);
                    if (y > location[1] + view.getHeight()) {
                        anyLayer.dismiss();
                    }
                    return true;
                }
                return true;
            }
        });
    }

    /**
     * 获取当前系统时间
     */
    protected String getCurrentSystemTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    protected String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public interface OnItemClickedListener {
        void clicked(String url, int position);
    }

}
