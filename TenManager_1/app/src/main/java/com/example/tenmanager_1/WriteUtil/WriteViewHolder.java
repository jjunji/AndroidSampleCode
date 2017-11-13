package com.example.tenmanager_1.WriteUtil;

import android.nfc.Tag;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 전지훈 on 2017-11-10.
 */

public class WriteViewHolder{
    ImageView btnUp;
    ImageView btnDown;
    TextView txtTitle;
    TextView txtContent;
    CheckBox smsCheckBox;
    int tag;

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag(){
        return tag;
    }
}
