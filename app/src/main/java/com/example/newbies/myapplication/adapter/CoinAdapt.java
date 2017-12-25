package com.example.newbies.myapplication.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.util.CoinsModel;
import com.example.newbies.myapplication.util.GameMode;

import java.util.ArrayList;

/**
 *
 * @author NewBies
 * @date 2017/12/5
 */
public class CoinAdapt extends RecyclerView.Adapter<CoinAdapt.ViewHolder>{

    /**
     * 每一个item的边长
     */
    private int side;
    /**
     * 随机开始时的数据
     */
    private ArrayList<Character> data;
    private Context context;
    /**
     * 多枚硬币模型
     */
    private CoinsModel coinsModel;
    private int row;
    /**
     * 用于存储当前显示的硬币，方便未来对它们的操作
     */
    private ArrayList<TextView> textViews;

    public CoinAdapt(ArrayList<Character> data, int side, CoinsModel coinsModel){
        this.data = data;
        this.side = side;
        this.coinsModel = coinsModel;
        this.textViews = new ArrayList<>();
        this.row = (int) Math.sqrt(data.size());
    }

    public ArrayList<TextView> getTextViews() {
        return textViews;
    }

    static class  ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.coin);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.coin_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //将每一枚硬币都设置成正方形
        holder.textView.setWidth(side);
        holder.textView.setHeight(side);
        holder.textView.setText(data.get(position) + "");
        textViews.add(holder.textView);
        //为每枚硬币设置事件响应
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCoin(position);
            }
        });
    }

    /**
     * 翻转所有相邻硬币，并返回翻转后的索引值
     * @param position
     * @return
     */
    public void modifyCoin( int position){
        int row = position / this.row;
        int column = position % this.row;

        flipACell( row, column);
        //根据不同的游戏模式，进行不同的翻转
        if(coinsModel.getGame_mode() == GameMode.STRIGHT){
            flipACell(row - 1, column);
            flipACell(row + 1, column);
            flipACell(row, column - 1);
            flipACell(row, column + 1);
        }
        else {
            flipACell(row - 1, column - 1);
            flipACell( row - 1, column + 1);
            flipACell( row + 1, column - 1);
            flipACell( row + 1, column + 1);
        }
    }

    /**
     * 翻转单枚硬币
     * @param row
     * @param column
     */
    public void flipACell(int row, int column){
        if(row >= 0 && row <= this.row - 1  && column >= 0 && column <= this.row - 1 ){

            if(data.get(row * this.row + column) == 'H'){
                //将H变为T
                data.set(row * this.row + column, 'T');
                textViews.get(row * this.row + column).setText("T");
            }
            else{
                //将T变为H
                data.set(row * this.row + column, 'H');
                textViews.get(row * this.row + column).setText("H");
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setSide(int side) {
        this.side = side;
    }

    public void setData(ArrayList<Character> data) {
        this.data = data;
        this.textViews.clear();
        notifyDataSetChanged();
    }

    public ArrayList<Character> getData(){
        return this.data;
    }

    public void setCoinsModel(CoinsModel coinsModel) {
        this.coinsModel = coinsModel;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public char[] getNodes(){
        char[] node = new char[data.size()];
        for(int i = 0; i < data.size(); i++){
            node[i] = data.get(i);
        }
        return node;
    }
}
