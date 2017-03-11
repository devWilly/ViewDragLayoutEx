package com.devwilly.tutorial.viewdraghelperex.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;


/**
 * Created by Willy on 2017/3/11.
 */

public class ViewDragLayout extends ViewGroup {

    public ViewDragLayout(Context context) {
        super(context);
    }

    public ViewDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
