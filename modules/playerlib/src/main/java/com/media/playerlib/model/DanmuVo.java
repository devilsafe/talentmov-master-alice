package com.media.playerlib.model;

import java.io.Serializable;

/**
 * @author huangyong
 * createTime 2019/6/29
 */
public class DanmuVo implements Serializable {

    private String content;
    private String timeMillis;
    private String danmuColor;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(String timeMillis) {
        this.timeMillis = timeMillis;
    }

    public String getDanmuColor() {
        return danmuColor;
    }

    public void setDanmuColor(String danmuColor) {
        this.danmuColor = danmuColor;
    }
}
