package com.lib.common.util;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘工具类
 *
 * @author zhaobo
 * @date 19/1/17
 */
public class SoftKeyboardUtils {
    private SoftKeyboardUtils() {
    }

    /**
     * 隐藏软键盘，在view加载完调用
     *
     * @param view 添加到窗口的view
     */
    public static void hideSoftInput(View view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            //第二个参数最好选0，跟show的参数有关系
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 通过post的形式隐藏键盘，可以再oncreate调用
     *
     * @param view view
     */
    public static void postHideSoftInput(View view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        final Context context = view.getContext();
        final IBinder binder = view.getWindowToken();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    //第二个参数最好选0，跟show的参数有关系
                    imm.hideSoftInputFromWindow(binder, 0);
                }
            }
        }, 50);

    }

    /**
     * 显示软键盘
     *
     * @param view 必须是EditText
     */
    public static void showSoftInput(EditText view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        if (view.getVisibility() != View.VISIBLE) {
            return;
        }
        if (!view.isFocused()) {
            view.requestFocus();
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * post显示软键盘
     *
     * @param view 必须是EditText
     */
    public static void postShowSoftInput(final EditText view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        if (view.getVisibility() != View.VISIBLE) {
            return;
        }
        if (!view.isFocused()) {
            view.requestFocus();
        }
        final Context context = view.getContext();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    //第二个参数最好选0，跟show的参数有关系
                    imm.showSoftInput(view, 0);
                }
            }
        }, 100);
    }

    /**
     * post显示软键盘
     *
     * @param view 必须是EditText
     */
    public static void postShowSoftInput(final EditText view, int postDelayMillSeconds) {
        if (view == null || view.getContext() == null) {
            return;
        }
        if (view.getVisibility() != View.VISIBLE) {
            return;
        }
        if (!view.isFocused()) {
            view.requestFocus();
        }
        final Context context = view.getContext();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    //第二个参数最好选0，跟show的参数有关系
                    imm.showSoftInput(view, 0);
                }
            }
        }, postDelayMillSeconds);
    }

    /**
     * 切换键盘显示装态
     *
     * @param view view，显示的时候不要求edittext
     */
    public static void toggleSoftInput(View view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInputFromWindow(view.getWindowToken(), 0, 0);
        }
    }

    public static void postToggleSoftInput(View view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        final Context context = view.getContext();
        final IBinder binder = view.getWindowToken();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    //第二个参数最好选0，跟show的参数有关系
                    imm.toggleSoftInputFromWindow(binder, 0, 0);
                }
            }
        }, 100);
    }

}
