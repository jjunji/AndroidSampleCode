package com.example.tenmanager_1.CustomerUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.tenmanager_1.ContactUtil.IndexScroller;

/**
 * Created by 전지훈 on 2017-11-14.
 */

public class IndexableCustomerListView extends ListView {

    private boolean mIsFastScrollEnabled = false;
    private IndexScroller mScroller = null;
    private GestureDetector mGestureDetector = null;

    public IndexableCustomerListView(Context context) {
        super(context);
    }

    public IndexableCustomerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexableCustomerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFastScrollEnabled() {
        return mIsFastScrollEnabled;
    }

    @Override
    public void setFastScrollEnabled(boolean enabled) {
        mIsFastScrollEnabled = enabled;
        if (mIsFastScrollEnabled) {
            if (mScroller == null)
                //mScroller = new IndexScroller(getContext(), this);
                mScroller = new IndexScroller(getContext(), this);
        } else {
            if (mScroller != null) {
                mScroller.hide();
                mScroller = null;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Overlay index bar

        if (mScroller != null)
            mScroller.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Intercept ListView's touch event
        if (mScroller != null && mScroller.onTouchEvent(ev))
            return true;

        if (mGestureDetector == null) {
            mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2,
                                       float velocityX, float velocityY) {
                    // If fling happens, index bar shows
                    if (mScroller != null)
                        mScroller.show();

                    return super.onFling(e1, e2, velocityX, velocityY);
                }

            });
        }
        mGestureDetector.onTouchEvent(ev);

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = super.onInterceptTouchEvent(ev);
        if(ret){
            if(mScroller.contains(ev.getX(), ev.getY())){
                getParent().requestDisallowInterceptTouchEvent(true);
                return ret;
            }
        }
/*        if(mScroller.contains(ev.getX(), ev.getY()))
        return true;*/
        //return super.onInterceptTouchEvent(ev);
        return ret;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if (mScroller != null)
            mScroller.setAdapter(adapter);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mScroller != null)
            mScroller.onSizeChanged(w, h, oldw, oldh);
    }

}
