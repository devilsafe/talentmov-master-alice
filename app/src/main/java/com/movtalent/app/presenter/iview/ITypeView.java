package com.movtalent.app.presenter.iview;

import com.movtalent.app.model.vo.VideoTypeVo;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public interface ITypeView extends IBase{
    void loadDone(VideoTypeVo typeListDto);

    void loadAdDone();
}
