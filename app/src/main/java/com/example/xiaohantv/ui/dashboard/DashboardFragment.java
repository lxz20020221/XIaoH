package com.example.xiaohantv.ui.dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.xiaohantv.R;
import com.example.xiaohantv.Utils.SharedPreferencesUtils;
import com.example.xiaohantv.Utils.WebAdapter;
import com.example.xiaohantv.Utils.myFra;
import com.example.xiaohantv.tools.fraSendValue;


/**
 * @author 小志
 * @date 2022-11-10
 */
public class DashboardFragment extends myFra {

    private static final String TAG = "Dash";
    private WebView okWeb;
    private Button butOn;
    String urlHeader;
    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    public int of = 1;
    public int opn = 2;
    SharedPreferencesUtils sharedPreferencesUtils;
    private com.example.xiaohantv.tools.fraSendValue fraSendValue;
    private Button butIn;
    WebAdapter webAdapter;


    @Override
    public void onResume() {
        super.onResume();
        webAdapter = new WebAdapter(okWeb);
        webAdapter.mWebSettings();
        webAdapter.setToast(this.getActivity());
        sharedPreferencesUtils = new SharedPreferencesUtils(this.getActivity(), "XiaoHan");
        sharedPreferencesUtils.commitInstance();
        sharedPreferencesUtils.put("1线", "https://okjx.cc/?url=");
 //       sharedPreferencesUtils.put("2线", "https://api.jiexi.la/?url=");
//        sharedPreferencesUtils.put("3线", "https://www.pangujiexi.cc/jiexi.php?url=");
//        sharedPreferencesUtils.put("4线", "https://ckmov.ccyjjd.com/ckmov/?url=");
//        sharedPreferencesUtils.put("5线", "https://ckmov.ccyjjd.com/ckmov/?url=");
        urlHeader = String.valueOf(sharedPreferencesUtils.get("1线", ""));
        String url = urlHeader + sharedPreferencesUtils.get("视频链接", "");
        webAdapter.initWeb(url);
        webAdapter.myWebViewClient(getContext());
        Log.d(TAG, "onResume: " + url);
        okWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                fraSendValue.DashSendValue(of, okWeb);
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                fraSendValue.DashSendValue(opn, okWeb);
                hideCustomView();
            }
        });
    }

    @Override
    protected void init(View view) {
        initView(view);
    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_dashboard;
    }

    private void initView(View view) {
        okWeb = view.findViewById(R.id.ok_web);
        butOn = view.findViewById(R.id.but_on);
        butIn = view.findViewById(R.id.but_in);
        butOn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_notifications, null);
        });
        butIn.setOnClickListener(v -> {
            Toast.makeText(getContext(),"目前只有这一条线路",Toast.LENGTH_SHORT).show();
        });
//        butIn.setOnClickListener(v -> {
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
//            View dialog = View.inflate(getContext(), R.layout.dialong_adapter, null);
//            alertDialog.setView(dialog);
//            final AlertDialog dlg = alertDialog.create();
//            dlg.show();
//            dialog.findViewById(R.id.but_1).setOnClickListener(v1 -> {
//                urlHeader = String.valueOf(sharedPreferencesUtils.get("1线", ""));
//                String url = urlHeader + sharedPreferencesUtils.get("视频链接", "");
//                //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//                okWeb.loadUrl(url);
//                okWeb.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                        view.loadUrl(request.getUrl().toString());
//                        return true;
//                    }
//                });
//                Log.d(TAG, "initView: 1线"+url);
//                dlg.cancel();
//            });
//            dialog.findViewById(R.id.but_2).setOnClickListener(v1 -> {
//                urlHeader = String.valueOf(sharedPreferencesUtils.get("2线", ""));
//                String url = urlHeader + sharedPreferencesUtils.get("视频链接", "");
//                //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//                okWeb.loadUrl(url);
//                okWeb.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                        view.loadUrl(request.getUrl().toString());
//                        return true;
//                    }
//                });
//                Log.d(TAG, "initView: 2线"+url);
//                dlg.cancel();
//            });
//            dialog.findViewById(R.id.but_3).setOnClickListener(v1 -> {
//                urlHeader = String.valueOf(sharedPreferencesUtils.get("3线", ""));
//                String url = urlHeader + sharedPreferencesUtils.get("视频链接", "");
//                //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//                okWeb.loadUrl(url);
//                okWeb.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                        view.loadUrl(request.getUrl().toString());
//                        return true;
//                    }
//                });
//                Log.d(TAG, "initView: 3线"+url);
//                dlg.cancel();
//            });
//            dialog.findViewById(R.id.but_4).setOnClickListener(v1 -> {
//
//                urlHeader = String.valueOf(sharedPreferencesUtils.get("4线", ""));
//                String url = urlHeader + sharedPreferencesUtils.get("视频链接", "");
//                //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//                okWeb.loadUrl(url);
//                okWeb.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                        view.loadUrl(request.getUrl().toString());
//                        return true;
//                    }
//                });
//                Log.d(TAG, "initView: 4线"+url);
//                dlg.cancel();
//            });
//            dialog.findViewById(R.id.but_5).setOnClickListener(v1 -> {
//                urlHeader = String.valueOf(sharedPreferencesUtils.get("5线", ""));
//                String url = urlHeader + sharedPreferencesUtils.get("视频链接", "");
//                //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//                okWeb.loadUrl(url);
//                okWeb.setWebViewClient(new WebViewClient() {
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                        view.loadUrl(request.getUrl().toString());
//                        return true;
//                    }
//                });
//                Log.d(TAG, "initView: 5线"+url);
//                dlg.cancel();
  //          });
//      });
    }

    /**
     * 视频播放全屏
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }
        this.getActivity().getWindow().getDecorView();
        FrameLayout decor = (FrameLayout) this.getActivity().getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(view.getContext());
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }
        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) this.getActivity().getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        okWeb.setVisibility(View.VISIBLE);
    }


    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        this.getActivity().getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    //activity和fragment联系时候调用，fragment必须依赖activty
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取实现接口的activity 或者myListener=(MainActivity) context;
        fraSendValue = (fraSendValue) getActivity();

    }

}