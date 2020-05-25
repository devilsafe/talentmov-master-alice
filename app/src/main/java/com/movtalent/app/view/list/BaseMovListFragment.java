package com.movtalent.app.view.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.movtalent.app.R;
import com.movtalent.app.adapter.OnlineCategoryAdapter;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.iview.IListView;
import me.bakumon.statuslayoutmanager.library.DefaultOnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import java.util.ArrayList;


/**
 * @author huangyong
 * createTime 2019/6/18
 */
public abstract class BaseMovListFragment extends Fragment implements IListView {

    @BindView(R.id.mov_rv)
    XRecyclerView movRv;
    Unbinder unbinder;
    protected int index;
    protected OnlineCategoryAdapter movieListAdapter;
    private ArrayList<CommonVideoVo> data;

    private StatusLayoutManager statusLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_list_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        statusLayoutManager = new StatusLayoutManager.Builder(movRv)
                .setDefaultLayoutsBackgroundColor(Color.WHITE)
                .setDefaultEmptyClickViewTextColor(getResources().getColor(R.color.colorPrimary))
                .setLoadingLayout(R.layout.loading_layout)
                .setEmptyLayout(R.layout.empty_layout)
                .setDefaultEmptyText("当前分类可能没有内容")
                .setOnStatusChildClickListener(new DefaultOnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                        loadingListener.onRefresh();
                    }
                })
                .build();
        statusLayoutManager.showLoadingLayout();
        return view;
    }


    protected void initView() {

        if (getArguments() == null) {
            return;
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        movRv.setLayoutManager(gridLayoutManager);
        data = new ArrayList<>();
        movieListAdapter = new OnlineCategoryAdapter(getContext(), data);
        movRv.setAdapter(movieListAdapter);


        movRv.getDefaultFootView().setLoadingHint("正在加载请稍后");
        movRv.getDefaultFootView().setNoMoreHint("已经到底了");
        movRv.setLimitNumberToCallLoadMore(2);
        movRv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        movRv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        movRv.setPullRefreshEnabled(true);
        movRv.setLoadingListener(loadingListener);

        index = 1;
        initMovData(getArguments());
    }

    protected abstract void initMovData(Bundle arguments);


    XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            index = 1;
            pullRefresh();
        }

        @Override
        public void onLoadMore() {
            ++index;
            pullLoadMore();
        }
    };

    protected abstract void pullLoadMore();

    protected abstract void pullRefresh();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void loadDone(ArrayList<CommonVideoVo> videoVos) {
        if (movRv == null) {
            return;
        }
        if (data != null) {
            data.clear();
            data.addAll(videoVos);
            movieListAdapter.notifyDataSetChanged();

            movRv.postDelayed(() -> {
                if (movRv != null) {
                    movRv.refreshComplete();
                }
            }, 1000);

        } else {
            movRv.setNoMore(true);
        }
        statusLayoutManager.showSuccessLayout();
    }

    @Override
    public void loadEmpty() {
        statusLayoutManager.showEmptyLayout();
        movRv.setNoMore(true);
    }

    @Override
    public void loadError() {
        if (movRv == null) {
            return;
        }
        movRv.setNoMore(true);
    }

    @Override
    public void loadMore(ArrayList<CommonVideoVo> videoVos) {
        if (movRv != null && data != null && videoVos.size() > 0) {
            data.addAll(videoVos);
            movieListAdapter.notifyDataSetChanged();
            movRv.postDelayed(() -> {
                if (movRv != null) {
                    movRv.loadMoreComplete();
                }
            }, 1000);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
