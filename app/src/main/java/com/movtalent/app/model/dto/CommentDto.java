package com.movtalent.app.model.dto;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class CommentDto {


    /**
     * Code : 200
     * Data : {"comment_rid":19552,"user_id":12,"comment_pid":4,"comment_name":"ceshiuuu","comment_time":1569059743,"comment_content":"责ssss二级留言","id":"13"}
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
         * comment_rid : 19552
         * user_id : 12
         * comment_pid : 4
         * comment_name : ceshiuuu
         * comment_time : 1569059743
         * comment_content : 责ssss二级留言
         * id : 13
         */

        private int comment_rid;
        private int user_id;
        private int comment_pid;
        private String comment_name;
        private int comment_time;
        private String comment_content;
        private String id;

        public int getComment_rid() {
            return comment_rid;
        }

        public void setComment_rid(int comment_rid) {
            this.comment_rid = comment_rid;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getComment_pid() {
            return comment_pid;
        }

        public void setComment_pid(int comment_pid) {
            this.comment_pid = comment_pid;
        }

        public String getComment_name() {
            return comment_name;
        }

        public void setComment_name(String comment_name) {
            this.comment_name = comment_name;
        }

        public int getComment_time() {
            return comment_time;
        }

        public void setComment_time(int comment_time) {
            this.comment_time = comment_time;
        }

        public String getComment_content() {
            return comment_content;
        }

        public void setComment_content(String comment_content) {
            this.comment_content = comment_content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
