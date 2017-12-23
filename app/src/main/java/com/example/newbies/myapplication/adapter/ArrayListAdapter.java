package com.example.newbies.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.util.MyArrayList;
import com.example.newbies.myapplication.util.MyLinkedList;
import com.example.newbies.myapplication.util.MyList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NewBies
 * @date 2017/12/11
 */
public class ArrayListAdapter extends BaseListAdapter<String>{

    public ArrayListAdapter(Context context, MyList<String> data, int resourceId) {
        super(context,data,resourceId);
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private TextView index;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.list_and_tree_item);
            index = (TextView)itemView.findViewById(R.id.value_index);
        }
    }

    @Override
    public ArrayListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resourceId, parent,false);
        return new ArrayListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).index.setText(position + "");
        ((ViewHolder) holder).textView.setText((String) data.get(position));
    }

    @Override
    public int getItemCount() {
        if(data instanceof MyArrayList){
            return ((MyArrayList<String>)data).getTrueSize();
        }
        return data.size();
    }

    @Override
    public void setItemChanged(int position) {
    }

}
