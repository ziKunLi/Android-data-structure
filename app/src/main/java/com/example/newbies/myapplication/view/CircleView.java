package com.example.newbies.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 *
 * @author NewBies
 * @date 2017/11/26
 */
public class CircleView extends View {


    /**
     * 圆心所在横坐标
     */
    private float x;
    /**
     * 圆心所在纵坐标
     */
    private float y;
    /**
     * 圆的半径
     */
    private float radius;
    /**
     * 用于填充的文字
     */
    private String text;
    /**
     * 画笔
     */
    private Paint paint;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, float x, float y, float radius, String text, Paint paint){
        super(context);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.text = text;
        this.paint = paint;
    }

    /**
     * 设置画笔
     * @param paint
     */
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * 得到画笔
     * @return
     */
    public Paint getPaint() {
        return paint;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * 绘制圆形
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(paint != null){
            //绘制圆形
            canvas.drawCircle(x,y,radius,paint);
            canvas.drawText(text,x - text.length()/2, y + 10,paint);
        }
    }
}
