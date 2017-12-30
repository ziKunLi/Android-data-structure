package com.example.newbies.myapplication.activity.studyActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;
import com.example.newbies.myapplication.adapter.ArrayListAdapter;
import com.example.newbies.myapplication.adapter.BaseListAdapter;
import com.example.newbies.myapplication.adapter.LinkedListAdapter;
import com.example.newbies.myapplication.adapter.ListAdapt;
import com.example.newbies.myapplication.util.BinaryTree;
import com.example.newbies.myapplication.util.HuffmanTree;
import com.example.newbies.myapplication.util.LineUtil;
import com.example.newbies.myapplication.util.MyArrayList;
import com.example.newbies.myapplication.util.MyLinkedList;
import com.example.newbies.myapplication.util.MyList;
import com.example.newbies.myapplication.view.CircleView;
import com.example.newbies.myapplication.view.LineView;
import com.example.newbies.myapplication.view.VertexView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author NewBies
 * @date 2017/12/7
 */
public class JDCShowActivity extends BaseActivity implements View.OnClickListener{

    private static final int SIZE = 8;
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
    private BaseListAdapter<String> arrayListAdapter;
    private BaseListAdapter<String> linkedListAdapter;
    private BaseListAdapter<String> queueAdapter;
    private BaseListAdapter<String> stackAdapter;
    /**
     * 用于填充RecyclerAdapt的数据
     */
    private MyList<String> arrayListData;
    private MyList<String> linkedListData;
    private MyList<String> queueData;
    private MyList<String> stackData;
    /**
     * 抽屉菜单
     */
    private DrawerLayout jdc_select;
    /**
     * ArrayList抽屉菜单按钮
     */
    private EditText arrayListVaule;
    private EditText arrayListIndex;
    private Button arrayListSearch;
    private Button arrayListDelete;
    private Button arrayListInsert;
    private Button trimToSize;

    /**
     * LinkedList抽屉菜单按钮
     */
    private EditText linkedListValue;
    private EditText linkedListIndex;
    private Button linkedListSearch;
    private Button linkedListDelete;
    private Button linkedListInsert;

    /**
     * stack抽屉菜单按钮
     */
    private EditText stackValue;
    private Button push;
    private Button pop;

    /**
     * queue抽屉菜单按钮
     */
    private EditText queueValue;
    private Button enqueue;
    private Button dequeue;
    /**
     * bst抽屉菜单按钮
     */
    private EditText bstValue;
    private Button bstSearch;
    private Button bstAdd;
    private Button bstDelete;
    /**
     * graph顶部操作按钮
     */
    private LinearLayout addSite;
    private EditText startVertex;
    private EditText endVertex;
    private LinearLayout dfs;
    private LinearLayout bfs;
    private LinearLayout shortestPath;
    private LinearLayout minTree;
    /**
     * 对已经绘制图形的撤销，前进，刷新操作
     */
    private FloatingActionsMenu graphFloatButton;
    private FloatingActionButton updata;
    private FloatingActionButton redo;
    private FloatingActionButton undo;
    /**
     * 底部操作栏菜单
     */
    private LinearLayout arrayListBar;
    private LinearLayout linkedListBar;
    private LinearLayout stackBar;
    private LinearLayout queueBar;
    private LinearLayout bstBar;
    private LinearLayout graphBar;
    /**
     * 底部操作栏菜单的高度
     */
    private int bottomHeight;
    /**
     * 显示底部操作菜单的动画
     */
    private ObjectAnimator showArrayListBar;
    private ObjectAnimator showLinkedListBar;
    private ObjectAnimator showStackBar;
    private ObjectAnimator showQueueBar;
    private ObjectAnimator showBstBar;
    private ObjectAnimator showGraphBar;
    private ObjectAnimator showFloatButton;
    /**
     * 关闭底部操作菜单的动画
     */
    private ObjectAnimator closeArrayListBar;
    private ObjectAnimator closeLinkedListBar;
    private ObjectAnimator closeStackBar;
    private ObjectAnimator closeQueueBar;
    private ObjectAnimator closeGraphBar;
    private ObjectAnimator closeBstBar;
    private ObjectAnimator closeFloatButton;
    /**
     * 用于展示提示消息的弹窗
     */
    private AlertDialog.Builder dialog;
    /**
     * 用于绘制图和树的画笔
     */
    private Paint paint;
    /**
     * 绘制图形的画布
     */
    private FrameLayout mainView;
    /**
     * 二叉查找树
     */
    private BinaryTree<Integer> binaryTree;

