package com.media.playerlib.widget;

import android.content.Context;
import com.media.playerlib.R;


/**
 * @author huangyong
 * createTime 2019/6/30
 */
public class DanmuInputDialog extends BaseBottomInputSheet {


    public DanmuInputDialog(Context context, IBottomInput iPublish) {
        super(context, iPublish);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.danmu_input_layout;
    }

    @Override
    protected int getSendViewId() {
        return R.id.send;
    }

    @Override
    protected int getEditViewId() {
        return R.id.danmu_edt;
    }
}
