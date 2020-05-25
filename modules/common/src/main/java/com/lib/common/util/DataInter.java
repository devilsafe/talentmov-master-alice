package com.lib.common.util;

/**
 * @author huangyong
 * createTime 2019/6/22
 */
public class DataInter {



    public class KEY {

        public static final String TYPE_ID = "movie_type_id";
        public static final String VOD_ID = "movie_vod_id";
        public static final String TYPE_ID_ARRAY = "type_id_array";
        public static final String IS_LOGIN = "is_login";
        public static final String USER_MSG = "user_short_msg";
        public static final String USER_NAME = "user_name";
        public static final String USER_TOKEN = "user_token";
        public static final String USER_INFO = "user_info";
        public static final String PLAY_INDEX = "play_index";
        public static final String VCODE = "code";
        public static final String VOD_DATA = "vod_data";
        public static final String MOVIE_DATA = "movie_data";
        public static final String ACTION_REFRESH_COIN = "update_coin";
        public static final String AD_URL = "vod_ad_url";
        public static final String ACTION_EXIT_LOGIN = "exit.login";
        public static final String GROUP_ID = "user.groupid";
        public static final String USER_COIN_INFO = "user.coin.num";
        public static final String ICON_INDEX = "icon.index";
        public static final String ACTION_REFRESH_ICON = "refresh.icon";
        public static final String WEB_URL = "web_url";
        public static final String PLAY_CODEC = "media_codec";
    }

    public static final int[] movieTab = {
            6, 7, 8, 9, 10, 11, 12
    };
    //控制电视剧有哪些分类
    public static final int[] seriTab = {
            13, 14, 15, 16
    };

    public static final int[] seriTabV = {
            13, 13, 16, 22,  24, 25
    };

    public class DADA {

        //首页，混合数据
        public static final int HOME = 0;

        //电影
        public static final int MOVIE = 9;

        //电视剧
        public static final int SERIS = 16;

        //综艺
        public static final int SHOW = 3;

        //动漫
        public static final int CARTOON = 4;

        //头条
        public static final int NEWS = 4;

        //港剧
        public static final int HONGKONG = 14;
        //日剧
        public static final int JANP = 15;
        //美剧
        public static final int AMERICA = 16;
        //韩剧
        public static final int KORIA = 20;
        //泰剧
        public static final int TAIJU = 22;


        //伦理片
        public static final int AV = 22;

    }

    public static int[] mainId = {
            DataInter.DADA.MOVIE,
            DataInter.DADA.MOVIE,
            DataInter.DADA.SERIS,
            DataInter.DADA.CARTOON,
    };

    public static String[] mainTabContent = {
            "全部类型",
            "电影",
            "电视剧",
            "动漫"
    };

    public static String[] movieType = {
            "全部类别",
            "喜剧",
            "爱情",
            "恐怖",
            "动作",
            "科幻",
            "剧情",
            "战争",
            "伦理"
    };


    public static String[] curtoonArea = {
            "全部地区",
            "日本",
            "欧美",
            "大陆",
            "法国",
            "印度",
            "英国",
            "泰国",
            "韩国",
            "香港",
            "台湾",
            "西班牙",
            "加拿大",

    };
    public static String[] curtoonAreaV = {
            "日本",
            "日本",
            "欧美",
            "大陆",
            "法国",
            "印度",
            "英国",
            "泰国",
            "韩国",
            "香港",
            "台湾",
            "西班牙",
            "加拿大",

    };

    public static int[] typeId = {
            7,
            7,
            8,
            10,
            6,
            9,
            11,
            12,
            20
    };

    public static String[] movieArea = {
            "全部地区",
            "大陆",
            "香港",
            "台湾",
            "美国",
            "法国",
            "英国",
            "日本",
            "韩国",
            "德国",
            "泰国",
            "印度",
            "意大利",
            "西班牙"
    };


    public static String[] seriArea = {

            "全部类别",
            "国产",
            "欧美",
            "日本",
            "泰国",
            "韩国",
            "香港",
            "台湾"
    };


    public static String[] seleYearV = {
            "全部年代",
            "2019",
            "2018",
            "2017",
            "2016",
            "2015",
            "2014",
            "2013",
            "2012",
            "2011",
            "2010",
            "2009",
            "2008"
    };


}
