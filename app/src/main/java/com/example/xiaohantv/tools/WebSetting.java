package com.example.xiaohantv.tools;

import android.content.Context;


/**
 * @author 小志
 * @date 2022-11-11
 */
public interface WebSetting {
    /**
     * 初始化webView
     */
    void initWeb(String url);

    /**
     * 设置web状态
     */
    void state();


    /**
     * 设置WebSettings
     */
    void mWebSettings();

    /**
     * 设置 WebViewClient
     */
    int mWebViewClient();

    /**
     * 禁止弹窗
     */
    void myWebViewClient(Context context);

    /**
     * 弹出消息
     * */
    void setToast(Context context);

}
