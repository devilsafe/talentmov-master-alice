package com.movtalent.app.model.dto;

import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class TopicRDto {

    /**
     * Code : 200
     * Data : [{"topic_id":"1","topic_name":"热门影视","topic_pic_slide":"upload/topic/20190909-1/0ffcbdf0e04109d294ace52b148723c1.jpg","topic_sub":"9月看片推荐"},{"topic_id":"2","topic_name":"本周推荐","topic_pic_slide":"upload/topic/20190909-1/9020db6b37b1bee33302dfc11431e3e0.jpg","topic_sub":""}]
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
         * topic_id : 1
         * topic_name : 热门影视
         * topic_pic_slide : upload/topic/20190909-1/0ffcbdf0e04109d294ace52b148723c1.jpg
         * topic_sub : 9月看片推荐
         */

        private String topic_id;
        private String topic_name;
        private String topic_pic_slide;
        private String topic_sub;

        public String getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }

        public String getTopic_name() {
            return topic_name;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = topic_name;
        }

        public String getTopic_pic_slide() {
            return topic_pic_slide;
        }

        public void setTopic_pic_slide(String topic_pic_slide) {
            this.topic_pic_slide = topic_pic_slide;
        }

        public String getTopic_sub() {
            return topic_sub;
        }

        public void setTopic_sub(String topic_sub) {
            this.topic_sub = topic_sub;
        }
    }
}
