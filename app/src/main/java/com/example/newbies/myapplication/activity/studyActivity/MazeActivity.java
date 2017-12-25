package com.example.newbies.myapplication.activity.studyActivity;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;
import com.example.newbies.myapplication.adapter.MazeAdapter;
import com.example.newbies.myapplication.adapter.SavedMazesAdapter;
import com.example.newbies.myapplication.util.MazeModel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author NewBies
 * @date 2017/12/20
 */
public class MazeActivity extends BaseActivity implements View.OnClickListener{

    /**
     * 显示迷宫
     */
    private RecyclerView mazeView;
    private TextView marginLeft;
    private TextView marginRight;
    /**
     * 开始按钮
     */
    private Button start;
    /**
     * 随机创建迷宫
     */
    private Button randomStart;
    /**
     * 保存迷宫
     */
    private Button saveMaze;
    /**
     * 读取迷宫
     */
    private Button readMaze;
    /**
     * 操作迷宫的菜单
     */
    private View mazeMenu;
    /**
     * 显示已经保存了的迷宫
     */
    private View showSavedMazes;
    private RecyclerView savedMazes;
    private ViewPager viewPager;
    private ArrayList<View> views;
    private SavedMazesAdapter savedMazesAdapter;
    /**
     * 用于显示迷宫
     */
    private MazeAdapter mazeAdapter;
    private GridLayoutManager gridLayoutManager;
    /**
     * 迷宫模型
     */
    private MazeModel mazeModel;
    /**
     * 储存迷宫的二维数组
     */
    private int[][] mazeData;
    /**
     * 迷宫路径
     */
    private ArrayList<Integer> mazeWay;
    /**
     * SD卡路径
     */
    private String sdCardPath;
    /**
     * 设置保存迷宫名字的弹窗
     */
    private PopupWindow setMazeNamePop;
    /**
     * 取消保存
     */
    private Button cancel;
    /**
     * 确认保存
     */
    private Button sure;
    /**
     * 迷宫的名字
     */
    private EditText mazeName;
    /**
     * 所有文件
     */
    private ArrayList<File> allMazeFile;
    private File file;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏，该句话一定要放在setContentView(R.layout.huffman);之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.maze);
        getPermissions();
        initData();
        initView();
        initViewPager();
        initListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        //设置为横屏
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public void initData(){
        allMazeFile = new ArrayList<>();
        mazeModel = new MazeModel(8,8);
        mazeData = mazeModel.getMaze();
        //判断手机是否存在SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //获取SD卡的当前的工作
            sdCardPath  = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        //创建文件
        file = new File(sdCardPath + "/Maze/");
        //如果文件存在，则进行读取
        if(file.exists()){
            //获取到该文件夹下的所有文件
            readAllMaze(file.listFiles());
        }
    }

    @Override
    public void initView() {
        //查找相关组件
        mazeView = (RecyclerView)findViewById(R.id.maze);

        //设置RecyclerView

        marginLeft = (TextView)findViewById(R.id.marginleft);
        marginRight = (TextView)findViewById(R.id.marginright);
        marginLeft.setWidth((width - height)/4);
        marginRight.setWidth((width - height)/4);
        gridLayoutManager = new GridLayoutManager(this,mazeData[0].length);
        mazeAdapter = new MazeAdapter(mazeData,height/(mazeData[0].length));
        mazeView.setLayoutManager(gridLayoutManager);
        mazeView.setAdapter(mazeAdapter);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
    }

    /**
     * 初始化ViewPager
     */
    public void initViewPager() {
        views = new ArrayList<>();
        LayoutInflater inflater = getLayoutInflater();
        //获得应该在ViewPager中显示的视图
        mazeMenu = inflater.inflate(R.layout.maze_menu,null);
        showSavedMazes = inflater.inflate(R.layout.show_saved_mazes,null);
        //获得视图中相应的组件
        start = (Button)mazeMenu.findViewById(R.id.start);
        randomStart = (Button)mazeMenu.findViewById(R.id.creatMaze);
        saveMaze = (Button)mazeMenu.findViewById(R.id.saveMaze);
        readMaze = (Button)mazeMenu.findViewById(R.id.readMaze);
        //设置显示已存储迷宫的RecyclerView
        savedMazes = (RecyclerView) showSavedMazes.findViewById(R.id.savedMaze);
        savedMazes.setLayoutManager(new LinearLayoutManager(this));
        savedMazesAdapter = new SavedMazesAdapter(this,allMazeFile);
        savedMazes.setAdapter(savedMazesAdapter);
        //设置事件监听器
        savedMazesAdapter.setOnItemClickListener(new SavedMazesAdapter.OnClickListener() {
            @Override
            public void onClick(File file) {
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
                    mazeModel = (MazeModel) objectInputStream.readObject();
                    mazeData = mazeModel.getMaze();
                    gridLayoutManager.setSpanCount(mazeData[0].length);
                    mazeAdapter.setSide(height/(mazeData[0].length));
                    mazeAdapter.setMazeData(mazeData);
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLongClick(final File file) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MazeActivity.this);
                dialog.setTitle("");
                dialog.setCancelable(false);
                dialog.setMessage("确认删除？");
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        file.delete();
                        savedMazesAdapter.delete(file);
                    }
                });
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭提示框
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        views.add(showSavedMazes);
        views.add(mazeMenu);

        /**
         * 用于切换视图的适配器
         */
        PagerAdapter viewPagerAdaper = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == views.get((int)object);
            }

            /**
             * 该方法做了两件事，一，将要呈现的View加入到container中，第二，将代表该View的唯一值返回
             * 注意：这个方法很神奇，他不仅要加载当前要显示的界面进入container中，还要自动的将他认为即将
             *       使用界面加入到container中，这是为了确保在finishUpdate返回this is be done!并且在
             *       finishUpdate方法执行后，还会自动的加载新的他认为要加载的页面加载到container中
             * @param container
             * @param position
             * @return
             */
            @Override
            public Object instantiateItem(ViewGroup container,int position){
                container.addView(views.get(position));
                return position;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object){
                container.removeView(views.get(position));
            }

            /**
             * 每次启动界面时，就会调用这个方法
             * @param container
             */
            @Override
            public void startUpdate(ViewGroup container){
                int position = viewPager.getCurrentItem();
            }
        };
        //为viewPager设置适配器
        viewPager.setAdapter(viewPagerAdaper);

    }

    @Override
    public void initListener() {
        start.setOnClickListener(this);
        randomStart.setOnClickListener(this);
        saveMaze.setOnClickListener(this);
        readMaze.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                //查找路径
                mazeWay = mazeModel.findWay();
                //显示路径
                mazeAdapter.findPath(mazeWay);
                break;
            case R.id.creatMaze:
                //新建模型
                int width = 10 + (int)(Math.random() * 4) - (int)(Math.random() * 2);
                this.mazeModel = new MazeModel(width,width);
                gridLayoutManager.setSpanCount(width);
                mazeData = mazeModel.getMaze();
                mazeAdapter.setSide(height/(mazeData[0].length));
                mazeAdapter.setMazeData(mazeData);
                break;
            case R.id.saveMaze:
                showSaveMazePop();
                break;
            case R.id.readMaze:
                break;
            default:break;
        }
    }

    public void showSaveMazePop(){
        if(setMazeNamePop == null){
            View popView = getLayoutInflater().inflate(R.layout.set_maze_name_pop,null);
            setMazeNamePop = new PopupWindow(popView,width/2, ViewGroup.LayoutParams.WRAP_CONTENT,true);
            setMazeNamePop.showAtLocation(saveMaze, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
            //查找popWindow中的相关组件
            cancel = (Button)popView.findViewById(R.id.cancel);
            sure = (Button)popView.findViewById(R.id.sure);
            mazeName = (EditText)popView.findViewById(R.id.nameForMaze);
            //点击取消，关闭弹窗
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setMazeNamePop.dismiss();
                }
            });
            //点击确定，保存迷宫数据
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = mazeName.getText().toString();
                    if(!name.equals("")){
                        saveFile(name);
                        setMazeNamePop.dismiss();
                    }
                }
            });
            setMazeNamePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1.0f);
                }
            });
            setBackgroundAlpha(0.3f);
        }
        else{
            setMazeNamePop.showAtLocation(saveMaze, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
            setBackgroundAlpha(0.3f);
        }
    }

    /**
     * 遍历文件夹，读取所有迷宫
     * @param files
     */
    public void readAllMaze(File[] files){
        if (files != null) {
            //遍历文件夹中所有文件
            for(File file : files){
                //如果遍历到的也是一个文件夹，那么就递归调用该方法，继续调用
                if(file.isDirectory()){
                    //递归查找子目录中的文件
                    readAllMaze(file.listFiles());
                }
                else{
                    if(file.getName().endsWith(".dat")){
                        allMazeFile.add(file);
                    }
                }
            }
        }
    }

    public void saveFile(String name){
        File dir = new File(sdCardPath + "/Maze/");
        //如果该文件夹不存在，那么创建它
        if(!dir.exists()){
            dir.mkdirs();
        }

        final File mazeFile = new File(sdCardPath + "/Maze/" + name + ".dat");

        if (mazeFile.exists()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MazeActivity.this);
            dialog.setTitle("");
            dialog.setCancelable(false);
            dialog.setMessage("该文件已存在，是否覆盖？");
            dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    writeToFile(mazeFile);
                }
            });
            dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //关闭提示框
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            writeToFile(mazeFile);
            allMazeFile.add(mazeFile);
            //通知数据发生了改变
            savedMazesAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 存迷宫
     * @param file
     */
    public void writeToFile(File file) {
        //写入文件
        try {
            //如果文件不存在的话，这行语句也必须要，别问我为什么，我也不知道
            file.createNewFile();
            //创建二进制文件流
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            //写入文件
            objectOutputStream.writeObject(mazeModel);
            //关闭二进制流
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 动态获取相关权限
     */
    public void getPermissions(){
        int write = ContextCompat.checkSelfPermission(MazeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //动态获取创建和删除文件的权限
        if(write != PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
        //适配小米
        else{
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int checkOp = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_WRITE_EXTERNAL_STORAGE, Process.myUid(), getPackageName());
            }
            if (checkOp != AppOpsManager.MODE_ALLOWED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        }
        //动态获取向SD卡写入文件的权限
        if(ContextCompat.checkSelfPermission(MazeActivity.this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MazeActivity.this,new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},2);
        }
    }
}
