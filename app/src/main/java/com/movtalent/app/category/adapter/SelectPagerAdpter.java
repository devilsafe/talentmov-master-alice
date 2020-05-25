package com.movtalent.app.category.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/**
 * @author huangyong
 * createTime 2019/6/18
 */
public class SelectPagerAdpter extends FragmentPagerAdapter {


    private List<Fragment> listfragment;
    private List<String> titles;

    public SelectPagerAdpter(FragmentManager fm, List<Fragment> listfragment, List<String> titles) {
        super(fm);
        this.listfragment = listfragment;
        this.titles = titles;
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
        return titles == null ? "" : titles.get(position);
    }
}
