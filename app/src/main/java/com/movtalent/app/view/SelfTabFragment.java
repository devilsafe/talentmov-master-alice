package com.movtalent.app.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.gson.Gson;
import com.movtalent.app.App_Config;
import com.movtalent.app.R;
import com.movtalent.app.adapter.user.SelfBodyView;
import com.movtalent.app.adapter.user.SelfBodyViewViewBinder;
import com.movtalent.app.adapter.user.SelfHeadView;
import com.movtalent.app.adapter.user.SelfHeadViewViewBinder;
import com.movtalent.app.model.dto.LoginDto;
import com.movtalent.app.presenter.CoinPresenter;
import com.movtalent.app.presenter.iview.ICoin;
import com.movtalent.app.util.UserUtil;
import me.drakeet.multitype.MultiTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-11
 */
public class SelfTabFragment extends Fragment {

    @BindView(R.id.self_rv)
    RecyclerView selfRv;
    Unbinder unbinder;
    @BindView(R.id.backup)
    ImageView backup;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_view)
    FrameLayout rightView;
    private MultiTypeAdapter multiTypeAdapter;
    @VisibleForTesting
    List<Object> items;
    private SelfHeadView headView;
    private String coin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_self_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        backup.setVisibility(View.GONE);
        multiTypeAdapter = new MultiTypeAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        selfRv.setLayoutManager(manager);
        selfRv.setAdapter(multiTypeAdapter);

        multiTypeAdapter.register(SelfHeadView.class, new SelfHeadViewViewBinder());
        multiTypeAdapter.register(SelfBodyView.class, new SelfBodyViewViewBinder());

        items = new ArrayList<>();


        coin = "20";

        headView = new SelfHeadView("", "test", coin, onloginListener);
        items.add(headView);
        items.add(new SelfBodyView());


        multiTypeAdapter.setItems(items);


        ImageView btImg = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.layout_img, rightView, false);
        btImg.setImageResource(R.drawable.ic_set);
        btImg.setOnClickListener(v -> SettingActivity.start(v.getContext()));
        rightView.addView(btImg);
    }

    private SelfHeadViewViewBinder.OnLoginStatusChanged changedListener;
    SelfHeadViewViewBinder.OnloginListener onloginListener = new SelfHeadViewViewBinder.OnloginListener() {
        @Override
        public void onLogin() {

        }

        @Override
        public void statuLogin(SelfHeadViewViewBinder.OnLoginStatusChanged isLogin) {
            changedListener = isLogin;
        }

        @Override
        public void onExit() {
        }

        @Override
        public void goToSharePage() {
            if (switchListener != null) {
                switchListener.switchShare();
            }
        }
    };

    private void getUserInfo() {

        String userInfo = UserUtil.getUserInfo(getContext());
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(userInfo)) {
            LoginDto.DataBean dataBean = gson.fromJson(userInfo, LoginDto.DataBean.class);
            if (dataBean != null) {
                if (changedListener != null) {
                    changedListener.onLine(dataBean);
                }
            }
        } else {
            if (changedListener != null) {
                changedListener.offLine();
            }
        }

    }

    ICoin iCoin = new ICoin() {
        @Override
        public void updateCoin(String coin) {
            if (changedListener != null) {
                changedListener.updateCoin(coin);
                SelfHeadView headView = (SelfHeadView) items.get(0);
                headView.userCoins = coin;
                multiTypeAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void loadError() {

        }

        @Override
        public void loadEmpty() {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (UserUtil.isLogin()) {
            new CoinPresenter(iCoin).getCoin(UserUtil.getUserToken(getContext()));
        }
        refreshIcon();
        refreshHeadData();
    }

    private void refreshHeadData() {
        if (UserUtil.isLogin()) {
            String userName = UserUtil.getUserName();
            Object sec = items.get(0);
            SelfHeadView headView = (SelfHeadView) sec;
            headView.userName = userName;
            String userCoin = UserUtil.getUserCoin();
            if (!TextUtils.isEmpty(userCoin)) {
                headView.userCoins = userCoin;
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private OnSwitchListener switchListener;

    public void setOnSwitchListener(OnSwitchListener onSwitchListener) {
        this.switchListener = onSwitchListener;
    }

    public void refreshData() {
        getUserInfo();
        if (UserUtil.isLogin()) {
            new CoinPresenter(iCoin).getCoin(UserUtil.getUserToken(getContext()));
        }
        refreshIcon();
    }

    public void refreshIcon() {
        Object o = items.get(0);
        if (o instanceof SelfHeadView) {
            SelfHeadView headView = (SelfHeadView) o;
            if (UserUtil.isLogin()) {
                String userInfo = UserUtil.getUserInfo(getContext());
                Gson gson = new Gson();
                if (!TextUtils.isEmpty(userInfo)) {
                    LoginDto.DataBean dataBean = gson.fromJson(userInfo, LoginDto.DataBean.class);
                    if (dataBean != null) {
                        if (TextUtils.isEmpty(dataBean.getUser_portrait_thumb())) {
                            headView.userIcon = App_Config.ICON_GROUP[0] + "";
                        } else {
                            headView.userIcon = App_Config.ICON_GROUP[Integer.parseInt(dataBean.getUser_portrait_thumb())] + "";
                        }
                    }
                }
                multiTypeAdapter.notifyDataSetChanged();
            } else {
                headView.userIcon = R.drawable.ic_user_avator + "";
            }
        }
    }
}
