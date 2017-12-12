package com.example.newbies.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.util.MyArrayList;
import com.example.newbies.myapplication.util.MyList;

import java.util.ArrayList;

/**
 *
 * @author NewBies
 * @date 2017/12/11
 */
public class ArrayListAdapter extends ListAdapt {

    private  ArrayList<ViewHolder> viewHolders;
    public ArrayListAdapter(MyList data, int resourceId) {
        super(data, resourceId);
        this.viewHolders = new ArrayList<>();
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        this.viewHolders.add((ViewHolder) holder);
        ((ViewHolder)holder).textView.setText((String) data.get(position));
        ((ViewHolder)holder).index.setText(position + "");
    }


    @Override
    public int getItemCount() {
        return ((MyArrayList) data).getTrueSize();
    }

    @Override
    public void setItemText(int position, String text){
//        this.viewHolders.get(position).textView.getResources().getColor(R.color.colorPrimaryDark);
        this.viewHolders.get(position).textView.setText(text);
    }

    public void trimToSize(){
        if(this.data instanceof  MyArrayList){
            ((MyArrayList)this.data).trimToSize();
            //通知数据发生了改变
            this.notifyDataSetChanged();
            //通知组件大小发生了改变
            this.notifyItemChanged(data.size());
        }
    }
}
