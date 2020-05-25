package com.movtalent.app.presenter;

import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.VideoListDto;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.iview.IDetailView;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class DetailPresenter {

    private IDetailView iDetailView;

    public DetailPresenter(IDetailView iDetailView) {
        this.iDetailView = iDetailView;
    }

    public void getDetail(int vodId) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getMovDetail(vodId), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {
                        if (data != null) {
                            ArrayList<CommonVideoVo> videoVos = CommonVideoVo.from(data);
                            iDetailView.loadDone(videoVos.get(0));
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
