package com.movtalent.app.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import com.lib.common.util.SoftKeyboardUtils;
import com.lxj.xpopup.core.BottomPopupView;
import com.movtalent.app.R;
import com.movtalent.app.model.vo.CommentVo;
import com.movtalent.app.util.ToastUtil;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class BottomInputSheet extends BottomPopupView {

    private EditText editText;
    private OnPublishListener onPublishListener;
    private CommentVo commentVo;

    public BottomInputSheet(@NonNull Context context, CommentVo commentVo, OnPublishListener onPublishListener) {
        super(context);
        this.onPublishListener = onPublishListener;
        this.commentVo = commentVo;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.bottom_comment_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView send = findViewById(R.id.send);
        editText = findViewById(R.id.eidt_comment_content);

        if (commentVo!=null){
            editText.setHint("回复："+commentVo.getUserName());
        }
        send.setOnClickListener(v -> {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                ToastUtil.showMessage("内容不能为空");
            } else {
                if (onPublishListener!=null){
                    onPublishListener.onSend(editText.getText().toString());
                    SoftKeyboardUtils.postHideSoftInput(editText);
                    dismiss();
                }
            }
        });
        SoftKeyboardUtils.postShowSoftInput(editText, 2);
    }

    @Override
    protected void onShow() {
        super.onShow();

    }

    public interface OnPublishListener{
        void onSend(String conntent);
    }
}
