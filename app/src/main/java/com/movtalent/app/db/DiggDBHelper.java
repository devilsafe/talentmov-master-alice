package com.movtalent.app.db;

import com.lib.common.util.AppDbManager;
import com.lib.common.util.data.DiggCommentInfo;
import com.lib.common.util.room.DiggCommentDao;
import com.movtalent.app.App;
import com.movtalent.app.model.vo.CommentVo;
import com.movtalent.app.presenter.DiggPresenter;

import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-22
 */
public class DiggDBHelper {


    public static boolean dealDigg(CommentVo commentVo) {
        DiggCommentDao diggDao = AppDbManager.getInstance(App.getContext()).diggDao();
        List<DiggCommentInfo> infos = diggDao.getByCommentId(commentVo.getCommentId());
        if (infos != null && infos.size() > 0) {
            new DiggPresenter().cancelDigg(commentVo.getCommentId());
            diggDao.delete(infos.get(0));
            return true;
        }else {
            new DiggPresenter().diggComment(commentVo.getCommentId());
            DiggCommentInfo info = new DiggCommentInfo();
            info.status = 1;
            info.commentId=commentVo.getCommentId();
            diggDao.insert(info);
        }
        return false;
    }
}
