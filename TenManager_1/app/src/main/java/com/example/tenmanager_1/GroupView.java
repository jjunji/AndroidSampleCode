package com.example.tenmanager_1;

/**
 * Created by 전지훈 on 2017-11-23.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GroupView {
    View contentView;
    Context mContext;
    boolean selected;
    TextView tvTitle;
    View vLine;
    int index;

    public GroupView(Context mContext, ViewGroup parent, String title, int index) {
        this.mContext = mContext;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        contentView = mInflater.inflate(R.layout.view_group, parent, false);

        tvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
        vLine = contentView.findViewById(R.id.vLine);

        tvTitle.setText(title);
        this.index = index;

        contentView.setTag(this);
    }

    public void setOnClickListner(View.OnClickListener listener) {
        contentView.setOnClickListener(listener);
    }

    public View getView() {
        return contentView;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;

        if (selected) {
//            contentView.setBackgroundResource(R.drawable.line);
            vLine.setVisibility(View.VISIBLE);
//            tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.appColor));
            tvTitle.setTextColor(Color.parseColor("#d81b25"));
        } else {
//            contentView.setBackgroundResource(R.drawable.bg_subtabbar_noselect);
            vLine.setVisibility(View.INVISIBLE);
            tvTitle.setTextColor(Color.parseColor("#333333"));
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }


}
