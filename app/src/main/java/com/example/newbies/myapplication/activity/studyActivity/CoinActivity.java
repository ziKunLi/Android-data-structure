package com.example.newbies.myapplication.activity.studyActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;

/**
 *
 * @author NewBies
 * @date 2017/12/4
 */
public class CoinActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // 隐藏状态栏，该句话一定要放在setContentView(R.layout.huffman);之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.coin);
    }
    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }
}
