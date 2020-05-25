package com.movtalent.app.model.vo;

import java.io.Serializable;

/**
 * @author huangyong
 * createTime 2019-08-30
 */
public class ItemTab implements Serializable {
    /**
     * 拖动的是这个
     */
    private String itemName;
    private String typeId;
    /**
     * 为了方便后期排序，定义个tag
     */
    private String sectionTag;

    public String getSectionTag() {
        return sectionTag;
    }

    public void setSectionTag(String sectionTag) {
        this.sectionTag = sectionTag;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
