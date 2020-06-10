package com.movtalent.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.IconDto;
import com.movtalent.app.util.ToastUtil;
import com.movtalent.app.util.UserUtil;

/**
 * @author huangyong
 * createTime 2019-10-10
 */
public class IconViewModel extends ViewModel {

    private MutableLiveData<IconDto> loginResult = new MutableLiveData<>();


    public LiveData<IconDto> getIconResult() {
        return loginResult;
    }

    public void changeIcon(int index) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .changeIcon(UserUtil.getUserId(), String.valueOf(index)), new BaseApi.IResponseListener<IconDto>() {
                    @Override
                    public void onSuccess(IconDto data) {
                        if (loginResult != null) {
                            loginResult.postValue(data);
                        }
                    }

                    @Override
                    public void onFail() {
                        ToastUtil.showMessage("更新头像失败");
                    }
                }
        );
    }
}
