package com.example.newbies.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbies.myapplication.R;

import java.util.ArrayList;

/**
 *
 * @author NewBies
 * @date 2017/12/24
 */
public class CoinAnswerAdapter extends RecyclerView.Adapter<CoinAnswerAdapter.ViewHolder> {

    /**
     * 上下文对象
     */
    private Context context;
    /**
     * 数据
     */
    private  ArrayList<Character> data;
    private ArrayList<Integer> diff;
    /**
     * 组件的边长
     */
    private int side;
    private View view;

    public CoinAnswerAdapter(Context context, ArrayList<Character> data, int side, ArrayList<Integer> diff){
        this.context = context;
        this.data = data;
        this.side = side;
        this.diff = diff;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.coin);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.coin_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setHeight(side);
        holder.textView.setWidth(side);
        holder.textView.setText(data.get(position) + "");
        //将变化了的部分变成红色
        if(diff.contains(position)){
            holder.textView.setBackgroundResource(R.drawable.coin_diff_item);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Character> data){
        this.data = data;
        notifyDataSetChanged();
    }
}
