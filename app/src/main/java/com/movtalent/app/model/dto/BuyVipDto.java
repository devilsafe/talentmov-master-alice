package com.movtalent.app.model.dto;

/**
 * @author huangyong
 * createTime 2019-09-25
 */
public class BuyVipDto {

    /**
     * Code : 200
     * Data : {"item_data":{"user_points":"0","user_end_time":"1569456223","group_id":"3"},"item_code":1,"item_msg":"金币余额不足"}
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
         * item_data : {"user_points":"0","user_end_time":"1569456223","group_id":"3"}
         * item_code : 1
         * item_msg : 金币余额不足
         */

        private ItemDataBean item_data;
        private int item_code;
        private String item_msg;

        public ItemDataBean getItem_data() {
            return item_data;
        }

        public void setItem_data(ItemDataBean item_data) {
            this.item_data = item_data;
        }

        public int getItem_code() {
            return item_code;
        }

        public void setItem_code(int item_code) {
            this.item_code = item_code;
        }

        public String getItem_msg() {
            return item_msg;
        }

        public void setItem_msg(String item_msg) {
            this.item_msg = item_msg;
        }

        public static class ItemDataBean {
            /**
             * user_points : 0
             * user_end_time : 1569456223
             * group_id : 3
             */

            private String user_points;
            private String user_end_time;
            private String group_id;

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
        }
    }
}
