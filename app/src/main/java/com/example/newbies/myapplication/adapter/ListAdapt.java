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
public abstract class ListAdapt extends RecyclerView.Adapter{

    public MyList<String> data;
    public int resourceId;
    public OnItemClickListener onItemClickListener;

    public ListAdapt(MyList<String> data, int resourceId){
        this.data = data;
        this.resourceId = resourceId;

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


    /**
     * 在指定位置添加指定数据
     * @param position
     * @param data
     */
    public void  add(int position, String data){
        if(position < this.data.size()){
            this.data.set(position,data);
        }
        else{
            this.data.add(data);
        }
        //通知添加了数据
        this.notifyItemInserted(position);
        //通知数据发生了改变
        this.notifyDataSetChanged();
        //通知组件大小发生了改变
        this.notifyItemChanged(position);
        //这里，我发现了一个问题，就是在通知相关改变时，还没来得及通知，下面这条语句就，执行了，然后就可能报错，很尴尬呀
        setItemText(position, (String) data);
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

    public abstract void setItemText(int position, String text);
}
