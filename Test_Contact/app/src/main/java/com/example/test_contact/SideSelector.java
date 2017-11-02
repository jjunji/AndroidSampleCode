package com.example.test_contact;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SectionIndexer;

/**
 * Created by 김혜정 on 2017-11-01.
 */

public class SideSelector extends View {
    private static String TAG = SideSelector.class.getCanonicalName();


   /* char[] ChoSung = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146,
            0x3147, 0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };*/
 /*   int c = key.charAt(0) - 0xAC00;
    int a = c / (21 * 28);
    han_keyList.add(Character.toString(ChoSung[a]));*/

    public static char[] ALPHABET = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static char[] HANGUL = new char[]{0x3131, 0x3134, 0x3137, 0x3139, 0x3141, 0x3142, 0x3145,
            0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};



    public static final int BOTTOM_PADDING = 10;

    private SectionIndexer selectionIndexer = null;
    //private ListView list;
    private RecyclerView list;
    private Paint paint;
    private String[] sections;

    public SideSelector(Context context) {
        super(context);
        init();
    }

    public SideSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SideSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //setBackgroundColor(0xFF00FF00);
        paint = new Paint();
        paint.setColor(0xFFA6A9AA);
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public void setListView(RecyclerView _list) {
        list = _list;
        selectionIndexer = (SectionIndexer) _list.getAdapter();

        Object[] sectionsArr = selectionIndexer.getSections();
        sections = new String[sectionsArr.length];
        for (int i = 0; i < sectionsArr.length; i++) {
            sections[i] = sectionsArr[i].toString();
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int y = (int) event.getY();
        float selectedIndex = ((float) y / (float) getPaddedHeight()) * HANGUL.length;

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (selectionIndexer == null) {
                selectionIndexer = (SectionIndexer) list.getAdapter();
            }
            int position = selectionIndexer.getPositionForSection((int) selectedIndex);
            if (position == -1) {
                return true;
            }
            //list.setSelection(position);
            list.scrollToPosition(position);
            Log.i("SideSelector"," 현재위치 ===" + position);

        }
        return true;
    }

    protected void onDraw(Canvas canvas) {

        int viewHeight = getPaddedHeight();
        float charHeight = ((float) viewHeight) / (float) sections.length;

        float widthCenter = getMeasuredWidth() / 2;
        for (int i = 0; i < sections.length; i++) {
            canvas.drawText(String.valueOf(sections[i]), widthCenter, charHeight + (i * charHeight), paint);
        }
        super.onDraw(canvas);
    }

    private int getPaddedHeight() {
        return getHeight() - BOTTOM_PADDING;
    }
}
