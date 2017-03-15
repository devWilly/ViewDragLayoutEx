package com.devwilly.tutorial.viewdraghelperex.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


/**
 * Created by Willy on 2017/3/13.
 */

public class CustomRecyclerView extends RecyclerView {

    private int mTouchSlop;
    private float mMotionEventDownX;
    private float mMotionEventDownY;
    private boolean mCanScrollUp;
    private boolean mCanScrollDown;

    public CustomRecyclerView(Context context) {
        super(context);
        init();
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mMotionEventDownX = ev.getRawX();
                mMotionEventDownY = ev.getRawY();
                mCanScrollUp = canScrollingUp();
                mCanScrollDown = canScrollingDown();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float xDistance = Math.abs(mMotionEventDownX - ev.getRawX());
                float yDistance = Math.abs(mMotionEventDownY - ev.getRawY());

                if (yDistance > xDistance && yDistance > mTouchSlop) {
                    if (mMotionEventDownY > ev.getRawY() && mCanScrollDown) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }

                    if (mMotionEventDownY < ev.getRawY() && mCanScrollUp) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }

                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    private boolean canScrollingUp() {
        if (ViewCompat.canScrollVertically(this, -1)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean canScrollingDown() {
        if (ViewCompat.canScrollVertically(this, 1)) {
            return false;
        } else {
            return true;
        }
    }
}
