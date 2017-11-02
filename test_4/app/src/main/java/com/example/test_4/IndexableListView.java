package com.example.test_4;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 김혜정 on 2017-11-02.
 */

public class IndexableListView extends RecyclerView implements RecyclerView.OnItemTouchListener{

    public IndexScroller mScroller = null;
    //String mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";

    public IndexableListView(Context context) {
        super(context);
    }

    public IndexableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public List<String> mSections = new ArrayList<String>(){
        {
            add("#");
            add("ㄱ");
            add("ㄴ");
            add("ㄷ");
            add("ㄹ");
            add("ㅁ");
            add("ㅂ");
            add("ㅅ");
            add("ㅇ");
            add("ㅈ");
            add("ㅊ");
            add("ㅋ");
            add("ㅌ");
            add("ㅍ");
            add("ㅎ");
        }
    };

    public void init() {
        addOnItemTouchListener(this);
    }

    public void setFastScrollEnabled(boolean enable) {
        if (enable) {
            if (mScroller == null)
                mScroller = new IndexScroller(getContext(), this, mSections);
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
        if (mScroller != null)
            mScroller.show();

        // Intercept ListView's touch event
        if (mScroller != null && mScroller.onTouchEvent(ev))
            return true;


        return super.onTouchEvent(ev);
    }

    public void setIndexAdapter(List<String> sectionName, List<Integer> sectionPosition) {
        if (mScroller != null)
            mScroller.notifyChanges(sectionName, sectionPosition);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mScroller != null)
            mScroller.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void stopScroll() {
        try
        {
            super.stopScroll();
        }
        catch( NullPointerException exception )
        {
            Log.i("RecyclerView", "NPE caught in stopScroll");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mScroller != null && mScroller.contains(e.getX(), e.getY())) {
            mScroller.show();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

}
