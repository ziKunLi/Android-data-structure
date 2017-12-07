package com.example.newbies.myapplication.activity.studyActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;

/**
 * @author NewBies
 * @date 2017/12/7
 */
public class JDCShowActivity extends BaseActivity {

    /**
     * 屏幕的高和宽
     */
    private int height;
    private int width;
    /**
     * 抽屉菜单
     */
    private DrawerLayout jdc_select;
    /**
     * 7种存储结构
     */
    private TextView arrayList;
    private TextView linkedList;
    private TextView stack;
    private TextView queue;
    private TextView bst;
    private TextView weightGraph;
    private TextView unWeightGraph;
    /**
     * 底部操作栏菜单
     */
    private LinearLayout listAndTree;
    /**
     * 底部操作栏菜单的高度
     */
    private int bottomHeight;
    /**
     * 显示底部操作菜单的动画
     */
    ObjectAnimator showBottomActionBar;
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏，该句话一定要放在setContentView(R.layout.huffman);之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.jdc_show);

        initView();
        try {
            Thread.sleep(100);
            Thread getProperty = new Thread(new GetProperty());
            getProperty.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initListener();
    }

    @Override
    public void onResume(){
        super.onResume();
        //设置为横屏
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
    @Override
    public void initView() {

        //获取到屏幕的宽和高
        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        height = windowManager.getDefaultDisplay().getHeight();
        width = windowManager.getDefaultDisplay().getWidth();
        //初始化组件
        jdc_select = (DrawerLayout)findViewById(R.id.jdc_select);
        arrayList = (TextView)findViewById(R.id.arrayList);
        linkedList = (TextView)findViewById(R.id.linkedList);
        stack = (TextView)findViewById(R.id.stack);
        queue = (TextView)findViewById(R.id.queue);
        bst = (TextView)findViewById(R.id.bst);
        weightGraph = (TextView)findViewById(R.id.weightGraph);
        unWeightGraph = (TextView)findViewById(R.id.unWeightGraph);
        listAndTree = (LinearLayout)findViewById(R.id.list_and_tree);
        jdc_select.openDrawer(GravityCompat.START);
    }

    class GetProperty implements Runnable{

        @Override
        public void run() {
            //得到相关组件的高
            bottomHeight = listAndTree.getHeight();
            initAnim();
        }
    }

    @Override
    public void initListener() {
        arrayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                listAndTree.setVisibility(View.VISIBLE);
                //开启动画
                showBottomActionBar.start();
            }
        });
    }

    public void initAnim(){
        showBottomActionBar = ObjectAnimator.ofFloat(listAndTree, "translationY",height,height - 300);
        showBottomActionBar.setDuration(500);
    }
}
