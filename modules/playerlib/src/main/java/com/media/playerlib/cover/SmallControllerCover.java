package com.media.playerlib.cover;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kk.taurus.playerbase.entity.DataSource;
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
import com.media.playerlib.model.DataInter;
import com.media.playerlib.model.VideoPlayVo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import static com.media.playerlib.model.DataInter.Key.KEY_CURRENTPLAY_INDEX;
import static com.media.playerlib.model.DataInter.Key.KEY_WINDOW_MODE;


/**
 * Created by Taurus on 2018/4/15.
 */

public class SmallControllerCover extends BaseCover implements OnTimerUpdateListener, OnTouchGestureListener, View.OnClickListener {


    public static int CurrentIndex = 0;
    private static final boolean TIME_DELAY_FINISH = false;
    private final int MSG_CODE_DELAY_HIDDEN_CONTROLLER = 101;


    private final String KEY_IS_LANDSCAPE = "isLandscape";

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

    private final Handler callbacks = new Handler(Looper.getMainLooper()) {
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
    private ObjectAnimator mTopAnimators;
    private ObjectAnimator mLockAnimators;
    private TextView netSpeeds;
    private String netSpeed;
    private Gson gson;
    private VideoPlayVo videoPlayVo;
    private String urlListJson;
    private String title;
    private ImageView playNext;
    private final StringBuilder mFormatBuilder;
    private final Formatter mFormatter;
    private ImageView mLock;
    private ImageView fullBt;
    private TextView sendDanmu;
    private ImageView closePlay;
    private  int currentIndex;

    public SmallControllerCover(Context context) {
        super(context);

        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    View mTopContainer;
    View mBottomContainer;
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
        closePlay = view.findViewById(R.id.close_play);
        mBottomContainer = view.findViewById(R.id.cover_player_controller_bottom_container);
        mTopTitle = view.findViewById(R.id.cover_player_controller_text_view_video_title);
        mStateIcon = view.findViewById(R.id.cover_player_controller_image_view_play_state);
        mCurrTime = view.findViewById(R.id.cover_player_controller_text_view_curr_time);
        mTotalTime = view.findViewById(R.id.cover_player_controller_text_view_total_time);
        mSeekBar = view.findViewById(R.id.cover_player_controller_seek_bar);
        mBottomSeekBar = view.findViewById(R.id.cover_bottom_seek_bar);
        playNext = view.findViewById(R.id.cover_player_play_next);
        netSpeeds = view.findViewById(R.id.net_speed);
        fullBt = view.findViewById(R.id.full_screen);
        mLock = view.findViewById(R.id.video_lock);
        sendDanmu = view.findViewById(R.id.send_danmu);
        sendDanmu.setOnClickListener(this);
        mStateIcon.setOnClickListener(this);
        playNext.setOnClickListener(this);
        fullBt.setOnClickListener(this);
        closePlay.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        getGroupValue().registerOnGroupValueUpdateListener(groupValueUpdateListener);

        mLock.setOnClickListener(this);
    }

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        //设置标题
        boolean topEnable = getGroupValue().getBoolean(KEY_CONTROLLER_TOP_ENABLE, true);
        mControllerTopEnable = topEnable;
        title = getGroupValue().getString(DataInter.Key.KEY_CURRENTPLAY_TITLE);
        currentIndex = getGroupValue().getInt(KEY_CURRENTPLAY_INDEX);
        setTitle(title);
        //顶部控制条
        if (!topEnable) {
            setTopContainerState(false);
        }
    }


    @Override
    protected void onCoverDetachedToWindow() {
        super.onCoverDetachedToWindow();
        mTopContainer.setVisibility(View.GONE);
        mBottomContainer.setVisibility(View.GONE);
        removeDelayHiddenMessage();
    }

    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();

        cancelTopAnimation();
        cancelBottomAnimation();

