package com.movtalent.app.model.vo;


import com.movtalent.app.model.dto.CommentDto;
import com.movtalent.app.model.dto.CommentGDto;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class CommentVo {

    private String userName;
    private int commentId;
    private int commentPid;
    private String commentTime;
    private int commentUp;
    private int commentDown;
    private int commentReport;
    private int userId;
    private String commentContent;
    private int vodId;
    private String userIcon;


    private CommentVo replyVo;

    public CommentVo getReplyVo() {
        return replyVo;
    }

    public void setReplyVo(CommentVo replyVo) {
        this.replyVo = replyVo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentPid() {
        return commentPid;
    }

    public void setCommentPid(int commentPid) {
        this.commentPid = commentPid;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public int getCommentUp() {
        return commentUp;
    }

    public void setCommentUp(int commentUp) {
        this.commentUp = commentUp;
    }

    public int getCommentDown() {
        return commentDown;
    }

    public void setCommentDown(int commentDown) {
        this.commentDown = commentDown;
    }

    public int getCommentReport() {
        return commentReport;
    }

    public void setCommentReport(int commentReport) {
        this.commentReport = commentReport;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getVodId() {
        return vodId;
    }

    public void setVodId(int vodId) {
        this.vodId = vodId;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public static ArrayList<CommentVo> from(CommentGDto commentDto) {
        ArrayList<CommentVo> vos = new ArrayList<>();
        for (CommentGDto.DataBean.ItemBean dataBean : commentDto.getData().getItem()) {
            CommentGDto.DataBean.ItemBean.CommentBean comment = dataBean.getComment();
            CommentVo vo = new CommentVo();
            vo.setCommentContent(comment.getComment_content());
            vo.setCommentDown(Integer.parseInt(comment.getComment_down()));
            vo.setCommentUp(Integer.parseInt(comment.getComment_up()));
            vo.setCommentId(Integer.parseInt(comment.getComment_id()));
            vo.setCommentPid(Integer.parseInt(comment.getComment_pid()));
            vo.setCommentReport(Integer.parseInt(comment.getComment_report()));
            vo.setUserIcon(dataBean.getUser_icon());
            vo.setUserId(Integer.parseInt(comment.getUser_id()));
            vo.setUserName(comment.getComment_name());
            vo.setVodId(Integer.parseInt(comment.getComment_rid()));
            vo.setCommentTime(comment.getComment_time());

            CommentGDto.DataBean.ItemBean.ReplyBean reply = dataBean.getReply();
            if (reply != null) {
                CommentVo replyVo = new CommentVo();
                replyVo.setCommentContent(reply.getComment_content());
                replyVo.setCommentDown(Integer.parseInt(reply.getComment_down()));
                replyVo.setCommentUp(Integer.parseInt(reply.getComment_up()));
                replyVo.setCommentId(Integer.parseInt(reply.getComment_id()));
                replyVo.setCommentPid(Integer.parseInt(reply.getComment_pid()));
                replyVo.setUserIcon(dataBean.getRep_icon());
                replyVo.setCommentReport(Integer.parseInt(reply.getComment_report()));
                replyVo.setUserId(Integer.parseInt(reply.getUser_id()));
                replyVo.setUserName(reply.getComment_name());
                replyVo.setVodId(Integer.parseInt(reply.getComment_rid()));
                replyVo.setCommentTime(reply.getComment_time());
                vo.setReplyVo(replyVo);
            }
            vos.add(vo);
        }
        return vos;
    }

    public static CommentVo from(CommentDto commentDto, CommentVo pVo) {
        CommentVo vo = new CommentVo();
        CommentDto.DataBean dataBean = commentDto.getData();
        vo.setCommentContent(dataBean.getComment_content());
        vo.setCommentDown(0);
        vo.setCommentUp(0);
        vo.setCommentId(Integer.parseInt(dataBean.getId()));
        vo.setCommentPid(dataBean.getComment_pid());
        vo.setCommentReport(0);
        vo.setUserId(dataBean.getUser_id());
        vo.setUserName(dataBean.getComment_name());
        vo.setVodId(dataBean.getComment_rid());
        vo.setCommentTime(String.valueOf(dataBean.getComment_time()));
        if (pVo != null) {
            vo.setReplyVo(pVo);
        }
        return vo;
    }
}
