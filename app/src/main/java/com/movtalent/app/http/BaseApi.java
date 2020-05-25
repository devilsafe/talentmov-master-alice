package com.movtalent.app.http;


import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.movtalent.app.App;
import com.movtalent.app.util.NetUtil;
import com.movtalent.app.util.ToastUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * creator huangyong
 * createTime 2019/2/24 下午10:24
 * path dev.zx.com.supermovie.http
 * description:
 */
public class BaseApi {

    // 创建网络接口请求实例
    public static <T> T createApi(Class<T> service) {
        final String url = UrlConfig.BaseUrl;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(ApiManager.getClientInstance())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    // 创建网络接口请求实例
    public static <T> T createApi(Class<T> service, String BaseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(ApiManager.getClientInstance())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    // 创建XML网络接口请求实例
    public static <T> T createXmlApi(Class<T> service, String BaseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(ApiManager.getClientInstance())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }


    //执行网络请求
    public static <T> void request(Observable<T> observable,
                                   final IResponseListener<T> listener) {
//        if (isWifiProxy(App.getContext())) {
//            ToastUtil.showMessage("请关闭代理后重试");
//            return;
//        }
        if (!NetUtil.isNetworkAvailable(App.getContext())) {
            ToastUtil.showMessage("网络不可用,请检查网络");
            if (listener != null) {
                listener.onFail();
            }
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   if (listener != null) {
                                       listener.onFail();
                                   }
                               }

                               @Override
                               public void onComplete() {

                               }

                               @Override
                               public void onSubscribe(Disposable disposable) {

                               }

                               @Override
                               public void onNext(T data) {
                                   if (listener != null) {
                                       listener.onSuccess(data);
                                   }
                               }
                           }
                );


    }

    /*
     * 判断设备 是否使用代理上网
     * */
    private static boolean isWifiProxy(Context context) {

        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;

        String proxyAddress;

        int proxyPort;

        if (IS_ICS_OR_LATER) {

            proxyAddress = System.getProperty("http.proxyHost");

            String portStr = System.getProperty("http.proxyPort");

            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));

        } else {

            proxyAddress = android.net.Proxy.getHost(context);

            proxyPort = android.net.Proxy.getPort(context);

        }

        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);

    }

    public interface IResponseListener<T> {

        void onSuccess(T data);

        void onFail();
    }
}