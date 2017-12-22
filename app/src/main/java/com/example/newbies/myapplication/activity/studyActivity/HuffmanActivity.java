package com.example.newbies.myapplication.activity.studyActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;
import com.example.newbies.myapplication.adapter.MatchHuffmanAdapter;
import com.example.newbies.myapplication.util.Heap;
import com.example.newbies.myapplication.util.HuffmanTree;
import com.example.newbies.myapplication.view.CircleView;
import com.example.newbies.myapplication.view.LineView;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

import butterknife.ButterKnife;

/**
 *
 * @author NewBies
 * @date 2017/11/26
 */
public class HuffmanActivity extends BaseActivity{

    /**
     * 读取文件的按钮
     */
    private FloatingActionButton buildByFile;
    /**
     * 提供输入的文本框
     */
    private EditText someText;
    /**
     * 通过输入普通文本建立哈夫曼树
     */
    private FloatingActionButton buildByText;
    /**
     * 用于打开显示哈夫曼编码的按钮
     */
    private FloatingActionButton showHuffmanCode;
    /**
     * 用于打开展示所有编码的按钮
     */
    private FloatingActionButton showAllCode;
    /**
     * 文件路径
     */
    private String commonFilePath;
    /**
     * SD卡的工作目录
     */
    private String sdCardPath;
    /**
     * 用于展示哈夫曼树的布局
     */
    private FrameLayout show;
    /**
     * 绘制图形的画笔
     */
    private Paint paint;
    /**
     * 储存叶子结点哈夫曼编码的字符串数组
     */
    private String[] leafCode = new String[26];
    /**
     * 记录字符出现频率
     */
    private int[] charTimes = new int[26];
    /**
     * 用于展示哈夫曼编码键值对的表格
     */
    private RecyclerView table;
    /**
     * 用于展示表格的数据
     */
    private TreeMap<String, String> data;
    /**
     * 用于展示匹配成功后的哈夫曼编码或者字符
     */
    private TextView printCodeOrChar;
    /**
     * 匹配哈夫曼编码或字符的输入框
     */
    private EditText matchText;
    /**
     * 用于展示所有字符编码后的二进制字符
     */
    private TextView allCodeText;
    /**
     * 输入框或者读取文件中的字符
     */
    private String soureText = "";
    /**
     * 用于存储所有的编码后的二进制编码
     */
    private String allCodeString;
    /**
     * 手机屏幕的宽
     */
    private int width;
    /**
     * 手机屏幕的高
     */
    private int height;
    /**
     * 用于显示输入提示框的popupWindow
     */
    private PopupWindow inputPopupWindow = null;
    /**
     * 用于显示哈夫曼编码的popupWindow
     */
    private PopupWindow codePopupWindow = null;
    /**
     * 用于展示所有字符编码的弹窗
     */
    private PopupWindow allCodePopupWindow = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
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

    /**
     * 初始化视图
     */
    @Override
    public void initView() {
        buildByFile = (FloatingActionButton)findViewById(R.id.buildByFile);
        buildByText = (FloatingActionButton)findViewById(R.id.buildByText);
        showHuffmanCode = (FloatingActionButton)findViewById(R.id.showHuffmanCode);
        showAllCode = (FloatingActionButton)findViewById(R.id.showALLCode);
        show = (FrameLayout)findViewById(R.id.show);

        //创建一个画笔
        paint = new Paint();
        //设置画笔的颜色
        paint.setColor(Color.BLACK);
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

    }

