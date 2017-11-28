package com.example.newbies.myapplication.callBack;

import com.example.newbies.myapplication.callBackImp.OpenActivityImp;

/**
 * Created by NewBies on 2017/11/26.
 */

public class RecycleCallBack {

    private OpenActivityImp openActivityImp;

    public void setOnOpenActivityListener(OpenActivityImp openActivityListener){
        this.openActivityImp = openActivityListener;
    }
}
