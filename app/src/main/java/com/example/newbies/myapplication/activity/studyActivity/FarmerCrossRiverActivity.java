package com.example.newbies.myapplication.activity.studyActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;
import com.example.newbies.myapplication.util.FamerAcrossRiverModel;

import java.util.ArrayList;

/**
 * @author NewBies
 * @date 2017/12/15
 */
public class FarmerCrossRiverActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 开始播放按钮
     */
    private Button startPlay;
    /**
     * 左岸的三个物件
     */
    private ImageView leftBankCabbage;
    private ImageView leftBankSheep;
    private ImageView leftBankWoft;
    /**
     * 右岸的三个物件
     */
    private ImageView rightBankCabbage;
    private ImageView rightBankSheep;
    private ImageView rightBankWoft;
    /**
     * 船
     */
    private LinearLayout ferry;
    /**
     * 船上的物件
     */
    private ImageView ferryThing;
    /**
     * 屏幕的宽高
     */
    private int height;
    private int width;
    /**
     * 用于进行PX与dp的转换
     */
    private float scale;
    /**
     * 存储路径的线性表
     */
    private ArrayList<ArrayList<Integer>> path;
    private ArrayList<ArrayList<Integer>> state;
    /**
     * 船从左岸到右岸的动画
     */
    private ObjectAnimator leftToRight;
    /**
     * 船从右岸到左岸的动画
     */
    private ObjectAnimator rightToLeft;
    /**
     * 用于控制动画播放顺序的集合
     */
    private AnimatorSet animatorSet;
    /**
     * 定位当前是第几个步骤
     */
    private int index = 1;
    /**
     * 定位当前是第几种方法
     */
    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏，该句话一定要放在setContentView(R.layout.huffman);之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.farmer_cross_river);
        initData();
        initView();
        initAnim();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public void initData() {
        state = FamerAcrossRiverModel.getState();
    }

    @Override
    public void initView() {
        //获取到屏幕的宽和高
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        height = windowManager.getDefaultDisplay().getHeight();
        width = windowManager.getDefaultDisplay().getWidth();
        //获取到相关的组件
        startPlay = (Button) findViewById(R.id.startPlay);
        leftBankCabbage = (ImageView) findViewById(R.id.leftBankCabbage);
        leftBankSheep = (ImageView) findViewById(R.id.leftBankSheep);
        leftBankWoft = (ImageView) findViewById(R.id.leftBankWolf);
        rightBankCabbage = (ImageView) findViewById(R.id.rightBankCabbage);
        rightBankSheep = (ImageView) findViewById(R.id.rightBankSheep);
        rightBankWoft = (ImageView) findViewById(R.id.rightBankWolf);
        ferry = (LinearLayout) findViewById(R.id.ferry);
        ferryThing = (ImageView) findViewById(R.id.ferryThing);

        scale = getResources().getDisplayMetrics().density;
        //ferryThing.setBackgroundResource(R.drawable.ic_cabbage);
    }

    @Override
    public void initListener() {
        startPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startPlay:
                //点击播放按钮时将数据初始化
                index = 0;
                leftBankCabbage.setBackgroundResource(R.drawable.ic_cabbage);
                leftBankSheep.setBackgroundResource(R.drawable.ic_sheep);
                leftBankWoft.setBackgroundResource(R.drawable.ic_wolf);
                rightBankCabbage.setBackgroundResource(0);
                rightBankSheep.setBackgroundResource(0);
                rightBankWoft.setBackgroundResource(0);
                //再次点击，切换路径
                current = (current + 1)%state.size();
                leftToRight.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {

                        //根据船上的状态设置岸上物品的情况
                        switch (state.get(current).get(index - 1)) {
                            case 1:
                                rightBankCabbage.setBackgroundResource(R.drawable.ic_cabbage);
                                break;
                            case 2:
                                rightBankSheep.setBackgroundResource(R.drawable.ic_sheep);
                                break;
                            case 4:
                                rightBankWoft.setBackgroundResource(R.drawable.ic_wolf);
                                break;
                            default:
                                break;
                        }

                        //当到达目的地后，取消动画，
                        if (index == state.get(current).size()) {
                            //卸下船上的物品
                            ferryThing.setBackgroundResource(0);
                            //取消动画
                            leftToRight.cancel();
                            rightToLeft.cancel();
                            //取消动画监听器（这里不取消，就会被重复添加，就是个坑）
                            rightToLeft.removeAllListeners();
                            leftToRight.removeAllListeners();
                            //设置播放按钮可以点击
                            startPlay.setClickable(true);
                        } else {
                            //还没有到达目的地，继续播放动画
                            rightToLeft.start();
                        }
                    }

                    @Override
                    public void onAnimationStart(Animator animator) {
                        //根据状态获取货物，然后将相应货物从岸上取走
                        switch (state.get(current).get(index)) {
                            case 0:
                                ferryThing.setBackgroundResource(0);
                                break;
                            case 1:
                                //将物件装到船上去
                                ferryThing.setBackgroundResource(R.drawable.ic_cabbage);
                                //从岸上取走物件
                                leftBankCabbage.setBackgroundResource(0);
                                break;
                            case 2:
                                ferryThing.setBackgroundResource(R.drawable.ic_sheep);
                                leftBankSheep.setBackgroundResource(0);
                                break;
                            case 4:
                                ferryThing.setBackgroundResource(R.drawable.ic_wolf);
                                leftBankWoft.setBackgroundResource(0);
                                break;
                            default:
                                break;
                        }
                        index++;
                    }
                });

                rightToLeft.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        switch (state.get(current).get(index)) {
                            case 0:
                                ferryThing.setBackgroundResource(0);
                                break;
                            case 1:
                                ferryThing.setBackgroundResource(R.drawable.ic_cabbage);
                                rightBankCabbage.setBackgroundResource(0);
                                break;
                            case 2:
                                ferryThing.setBackgroundResource(R.drawable.ic_sheep);
                                rightBankSheep.setBackgroundResource(0);
                                break;
                            case 4:
                                ferryThing.setBackgroundResource(R.drawable.ic_wolf);
                                rightBankWoft.setBackgroundResource(0);
                                break;
                            default:
                                break;
                        }
                        index++;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        switch (state.get(current).get(index - 1)) {
                            case 1:
                                leftBankCabbage.setBackgroundResource(R.drawable.ic_cabbage);
                                break;
                            case 2:
                                leftBankSheep.setBackgroundResource(R.drawable.ic_sheep);
                                break;
                            case 4:
                                leftBankWoft.setBackgroundResource(R.drawable.ic_wolf);
                                break;
                            default:
                                break;
                        }

                        leftToRight.start();
                    }
                });
                //循环动画的起始点
                leftToRight.start();
                //设置播放按钮禁止点击
                startPlay.setClickable(false);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化动画
     */
    public void initAnim() {
        leftToRight = ObjectAnimator.ofFloat(ferry, "x", 0, width / 2 - 72 * scale + 0.5f);
        rightToLeft = ObjectAnimator.ofFloat(ferry, "x", width / 2 - 72 * scale + 0.5f, 0);
        leftToRight.setDuration(2000);
        rightToLeft.setDuration(2000);
        animatorSet = new AnimatorSet();
    }
}
