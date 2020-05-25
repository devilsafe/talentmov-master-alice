package com.movtalent.app.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.lib.common.util.data.SearchHistoryInfo;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.movtalent.app.R;
import com.movtalent.app.db.DbHelper;
import com.movtalent.app.model.dto.SearchWdDto;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.SearchPresenter;
import com.movtalent.app.presenter.iview.ISearch;
import com.yang.flowlayoutlibrary.FlowLayout;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class SearchPrepareFragment extends Fragment implements ISearch {

    @BindView(R.id.tv_history_item)
    FlowLayout tvHistory;
    @BindView(R.id.tv_recword_item)
    FlowLayout tvRecWord;
    Unbinder unbinder;
    @BindView(R.id.flow_wd_title)
    TextView flowWdTitle;
    @BindView(R.id.flow_rec_title)
    TextView flowRecTitle;
    @BindView(R.id.reload)
    TextView reload;
    @BindView(R.id.clear_his)
    TextView clearHis;
    private SearchPresenter searchPresenter;

    private OnSearchListener onSearchListener;

    public static SearchPrepareFragment getInstance() {
        return new SearchPrepareFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_history_fraglayout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        searchPresenter = new SearchPresenter(this);
        searchPresenter.getSearchWdRec();

        initHistory();

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload.post(new Runnable() {
                    @Override
                    public void run() {
                        searchPresenter.getSearchWdRec();
                    }
                });

            }
        });
        clearHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(getContext()).asConfirm("提示", "确定清空历史搜索记录？", new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        DbHelper.clearKeywords();
                        initHistory();
                    }
                }).show();
            }
        });
    }

    private void initHistory() {
        ArrayList<SearchHistoryInfo> history = DbHelper.getAllHistory();
        //推荐搜索词
        ArrayList<String> words = new ArrayList<>();
        for (SearchHistoryInfo dataBean : history) {
            words.add(dataBean.searchKeyWords);
        }
        if (tvHistory != null) {
            tvHistory.post(new Runnable() {
                @Override
                public void run() {
                    tvHistory.setViews(words, new FlowLayout.OnItemClickListener() {
                        @Override
                        public void onItemClick(String s) {
                            if (onSearchListener != null) {
                                onSearchListener.doSearch(s);
                            }
                        }
                    });
                }
            });
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loadDone(ArrayList<CommonVideoVo> videoVos) {
    }

    @Override
    public void loadWdDone(SearchWdDto wordDto) {
        //推荐搜索词
        ArrayList<String> words = new ArrayList<>();
        for (SearchWdDto.DataBean dataBean : wordDto.getData()) {
            words.add(dataBean.getVod_name());
        }
        tvRecWord.post(new Runnable() {
            @Override
            public void run() {
                tvRecWord.setViews(words, new FlowLayout.OnItemClickListener() {
                    @Override
                    public void onItemClick(String s) {
                        if (onSearchListener != null) {
                            onSearchListener.doSearch(s);
                        }
                    }
                });
            }
        });

    }

    @Override
    public void loadError() {
    }

    @Override
    public void loadEmpty() {

    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public void reloadKeyWords() {
        initHistory();
    }
}
