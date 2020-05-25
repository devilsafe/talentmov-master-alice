package com.movtalent.app.model.dto;

/**
 * @author huangyong
 * createTime 2019-09-17
 */
public class LoginDto {


    /**
     * Code : 200
     * Data : {"code":0,"user_id":"35","token":"161FFB83F5AD2EC7AA1A4622CC469227","tips":"登录成功","user_points":"10","user_name":"testicon","user_portrait_thumb":"0","rec_regist_link":"http://123.207.150.253/mvcms/index.php/user/reg.html?uid=35","rec_look_link":"http://123.207.150.253/mvcms/index.php/user/visit.html?uid=35","group_id":"2","user_end_time":"0"}
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
         * code : 0
         * user_id : 35
         * token : 161FFB83F5AD2EC7AA1A4622CC469227
         * tips : 登录成功
         * user_points : 10
         * user_name : testicon
         * user_portrait_thumb : 0
         * rec_regist_link : http://123.207.150.253/mvcms/index.php/user/reg.html?uid=35
         * rec_look_link : http://123.207.150.253/mvcms/index.php/user/visit.html?uid=35
         * group_id : 2
         * user_end_time : 0
         */

        private int code;
        private String user_id;
        private String token;
        private String tips;
        private String user_points;
        private String user_name;
        private String user_portrait_thumb;
        private String rec_regist_link;
        private String rec_look_link;
        private String group_id;
        private String user_end_time;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getUser_points() {
            return user_points;
        }

        public void setUser_points(String user_points) {
            this.user_points = user_points;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_portrait_thumb() {
            return user_portrait_thumb;
        }

        public void setUser_portrait_thumb(String user_portrait_thumb) {
            this.user_portrait_thumb = user_portrait_thumb;
        }

        public String getRec_regist_link() {
            return rec_regist_link;
        }

        public void setRec_regist_link(String rec_regist_link) {
            this.rec_regist_link = rec_regist_link;
        }

        public String getRec_look_link() {
            return rec_look_link;
        }

        public void setRec_look_link(String rec_look_link) {
            this.rec_look_link = rec_look_link;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getUser_end_time() {
            return user_end_time;
        }

        public void setUser_end_time(String user_end_time) {
            this.user_end_time = user_end_time;
        }
    }
}
