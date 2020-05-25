package com.movtalent.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.movtalent.app.http.UrlConfig;

import java.util.List;


/**
 * @author huangyong
 * createTime 2019/6/18
 */
public class HomePagerAdpter extends FragmentPagerAdapter {


    private Context context;
    private List<Fragment> listfragment;

    public HomePagerAdpter(FragmentManager fm, List<Fragment> listfragment, Activity activity) {
        super(fm);
        this.listfragment = listfragment;
        this.context = activity;
    }

    @Override
    public Fragment getItem(int i) {
        return listfragment.get(i);
    }


    @Override
    public int getCount() {
        return listfragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return UrlConfig.tabX[position];
    }
}
