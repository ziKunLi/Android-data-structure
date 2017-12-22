package com.example.newbies.myapplication.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbies.myapplication.R;

import java.util.ArrayList;

/**
 * @author NewBies
 * @date 2017/12/20
 */
public class MazeAdapter extends RecyclerView.Adapter<MazeAdapter.ViewHolder>{

    private Context context;
    /**
     * 构成迷宫的二维数组
     */
    private int[][] mazeData;
    /**
     * 迷宫中每一个结点的边长
     */
    private int side;
    private ArrayList<ViewHolder> viewHolders;

    public MazeAdapter(int[][] mazeData, int side){
        this.mazeData = mazeData;
        this.side = side;
        viewHolders = new ArrayList<>();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        /**
         * 组成迷宫的一个个方块
         */
        private TextView mazeItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mazeItem = (TextView)itemView.findViewById(R.id.mazeItem);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.maze_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置图片的大小
        holder.mazeItem.setWidth(this.side);
        holder.mazeItem.setHeight(this.side);
        viewHolders.add(holder);
        //设置起点图标
        if(position == 0){
            holder.mazeItem.setBackgroundResource(R.drawable.ic_start);
        }
        //设置终点图标
        else if(position == getItemCount() - 1){
            holder.mazeItem.setBackgroundResource(R.drawable.ic_end);
        }
        //设置路
        else if(mazeData[position/mazeData[0].length][position%mazeData[0].length] == 0){
            holder.mazeItem.setBackgroundResource(R.color.white);
        }
        //设置墙的图标
        else{
            holder.mazeItem.setBackgroundResource(R.drawable.ic_wall);
        }
    }

    @Override
    public int getItemCount() {
        return mazeData.length * mazeData[0].length;
    }

    /**
     * 显示正确的路径
     * @param path
     */
    public void findPath(ArrayList<Integer> path){
        for(int i = 1; i < path.size() - 1; i++){
            viewHolders.get(path.get(i)).mazeItem.setBackgroundResource(R.color.riverWater);
        }
    }
    /**
     * 设置迷宫数据，用于刷新迷宫
     * @param mazeData
     */
    public void setMazeData(int[][] mazeData){
        this.mazeData = mazeData;
        //把存进去的那些view给删掉，一定要记住
        viewHolders.clear();
        notifyDataSetChanged();
    }

    /**
     * 设置迷宫每个格子的大小
     * @param side
     */
    public void setSide(int side){
        this.side = side;
    }
}