        getGroupValue().unregisterOnGroupValueUpdateListener(groupValueUpdateListener);
        removeDelayHiddenMessage();
        callbacks.removeCallbacks(mSeekEventRunnable);


    }

    int EVENT_CODE_REQUEST_BACK = -100;
    int EVENT_CODE_REQUEST_CLOSE = -101;

    int EVENT_CODE_REQUEST_TOGGLE_SCREEN = -104;

    int EVENT_CODE_ERROR_SHOW = -111;

    int EVENT_CODE_TO_FLOAT_MODE = 221;

    /**
     * 注册事件接收key，注册后才能接收到事件
     */
    private final IReceiverGroup.OnGroupValueUpdateListener groupValueUpdateListener =
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
                    } else if (key.equals(KEY_CURRENTPLAY_INDEX)) {


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
        callbacks.removeCallbacks(mSeekEventRunnable);
        callbacks.postDelayed(mSeekEventRunnable, 300);
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

    private void setTitle(final String text) {
        mTopTitle.post(new Runnable() {
            @Override
            public void run() {
                if (currentIndex > 0) {
                    mTopTitle.setText(text + " 第" + (currentIndex + 1) + "集");
                } else {
                    mTopTitle.setText(text);
                }

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
        if (mTopAnimators != null) {
            mTopAnimators.cancel();
            mTopAnimators.removeAllListeners();
            mTopAnimators.removeAllUpdateListeners();
        }
    }

    private void cancelLockAnimation() {
        if (mLockAnimators != null) {
            mLockAnimators.cancel();
            mLockAnimators.removeAllListeners();
            mLockAnimators.removeAllUpdateListeners();
        }
    }

    private void setTopContainerState(final boolean state) {
        if (mControllerTopEnable) {
            mTopContainer.clearAnimation();
            cancelTopAnimation();
            mTopAnimators = ObjectAnimator.ofFloat(mTopContainer,
                    "alpha", state ? 0 : 1, state ? 1 : 0).setDuration(300);
            mTopAnimators.addListener(new AnimatorListenerAdapter() {
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
            mTopAnimators.start();
        } else {
            mTopContainer.setVisibility(View.GONE);
        }
    }

    private void setLockIconState(final boolean state) {
        mLock.clearAnimation();
        cancelLockAnimation();
        mLockAnimators = ObjectAnimator.ofFloat(mLock,
                "alpha", state ? 0 : 1, state ? 1 : 0).setDuration(300);
        mLockAnimators.addListener(new AnimatorListenerAdapter() {
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
        mLockAnimators.start();
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
        callbacks.sendEmptyMessageDelayed(MSG_CODE_DELAY_HIDDEN_CONTROLLER, 5000);
    }

    private void removeDelayHiddenMessage() {
        callbacks.removeMessages(MSG_CODE_DELAY_HIDDEN_CONTROLLER);
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
                //获取全局播放信息
                DataSource data = (DataSource) bundle.getSerializable(EventKey.SERIALIZABLE_DATA);
                getGroupValue().putObject(KEY_DATA_SOURCE, data);
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

        int EVENT_CODE_UPDATE_SEEK = -201;
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
        return View.inflate(context, R.layout.layout_small_controller_cover, null);
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
        if (i == R.id.cover_player_controller_image_view_play_state) {
            boolean selected = mStateIcon.isSelected();
            if (selected) {
                requestResume(null);
            } else {
                requestPause(null);
            }
            mStateIcon.setSelected(!selected);

        } else if (i == R.id.full_screen) {
            //倍速
            fullScreen();
        } else if (i == R.id.cover_player_play_next) {
            //播放下一个
            // playNextFilm();
        } else if (i == R.id.video_lock) {
            //锁定屏幕触摸
            lockScreen();
        } else if (i == R.id.send_danmu) {
            if (AppUtils.checkLogin(getContext())) {
                shoDanmuWindow();
            } else {
                Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.close_play) {
            notifyReceiverEvent(DataInter.Event.EVENT_CODE_CLOSE_SMALL_WINDOW, null);
        }
    }

    /**
     * 弹幕输入框
     */
    private void shoDanmuWindow() {
        notifyReceiverEvent(DataInter.Event.EVENT_CODE_SHOW_DANMU_INPUT, null);
    }

    /**
     * 全屏
     */
    private void fullScreen() {
        notifyReceiverEvent(DataInter.Event.FULLSCREEN, null);
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
                            SmallControllerCover.CurrentIndex = finalI + 1;
                            getGroupValue().putInt(DataInter.Key.KEY_CURRENTPLAY_INDEX, CurrentIndex);

                        }
                    });
                    break;
                }
            }
        }

    }

    private void showDlanWindow() {
        notifyReceiverEvent(DataInter.Event.EVENT_CODE_TO_DLAN_CAST, null);
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
