package com.movtalent.app;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.azhon.appupdate.utils.ScreenUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.lib.common.util.DataInter;
import com.lxj.xpopup.XPopup;
import com.movtalent.app.model.dto.PostDto;
import com.movtalent.app.model.dto.UpdateDto;
import com.movtalent.app.model.vo.VideoTypeVo;
import com.movtalent.app.presenter.PublishPresenter;
import com.movtalent.app.presenter.UpdatePresenter;
import com.movtalent.app.presenter.iview.IUpdate;
import com.movtalent.app.view.*;
import com.next.easynavigation.view.EasyNavigationBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.navigationBar)
    EasyNavigationBar navigationBar;
    @BindView(R.id.container)
    ConstraintLayout container;
    private String[] tabText = {"热点", "专题", "分享", "我的"};
    //未选中icon
    private int[] normalIcon = {R.drawable.ic_shouye_u, R.drawable.ic_topics_u, R.drawable.ic_shares_u, R.drawable.ic_selfs_u};
    //选中时icon
    private int[] selectIcon = {R.drawable.ic_shouye, R.drawable.ic_topics, R.drawable.ic_shares, R.drawable.ic_selfs};

    private List<Fragment> fragments = new ArrayList<>();

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (shareTabFragment != null && intent.getAction().equals(DataInter.KEY.ACTION_REFRESH_COIN)) {
                shareTabFragment.refreshData();
            }
            if (selfTabFragment != null && intent.getAction().equals(DataInter.KEY.ACTION_REFRESH_COIN)) {
                selfTabFragment.refreshData();
            }
            if (intent.getAction().equals(DataInter.KEY.ACTION_EXIT_LOGIN)) {
                if (selfTabFragment != null && shareTabFragment != null) {
                    shareTabFragment.refreshData();
                    selfTabFragment.refreshData();
                }
            }
            if (intent.getAction().equals(DataInter.KEY.ACTION_REFRESH_ICON)) {
                if (selfTabFragment != null) {
                    selfTabFragment.refreshIcon();
                }
            }
        }
    };
    private ShareTabFragment shareTabFragment; //分享
    private SelfTabFragment selfTabFragment;
    private PublishPresenter publishPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        VideoTypeVo typeVo = (VideoTypeVo) getIntent().getSerializableExtra(DataInter.KEY.TYPE_ID);
        shareTabFragment = new ShareTabFragment();
        selfTabFragment = new SelfTabFragment();
        fragments.add(HomeMainFragment.getInstance(typeVo));
        fragments.add(new TopicTabFragment());
        fragments.add(shareTabFragment);//
        fragments.add(selfTabFragment);

        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int position) {
                        if (position == 2) {
                            shareTabFragment.refreshData();
                        }
                        return false;
                    }
                })
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .build();
        shareTabFragment.setOnSwitchListener((new OnSwitchListener() {
            @Override
            public void switchToHome() {
                navigationBar.selectTab(0);
            }

            @Override
            public void switchShare() {
                navigationBar.selectTab(1);
            }
        }));
        selfTabFragment.setOnSwitchListener((new OnSwitchListener() {
            @Override
            public void switchToHome() {
                navigationBar.selectTab(0);
            }

            @Override
            public void switchShare() {
                navigationBar.selectTab(2);
            }
        }));
        registReceiver();

        new UpdatePresenter(new IUpdate() {
            @Override
            public void loadDone(UpdateDto dto) {
                checkVersion(dto);
            }

            @Override
            public void loadEmpty() {

            }
        }).getUpdate();

        requestAppPermissions();
        initPost();
    }

    private void initPost() {
        publishPresenter = new PublishPresenter(new PublishPresenter.IPost() {
            @Override
            public void loadPost(PostDto dto) {
                if (dto != null && dto.getData() != null && dto.getData().isShow()) {
                    new XPopup.Builder(HomeActivity.this).asCustom(new PostPop(HomeActivity.this, dto)).show();
                }
            }
        });
        publishPresenter.getPost();
    }

    private void registReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataInter.KEY.ACTION_REFRESH_COIN);
        intentFilter.addAction(DataInter.KEY.ACTION_EXIT_LOGIN);
        intentFilter.addAction(DataInter.KEY.ACTION_REFRESH_ICON);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private void requestAppPermissions() {
        Dexter.withActivity(this)
                .withPermissions(PERMISSIONS)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            // Toast.makeText(getApplicationContext(), "权限获取成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "权限获取失败", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    }
                })
                .check();
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

        DownloadManager manager = DownloadManager.getInstance(this);

        manager.setApkName("阿离影视.apk")
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

    /**
     * 再按一次退出
     */
    private long mExitTime = 0;

    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Snackbar snackbar = Snackbar.make(container, "再按一次退出噢~", Toast.LENGTH_SHORT);
            snackbar.show();
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }

    }
}
