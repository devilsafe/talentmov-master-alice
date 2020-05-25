package com.movtalent.app.adapter;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class SubjectEntity {

    private String subJectName;

    private String subJectNameSub;

    private String posterUrl;

    private int topId;

    public SubjectEntity(String subJectName, String posterUrl, int topId, String subJectNameSub) {
        this.subJectName = subJectName;
        this.posterUrl = posterUrl;
        this.topId = topId;
        this.subJectNameSub = subJectNameSub;
    }

    public String getSubJectName() {
        return subJectName;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public int getTopId() {
        return topId;
    }

    public String getSubJectNameSub() {
        return subJectNameSub;
    }

}