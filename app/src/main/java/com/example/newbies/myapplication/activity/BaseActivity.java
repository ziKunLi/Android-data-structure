package com.example.newbies.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.studyActivity.CoinActivity;
import com.example.newbies.myapplication.view.CustomDialog;
import com.gigamole.library.navigationtabstrip.NavigationTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NewBies
 * @date 2017/11/23
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Intent intent = new Intent();
    protected ImageButton main;
    protected ImageButton studyRecord;
    protected ImageButton skillTree;
    protected NavigationTabStrip bottom_button;
    private CustomDialog customDialog = null;
    /**
     * 屏幕宽度
     */
    public int width;
    /**
     * 屏幕高度
     */
    public int height;
    /**
     * 用于进行PX与dp的转换
     */
    public float scale;

    private List<Activity> activities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取屏幕的宽高
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
        scale = getResources().getDisplayMetrics().density;
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

    /**
     * 将dp为单位的数转换为px为单位的数
     * @param dp
     * @return
     */
    public double dpToPx(int dp){
        return dp*scale + 0.5;
    }

    /**
     * 将px为单位的数转换为以dp为单位的数
     * @param px
     * @return
     */
    public double pxToDp(int px){
        return (px - 0.5)/scale;
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     * @auther 李自坤
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
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
     * 显示旋转进度条
     */
    public void showProgressDialog(){
        if(customDialog == null){
            customDialog = new CustomDialog(this, R.style.CustomDialog);
        }
        customDialog.show();
    }

    /**
     * 取消显示旋转进度条
     */
    public void dismissProgressDialog(){
        customDialog.dismiss();
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
