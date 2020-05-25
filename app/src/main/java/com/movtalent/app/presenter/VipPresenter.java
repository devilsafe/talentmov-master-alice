package com.movtalent.app.presenter;

import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.BuyVipDto;
import com.movtalent.app.model.dto.PayLogDto;
import com.movtalent.app.model.vo.VipVo;
import com.movtalent.app.presenter.iview.IVipView;
import com.movtalent.app.util.ToastUtil;
import com.movtalent.app.util.UserUtil;

/**
 * @author huangyong
 * createTime 2019-09-25
 */
public class VipPresenter {

    private IVipView iVipView;

    public VipPresenter(IVipView iVipView) {
        this.iVipView = iVipView;
    }

    public void buyVip(int vipType){
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .buyVip(UserUtil.getUserId(), vipType), new BaseApi.IResponseListener<BuyVipDto>() {
                    @Override
                    public void onSuccess(BuyVipDto data) {
                        if (data.getData().getItem_code()==0){
                            UserUtil.updateUserCoin(data);
                            iVipView.payDone(VipVo.from(data));
                            ToastUtil.showMessage("会员升级成功");
                        }else {
                            ToastUtil.showMessage("金币余额不足，快去赚金币吧");
                        }
                    }

                    @Override
                    public void onFail() {
                        iVipView.loadError();
                    }
                }
        );
    }

    public void getPayHistory() {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getPayLog(UserUtil.getUserId()), new BaseApi.IResponseListener<PayLogDto>() {
                    @Override
                    public void onSuccess(PayLogDto data) {
                        if (data.getData().getPay_log()!=null&&data.getData().getPay_log().size()>0){
                            iVipView.loadLogDone(data);
                        }
                    }

                    @Override
                    public void onFail() {
                        iVipView.loadError();
                    }
                }
        );
    }
}
