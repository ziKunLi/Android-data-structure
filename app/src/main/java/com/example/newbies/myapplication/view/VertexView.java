package com.example.newbies.myapplication.view;

import android.content.Context;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.util.LineUtil;

/**
 *
 * @author NewBies
 * @date 2017/12/26
 */
public class VertexView extends android.support.v7.widget.AppCompatTextView {

    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private FrameLayout.LayoutParams layoutParams;
    private boolean isChanged = false;
    private LineUtil lineUtil;
    private int id;

    public VertexView(Context context, int id) {
        super(context);
        lineUtil = LineUtil.getInstance();
        this.id = id;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        //获取手机触摸点的横坐标和纵坐标
        endX = (int)event.getX();
        endY = (int)event.getY();

        layoutParams = (FrameLayout.LayoutParams) this.getLayoutParams();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = endX;
                startY = endY;
                if(!isChanged){
                    setBackgroundResource(R.drawable.vertex_chosed_bg);
                    isChanged = true;
                }
                else{
                    setBackgroundResource(R.drawable.vertex_item_bg);
                    isChanged = false;
                }
                lineUtil.sendDrawLineStartMessage(this);
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offsetX = endX - startX;
                int offsetY = endY - startY;
                lineUtil.sendUpdataLineMessage(this);
                //调用layout方法来重新放置它的位置
                layoutParams.setMargins(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                requestLayout();
                break;
            case MotionEvent.ACTION_UP:
                if(!isChanged){
                    setBackgroundResource(R.drawable.vertex_chosed_bg);
                    isChanged = true;
                }
                else{
                    setBackgroundResource(R.drawable.vertex_item_bg);
                    isChanged = false;
                }
                lineUtil.sendDrawLineEndMessage(this);
                break;
            default:break;
        }
        return  true;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    @Override
    public int getId() {
        return id;
    }
}
