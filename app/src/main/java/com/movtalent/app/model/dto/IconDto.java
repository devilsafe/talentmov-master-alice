package com.movtalent.app.model.dto;

/**
 * @author huangyong
 * createTime 2019-10-10
 */
public class IconDto {

    /**
     * Code : 200
     * Data : {"user_portrait_thumb":"7"}
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
         * user_portrait_thumb : 7
         */

        private String user_portrait_thumb;

        public String getUser_portrait_thumb() {
            return user_portrait_thumb;
        }

        public void setUser_portrait_thumb(String user_portrait_thumb) {
            this.user_portrait_thumb = user_portrait_thumb;
        }
    }
}
