package com.movtalent.app.adapter.vip;


/**
 * @author huangyong
 * createTime 2019-09-19
 */
public class VipShareItemSectionn {

    private String vipShareContent;
    private String vipShareTitle;
    private int vipShareIcon;

    public String getVipShareTitle() {
        return vipShareTitle;
    }

    public int getVipShareIcon() {
        return vipShareIcon;
    }

    public String getVipShareContent() {
        return vipShareContent;
    }

    public VipShareItemSectionnViewBinder.VipShareClickListener getVipShareClickListener() {
        return vipShareClickListener;
    }

    private VipShareItemSectionnViewBinder.VipShareClickListener vipShareClickListener;

    public VipShareItemSectionn(String vipShareTitle,int vipShareIcon,String vipShareContent, VipShareItemSectionnViewBinder.VipShareClickListener vipShareClickListener) {
        this.vipShareTitle = vipShareTitle;
        this.vipShareContent = vipShareContent;
        this.vipShareIcon = vipShareIcon;
        this.vipShareClickListener = vipShareClickListener;
    }
}