package com.movtalent.app.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.movtalent.app.R;
import jaygoo.library.m3u8downloader.view.DownloadPageFragment;

/**
 * @author huangyong
 * createTime 2019-09-19
 */
public class AllDownLoadActivity extends AppCompatActivity {


    @BindView(R.id.backup)
    ImageView backup;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_view)
    FrameLayout rightView;
    @BindView(R.id.toolbar_layout)
    Toolbar toolbarLayout;
    @BindView(R.id.frag_container)
    FrameLayout fragContainer;

    public static void startTo(Context context) {
        Intent intent = new Intent(context, AllDownLoadActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_download_layout);
        ButterKnife.bind(this);

        centerTv.setText("下载中心");
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container,new DownloadPageFragment());
        transaction.commitAllowingStateLoss();
    }
}
