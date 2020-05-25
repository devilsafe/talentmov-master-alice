package com.movtalent.app.adapter.vip;

/**
 * @author huangyong
 * createTime 2019-09-18
 */
public class VipHeaderSection {

    private VipHeaderSectionViewBinder.OnVipClickListener clickListener;

    public void setCoinNum(String coinNum) {
        this.coinNum = coinNum;
    }

    private String coinNum;

    public VipHeaderSectionViewBinder.OnVipClickListener getClickListener() {
        return clickListener;
    }

    public String getCoinNum() {
        return coinNum;
    }

    public VipHeaderSection(VipHeaderSectionViewBinder.OnVipClickListener clickListener, String coinNum) {
        this.clickListener = clickListener;
        this.coinNum = coinNum;
    }
}