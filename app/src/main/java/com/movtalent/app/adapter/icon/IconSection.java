package com.movtalent.app.adapter.icon;


/**
 * @author huangyong
 * createTime 2019-10-10
 */
public class IconSection {

    public int iconIndex;

    public boolean isChosed() {
        return chosed;
    }

    public void setChosed(boolean chosed) {
        this.chosed = chosed;
    }

    private boolean chosed;
    public IconSectionViewBinder.OnItemClickListener clickListener;
    public IconSection(int iconIndex, IconSectionViewBinder.OnItemClickListener clickListener) {
        this.iconIndex = iconIndex;
        this.clickListener = clickListener;
    }
}