package com.movtalent.app.adapter.shop;


/**
 * @author huangyong
 * createTime 2019-09-26
 */
public class VipBrandSection {

    private final int itemIcon;
    private final String itemTitle;
    private final String payDesc;

    public String getPayText() {
        return payText;
    }

    private final String payText;

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

    private final VipBrandSectionViewBinder.OnClickItemPayListener clickItemPayListener;

    public VipBrandSection(int itemIcon, String itemTitle, String payDesc, String payCoinNum, VipBrandSectionViewBinder.OnClickItemPayListener clickItemPayListener) {
        this.itemIcon = itemIcon;
        this.itemTitle = itemTitle;
        this.payDesc = payDesc;
        this.payText = payCoinNum;
        this.clickItemPayListener = clickItemPayListener;
    }
}