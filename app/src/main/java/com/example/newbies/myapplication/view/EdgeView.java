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
    private Paint linePaint;
    private boolean isChanged = false;
    private boolean isEnd = false;
    private int length = 0;

    public EdgeView(Context context){
        super(context);
    }

    public EdgeView(Context context, float startX, float startY, float endX, float endY, String text, Paint paint){
        super(context);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.linePaint = paint;
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

    /**
     * 设置带箭头的线
     * @param isEnd
     * @param paint
     */
    public void resetLine(boolean isEnd, Paint paint){
        this.isChanged = true;
        this.isEnd = isEnd;
        this.linePaint = paint;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(linePaint != null){
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
            //绘制线
            canvas.drawLine(trueStartX, trueStartY, trueEndX, trueEndY, linePaint);
            //以下代码虽被废弃，但价值存在
//            length = (int) Math.sqrt(Math.pow(Math.abs(trueStartX - trueEndX),2) + Math.pow(Math.abs(trueStartY - trueEndY), 2));

            //绘制箭头
            if(isChanged){
                if(isEnd){
                    canvas.drawCircle(trueEndX,trueEndY,10,linePaint);
                }
                else {
                    canvas.drawCircle(trueStartX,trueStartY,10,linePaint);
                }
            }
        }
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
        this.invalidate();
    }

    public Paint getLinePaint() {
        return linePaint;
    }

    public void setLinePaint(Paint linePaint) {
        this.linePaint = linePaint;
    }

    public int getLength(){
        return length;
    }
}
