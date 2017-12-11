package com.example.newbies.myapplication.activity.studyActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;
import com.example.newbies.myapplication.adapter.ListAdapt;
import com.example.newbies.myapplication.util.MyArrayList;
import com.example.newbies.myapplication.util.MyList;

import java.util.ArrayList;

/**
 * @author NewBies
 * @date 2017/12/7
 */
public class JDCShowActivity extends BaseActivity implements View.OnClickListener{

    /**
     * 屏幕的高和宽
     */
    private int height;
    private int width;
    /**
     * 7种存储结构
     */
    private TextView arrayList;
    private TextView linkedList;
    private TextView stack;
    private TextView queue;
    private TextView bst;
    private TextView weightGraph;
    private TextView unWeightGraph;
    /**
     * 用于模拟线性结构的RecyclerView
     */
    private RecyclerView recyclerView;
    /**
     * 适配器
     */
    private ListAdapt<String> listAdapt;
    /**
     * 用于填充RecyclerAdapt的数据
     */
    private MyList<String> data;
    /**
     * 线性表中数据的当前位置，也可以叫做实际大小
     */
    private int current;
    /**
     * 抽屉菜单
     */
    private DrawerLayout jdc_select;
    /**
     * 抽屉菜单按钮
     */
    private TextView input_value;
    private TextView input_index;
    private EditText vaule;
    private EditText index;
    private Button search;
    private Button delete;
    private Button insert;
    private Button trimToSize;

    private EditText stack_and_queue_value;
    private Button push_or_enqueue;
    private Button pop_or_dequeue;

    private EditText bstValue;
    private Button bstSearch;
    private Button bstAdd;
    private Button bstDelete;
    /**
     * 底部操作栏菜单
     */
    private LinearLayout listBar;
    private LinearLayout stack_and_queue_bar;
    private LinearLayout bstBar;
    /**
     * 底部操作栏菜单的高度
     */
    private int bottomHeight;
    /**
     * 显示底部操作菜单的动画
     */
    ObjectAnimator showList_Bar;
    ObjectAnimator showStackAndQueueBar;
    ObjectAnimator showBstBar;
    ObjectAnimator showGraphBar;
    /**
     * 关闭底部操作菜单的动画
     */
    ObjectAnimator closeList_Bar;
    ObjectAnimator closeGraphBar;
    ObjectAnimator closeBstBar;
    ObjectAnimator closeStackAndQueueBar;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏，该句话一定要放在setContentView(R.layout.huffman);之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.jdc_show);

        initData();
        initView();
