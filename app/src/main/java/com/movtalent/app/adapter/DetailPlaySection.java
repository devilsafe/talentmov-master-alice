package com.movtalent.app.adapter;

import com.movtalent.app.adapter.event.OnSeriClickListener;
import com.movtalent.app.model.vo.CommonVideoVo;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class DetailPlaySection {

    private CommonVideoVo commonVideoVo;

    private int groupPlay;

    public int getGroupPlay() {
        return groupPlay;
    }

    public void setGroupPlay(int groupPlay) {
        this.groupPlay = groupPlay;
    }

    public CommonVideoVo getCommonVideoVo() {
        return commonVideoVo;
    }

    public OnSeriClickListener getClickListener() {
        return clickListener;
    }

    private OnSeriClickListener clickListener;

    public DetailPlaySection(int groupPlay, CommonVideoVo commonVideoVo, OnSeriClickListener clickListener) {
        this.commonVideoVo = commonVideoVo;
        this.groupPlay = groupPlay;
        this.clickListener = clickListener;
    }
}