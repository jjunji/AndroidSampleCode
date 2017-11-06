package com.example.test.CallHistory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.test.Contact.ContactAdapter;
import com.example.test.Contact.IndexableListView;
import com.example.test.Data.CallHistoryData;
import com.example.test.Data.ContactData;
import com.example.test.Loader.CallHistoryLoader;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class CallHistoryActivity extends AppCompatActivity {
    ListView callListView;
    CallHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);

        callListView = (ListView) findViewById(R.id.callListView);

        CallHistoryLoader ChLoader = new CallHistoryLoader(this);

        List<CallHistoryData> datas = ChLoader.getContacts();

        callListView = (ListView) findViewById(R.id.callListView);
        adapter = new CallHistoryAdapter((ArrayList<CallHistoryData>) datas, getApplicationContext());

        callListView.setAdapter(adapter);
        callListView.setFastScrollEnabled(true);
    }
}
