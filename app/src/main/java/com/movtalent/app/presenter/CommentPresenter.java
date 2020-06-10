package com.movtalent.app.presenter;

import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.CommentDDto;
import com.movtalent.app.model.dto.CommentDto;
import com.movtalent.app.model.dto.CommentGDto;
import com.movtalent.app.model.vo.CommentVo;
import com.movtalent.app.presenter.iview.ICView;
import com.movtalent.app.util.ToastUtil;
import com.movtalent.app.util.UserUtil;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class CommentPresenter {
    private final ICView icView;

    public CommentPresenter(ICView icView) {
        this.icView = icView;
    }


    public void publishComment(String CommentContent, int vodId, CommentVo commentVo, int commentPid) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .newComment(UserUtil.getUserId(), UserUtil.getUserName(), CommentContent, vodId, commentPid), new BaseApi.IResponseListener<CommentDto>() {
                    @Override
                    public void onSuccess(CommentDto data) {
                        icView.publishDone(CommentVo.from(data,commentVo));
                    }

                    @Override
                    public void onFail() {

                    }
                }
        );
    }

    public void deleteComment(int commentId) {

        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .deleteComment(UserUtil.getUserId(), commentId), new BaseApi.IResponseListener<CommentDDto>() {
                    @Override
                    public void onSuccess(CommentDDto data) {
                        if (data.getData() == 1) {
                            ToastUtil.showMessage("删除成功");
                        }
                    }

                    @Override
                    public void onFail() {
                        ToastUtil.showMessage("删除失败");
                    }
                }
        );
    }

    public void getCommentByVodId(int vodId, int page, int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getComment(vodId, page, limit), new BaseApi.IResponseListener<CommentGDto>() {
                    @Override
                    public void onSuccess(CommentGDto data) {
                        ArrayList<CommentVo> commentVos = CommentVo.from(data);
                        icView.loadAllDone(commentVos);
                    }

                    @Override
                    public void onFail() {
                        ToastUtil.showMessage("评论加载失败");
                    }
                }
        );
    }

    public void getCommentByVodIdMore(int vodId, int page, int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getComment(vodId, page, limit), new BaseApi.IResponseListener<CommentGDto>() {
                    @Override
                    public void onSuccess(CommentGDto data) {
                        if (data.getData() != null && data.getData().getItem().size() > 0) {
                            ArrayList<CommentVo> commentVos = CommentVo.from(data);
                            icView.loadMoreDone(commentVos);
                        }
                    }

                    @Override
                    public void onFail() {
                        ToastUtil.showMessage("评论加载失败");
                    }
                }
        );
    }
}
