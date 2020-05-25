package com.movtalent.app.model;


import java.io.Serializable;

/**
 * @author huangyong
 * createTime 2019/6/22
 */
public class VideoVo implements Serializable {

    private String playUrl;

    private String title;

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
