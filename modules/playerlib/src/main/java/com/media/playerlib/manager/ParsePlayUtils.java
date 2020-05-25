package com.media.playerlib.manager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.*;
import android.widget.LinearLayout;
import com.just.agentweb.AgentWeb;


/**
 * @author huangyong
 * createTime 2019-09-10
 */
public class ParsePlayUtils {

    private static volatile ParsePlayUtils playUtilsl;

    public static ParsePlayUtils getInstance() {
        if (playUtilsl == null) {
            synchronized (ParsePlayUtils.class) {
                if (playUtilsl == null) {
                    playUtilsl = new ParsePlayUtils();
                }
            }
        }
        return playUtilsl;
    }

    public void destroy() {
        if (findListener != null) {
            findListener = null;
        }
    }

    public interface OnPlayUrlFindListener {
        void onFindUrl(String url);
        void onError();
    }

    private OnPlayUrlFindListener findListener;

    public void toParsePlay(Activity activity, String url, ViewGroup viewGroup, OnPlayUrlFindListener findListener) {
        this.findListener = findListener;
        AgentWeb.with(activity).setAgentWebParent(viewGroup, new LinearLayout.LayoutParams(0,0)).useDefaultIndicator()
                .setWebViewClient(new MyWebViewClient())
                .createAgentWeb()
                .ready()
                .go("https://www.qianyicp.com/jiexi/?url=" + url);

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
        public void onPageFinished(WebView view, String url) {
           // view.stopLoading();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (findListener != null) {
                findListener.onError();
            }
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
            newLink(url);
            return super.shouldInterceptRequest(view, request);
        }


    }

    private String mlink;

    private void newLink(String url) {
        if (url.length() < 5) return;
        if (url.equals(mlink)) return;
        Log.d("我", "newLink: "+url);
        mlink = url;
        String type = "";
        if (findListener == null) {
            return;
        }
        try {
            type = parseSuffix(url);
            if (type.equals("m3u8")) {
                findListener.onFindUrl(url);
                Log.e("getplayurls", url + "");
            } else if (type.equals("mp4")) {
                findListener.onFindUrl(url);
                Log.e("getplayurls", url + "");
            } else if (type.equals("flv")) {
                findListener.onFindUrl(url);
                Log.e("getplayurls", url + "");
            } else if (type.equals("avi")) {
                Log.e("getplayurls", url + "");
                findListener.onFindUrl(url);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String parseSuffix(String url) {
        String con = url;
        String t1 = StringUtil.getTextRight(con, ".");
        if (t1.length() < 5) return t1;

        t1 = StringUtil.getLeftText(url, "?");
        if (t1 != null) con = t1;

        t1 = StringUtil.getTextRight(con, ".");
        if (t1 == null || t1.length() > 5) t1 = StringUtil.getTextRight(con, "/");

        con = t1;
        return con;
    }
}
