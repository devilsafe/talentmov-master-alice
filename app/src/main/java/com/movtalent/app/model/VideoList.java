package com.movtalent.app.model;

import com.movtalent.app.model.dto.VideoListDto;

import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class VideoList {

    public List<VideoListDto.DataBean> getVideoVos() {
        return videoVos;
    }

    final List<VideoListDto.DataBean> videoVos;

    public VideoList(List<VideoListDto.DataBean> videoVos) {
        this.videoVos = videoVos;
    }
}
