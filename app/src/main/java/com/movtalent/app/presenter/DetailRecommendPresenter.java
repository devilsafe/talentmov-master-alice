package com.movtalent.app.presenter;

import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.VideoListDto;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.iview.IRecView;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class DetailRecommendPresenter {

    private IRecView iDetailView;

    public DetailRecommendPresenter(IRecView iDetailView) {
        this.iDetailView = iDetailView;
    }

    public void getRecommend(int typeId) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getRecommend(typeId), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {
                        if (data != null) {
                            ArrayList<CommonVideoVo> videoVos = CommonVideoVo.from(data);
                            iDetailView.loadRecmmedDone(videoVos);
                        } else {
                            iDetailView.loadEmpty();
                        }

                    }

                    @Override
                    public void onFail() {
                        iDetailView.loadError();
                    }
                }
        );
    }
}
