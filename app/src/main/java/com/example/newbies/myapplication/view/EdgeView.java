package com.example.newbies.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;

/**
 *
 * @author NewBies
 * @date 2017/12/27
 */

public class EdgeView extends AppCompatTextView{
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private Paint paint;

    public EdgeView(Context context){
        super(context);
    }

    public EdgeView(Context context, float startX, float startY, float endX, float endY, String text, Paint paint){
        super(context);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.paint = paint;
    }

    /**
     * 重绘起点坐标
     * @param startX
     * @param startY
     */
    public void resetStartPoint(float startX, float startY){
        this.startX = startX;
        this.startY = startY;
        //该方法会自动调用onDraw方法
        this.invalidate();
    }

    /**
     * 重绘终点坐标
     * @param endX
     * @param endY
     */
    public void resetEndPoint(float endX, float endY){
        this.endX = endX;
        this.endY = endY;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(paint != null){
            double cos = Math.cos(Math.atan(Math.abs(endY - startY)/Math.abs(endX - startX)));
            double sin = Math.sin(Math.atan(Math.abs(endY - startY)/Math.abs(endX - startX)));
            //计算距离圆心的偏移量
            int offsetX = (int) (50*cos);
            int offsetY = (int) (50*sin);
            float trueStartX = 0;
            float trueStartY = 0;
            float trueEndX = 0;
            float trueEndY = 0;
            if(endX > startX){
                trueStartX = startX + offsetX;
                trueEndX = endX - offsetX;
            }
            else {
                trueStartX = startX - offsetX;
                trueEndX = endX + offsetX;
            }
            if(endY > startY){
                trueStartY = startY + offsetY;
                trueEndY = endY - offsetY;
            }
            else {
                trueStartY = startY - offsetY;
                trueEndY = endY + offsetY;
            }
            canvas.drawLine(trueStartX, trueStartY, trueEndX, trueEndY, paint);
            canvas.drawCircle(trueStartX,trueStartY,10,paint);
        }
    }
}
