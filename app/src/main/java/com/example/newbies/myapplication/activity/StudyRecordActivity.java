package com.example.newbies.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.studyActivity.CoinActivity;
import com.example.newbies.myapplication.adapter.RecycleAdapter;
import com.gigamole.library.navigationtabstrip.NavigationTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NewBies
 * @date 2017/11/23
 */
public class StudyRecordActivity extends  BaseActivity {

    private List<String> data = new ArrayList<>();
    private RecyclerView recyclerView = null;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_record_activity);

        ininData();
        initView();
        initListener();
    }

    @Override
    public void initView() {
        bottom_button = (NavigationTabStrip)findViewById(R.id.bottom_button);
        bottom_button.setTabIndex(0,true);
        recyclerView = (RecyclerView)findViewById(R.id.recyleListView);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecycleAdapter(data));

    }

    @Override
    public void initListener() {

        bottom_button.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
                switch (index){
                    case 1:
                        openActivity(MainActivity.class);
                        finish();
                        break;
                    case 2:
                        openActivity(SkillTreeActivity.class);
                        finish();
                        break;
                    default:break;
                }
            }

            @Override
            public void onEndTabSelected(String title, int index) {

            }
        });
    }

    public void ininData(){
        data.add("哈夫曼");
        data.add("24点扑克牌游戏");
        data.add("16枚硬币反面问题");
        data.add("线性表、树、图");
        data.add("农夫过河");
        data.add("迷宫问题");
    }

    /**
     * 重写返回按钮事件，单击返回按钮回到桌面
     */
    @Override
    public void onBackPressed(){
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}
