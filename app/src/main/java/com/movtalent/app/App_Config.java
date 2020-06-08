package com.movtalent.app;

/**
 * @author huangyong
 * createTime 2019-10-13
 * 全局的自定义配置
 * 根据自己实际情况修改
 */
public class App_Config {


    /**
     * 服务器所有请求依赖的主域名，为cms接口系统安装的地址
     */
    public static final String BASE_URL = "http://192.168.1.104/";
//    public static final String BASE_URL = "http://194.58.119.183/";
    /**
     * 主分类配置，不建议修改
     */
    public static final String[] TAB_ARR = {"推荐", "电视剧", "电影", "动漫", "综艺"};

//
//    /**
//     * 友盟统计key
//     */
//    public static final String UMENKEY = "5db8f7ca0cafb294cd000838";
//    /**
//     * 友盟统计app渠道名
//     */
//    public static final String UMEN_APP_NAME = "movtalent";
//    /**
//     * 友盟推送app key
//     */
//    public static final String UMEN_PUSH_KEY = "4b3893e95500500c2c2f88069e56da0c";


    /**
     * qq分享 appId
     */
    public static final String QQ_APP_ID = "1109823571";
    public static final String QQ_APP_SCOP = "get_user_info,"
            + "get_simple_userinfo,"
            + "add_share,"
            + "add_topic,"
            + "add_pic_t";
    /**
     * 微信分享、朋友圈分享 appId
     * 替换为自己申请的账户信息
     */
    public static final String WEICHAT_APP_ID = "wxd2069b96357bdfe7";
    public static final String WEICHAT_APP_SECRET = "89bc6b30bb7c750798296a7897bf8235";
    /**
     * 微博分享 配置
     */
    public static final String WEIBO_APP_KEY = "xxxxxxxxxxxx";
    public static final String WEIBO_APP_SCOP = "friendships_groups_read,"
            + "friendships_groups_write,"
            + "statuses_to_me_read,"
            + "follow_app_official_microblog";
    public static final String WEIBO_REDIRECT_URL = "xxxxxxxxxxxx";
    
    
    /**
     * 横条广告配置，将html文件放到assets目录里
     */
    public static final String AD_BANNER = "file:///android_asset/ad_banner.html";


    /**
     * 开屏页地址
     */
    public static final String SPLAH_URL = "http://www.baidu.com";

    /**
     * 内置头像，可自己替换
     */
    public static final int[] ICON_GROUP = {
            R.drawable.ic_head_1,
            R.drawable.ic_head_2,
            R.drawable.ic_head_3,
            R.drawable.ic_head_4,
            R.drawable.ic_head_5,
            R.drawable.ic_head_6,
            R.drawable.ic_head_7,
            R.drawable.ic_head_8
    };
    /**
     * 服务器里这个接口系统的安装目录
     */
    public static final String SERVER_PATH = "app/app/app/cms/";
    /**
     * 会员显示配置，不建议修改，如果要改，需要同时改后台接口
     */
    public static final String VIP_1 = "VIP会员1天";
    public static final String VIP_2 = "VIP会员1周";
    public static final String VIP_3 = "VIP会员1月";
    public static final String VIP_4 = "VIP会员1年";


    /**
     * 分类筛选页面的地区配置，这个就自己写吧，读服务器没意义，看下影片都有哪些地区
     */
    public static final String[] AREA_CONFIG = {
            "全部地区",
            "大陆",
            "美国",
            "法国",
            "印度",
            "英国",
            "日本",
            "泰国",
            "韩国",
            "香港",
            "台湾",
            "西班牙",
            "加拿大",
            "其他",
            "海外"
    };

//    public static final String JUMP_URL_3 = "http://www.baidu.com";
//    public static final String JUMP_URL_2 = "http://www.baidu.com";
    public static final String JUMP_URL_1 = "https://www.518915.com";
}
