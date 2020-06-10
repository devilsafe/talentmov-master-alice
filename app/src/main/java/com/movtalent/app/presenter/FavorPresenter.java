package com.movtalent.app.presenter;

import android.widget.ImageView;
import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.FavorDto;
import com.movtalent.app.model.dto.VideoListDto;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.iview.IFavor;
import com.movtalent.app.util.UserUtil;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class FavorPresenter {

    private final IFavor iFavor;

    public FavorPresenter(IFavor iFavor) {
        this.iFavor = iFavor;
    }

    public void doFavor(String uid, int vodId) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .doFavor(uid, vodId), new BaseApi.IResponseListener<FavorDto>() {
                    @Override
                    public void onSuccess(FavorDto data) {
                        iFavor.favorDone();
                    }

                    @Override
                    public void onFail() {
                        iFavor.loadError();
                    }
                }
        );
    }

    public void cancelFavor(String uid, int vodId) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .cancelFavor(uid, vodId), new BaseApi.IResponseListener<FavorDto>() {
                    @Override
                    public void onSuccess(FavorDto data) {
                        iFavor.cancelDone();
                    }

                    @Override
                    public void onFail() {
                        iFavor.loadError();
                    }
                }
        );
    }

    public void getHaveFavor(String uid, int vodId) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getUserFavorOrNot(uid, vodId), new BaseApi.IResponseListener<FavorDto>() {
                    @Override
                    public void onSuccess(FavorDto data) {
                        iFavor.loadHaveDone(data.getData().getCode() == 0);
                    }

                    @Override
                    public void onFail() {
                        iFavor.loadError();
                    }
                }
        );
    }

    public void getAllFavor(String uid) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getUserFavor(uid), new BaseApi.IResponseListener<VideoListDto>() {
                    @Override
                    public void onSuccess(VideoListDto data) {
                        ArrayList<CommonVideoVo> videoVos = CommonVideoVo.from(data);
                        iFavor.loadDone(videoVos);
                    }

                    @Override
                    public void onFail() {
                        iFavor.loadError();
                    }
                }
        );
    }

    public void toggleFavor(ImageView favor, int vodId, boolean selected) {
        String userId = UserUtil.getUserId();
        if (selected) {
            favor.setSelected(false);
            cancelFavor(userId, vodId);
        } else {
            favor.setSelected(true);
            doFavor(userId, vodId);
        }
    }
}
