package com.example.newbies.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.newbies.myapplication.R;

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
    private ArrayList<String> data;
    private Context context;

    public CoinAdapt(ArrayList<String> data, int side){
        this.data = data;
        this.side = side;
    }

    static class  ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.someCoin);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = new View(context);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //将每一枚硬币都设置成正方形
        holder.textView.setWidth(side);
        holder.textView.setHeight(side);
        //为每枚硬币设置事件响应
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
