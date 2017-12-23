package com.example.newbies.myapplication.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import com.example.newbies.myapplication.activity.studyActivity.MazeActivity;
import com.example.newbies.myapplication.util.MyList;

/**
 *
 * @author NewBies
 * @date 2017/12/22
 */
public abstract class BaseListAdapter<E> extends RecyclerView.Adapter{

    /**
     * 用于回调的接口
     */
    public ListAdapt.OnItemClickListener onItemClickListener;
    /**
     * 用于存储数据
     */
    public MyList<E> data;
    /**
     * 上下文
     */
    public Context context;

    /**
     * 加载的资源文件ID
     */
    public int resourceId;

    public BaseListAdapter(Context context,MyList<E> data, int resourceId){
        this.context = context;
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
    public void setOnItemClickListener(ListAdapt.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public abstract void setItemChanged(int position);
}
