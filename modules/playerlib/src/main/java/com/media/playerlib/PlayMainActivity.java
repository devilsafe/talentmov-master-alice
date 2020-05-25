package com.media.playerlib;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.media.playerlib.manager.FullPlayerPresenter;
import com.media.playerlib.model.DataInter;
import com.media.playerlib.model.VideoPlayVo;

/**
 * @author huangyong
 */
public class PlayMainActivity extends AppCompatActivity {
    private FrameLayout container;
    private FrameLayout frameLayout;
    private FullPlayerPresenter playerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity_main);
        container = findViewById(R.id.player_container);
        frameLayout = (FrameLayout) ViewGroup.inflate(this, R.layout.item_detail_ad_entity, container);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        playerPresenter = new FullPlayerPresenter();
        frameLayout = (FrameLayout) ViewGroup.inflate(this,R.layout.item_detail_ad_entity,container);
        String playUrl = getIntent().getStringExtra(DataInter.Key.KEY_CURRENTPLAY_URL);
        String playTitle = getIntent().getStringExtra(DataInter.Key.KEY_CURRENTPLAY_TITLE);

        Log.e("getplayTitle",playUrl);

        VideoPlayVo videoPlayVo = new VideoPlayVo();
        videoPlayVo.setPlayUrl(playUrl);
        videoPlayVo.setIndex(0);
        videoPlayVo.setTitle(playTitle);
        playerPresenter.initView(this,container);
        playerPresenter.initData(videoPlayVo);
        playerPresenter.switchPlay(playUrl,0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerPresenter.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerPresenter.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerPresenter.resume();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (playerPresenter != null) {
            playerPresenter.onConfigurationChanged(newConfig);
        }
    }

}
