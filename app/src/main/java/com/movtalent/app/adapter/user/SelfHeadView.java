package com.movtalent.app.adapter.user;

/**
 * @author huangyong
 * createTime 2019-09-16
 */
public class SelfHeadView {

    public String userIcon;
    public String userName;
    public String userCoins;


    public SelfHeadViewViewBinder.OnloginListener getOnloginListener() {
        return onloginListener;
    }

    private SelfHeadViewViewBinder.OnloginListener onloginListener;

    public String getUserIcon() {
        return userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCoins() {
        return userCoins;
    }

    public SelfHeadView(String userIcon, String userName, String userCoins, SelfHeadViewViewBinder.OnloginListener onloginListener) {
        this.userIcon = userIcon;
        this.userName = userName;
        this.userCoins = userCoins;
        this.onloginListener = onloginListener;
    }
}