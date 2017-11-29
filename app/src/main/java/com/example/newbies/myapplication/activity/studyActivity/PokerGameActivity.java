package com.example.newbies.myapplication.activity.studyActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;

/**
 *
 * @author NewBies
 * @date 2017/11/28
 */
public class PokerGameActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poker_game);
        initView();
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

    }

    @Override
    public void initListener() {

    }
}
