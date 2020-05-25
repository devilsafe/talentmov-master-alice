package com.movtalent.app.adapter.setting;

/**
 * @author huangyong
 * createTime 2019-09-18
 */
public class TextItemSection {

    private String itemText;

    public String getItemText() {
        return itemText;
    }

    public TextItemSectionViewBinder.OnItemClickListenr getClickListenr() {
        return clickListenr;
    }

    private TextItemSectionViewBinder.OnItemClickListenr clickListenr;

    public TextItemSection(String itemText, TextItemSectionViewBinder.OnItemClickListenr clickListenr) {
        this.itemText = itemText;
        this.clickListenr = clickListenr;
    }
}