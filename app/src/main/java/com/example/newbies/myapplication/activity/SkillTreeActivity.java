package com.example.newbies.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.newbies.myapplication.R;

/**
 * Created by NewBies on 2017/11/23.
 */

public class SkillTreeActivity extends  BaseActivity {
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skills_tree_activity);
        main = (ImageButton)findViewById(R.id.main_activity);
        studyRecord = (ImageButton)findViewById(R.id.study_record);
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MainActivity.class);
                finish();
            }
        });
        studyRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(StudyRecordActivity.class);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}
