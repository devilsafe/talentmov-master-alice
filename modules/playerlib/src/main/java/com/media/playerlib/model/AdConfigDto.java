package com.media.playerlib.model;

/**
 * @author huangyong
 * createTime 2019-10-19
 */
public class AdConfigDto {

    /**
     * Code : 200
     * Data : {"ad_splash":{"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490871324&di=db56f6d4ed91343a3fcdf5888d03b011&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F13%2F20151113102650_WyAuR.thumb.700_0.jpeg","link":"http://www.baidu.com"},"ad_home_1":{"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"},"ad_home_2":{"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"},"ad_home_3":{"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"},"ad_home_4":{"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"},"ad_detail":{"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"},"ad_player":{"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"},"ad_user_center":{"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"}}
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
         * ad_splash : {"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490871324&di=db56f6d4ed91343a3fcdf5888d03b011&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F13%2F20151113102650_WyAuR.thumb.700_0.jpeg","link":"http://www.baidu.com"}
         * ad_home_1 : {"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"}
         * ad_home_2 : {"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"}
         * ad_home_3 : {"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"}
         * ad_home_4 : {"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"}
         * ad_detail : {"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"}
         * ad_player : {"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"}
         * ad_user_center : {"img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png","link":"http://www.baidu.com"}
         */

        private AdSplashBean ad_splash;
        private AdHome1Bean ad_home_1;
        private AdHome2Bean ad_home_2;
        private AdHome3Bean ad_home_3;
        private AdHome4Bean ad_home_4;
        private AdDetailBean ad_detail;
        private AdPlayerBean ad_player;
        private AdUserCenterBean ad_user_center;

        public AdSplashBean getAd_splash() {
            return ad_splash;
        }

        public void setAd_splash(AdSplashBean ad_splash) {
            this.ad_splash = ad_splash;
        }

        public AdHome1Bean getAd_home_1() {
            return ad_home_1;
        }

        public void setAd_home_1(AdHome1Bean ad_home_1) {
            this.ad_home_1 = ad_home_1;
        }

        public AdHome2Bean getAd_home_2() {
            return ad_home_2;
        }

        public void setAd_home_2(AdHome2Bean ad_home_2) {
            this.ad_home_2 = ad_home_2;
        }

        public AdHome3Bean getAd_home_3() {
            return ad_home_3;
        }

        public void setAd_home_3(AdHome3Bean ad_home_3) {
            this.ad_home_3 = ad_home_3;
        }

        public AdHome4Bean getAd_home_4() {
            return ad_home_4;
        }

        public void setAd_home_4(AdHome4Bean ad_home_4) {
            this.ad_home_4 = ad_home_4;
        }

        public AdDetailBean getAd_detail() {
            return ad_detail;
        }

        public void setAd_detail(AdDetailBean ad_detail) {
            this.ad_detail = ad_detail;
        }

        public AdPlayerBean getAd_player() {
            return ad_player;
        }

        public void setAd_player(AdPlayerBean ad_player) {
            this.ad_player = ad_player;
        }

        public AdUserCenterBean getAd_user_center() {
            return ad_user_center;
        }

        public void setAd_user_center(AdUserCenterBean ad_user_center) {
            this.ad_user_center = ad_user_center;
        }

        public static class AdSplashBean {
            /**
             * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490871324&di=db56f6d4ed91343a3fcdf5888d03b011&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201511%2F13%2F20151113102650_WyAuR.thumb.700_0.jpeg
             * link : http://www.baidu.com
             */

            private String img;
            private String link;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class AdHome1Bean {
            /**
             * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png
             * link : http://www.baidu.com
             */

            private String img;
            private String link;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class AdHome2Bean {
            /**
             * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png
             * link : http://www.baidu.com
             */

            private String img;
            private String link;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class AdHome3Bean {
            /**
             * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png
             * link : http://www.baidu.com
             */

            private String img;
            private String link;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class AdHome4Bean {
            /**
             * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png
             * link : http://www.baidu.com
             */

            private String img;
            private String link;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class AdDetailBean {
            /**
             * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png
             * link : http://www.baidu.com
             */

            private String img;
            private String link;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class AdPlayerBean {
            /**
             * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png
             * link : http://www.baidu.com
             */

            private String img;
            private String link;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class AdUserCenterBean {
            /**
             * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571490781113&di=800e9183d25b9e0bd151f17b2bbebff2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0126d556e91f706ac7255885734214.png
             * link : http://www.baidu.com
             */

            private String img;
            private String link;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }
}
