package com.example.newbies.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newbies.myapplication.R;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author NewBies
 * @date 2017/12/20
 */
public class SavedMazesAdapter extends RecyclerView.Adapter<SavedMazesAdapter.ViewHolder> {

    private Context context;
    private  ArrayList<File> files;
    private OnClickListener onClickListener;

    public SavedMazesAdapter(Context context, ArrayList<File> files){
        this.context = context;
        this.files = files;
    }

    /**
     * 用于回调的接口
     */
    public interface OnClickListener{
        /**
         * 单击事件
         */
        void onClick(File file);

        /**
         * 长按事件
         */
        void onLongClick(File file);
    }

    public void setOnItemClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView savedMazeName;

        public ViewHolder(View itemView) {
            super(itemView);
            savedMazeName = (TextView)itemView.findViewById(R.id.savedMazeName);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.saved_mazes_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.savedMazeName.setText(files.get(position).getName());
        //设置相关的事件响应
        holder.savedMazeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(files.get(position));
            }
        });
        holder.savedMazeName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClickListener.onLongClick(files.get(position));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    /**
     * 添加文件
     * @param file
     */
    public void add(File file){
        files.add(file);
        //在指定位置插入数据
        notifyItemInserted(files.size() - 1);
        //通知数据发生了改变
        notifyDataSetChanged();
        //通知组件大小发生了改变
        notifyItemChanged(files.size() - 1);
    }
    /**
     * 删除文件
     * @param file
     */
    public void delete(File file){
        files.remove(file);
        notifyDataSetChanged();
    }
}
