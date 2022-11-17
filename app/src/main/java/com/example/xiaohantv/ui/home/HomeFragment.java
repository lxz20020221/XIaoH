package com.example.xiaohantv.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.xiaohantv.R;
import com.example.xiaohantv.Utils.SharedPreferencesUtils;
import com.example.xiaohantv.Utils.WebAdapter;
import com.example.xiaohantv.Utils.myFra;
import com.example.xiaohantv.tools.fraSendValue;

/**
 * @author 小志
 */
public class HomeFragment extends myFra {

    private static final String TAG = "muFra";
    public WebView web;
    private String url;
    private ImageView iconIqy;
    private TextView textQy;
    private ImageView iconYouku;
    private TextView textYouku;
    private ImageView iconTx;
    private TextView textTengxun;
    private Button stmp;
    private com.example.xiaohantv.tools.fraSendValue fraSendValue;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void init(View view) {
        initView(view);
        fraSendValue.HomeSendValue(web);
        WebAdapter webAdapter = new WebAdapter(web);
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this.getContext(), "XiaoHan");
        sharedPreferencesUtils.commitInstance();
        iconIqy.setOnClickListener(v -> {
            url = "https://www.iqiyi.com/";
            initWeb(webAdapter, this.getContext());
        });
        textQy.setOnClickListener(v -> {
            url = "https://www.iqiyi.com/";
            initWeb(webAdapter, this.getActivity());
        });
        iconYouku.setOnClickListener(v -> {
            url = "https://www.youku.com/";
            initWeb(webAdapter, this.getActivity());
        });
        textYouku.setOnClickListener(v -> {
            url = "https://www.youku.com/";
            initWeb(webAdapter, this.getActivity());
        });
        iconTx.setOnClickListener(v -> {
            url = "https://v.qq.com/channel/choice?channel_2022=1";
            initWeb(webAdapter, this.getActivity());
        });
        textTengxun.setOnClickListener(v -> {
            url = "https://v.qq.com/channel/choice?channel_2022=1";
            initWeb(webAdapter, this.getActivity());
        });
        stmp.setOnClickListener(v -> {
            sharedPreferencesUtils.put("视频链接", web.getUrl());
            Log.d(TAG, "init: " + web.getUrl());
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_dashboard, null);
        });

    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_home;
    }


    private void initView(View view) {
        web = view.findViewById(R.id.web);
        iconIqy = view.findViewById(R.id.icon_iqy);
        textQy = view.findViewById(R.id.text_qy);
        iconYouku = view.findViewById(R.id.icon_youku);
        textYouku = view.findViewById(R.id.text_youku);
        iconTx = view.findViewById(R.id.icon_tx);
        textTengxun = view.findViewById(R.id.text_tengxun);
        stmp = view.findViewById(R.id.but_stmp);

    }

    @Override
    public void onDestroy() {
        if (web != null) {
            web.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            web.clearHistory();
            ((ViewGroup) web.getParent()).removeView(web);
            web.destroy();
            web = null;
        }
        super.onDestroy();
    }
    @SuppressLint("ClickableViewAccessibility")
    public void initWeb(WebAdapter webAdapter, Context context) {
        webAdapter.mWebSettings();
        webAdapter.initWeb(url);
        //获取焦点
        web.setOnTouchListener((View view1, MotionEvent event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_UP:
                    if (!view1.hasFocus()) {
                        view1.requestFocus();
                        Log.d(TAG, "init: " + view1.hasFocus());
                    } else {
                        Log.d(TAG, "init: " + view1.requestFocus());
                    }
                default:
                    break;
            }
            return false;
        });
        webAdapter.myWebViewClient(context);
        webAdapter.setToast(context);
        webAdapter.mWebViewClient();
        iconIqy.setVisibility(View.GONE);
        textQy.setVisibility(View.GONE);
        iconYouku.setVisibility(View.GONE);
        textYouku.setVisibility(View.GONE);
        iconTx.setVisibility(View.GONE);
        textTengxun.setVisibility(View.GONE);
        web.setVisibility(View.VISIBLE);
    }

    //activity和fragment联系时候调用，fragment必须依赖activty
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取实现接口的activity 或者myListener=(MainActivity) context;
        fraSendValue = (fraSendValue) getActivity();

    }
}