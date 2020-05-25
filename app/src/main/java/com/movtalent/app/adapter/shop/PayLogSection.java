package com.movtalent.app.adapter.shop;

/**
 * @author huangyong
 * createTime 2019-09-25
 */
public class PayLogSection {

    private String coinNum;
    private String payTime;
    public String getCoinNum() {
        return coinNum;
    }

    public String getPayTime() {
        return payTime;
    }

    public String getVipType() {
        return vipType;
    }

    private String vipType;

    public PayLogSection(String coinNum, String payTime, String vipType) {
        this.coinNum = coinNum;
        this.payTime = payTime;
        this.vipType = vipType;
    }
}