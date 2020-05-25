package com.lib.common.util.tool;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import com.lib.common.util.KeyboardVisibilityListener;

/**
 * Created by michael on 2017/3/14.
 */

public class KeyboardVisibilityHelper {
    private static final String TAG = "KeyboardVisibility";
    // 键盘最小高度
    private static final int MIN_KEYBOARD_HEIGHT_PX = 150;

    public static void listenKeyboardVisibility(Activity activity, final KeyboardVisibilityListener listener) {
        if (activity == null || listener == null) {
            return;
        }
        final View decorView = activity.getWindow().getDecorView();
        if (decorView == null) {
            return;
        }

        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private final Rect windowVisibleDisplayFrame = new Rect();
            private int lastVisibleDecorViewHeight;

            @Override
            public void onGlobalLayout() {
                //Log.d(TAG, "onGlobalLayout() called");
                // 测量 window 的可见高度
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
                final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();
                //Log.d(TAG, "lastVisibleDecorViewHeight = " + lastVisibleDecorViewHeight);
                //Log.d(TAG, "visibleDecorViewHeight = " + visibleDecorViewHeight);

                // 根据键盘最小高度判断键盘是否可见
                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                        // 计算当前键盘有多高
                        //android9需要特殊处理
                        int currentKeyboardHeight = decorView.getHeight() - (windowVisibleDisplayFrame.bottom -
                                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ? windowVisibleDisplayFrame.top : 0));
                        //Log.d(TAG, "decorView.getHeight() - windowVisibleDisplayFrame.bottom = : " + decorView.getHeight() + " - " + windowVisibleDisplayFrame.bottom + " = " + currentKeyboardHeight);
                        listener.onKeyboardShown(currentKeyboardHeight);
                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        listener.onKeyboardHidden();
                    }
                }
                lastVisibleDecorViewHeight = visibleDecorViewHeight;
            }
        });
    }
}
