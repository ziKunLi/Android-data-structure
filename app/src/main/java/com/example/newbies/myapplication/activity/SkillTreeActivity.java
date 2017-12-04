package com.example.newbies.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.newbies.myapplication.R;
import com.gigamole.library.navigationtabstrip.NavigationTabStrip;

/**
 *
 * @author NewBies
 * @date 2017/11/23
 */

public class SkillTreeActivity extends  BaseActivity {
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skills_tree_activity);
        bottom_button = (NavigationTabStrip)findViewById(R.id.bottom_button);
        bottom_button.setTabIndex(2,true);
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        bottom_button.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
                switch (index){
                    case 0:
                        openActivity(StudyRecordActivity.class);
                        finish();
                        break;
                    case 1:
                        openActivity(MainActivity.class);
                        finish();
                    default:break;
                }
            }

            @Override
            public void onEndTabSelected(String title, int index) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        //回到桌面
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}
