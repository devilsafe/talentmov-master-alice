package com.media.playerlib.cover;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.kk.taurus.playerbase.event.BundlePool;
import com.kk.taurus.playerbase.event.EventKey;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.receiver.BaseCover;
import com.kk.taurus.playerbase.receiver.IReceiverGroup;
import com.kk.taurus.playerbase.receiver.PlayerStateGetter;
import com.kk.taurus.playerbase.touch.OnTouchGestureListener;
import com.kk.taurus.playerbase.utils.TimeUtil;
import com.media.playerlib.R;
import com.media.playerlib.model.DataInter;


public class GestureCover extends BaseCover implements OnTouchGestureListener {

    View mVolumeBox;
    View mBrightnessBox;
    ImageView mVolumeIcon;
    TextView mVolumeText;
    TextView mBrightnessText;
    View mFastForwardBox;
    TextView mFastForwardStepTime;
    TextView mFastForwardProgressTime;

    private boolean firstTouch;

    private int mSeekProgress = -1;

    private int mWidth, mHeight;
    private long newPosition;

    private boolean mHorizontalSlide;
    private float brightness = -1;
    private int volume;
    private AudioManager audioManager;
    private int mMaxVolume;

    private boolean mGestureEnable = true;

    private Bundle mBundle;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }
        }
    };
    private boolean horizontalSlide;
    private boolean rightVerticalSlide;
    private float mBrightness;
    private ImageView forwardIcon;

    public GestureCover(Context context) {
        super(context);
    }

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();

        View view = getView();
        mFastForwardProgressTime = view.findViewById(R.id.cover_player_gesture_operation_fast_forward_text_view_progress_time);
        mFastForwardStepTime = view.findViewById(R.id.cover_player_gesture_operation_fast_forward_text_view_step_time);
        mFastForwardBox = view.findViewById(R.id.cover_player_gesture_operation_fast_forward_box);
        mBrightnessText = view.findViewById(R.id.cover_player_gesture_operation_brightness_text);
        mVolumeText = view.findViewById(R.id.cover_player_gesture_operation_volume_text);
        mVolumeIcon = view.findViewById(R.id.cover_player_gesture_operation_volume_icon);
        forwardIcon = view.findViewById(R.id.forward_icon);
        mBrightnessBox = view.findViewById(R.id.cover_player_gesture_operation_brightness_box);
        mVolumeBox = view.findViewById(R.id.cover_player_gesture_operation_volume_box);
        mBundle = new Bundle();
        initAudioManager(getContext());

    }

    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();
    }

    private void initAudioManager(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    private void sendSeekEvent(int progress) {
        getGroupValue().putBoolean(DataInter.Key.KEY_TIMER_UPDATE_ENABLE, false);
        mSeekProgress = progress;
        mHandler.removeCallbacks(mSeekEventRunnable);
        mHandler.postDelayed(mSeekEventRunnable, 300);
    }

    private Runnable mSeekEventRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSeekProgress < 0)
                return;
            Bundle bundle = BundlePool.obtain();
            bundle.putInt(EventKey.INT_DATA, mSeekProgress);
            requestSeek(bundle);
        }
    };

    private IReceiverGroup.OnGroupValueUpdateListener mOnGroupValueUpdateListener =
            new IReceiverGroup.OnGroupValueUpdateListener() {
                @Override
                public String[] filterKeys() {
                    return new String[]{
                            DataInter.Key.KEY_GESTURE_ENABLE,
                            DataInter.Key.KEY_COMPLETE_SHOW};
                }

                @Override
                public void onValueUpdate(String key, Object value) {
                    if (key.equals(DataInter.Key.KEY_COMPLETE_SHOW)) {
                        setGestureEnable(!(boolean) value);
                    } else if (key.equals(DataInter.Key.KEY_GESTURE_ENABLE)) {
                        setGestureEnable((Boolean) value);
                    }
                }
            };

    @Override
    protected void onCoverAttachedToWindow() {
        super.onCoverAttachedToWindow();
        getGroupValue().registerOnGroupValueUpdateListener(mOnGroupValueUpdateListener);
        getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = getView().getWidth();
                mHeight = getView().getHeight();
                getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    protected void onCoverDetachedToWindow() {
        super.onCoverDetachedToWindow();
        getGroupValue().unregisterOnGroupValueUpdateListener(mOnGroupValueUpdateListener);
    }

    public void setVolumeBoxState(boolean state) {
        if (mVolumeBox != null) {
            mVolumeBox.setVisibility(state ? View.VISIBLE : View.GONE);
        }
    }

    public void setVolumeIcon(int resId) {
        if (mVolumeIcon != null) {
            mVolumeIcon.setImageResource(resId);
        }
    }

    public void setVolumeText(String text) {
        if (mVolumeText != null) {
            mVolumeText.setText(text);
        }
    }

    public void setBrightnessBoxState(boolean state) {
        if (mBrightnessBox != null) {
            mBrightnessBox.setVisibility(state ? View.VISIBLE : View.GONE);
        }
    }

    public void setBrightnessText(String text) {
        if (mBrightnessText != null) {
            mBrightnessText.setText(text);

        }
    }

    private void setFastForwardState(boolean state) {
        mFastForwardBox.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    private void setFastForwardStepTime(String text) {
        mFastForwardStepTime.setText(text);
    }

    private void setFastForwardProgressTime(String text) {
        mFastForwardProgressTime.setText(text);
    }

    public void setGestureEnable(boolean gestureEnable) {
        this.mGestureEnable = gestureEnable;
    }

    @Override
    public View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_gesture_cover, null);
    }

    @Override
    public int getCoverLevel() {
        return levelLow(0);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
                setGestureEnable(true);
                break;
        }
    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onSingleTapUp(MotionEvent event) {

    }

    @Override
    public void onDoubleTap(MotionEvent event) {
    }

    @Override
    public void onDown(MotionEvent event) {
        mHorizontalSlide = false;
        firstTouch = true;
        volume = getVolume();
        if (getActivity() != null)
            mBrightness = getActivity().getWindow().getAttributes().screenBrightness;
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!mGestureEnable)
            return;
        float mOldX = e1.getX(), mOldY = e1.getY();
        float deltaY = mOldY - e2.getY();
        float deltaX = mOldX - e2.getX();
        if (firstTouch) {
            horizontalSlide = Math.abs(distanceX) >= Math.abs(distanceY);
            rightVerticalSlide = mOldX > mWidth * 0.5f;
            firstTouch = false;
        }

        if (horizontalSlide) {
            onHorizontalSlide(-deltaX / (mWidth * 10));
        } else {
            if (Math.abs(deltaY) > mHeight)
                return;
            if (rightVerticalSlide) {
                slideToChangeVolume(deltaY);
            } else {
                slideToChangeBrightness(deltaY);
            }
        }
    }

    private int getDuration() {
        PlayerStateGetter playerStateGetter = getPlayerStateGetter();
        return playerStateGetter == null ? 0 : playerStateGetter.getDuration();
    }

    private int getCurrentPosition() {
        PlayerStateGetter playerStateGetter = getPlayerStateGetter();
        return playerStateGetter == null ? 0 : playerStateGetter.getCurrentPosition();
    }

    private void onHorizontalSlide(float percent) {
        if (getDuration() <= 0)
            return;
        mHorizontalSlide = true;
        if (getGroupValue().getBoolean(DataInter.Key.KEY_TIMER_UPDATE_ENABLE)) {
            getGroupValue().putBoolean(DataInter.Key.KEY_TIMER_UPDATE_ENABLE, false);
        }
        long position = getCurrentPosition();
        long duration = getDuration();
        long deltaMax = Math.min(getDuration() / 2, duration - position);
        long delta = (long) (deltaMax * percent);
        newPosition = delta + position;
        if (newPosition > duration) {
            newPosition = duration;

        } else if (newPosition <= 0) {
            newPosition = 0;
            delta = -position;
        }

        if (delta < 0) {
            setProgressIcon(R.drawable.ic_round_fast_rewind);
        } else {
            setProgressIcon(R.drawable.ic_round_fast_forward);
        }

        int showDelta = (int) delta / 1000;
        if (showDelta != 0) {
            mBundle.putInt(EventKey.INT_ARG1, (int) newPosition);
            mBundle.putInt(EventKey.INT_ARG2, (int) duration);
            notifyReceiverPrivateEvent(
                    DataInter.ReceiverKey.KEY_CONTROLLER_COVER,
                    DataInter.PrivateEvent.EVENT_CODE_UPDATE_SEEK,
                    mBundle);
            setFastForwardState(true);
            String text = showDelta > 0 ? ("+" + showDelta) : "" + showDelta;
            setFastForwardStepTime(text + "s");
            String progressText = TimeUtil.getTimeSmartFormat(newPosition) + "/" + TimeUtil.getTimeSmartFormat(duration);
            setFastForwardProgressTime(progressText);
        }
    }

    /**
     * 设置快进快退icon
     *
     * @param iconId
     */
    private void setProgressIcon(int iconId) {
        if (forwardIcon != null) {
            forwardIcon.setImageResource(iconId);
        }
    }


    protected void slideToChangeVolume(float deltaY) {
        mHorizontalSlide = false;
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float deltaV = deltaY * 2 / mHeight * streamMaxVolume;
        float index = volume + deltaV;
        if (index > streamMaxVolume) index = streamMaxVolume;
        if (index < 0) {
            setVolumeIcon(R.drawable.ic_volume_off_white);
            index = 0;
        } else {
            setVolumeIcon(R.drawable.ic_volume_up_white);
        }
        int percent = (int) (index / streamMaxVolume * 100);
        setVolumeText(percent + "%");
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) index, 0);
        setBrightnessBoxState(false);
        setFastForwardState(false);
        setVolumeBoxState(true);
    }

    protected void slideToChangeBrightness(float deltaY) {
        Activity activity = getActivity();
        if (activity == null)
            return;
        setVolumeBoxState(false);
        setFastForwardState(false);
        setBrightnessBoxState(true);
        Window window = activity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (mBrightness == -1.0f) mBrightness = 0.5f;
        float brightness = deltaY * 2 / mHeight * 1.0f + mBrightness;
        if (brightness < 0) {
            brightness = 0f;
        }
        if (brightness > 1.0f) brightness = 1.0f;
        int percent = (int) (brightness * 100);
        setBrightnessText(percent + "%");
        attributes.screenBrightness = brightness;
        window.setAttributes(attributes);
    }


    private Activity getActivity() {
        Context context = getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    private int getVolume() {
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (volume < 0)
            volume = 0;
        return volume;
    }

    @Override
    public void onEndGesture() {
        volume = -1;
        brightness = -1f;
        setVolumeBoxState(false);
        setBrightnessBoxState(false);
        setFastForwardState(false);
        if (newPosition >= 0 && mHorizontalSlide) {
            sendSeekEvent((int) newPosition);
            newPosition = 0;
        } else {
            getGroupValue().putBoolean(DataInter.Key.KEY_TIMER_UPDATE_ENABLE, true);
        }
        mHorizontalSlide = false;
    }
}
