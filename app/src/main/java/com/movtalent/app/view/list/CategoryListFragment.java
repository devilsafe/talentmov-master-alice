package com.movtalent.app.view.list;

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
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.movtalent.app.R;
import com.movtalent.app.adapter.OnlineCategoryAdapter;
import com.movtalent.app.model.dto.HomeLevelDto;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.CategoryPresenter;
import com.movtalent.app.presenter.iview.IHomeView;
import com.movtalent.app.presenter.iview.IListView;

import java.util.ArrayList;


/**
 * @author huangyong
 * createTime 2019/6/18
 */
public class CategoryListFragment extends Fragment implements IListView, IHomeView {

    @BindView(R.id.mov_rv)
    XRecyclerView movRv;
    Unbinder unbinder;
    private int index;
    private OnlineCategoryAdapter movieListAdapter;
    private ArrayList<CommonVideoVo> data;

    private CategoryPresenter listPresenter;
    private LoadingPopupView loadingPopupView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_list_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {

        loadingPopupView = new XPopup.Builder(getContext()).asLoading("正在加载...");
        loadingPopupView.show();

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

        listPresenter = new CategoryPresenter(this);

    }


    XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            index = 1;
            listPresenter.getCategory(typeId, vodArea, vodYear, index, 18);
        }

        @Override
        public void onLoadMore() {
            listPresenter.getCategoryMore(typeId, vodArea, vodYear, ++index, 18);
        }
    };

    public static CategoryListFragment newInstance() {
        CategoryListFragment movListFragment = new CategoryListFragment();
        return movListFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void loadDone(HomeLevelDto videoVos) {

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
                    loadingPopupView.dismiss();
                }
            }, 1000);
        }
    }

    @Override
    public void loadEmpty() {
        loadingPopupView.dismiss();
    }

    @Override
    public void loadError() {
        movRv.setNoMore(true);
        loadingPopupView.dismiss();
    }

    @Override
    public void loadMore(ArrayList<CommonVideoVo> videoVos) {
        if (movRv != null && data != null && videoVos.size() > 0) {
            data.addAll(videoVos);
            movieListAdapter.notifyDataSetChanged();
            movRv.postDelayed(() -> {
                if (movRv != null) {
                    movRv.loadMoreComplete();
                    loadingPopupView.dismiss();
                }
            }, 1000);
        }

    }

    @Override
    public void loadBanner(ArrayList<CommonVideoVo> from) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private String vodArea;
    private int vodYear;
    private int typeId;

    public void refreshData(int typeId, String requestArea, int requestYear) {
        this.typeId = typeId;
        this.vodArea = requestArea;
        this.vodYear = requestYear;
        index = 1;
        if (listPresenter!=null){
            listPresenter.getCategory(typeId, requestArea, requestYear, index, 18);
            loadingPopupView.show();
        }

    }
}
