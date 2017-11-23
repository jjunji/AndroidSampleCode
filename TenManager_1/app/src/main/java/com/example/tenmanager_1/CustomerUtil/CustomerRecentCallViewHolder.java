package com.example.tenmanager_1.CustomerUtil;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 전지훈 on 2017-11-14.
 */

public class CustomerRecentCallViewHolder {
    ImageView imgType;
    TextView txtName;
    TextView txtNumber;
    TextView txtDate;
    ImageView btnCall;
    ImageView btnSend;
    int tag;

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }
}
