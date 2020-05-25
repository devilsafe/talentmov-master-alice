package com.movtalent.app.presenter;

import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.HomeLevelDto;
import com.movtalent.app.model.dto.VideoListDto;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.iview.IHomeView;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class HomePresenter {

    private IHomeView iHomeView;

    public HomePresenter(IHomeView iHomeView) {
        this.iHomeView = iHomeView;
    }


    public void getHomeLevel(){
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getHomeLevel(), new BaseApi.IResponseListener<HomeLevelDto>() {
                    @Override
                    public void onSuccess(HomeLevelDto data) {
                        iHomeView.loadDone(data);
                    }

                    @Override
                    public void onFail() {
                        iHomeView.loadError();
                    }
                }
        );
    }

    public void getHomeLevel(int level,int page,int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getHomeLevelAll(level,page,limit), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {
                        iHomeView.loadDone(CommonVideoVo.from(data));
                    }

                    @Override
                    public void onFail() {
                        iHomeView.loadError();
                    }
                }
        );
    }

    public void getHomeLevelMore(int level,int page,int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getHomeLevelAll(level,page,limit), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {
                        iHomeView.loadMore(CommonVideoVo.from(data));
                    }

                    @Override
                    public void onFail() {
                        iHomeView.loadError();
                    }
                }
        );
    }

    /**
     * 获取banner
     * @param level
     * @param page
     * @param limit
     */
    public void getHomeBannerLevel(int level, int page, int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getHomeLevelAll(level,page,limit), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {
                        iHomeView.loadBanner(CommonVideoVo.from(data));
                    }

                    @Override
                    public void onFail() {
                        iHomeView.loadError();
                    }
                }
        );
    }
}
