package com.example.xiaohantv.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.xiaohantv.tools.WebSetting;


public class WebAdapter implements WebSetting {

    WebView webView;


    public WebAdapter(WebView webView) {
        this.webView = webView;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initWeb(String url) {
        webView.loadUrl(url);
        // 取消Vertical ScrollBar显示
        webView.setVerticalScrollBarEnabled(false);
        // 取消Horizontal ScrollBar显示
        webView.setHorizontalScrollBarEnabled(false);
        //去掉超链接
        webView.setFocusable(false);
        //硬件加速
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        // 获取焦点
        webView.requestFocus();
        // 这里如果设置false, 则点击h5页面中的输入框时不能唤起软键盘
        webView.setEnabled(true);
        //设置不用系统浏览器打开,直接显示在当前Webview
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void state() {
        webView.getUrl();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void mWebSettings() {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //缩放操作
        //支持缩放，默认为true。是下面那个的前提。
        webSettings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);
        //其他细节操作
        //webview中缓存
        //  webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 应用可以有缓存
        webSettings.setAppCacheEnabled(true);
        // 优先使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //布局算法
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        //加载视频
        webSettings.setDomStorageEnabled(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        //显示图片
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    public int mWebViewClient() {

        final int[] code = new int[100];
        int i1 = 0;
        //设置WebChromeClient类
        webView.setWebChromeClient(new WebChromeClient() {
            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                for (int i = 1; i < newProgress; i++) {
                    int i1 = code[i];
                }
            }
        });
        return i1;
    }

    @Override
    public void myWebViewClient(Context context) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {

                WebView.HitTestResult hit = view.getHitTestResult();
                //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
                if (TextUtils.isEmpty(hit.getExtra()) || hit.getType() == 0) {
                    //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
                    Log.e("重定向", "重定向: " + hit.getType() + " && EXTRA（）" + hit.getExtra() + "------");
                    Log.e("重定向", "GetURL: " + view.getUrl() + "\n" + "getOriginalUrl()" + view.getOriginalUrl());
                    Log.d("重定向", "URL: " + url);
                }
                //加载的url是http/https协议地址
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view.loadUrl(url);
                    //返回false表示此url默认由系统处理,url未加载完成，会继续往下走
                    return false;

                } else { //加载的url是自定义协议地址
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }

            }
        });
    }

    @Override
    public void setToast(Context context) {
        //设置WebViewClient类
        webView.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Toast.makeText(context, "小韩不要急，正在加载zhong~~~", Toast.LENGTH_SHORT).show();
            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                Toast.makeText(context, "加载完成了，可以追剧lo~~~", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
