package com.movtalent.app.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.antiless.support.widget.TabLayout;
import com.lib.common.util.DataInter;
import com.lib.common.util.tool.StringUtil;
import com.movtalent.app.R;
import com.movtalent.app.adapter.ShopCenterPagerAdpter;
import com.movtalent.app.model.dto.PayLogDto;
import com.movtalent.app.model.vo.VipVo;
import com.movtalent.app.presenter.CoinPresenter;
import com.movtalent.app.presenter.VipPresenter;
import com.movtalent.app.presenter.iview.ICoin;
import com.movtalent.app.presenter.iview.IVipView;
import com.movtalent.app.util.UserUtil;
import com.movtalent.app.view.list.ShopHistoryFragment;
import com.movtalent.app.view.list.ShopListFragment;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-24
 */
public class CoinShopActivity extends AppCompatActivity implements ICoin, IVipView {


    ArrayList<Object> items;
    @BindView(R.id.shop_title)
    TextView shopTitle;
    @BindView(R.id.coin_num)
    TextView coinNum;
    @BindView(R.id.tab_vp)
    ViewPager tabVp;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.backup)
    ImageView backup;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_view)
    FrameLayout rightView;
    @BindView(R.id.toolbar_layout)
    Toolbar toolbarLayout;
    @BindView(R.id.arc_view)
    ArcShapeView arcView;
    @BindView(R.id.shop_title2)
    TextView shopTitle2;
    @BindView(R.id.coin_num2)
    TextView coinNum2;
    @BindView(R.id.heaer_section2)
    RelativeLayout heaerSection2;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DataInter.KEY.ACTION_REFRESH_COIN)){
                initData();
            }

        }
    };
    private ShopListFragment shopListFragment;
    private ShopHistoryFragment historyFragment;

    public static void start(Context context) {
        Intent intent = new Intent(context, CoinShopActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_shop_layout);
        ButterKnife.bind(this);
        items = new ArrayList<>();
        centerTv.setText("兑换中心");
        ArrayList<Fragment> pageFrag = new ArrayList<>();

        shopListFragment = ShopListFragment.getInstance();
        historyFragment = ShopHistoryFragment.getInstance();
        pageFrag.add(shopListFragment);
        pageFrag.add(historyFragment);
        ShopCenterPagerAdpter pagerAdpter = new ShopCenterPagerAdpter(getSupportFragmentManager(), pageFrag, this);
        tabVp.setAdapter(pagerAdpter);
        tablayout.setupWithViewPager(tabVp);
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registReceiver();
        initData();
    }
    private void registReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataInter.KEY.ACTION_REFRESH_COIN);
        registerReceiver(receiver, intentFilter);
    }
    private void initData() {
        CoinPresenter presenter = new CoinPresenter(this);
        presenter.getCoin(UserUtil.getUserToken(this));

        VipPresenter vipPresenter = new VipPresenter(this);
        vipPresenter.getPayHistory();
    }

    @Override
    public void updateCoin(String coin) {
        coinNum.setText(coin);
    }

    @Override
    public void loadError() {

    }

    @Override
    public void loadEmpty() {

    }

    @Override
    public void payDone(VipVo from) {

    }

    @Override
    public void loadLogDone(PayLogDto data) {
        if (data != null && data.getData().getVip_data() != null) {
            PayLogDto.DataBean.VipDataBean vip_data = data.getData().getVip_data();
            String userEndTime = vip_data.getUser_end_time();
            String userPoints = vip_data.getUser_points();

            coinNum.post(new Runnable() {
                @Override
                public void run() {
                    coinNum.setText(userPoints);
                    coinNum2.setText(StringUtil.getYmdhmsTimeStringv(Long.parseLong(userEndTime + "000")));
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