    private FrameLayout.LayoutParams layoutParams;

    private int vertexCurrent = 0;
    private ArrayList<Vertex> vertices;
    /**
     * 绘制图时的工具类
     */
    private LineUtil lineUtil;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏，该句话一定要放在setContentView(R.layout.huffman);之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.jdc_show);

        initData();
        initView();
        initAnim();
        initPaint();
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
        arrayListData = new MyArrayList<>();
        linkedListData = new MyLinkedList<>();
        queueData = new MyLinkedList<>();
        stackData = new MyLinkedList<>();
        binaryTree = new BinaryTree<>();
        vertices = new ArrayList<>();
        lineUtil = LineUtil.getInstance();
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
        recyclerView = (RecyclerView)findViewById(R.id.listRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //设置为水平方向布局
        linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        linkedListAdapter = new LinkedListAdapter(this,linkedListData, R.layout.linked_list_item);
        arrayListAdapter = new ArrayListAdapter(this,arrayListData, R.layout.array_list_item);
        queueAdapter = new ArrayListAdapter(this,queueData,R.layout.array_list_item);
        stackAdapter = new ArrayListAdapter(this,stackData,R.layout.array_list_item);

        recyclerView.setLayoutManager(linearLayoutManager);

        //各种数据结构的底部操作栏
        arrayListBar = (LinearLayout)findViewById(R.id.arrayListBar);
        linkedListBar = (LinearLayout)findViewById(R.id.linkedListBar);
        stackBar = (LinearLayout)findViewById(R.id.stackBar);
        queueBar = (LinearLayout)findViewById(R.id.queueBar);
        bstBar = (LinearLayout)findViewById(R.id.bstBar);
        graphBar = (LinearLayout)findViewById(R.id.graph);
        //ArrayList底部操作栏相关组件
        arrayListVaule = (EditText)findViewById(R.id.arrayListValue);
        arrayListIndex = (EditText)findViewById(R.id.arrayListIndexValue);
        arrayListSearch = (Button) findViewById(R.id.arrayListSearch);
        arrayListDelete = (Button)findViewById(R.id.arrayListDelete);
        arrayListInsert = (Button)findViewById(R.id.arrayListInsert);
        trimToSize = (Button)findViewById(R.id.trimToSize);
        //LinkedList底部操作栏相关组件
        linkedListValue = (EditText)findViewById(R.id.linkedListValue);
        linkedListIndex = (EditText)findViewById(R.id.linkedListIndexValue);
        linkedListSearch = (Button) findViewById(R.id.linkedListSearch);
        linkedListDelete = (Button)findViewById(R.id.linkedListDelete);
        linkedListInsert = (Button)findViewById(R.id.linkedListInsert);
        //stack底部操作栏的相关组件
        stackValue = (EditText)findViewById(R.id.stackValue);
        push = (Button)findViewById(R.id.push);
        pop = (Button)findViewById(R.id.pop);
        //queue底部操作栏的相关组件
        queueValue = (EditText)findViewById(R.id.queueValue);
        enqueue = (Button)findViewById(R.id.enqueue);
        dequeue = (Button)findViewById(R.id.dequeue);
        //BST底部操作栏的相关组件
        bstValue = (EditText)findViewById(R.id.bstValue);
        bstSearch = (Button) findViewById(R.id.bstSearch);
        bstAdd = (Button)findViewById(R.id.bstAdd);
        bstDelete = (Button)findViewById(R.id.bstDelete);
        //graph顶部操作栏相关组件
        addSite = (LinearLayout)findViewById(R.id.addSite);
        startVertex = (EditText)findViewById(R.id.startVertex);
        endVertex = (EditText)findViewById(R.id.endVertex);
        dfs = (LinearLayout)findViewById(R.id.dfs);
        bfs = (LinearLayout)findViewById(R.id.bfs);
        shortestPath = (LinearLayout)findViewById(R.id.shortestPath);
        minTree = (LinearLayout)findViewById(R.id.minTree);
        graphFloatButton = (FloatingActionsMenu)findViewById(R.id.graphFloatButton);
        updata = (FloatingActionButton)findViewById(R.id.upData);
        undo = (FloatingActionButton)findViewById(R.id.undo);
        redo = (FloatingActionButton)findViewById(R.id.redo);
        //用于绘制图形的画布
        mainView = (FrameLayout)findViewById(R.id.mainView);
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
                arrayListBar.setVisibility(View.VISIBLE);
                //设置相应的适配器
                recyclerView.setAdapter(arrayListAdapter);
                //将用于模拟线性结构的recyclerView显示出来
                recyclerView.setVisibility(View.VISIBLE);
                //开启动画
                showArrayListBar.start();
                break;
            case R.id.linkedList:
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                linkedListBar.setVisibility(View.VISIBLE);
                //设置相应的适配器
                recyclerView.setAdapter(linkedListAdapter);
                //将用于模拟线性结构的recyclerView显示出来
                recyclerView.setVisibility(View.VISIBLE);
                //开启动画
                showLinkedListBar.start();
                break;
            case R.id.stack:
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                stackBar.setVisibility(View.VISIBLE);
                //设置相应的适配器
                recyclerView.setAdapter(stackAdapter);
                //将用于模拟线性结构的recyclerView显示出来
                recyclerView.setVisibility(View.VISIBLE);
                //开启动画
                showStackBar.start();
                break;
            case R.id.queue:
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                queueBar.setVisibility(View.VISIBLE);
                //设置相应的适配器
                recyclerView.setAdapter(queueAdapter);
                //将用于模拟线性结构的recyclerView显示出来
                recyclerView.setVisibility(View.VISIBLE);
                //开启动画
                showQueueBar.start();
                break;
            case R.id.bst:
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                bstBar.setVisibility(View.VISIBLE);
                showBstBar.start();
                binaryTree = new BinaryTree<>();
                break;
            case R.id.unWeightGraph:
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                graphBar.setVisibility(View.VISIBLE);
                showGraphBar.start();
                //用于绘制图的线条的工具类
                lineUtil.setDrawPane(this,mainView, LineUtil.GraphType.UNWEIGHT_GRAPH,paint);
                graphFloatButton.setVisibility(View.VISIBLE);
                showFloatButton.start();
                break;
            case R.id.weightGraph:
                //关闭抽屉
                jdc_select.closeDrawer(GravityCompat.START);
                graphBar.setVisibility(View.VISIBLE);
                showGraphBar.start();
                //用于绘制图的线条的工具类
                lineUtil.setDrawPane(this,mainView, LineUtil.GraphType.WEIGHT_GRAPH,paint);
                graphFloatButton.setVisibility(View.VISIBLE);
                showFloatButton.start();
                break;
            case R.id.arrayListSearch:
                break;
            case R.id.arrayListInsert:
                try {
                    if(arrayListIndex.getText().toString().equals("")){
                        arrayListData.add(Integer.parseInt(arrayListVaule.getText().toString()) + "");
                    }
                    else {
                        arrayListData.set(Integer.parseInt(arrayListIndex.getText().toString()),Integer.parseInt(arrayListVaule.getText().toString()) + "");
                    }
                    //通知数据发生了改变
                    arrayListAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.arrayListDelete:
                try {
                    //删除最后一个元素
                    if(arrayListIndex.getText().toString().equals("")){
                        arrayListData.remove(arrayListData.size() - 1);
                    }
                    //删除指定位置的元素
                    else {
                        arrayListData.remove(Integer.parseInt(arrayListIndex.getText().toString()));
                    }
                    //通知数据发生了改变
                    arrayListAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.trimToSize:
                ((MyArrayList)arrayListData).trimToSize();
                arrayListAdapter.notifyDataSetChanged();
                break;
            case R.id.linkedListInsert:
                try {
                    if(linkedListIndex.getText().toString().equals("")){
                        linkedListData.add(Integer.parseInt(linkedListValue.getText().toString()) + "");
                    }
                    else {
                        linkedListData.set(Integer.parseInt(linkedListIndex.getText().toString()),Integer.parseInt(linkedListValue.getText().toString()) + "");
                    }
                    //通知数据发生了改变
                    linkedListAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.linkedListDelete:
                try {
                    //删除最后一个元素
                    if(linkedListIndex.getText().toString().equals("")){
                        //删除指定元素
                        if(!linkedListValue.getText().toString().equals("")){
                            ((MyLinkedList<String>)linkedListData).remove(linkedListValue.getText().toString());
                        }
                        else {
                            ((MyLinkedList<String>) linkedListData).removeLast();
                        }
                    }
                    //删除指定位置的元素
                    else {
                        linkedListData.remove(Integer.parseInt(linkedListIndex.getText().toString()));
                    }
                    //通知数据发生了改变
                    linkedListAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.push:
                try {
                    //依次添加
                    stackData.add(Integer.parseInt(stackValue.getText().toString()) + "");
                    stackAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.pop:
                try {
                    //删除最后一个元素，也就是栈顶元素
                    ((MyLinkedList<String>)stackData).removeLast();
                    stackAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.enqueue:
                try {
                    //依次添加
                    queueData.add(Integer.parseInt(queueValue.getText().toString()) + "");
                    queueAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.dequeue:
                try {
                    //删除最后一个元素，也就是栈顶元素
                    ((MyLinkedList<String>)queueData).removeFirst();
                    queueAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.bstSearch:break;
            case R.id.bstAdd:
                try {
                    binaryTree.insert(Integer.parseInt(bstValue.getText().toString()));
                    if(mainView.getChildCount() > SIZE){
                        //从第6个开始删除，删除mainView.getChildCount() - 6个view，也就是把面板上的二叉查找树删除完了
                        mainView.removeViews(SIZE,mainView.getChildCount() - SIZE);
                    }
                    //绘制出根节点和相关线条
                    mainView.addView(new CircleView(this, width/2, 150f, 47f,binaryTree.getRoot().element + "",paint));
                    //绘制其他结点
                    drawBST(binaryTree.getRoot(), width/2,150f, 1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.bstDelete:
                try {
                    binaryTree.delete(Integer.parseInt(bstValue.getText().toString()));
                    if(mainView.getChildCount() > SIZE){
                        //从第6个开始删除，删除mainView.getChildCount() - 7个view，也就是把面板上的二叉查找树删除完了
                        mainView.removeViews(SIZE,mainView.getChildCount() - SIZE);
                    }
                    //绘制出根节点和相关线条
                    mainView.addView(new CircleView(this, width/2, 150f, 47f,binaryTree.getRoot().element + "",paint));
                    //绘制其他结点
                    drawBST(binaryTree.getRoot(), width/2,150f, 1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.addSite:
                final VertexView vertexView = new VertexView(this,vertexCurrent++);
                vertexView.setText(vertexCurrent + "");
                //设置内部布局
                vertexView.setGravity(Gravity.CENTER);
                vertexView.setBackgroundResource(R.drawable.vertex_item_bg);
                mainView.addView(vertexView,100,100);
                //这行语句必须在添加到了面板中才行
                layoutParams = (FrameLayout.LayoutParams) vertexView.getLayoutParams();
                //设置组件的位置
                layoutParams.setMargins(100,30,0,100);
                //刷新组件位置
                vertexView.requestLayout();
                break;
            case R.id.dfs:
                lineUtil.sendUpdataMessage();
                endVertex.setClickable(false);
                try{
                    int start = Integer.parseInt(startVertex.getText().toString()) - 1;
                    if(start < 0||start > lineUtil.getVertexSize() - 1){
                        throw new Exception();
                    }
                    lineUtil.sendDfsMessage(start);
                }catch (Exception e){
                    Toast.makeText(this, "请输入正确的起点！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bfs:
                lineUtil.sendUpdataMessage();
                endVertex.setClickable(false);
                try{
                    int start = Integer.parseInt(startVertex.getText().toString()) - 1;
                    if(start < 0||start > lineUtil.getVertexSize() - 1){
                        throw new Exception();
                    }
                    lineUtil.sendBfsMessage(start);
                }catch (Exception e){
                    Toast.makeText(this, "请输入正确的起点！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.shortestPath:
                lineUtil.sendUpdataMessage();
                endVertex.setClickable(true);
                try{
                    int start = Integer.parseInt(startVertex.getText().toString()) - 1;
                    int end = Integer.parseInt(endVertex.getText().toString()) - 1;
                    if(start < 0||start >= lineUtil.getVertexSize() - 1||end < 0||end > lineUtil.getVertexSize() - 1){
                        throw new Exception();
                    }
                    lineUtil.sendPathMessage(start, end);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(this, "请输入正确的起点或终点！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.minTree:
                lineUtil.sendUpdataMessage();
                try{
                    int start = Integer.parseInt(startVertex.getText().toString()) - 1;
                    if(start < 0||start > lineUtil.getVertexSize() - 1){
                        throw new Exception();
                    }
                    lineUtil.sendMinTreeMessage(start);
                }catch (Exception e){
                    Toast.makeText(this, "请输入正确的起点！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.upData:
                lineUtil.sendUpdataMessage();
                break;
            case R.id.undo:
                break;
            case R.id.redo:
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
                if (arrayListBar.getVisibility() == View.VISIBLE){
                    closeArrayListBar.start();
                    arrayListBar.setVisibility(View.GONE);
                }
                else if(linkedListBar.getVisibility() == View.VISIBLE){
                    closeLinkedListBar.start();
                    linkedListBar.setVisibility(View.GONE);
                }
                else if(stackBar.getVisibility() == View.VISIBLE){
                    closeStackBar.start();
                    stackBar.setVisibility(View.GONE);
                }
                else if(queueBar.getVisibility() == View.VISIBLE){
                    closeQueueBar.start();
                    queueBar.setVisibility(View.GONE);
                }
                else if(bstBar.getVisibility() == View.VISIBLE){
                    closeBstBar.start();
                    if(mainView.getChildCount() > SIZE){
                        //从第size个开始删除，删除mainView.getChildCount() - size个view，也就是把面板上的二叉查找树删除完了
                        mainView.removeViews(SIZE,mainView.getChildCount() - SIZE);
                    }
                    bstBar.setVisibility(View.GONE);
                    binaryTree = null;
                }
                else if(graphBar.getVisibility() == View.VISIBLE){
                    mainView.removeViews(SIZE,mainView.getChildCount() - SIZE);
                    graphBar.setVisibility(View.GONE);
                    vertexCurrent = 0;
                    closeGraphBar.start();
                    closeFloatButton.start();
                    graphFloatButton.setVisibility(View.GONE);
                    lineUtil.clear();
                }

                //如果用来展示模拟线性结构的recycleView还是显示着的，那么将其隐藏
                if(recyclerView.getVisibility() == View.VISIBLE){
                    recyclerView.setVisibility(View.GONE);
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
        unWeightGraph.setOnClickListener(this);
        weightGraph.setOnClickListener(this);
        //MyArrayList添加元素事件
        arrayListSearch.setOnClickListener(this);
        arrayListInsert.setOnClickListener(this);
        arrayListDelete.setOnClickListener(this);
        trimToSize.setOnClickListener(this);
        //MyLinkedList添加元素事件
        linkedListInsert.setOnClickListener(this);
        linkedListDelete.setOnClickListener(this);
        linkedListSearch.setOnClickListener(this);
        //为Stack添加元素事件
        push.setOnClickListener(this);
        pop.setOnClickListener(this);
        //为queue添加元素事件
        enqueue.setOnClickListener(this);
        dequeue.setOnClickListener(this);
        //为bst添加事件响应
        bstAdd.setOnClickListener(this);
        bstDelete.setOnClickListener(this);
        //为graph设置相应的事件响应
        addSite.setOnClickListener(this);
        dfs.setOnClickListener(this);
        bfs.setOnClickListener(this);
        minTree.setOnClickListener(this);
        shortestPath.setOnClickListener(this);
        updata.setOnClickListener(this);
    }

    /**
     * 初始化动画
     */
    public void initAnim(){
        //底部操作栏打开动画
        showArrayListBar = ObjectAnimator.ofFloat(arrayListBar, "y",height,height - 300);
        showLinkedListBar = ObjectAnimator.ofFloat(linkedListBar, "y",height,height - 300);
        showStackBar = ObjectAnimator.ofFloat(stackBar, "y",height,height - 150);
        showQueueBar = ObjectAnimator.ofFloat(queueBar, "y",height,height - 150);
        showBstBar = ObjectAnimator.ofFloat(bstBar,"y",height,height - 150);
        showGraphBar = ObjectAnimator.ofFloat(graphBar,"y",-(int)dpToPx(50),0);
        showFloatButton = ObjectAnimator.ofFloat(graphFloatButton,"y",height,height - 930);
        showArrayListBar.setDuration(500);
        showLinkedListBar.setDuration(500);
        showStackBar.setDuration(500);
        showQueueBar.setDuration(500);
        showBstBar.setDuration(500);
        showGraphBar.setDuration(500);
        showFloatButton.setDuration(500);
        //底部操作栏关闭动画
        closeArrayListBar = ObjectAnimator.ofFloat(arrayListBar, "y",height - 300, height);
        closeLinkedListBar = ObjectAnimator.ofFloat(linkedListBar,"y",height - 300, height);
        closeStackBar = ObjectAnimator.ofFloat(stackBar,"y",height - 150,height);
        closeQueueBar = ObjectAnimator.ofFloat(queueBar,"y",height - 150,height);
        closeBstBar = ObjectAnimator.ofFloat(bstBar,"y",height - 150, height);
        closeGraphBar = ObjectAnimator.ofFloat(graphBar,"y",0,-(int)dpToPx(50));
        closeFloatButton = ObjectAnimator.ofFloat(graphFloatButton,"y",height - 930,height);
        closeArrayListBar.setDuration(500);
        closeLinkedListBar.setDuration(500);
        closeStackBar.setDuration(500);
        closeBstBar.setDuration(500);
        closeGraphBar.setDuration(500);
        closeFloatButton.setDuration(500);
    }

    public void initPaint(){
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
    }

    /**
     * 绘制二叉查找树
     * @param root
     * @param offsetX 根节点的圆心X轴坐标
     * @param offsetY 根节点的圆心Y轴坐标
     * @param level 表明该节点是在第几层
     */
    public void drawBST(BinaryTree.TreeNode root, float offsetX, float offsetY, int level){
        if(root.left != null){
            //计算X轴方向的偏移量
            float x = width/2;
            for(int i = 0; i < level; i++){
                x = x/2;
            }
            x = offsetX - x;
            //绘制圆
            mainView.addView(new CircleView(this, x, offsetY + 150, 47,root.left.element + "", this.paint));
            //绘制连接线
            mainView.addView(new LineView(this,offsetX - 33.3f, offsetY + 33.3f, x + 33.3f, offsetY + 116.7f,"", this.paint));
            //递归调用，绘制下一个结点，同时其Y轴偏移量加150，也就是下一个结点向下移动
            drawBST(root.left, x, offsetY + 150, ++level);
            //优先向左遍历，如果左边的遍历完了，层数就应该依次减一，回归到第一层去遍历右节点
            level--;
        }
        if(root.right != null){
            float x = width/2;
            //计算
            for(int i = 0; i < level; i++){
                x = x/2;
            }
            x = offsetX + x;
            //绘制圆
            mainView.addView(new CircleView(this, x, offsetY + 150, 47,root.right.element + "", this.paint));
            //绘制连接线
            mainView.addView(new LineView(this,offsetX + 33.3f, offsetY + 33.3f, x - 33.3f, offsetY + 116.7f,"", this.paint));
            drawBST(root.right, x, offsetY + 150, ++level);
        }

    }

    class Vertex{
        private int x;
        private int y;

        public Vertex(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lineUtil.clear();
    }
}
