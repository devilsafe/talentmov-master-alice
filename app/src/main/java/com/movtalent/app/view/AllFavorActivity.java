package com.movtalent.app.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.movtalent.app.R;
import com.movtalent.app.adapter.OnlineSearchAdapter;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.FavorPresenter;
import com.movtalent.app.presenter.iview.IFavor;
import com.movtalent.app.util.UserUtil;
import com.movtalent.app.view.list.CommonListFragment;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-14
 * 收藏页
 */
public class AllFavorActivity extends AppCompatActivity implements IFavor {


    @BindView(R.id.backup)
    ImageView backup;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_view)
    FrameLayout rightView;
    @BindView(R.id.mov_content)
    FrameLayout movCentent;
    private CommonListFragment commonListFragment;

    public static void startTo(Context context) {
        Intent intent = new Intent(context, AllFavorActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_level_layout);
        ButterKnife.bind(this);


        FavorPresenter favorPresenter = new FavorPresenter(this);
        favorPresenter.getAllFavor(UserUtil.getUserId());
        backup.setOnClickListener(v -> finish());

        centerTv.setText("我的收藏");

        commonListFragment = CommonListFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mov_content, commonListFragment);
        fragmentTransaction.commitAllowingStateLoss();


    }

    @Override
    public void favorDone() {

    }

    @Override
    public void loadDone(ArrayList<CommonVideoVo> videoListDto) {
        backup.post(new Runnable() {
            @Override
            public void run() {
                OnlineSearchAdapter movieListAdapter = new OnlineSearchAdapter(AllFavorActivity.this, videoListDto);
                commonListFragment.refreshData(movieListAdapter);
                movieListAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void cancelDone() {

    }

    @Override
    public void loadHaveDone(boolean haveFavor) {

    }

    @Override
    public void loadError() {

    }

    @Override
    public void loadEmpty() {

    }
}
