package com.movtalent.app.presenter;


import android.content.Intent;
import com.lib.common.util.DataInter;
import com.movtalent.app.App;
import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.PointDto;
import com.movtalent.app.presenter.iview.ICoin;
import com.movtalent.app.util.UserUtil;

/**
 * @author huangyong
 * createTime 2019-09-19
 */
public class CoinPresenter {

    private final ICoin iCoin;

    public CoinPresenter(ICoin iCoin) {
        this.iCoin = iCoin;
    }

    public void inCreaseCoin(String userToken) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .addUserCoin(userToken), new BaseApi.IResponseListener<PointDto>() {
                    @Override
                    public void onSuccess(PointDto data) {
                        Intent intent = new Intent();
                        intent.setAction(DataInter.KEY.ACTION_REFRESH_COIN);
                        App.getContext().sendBroadcast(intent);
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }

    public void getCoin(String userToken) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getUserCoin(userToken), new BaseApi.IResponseListener<PointDto>() {
                    @Override
                    public void onSuccess(PointDto data) {
                        if (iCoin!=null){
                            iCoin.updateCoin(data.getData().getUser_points());
                            UserUtil.saveUserCoin(data.getData());
                        }
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }
}
