package com.example.newbies.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.newbies.myapplication.R;

import java.util.ArrayList;

/**
 * @author NewBies
 * @date 2017/12/1
 */
public class TableRecycleView{

    private Activity context;
    private RecyclerView recyclerView;
    private int recyclerViewId;
    private int column;
    private ArrayList<String> data;
    private int viewGroupId;
    private int[] colorIds;


    public TableRecycleView(Activity context, int viewGroupId, ArrayList<String> data, int column){
        this.context = context;
        this.viewGroupId = viewGroupId;
        this.data = data;
        this.column = column;
    }


    public void init(){
        GridLayoutManager layoutManager = new GridLayoutManager(context,column);
        recyclerView = (RecyclerView)context.findViewById(R.id.table);
        recyclerView.setLayoutManager(layoutManager);
        TableAdapter tableAdapter = new TableAdapter(viewGroupId, data, column);
        tableAdapter.setColor(colorIds);
        recyclerView.setAdapter(tableAdapter);
    }

    public void setColor(int...colorIds){
        this.colorIds = colorIds;
    }

}


class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder>{

    /**
     * 加载的布局id
     */
    private int viewGroupId;
    /**
     * 上下文对象
     */
    private static Context context;
    /**
     * 表格列的数量
     */
    private static int column;
    /**
     * 表格中各个方框的数据
     */
    private ArrayList<String> data;
    private int[] colorIds;

    static class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = new TextView(context);
        }
    }

    public TableAdapter(int viewGroupId, ArrayList<String> data, int column){
        this.viewGroupId = viewGroupId;
        this.data = data;
        TableAdapter.column = column;
    }

    public void setColor(int...colorIds){
        this.colorIds = colorIds;
    }

    /**
     * 加载子布局，同时可以设置相关相应事件
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(viewGroupId,parent,false);
        TableAdapter.ViewHolder viewHolder = new TableAdapter.ViewHolder(view);

        return viewHolder;
    }

    /**
     * 为每个特定的组件设置属性
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
        if(colorIds.length > 0){
            holder.textView.getResources().getColor(colorIds[(int)position/column%colorIds.length]);
        }
    }

    /**
     * 这个返回的大小决定了显示出来的RecycleView的大小
     * @return
     */
    @Override
    public int getItemCount() {
        return data.size();
    }
}
class Column{
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
