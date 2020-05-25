package com.movtalent.app.adapter.shop;


/**
 * @author huangyong
 * createTime 2019-09-26
 */
public class VipBrandSection {

    private int itemIcon;
    private String itemTitle;
    private String payDesc;

    public String getPayText() {
        return payText;
    }

    private String payText;

    public int getItemIcon() {
        return itemIcon;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getPayDesc() {
        return payDesc;
    }

    public VipBrandSectionViewBinder.OnClickItemPayListener getClickItemPayListener() {
        return clickItemPayListener;
    }

    private VipBrandSectionViewBinder.OnClickItemPayListener clickItemPayListener;

    public VipBrandSection(int itemIcon, String itemTitle, String payDesc, String payCoinNum, VipBrandSectionViewBinder.OnClickItemPayListener clickItemPayListener) {
        this.itemIcon = itemIcon;
        this.itemTitle = itemTitle;
        this.payDesc = payDesc;
        this.payText = payCoinNum;
        this.clickItemPayListener = clickItemPayListener;
    }
}