package com.example.newbies.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import com.example.newbies.myapplication.R;
import com.gigamole.library.navigationtabstrip.NavigationTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 李自坤
 * @date 2017/11/23
 */
public class MainActivity extends BaseActivity {

    Button openComando = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        /**
         * 在activity中的oncreate()方法里初始化butterknife框架 ,注意初始化要放在setContentView()之后
         */
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    @Override
    public void initView() {
        openComando = (Button)findViewById(R.id.commando);
        bottom_button = (NavigationTabStrip)findViewById(R.id.bottom_button);
        bottom_button.setTabIndex(1,true);
    }

    @Override
    public void initListener(){
        openComando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("知乎","https://www.zhihu.com/");
                openActivity(WebViewActivty.class,bundle);
            }
        });
        bottom_button.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
                switch (index){
                    case 0:
                        openActivity(StudyRecordActivity.class);
                        finish();
                        break;
                    case 2:
                        openActivity(SkillTreeActivity.class);
                        finish();
                    default:break;
                }
            }

            @Override
            public void onEndTabSelected(String title, int index) {

            }
        });
//        studyRecord.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//                openActivity(StudyRecordActivity.class);
//                finish();
//            }
//        });
//
//        skillTree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openActivity(SkillTreeActivity.class);
//                finish();
//            }
//        });
    }

    @Override
    public void onBackPressed(){
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}
