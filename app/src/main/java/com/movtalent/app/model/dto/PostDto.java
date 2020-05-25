package com.movtalent.app.model.dto;

/**
 * @author huangyong
 * createTime 2019-10-16
 */
public class PostDto {


    /**
     * Code : 200
     * Data : {"content":"公告内容公告内容公告内容公告内容公告内容公告内容公告内容公告内容公告内容公告内容","title":"公告标题","show":true}
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
         * content : 公告内容公告内容公告内容公告内容公告内容公告内容公告内容公告内容公告内容公告内容
         * title : 公告标题
         * show : true
         */

        private String content;
        private String title;
        private boolean show;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }
    }
}
