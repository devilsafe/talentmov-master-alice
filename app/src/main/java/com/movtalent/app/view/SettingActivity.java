package com.movtalent.app.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.azhon.appupdate.utils.ScreenUtil;
import com.lib.common.util.DataInter;
import com.lib.common.util.SharePreferencesUtil;
import com.lib.common.util.utils.DataCleanManager;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.CenterListPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.media.playerlib.PlayApp;
import com.movtalent.app.R;
import com.movtalent.app.adapter.setting.TextItemSection;
import com.movtalent.app.adapter.setting.TextItemSectionViewBinder;
import com.movtalent.app.model.dto.UpdateDto;
import com.movtalent.app.presenter.UpdatePresenter;
import com.movtalent.app.presenter.iview.IUpdate;
import com.movtalent.app.util.UserUtil;
import me.drakeet.multitype.MultiTypeAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-18
 */
public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.setting)
    RecyclerView setting;
    @VisibleForTesting
    List<Object> items;
    @BindView(R.id.backup)
    ImageView backup;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_view)
    FrameLayout rightView;
    @BindView(R.id.toolbar_layout)
    Toolbar toolbarLayout;
    @BindView(R.id.exit)
    TextView exit;
    private DownloadManager manager;

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ButterKnife.bind(this);

        centerTv.setText("设置");

        MultiTypeAdapter adapter = new MultiTypeAdapter();

        adapter.register(TextItemSection.class, new TextItemSectionViewBinder());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        setting.setLayoutManager(linearLayoutManager);
        setting.setAdapter(adapter);

        items = new ArrayList<>();
        String userToken = UserUtil.getUserToken(this);
        if (TextUtils.isEmpty(userToken)) {
            exit.setVisibility(View.INVISIBLE);
        } else {
            exit.setVisibility(View.VISIBLE);
            items.add(new TextItemSection("账户设置", () -> {
                    UserProfileActivity.start(SettingActivity.this);
            }));
        }
        String[] arr = {"IjkPlayer解码","ExoPlayer解码","MediaPlayer解码"};
        items.add(new TextItemSection("播放器设置", () -> {
            CenterListPopupView listPopupView = new XPopup.Builder(this).asCenterList("切换解码器", arr, new OnSelectListener() {
                @Override
                public void onSelect(int position, String text) {
                    switch (position) {
                        case 0:
                            PlayApp.swich(PlayApp.PLAN_ID_IJK);
                            SharePreferencesUtil.setIntSharePreferences(SettingActivity.this,DataInter.KEY.PLAY_CODEC,0);
                            break;
                        case 1:
                            PlayApp.swich(PlayApp.PLAN_ID_EXO);
                            SharePreferencesUtil.setIntSharePreferences(SettingActivity.this,DataInter.KEY.PLAY_CODEC,1);
                            break;
                        case 2:
                            PlayApp.swich(PlayApp.PLAN_ID_MEDIA);
                            SharePreferencesUtil.setIntSharePreferences(SettingActivity.this,DataInter.KEY.PLAY_CODEC,2);
                            break;
                    }
                }
            });
            listPopupView.show();
            int preferences = SharePreferencesUtil.getIntSharePreferences(this, DataInter.KEY.PLAY_CODEC, 0);
            listPopupView.setCheckedPosition(preferences);
        }));
        items.add(new TextItemSection("清除缓存", () -> {
            new XPopup.Builder(this).asConfirm("提示！", "清空缓存后，图片缓存及登录状态将被清除，下载和浏览记录不会被清除。", new OnConfirmListener() {
                @Override
                public void onConfirm() {
                    DataCleanManager.cleanInternalCache(SettingActivity.this);
                    UserUtil.exitLogin(SettingActivity.this);
                    sendBroadcast(new Intent(DataInter.KEY.ACTION_REFRESH_COIN));
                }
            }).show();
        }));
        items.add(new TextItemSection("关于我们", () -> {
            AboutUsActivity.start(SettingActivity.this);
        }));
        items.add(new TextItemSection("检查更新", () -> {
//
            new UpdatePresenter(new IUpdate() {
                @Override
                public void loadDone(UpdateDto dto) {
                    exit.post(new Runnable() {
                        @Override
                        public void run() {
                            checkVersion(dto);

                        }
                    });
                }

                @Override
                public void loadEmpty() {

                }
            }).getUpdate();
        }));
        adapter.setItems(items);
        adapter.notifyDataSetChanged();


        exit.setOnClickListener(v -> new XPopup.Builder(SettingActivity.this).asConfirm("提示!", "确认退出登录？", new OnConfirmListener() {
            @Override
            public void onConfirm() {
                UserUtil.exitLogin(SettingActivity.this);
                sendBroadcast(new Intent(DataInter.KEY.ACTION_EXIT_LOGIN));
                finish();
            }
        }).show());
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkVersion(UpdateDto dto) {
        /*
         * 整个库允许配置的内容
         * 非必选
         */
        UpdateConfiguration configuration = new UpdateConfiguration()
                //输出错误日志
                .setEnableLog(true)
                //设置自定义的下载
                //.setHttpManager()
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //设置对话框背景图片 (图片规范参照demo中的示例图)
                //.setDialogImage(R.drawable.ic_dialog)
                //设置按钮的颜色
                //.setDialogButtonColor(Color.parseColor("#E743DA"))
                //设置按钮的文字颜色
                .setDialogButtonTextColor(Color.WHITE)
                //设置是否显示通知栏进度
                .setShowNotification(true)
                //设置是否提示后台下载toast
                .setShowBgdToast(false)
                //设置强制更新
                .setForcedUpgrade(false)
                //设置对话框按钮的点击监听
                .setButtonClickListener(new OnButtonClickListener() {
                    @Override
                    public void onButtonClick(int id) {

                    }
                })
                //设置下载过程的监听
                .setOnDownloadListener(new OnDownloadListener() {
                    @Override
                    public void start() {

                    }

                    @Override
                    public void downloading(int max, int progress) {

                    }

                    @Override
                    public void done(File apk) {

                    }

                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void error(Exception e) {

                    }
                });

        manager = DownloadManager.getInstance(this);

        manager.setApkName("极光影院.apk")
                .setApkUrl(dto.getData().getDownloadUrl())
                .setSmallIcon(R.mipmap.ticon2)
                .setShowNewerToast(true)
                .setConfiguration(configuration)
//                .setDownloadPath(Environment.getExternalStorageDirectory() + "/AppUpdate")
                .setApkVersionCode(dto.getData().getVersionCode())
                .setApkVersionName(dto.getData().getVersion())
                .setApkSize("20.4")
                .setAuthorities(getPackageName())
                .setApkDescription(dto.getData().getUpdateMsg())
                .download();
        Window dialogWindow = manager.getDefaultDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenUtil.getWith(this) * 0.8f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);

    }
}
