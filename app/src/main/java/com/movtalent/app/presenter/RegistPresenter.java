package com.movtalent.app.presenter;

import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.LoginDto;
import com.movtalent.app.presenter.iview.IUserView;
import com.movtalent.app.util.Md5Utils;
import com.movtalent.app.util.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huangyong
 * createTime 2019-09-17
 */
public class RegistPresenter {
    private IUserView iLoginView;

    public RegistPresenter(IUserView iLoginView) {
        this.iLoginView = iLoginView;
    }


    public void regist(String name, String pass, String mail) {
        String md5Pass = Md5Utils.stringToMD5(pass);

        if (mail.length() < 8) {
            ToastUtil.showMessage("邮箱不得少于8位");
            return;
        }

        if (name.length() < 6) {
            ToastUtil.showMessage("用户名不得少于6个字符");
            return;
        }

        if (!emailFormat(mail)) {
            ToastUtil.showMessage("邮箱格式不合法");
            return;
        }
        if (pass.length() < 6) {
            ToastUtil.showMessage("密码不能少于6个字符");
            return;
        }
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .regist(name, md5Pass, mail), new BaseApi.IResponseListener<LoginDto>() {
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

    public static boolean emailFormat(String email) {
        boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }
}
