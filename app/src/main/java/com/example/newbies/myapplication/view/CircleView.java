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
     * 手机屏幕的宽
     */
    private int width;
    /**
     * 手机屏幕的高
     */
    private int height;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    /**
     * 获得屏幕的宽和高
     */
    public void init(){
        WindowManager windowManager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
    }

    /**
     * 绘制圆形
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //创建一个画笔
        Paint paint = new Paint();
        //设置画笔的颜色
        paint.setColor(Color.BLACK);
        //设置画笔的锯齿效果
        paint.setAntiAlias(true);
        //设置画笔的风格（空心或实心）
        paint.setStyle(Paint.Style.STROKE);
        //绘制圆形
        canvas.drawCircle(300,400,width/32,paint);
    }
}
