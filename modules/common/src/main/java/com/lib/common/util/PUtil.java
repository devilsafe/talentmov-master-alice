package com.lib.common.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * creator huangyong
 * createTime 2019/3/12 上午10:44
 * path com.huangyong.playerlib.util
 * description:
 */
public class PUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenW(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenH(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    private int getScreenRealHeight(Activity activity) {
        int heightPixels;
        WindowManager w = activity.getWindowManager();
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        heightPixels = metrics.heightPixels;
        // includes window decorations (statusbar bar/navigation bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                heightPixels = (Integer) Display.class
                        .getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
            // includes window decorations (statusbar bar/navigation bar)
        else if (Build.VERSION.SDK_INT >= 17)
            try {
                android.graphics.Point realSize = new android.graphics.Point();
                Display.class.getMethod("getRealSize",
                        android.graphics.Point.class).invoke(d, realSize);
                heightPixels = realSize.y;
            } catch (Exception ignored) {
            }
        return heightPixels;
    }

    public static boolean isTopActivity(Activity activity){
        return activity!=null && isTopActivity(activity, activity.getClass().getName());
    }

    public static boolean isTopActivity(Context context, String activityName){
        return isForeground(context, activityName);
    }

    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String stringForTime(int timeMs) {

        StringBuilder  mFormatBuilder = new StringBuilder();
        Formatter  mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
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

}
