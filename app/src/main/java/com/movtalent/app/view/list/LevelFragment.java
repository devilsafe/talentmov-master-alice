package com.movtalent.app.view.list;

import android.os.Bundle;
import com.media.playerlib.model.DataInter;
import com.movtalent.app.model.dto.HomeLevelDto;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.HomePresenter;
import com.movtalent.app.presenter.iview.IHomeView;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class LevelFragment extends BaseMovListFragment implements IHomeView {


    private HomePresenter homePresenter;
    private int levelId;

    public static LevelFragment getInstance(int levelId){
        Bundle bundle = new Bundle();
        bundle.putInt(DataInter.Key.LEVEL_ID,levelId);
        LevelFragment levelFragment = new LevelFragment();
        levelFragment.setArguments(bundle);
        return levelFragment;
    }

    @Override
    protected void initMovData(Bundle arguments) {
        levelId = getArguments().getInt(DataInter.Key.LEVEL_ID);
        if (levelId == 0) {
            return;
        }
        homePresenter = new HomePresenter(this);
        homePresenter.getHomeLevel(levelId, index, 18);
    }

    @Override
    protected void pullLoadMore() {
        homePresenter.getHomeLevelMore(levelId, index, 18);
    }

    @Override
    protected void pullRefresh() {
        homePresenter.getHomeLevel(levelId, index, 18);
    }

    @Override
    public void loadDone(HomeLevelDto videoVos) {
    }

    @Override
    public void loadBanner(ArrayList<CommonVideoVo> from) {

    }

    @Override
    public void loadError() {
        super.loadError();
    }
}
