package com.movtalent.app.presenter;

import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.VideoListDto;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.iview.IHomeView;

/**
 * @author huangyong
 * createTime 2019-09-14
 * 分类筛选页面
 * query
 */
public class CategoryPresenter {

    private IHomeView iHomeView;

    public CategoryPresenter(IHomeView iHomeView) {
        this.iHomeView = iHomeView;
    }

    public void getCategory(int typeId,String area,int year,int page,int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getCategory(typeId,area,year,page,limit), new BaseApi.IResponseListener<VideoListDto>() {
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

    public void getCategoryMore(int typeId,String area,int year,int page,int limit) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getCategory(typeId,area,year,page,limit), new BaseApi.IResponseListener<VideoListDto>() {
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
}
