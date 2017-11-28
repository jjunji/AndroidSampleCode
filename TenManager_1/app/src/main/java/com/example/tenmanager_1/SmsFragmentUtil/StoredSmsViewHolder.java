package com.example.tenmanager_1.SmsFragmentUtil;

import android.widget.RadioButton;
import android.widget.TextView;


/**
 * Created by 전지훈 on 2017-11-13.
 */

public class StoredSmsViewHolder {
    TextView txtItemTitle;
    public RadioButton radioBtn;
    int tag;

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag(){
        return tag;
    }
}
