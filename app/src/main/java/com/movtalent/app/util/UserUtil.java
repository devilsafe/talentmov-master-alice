package com.movtalent.app.util;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.lib.common.util.DataInter;
import com.lib.common.util.SharePreferencesUtil;
import com.movtalent.app.App;
import com.movtalent.app.model.dto.BuyVipDto;
import com.movtalent.app.model.dto.LoginDto;
import com.movtalent.app.model.dto.PointDto;


/**
 * @author huangyong
 * createTime 2019/6/27
 */
public class UserUtil {

    public static void saveUserInfo(Context context, LoginDto.DataBean dataBean, String userInfo) {
        SharePreferencesUtil.setStringSharePreferences(context, DataInter.KEY.USER_TOKEN, dataBean.getToken());
        SharePreferencesUtil.setStringSharePreferences(context, DataInter.KEY.USER_INFO, userInfo);
        SharePreferencesUtil.setBooleanSharePreferences(context, DataInter.KEY.IS_LOGIN, true);
    }

    public static String getUserInfo(Context context) {
        String userInfo = SharePreferencesUtil.getStringSharePreferences(context, DataInter.KEY.USER_INFO, "");
        return userInfo;
    }

    public static void exitLogin(Context context) {
        SharePreferencesUtil.setBooleanSharePreferences(context, DataInter.KEY.IS_LOGIN, false);
        SharePreferencesUtil.setStringSharePreferences(context, DataInter.KEY.USER_TOKEN, "");
        SharePreferencesUtil.setStringSharePreferences(context, DataInter.KEY.USER_INFO, "");
    }

    public static String getUserToken(Context context) {
        return SharePreferencesUtil.getStringSharePreferences(context, DataInter.KEY.USER_TOKEN, "");
    }

    public static boolean isLogin() {

        if (TextUtils.isEmpty(getUserToken(App.getContext()))) {
            return false;
        }
        return true;
    }

    public static String getUserId() {
        if (isLogin()) {
            String userInfo = getUserInfo(App.getContext());
            LoginDto.DataBean user = new Gson().fromJson(userInfo, LoginDto.DataBean.class);
            return user.getUser_id();
        }
        return null;
    }

    public static int getGroupId() {
        if (isLogin()) {
            String userCoinInfo = SharePreferencesUtil.getStringSharePreferences(App.getContext(), DataInter.KEY.USER_COIN_INFO, null);

            PointDto.DataBean dataBean = new Gson().fromJson(userCoinInfo, PointDto.DataBean.class);
            if (dataBean != null && !TextUtils.isEmpty(dataBean.getGroup_id())) {
                return Integer.parseInt(dataBean.getGroup_id());
            }
        }
        return 0;
    }


    public static String getUserName() {

        if (isLogin()) {
            String userInfo = getUserInfo(App.getContext());
            if (TextUtils.isEmpty(userInfo)) {
                return "";
            }
            LoginDto.DataBean user = new Gson().fromJson(userInfo, LoginDto.DataBean.class);
            return user.getUser_name();
        }
        return null;
    }

    public static boolean checkAuth() {
        return UserUtil.getGroupId() > 2 && UserUtil.getAuthTime();
    }

    private static boolean getAuthTime() {
        if (isLogin()) {
            String vipEndTime = getUserVipEndTime();
            if (TextUtils.isEmpty(vipEndTime)) {
                return false;
            }
            return (Long.parseLong(vipEndTime) > (System.currentTimeMillis() / 1000));
        }
        return false;
    }

    public static void saveUserCoin(PointDto.DataBean data) {
        String info = new Gson().toJson(data);
        SharePreferencesUtil.setStringSharePreferences(App.getContext(), DataInter.KEY.USER_COIN_INFO, info);
    }

    public static String getUserVipEndTime() {
        String userId = getUserId();
        String coinInfo = SharePreferencesUtil.getStringSharePreferences(App.getContext(), DataInter.KEY.USER_COIN_INFO, null);
        if (TextUtils.isEmpty(coinInfo) || TextUtils.isEmpty(userId)) {
            return "";
        }
        PointDto.DataBean dataBean = new Gson().fromJson(coinInfo, PointDto.DataBean.class);
        if (userId.equals(dataBean.getUser_id())) {
            return dataBean.getUser_end_time();
        }
        return "";
    }

    public static String getUserCoin() {
        String userId = getUserId();
        String coinInfo = SharePreferencesUtil.getStringSharePreferences(App.getContext(), DataInter.KEY.USER_COIN_INFO, null);
        if (TextUtils.isEmpty(coinInfo) || TextUtils.isEmpty(userId)) {
            return "";
        }
        PointDto.DataBean dataBean = new Gson().fromJson(coinInfo, PointDto.DataBean.class);
        if (userId.equals(dataBean.getUser_id())) {
            return dataBean.getUser_points();
        }
        return null;
    }

    /**
     * 兑换会员后，更新本地金币信息
     *
     * @param data
     */
    public static void updateUserCoin(BuyVipDto data) {
        String userId = getUserId();
        String coinInfo = SharePreferencesUtil.getStringSharePreferences(App.getContext(), DataInter.KEY.USER_COIN_INFO, null);
        if (TextUtils.isEmpty(coinInfo) || TextUtils.isEmpty(userId)) {
            return;
        }
        Gson gson = new Gson();
        PointDto.DataBean dataBean = gson.fromJson(coinInfo, PointDto.DataBean.class);
        if (dataBean != null && !TextUtils.isEmpty(dataBean.getUser_id()) && userId.equals(dataBean.getUser_id())) {
            dataBean.setUser_points(data.getData().getItem_data().getUser_points());
            dataBean.setGroup_id(data.getData().getItem_data().getGroup_id());
            dataBean.setUser_end_time(data.getData().getItem_data().getUser_end_time());
            saveUserCoin(dataBean);
        }
    }

    /**
     * 更新用户头像
     *
     * @param portraitThumb
     */
    public static void updateUserIcon(String portraitThumb) {
        String userInfo = UserUtil.getUserInfo(App.getContext());
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(userInfo)) {
            LoginDto.DataBean dataBean = gson.fromJson(userInfo, LoginDto.DataBean.class);
            if (dataBean != null) {
                dataBean.setUser_portrait_thumb(portraitThumb);
                String newUserInfo = gson.toJson(dataBean);
                saveUserInfo(App.getContext(), dataBean, newUserInfo);
            }
        }
    }

    public static String getUserIcon() {
        String userInfo = UserUtil.getUserInfo(App.getContext());
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(userInfo)) {
            LoginDto.DataBean dataBean = gson.fromJson(userInfo, LoginDto.DataBean.class);
            if (dataBean != null) {
                String portrait_thumb = dataBean.getUser_portrait_thumb();
                return portrait_thumb;
            }
        }
        return "";
    }
}
