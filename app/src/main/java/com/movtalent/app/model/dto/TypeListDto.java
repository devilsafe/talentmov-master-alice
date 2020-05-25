package com.movtalent.app.model.dto;

import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-11
 */
public class TypeListDto {


    /**
     * Code : 200
     * Data : [{"type_name":"电影","type_id":"1","type_sort":"1","type_pid":"0"},{"type_name":"连续剧","type_id":"2","type_sort":"2","type_pid":"0"},{"type_name":"综艺","type_id":"3","type_sort":"3","type_pid":"0"},{"type_name":"动漫","type_id":"4","type_sort":"4","type_pid":"0"},{"type_name":"资讯","type_id":"5","type_sort":"5","type_pid":"0"},{"type_name":"动作片","type_id":"6","type_sort":"1","type_pid":"1"},{"type_name":"喜剧片","type_id":"7","type_sort":"2","type_pid":"1"},{"type_name":"爱情片","type_id":"8","type_sort":"3","type_pid":"1"},{"type_name":"科幻片","type_id":"9","type_sort":"4","type_pid":"1"},{"type_name":"恐怖片","type_id":"10","type_sort":"5","type_pid":"1"},{"type_name":"剧情片","type_id":"11","type_sort":"6","type_pid":"1"},{"type_name":"战争片","type_id":"12","type_sort":"7","type_pid":"1"},{"type_name":"国产剧","type_id":"13","type_sort":"1","type_pid":"2"},{"type_name":"港台剧","type_id":"14","type_sort":"2","type_pid":"2"},{"type_name":"日韩剧","type_id":"15","type_sort":"3","type_pid":"2"},{"type_name":"欧美剧","type_id":"16","type_sort":"4","type_pid":"2"},{"type_name":"公告","type_id":"17","type_sort":"1","type_pid":"5"},{"type_name":"头条","type_id":"18","type_sort":"2","type_pid":"5"},{"type_name":"福利片","type_id":"20","type_sort":"0","type_pid":"2"}]
     * Msg :
     */

    private int Code;
    private String Msg;
    private List<DataBean> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * type_name : 电影
         * type_id : 1
         * type_sort : 1
         * type_pid : 0
         */

        private String type_name;
        private String type_id;
        private String type_sort;
        private String type_pid;

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getType_sort() {
            return type_sort;
        }

        public void setType_sort(String type_sort) {
            this.type_sort = type_sort;
        }

        public String getType_pid() {
            return type_pid;
        }

        public void setType_pid(String type_pid) {
            this.type_pid = type_pid;
        }
    }


}
