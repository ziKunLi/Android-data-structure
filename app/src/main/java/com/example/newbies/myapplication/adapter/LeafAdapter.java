package com.example.newbies.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.enity.HuffmanLeaf;

import java.util.List;

/**
 *
 * @author NewBies
 * @date 2017/11/27
 */
public class LeafAdapter extends ArrayAdapter {

    /**
     * 子布局ID
     */
    private int resourceId;

    class ViewHolder{
        TextView leaf1;
        TextView leaf2;
        TextView leaf3;
        TextView leaf4;
        TextView leaf5;
        TextView leaf6;
        TextView leaf7;
        TextView leaf8;
        TextView leaf9;
        TextView leaf10;
        TextView leaf11;
        TextView leaf12;
        TextView leaf13;
        TextView leaf14;
        TextView leaf15;
        TextView leaf16;
        TextView leaf17;
        TextView leaf18;
    }

    public LeafAdapter(@NonNull Context context, int resource, List<Integer> data) {
        super(context, resource, data);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //获取当前leaf的实例
//        HuffmanLeaf leaf = (HuffmanLeaf) getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.leaf1 = (TextView)view.findViewById(R.id.leaf1);
            viewHolder.leaf2 = (TextView)view.findViewById(R.id.leaf2);
            viewHolder.leaf3 = (TextView)view.findViewById(R.id.leaf3);
            viewHolder.leaf4 = (TextView)view.findViewById(R.id.leaf4);
            viewHolder.leaf5 = (TextView)view.findViewById(R.id.leaf5);
            viewHolder.leaf6 = (TextView)view.findViewById(R.id.leaf6);
            viewHolder.leaf7 = (TextView)view.findViewById(R.id.leaf7);
            viewHolder.leaf8 = (TextView)view.findViewById(R.id.leaf8);
            viewHolder.leaf9 = (TextView)view.findViewById(R.id.leaf9);
            viewHolder.leaf10 = (TextView)view.findViewById(R.id.leaf10);
            viewHolder.leaf11= (TextView)view.findViewById(R.id.leaf11);
            viewHolder.leaf12 = (TextView)view.findViewById(R.id.leaf12);
            viewHolder.leaf13 = (TextView)view.findViewById(R.id.leaf13);
            viewHolder.leaf14 = (TextView)view.findViewById(R.id.leaf14);
            viewHolder.leaf15 = (TextView)view.findViewById(R.id.leaf15);
            viewHolder.leaf16 = (TextView)view.findViewById(R.id.leaf16);
            viewHolder.leaf17 = (TextView)view.findViewById(R.id.leaf17);
            viewHolder.leaf18 = (TextView)view.findViewById(R.id.leaf18);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        return  view;
    }
}
