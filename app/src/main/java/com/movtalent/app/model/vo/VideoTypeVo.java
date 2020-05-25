package com.movtalent.app.model.vo;


import com.movtalent.app.model.dto.TypeListDto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-08-23
 */
public class VideoTypeVo implements Serializable {


    private ArrayList<ClassBean> classInfo;

    public ArrayList<ClassBean> getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(ArrayList<ClassBean> classInfo) {
        this.classInfo = classInfo;
    }

    public static class ClassBean implements Serializable{
        /**
         * type_id : 1
         * type_name : 电影
         */

        private int type_id;
        private String type_name;

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }

    public static VideoTypeVo from(TypeListDto data) {
        VideoTypeVo videoTypeVo = new VideoTypeVo();

        ArrayList<VideoTypeVo.ClassBean> dataBean = new ArrayList<>();

        for (TypeListDto.DataBean infos : data.getData()) {
            VideoTypeVo.ClassBean classBean = new VideoTypeVo.ClassBean();
            classBean.setType_id(Integer.parseInt(infos.getType_id()));
            classBean.setType_name(infos.getType_name());
            dataBean.add(classBean);
        }
        videoTypeVo.setClassInfo(dataBean);
        return videoTypeVo;
    }
}
