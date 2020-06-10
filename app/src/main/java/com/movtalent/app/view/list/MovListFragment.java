package com.movtalent.app.view.list;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.media.playerlib.model.DataInter;
import com.movtalent.app.R;
import com.movtalent.app.adapter.OnlineCategoryAdapter;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.MovieListPresenter;
import com.movtalent.app.presenter.iview.IListView;
import me.bakumon.statuslayoutmanager.library.DefaultOnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import java.util.ArrayList;


/**
 * @author huangyong
 * createTime 2019/6/18
 */
public class MovListFragment extends Fragment implements IListView {

    @BindView(R.id.mov_rv)
    XRecyclerView movRv;
    Unbinder unbinder;
    private int index;
    private OnlineCategoryAdapter movieListAdapter;
    private ArrayList<CommonVideoVo> data;
    private int typeId;

    private StatusLayoutManager statusLayoutManager;
    private MovieListPresenter listPresenter;

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


    private void initView() {

        if (getArguments() == null) {
            return;
        }

        typeId = getArguments().getInt(DataInter.Key.TYPE_ID);

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


        listPresenter = new MovieListPresenter(this);
        initRefreshAndLoadMore(typeId);

    }

    private void initRefreshAndLoadMore(final int typeId) {
        index = 1;
        listPresenter.loadListByType(typeId, index, 18);
    }

    XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            index = 1;
            listPresenter.loadListByType(typeId, index, 18);
        }

        @Override
        public void onLoadMore() {
            listPresenter.loadListMoreByType(typeId, ++index, 18);
        }
    };

    public static MovListFragment newInstance(int typeId) {
        MovListFragment movListFragment = new MovListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DataInter.Key.TYPE_ID, typeId);
        movListFragment.setArguments(bundle);
        return movListFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void loadDone(ArrayList<CommonVideoVo> videoVos) {
        if (movRv != null && data != null) {
            data.clear();
            data.addAll(videoVos);
            movieListAdapter.notifyDataSetChanged();

            movRv.postDelayed(() -> {
                if (movRv != null) {
                    movRv.refreshComplete();
                }
            }, 1000);
        }
        statusLayoutManager.showSuccessLayout();
    }

    @Override
    public void loadEmpty() {
        statusLayoutManager.showEmptyLayout();
    }

    @Override
    public void loadError() {
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
