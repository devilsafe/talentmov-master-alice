package com.movtalent.app.http;

import android.util.Log;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * 请求拦截器
 * Created by HY on 2017/4/12.
 */
public class HttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //打印请求链接
        String TAG_REQUEST = "requestrequest";
        Log.d(TAG_REQUEST,request.url().toString());
        Response response =  chain.proceed(request);
        Log.d(TAG_REQUEST, response.message());
        return response;
    }

}
