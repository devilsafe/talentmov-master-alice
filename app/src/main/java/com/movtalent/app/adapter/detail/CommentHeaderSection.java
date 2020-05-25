package com.movtalent.app.adapter.detail;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class CommentHeaderSection {

    public CommentHeaderSectionViewBinder.OnHeadListener getHeadListener() {
        return headListener;
    }

    private CommentHeaderSectionViewBinder.OnHeadListener headListener;

    public CommentHeaderSection(CommentHeaderSectionViewBinder.OnHeadListener headListener) {
        this.headListener = headListener;
    }
}