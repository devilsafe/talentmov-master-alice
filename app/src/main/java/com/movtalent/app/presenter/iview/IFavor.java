package com.movtalent.app.presenter.iview;

import com.movtalent.app.model.vo.CommonVideoVo;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public interface IFavor extends IBase{
    void favorDone();
    void loadDone(ArrayList<CommonVideoVo> videoListDto);
    void cancelDone();
    void loadHaveDone(boolean haveFavor);
}
