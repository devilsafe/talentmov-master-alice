package com.movtalent.app.model.vo;

import android.util.SparseArray;
import com.movtalent.app.model.VideoVo;
import com.movtalent.app.model.dto.VideoListDto;
import com.movtalent.app.util.PlayUrlUtils;

import java.util.ArrayList;


/**
 * @author huangyong
 * createTime 2019-08-21
 * 通用的在线资源数据模型封装
 * 为了解耦服务器数据与UI展示
 * 不至于接口变了，UI改起来费劲
 */
public class CommonVideoVo {

    private String movTypeName;
    private String movName;
    private String movDesc;
    private String movPoster;
    private String movUpdateTime;
    private String movId;
    private int movTypeId;
    private String movDirector;
    private String movRemark;
    private String movActor;
    private String movArea;
    private String movYear;

    public String getVodPlayFrom() {
        return vodPlayFrom;
    }

    public void setVodPlayFrom(String vodPlayFrom) {
        this.vodPlayFrom = vodPlayFrom;
    }

    private String vodPlayFrom;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    //一般页面用不到，可以不赋值，只有历史记录显示需要
    private int playPosition;

    public int getPlayPosition() {
        return playPosition;
    }

    public void setPlayPosition(int playPosition) {
        this.playPosition = playPosition;
    }

    public String getMovScore() {
        return movScore;
    }

    public void setMovScore(String movScore) {
        this.movScore = movScore;
    }

    private String movScore;


    public int getOriginTag() {
        return originTag;
    }

    public void setOriginTag(int originTag) {
        this.originTag = originTag;
    }

    private int originTag;

    public String getMovYear() {
        return movYear;
    }

    public void setMovYear(String movYear) {
        this.movYear = movYear;
    }

    private SparseArray<ArrayList<VideoVo>> movPlayUrlList;


    public SparseArray<ArrayList<VideoVo>> getMovPlayUrlList() {
        return movPlayUrlList;
    }

    public void setMovPlayUrlList(SparseArray<ArrayList<VideoVo>> movPlayUrlList) {
        this.movPlayUrlList = movPlayUrlList;
    }

    public String getMovTypeName() {
        return movTypeName;
    }

    public void setMovTypeName(String movTypeName) {
        this.movTypeName = movTypeName;
    }

    public String getMovName() {
        return movName;
    }

    public void setMovName(String movName) {
        this.movName = movName;
    }


    public String getMovDesc() {
        return movDesc;
    }

    public void setMovDesc(String movDesc) {
        this.movDesc = movDesc;
    }

    public String getMovPoster() {
        return movPoster;
    }

    public void setMovPoster(String movPoster) {
        this.movPoster = movPoster;
    }

    public String getMovUpdateTime() {
        return movUpdateTime;
    }

    public void setMovUpdateTime(String movUpdateTime) {
        this.movUpdateTime = movUpdateTime;
    }

    public String getMovId() {
        return movId;
    }

    public void setMovId(String movId) {
        this.movId = movId;
    }

    public int getMovTypeId() {
        return movTypeId;
    }

    public void setMovTypeId(int movTypeId) {
        this.movTypeId = movTypeId;
    }

    public String getMovDirector() {
        return movDirector;
    }

    public void setMovDirector(String movDirector) {
        this.movDirector = movDirector;
    }

    public String getMovRemark() {
        return movRemark;
    }

    public void setMovRemark(String movRemark) {
        this.movRemark = movRemark;
    }

    public String getMovActor() {
        return movActor;
    }

    public void setMovActor(String movActor) {
        this.movActor = movActor;
    }

    public String getMovArea() {
        return movArea;
    }

    public void setMovArea(String movArea) {
        this.movArea = movArea;
    }


    public static ArrayList<CommonVideoVo> from(VideoListDto data) {
        ArrayList<CommonVideoVo> commonVideoVos = new ArrayList<>();
        for (VideoListDto.DataBean video : data.getData()) {
            CommonVideoVo videoVo = new CommonVideoVo();
            videoVo.setMovName(video.getVod_name());
            videoVo.setMovActor(video.getVod_actor());
            videoVo.setMovArea(video.getVod_area());
            videoVo.setMovDesc(video.getVod_content());
            videoVo.setMovId(video.getVod_id());
            videoVo.setMovTypeId(Integer.parseInt(video.getType_id()));
            videoVo.setMovPoster(video.getVod_pic());
            videoVo.setMovPlayUrlList(PlayUrlUtils.convertGroupPlayList(video.getVod_play_url()));
            videoVo.setMovRemark(video.getVod_remarks());
            videoVo.setMovYear(video.getVod_year());
            videoVo.setVodPlayFrom(video.getVod_play_from());
            videoVo.setMovUpdateTime(video.getVod_time());
            videoVo.setMovScore(video.getVod_score());
            commonVideoVos.add(videoVo);
        }
        return commonVideoVos;
    }

}