    /**
     * 初始化相关的监听事件
     */
    @Override
    public void initListener() {
        buildByFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAssignFolder("");
            }
        });

        buildByText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText();
            }
        });

        showHuffmanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHuffmanCode();
            }
        });

        showAllCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCodeString = enCode(soureText);
                showAllCode();
                //将进行压缩后的字符串转换为二进制，存入文件，达到压缩文件的效果
                saveCompressedFile("A_test","codeFile.dat",allCodeString);
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

        File file = new File(path);
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
                onResumeHuffmanTree(content.toString());
                isr.close();
                br.close();
                is.close();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(this, "读取错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 利用产生的哈夫曼树压缩文件
     * @param path
     * @param fileName
     */
    public void saveCompressedFile(String path, String fileName, String text){
        //判断手机是否存在SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //获取SD卡的当前的工作
            sdCardPath  = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        File tempFile = new File(sdCardPath + "/" +path + "/" + fileName);
        try {
            //创建二进制文件流
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(tempFile)));
            //根据传进来的
            int[] allCode = analysisByte(text);
            for(int i = 0; i < allCode.length; i++){
                //以二进制的方式将编码存入文件
                dataOutputStream.write(allCode[i]);
            }
            dataOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将每八个字符（二进制编码）装换为十进制
     * @param text
     * @return
     */
    public int[] analysisByte(String text){
        //确定该编码一共能转换多少个编码
        int size = text.length()/8;
        int[] allCode = new int[size + 1];
        String tempString = "";
        for(int i = 0 ; i < size; i++){
            tempString = text.substring(i * 8, (i + 1) * 8);
            //进制转换
            for(int j = 0; j < tempString.length(); j++){
                allCode[i] += (Integer.parseInt(tempString.charAt(tempString.length() - j - 1) + "")) * (int) Math.pow(2, j);
            }
        }
        //将几个不能被八整除的编码单独进行二进制到十进制的转换
        tempString = text.substring(size * 8, text.length());
        for(int j = 0; j < tempString.length(); j++){
            allCode[size] += (Integer.parseInt(tempString.charAt(tempString.length() - j - 1) + "")) * (int) Math.pow(2, j);
        }
        return allCode;
    }

    /**
     * 筛选字符，去除不安全的字符
     * @param text
     * @return
     */
    public String screenString(String text){
        StringBuilder stringBuilder = new StringBuilder();
        char temp = ' ';
        for(int i = 0; i < text.length(); i++){
            temp = text.charAt(i);
            if(temp != ' '&&Character.isLetter(temp)){
                stringBuilder.append(temp);
            }
        }
        String current = stringBuilder.toString().toLowerCase();
        return current;
    }
    /**
     * 从输入框中获得字符并计算其权重，没有进行字符的筛选，如需要筛选，请调用screenString
     * @return
     */
    public int[] getCharTimeByEditText(String text){

        for(int i = 0; i < text.length(); i++){
            charTimes[text.charAt(i) - 97]++;
        }
        return  charTimes;
    }

    /**
     * @auther 李自坤
     * 弹出自定义的popWindow，显示输入字符以便生成哈夫曼树的弹窗
     */
    public void inputText() {

        if (inputPopupWindow == null) {
            //初始化PopupWindow的布局
            View popView = getLayoutInflater().inflate(R.layout.input_popupwindow, null);
            inputPopupWindow = new PopupWindow(popView,width/2,ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效
            inputPopupWindow.setBackgroundDrawable(new ColorDrawable());
            //设置Gravity，让它显示在屏幕中心。
            inputPopupWindow.showAtLocation(show, Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
            //点击外部关闭。
            inputPopupWindow.setOutsideTouchable(true);
            //设置item的点击监听
            someText = popView.findViewById(R.id.someText);
            popView.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取到输入框中的字符
                    String text = String.valueOf(someText.getText());
                    allCodeString = text;
                    //如果输入的字符串是错误的，那么该哈夫曼树就为空
                    if(text.equals("")){
                        return;
                    }
                    onResumeHuffmanTree(text);
                    //关闭输入提示框
                    inputPopupWindow.dismiss();
                }
            });
            popView.findViewById(R.id.cencle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inputPopupWindow.dismiss();
                }
            });
            //设置popupWindow关闭的事件
            inputPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1f);
                }
            });
        } else {
            inputPopupWindow.showAtLocation(show, Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        setBackgroundAlpha(0.3f);
    }

    /**
     * 每次进行绘制哈夫曼树之前做的准备，就是进行一些初始化的准备
     * @param text
     */
    public void onResumeHuffmanTree(String text){
        allCodeString = "";
        //每次点击初始化哈夫曼编码，避免之前输入的字符造成的影响
        for(int i = 0 ; i < 26; i++){
            leafCode[i] = null;
            charTimes[i] = 0;
        }
        //对传入进来的字符串进行筛选，防止危险字符使整个程序崩溃
        text = screenString(text);
        if(text == null || text.equals("")){
            Toast.makeText(this,"该文件暂不支持！",Toast.LENGTH_SHORT).show();
            return;
        }
        soureText = text;
        //得打哈夫曼树
        HuffmanTree tree = getHuffmanTree(getCharTimeByEditText(text));
        //得到哈夫曼树的根节点
        HuffmanTree.Node root = tree.root;
        getAllCode(root);
        if(show.getChildCount() > 1){
            show.removeViews(1,show.getChildCount() - 1);
        }
        //绘制出根节点和相关线条
        show.addView(new CircleView(HuffmanActivity.this, width/2, 150f, 47f,root.weight + "",paint));
        showHuffman(root, width/2,150f, 1);
    }

    /**
     * 展示所有字符和相应编码的键值对
     */
    public void showHuffmanCode(){
        if (codePopupWindow == null) {
            //初始化PopupWindow的布局
            View popView = getLayoutInflater().inflate(R.layout.show_huffman_code, null);
            //设置PopupWindow的大小，ture设置focusAble
            codePopupWindow = new PopupWindow(popView, width*3/4,height, true);
            //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效
            codePopupWindow.setBackgroundDrawable(new ColorDrawable());
            //设置Gravity，让它显示在屏幕中心。
            codePopupWindow.showAtLocation(show, Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0);
            //加载表格
            table = (RecyclerView)popView.findViewById(R.id.table);
            //在popupWindow中的按钮只能在popupWindow中获取，不然会获取失败，导致空引用异常
            matchText = popView.findViewById(R.id.searchText);
            printCodeOrChar =  popView.findViewById(R.id.printCodeOrChar);
            //匹配事件
            //设置item的点击监听
            popView.findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<String> deCode = deCode(leafCode,charTimes, String.valueOf(matchText.getText()));
                    printCodeOrChar.setText(deCode.toString());
                }
            });
            popView.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    codePopupWindow.dismiss();
                }
            });
            //设置popupWindow关闭的事件
            codePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1f);
                }
            });
        } else {
            codePopupWindow.showAtLocation(show, Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0);
            matchText.setText("");
            printCodeOrChar.setText("哈夫曼编码表");
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        table.setLayoutManager(layoutManager);
        initTable();
        table.setAdapter(new MatchHuffmanAdapter(data));
        setBackgroundAlpha(0.33f);
    }

    public void showAllCode(){
        if(allCodePopupWindow == null){
            //初始化popupWindow的布局文件
            View popupView = getLayoutInflater().inflate(R.layout.show_all_code,null);
            //设置popupWindow的大小，同时将focusable设置为true
            allCodePopupWindow = new PopupWindow(popupView, width * 3/4,height,true);
            //必须设置BackgroundDrawable后，设置OutsideTouchable才会有效果
            allCodePopupWindow.setBackgroundDrawable(new ColorDrawable());
            //设置点击外面关闭
            allCodePopupWindow.setOutsideTouchable(true);
            //设置popupWindow显示的位置，这里我将其设置显示在屏幕中心
            allCodePopupWindow.showAtLocation(show, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
            allCodePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1.0f);
                }
            });
            //设置背景为半透明
            setBackgroundAlpha(0.35f);
            allCodeText = popupView.findViewById(R.id.allCodeText);
            allCodeText.setText("原文：" + soureText + "\n\n编码后：" + allCodeString);
        }else{
            allCodeText.setText("原文：" + soureText + "\n\n编码后：" + allCodeString);
            //设置背景为半透明
            setBackgroundAlpha(0.35f);
            //设置popupWindow显示的位置，这里我将其设置显示在屏幕中心
            allCodePopupWindow.showAtLocation(show, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
        }
    }

    /**
     * 初始化表格的数据
     */
    public void initTable(){
        data = new TreeMap<>();
        for(int i = 0 ; i < 26; i++){
            if(leafCode[i] != null){
                data.put((char)(i + 65)+"", leafCode[i]);
            }
        }
    }

    /**
     * 根据权值，得到一颗哈夫曼树
     * @param charTimes
     */
    public HuffmanTree getHuffmanTree(int[] charTimes){
        if(charTimes == null){
            return  null;
        }
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

    /**
     * 绘制哈夫曼树
     * @param root
     * @param offsetX 根节点的圆心X轴坐标
     * @param offsetY 根节点的圆心Y轴坐标
     * @param current
     */
    public void showHuffman(HuffmanTree.Node root, float offsetX, float offsetY, int current){
        if(root.left != null){
            //计算X轴方向的偏移量
            float x = width/2;
            for(int i = 0; i < current; i++){
                x = x/2;
            }
            x = offsetX - x;
            //绘制圆
            show.addView(new CircleView(HuffmanActivity.this, x, offsetY + 150, 47,root.left.weight + "", this.paint));
            //绘制连接线
            show.addView(new LineView(HuffmanActivity.this,offsetX - 33.3f, offsetY + 33.3f, x + 33.3f, offsetY + 116.7f,"", this.paint));
            //递归调用，绘制下一个结点，同时其Y轴偏移量加150，也就是下一个结点向下移动
            showHuffman(root.left, x, offsetY + 150, ++current);
        }
        current--;
        if(root.right != null){
            float x = width/2;
            //计算
            for(int i = 0; i < current; i++){
                x = x/2;
            }
            x = offsetX + x;
            //绘制圆
            show.addView(new CircleView(HuffmanActivity.this, x, offsetY + 150, 47,root.right.weight + "", this.paint));
            //绘制连接线
            show.addView(new LineView(HuffmanActivity.this,offsetX + 33.3f, offsetY + 33.3f, x - 33.3f, offsetY + 116.7f,"", this.paint));
            showHuffman(root.right, x, offsetY + 150, ++current);
        }

    }

    /**
     * 根据哈夫曼编码得到字符
     * @param charTimes
     * @return
     */
    public ArrayList<String> deCode(String[] leafCode, int[] charTimes, String text) {
        int count = 0;
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            String temp = text.substring(count, i + 1);

            for (int j = 0; j < leafCode.length; j++) {
                if (charTimes[j] != 0 && leafCode[j].equals(temp)) {
                    list.add(temp + " is " + (char)(j + 65));
                    count = i + 1;
                }
            }
        }
        return list;
    }

    /**
     * 将字符串解析成哈夫曼编码
     * @param text
     * @return
     */
    public String enCode(String text){
        if(text == null||text.equals("")){
            return "";
        }
        text = text.trim();
        StringBuilder allCode = new StringBuilder();
        for(int i = 0; i < text.length(); i++){
            allCode.append(leafCode[text.charAt(i) - 97]);
        }
        return allCode.toString();
    }

    @Override
    protected  void onActivityResult(int requestCode,int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            Uri uri = data.getData();
            String scheme = uri.getScheme();
            //从系统文件管理器中查找文件
            if (requestCode == 1) {
                //得到文件的真实路径
                if (scheme == null) {
                    commonFilePath = uri.getPath();
                } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                    Cursor cursor = this.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);

                    if (null != cursor) {
                        if (cursor.moveToFirst()) {
                            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                            if (index > -1) {
                                commonFilePath = cursor.getString(index);
                            }
                        }
                        cursor.close();
                    }
                }
                //读取文件
                readFile(commonFilePath);
            }
        }
    }
}