//        try {
//            Thread.sleep(100);
//            Thread getProperty = new Thread(new GetProperty());
//            getProperty.start();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        initAnim();
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

    public void initData(){
        data = new MyArrayList<>();
    }
    @Override
    public void initView() {

        //获取到屏幕的宽和高
        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        height = windowManager.getDefaultDisplay().getHeight();
        width = windowManager.getDefaultDisplay().getWidth();
        //初始化组件
        jdc_select = (DrawerLayout)findViewById(R.id.jdc_select);
        //七种存储结构的组件
        arrayList = (TextView)findViewById(R.id.arrayList);
        linkedList = (TextView)findViewById(R.id.linkedList);
        stack = (TextView)findViewById(R.id.stack);
        queue = (TextView)findViewById(R.id.queue);
        bst = (TextView)findViewById(R.id.bst);
        weightGraph = (TextView)findViewById(R.id.weightGraph);
        unWeightGraph = (TextView)findViewById(R.id.unWeightGraph);

        //模拟线性存储结构的RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.list_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //设置为水平方向布局
        linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        listAdapt = new ListAdapt<>(data, R.layout.array_list_item);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapt);

        //底部操作栏
        listBar = (LinearLayout)findViewById(R.id.list_bar);
        stack_and_queue_bar = (LinearLayout)findViewById(R.id.stack_and_queue_bar);
        bstBar = (LinearLayout)findViewById(R.id.bst_bar);
        //底部操作栏相关组件
        input_value = (TextView)findViewById(R.id.input_value);
        input_index = (TextView)findViewById(R.id.input_index);
        vaule = (EditText)findViewById(R.id.value);
        index = (EditText)findViewById(R.id.index);
        search = (Button) findViewById(R.id.search);
        delete = (Button)findViewById(R.id.delete);
        insert = (Button)findViewById(R.id.insert);
        trimToSize = (Button)findViewById(R.id.trimToSize);

        stack_and_queue_value = (EditText)findViewById(R.id.stack_and_queue_value);
        push_or_enqueue = (Button)findViewById(R.id.push_or_enqueue);
        pop_or_dequeue = (Button)findViewById(R.id.pop_or_dequeue);

        bstValue = (EditText)findViewById(R.id.bst_value);
        bstSearch = (Button) findViewById(R.id.bst_search);
        bstAdd = (Button)findViewById(R.id.bst_add);
        bstDelete = (Button)findViewById(R.id.bst_delete);
        //默认将抽屉打开，以便于提示用户，这里有一个抽屉
        jdc_select.openDrawer(GravityCompat.START);
    }

    /**
     * 对几乎所有响应事件进行封装，避免为每一个响应事件都new一个对象
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrayList:
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                listBar.setVisibility(View.VISIBLE);
                //如果打开了链表底部操作栏，那么这个按钮应该是被隐藏了的，这时再打开线性表时就应该将其显示
                if(trimToSize.getVisibility() == View.GONE){
                    trimToSize.setVisibility(View.VISIBLE);
                }
                //开启动画
                showList_Bar.start();
                break;
            case R.id.linkedList:
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                listBar.setVisibility(View.VISIBLE);
                //处理不同之处，linkedList没有压缩的操作，所以不应该显示这个按钮
                trimToSize.setVisibility(View.GONE);
                //开启动画
                showList_Bar.start();
                break;
            case R.id.stack:
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                stack_and_queue_bar.setVisibility(View.VISIBLE);
                //处理不同之处
                push_or_enqueue.setText("进栈");
                pop_or_dequeue.setText("出栈");
                //开启动画
                showStackAndQueueBar.start();
                break;
            case R.id.queue:
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                stack_and_queue_bar.setVisibility(View.VISIBLE);
                //处理不同之处
                push_or_enqueue.setText("入队列");
                pop_or_dequeue.setText("出队列");
                //开启动画
                showStackAndQueueBar.start();
                break;
            case R.id.bst:
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                bstBar.setVisibility(View.VISIBLE);
                showBstBar.start();
                break;
            case R.id.search:
                break;
            case R.id.insert:
                try {
                    if(index.getText().toString().equals("")){
                        listAdapt.add(current,Integer.parseInt(vaule.getText().toString()) + "");
                        current++;
                    }
                    else {
                        listAdapt.add(Integer.parseInt(index.getText().toString()), Integer.parseInt(vaule.getText().toString()) + "");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.delete:
                try {
                    if(current > 0){
                        if(index.getText().toString().equals("")){
                            current--;
                            listAdapt.remove(current);
                        }
                        else {
                            listAdapt.remove(Integer.parseInt(index.getText().toString()));
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.trimToSize:
                listAdapt.trimToSize();
                break;
            default:break;
        }
    }

    @Override
    public void initListener() {
        jdc_select.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerOpened(View drawerView) {
                //当侧滑栏打开时，如果底部操作栏是打开的，那么就把它关闭
                if (listBar.getVisibility() == View.VISIBLE){
                    closeList_Bar.start();
                    listBar.setVisibility(View.GONE);
                }
                else if(stack_and_queue_bar.getVisibility() == View.VISIBLE){
                    closeStackAndQueueBar.start();
                    stack_and_queue_bar.setVisibility(View.GONE);
                }
                else if(bstBar.getVisibility() == View.VISIBLE){
                    closeBstBar.start();
                    bstBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }
        });
        arrayList.setOnClickListener(this);
        linkedList.setOnClickListener(this);
        stack.setOnClickListener(this);
        queue.setOnClickListener(this);
        bst.setOnClickListener(this);
        search.setOnClickListener(this);
        //MyArrayList添加元素事件
        insert.setOnClickListener(this);
        delete.setOnClickListener(this);
        trimToSize.setOnClickListener(this);
    }



    /**
     * 初始化动画
     */
    public void initAnim(){
        //底部操作栏打开动画
        showList_Bar = ObjectAnimator.ofFloat(listBar, "y",height,height - 300);
        showStackAndQueueBar = ObjectAnimator.ofFloat(stack_and_queue_bar, "y",height,height - 150);
        showBstBar = ObjectAnimator.ofFloat(bstBar,"y",height,height - 150);
        showList_Bar.setDuration(500);
        showStackAndQueueBar.setDuration(500);
        showBstBar.setDuration(500);
        //底部操作栏关闭动画
        closeList_Bar = ObjectAnimator.ofFloat(listBar, "y",height - 300, height);
        closeStackAndQueueBar = ObjectAnimator.ofFloat(stack_and_queue_bar,"y",height - 150,height);
        closeBstBar = ObjectAnimator.ofFloat(bstBar,"y",height - 150, height);
        closeList_Bar.setDuration(500);
        closeStackAndQueueBar.setDuration(500);
        closeBstBar.setDuration(500);
    }
}
