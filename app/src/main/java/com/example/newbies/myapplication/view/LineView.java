package com.example.newbies.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 *
 * @author NewBies
 * @date 2017/11/30
 */
public class LineView extends View {

    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private Paint paint;

    public LineView(Context context){
        super(context);
    }

    public LineView(Context context, float startX, float startY, float endX, float endY, String text, Paint paint){
        super(context);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.paint = paint;
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(paint != null){
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }
}
