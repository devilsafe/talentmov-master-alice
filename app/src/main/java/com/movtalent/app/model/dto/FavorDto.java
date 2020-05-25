package com.movtalent.app.model.dto;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class FavorDto {

    /**
     * Code : 200
     * Data : {"item":1,"code":0}
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
         * item : 1
         * code : 0
         */

        private int item;
        private int code;

        public int getItem() {
            return item;
        }

        public void setItem(int item) {
            this.item = item;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
