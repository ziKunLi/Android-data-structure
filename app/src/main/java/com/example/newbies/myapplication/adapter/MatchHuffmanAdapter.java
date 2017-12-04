package com.example.newbies.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newbies.myapplication.R;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author NewBies
 * @date 2017/12/4
 */
public class MatchHuffmanAdapter extends  RecyclerView.Adapter<MatchHuffmanAdapter.ViewHolder>{
    public ArrayList<String> key = null;
    public ArrayList<String> value = null;
    public Context context = null;

    public MatchHuffmanAdapter(TreeMap<String, String> data){
        key = new ArrayList<>();
        value = new ArrayList<>();
        //将键值分别存入两个list
       for(String key : data.keySet()){
           this.key.add(key);
           this.value.add(data.get(key));
       }
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView code_key;
        public TextView code_value;

        public ViewHolder(View itemView) {
            super(itemView);
            this.code_key = (TextView)itemView.findViewById(R.id.code_key);
            this.code_value = (TextView)itemView.findViewById(R.id.code_value);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.code_key_value_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.code_key.setText(key.get(position));
        holder.code_value.setText(value.get(position));
    }

    @Override
    public int getItemCount() {
        return key.size();
    }
}
