package com.media.playerlib.model;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019/6/23
 */
public class VideoPlayVo {
    public int getVodId() {
        return vodId;
    }

    public void setVodId(int vodId) {
        this.vodId = vodId;
    }

    private int vodId;

    private String playUrl;

    private int index;

    private ArrayList<String> seriUrls;

    private String title;

    private long position;

    private long duration;

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<String> getSeriUrls() {
        return seriUrls;
    }

    public void setSeriUrls(ArrayList<String> seriUrls) {
        this.seriUrls = seriUrls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
