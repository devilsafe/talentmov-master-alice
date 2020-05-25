package com.movtalent.app.view.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.movtalent.app.R;
import com.movtalent.app.adapter.shop.PayLogSection;
import com.movtalent.app.adapter.shop.PayLogSectionViewBinder;
import com.movtalent.app.model.dto.PayLogDto;
import com.movtalent.app.model.vo.VipVo;
import com.movtalent.app.presenter.VipPresenter;
import com.movtalent.app.presenter.iview.IVipView;
import me.drakeet.multitype.MultiTypeAdapter;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-24
 * 可兑换内容列表
 */
public class ShopHistoryFragment extends Fragment implements IVipView {

    @BindView(R.id.pay_list)
    RecyclerView payList;
    Unbinder unbinder;
    ArrayList<Object> items;
    private MultiTypeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_pay_log_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        VipPresenter presenter = new VipPresenter(this);
        presenter.getPayHistory();

        items = new ArrayList<>();
        adapter = new MultiTypeAdapter();
        adapter.register(PayLogSection.class, new PayLogSectionViewBinder());
        adapter.setItems(items);
        payList.setLayoutManager(new LinearLayoutManager(getContext()));
        payList.setAdapter(adapter);
    }


    public static ShopHistoryFragment getInstance() {
        ShopHistoryFragment shopHistoryFragment = new ShopHistoryFragment();
        return shopHistoryFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void payDone(VipVo from) {

    }

    @Override
    public void loadLogDone(PayLogDto data) {
        if (data.getData().getPay_log() != null && data.getData().getPay_log().size() > 0) {
            for (PayLogDto.DataBean.PayLogBean dataBean : data.getData().getPay_log()) {
                PayLogSection section = new PayLogSection(dataBean.getPlog_points(),dataBean.getPlog_time(),dataBean.getPlog_type());
                items.add(section);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadError() {

    }

    @Override
    public void loadEmpty() {

    }
}
