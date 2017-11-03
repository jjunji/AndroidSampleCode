package com.example.test_3;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class itemView extends LinearLayout {

    TextView txtName, txtPhoneNumber;
    Button button2;

    public itemView(Context context) {
        super(context);
        init(context);
    }

    public itemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_contact,this, true);

        txtName = (TextView) findViewById(R.id.txtName);
        txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
        button2 = (Button) findViewById(R.id.button2);
    }

    public void setName(String name){
        txtName.setText(name);
    }

    public void setTel(String phoneNumber){
        txtPhoneNumber.setText(phoneNumber);
    }

}
