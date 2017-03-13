package com.devwilly.tutorial.viewdraghelperex.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Willy on 2017/3/11.
 */

public class ViewDragLayout extends ViewGroup {

    private static final int VEL_THRESHOLD = 500;
    private static final int DISTANCE_THRESHOLD = 500;
    private View mFirstView, mSecondView;
    private ViewDragHelper mViewDragHelper;
    private GestureDetectorCompat mGestureDetectorCompat;
    private int mFirstHeight;

    public ViewDragLayout(Context context) {
        super(context);
        init();
    }

    public ViewDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mGestureDetectorCompat = new GestureDetectorCompat(getContext(), new YScrollDetector());
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

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int finalTop = top;

            if (child == mFirstView) {
                if (top > 0) {
                    finalTop = 0;
                }
            } else if (child == mSecondView) {
                if (top < 0) {
                    finalTop = 0;
                }
            }

            return finalTop;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mFirstView) {
                mSecondView.offsetTopAndBottom(dy);
            } else if (changedView == mSecondView) {
                mFirstView.offsetTopAndBottom(dy);
            }

            ViewCompat.postInvalidateOnAnimation(ViewDragLayout.this);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            animTopOrBottom(releasedChild, yvel);
        }
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void animTopOrBottom(View releasedChild, float yvel) {
        int finalTop = 0;

        if (releasedChild == mFirstView) {
            if (yvel < -VEL_THRESHOLD || (releasedChild.getTop() < -DISTANCE_THRESHOLD)) {
                finalTop = -mFirstHeight;
            }
        } else if (releasedChild == mSecondView) {
            if (yvel > VEL_THRESHOLD || (releasedChild.getTop() > DISTANCE_THRESHOLD)) {
                finalTop = mFirstHeight;
            }
        }

        if (mViewDragHelper.smoothSlideViewTo(releasedChild, 0, finalTop)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        // if "first view" and "second view" are transformed, don't deal with touch event.
        if (mFirstView.getTop() < 0 && mFirstView.getBottom() > 0) {
            return false;
        }

        boolean yScroll = mGestureDetectorCompat.onTouchEvent(ev);
        boolean shouldIntercept = mViewDragHelper.shouldInterceptTouchEvent(ev);

        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            mViewDragHelper.processTouchEvent(ev);
        }
        return yScroll && shouldIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // ViewDragHelper.callback achieve drag effect
        // https://github.com/umano/AndroidSlidingUpPanel/issues/351
        try {
            mViewDragHelper.processTouchEvent(event);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceY) > Math.abs(distanceX);
        }
    }
}
