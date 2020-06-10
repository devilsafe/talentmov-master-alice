package com.movtalent.app.category;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.movtalent.app.App_Config;
import com.movtalent.app.R;
import com.movtalent.app.category.adapter.CateTabAdapter;
import com.movtalent.app.category.adapter.CateTabAdapter2;
import com.movtalent.app.http.UrlConfig;
import com.movtalent.app.model.vo.VideoTypeVo;
import com.movtalent.app.view.list.CategoryListFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author huangyong
 */
public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.main_cat_tab)
    RecyclerView mainCatTab;
    @BindView(R.id.type_cat_tab)
    RecyclerView typeCatTab;
    @BindView(R.id.area_cat_tab)
    RecyclerView areaCatTab;
    @BindView(R.id.year_cat_tab)
    RecyclerView yearCatTab;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.cate_list)
    FrameLayout cateList;
    Unbinder unbinder;
    @BindView(R.id.request_tv)
    TextView requestTv;
    @BindView(R.id.back_icon)
    ImageView backIcon;
    private CateTabAdapter2 movTabAdapter;
    private CateTabAdapter areaTabAdapter;
    private CateTabAdapter yearTabAdapter;
    private int requestTypeId;
    private String requestArea;
    private int requestYear;
    private CategoryListFragment cateListFragment;
    private HashMap<String, String> dataPair;

    private String typeContent;

    public static void start(FragmentActivity activity) {
        Intent intent = new Intent(activity, CategoryActivity.class);
        activity.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_category_layout);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);

        ButterKnife.bind(this);

        initData();
    }

    private void initData() {

        dataPair = new HashMap<>(18);


        requestTypeId = 0;
        requestArea = "";
        requestYear = 0;

        String[] mainTab = {
                "全部形式", "电影", "电视剧", "动漫", "综艺"};


        //地区都一样
        String[] areaTabMov = App_Config.AREA_CONFIG;

        String[] yearTab = {
                "全部年代",
                "2019",
                "2018",
                "2017",
                "2016",
                "2016以前",
        };
        /**
         * 类型是根据主tab动态改变的
         */
        ArrayList<VideoTypeVo.ClassBean> movie = UrlConfig.movie;
        VideoTypeVo.ClassBean bean = new VideoTypeVo.ClassBean();
        bean.setType_id(0);
        bean.setType_name("全部类别");
        movie.add(0, bean);

        ArrayList<VideoTypeVo.ClassBean> seri = UrlConfig.seri;
        VideoTypeVo.ClassBean seriBean = new VideoTypeVo.ClassBean();
        seriBean.setType_id(0);
        seriBean.setType_name("全部类别");
        seri.add(0, seriBean);

        movTabAdapter = new CateTabAdapter2(movie, (position, content) -> {
            requestTypeId = position == 0 ? 0 : content.getType_id();
            typeContent = content.getType_name();
            refreshData();
        });

        areaTabAdapter = new CateTabAdapter(areaTabMov, (position, content) -> {
            requestArea = position == 0 ? "" : content;
            refreshData();
        });

        yearTabAdapter = new CateTabAdapter(yearTab, (position, content) -> {
            if (position == 0) {
                requestYear = 0;
                refreshData();
                return;
            }
            if (position == 5) {
                requestYear = 2010;
            }
            requestYear = Integer.parseInt(content);
            refreshData();
        });
        typeCatTab.setVisibility(View.GONE);
        CateTabAdapter mainTabAdapter = new CateTabAdapter(mainTab, (position, content) -> {

            switch (position) {
                case 0:
                    //全部分类，显示类别列表
                    typeCatTab.setVisibility(View.GONE);
                    areaCatTab.setVisibility(View.VISIBLE);
                    typeContent="全部分类";
                    break;
                case 1:
                    //电视剧显示类别列表
                    typeCatTab.setVisibility(View.VISIBLE);
                    areaCatTab.setVisibility(View.VISIBLE);
                    CategoryActivity.this.movTabAdapter.setData(movie);
                    requestTypeId = 7;
                    typeContent="电视剧";
                    break;
                case 2:
                    //电影，显示类别列表
                    typeCatTab.setVisibility(View.VISIBLE);
                    areaCatTab.setVisibility(View.VISIBLE);
                    CategoryActivity.this.movTabAdapter.setData(seri);
                    typeContent="电影";
                    requestTypeId = 13;
                    break;
                case 3:
                    //动漫，隐藏类别列表
                    typeCatTab.setVisibility(View.GONE);
                    areaCatTab.setVisibility(View.VISIBLE);
                    requestTypeId = 4;
                    typeContent="动漫";
                    break;
                case 4:
                    //综艺，隐藏类别列表，更换地区数据
                    typeCatTab.setVisibility(View.GONE);
                    areaCatTab.setVisibility(View.VISIBLE);
                    requestTypeId = 3;
                    typeContent="综艺";
                    break;
                default:
                    //全部分类，显示类别列表
                    typeCatTab.setVisibility(View.GONE);
                    areaCatTab.setVisibility(View.VISIBLE);
                    break;
            }
            refreshData();
        });


        LinearLayoutManager mainManager = new LinearLayoutManager(this);
        LinearLayoutManager typeManager = new LinearLayoutManager(this);
        LinearLayoutManager areaManager = new LinearLayoutManager(this);
        LinearLayoutManager yearManager = new LinearLayoutManager(this);
        mainManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        typeManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        areaManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        yearManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mainCatTab.setLayoutManager(mainManager);
        typeCatTab.setLayoutManager(typeManager);
        areaCatTab.setLayoutManager(areaManager);
        yearCatTab.setLayoutManager(yearManager);
        mainCatTab.setAdapter(mainTabAdapter);
        typeCatTab.setAdapter(this.movTabAdapter);
        areaCatTab.setAdapter(areaTabAdapter);
        yearCatTab.setAdapter(yearTabAdapter);


        cateListFragment = CategoryListFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.cate_list, cateListFragment);
        transaction.commitAllowingStateLoss();

        mainCatTab.post(() -> refreshData());

        appBar.addOnOffsetChangedListener(new MyOffsetChangedListener());
        backIcon.setOnClickListener(v -> finish());

    }

    private class MyOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            float progress = Math.abs(verticalOffset) * 1.0f / appBarLayout.getTotalScrollRange();
            if (progress >= 0.8) {
                // toobar.setVisibility(View.VISIBLE);
                // toobar.setAlpha(progress);
                requestTv.setVisibility(View.VISIBLE);
            } else {
                // toobar.setVisibility(View.VISIBLE);
                // toobar.setAlpha(0.4f);
                requestTv.setVisibility(View.INVISIBLE);
            }

        }
    }

    private void refreshData() {
        String type = requestTypeId == 0 ? "全部分类" : typeContent;
        String area = TextUtils.isEmpty(requestArea) ? "全部地区" : requestArea;
        String year = requestYear == 0 ? "全部年代" : (requestYear + "");
        requestTv.setText(type + "/" + area + "/" + year);

        requestTv.post(() -> {

            if (cateListFragment != null) {
                cateListFragment.refreshData(requestTypeId, requestArea, requestYear);
            }
        });

    }


}
