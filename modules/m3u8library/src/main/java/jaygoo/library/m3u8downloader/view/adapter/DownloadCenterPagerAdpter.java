package jaygoo.library.m3u8downloader.view.adapter;

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
public class DownloadCenterPagerAdpter extends FragmentPagerAdapter {


    private final Context context;
    private final List<Fragment> listfragment;
    private final String[] tile = {"正在下载","已完成"};

    public DownloadCenterPagerAdpter(FragmentManager fm, List<Fragment> listfragment, Activity activity) {
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
