package com.example.tenmanager_1.Service_Dialog;

import android.content.Intent;
import android.icu.util.DateInterval;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.tenmanager_1.R;

public class DialogActivity extends AppCompatActivity {

    Button btnSms, btnHistory;
    TextView txtPhoneNumber, txtName;
    private final int SMSFRAGMENT = 1;
    private final int HISTORYFRAGMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

        init();
        callFragment(SMSFRAGMENT);
        setText();
        setButtonListener();
    }

    private void callFragment(int fragmentIndex) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (fragmentIndex){
            case 1 :
                Dialog_SmsFragment dialogSmsFragment = new Dialog_SmsFragment();
                transaction.replace(R.id.dialogContainer, dialogSmsFragment);
                transaction.commit();
                break;
            case 2 :
                Dialog_HistoryFragment dialogHistoryFragment = new Dialog_HistoryFragment();
                transaction.replace(R.id.dialogContainer, dialogHistoryFragment);
                transaction.commit();
                break;
        }
    }

    private void setButtonListener(){
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment(SMSFRAGMENT);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment(HISTORYFRAGMENT);
            }
        });
    }

    private void setText(){
        txtPhoneNumber.setText(getIntent().getStringExtra("phoneNumber"));

    }

    private void init() {
        btnSms = (Button) findViewById(R.id.btnSms);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        txtName = (TextView) findViewById(R.id.txtName);
        txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
    }



}
