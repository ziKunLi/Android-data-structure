package com.example.newbies.myapplication.activity.studyActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;

/**
 * Created by NewBies on 2017/11/26.
 */

public class HuffmanActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huffman);
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

    /**
     * 从文件中读取到字符频率
     */
    public void getCharTimeByFile(){

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
}
