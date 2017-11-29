package com.example.newbies.myapplication.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 *
 * @author NewBies
 * @date 2017/11/28
 */
public class ClickLinearlayout extends LinearLayout implements Checkable {

    private boolean isCheck;

    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };

    public ClickLinearlayout(Context context) {
        super(context);
    }

    public ClickLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * onCreateDrawableState函数将state_checked属性加入到view的状态列表中，这样就可以在selector设置了。
     * @param extraSpace
     * @return
     */
    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
    @Override
    public void setChecked(boolean checked) {
        isCheck = checked;
        refreshDrawableState();
    }

    @Override
    public boolean isChecked() {
        return isCheck;
    }

    @Override
    public void toggle() {
        isCheck = !isCheck;
    }
}
