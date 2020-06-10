package com.movtalent.app.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


/**
 * @author huangyong
 * createTime 2019/6/18
 */
public class ShopCenterPagerAdpter extends FragmentPagerAdapter {


    private Context context;
    private List<Fragment> listfragment;
    private String[] tile = {"兑换会员","兑换记录"};

    public ShopCenterPagerAdpter(FragmentManager fm, List<Fragment> listfragment, Activity activity) {
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
        return tile[position];
    }
}
