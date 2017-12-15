package com.example.newbies.myapplication.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.newbies.myapplication.R;

/**
 *
 * @author NewBies
 * @date 2017/12/14
 */

public class CustomDialog  extends ProgressDialog {
    public CustomDialog(Context context) {
        super(context);
    }


    public CustomDialog(Context context, int theme)
    {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context)
    {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.progress_dialog_1);
    }

    @Override
    public void show()
    {
        super.show();
    }
}