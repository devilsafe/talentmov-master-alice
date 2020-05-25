package com.movtalent.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;
import com.movtalent.app.App;
import com.movtalent.app.presenter.iview.IReport;
import com.movtalent.app.util.UserUtil;
import com.movtalent.app.view.LoginActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;


/**
 * @author huangyong
 * createTime 2019-07-09
 */
public class ReportPresenter {

    private Context context;
    private IReport iReport;

    public ReportPresenter(Context context, IReport iReport) {
        this.context = context;
        this.iReport = iReport;
    }

    /**
     * 上报反馈
     *
     * @param repContent
     * @param repContact
     * @param title
     * @param rpTpye
     */
    public void reportMsg(String repContent, String repContact, String title, int rpTpye) {
        String userToken = UserUtil.getUserToken(context);
        if (TextUtils.isEmpty(userToken)) {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put("repContent", repContent);
            params.put("repContact", repContact);
            params.put("title", title);
            params.put("rpTpye", String.valueOf(rpTpye));
            MobclickAgent.onEvent(App.getContext(), "report", params);
            iReport.loadDone();
        }

    }
}
