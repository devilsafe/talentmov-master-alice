package com.movtalent.app.adapter.detail;

import com.movtalent.app.model.vo.CommentVo;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class CommentContainerSection {

    public ArrayList<CommentVo> getVos() {
        return vos;
    }

    public CommentContainerSectionViewBinder.OnCommentLoadListener getCommentLoadListener() {
        return commentLoadListener;
    }

    public void setVos(ArrayList<CommentVo> vos) {
        this.vos = vos;
    }

    private ArrayList<CommentVo> vos;


    private final CommentContainerSectionViewBinder.OnCommentLoadListener commentLoadListener;

    public CommentContainerSection(ArrayList<CommentVo> vos, CommentContainerSectionViewBinder.OnCommentLoadListener commentLoadListener) {
        this.commentLoadListener = commentLoadListener;
        this.vos = vos;
    }
}