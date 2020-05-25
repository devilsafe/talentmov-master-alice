package com.movtalent.app.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.lib.common.util.AppDbManager;
import com.lib.common.util.PUtil;
import com.lib.common.util.data.HistoryInfo;
import com.lib.common.util.room.HistoryDao;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.media.playerlib.model.DataInter;
import com.movtalent.app.App;
import com.movtalent.app.R;
import com.movtalent.app.adapter.OnlineHistoryAdapter;
import com.movtalent.app.db.DbHelper;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.view.list.CommonListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-14
 * 历史页
 */
public class AllHistoryActivity extends AppCompatActivity {


    @BindView(R.id.backup)
    ImageView backup;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_view)
    FrameLayout rightView;
    @BindView(R.id.mov_content)
    FrameLayout movCentent;

    public static void startTo(Context context) {
        Intent intent = new Intent(context, AllHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_level_layout);
        ButterKnife.bind(this);

        int levelId = getIntent().getIntExtra(DataInter.Key.LEVEL_ID, 0);

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView manage = new ImageView(this);
        manage.setImageResource(R.drawable.ic_manage);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(PUtil.dip2px(this, 24), PUtil.dip2px(this, 24));
        params.gravity = Gravity.CENTER;
        manage.setLayoutParams(params);
        rightView.addView(manage);
        centerTv.setText("观看历史");
        Gson gson = new Gson();

        initHistory(gson);

        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(AllHistoryActivity.this).asConfirm("提示", "确定清空所有历史记录？", new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        DbHelper.clearHistory();
                        initHistory(gson);
                    }
                }).show();
            }
        });
    }

    private void initHistory(Gson gson) {
        HistoryDao historyDao = AppDbManager.getInstance(App.getContext()).historyDao();
        List<HistoryInfo> all = historyDao.getAll();

        ArrayList<CommonVideoVo> videoVos = new ArrayList<>();
        if (all != null && all.size() > 0) {
            for (HistoryInfo info : all) {
                String vodJson = info.getVodJson();
                CommonVideoVo videoVo = gson.fromJson(vodJson, CommonVideoVo.class);
                videoVo.setPlayPosition(info.getProgress());
                videoVo.setIndex(info.getIndex());
                videoVos.add(videoVo);
            }
        }

        CommonListFragment listFragment = CommonListFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mov_content, listFragment);
        fragmentTransaction.commitAllowingStateLoss();
        if (videoVos.size() > 0) {
            centerTv.post(new Runnable() {
                @Override
                public void run() {
                    listFragment.refreshData(new OnlineHistoryAdapter(AllHistoryActivity.this, videoVos));
                }
            });
        }
    }
}
