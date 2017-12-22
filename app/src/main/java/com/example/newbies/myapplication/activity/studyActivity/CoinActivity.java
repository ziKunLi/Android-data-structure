package com.example.newbies.myapplication.activity.studyActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;
import com.example.newbies.myapplication.adapter.CoinAdapt;
import com.example.newbies.myapplication.util.CoinsModel;
import com.example.newbies.myapplication.util.GameMode;

import java.util.ArrayList;

/**
 *
 * @author NewBies
 * @date 2017/12/4
 */
public class CoinActivity extends BaseActivity {

    private TextView description;
    private AppCompatSpinner coin_count;
    private AppCompatSpinner game_mode;
    /**
     * 返回上一步的按钮
     */
    private Button previous;
    /**
     * 重新开始的按钮
     */
    private Button restart;
    /**
     * 查看答案的按钮
     */
    private Button answer;
    /**
     * 给出提示信息的按钮
     */
    private Button remind;
    private RecyclerView someCoin;
    private GridLayoutManager layoutManager;
    private CoinAdapt coinAdapt;
    /**
     * 屏幕宽度
     */
    private int width;
    /**
     * 屏幕高度
     */
    private int height;
    /**
     * 两种硬币数量的模型
     */
    private CoinsModel[] coinsModel = new CoinsModel[4];
    private ArrayList<Character> data;
    /**
     * 游戏模式
     */
    private GameMode gameMode = GameMode.STRIGHT;
    private int row = 3;
    private ArrayList<Integer> path;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // 隐藏状态栏，该句话一定要放在setContentView(R.layout.huffman);之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.coin);
        initData();
        initView();
        initListener();
    }
    @Override
    public void initView() {
        //获取屏幕的宽高
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
        scale = getResources().getDisplayMetrics().density;

        description = (TextView)findViewById(R.id.description);
        coin_count = (AppCompatSpinner)findViewById(R.id.coin_count);
        game_mode = (AppCompatSpinner)findViewById(R.id.game_mode);
        previous = (Button)findViewById(R.id.previous);
        restart = (Button)findViewById(R.id.restart);
        answer = (Button)findViewById(R.id.answer);
        remind = (Button)findViewById(R.id.remind);

        //将两种硬币数量的情况初始化，而不在以后使用时边使用边初始化，降低了系统负担
        coinsModel[0] = new CoinsModel(9, gameMode);
        coinsModel[1] = new CoinsModel(16, gameMode);
        coinsModel[2] = new CoinsModel(9, GameMode.OBLIQUE);
        coinsModel[3] = new CoinsModel(16, GameMode.OBLIQUE);
        someCoin = (RecyclerView)findViewById(R.id.someCoin);
        layoutManager = new GridLayoutManager(this, 3);
        someCoin.setLayoutManager(layoutManager);
        coinAdapt = new CoinAdapt(data,(width - 200)/3, coinsModel[0]);
        someCoin.setAdapter(coinAdapt);
    }

    @Override
    public void initListener() {
        //注意：在初始化该监听器时，监听器中相关方法也会被执行一次，注意：只是针对该监听器（别问我为啥，我打断点知道的）
        coin_count.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //选中时的操作，避免在被选择后，再次选择同一种类型而导致方法的重复执行，给系统带来的不必要的压力
                modifyGame(position,gameMode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //未选中时的操作
            }
        });

        game_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 : modifyGame(row - 3, GameMode.STRIGHT);break;
                    case 1 : modifyGame(row - 3, GameMode.OBLIQUE);break;
                    default:break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //查看答案
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer();
            }
        });

        //重新开始
        restart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int offset = (gameMode == GameMode.STRIGHT) ? 3 : 1;
                initData();
                coinAdapt.setData(data);
                someCoin.setAdapter(coinAdapt);
            }
        });

        //提示
    }

    public void initData(){
        if(data == null){
            data = new ArrayList<>();
        }
        else{
            data.clear();
        }
        int random = 0;
        int offset = (gameMode == GameMode.STRIGHT) ? 3 : 1;
        while(true){
            for(int i = 0; i < row; i++){
                for(int j = 0; j < row; j++){
                    random =(int)(Math.random() * 2);
                    if(random % 2 == 0){
                        data.add('H');
                    }
                    else {
                        data.add('T');
                    }
                }
            }
            //在未初始化硬币模型时，硬币模型为空，第一次产生的3*3的一定是有解的，所以直接跳出循环
            if(coinsModel[row - offset] == null){
                break;
            }
            //判断产生的随机序列是否有解，同时将正确的解存入path中
            path = (ArrayList<Integer>) coinsModel[row - offset].getShotestPath(coinsModel[row - offset].getIndex(data));
            if(path.size() > 1){
                break;
            }
            data.clear();
        }
    }

    /**
     * 修改硬币数量和游戏模式，如果判断没有进行修改，那么该方法大部分主体将不会执行
     * @param position
     * @param gameMode
     */
    public void modifyGame(int position, GameMode gameMode){
        //判断当前游戏模式是否被更改，如果是被更改，那么更改（说了当没说，，，）
        this.gameMode = gameMode;
        if(this.gameMode.equals(GameMode.STRIGHT)){
            coinAdapt.setCoinsModel(coinsModel[position]);
        }
        else {
            coinAdapt.setCoinsModel(coinsModel[position + 2]);
        }

        if((position + 3) != row ){
            row = position + 3;
            initData();
            layoutManager.setSpanCount(row);
            coinAdapt.setData(data);
            coinAdapt.setSide((width - 200)/row);

            coinAdapt.setRow(row);
            someCoin.setAdapter(coinAdapt);
        }
    }

    public void getAnswer(){
        int offset = (gameMode == GameMode.STRIGHT) ? 3 : 1;
        if(path == null){
            path = (ArrayList<Integer>) coinsModel[row - offset].getShotestPath(coinsModel[row - offset].getIndex(data));
        }
        for(int i = 0; i < path.size(); i++){
            data = coinsModel[row - offset].getNode(path.get(i));
            coinAdapt.setData(data);
            someCoin.setAdapter(coinAdapt);
        }
    }
}