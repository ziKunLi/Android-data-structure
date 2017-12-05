package com.example.newbies.myapplication.activity.studyActivity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;
import com.example.newbies.myapplication.adapter.CoinAdapt;

/**
 *
 * @author NewBies
 * @date 2017/12/4
 */
public class CoinActivity extends BaseActivity {

    private TextView description;
    private Spinner coin_count;
    private Spinner game_mode;
    private Button previous;
    private Button restart;
    private Button answer;
    private Button remind;
    private RecyclerView someCoin;
    private GridLayoutManager layoutManager;
    private CoinAdapt coinAdapt;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // 隐藏状态栏，该句话一定要放在setContentView(R.layout.huffman);之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.coin);
    }
    @Override
    public void initView() {
        description = (TextView)findViewById(R.id.description);
        coin_count = (Spinner)findViewById(R.id.coin_count);
        game_mode = (Spinner)findViewById(R.id.game_mode);
        previous = (Button)findViewById(R.id.previous);
        restart = (Button)findViewById(R.id.restart);
        answer = (Button)findViewById(R.id.answer);
        remind = (Button)findViewById(R.id.remind);

        someCoin = (RecyclerView)findViewById(R.id.someCoin);
        layoutManager = new GridLayoutManager(this, 4);
//        coinAdapt = new CoinAdapt(4,16);
//        someCoin.setAdapter();
    }

    @Override
    public void initListener() {

    }
}