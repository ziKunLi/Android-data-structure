package com.example.newbies.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.newbies.myapplication.R;

/**
 * Created by NewBies on 2017/11/23.
 */

public class WebViewActivty extends BaseActivity {

    private String url = "";


    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        WebView webView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        url = bundle.getString("知乎");
        //設置支持JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        //啟用緩存
        webView.getSettings().setAppCacheEnabled(true);
        //設置緩存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        //加載網頁
        webView.loadUrl(url);
        //最後請記住，要申請訪問網絡權限
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onBackPressed(){
        openActivity(MainActivity.class);
    }
}
