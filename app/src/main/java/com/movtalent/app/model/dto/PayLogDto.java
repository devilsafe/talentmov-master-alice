package com.movtalent.app.model.dto;

import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-25
 */
public class PayLogDto {


    /**
     * Code : 200
     * Data : {"pay_log":[{"user_id":"2","plog_type":"7","plog_points":"10","plog_time":"1569336427"},{"user_id":"2","plog_type":"7","plog_points":"300","plog_time":"1569336462"},{"user_id":"2","plog_type":"7","plog_points":"70","plog_time":"1569336653"},{"user_id":"2","plog_type":"7","plog_points":"10","plog_time":"1569336681"},{"user_id":"2","plog_type":"7","plog_points":"300","plog_time":"1569369088"},{"user_id":"2","plog_type":"7","plog_points":"10","plog_time":"1569372079"},{"user_id":"2","plog_type":"7","plog_points":"10","plog_time":"1569422543"}],"vip_data":{"user_points":"9300","user_end_time":"1575470827","group_id":"3"}}
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
         * pay_log : [{"user_id":"2","plog_type":"7","plog_points":"10","plog_time":"1569336427"},{"user_id":"2","plog_type":"7","plog_points":"300","plog_time":"1569336462"},{"user_id":"2","plog_type":"7","plog_points":"70","plog_time":"1569336653"},{"user_id":"2","plog_type":"7","plog_points":"10","plog_time":"1569336681"},{"user_id":"2","plog_type":"7","plog_points":"300","plog_time":"1569369088"},{"user_id":"2","plog_type":"7","plog_points":"10","plog_time":"1569372079"},{"user_id":"2","plog_type":"7","plog_points":"10","plog_time":"1569422543"}]
         * vip_data : {"user_points":"9300","user_end_time":"1575470827","group_id":"3"}
         */

        private VipDataBean vip_data;
        private List<PayLogBean> pay_log;

        public VipDataBean getVip_data() {
            return vip_data;
        }

        public void setVip_data(VipDataBean vip_data) {
            this.vip_data = vip_data;
        }

        public List<PayLogBean> getPay_log() {
            return pay_log;
        }

        public void setPay_log(List<PayLogBean> pay_log) {
            this.pay_log = pay_log;
        }

        public static class VipDataBean {
            /**
             * user_points : 9300
             * user_end_time : 1575470827
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

        public static class PayLogBean {
            /**
             * user_id : 2
             * plog_type : 7
             * plog_points : 10
             * plog_time : 1569336427
             */

            private String user_id;
            private String plog_type;
            private String plog_points;
            private String plog_time;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getPlog_type() {
                return plog_type;
            }

            public void setPlog_type(String plog_type) {
                this.plog_type = plog_type;
            }

            public String getPlog_points() {
                return plog_points;
            }

            public void setPlog_points(String plog_points) {
                this.plog_points = plog_points;
            }

            public String getPlog_time() {
                return plog_time;
            }

            public void setPlog_time(String plog_time) {
                this.plog_time = plog_time;
            }
        }
    }
}
