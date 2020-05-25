package com.movtalent.app.http;


import com.movtalent.app.App_Config;
import com.movtalent.app.model.vo.VideoTypeVo;

import java.util.ArrayList;

/**
 * Created by Huangyong on 2017/9/12.
 */

public class UrlConfig {



    public static String BaseUrl = App_Config.BASE_URL;


    //广告条
    public static final String AD_BANNER = App_Config.AD_BANNER;
    /**
     * 开屏
     */
    public static final String AD_SPLASH = App_Config.SPLAH_URL;
    /**
     * 关于我们
     */
    public static final String ABOUT_US = "https://www.518915.com";
    /**
     * 获取分类数据列表
     */
    static final String getMovieList = App_Config.SERVER_PATH+"public/?service=App.Mov.GetOnlineList";
    /**
     * 获取影片分类列表
     */
    static final String getVodType = App_Config.SERVER_PATH+"public/?service=App.Mov.GetTypeList";
    /**
     * 搜索接口
     */
    static final String getSearch = App_Config.SERVER_PATH+"public/?service=App.Mov.SearchVod";
    /**
     * 获取搜索推荐词
     */
    static final String getSearchRecWd = App_Config.SERVER_PATH+"public/?service=App.Mov.GetSearchRec";
    /**
     * 猜你喜欢
     */
    static final String getRecmend = App_Config.SERVER_PATH+"public/?service=App.Mov.GetRecommend";
    /**
     * 首页推荐1、2、3、4、5，需要后台手动添加 推荐6 为首页banner
     */
    static final String getHomeLevel = App_Config.SERVER_PATH+"public/?service=App.Mov.GetHomeLevel";
    /**
     * 获取查看更多
     */
    static final String getHomeLevelAll = App_Config.SERVER_PATH+"public/?service=App.Mov.GetHomeLevelAll";
    /**
     * 获取影片详情
     */
    static final String getVodDetail = App_Config.SERVER_PATH+"public/?service=App.Mov.GetOnlineMvById";
    /**
     * 获取专题分类列表
     */
    static final String getTopicRoot = App_Config.SERVER_PATH+"public/?service=App.Mov.GetTopicRootList";
    /**
     * 获取专题内容列表
     */
    static final String getTopicList = App_Config.SERVER_PATH+"public/?service=App.Mov.GetTopicList";

    /**
     * 获取分类筛选列表
     */
    static final String getCategoryList = App_Config.SERVER_PATH+"public/?service=App.Mov.GetCategory";
    /**
     * 增加金币
     */
    static final String addUserCoin = App_Config.SERVER_PATH+"public/?service=App.Mov.AddCoin";
    /**
     * 获取金币
     */
    static final String getUserCoin = App_Config.SERVER_PATH+"public/?service=App.Mov.GetCoin";
    /**
     * 检查更新
     */
    static final String getUpdate = App_Config.SERVER_PATH+"public/?service=App.Mov.CheckUpdate";

    /**
     * 获取公告
     */
    static final String getPublish = App_Config.SERVER_PATH+"public/?service=App.Mov.CheckPubish";
    static final String getADConfig = App_Config.SERVER_PATH+"public/?service=App.Mov.GetAdConfig";


    /*用户相关*/
    /**
     * 注册
     */
    static final String regist = App_Config.SERVER_PATH+"public/?service=App.User_Register.Go";
    /**
     * 登录
     */
    static final String login = App_Config.SERVER_PATH+"public/?service=App.User_Login.Go";
    /**
     * 更换头像
     */
    static final String changeIcon = App_Config.SERVER_PATH+"public/?service=App.Mov.UpdateUserIcon";

    /*金币兑换会员*/

    /**
     * 兑换
     */
    static final String BUY_VIP = App_Config.SERVER_PATH+"public/?service=App.Mov.BuyVip";
    /**
     * 查询兑换记录
     */
    static final String GET_PAY_LOG = App_Config.SERVER_PATH+"public/?service=App.Mov.GetPayLog";


    /**
     * 收藏：添加收藏
     */
    static final String ADD_FAVOR =App_Config.SERVER_PATH+ "public/?service=App.Mov.AddFavor";
    /**
     * 收藏：取消收藏
     */
    static final String CANCEL_FAVOR = App_Config.SERVER_PATH+"public/?service=App.Mov.CancelFavor";
    /**
     * 收藏：获取收藏
     */
    static final String GET_FAVOR = App_Config.SERVER_PATH+"public/?service=App.Mov.GetFavor";
    /**
     * 收藏：获取当前是否收藏
     */
    static final String GET_HAVE_FAVOR = App_Config.SERVER_PATH+"public/?service=App.Mov.GetHaveFavor";

    /**
     * 新建评论
     */
    static final String NEW_COMMENT = App_Config.SERVER_PATH+"public/?service=App.Mov.AddComment";

    /**
     * 删除评论
     */
    static final String DELETE_COMMENT = App_Config.SERVER_PATH+"public/?service=App.Mov.DeleteComment";
    /**
     * 获取当前影片的所有评论
     */
    static final String GET_COMMENT = App_Config.SERVER_PATH+"public/?service=App.Mov.GetComment";
    /**
     * 给评论点赞
     */
    static final String DIGG_COMMENT = App_Config.SERVER_PATH+"public/?service=App.Mov.DiggComment";
    /**
     * 取消评论点赞
     */
    static final String CANCEL_DIGG_COMMENT =App_Config.SERVER_PATH+ "public/?service=App.Mov.CancelDiggComment";


    /**
     * 主分类暂时定为不可编辑
     */
    public static String[] tabX =App_Config.TAB_ARR;
    /**
     * 分类信息保存到全局
     */
    public static VideoTypeVo TYPE_DATA = null;
    //四个分类数据
    public static ArrayList<VideoTypeVo.ClassBean> show;
    public static ArrayList<VideoTypeVo.ClassBean> curtoon;
    public static ArrayList<VideoTypeVo.ClassBean> seri;
    public static ArrayList<VideoTypeVo.ClassBean> movie;
}
