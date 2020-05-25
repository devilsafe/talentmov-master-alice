package com.lib.common.util;

import android.content.Context;


/**
 * @author huangyong
 * createTime 2019/6/26
 */
public class AppUtils {

    public static boolean checkLogin(Context context){
       return SharePreferencesUtil.getBooleanSharePreferences(context, DataInter.KEY.IS_LOGIN, false);
    }
}
