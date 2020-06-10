package com.movtalent.app.presenter;

import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.TopicRDto;
import com.movtalent.app.model.dto.VideoListDto;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.iview.ITopicView;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class TopicPresenter {

    private final ITopicView iTopicView;

    public TopicPresenter(ITopicView iTopicView) {
        this.iTopicView = iTopicView;
    }


    public void getTopicRList(int page, int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getTopicRoot(page, limit), new BaseApi.IResponseListener<TopicRDto>() {
                    @Override
                    public void onSuccess(TopicRDto data) {
                        iTopicView.loadDone(data);
                    }

                    @Override
                    public void onFail() {
                        iTopicView.loadError();
                    }
                }
        );
    }

    public void getTopicRListMore(int page, int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getTopicRoot(page, limit), new BaseApi.IResponseListener<TopicRDto>() {
                    @Override
                    public void onSuccess(TopicRDto data) {
                        iTopicView.loadMore(data);
                    }

                    @Override
                    public void onFail() {
                        iTopicView.loadError();
                    }
                }
        );
    }

    public void getTopicList(int topicId) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getTopicList(topicId), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {
                        iTopicView.loadDone(CommonVideoVo.from(data));
                    }

                    @Override
                    public void onFail() {
                        iTopicView.loadError();
                    }
                }
        );
    }

}
