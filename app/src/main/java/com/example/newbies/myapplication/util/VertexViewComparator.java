package com.example.newbies.myapplication.util;

import android.view.View;

import com.example.newbies.myapplication.view.VertexView;

import java.util.Comparator;

/**
 *
 * @author NewBies
 * @date 2017/12/29
 */
public class VertexViewComparator implements Comparator<View> {

    @Override
    public int compare(View o1, View o2) {
        if(o1.getId() < o2.getId()){
            return -1;
        }
        else if(o1.getId() > o2.getId()){
            return 1;
        }
        return 0;
    }
}
