package com.movtalent.app.third;


import com.movtalent.app.util.ToastUtil;
import kale.sharelogin.ShareListener;

/**
 * @author Jack Tony
 * @date 2015/10/23
 */
public class MyShareListener extends ShareListener {


    @Override
    public void onSuccess() {
        super.onSuccess();
        String result = "分享成功";
        ToastUtil.showMessage(result);
    }

    @Override
    public void onCancel() {
        super.onCancel();
        String result = "取消分享";
        ToastUtil.showMessage(result);
    }

    @Override
    public void onError(String msg) {
        super.onError(msg);
        String result = "分享失败，出错信息：" + msg;
        ToastUtil.showMessage(result);
    }

}