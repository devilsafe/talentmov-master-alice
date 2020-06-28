package com.media.playerlib.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import com.lib.common.util.KeyboardVisibilityListener;
import com.lib.common.util.PUtil;
import com.lib.common.util.SoftKeyboardUtils;
import com.lib.common.util.tool.KeyboardVisibilityHelper;


/**
 * @author huangyong
 * createTime 2019/2/23
 * description: 通用的底部弹起的输入框
 */
public abstract class BaseBottomInputSheet extends Dialog{



    private EditText editView;
    private String mEditContent;

    private final IBottomInput iPublish;
    private long clickTime;


    public BaseBottomInputSheet(Context context, IBottomInput iPublish) {
        super(context, com.lib.common.R.style.Dialog_Fullscreen);
        this.iPublish = iPublish;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
    }

    protected abstract int getLayoutId();

    private void initView() {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度，影响文本输入框的扩展高度
        lp.height = PUtil.dip2px( getContext(),120);
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        getWindow().setAttributes(lp);
        getWindow().setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(true);
        setCancelable(false);


        mEditContent = "";
        editView = findViewById(getEditViewId());
        View publishBtn = findViewById(getSendViewId());

        editView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEditContent = s.toString();
            }
        });


        clickTime = 0;
        publishBtn.setOnClickListener(v -> {
            //发布操作由业务类实现
            if (System.currentTimeMillis() - clickTime < 2000) {
                return;
            }
            if (iPublish != null) {
                iPublish.onContentPublish(mEditContent);
                clickTime = System.currentTimeMillis();
                dismiss();
            }
        });

    }


    protected abstract int getSendViewId();

    protected abstract int getEditViewId();


    /**
     * 获取发布结果
     */
    public interface OnPublishResult {
        /**
         * 点击发布后执行完成
         */
        void onResult();
    }

    /**
     * 发布完城，关闭输入框
     */
    private final OnPublishResult onPublishResult = new OnPublishResult() {
        @Override
        public void onResult() {
            BaseBottomInputSheet.this.dismiss();
        }
    };


    @Override
    public void show() {
        super.show();
        if (!TextUtils.isEmpty(mEditContent)) {
            mEditContent = "";
        }
        if (editView != null) {
            editView.setText("");
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!TextUtils.isEmpty(mEditContent)) {
                new AlertDialog.Builder(getContext()).setMessage(getContext().getString(com.lib.common.R.string.confirm_exit_edit)).setPositiveButton(com.lib.common.R.string.confirm, (dialog, which) -> {
                    mEditContent = "";
                    dialog.dismiss();
                    dismiss();
                }).setNegativeButton(com.lib.common.R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                    showInputMethod();
                }).setCancelable(true).show();
            } else {
                dismiss();
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    public void showInputMethod() {
        SoftKeyboardUtils.postShowSoftInput(editView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float rawY = event.getRawY();
        int[] location = new int[2];
        editView.getLocationInWindow(location);

        if (event.getAction() == MotionEvent.ACTION_OUTSIDE || (rawY > location[1])) {
            //如果编辑框文本不为空，只收起键盘，否则关闭输入Dialog
            if (!TextUtils.isEmpty(mEditContent)) {
                SoftKeyboardUtils.postHideSoftInput(editView);
            } else {
                mEditContent = "";
                editView.setText("");
                editView.clearFocus();
                SoftKeyboardUtils.postHideSoftInput(editView);
            }
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    /**
     * 如果输入框文本为空，关闭输入框
     */
    private void dismissInputBoard() {
        dismiss();
    }

    public void setKeyBoradListener(Activity activity) {
        KeyboardVisibilityHelper.listenKeyboardVisibility(activity, new KeyboardVisibilityListener() {
            @Override
            public void onKeyboardShown(int currentKeyboardHeight) {
            }

            @Override
            public void onKeyboardHidden() {
                dismissInputBoard();
                Log.e("getkeyboard","dismiss");
            }
        });
    }

}
