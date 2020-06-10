package com.movtalent.app.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.lib.common.util.DataInter;
import com.lxj.xpopup.core.BottomPopupView;
import com.movtalent.app.R;
import com.movtalent.app.presenter.CoinPresenter;
import com.movtalent.app.presenter.iview.ICoin;
import com.movtalent.app.util.ToastUtil;
import com.movtalent.app.util.UserUtil;
import kale.sharelogin.ShareListener;
import kale.sharelogin.ShareLoginLib;
import kale.sharelogin.content.ShareContent;
import kale.sharelogin.qq.QQPlatform;
import kale.sharelogin.weibo.WeiBoPlatform;
import kale.sharelogin.weixin.WeiXinPlatform;

/**
 * @author huangyong
 * createTime 2019-09-19
 */
public class BottomShareView extends BottomPopupView implements View.OnClickListener {

    private final Activity activity;
    private TextView cancel;
    private final ShareContent shareContent;

    public BottomShareView(@NonNull Context context, ShareContent shareContent) {
        super(context);
        this.activity = (Activity) context;
        this.shareContent = shareContent;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.share_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView weichat = findViewById(R.id.share_weichat);
        TextView weichatCir = findViewById(R.id.share_weichat_cir);
        TextView qq = findViewById(R.id.share_qq);
        TextView weibo = findViewById(R.id.share_weibo);
        cancel = findViewById(R.id.cancel);

        weichat.setOnClickListener(this);
        weichatCir.setOnClickListener(this);
        qq.setOnClickListener(this);
        weibo.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_weichat:
                ShareLoginLib.doShare(activity, WeiXinPlatform.FRIEND, shareContent, shareListener);
                dismiss();
                break;
            case R.id.share_weichat_cir:
                ShareLoginLib.doShare(activity, WeiXinPlatform.TIMELINE, shareContent, shareListener);
                dismiss();
                break;
            case R.id.share_qq:
                ShareLoginLib.doShare(activity, QQPlatform.FRIEND, shareContent, shareListener);
                dismiss();
                break;
            case R.id.share_weibo:
                ShareLoginLib.doShare(activity, WeiBoPlatform.TIMELINE, shareContent, shareListener);
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
            default:
                break;
        }
    }


    private final ShareListener shareListener = new ShareListener() {
        @Override
        public void onSuccess() {
            super.onSuccess();
            ToastUtil.showMessage("分享成功");
            String userToken = UserUtil.getUserToken(getContext());
            if (!TextUtils.isEmpty(userToken)) {
                //如果用户登录了，同步增加其积分，否则不与增加
                new CoinPresenter(iCoin).inCreaseCoin(userToken);
            }
        }

        @Override
        public void onError(String errorMsg) {
            super.onError(errorMsg);
            ToastUtil.showMessage(errorMsg);
        }
    };
    ICoin iCoin = new ICoin() {
        @Override
        public void updateCoin(String coin) {
            Intent intent = new Intent();
            intent.setAction(DataInter.KEY.ACTION_REFRESH_COIN);
            getContext().sendBroadcast(intent);
        }

        @Override
        public void loadError() {

        }

        @Override
        public void loadEmpty() {

        }
    };
}
