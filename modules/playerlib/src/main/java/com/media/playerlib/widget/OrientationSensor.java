package com.media.playerlib.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * creator huangyong
 * createTime 2019/3/12 上午11:50
 * path com.huangyong.playerlib.util
 * description:
 */
public class OrientationSensor {
    private final int MSG_SENSOR = 888;

    private OnOrientationListener onOrientationListener;

    // 是否是竖屏
    private boolean isPortrait = true;

    private SensorManager sm;
    private OrientationSensorListener listener;
    private Sensor sensor;

    private SensorManager sm1;
    private Sensor sensor1;
    private OrientationSensorListener1 listener1;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SENSOR:
                    int orientation = msg.arg1;
                    if (orientation > 45 && orientation < 135) {
                        if (isPortrait) {
                            //切换成横屏反向：ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                            callbackOnLandScape(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                            isPortrait = false;
                        }
                    } else if (orientation > 135 && orientation < 225) {
                        if (!isPortrait) {
                            /*
                             * 切换成竖屏反向：ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT(9),
                             * ActivityInfo.SCREEN_ORIENTATION_SENSOR:根据重力感应自动旋转
                             * 此处正常应该是上面第一个属性，但是在真机测试时显示为竖屏正向，所以用第二个替代
                             */
                            callbackOnPortrait(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            isPortrait = true;
                        }
                    } else if (orientation > 225 && orientation < 315) {
                        if (isPortrait) {
                            //切换成横屏：ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            callbackOnLandScape(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            isPortrait = false;
                        }
                    } else if ((orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45)) {
                        if (!isPortrait) {
                            //切换成竖屏ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            callbackOnPortrait(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            isPortrait = true;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };


    public OrientationSensor(Activity activity, OnOrientationListener orientationListener){
        this.onOrientationListener = orientationListener;

        // 注册重力感应器,监听屏幕旋转
        sm = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new OrientationSensorListener(mHandler);

        // 根据 旋转之后/点击全屏之后 两者方向一致,激活sm.
        sm1 = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        sensor1 = sm1.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener1 = new OrientationSensorListener1();
    }

    private void callbackOnLandScape(int orientation){
        if(onOrientationListener!=null)
            onOrientationListener.onLandScape(orientation);
    }

    private void callbackOnPortrait(int orientation){
        if(onOrientationListener!=null)
            onOrientationListener.onPortrait(orientation);
    }

    /**
     * 手动横竖屏切换方向
     */
    public void toggleScreen() {
        sm.unregisterListener(listener);
        sm1.registerListener(listener1, sensor1,SensorManager.SENSOR_DELAY_UI);
        if (isPortrait) {
            isPortrait = false;
            // 切换成横屏
            callbackOnLandScape(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            isPortrait = true;
            // 切换成竖屏
            callbackOnPortrait(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void enable(){
        sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void disable(){
        sm.unregisterListener(listener);
        sm1.unregisterListener(listener1);
    }

    public boolean isPortrait(){
        return this.isPortrait;
    }

    /**
     * 重力感应监听者
     */
    public class OrientationSensorListener implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        public static final int ORIENTATION_UNKNOWN = -1;

        private Handler rotateHandler;

        public OrientationSensorListener(Handler handler) {
            rotateHandler = handler;
        }

        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }

        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y
            // value
            if (magnitude * 4 >= Z * Z) {
                // 屏幕旋转时
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - (int) Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
            if (rotateHandler != null) {
                rotateHandler.obtainMessage(MSG_SENSOR, orientation, 0).sendToTarget();
            }
        }
    }

    public class OrientationSensorListener1 implements SensorEventListener {
        private static final int _DATA_X = 0;
        private static final int _DATA_Y = 1;
        private static final int _DATA_Z = 2;

        public static final int ORIENTATION_UNKNOWN = -1;

        public OrientationSensorListener1() {
        }

        public void onAccuracyChanged(Sensor arg0, int arg1) {
        }

        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            int orientation = ORIENTATION_UNKNOWN;
            float X = -values[_DATA_X];
            float Y = -values[_DATA_Y];
            float Z = -values[_DATA_Z];
            float magnitude = X * X + Y * Y;
            // Don't trust the angle if the magnitude is small compared to the y
            // value
            if (magnitude * 4 >= Z * Z) {
                // 屏幕旋转时
                float OneEightyOverPi = 57.29577957855f;
                float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
                orientation = 90 - (int) Math.round(angle);
                // normalize to 0 - 359 range
                while (orientation >= 360) {
                    orientation -= 360;
                }
                while (orientation < 0) {
                    orientation += 360;
                }
            }
            if (orientation > 225 && orientation < 315) {// 检测到当前实际是横屏
                if (!isPortrait) {
                    sm.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_UI);
                    sm1.unregisterListener(listener1);
                }
            } else if ((orientation > 315 && orientation < 360) || (orientation > 0 && orientation < 45)) {// 检测到当前实际是竖屏
                if (isPortrait) {
                    sm.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_UI);
                    sm1.unregisterListener(listener1);
                }
            }
        }
    }

    public interface OnOrientationListener{
        void onLandScape(int orientation);
        void onPortrait(int orientation);
    }
}
