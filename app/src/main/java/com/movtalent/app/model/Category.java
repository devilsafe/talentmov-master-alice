package com.movtalent.app.model;

import android.view.View;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class Category {

    private String catName;

    public String getCatName() {
        return catName;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    private View.OnClickListener clickListener;

    public Category(String catName, View.OnClickListener clickListener) {
        this.catName = catName;
        this.clickListener = clickListener;
    }
}
