package com.movtalent.app.model.vo;

import com.movtalent.app.model.dto.BuyVipDto;

/**
 * @author huangyong
 * createTime 2019-09-25
 */
public class VipVo {

    private String endTime;
    private String vipLevel;
    private String coinNum;

    public static VipVo from(BuyVipDto data) {
        VipVo vipVo = new VipVo();
        vipVo.setCoinNum(data.getData().getItem_data().getUser_points());
        vipVo.setEndTime(data.getData().getItem_data().getUser_end_time());
        vipVo.setVipLevel(data.getData().getItem_data().getGroup_id());
        return vipVo;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(String coinNum) {
        this.coinNum = coinNum;
    }
}
