package com.movtalent.app.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;
import android.webkit.*;
import android.widget.LinearLayout;
import com.lib.common.util.tool.StringUtil;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 幻陌 9.04
 * 
 */

public class ParseWebUrlHelper {
    private static ParseWebUrlHelper parseWebUrlHelper;
    private String webUrl;
    private Activity mAct;
    private WebView webView;
    private int timeOut = 20 * 1000;
    private OnParseWebUrlListener onParseListener;

    public static ParseWebUrlHelper getInstance() {
        if (parseWebUrlHelper == null) parseWebUrlHelper = new ParseWebUrlHelper();
        return parseWebUrlHelper;
    }

    public ParseWebUrlHelper init(Activity act, String url) {
        mAct = act;
        webUrl = url;
        ViewGroup mainView = (ViewGroup) mAct.findViewById(android.R.id.content);
        webView = new WebView(mAct);
        webView.setLayoutParams(new LinearLayout.LayoutParams(1, 1));
        mainView.addView(this.webView);
        initWebSettings();
        return this;
    }

    private void initWebSettings() {
        webView.clearFocus();
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBlockNetworkImage(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAllowContentAccess(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setBuiltInZoomControls(true);// 隐藏缩放按钮
        mWebSettings.setUseWideViewPort(true);// 可任意比例缩放
        mWebSettings.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        mWebSettings.setSavePassword(true);
        mWebSettings.setSaveFormData(true);// 保存表单数据
        mWebSettings.setTextZoom(100);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setSupportMultipleWindows(true);// 新加//我就是没有这一行，死活不出来。MD，硬是没有人写这一句！
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mWebSettings.setMediaPlaybackRequiresUserGesture(true);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            mWebSettings.setAllowFileAccessFromFileURLs(true);
            mWebSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setAppCachePath(mAct.getCacheDir().getAbsolutePath());
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setGeolocationDatabasePath(mAct.getDir("database", 0).getPath());
        mWebSettings.setGeolocationEnabled(true);
        CookieManager instance = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(mAct.getApplicationContext());
        }
        instance.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            instance.setAcceptThirdPartyCookies(webView, true);
        }
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        enabledCookie(webView);//启用cookie
    }

    public ParseWebUrlHelper setLoadUrl(String url){
        this.webUrl=url;
        return this;
    }
    public ParseWebUrlHelper startParse(){
        webView.loadUrl(this.webUrl);
        return this;
    }
    /*启用cookie*/
    private void enabledCookie(WebView web) {
        CookieManager instance = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(mAct);
        }
        instance.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            instance.setAcceptThirdPartyCookies(web, true);
        }
    }

    public ParseWebUrlHelper setOnParseListener(OnParseWebUrlListener onParseListener) {
        this.onParseListener = onParseListener;
        return this;
    }

    public void stop() {
        webView.stopLoading();
        webView.loadUrl("about:blank");
    }

    private class MyWebChromeClient extends WebChromeClient{
        @Override // 进度改变
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
        @Override // 得到网页标题
        public void onReceivedTitle(WebView view, String title) {
        }



    }
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //super.onPageStarted(view, url, favicon);
            //startConut();//加载超时处理
        }

        @Override
        public void onPageFinished(WebView view, String url){

        }

        @Override
        public void onReceivedError(WebView view, int errorCode,String description, String failingUrl){
            onParseListener.onError(description);
        }
        /*解决ssl证书问题*/
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

       /* @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            //newLink(url);
            return super.shouldInterceptRequest(view, url);
        }
*/
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            newLink(url,request.getRequestHeaders());
            return super.shouldInterceptRequest(view, request);
        }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if(request.getUrl().toString().startsWith("intent") || request.getUrl().toString().startsWith("youku")){
                return true;
            }else{
                return super.shouldOverrideUrlLoading(view, request);
            }
        }

    }
    /*解决webview加载超时问题*/
    private void startConut(){
        final Timer timer=new Timer();
        TimerTask timerTask=new TimerTask(){
            @Override
            public void run()
            {
                onParseListener.onError("解析视频超时，请检查网速或网络是否出现问题...");
                timer.cancel();
                timer.purge();
            }
        };
        timer.schedule(timerTask,timeOut,1);
    }

    public interface OnParseWebUrlListener {
        void onFindUrl(String url, String type, Map<String, String> hander);
        void onError(String errorMsg);
    }

    private String mlink;
    public void newLink(String url, Map<String, String> hander){
        if (url.length() < 5) return;
        if (url.equals(mlink)) return;

        mlink = url;
        String type = "";
        //onParseListener.onFindUrl(type);

        try {
            type = parseSuffix(url);
            if (type.equals("js") || type.equals("css") || type.equals("html") || type.equals("ico")){
                return;
            }

            if (type.equals("m3u8")){
                onParseListener.onFindUrl(url,"m3u8",hander);
            }else if (type.equals("mp4")){
                onParseListener.onFindUrl(url,"mp4",hander);
            }else if (type.equals("flv")){
                onParseListener.onFindUrl(url,"flv",hander);
            }else if (type.equals("avi")){
                onParseListener.onFindUrl(url,"avi",hander);
            }


            /*URL u = new URL(url);
            type = u.openConnection().getContentType();
            Log.i("eee-type",type + "|" + url);
            if (type !=null){
                String left = MxText.Text_getLeft(type,"/");
                switch (left.toString()){
                    case "video":
                        onParseListener.onFindUrl(url);
                        break;
                    case "application":
                        String right = MxText.Text_getRight(type,"/");
                        if (MxText.Text_getLeft(right,";") != null) right = MxText.Text_getLeft(right,";");
                        if (right.equals("vnd.apple.mpegurl") || right.equals("x-mpegURL")) onParseListener.onFindUrl(url);
                        Log.i("eee-type",type);
                        return;
                    default:
                        type = parseSuffix(url);
                        if (type.equals("m3u8") || type.equals("mp4"))
                            onParseListener.onFindUrl(url);
                        break;
                }
                return;
            }*/




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String parseSuffix(String url) {
        String con = url;
        String t1 = StringUtil.getTextRight(con,".");
        if (t1.length()< 5) return t1;

        t1 = StringUtil.getLeftText(url,"?");
        if (t1 != null) con = t1;

        t1 = StringUtil.getTextRight(con,".");
        if (t1 == null || t1.length() > 5) t1 = StringUtil.getTextRight(con,"/");


        con = t1;
        return con;
    }

    public String getMimeType(String fileUrl)throws IOException {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(fileUrl);
        return type;
    }
}