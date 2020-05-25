package com.movtalent.app.model.dto;

import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class SearchWdDto {

    /**
     * Code : 200
     * Data : [{"vod_name":"辛普森一家第二十一季","vod_pic":"https://img.tupian-zuida.com/pic/upload/vod/2019-08-16/201908161565969492.jpg","vod_id":"19187","vod_remarks":"23集全","vod_score":"8.0"},{"vod_name":"超级洛佩兹","vod_pic":"http://pic.bjssmd.net/img/p2519701999.jpg","vod_id":"19562","vod_remarks":"HD","vod_score":"8.0"},{"vod_name":"乱世豪情","vod_pic":"http://pic.bjssmd.net/img/p2506928842.jpg","vod_id":"19608","vod_remarks":"HD","vod_score":"8.0"},{"vod_name":"飞鸿笑传之破茧","vod_pic":"http://pic.bjssmd.net/img/p2518168706.jpg","vod_id":"19416","vod_remarks":"HD","vod_score":"8.0"},{"vod_name":"日本少林","vod_pic":"http://pic.bjssmd.net/img/p2307325165.jpg","vod_id":"19657","vod_remarks":"HD","vod_score":"10.0"},{"vod_name":"毒窝","vod_pic":"http://pic.bjssmd.net/img/p2553275983.jpg","vod_id":"19542","vod_remarks":"HD","vod_score":"10.0"},{"vod_name":"24小时第七季","vod_pic":"https://img.tupian-zuida.com/pic/upload/vod/2019-08-19/201908191566181825.jpg","vod_id":"19296","vod_remarks":"24集全","vod_score":"9.0"},{"vod_name":"震天桥傻王","vod_pic":"https://img.tupian-zuida.com/pic/upload/vod/2019-08-22/201908221566473205.jpg","vod_id":"19370","vod_remarks":"HD1280高清国语中字版","vod_score":"10.0"},{"vod_name":"综艺玩很大[2019]","vod_pic":"https://img.tupian-zuida.com/pic/upload/vod/2017-10-01/201710011506855641.jpg","vod_id":"19240","vod_remarks":"20190817期","vod_score":"9.0"},{"vod_name":"人生七部曲","vod_pic":"http://pic.bjssmd.net/img/p2215810689.jpg","vod_id":"19476","vod_remarks":"HD","vod_score":"8.0"},{"vod_name":"公园与游憩第五季","vod_pic":"https://img.tupian-zuida.com/pic/upload/vod/2019-08-21/201908211566395416.jpg","vod_id":"19373","vod_remarks":"22集全","vod_score":"9.0"},{"vod_name":"恋爱氧气","vod_pic":"http://pic.bjssmd.net/img/p2530686539.jpg","vod_id":"19449","vod_remarks":"HD","vod_score":"9.0"},{"vod_name":"极地恶灵第二季","vod_pic":"https://img.tupian-zuida.com/pic/upload/vod/2019-08-13/201908131565658163.jpg","vod_id":"19250","vod_remarks":"更新到02集","vod_score":"8.0"},{"vod_name":"无耻之徒(美版)第七季","vod_pic":"https://img.tupian-zuida.com/pic/upload/vod/2019-08-18/201908181566140916.jpg","vod_id":"19262","vod_remarks":"12集全","vod_score":"10.0"},{"vod_name":"笑到最后","vod_pic":"http://pic.bjssmd.net/img/p2545233372.jpg","vod_id":"19576","vod_remarks":"HD","vod_score":"10.0"},{"vod_name":"乐高幻影忍者大电影","vod_pic":"http://pic.bjssmd.net/img/p2493573503.jpg","vod_id":"19641","vod_remarks":"HD","vod_score":"9.0"},{"vod_name":"妙女神探第五季","vod_pic":"https://img.tupian-zuida.com/pic/upload/vod/2019-08-19/201908191566219275.jpg","vod_id":"19325","vod_remarks":"18集全","vod_score":"10.0"},{"vod_name":"草地状元[2019]","vod_pic":"https://img.tupian-zuida.com/pic/upload/vod/2019-01-08/201901081546917900.jpg","vod_id":"19310","vod_remarks":"20190819期","vod_score":"10.0"},{"vod_name":"实习医生风云第二季","vod_pic":"https://img.tupian-zuida.com/pic/upload/vod/2019-08-18/201908181566138950.jpg","vod_id":"19278","vod_remarks":"22集全","vod_score":"8.0"},{"vod_name":"花花万物第二季","vod_pic":"https://img.tupian-zuida.com/pic/upload/vod/2019-08-06/201908061565075104.jpg","vod_id":"19311","vod_remarks":"20190820期","vod_score":"8.0"}]
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
         * vod_name : 辛普森一家第二十一季
         * vod_pic : https://img.tupian-zuida.com/pic/upload/vod/2019-08-16/201908161565969492.jpg
         * vod_id : 19187
         * vod_remarks : 23集全
         * vod_score : 8.0
         */

        private String vod_name;
        private String vod_pic;
        private String vod_id;
        private String vod_remarks;
        private String vod_score;

        public String getVod_name() {
            return vod_name;
        }

        public void setVod_name(String vod_name) {
            this.vod_name = vod_name;
        }

        public String getVod_pic() {
            return vod_pic;
        }

        public void setVod_pic(String vod_pic) {
            this.vod_pic = vod_pic;
        }

        public String getVod_id() {
            return vod_id;
        }

        public void setVod_id(String vod_id) {
            this.vod_id = vod_id;
        }

        public String getVod_remarks() {
            return vod_remarks;
        }

        public void setVod_remarks(String vod_remarks) {
            this.vod_remarks = vod_remarks;
        }

        public String getVod_score() {
            return vod_score;
        }

        public void setVod_score(String vod_score) {
            this.vod_score = vod_score;
        }
    }
}
