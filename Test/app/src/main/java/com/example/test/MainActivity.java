package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test.CallHistory.CallHistoryActivity;
import com.example.test.Contact.ContactActivity;
import com.example.test.SmsHistory.SmsHistoryActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnContact, btnCallHistory, btnSmsHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setClickListener();
    }

    private void init() {
        btnContact = (Button) findViewById(R.id.btnContact);
        btnCallHistory = (Button) findViewById(R.id.btnCallHistory);
        btnSmsHistory = (Button) findViewById(R.id.btnSmsHistory);
    }

    private void setClickListener(){
        btnContact.setOnClickListener(this);
        btnCallHistory.setOnClickListener(this);
        btnSmsHistory.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnContact :
                startActivity(new Intent(this, ContactActivity.class));
                break;

            case R.id.btnCallHistory :
                startActivity(new Intent(this, CallHistoryActivity.class));
                break;

            case R.id.btnSmsHistory :
                startActivity(new Intent(this, SmsHistoryActivity.class));
                break;
        }
    }
}
