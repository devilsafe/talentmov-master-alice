package com.movtalent.app.http;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Huangyong on 2017/9/13.
 */

public class ApiManager {
    private static ApiService api;
    protected static final Object monitor = new Object();
    private static Retrofit retrofit;
    private static OkHttpClient client;

    private ApiManager() {
    }
    static {
        //添加应用拦截器
        client = new OkHttpClient.Builder()
                //添加应用拦截器
                .connectTimeout(35, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(UrlConfig.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }
    public static ApiService getRetrofitInstance(){

        if (api==null){
            synchronized (monitor){
                api = retrofit.create(ApiService.class);
            }
        }
        return api;
    }
    public static OkHttpClient getClientInstance(){
        return client;
    }

}
