package com.movtalent.app.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.movtalent.app.R;

/**
 * @author huangyong
 * createTime 2019-10-07
 */
public class CastDescriptionnActivity extends BaseActivity {
    @BindView(R.id.backup)
    ImageView backup;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_view)
    FrameLayout rightView;
    @BindView(R.id.toolbar_layout)
    Toolbar toolbarLayout;
    @BindView(R.id.cast_img)
    ImageView castImg;
    @BindView(R.id.root)
    RelativeLayout root;

    public static void start(Context context) {
        Intent intent = new Intent(context, CastDescriptionnActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        centerTv.setText("投屏说明");

    }

    @Override
    protected void initView() {
        backup.setOnClickListener(v -> finish());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cast_layout;
    }

}
