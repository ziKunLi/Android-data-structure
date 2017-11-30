package com.example.newbies.myapplication.activity.studyActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;
import com.example.newbies.myapplication.adapter.LeafAdapter;
import com.example.newbies.myapplication.util.Heap;
import com.example.newbies.myapplication.util.HuffmanTree;
import com.example.newbies.myapplication.view.CircleView;
import com.example.newbies.myapplication.view.LineView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by NewBies on 2017/11/26.
 */

public class HuffmanActivity extends BaseActivity{

    private List<Integer> data  = new ArrayList<>();

    /**
     * 读取文件的按钮
     */
    private Button buildHuffman;
    /**
     * 通过输入产生哈夫曼树的按钮
     */
    private Button build;
    /**
     * 提供输入的文本框
     */
    private EditText someText;
    /**
     * 暂定
     */
    private EditText fileName;
    /**
     * 文件路径
     */
    private String commonFilePath;
    /**
     * 用于展示哈夫曼树的布局
     */
    private FrameLayout show;
    /**
     * 绘制图形的画笔
     */
    private Paint paint;
    /**
     * 储存所有结点的权值
     */
    private int[] weight = new int[50];
    /**
     * 存储所有结点哈夫曼编码的字符串数组(最多为50个)
     */
    private String[] allCode = new String[50];
    /**
     * 储存叶子结点哈夫曼编码的字符串数组
     */
    private String[] leafCode = new String[26];
    /**
     * 手机屏幕的宽
     */
    private int width;
    /**
     * 手机屏幕的高
     */
    private int height;
    /**
     * 绘制图形时坐标的X轴偏移量
     */
    private int offsetX;
    /**
     * 绘制图形时坐标的Y轴偏移量
     */
    private int offsetY;

    private PopupWindow mPopupWindow = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // 隐藏状态栏，该句话一定要放在setContentView(R.layout.huffman);之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.huffman);
        ButterKnife.bind(this);
        initView();
        initListener();
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
        fileName = (EditText)findViewById(R.id.fileName);
        build = (Button)findViewById(R.id.build);
        someText = (EditText)findViewById(R.id.someText);
        show = (FrameLayout)findViewById(R.id.show);

        //创建一个画笔
        paint = new Paint();
        //设置画笔的颜色
        paint.setColor(Color.RED);
        //设置画笔的锯齿效果
        paint.setAntiAlias(true);
        //设置画笔的风格（空心或实心）
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        //设置画笔文字大小
        paint.setTextSize(60);
        //设置文字居中
        paint.setTextAlign(Paint.Align.CENTER);

