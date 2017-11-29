package com.example.newbies.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NewBies on 2017/11/23.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Intent intent = new Intent();
    protected ImageButton main;
    protected ImageButton studyRecord;
    protected ImageButton skillTree;

    private List<Activity> activities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
    }

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 初始化响应器
     */
    public abstract void initListener();

    /**
     * 不带数据的活动切换
     * @param clz
     */
    public void openActivity(Class<?> clz){
        intent.setClass(this,clz);
        startActivity(intent);
    }

    /**
     * 带数据的活动切换
     * @param clz
     * @param bundle
     */
    public void openActivity(Class<?> clz, Bundle bundle){
        if(bundle != null){
            intent.putExtras(bundle);
        }
        openActivity(clz);
    }

    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        activities.remove(this);
    }

    /**
     * 退出程序
     */
    public void exit(){
        for(int i = 0; i < activities.size(); i++){
            activities.get(i).finish();
        }
    }
}
