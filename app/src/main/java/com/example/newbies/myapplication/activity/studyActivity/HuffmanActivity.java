package com.example.newbies.myapplication.activity.studyActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;
import com.example.newbies.myapplication.adapter.LeafAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by NewBies on 2017/11/26.
 */

public class HuffmanActivity extends BaseActivity{

    private List<Integer> data  = new ArrayList<>();

    private Button buildHuffman;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huffman);
        ButterKnife.bind(this);
        initData();
        initView();
        initListener();
    }

    public void initData(){
        data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0);
        data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0);
        data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0);
        data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0);
        data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0);
        data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0); data.add(0);

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
        buildHuffman = (Button)findViewById(R.id.buildHuffman);
        LeafAdapter leafAdapter = new LeafAdapter(HuffmanActivity.this,R.layout.list_view_item,data);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(leafAdapter);
    }

    @Override
    public void initListener() {
        buildHuffman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAssignFolder("");
                Toast.makeText(HuffmanActivity.this, "something here", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 打开系统文件管理器
     * @param path
     */
    public void openAssignFolder(String path){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //设置类型，这里设置的是任意类型
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,1);
    }
    /**
     * 从文件中读取到字符频率
     */
    public void getCharTimeByFile(){

    }

    /**
     * 将哈夫曼树存入文件
     */
    public void inputFile(){

    }

    /**
     * 计算字符频率
     */
    public void computeCharTimes(){
        
    }

    /**
     * 计算哈夫曼编码
     */
    public void computeHuffmanCode(){

    }

    /**
     * 得到一个字符串的哈夫曼编码
     * @return
     */
    public String getHuffmanCodeByString(String string){
        return null;
    }

    /**
     * 根据哈夫曼编码得到字符
     * @param code
     * @return
     */
    public String getStringByHuffmanCode(String code){
        return null;
    }

    @Override
    protected  void onActivityResult(int requestCode,int resultCode, Intent data){

        if(resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            if(requestCode == 1){
                Toast.makeText(this, "文件路径" + uri.getPath() + toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
