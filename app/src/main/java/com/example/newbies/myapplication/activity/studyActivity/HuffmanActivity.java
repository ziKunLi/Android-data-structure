package com.example.newbies.myapplication.activity.studyActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.example.newbies.myapplication.util.Heap;
import com.example.newbies.myapplication.util.HuffmanTree;
import com.example.newbies.myapplication.view.CircleView;
import com.example.newbies.myapplication.view.LineView;
import com.example.newbies.myapplication.view.TableRecycleView;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 *
 * @author NewBies
 * @date 2017/11/26
 */
public class HuffmanActivity extends BaseActivity{

    private List<Integer> data  = new ArrayList<>();

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
     * 进行搜索的字符或哈夫曼编码
     */
    private EditText searchText;
    /**
     * 用于进行搜索匹配的按钮
     */
    private Button searchButton;
    /**
     * 退出编码表的按钮
     */
    private Button exit;
    /**
     * 用于展示匹配结果的按钮
     */
    private TextView printCodeOrChar;
    /**
     * 用于展示字符和编码对应关系的表格
     */
    private TableRecycleView codeTable;
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
     * 用于显示输入提示框的popupWindow
     */
    private PopupWindow inputPopupWindow = null;
    /**
     * 用于显示哈夫曼编码的popupWindow
     */
    private PopupWindow codePopupWindow = null;

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
        buildByFile = (FloatingActionButton)findViewById(R.id.buildByFile);
        buildByText = (FloatingActionButton)findViewById(R.id.buildByText);
        showHuffmanCode = (FloatingActionButton)findViewById(R.id.showHuffmanCode);
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

    }

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
        if(text.equals("")){
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        char temp = ' ';
        for(int i = 0; i < text.length(); i++){
            temp = text.charAt(i);
            if(temp != ' '&&Character.isLetter(temp)){
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

    public View  initPopupWindow(PopupWindow popupWindow, int layoutId){
        //初始化PopupWindow的布局
        View popView = getLayoutInflater().inflate(layoutId, null);
        //popView即popupWindow的布局，ture设置focusAble
        popupWindow = new PopupWindow(popView,
                width/2,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //设置Gravity，让它显示在屏幕中心。
        popupWindow.showAtLocation(show, Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        return popView;
    }
    /**
     * @auther 李自坤
     * 弹出自定义的popWindow
     */
    public void inputText() {

        if (inputPopupWindow == null) {
            View popView =  initPopupWindow(inputPopupWindow, R.layout.input_popupwindow);
            //点击外部关闭。
            inputPopupWindow.setOutsideTouchable(true);
            //设置item的点击监听
            someText = popView.findViewById(R.id.someText);
            popView.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HuffmanTree tree = getHuffmanTree(getCharTimeByEditText());
                    if(tree == null){
                        return;
                    }
                    HuffmanTree.Node root = tree.root;
                    getAllCode(root);
                    Log.d("tag", show.getChildCount() + "");
                    if(show.getChildCount() > 1){
                        show.removeViews(1,show.getChildCount() - 1);
                    }
                    show.addView(new CircleView(HuffmanActivity.this, width/2, 150f, 47f,root.weight + "",paint));
                    showHuffman(root, width/2,150f, 1);
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
                    backgroundAlpha(1f);
                }
            });
        } else {
            inputPopupWindow.showAtLocation(show, Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        backgroundAlpha(0.3f);
    }

    public void showHuffmanCode(){
        if (codePopupWindow == null) {
            View popView =  initPopupWindow(codePopupWindow, R.layout.show_huffman_code);
            //点击外部关闭。
            codePopupWindow.setOutsideTouchable(true);
            //在popupWindow中的按钮只能在popupWindow中获取，不然会获取失败，导致空引用异常
            searchText = popView.findViewById(R.id.searchText);
//            codeTable = popView.findViewById(R.id.table);

            //设置item的点击监听
            popView.findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
                    backgroundAlpha(1f);
                }
            });
        } else {
            codePopupWindow.showAtLocation(show, Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        backgroundAlpha(0.3f);
    }

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
            float x = width/2;
            for(int i = 0; i < current; i++){
                x = x/2;
            }
            x = offsetX - x;
//            Math.atan()
            show.addView(new CircleView(HuffmanActivity.this, x, offsetY + 150, 47,root.left.weight + "", this.paint));
            show.addView(new LineView(HuffmanActivity.this,offsetX - 33.3f, offsetY + 33.3f, x + 33.3f, offsetY + 116.7f,"", this.paint));
            showHuffman(root.left, x, offsetY + 150, ++current);
        }
        current--;
        if(root.right != null){
            float x = width/2;
            for(int i = 0; i < current; i++){
                x = x/2;
            }
            x = offsetX + x;
            show.addView(new CircleView(HuffmanActivity.this, x, offsetY + 150, 47,root.right.weight + "", this.paint));
            show.addView(new LineView(HuffmanActivity.this,offsetX + 33.3f, offsetY + 33.3f, x - 33.3f, offsetY + 116.7f,"", this.paint));
            showHuffman(root.right, x, offsetY + 150, ++current);
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
                //fileName.setText(commonFilePath);
            }
        }
    }
}
