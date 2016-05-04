package com.ilmare.swipedeleteitem.View;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 侧拉删除控件
 *
 * @author poplar
 */
public class SwipeLayout extends FrameLayout {

    private LinearLayout mFrontView;  //前景

    private LinearLayout mBackView;  //后面的view
    private int mHeight;
    private int  mRange;
    private int mWidth;
    private ViewDragHelper viewDragHelper;

    private ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {


        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;

        }


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return super.clampViewPositionHorizontal(child, left, dx);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);
        }


        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

    };


    public SwipeLayout(Context context) {
        super(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //
        viewDragHelper = ViewDragHelper.create(this, 1.0f, mCallBack);

    }



    @Override
    protected void onFinishInflate() {


        mFrontView = (LinearLayout) getChildAt(0);
        mBackView = (LinearLayout)getChildAt(1);
        super.onFinishInflate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHeight = mFrontView.getMeasuredHeight();
        mWidth = mFrontView.getMeasuredWidth();
        mRange = mBackView.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutContent(false);
    }

    private void layoutContent(boolean isOpen) {
        // 摆放前View
        Rect frontRect = computeFrontViewRect(isOpen);
        mFrontView.layout(frontRect.left, frontRect.top, frontRect.right, frontRect.bottom);
        // 摆放后View
        Rect backRect = computeBackViewViaFront(frontRect);
        mBackView.layout(backRect.left, backRect.top, backRect.right, backRect.bottom);

        // 调整顺序, 把mFrontView前置
        bringChildToFront(mFrontView);
    }



    private Rect computeFrontViewRect(boolean isOpen) {
        int width=0;
        if (isOpen){
            width=-mRange;
        }
        return new Rect(0,0,width,mHeight);
    }

    private Rect computeBackViewViaFront(Rect frontRect) {
        return new Rect(0,0,mWidth,mHeight);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return viewDragHelper.shouldInterceptTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        try {
            viewDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}