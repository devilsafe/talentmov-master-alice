package com.movtalent.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lib.common.util.DataInter;
import com.media.playerlib.manager.RxCountDown;
import com.media.playerlib.model.AdConfigDto;
import com.media.playerlib.widget.GlobalDATA;
import com.movtalent.app.http.UrlConfig;
import com.movtalent.app.model.vo.VideoTypeVo;
import com.movtalent.app.presenter.SplashPresenter;
import com.movtalent.app.presenter.iview.ITypeView;


/**
 * @author huangyong
 */
public class SplashActivity extends AppCompatActivity implements ITypeView {

    @BindView(R.id.splash_ad)
    ImageView splashAd;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.icon_img)
    ImageView iconImg;
    @BindView(R.id.icon_title)
    TextView iconTitle;
    @BindView(R.id.icon_about)
    LinearLayout iconAbout;
    @BindView(R.id.time_cut)
    TextView timeCut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SplashPresenter splashPresenter = new SplashPresenter(this);
        splashPresenter.getTypeList();
        splashPresenter.getad();

    }

    @Override
    public void loadDone(VideoTypeVo typeListDto) {

        UrlConfig.TYPE_DATA = typeListDto;

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(DataInter.KEY.TYPE_ID, typeListDto);
        splashAd.postDelayed(() -> {

        }, 2000);

        RxCountDown.countdown(3)
                .doOnSubscribe(disposable -> {
                })
                .subscribe(integer -> {
                    timeCut.setText(integer + "秒");
                }, throwable -> {

                }, () -> {
                    startActivity(intent);
                    finish();
                });


    }

    @Override
    public void loadAdDone() {
        if (!TextUtils.isEmpty(GlobalDATA.AD_INFO)) {
            AdConfigDto.DataBean dataBean = new Gson().fromJson(GlobalDATA.AD_INFO, AdConfigDto.DataBean.class);
            if (dataBean != null && dataBean.getAd_splash() != null && !TextUtils.isEmpty(dataBean.getAd_splash().getImg())) {
                Glide.with(this).load(dataBean.getAd_splash().getImg()).into(splashAd);
                if (TextUtils.isEmpty(dataBean.getAd_splash().getLink())) {
                    return;
                }
                splashAd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(dataBean.getAd_splash().getLink());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }

        }
    }

    @Override
    public void loadError() {
//        ToastUtil.showMessage("数据加载失败，请退出重试");
    }

    @Override
    public void loadEmpty() {

    }
}