        WindowManager windowManager = (WindowManager)HuffmanActivity.this.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
        offsetX = width/2;
        offsetY = 100;
    }

    @Override
    public void initListener() {
        buildHuffman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAssignFolder("");
            }
        });
        build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HuffmanTree.Node root = getHuffmanTree(getCharTimeByEditText()).root;
                getAllCode(root);
                show.addView(new CircleView(HuffmanActivity.this, width/2, 100f, 47f,root.weight + "",paint));
                showHuffman(root, width/2,100f, 1);
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
     * 根据指定路径，读取文件
     * @param path
     */
    public void readFile(String path){
        File file = new File("" + path);

        //非线程安全，但效率高，PS：这里我没用到线程
        StringBuilder content = new StringBuilder();
        try{
            FileInputStream is = new FileInputStream(file);
            if(null != is){
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                //临时变量，用于暂时存储读取出来的一行数据
                String line = "";
                //逐行读取
                while (true){
                    line = br.readLine();
                    if(line == null){
                        break;
                    }
                    content.append(line);
                }
            }
        }catch (FileNotFoundException e){
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(this, "读取错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从输入框中获得字符并计算其权重
     * @return
     */
    public int[] getCharTimeByEditText(){
        //获取到输入框中的字符
        String text = String.valueOf(someText.getText());
        StringBuilder stringBuilder = new StringBuilder();
        char temp = ' ';
        for(int i = 0; i < text.length(); i++){
            temp = text.charAt(i);
            if(temp != ' '){
                stringBuilder.append(temp);
            }
        }
        String current = stringBuilder.toString().toLowerCase();
        //用于记录各个字符频率的数组
        int[] charTimes = new int[26];
        for(int i = 0; i < current.length(); i++){
            charTimes[current.charAt(i) - 97]++;
        }
        return  charTimes;
    }
//    /**
//     * @auther 李自坤
//     * 弹出自定义的popWindow
//     */
//    public void popUpMyOverflow() {
//        //获取状态栏高度
//        Rect frame = new Rect();
//        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        //状态栏高度+toolbar的高度
//        int yOffset = frame.top + toolbar.getHeight();
//        if (mPopupWindow == null) {
//            //初始化PopupWindow的布局
//            View popView = getLayoutInflater().inflate(R.layout.main_action_popupmenu, null);
//            //popView即popupWindow的布局，ture设置focusAble
//            mPopupWindow = new PopupWindow(popView,
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
//            //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效
//            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
//            //点击外部关闭。
//            mPopupWindow.setOutsideTouchable(true);
//            //设置一个动画。
//            mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
//            //设置Gravity，让它显示在右上角。
//            mPopupWindow.showAtLocation(toolbar, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
//            //设置item的点击监听
//            popView.findViewById(R.id.set_site_time).setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("mode", true);
//                    bundle.putSerializable("terminal_ids", mDataSet);
//                    openActivity(SetSiteTimeActivity.class, bundle);
//                }
//            });
//            //设置popupWindow关闭的事件
//            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    backgroundAlpha(1f);
//                }
//            });
//
//            //站点开关复位
//            popView.findViewById(R.id.site_switch_reset).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setMessage("开关复位命令将清楚所有强制状态，站点恢复到自动控制，所有开关状态将按自动状态重新动作");
//                    builder.setTitle("确认开关复位？");
//                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int i) {
//                            String terminal_ids = "";
//                            for (Iterator iterator = mDataSet.iterator(); iterator.hasNext(); ) {
//                                terminal_ids += iterator.next().toString() + ",";
//                            }
//
//                            Map<String, Object> map = new HashMap<>();
//                            map.put("is_auto_reset", 1);
//                            doResetTerminal(map, terminal_ids);
//                            dialog.dismiss();
//                            onResume();
//                        }
//                    });
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int i) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.create().show();
//                }
//            });
//            popView.findViewById(R.id.open_shut_branch).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("mode", true);
//                    bundle.putSerializable("terminal_ids", mDataSet);
//                    openActivity(OpenDownBranchActivity.class, bundle);
//                }
//            });
//            popView.findViewById(R.id.set_switch_time).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, SetOnOffTimeActivity.class);
//                    intent.putExtra("multi_choice", true);
//                    intent.putExtra("terminal_id", mDataSet);
//                    startActivity(intent);
//                }
//            });
//            popView.findViewById(R.id.forced_switch_lights).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, ForcedSwitchActivity.class);
//                    intent.putExtra("multi_choice", true);
//                    intent.putExtra("terminal_id", mDataSet);
//                    startActivity(intent);
//                }
//            });
//            popView.findViewById(R.id.set_weekend).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, SetWeekendTimeActivity.class);
//                    intent.putExtra("multi_choice", true);
//                    intent.putExtra("terminal_id", mDataSet);
//                    startActivity(intent);
//                }
//            });
//        } else {
//            mPopupWindow.showAtLocation(toolbar, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
//        }
//        backgroundAlpha(0.3f);
//    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     * @auther 李自坤
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
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
     * 根据权值，得到一颗哈夫曼树
     * @param charTimes
     */
    public HuffmanTree getHuffmanTree(int[] charTimes){
        Heap<HuffmanTree> heap = new Heap<>();
        for(int i =0 ; i < charTimes.length; i++){
            if(charTimes[i] > 0){
                heap.add(new HuffmanTree(charTimes[i],(char)(i + 97)));
            }
        }
        //当堆的大小为1时，则堆中就只有一个最大值，即为哈夫曼树
        while (heap.getSize() > 1){
            //从堆中找出最小的值
            HuffmanTree left = heap.remove();
            //从堆找出次小值
            HuffmanTree right = heap.remove();
            //将最小值和次小值合并成一颗新的树
            heap.add(new HuffmanTree(left,right));
       }

       //返回该哈夫曼树
       return heap.remove();
    }

    /**
     * 前序遍历方法，得到所有结点的哈夫曼编码
     * @param root
     * @return
     */
    public void getAllCode(HuffmanTree.Node root){
        if(root.left != null){
            root.left.code = root.code + "0";
            getAllCode(root.left);

            root.right.code = root.code + "1";
            getAllCode(root.right);
        }
        //左边结点为空，说明该结点为叶子结点，将其哈夫曼编码进行存储
        else {

            leafCode[root.element - 97] = root.code;
        }
    }

    public void showHuffman(HuffmanTree.Node root, float offsetX, float offsetY, int current){
        if(root.left != null){
            float x = width/2;
            for(int i = 0; i < current; i++){
                x = x/2;
            }
            x = offsetX - x;
            show.addView(new CircleView(HuffmanActivity.this, x, offsetY + 100, 47,root.left.weight + "", this.paint));
            showHuffman(root.left, x, offsetY + 150, ++current);
        }
        current--;
        if(root.right != null){
            float x = width/2;
            for(int i = 0; i < current; i++){
                x = x/2;
            }
            x = offsetX + x;
            show.addView(new CircleView(HuffmanActivity.this, x, offsetY + 100, 47,root.right.weight + "", this.paint));
            showHuffman(root.right, x, offsetY + 150, ++current);
        }

    }
    /**
     * 根据哈夫曼编码，初始化哈夫曼树
     */

    /**
     * 根据所有结点的哈夫曼编码计算其结点所在具体位置，绘制图形界面
     */
    public void setGUI(){
        for(int i = 0; i < 50; i++){

        }
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
            //从系统文件管理器中查找文件
            if(requestCode == 1){
                commonFilePath = uri.getPath().toString();
                readFile(commonFilePath);
                fileName.setText(commonFilePath);
            }
        }
    }
}
