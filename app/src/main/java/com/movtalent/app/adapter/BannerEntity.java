package com.movtalent.app.adapter;

import com.movtalent.app.model.vo.CommonVideoVo;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class BannerEntity {

    private ArrayList<CommonVideoVo> videoVos;

    public ArrayList<CommonVideoVo> getVideoVos() {
        return videoVos;
    }

    public void setVideoVos(ArrayList<CommonVideoVo> videoVos) {
        this.videoVos = videoVos;
    }
}