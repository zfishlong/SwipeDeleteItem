package com.ilmare.swipedeleteitem.View;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.security.PrivateKey;

/**
 * Created by zhangchenggeng
 * Time 2016/5/5 8:59.
 * Descripton:
 * History:
 * 版权所有
 */
public class MySwipeLayout extends FrameLayout {


    private LinearLayout mBackView;
    private RelativeLayout mFrontView;

    private int mWidth; //前面宽度
    private int mRange; //后面宽度
    private int mHeigh; //前面view高度

    private ViewDragHelper mDragHelper;

    public static enum Status{
        Close, Open, Draging
    }

    private  Status lastStatus=Status.Close;
    private OnSwipeLayoutListener onSwipeLayoutListener;

    public static interface OnSwipeLayoutListener {

        void onClose(MySwipeLayout mSwipeLayout);

        void onOpen(MySwipeLayout mSwipeLayout);

        // 要去关闭
        void onStartClose(MySwipeLayout mSwipeLayout);
        // 要去开启
        void onStartOpen(MySwipeLayout mSwipeLayout);
    }

    public OnSwipeLayoutListener getOnSwipeLayoutListener() {
        return onSwipeLayoutListener;
    }

    public void setOnSwipeLayoutListener(OnSwipeLayoutListener onSwipeLayoutListener) {
        this.onSwipeLayoutListener = onSwipeLayoutListener;
    }

    public MySwipeLayout(Context context) {
        this(context, null);
    }


    public MySwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //1
        mDragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }



    //2
    private ViewDragHelper.Callback  callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        // 限定移动范围
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            // left
            if(child == mFrontView){
                if(left > 0){
                    return 0;
                }else if(left < -mRange){
                    return -mRange;
                }
            }else if (child == mBackView) {
                if(left > mWidth){
                    return mWidth;
                }else if (left < mWidth - mRange) {
                    return mWidth - mRange;
                }
            }
            return left;
        }


        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

            // 传递事件
            if(changedView == mFrontView){
                mBackView.offsetLeftAndRight(dx);
            }else if (changedView == mBackView) {
                mFrontView.offsetLeftAndRight(dx);
            }

            dispatchSwipeEvent();

            // 兼容老版本
            invalidate();
        }

        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            if (xvel == 0 && mFrontView.getLeft() < -mRange / 2.0f) {
                open();
            }else if (xvel < 0) {
                open();
            }else {
                close();
            }

        }

    };


    public void close() {
        Toast.makeText(getContext(), "Close", Toast.LENGTH_SHORT).show();
        close(true);
    }

    private void close(boolean isSmooth) {
        int finalLeft = 0;
        if(isSmooth){
            //开始动画
            if(mDragHelper.smoothSlideViewTo(mFrontView, finalLeft, 0)){
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }else {
            layoutContent(false);
        }
    }


    public void open() {
        Toast.makeText(getContext(), "Open", Toast.LENGTH_SHORT).show();
        open(true);
    }

    public void open(boolean isSmooth){
        int finalLeft = -mRange;
        if(isSmooth){
            //开始动画
            if(mDragHelper.smoothSlideViewTo(mFrontView, finalLeft, 0)){
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }else {
            layoutContent(true);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if(mDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }


    public  void dispatchSwipeEvent() {

        Status currentStatus=updateStatus();

        if(onSwipeLayoutListener!=null){
            if(lastStatus!=currentStatus){
                if(lastStatus==Status.Open&&currentStatus==Status.Draging){
                    onSwipeLayoutListener.onStartClose(this);
                } else if(lastStatus==Status.Close&&currentStatus==Status.Draging){
                    onSwipeLayoutListener.onStartOpen(this);
                }else if (currentStatus==Status.Close){
                    onSwipeLayoutListener.onClose(this);
                }else if(currentStatus==Status.Open){
                    onSwipeLayoutListener.onOpen(this);
                }
            }
        }
        lastStatus=currentStatus;
    }

    private Status updateStatus() {

        if(mFrontView.getLeft()==0){
            return Status.Close;
        }else if(mFrontView.getLeft()==-mRange){
            return Status.Open;
        }else{
            return Status.Draging;
        }
    }

    //3
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println(mDragHelper);
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // 当xml被填充完毕时调用
        mBackView = (LinearLayout) getChildAt(0);
        mFrontView = (RelativeLayout) getChildAt(1);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = mFrontView.getMeasuredWidth();
        mHeigh = mFrontView.getMeasuredHeight();
        mRange = mBackView.getMeasuredWidth();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        layoutContent(false);

    }

    //根据是否打开 摆放布局
    private void layoutContent(boolean isOpen) {
        Rect mFrontRect=computeFrontBound(isOpen);
        mFrontView.layout(mFrontRect.left, mFrontRect.top, mFrontRect.right, mFrontRect.bottom);

        Rect mBackRect=computeBackBound(mFrontRect);
        mBackView.layout(mBackRect.left,mBackRect.top,mBackRect.right,mBackRect.bottom);

        bringChildToFront(mFrontView);
    }

    private Rect computeBackBound(Rect mFrontRect) {
        int left=mFrontRect.right;
        return new Rect(left,0,left+mRange,mHeigh);
    }

    private Rect computeFrontBound(boolean isOpen) {
        int left=0;
        if(isOpen){
            left=-mRange;
        }
        return new Rect(left,0,left+mWidth,mHeigh);
    }



}
