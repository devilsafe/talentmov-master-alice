package com.movtalent.app.model.dto;

import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-11
 */
public class VideoListDto {


    /**
     * Code : 200
     * Data : [{"vod_id":"227","type_id":"7","vod_class":"喜剧","vod_name":"秘密情人","vod_actor":"莱西·沙伯特,安德鲁·W·沃克,彼得·麦克内尔,塔拉·尤伦,Catherine,Burdon,Kevin,Claydon,Richard,Young","vod_pic":"https://tu.tianzuida.com/pic/upload/vod/2019-09-17/201909171568695975.jpg","vod_remarks":"HD1280高清中英双字版","vod_area":"美国","vod_lang":"英语","vod_year":"2018","vod_score":"2.0","vod_time":"1569215403","vod_time_add":"1569215403","vod_content":"一位年轻女子从一位神秘的\u201c杂工\u201d留下的纸条中得到建议，当一位销售代表带着购买她家酒厂的计划来到这里时","vod_play_url":"HD1280高清中英双字版$http://bili.meijuzuida.com/20190917/21460_b3c73447/index.m3u8$$$HD1280高清中英双字版$http://bili.meijuzuida.com/share/1e74f51b4cd0aaa6c3e0b77c71851ea7"},{"vod_id":"293","type_id":"7","vod_class":"喜剧","vod_name":"正义复仇者","vod_actor":"艾米·斯马特,西蒙·雷克斯,Tony,Cavalero,史蒂芬·兰纳兹","vod_pic":"https://tu.tianzuida.com/pic/upload/vod/2019-09-18/201909181568773246.jpg","vod_remarks":"HD1280高清中英双字版","vod_area":"美国","vod_lang":"英语","vod_year":"2018","vod_score":"6.0","vod_time":"1569215411","vod_time_add":"1569215411","vod_content":"本片恶搞DC及漫威众多超级英雄，讲述了超蝙从正义复仇者退休后，却又必须肩负重任，阻止小丑男毁灭世界....","vod_play_url":"HD1280高清中英双字版$http://bili.meijuzuida.com/20190917/21464_cde5905c/index.m3u8$$$HD1280高清中英双字版$http://bili.meijuzuida.com/share/50806d9f6a9a340bcbd59d1f7ee5126c"}]
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
         * vod_id : 227
         * type_id : 7
         * vod_class : 喜剧
         * vod_name : 秘密情人
         * vod_actor : 莱西·沙伯特,安德鲁·W·沃克,彼得·麦克内尔,塔拉·尤伦,Catherine,Burdon,Kevin,Claydon,Richard,Young
         * vod_pic : https://tu.tianzuida.com/pic/upload/vod/2019-09-17/201909171568695975.jpg
         * vod_remarks : HD1280高清中英双字版
         * vod_area : 美国
         * vod_lang : 英语
         * vod_play_from : bjm3u8
         * vod_year : 2018
         * vod_score : 2.0
         * vod_time : 1569215403
         * vod_time_add : 1569215403
         * vod_content : 一位年轻女子从一位神秘的“杂工”留下的纸条中得到建议，当一位销售代表带着购买她家酒厂的计划来到这里时
         * vod_play_url : HD1280高清中英双字版$http://bili.meijuzuida.com/20190917/21460_b3c73447/index.m3u8$$$HD1280高清中英双字版$http://bili.meijuzuida.com/share/1e74f51b4cd0aaa6c3e0b77c71851ea7
         */

        private String vod_id;
        private String type_id;
        private String vod_class;
        private String vod_name;
        private String vod_actor;
        private String vod_pic;
        private String vod_remarks;
        private String vod_area;
        private String vod_lang;
        private String vod_year;
        private String vod_score;
        private String vod_time;
        private String vod_time_add;
        private String vod_content;
        private String vod_play_url;

        public String getVod_play_from() {
            return vod_play_from;
        }

        public void setVod_play_from(String vod_play_from) {
            this.vod_play_from = vod_play_from;
        }

        private String vod_play_from;

        public String getVod_id() {
            return vod_id;
        }

        public void setVod_id(String vod_id) {
            this.vod_id = vod_id;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getVod_class() {
            return vod_class;
        }

        public void setVod_class(String vod_class) {
            this.vod_class = vod_class;
        }

        public String getVod_name() {
            return vod_name;
        }

        public void setVod_name(String vod_name) {
            this.vod_name = vod_name;
        }

        public String getVod_actor() {
            return vod_actor;
        }

        public void setVod_actor(String vod_actor) {
            this.vod_actor = vod_actor;
        }

        public String getVod_pic() {
            return vod_pic;
        }

        public void setVod_pic(String vod_pic) {
            this.vod_pic = vod_pic;
        }

        public String getVod_remarks() {
            return vod_remarks;
        }

        public void setVod_remarks(String vod_remarks) {
            this.vod_remarks = vod_remarks;
        }

        public String getVod_area() {
            return vod_area;
        }

        public void setVod_area(String vod_area) {
            this.vod_area = vod_area;
        }

        public String getVod_lang() {
            return vod_lang;
        }

        public void setVod_lang(String vod_lang) {
            this.vod_lang = vod_lang;
        }

        public String getVod_year() {
            return vod_year;
        }

        public void setVod_year(String vod_year) {
            this.vod_year = vod_year;
        }

        public String getVod_score() {
            return vod_score;
        }

        public void setVod_score(String vod_score) {
            this.vod_score = vod_score;
        }

        public String getVod_time() {
            return vod_time;
        }

        public void setVod_time(String vod_time) {
            this.vod_time = vod_time;
        }

        public String getVod_time_add() {
            return vod_time_add;
        }

        public void setVod_time_add(String vod_time_add) {
            this.vod_time_add = vod_time_add;
        }

        public String getVod_content() {
            return vod_content;
        }

        public void setVod_content(String vod_content) {
            this.vod_content = vod_content;
        }

        public String getVod_play_url() {
            return vod_play_url;
        }

        public void setVod_play_url(String vod_play_url) {
            this.vod_play_url = vod_play_url;
        }
    }
}
