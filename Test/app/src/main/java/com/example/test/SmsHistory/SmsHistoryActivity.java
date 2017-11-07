package com.example.test.SmsHistory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.test.CallHistory.CallHistoryAdapter;
import com.example.test.Data.CallHistoryData;
import com.example.test.Data.SmsHistoryData;
import com.example.test.Loader.CallHistoryLoader;
import com.example.test.Loader.ContactLoader;
import com.example.test.Loader.SmsHistoryLoader;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class SmsHistoryActivity extends AppCompatActivity {
    ListView smsListView;
    SmsHistoryAdapter adapter;
    ContactLoader loader = new ContactLoader(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_history);

        smsListView = (ListView) findViewById(R.id.smsListView);

        SmsHistoryLoader ShLoader = new SmsHistoryLoader(this, loader.getContacts());

        List<SmsHistoryData> datas = ShLoader.getContacts();

        smsListView = (ListView) findViewById(R.id.smsListView);
        adapter = new SmsHistoryAdapter((ArrayList<SmsHistoryData>) datas, getApplicationContext());

        smsListView.setAdapter(adapter);
        smsListView.setFastScrollEnabled(true);
    }
}
