package com.devwilly.tutorial.viewdraghelperex.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Willy on 2017/3/11.
 */

public class ViewDragLayout extends ViewGroup {

    private View mFirstView, mSecondView;
    private int mFirstHeight;

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
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFirstView = getChildAt(0);
        mSecondView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mFirstView.getTop() == 0) {
            mFirstView.layout(l, 0, r, b - t);
            mSecondView.layout(l, 0, r, b - t);

            mFirstHeight = mFirstView.getMeasuredHeight();
            mSecondView.offsetTopAndBottom(mFirstHeight);
        } else {
            mFirstView.layout(l, mFirstView.getTop(), r, mFirstView.getBottom());
            mSecondView.layout(l, mSecondView.getTop(), r, mSecondView.getBottom());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(
                resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }
}
