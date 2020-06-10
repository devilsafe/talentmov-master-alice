package com.movtalent.app.adapter;
/**
 * Created by HuangYong on 2018/9/19.
 */

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.movtalent.app.model.vo.VideoTypeVo;
import com.movtalent.app.view.list.MovListFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @created 2018/9/19
 * @changeRecord [修改记录] <br/>
 * 2018/9/19 ：created
 */
public class OnlineTabFragmentPagerAdapter extends FragmentPagerAdapter {

    private final FragmentManager fragmetnmanager;

    //创建一个List<Fragment>

    private final List<MovListFragment> listfragment;
    private final ArrayList<VideoTypeVo.ClassBean> typeIdArray;

    public OnlineTabFragmentPagerAdapter(FragmentManager fm, ArrayList<MovListFragment> list, ArrayList<VideoTypeVo.ClassBean> typeIdArray) {
        super(fm);
        this.fragmetnmanager = fm;
        this.listfragment = list;
        this.typeIdArray = typeIdArray;
    }

    @Override
    public Fragment getItem(int position) {
        return listfragment.get(position);
    }

    @Override
    public int getCount() {
        return listfragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return typeIdArray.get(position).getType_name();
    }
}
