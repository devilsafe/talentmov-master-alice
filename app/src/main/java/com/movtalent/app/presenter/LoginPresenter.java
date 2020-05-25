package com.movtalent.app.presenter;

import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.LoginDto;
import com.movtalent.app.presenter.iview.IUserView;
import com.movtalent.app.util.Md5Utils;

/**
 * @author huangyong
 * createTime 2019-09-17
 */
public class LoginPresenter {
    private IUserView iLoginView;

    public LoginPresenter(IUserView iLoginView) {
        this.iLoginView = iLoginView;
    }


    /**
     * 登录
     * @param name
     * @param pass
     */
    public void login(String name,String pass){

        String md5Pass = Md5Utils.stringToMD5(pass);
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .login(name,md5Pass), new BaseApi.IResponseListener<LoginDto>() {
                    @Override
                    public void onSuccess(LoginDto data) {
                        iLoginView.loadDone(data);
                    }

                    @Override
                    public void onFail() {
                        iLoginView.loadError();
                    }
                }
        );
    }
}
