package com.movtalent.app.presenter;

import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.SearchWdDto;
import com.movtalent.app.model.dto.VideoListDto;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.iview.ISearch;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class SearchPresenter {

    private final ISearch iSearch;

    public SearchPresenter(ISearch iSearch) {
        this.iSearch = iSearch;
    }


    public void doSearch(String keyword) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getMovSearch(keyword), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {

                        if (data!=null&&data.getData()!=null&&data.getData().size()>0){
                            iSearch.loadDone(CommonVideoVo.from(data));
                        }else {
                            iSearch.loadEmpty();
                        }


                    }

                    @Override
                    public void onFail() {
                        iSearch.loadError();
                    }
                }
        );
    }

    public void getSearchWdRec() {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getMovRecSearchWord(), new BaseApi.IResponseListener<SearchWdDto>() {
                    @Override
                    public void onSuccess(SearchWdDto data) {
                        iSearch.loadWdDone(data);
                    }

                    @Override
                    public void onFail() {
                        iSearch.loadError();
                    }
                }
        );
    }
}
