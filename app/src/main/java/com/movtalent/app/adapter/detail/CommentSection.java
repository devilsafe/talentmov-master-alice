package com.movtalent.app.adapter.detail;

import com.movtalent.app.model.vo.CommentVo;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class CommentSection {
    public CommentVo getCommentVo() {
        return commentVo;
    }

    private CommentVo commentVo;

    public CommentSectionViewBinder.OnReplyListener getReplyListener() {
        return replyListener;
    }

    private CommentSectionViewBinder.OnReplyListener replyListener;


    public CommentSection(CommentVo commentVo,CommentSectionViewBinder.OnReplyListener replyListener) {
        this.replyListener = replyListener;
        this.commentVo = commentVo;
    }


}