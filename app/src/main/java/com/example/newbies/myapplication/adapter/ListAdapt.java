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
 * @date 2017/12/8
 */
public class ListAdapt<E> extends RecyclerView.Adapter<ListAdapt.ViewHolder>{

    private MyList<E> data;
    private int resourceId;
    private ArrayList<ViewHolder> viewHolders;
    private OnItemClickListener onItemClickListener;

    public ListAdapt(MyList<E> data, int resourceId){
        this.data = data;
        this.resourceId = resourceId;
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

    /**
     * 方便RecyclerView中item进行响应事件的回调接口
     */
    interface OnItemClickListener{
        /**
         * 点击回调事件
         * @param position
         */
        void onClick(int position);

        /**
         * 长按回调事件
         * @param position
         */
        void onLongClick(int position);
    }

    /**
     * 设置item的点击与长按事件监听器
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        this.viewHolders.add(holder);
        holder.textView.setText((String) data.get(position));
        holder.index.setText(position + "");
    }

    @Override
    public int getItemCount() {
        if(data instanceof MyArrayList){
            return ((MyArrayList) data).getTrueSize();
        }
        else{
            return data.size();
        }
    }

    /**
     * 在指定位置添加指定数据
     * @param position
     * @param data
     */
    public void  add(int position, E data){
        if(position < this.data.size()){
            this.data.set(position,data);
        }
        else{
            this.data.add(data);
        }
        this.setItemText(position, (String) data);
        //通知添加了数据
        this.notifyItemInserted(position);
        //通知数据发生了改变
        this.notifyDataSetChanged();
        //通知组件大小发生了改变
        this.notifyItemChanged(position);
    }

    /**
     * 删除指定位置的item
     * @param position
     */
    public void remove(int position){
        this.data.remove(position);
        //删除指定位置的item
        this.notifyItemRemoved(position);
        //通知数据发生了改变
        this.notifyDataSetChanged();
        //通知组件大小发生了改变
        this.notifyItemChanged(position);
    }

    public void setItemText(int position, String text){
        this.viewHolders.get(position).textView.getResources().getColor(R.color.colorPrimaryDark);
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
