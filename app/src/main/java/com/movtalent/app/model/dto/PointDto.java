package com.movtalent.app.model.dto;

/**
 * @author huangyong
 * createTime 2019-09-20
 */
public class PointDto {


    /**
     * Code : 200
     * Data : {"user_points":"0","user_end_time":"1569831563","group_id":"3","user_id":"9"}
     * Msg :
     */

    private int Code;
    private DataBean Data;
    private String Msg;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public static class DataBean {
        /**
         * user_points : 0
         * user_end_time : 1569831563
         * group_id : 3
         * user_id : 9
         */

        private String user_points;
        private String user_end_time;
        private String group_id;
        private String user_id;

        public String getUser_points() {
            return user_points;
        }

        public void setUser_points(String user_points) {
            this.user_points = user_points;
        }

        public String getUser_end_time() {
            return user_end_time;
        }

        public void setUser_end_time(String user_end_time) {
            this.user_end_time = user_end_time;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
