package com.movtalent.app.presenter.iview;

import com.movtalent.app.model.dto.HomeLevelDto;
import com.movtalent.app.model.vo.CommonVideoVo;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public interface IHomeView extends IBase{
    void loadDone(HomeLevelDto videoVos);
    void loadDone(ArrayList<CommonVideoVo> videoVos);
    void loadMore(ArrayList<CommonVideoVo> videoVos);

    void loadBanner(ArrayList<CommonVideoVo> from);
}
