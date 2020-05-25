package com.movtalent.app.presenter.iview;


import com.movtalent.app.model.vo.CommonVideoVo;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-08-23
 */
public interface IListView extends IBase{

    void loadDone(ArrayList<CommonVideoVo> videoVos);


    void loadMore(ArrayList<CommonVideoVo> videoVos);
}
