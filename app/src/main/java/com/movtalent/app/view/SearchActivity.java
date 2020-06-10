package com.movtalent.app.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.lib.common.util.SoftKeyboardUtils;
import com.movtalent.app.R;
import com.movtalent.app.adapter.OnlineSearchAdapter;
import com.movtalent.app.db.DbHelper;
import com.movtalent.app.model.dto.SearchWdDto;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.SearchPresenter;
import com.movtalent.app.presenter.iview.ISearch;
import com.movtalent.app.util.ToastUtil;
import com.movtalent.app.view.list.CommonListFragment;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class SearchActivity extends AppCompatActivity implements ISearch {

    @BindView(R.id.backup)
    ImageView backup;
    @BindView(R.id.to_search)
    EditText toSearch;
    @BindView(R.id.do_search)
    TextView doSearch;
    @BindView(R.id.home_head)
    RelativeLayout homeHead;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.clear)
    ImageView clear;
    private SearchPrepareFragment prepareFragment;
    private SearchPresenter searchPresenter;
    private CommonListFragment listFragment;
    private String keyword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        ButterKnife.bind(this);
        SoftKeyboardUtils.postShowSoftInput(toSearch);


        prepareFragment = SearchPrepareFragment.getInstance();
        listFragment = CommonListFragment.newInstance();
        prepareFragment.setOnSearchListener(onSearchListener);


        showPrepareView();
        searchPresenter = new SearchPresenter(this);
        doSearch.setOnClickListener(v -> SearchAndShowResultFragment());


        toSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    keyword = s.toString();
                    showPrepareView();
                }
            }
        });
        toSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                SearchAndShowResultFragment();
                return true;
            }
            return false;
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearch.setText("");
                showPrepareView();
            }
        });

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private final OnSearchListener onSearchListener = new OnSearchListener() {
        @Override
        public void doSearch(String keyWord) {
            if (TextUtils.isEmpty(keyWord)) {
                return;
            }
            toSearch.setText(keyWord);
            toSearch.setSelection(toSearch.getText().length());
            if (keyWord.length() > 2) {
                searchPresenter.doSearch(keyWord.substring(0, 2));
            } else {
                searchPresenter.doSearch(keyWord);
            }
            if (TextUtils.isEmpty(keyWord)) {
                ToastUtil.showMessage("搜索词不能为空");
                return;
            }
            //存入最近搜索
            if (!DbHelper.checkKeyWords(keyWord)) {
                DbHelper.addKeywords(keyWord);
                if (prepareFragment!=null){
                    prepareFragment.reloadKeyWords();
                }
            }

        }
    };

    private void SearchAndShowResultFragment() {
        String keyWord = toSearch.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtil.showMessage("搜索词不能为空");
            return;
        }
        //存入最近搜索
        if (!DbHelper.checkKeyWords(keyWord)) {
            DbHelper.addKeywords(keyWord);
            if (prepareFragment!=null){
                prepareFragment.reloadKeyWords();
            }
        }
        searchPresenter.doSearch(keyWord);
    }


    /**
     * 先显示推荐搜索词，只有输入搜索词时才显示历史搜索词
     */
    private void showPrepareView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, prepareFragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void loadDone(ArrayList<CommonVideoVo> videoVos) {


        if (listFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, listFragment);
            transaction.commitAllowingStateLoss();
            doSearch.post(() -> {
                OnlineSearchAdapter movieListAdapter = new OnlineSearchAdapter(SearchActivity.this, videoVos);
                listFragment.refreshData(movieListAdapter);
            });

        }
    }

    @Override
    public void loadWdDone(SearchWdDto wordDto) {

    }

    @Override
    public void loadError() {
        Toast.makeText(this, "未找到相关内容", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadEmpty() {
        Toast.makeText(this, "未找到相关内容", Toast.LENGTH_SHORT).show();
    }
}
