package com.example.newbies.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.util.MyList;

import java.util.ArrayList;

/**
 *
 * @author NewBies
 * @date 2017/12/11
 */
public class LinkedListAdapter extends ListAdapt {

    private ArrayList<ViewHolder> viewHolders;

    public LinkedListAdapter(MyList<String> data, int resourceId) {
        super(data, resourceId);
        this.viewHolders = new ArrayList<>();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private TextView index;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.linked_list_item);
            index = (TextView)itemView.findViewById(R.id.index);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        this.viewHolders.add((ViewHolder)holder);
        ((ViewHolder)holder).textView.setText(data.get(position));
        ((ViewHolder)holder).index.setText(position + "");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void setItemText(int position, String text) {
        viewHolders.get(position).textView.setText(text);
    }
}
