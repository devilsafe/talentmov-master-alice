package com.movtalent.app.adapter.event;


import com.movtalent.app.model.vo.CommonVideoVo;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public interface OnSeriClickListener {
    void switchPlay(String url,int index,int groupIndex);
    void showAllSeri(CommonVideoVo commonVideoVo);
}
