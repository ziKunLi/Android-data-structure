package com.example.newbies.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.newbies.myapplication.R;

/**
 *
 * @author NewBies
 * @date 2017/12/1
 */
public class InputPopupWindow extends PopupWindow {

    private Context context;
    private View view;
    /**
     * 通过输入产生哈夫曼树的按钮
     */
    private Button sure;
    /**
     * 提供输入的文本框
     */
    public EditText someText;

    public InputPopupWindow(final Activity context, View.OnClickListener cencelOnClick, View.OnClickListener sureOnClick){
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.input_popupwindow,null);
        this.setContentView(this.view);
        sure = (Button)view.findViewById(R.id.sure);
        someText = (EditText)view.findViewById(R.id.someText);
        //设置按钮监听
        sure.setOnClickListener(sureOnClick);
        //设置外部可点击
        this.setOutsideTouchable(true);

        //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效
        this.setBackgroundDrawable(new ColorDrawable());
        //点击外部关闭。
        this.setOutsideTouchable(true);
        //设置一个动画。
        this.setAnimationStyle(android.R.style.Animation_Dialog);

        // 设置弹出窗体可点击
        this.setFocusable(true);
    }
}
