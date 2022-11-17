package com.example.xiaohantv.ui.notifications;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
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
public class NotificationsFragment extends myFra {


    private static final String TAG = "NO";
    private WebView webOm;
    private Button butStp;
    private com.example.xiaohantv.tools.fraSendValue fraSendValue;

    @Override
    protected void init(View view) {
        initView(view);
        fraSendValue.HomeSendValue(webOm);
        WebAdapter webAdapter = new WebAdapter(webOm);
        webAdapter.mWebSettings();
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this.getActivity(), "XiaoHan");
        sharedPreferencesUtils.commitInstance();
        String url = String.valueOf(sharedPreferencesUtils.get("视频链接", ""));
        webAdapter.initWeb(url);
        webAdapter.myWebViewClient(getContext());
        webAdapter.setToast(this.getActivity());
        webAdapter.mWebViewClient();
        if (webOm.getUrl() != null) {
            butStp.setOnClickListener(v -> {
                sharedPreferencesUtils.put("视频链接", webOm.getUrl());
                Log.d(TAG, "init: " + webOm.getUrl());
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.navigation_dashboard, null);
            });
        }else {
            Toast.makeText(getContext(),"先去选一个视频！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int getlayout() {
        return R.layout.fragment_notifications;
    }

    private void initView(View view) {
        webOm = view.findViewById(R.id.web_om);
        butStp = view.findViewById(R.id.but_stp);
    }

    //activity和fragment联系时候调用，fragment必须依赖activty
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取实现接口的activity 或者myListener=(MainActivity) context;
        fraSendValue = (fraSendValue) getActivity();

    }
}