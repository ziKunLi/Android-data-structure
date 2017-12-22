package com.example.newbies.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.studyActivity.CoinActivity;
import com.example.newbies.myapplication.activity.studyActivity.FarmerCrossRiverActivity;
import com.example.newbies.myapplication.activity.studyActivity.HuffmanActivity;
import com.example.newbies.myapplication.activity.studyActivity.JDCShowActivity;
import com.example.newbies.myapplication.activity.studyActivity.MazeActivity;
import com.example.newbies.myapplication.activity.studyActivity.PokerGameActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NewBies
 * @date 2017/11/26
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    public List<String> list = new ArrayList<>();
    public Context context = null;
    public Intent intent = new Intent();

    public RecycleAdapter(List<String> data){
        this.list = data;
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{

        CheckedTextView button = null;
        TextView text = null;
        public ViewHolder(View itemView) {
            super(itemView);
            button = (CheckedTextView)itemView.findViewById(R.id.button);
            text = (TextView)itemView.findViewById(R.id.text);
        }
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
        View view = LayoutInflater.from(context).inflate(R.layout.recyle_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    /**
     * 为每个特定的组件设置属性
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.text.setText(list.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:intent.setClass(context, HuffmanActivity.class);break;
                    case 1:intent.setClass(context, PokerGameActivity.class);break;
                    case 2:intent.setClass(context, CoinActivity.class);break;
                    case 3:intent.setClass(context, JDCShowActivity.class);break;
                    case 4:intent.setClass(context, FarmerCrossRiverActivity.class);break;
                    case 5:intent.setClass(context, MazeActivity.class);break;
                    default:break;
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
